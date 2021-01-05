package chapter09;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import javax.annotation.Nullable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * create 2021-01-03
 * author zy
 * desc 事件时间
 */
public class EventTimePractise {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        String[] names = new String[]{"id","number","jj"};
        TypeInformation[] types = new TypeInformation[]{Types.INT, Types.INT,Types.SQL_TIMESTAMP};
        DataStream<Row> source = env.addSource(new SourceFunction<Row>() {
            @Override
            public void run(SourceContext<Row> sourceContext) throws Exception {
                int count = 1;
                while (true){
                    Row row = Row.of(count %2,count,new Timestamp(System.currentTimeMillis() ));
                    System.out.println("Send data is:" + row);
                    sourceContext.collect(row);
                    Thread.sleep(300);
                    count++;
                }
            }

            @Override
            public void cancel() {

            }
        }).returns(Types.ROW_NAMED(names,types));
//        source.print();
        source.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Row>(){

            @Override
            public long extractTimestamp(Row row, long l) {
                long result =  ((Timestamp)row.getField(2)).getTime();

                return new Timestamp(System.currentTimeMillis()).getTime() * 1000;
            }

            @Nullable
            @Override
            public Watermark getCurrentWatermark() {
                return new Watermark(System.currentTimeMillis());
            }
        });
        StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
        tableEnv.registerDataStream("test",source,"id,number,jj.rowtime");
        String sql = "select id," +
                "sum(number) as ss," +
                "TUMBLE_START(jj,INTERVAL '10' SECOND) as wStart," +
                "TUMBLE_ROWTIME(jj,INTERVAL '10' SECOND)," +
                "TUMBLE_END(jj,INTERVAL '10' SECOND) as wEnd" +
                " from test " +
                "group by TUMBLE(jj,INTERVAL '10' SECOND),id";
        Table table  = tableEnv.sqlQuery(sql);
        tableEnv.toRetractStream(table,Row.class).print();
        env.execute("process window");
    }
}
