package chapter09;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;


/**
 * create 2021-01-03
 * author zy
 * desc 处理时间
 */
public class ProcessTimePractise {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        String[] names = new String[]{"id","number"};
        TypeInformation[] types = new TypeInformation[]{Types.INT, Types.INT};
        DataStream<Row> source = env.addSource(new SourceFunction<Row>() {
            @Override
            public void run(SourceContext<Row> sourceContext) throws Exception {
                int count = 1;
                while (true){
                    Row row = Row.of(count %2,count);
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
        StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
        tableEnv.registerDataStream("test",source,"id,number,autoAddTime.proctime");
        String sql = "select id," +
                "sum(number) as ss," +
                "TUMBLE_START(autoAddTime,INTERVAL '10' SECOND) as wStart," +
                "TUMBLE_END(autoAddTime,INTERVAL '10' SECOND) as wEnd," +
                "TUMBLE_PROCTIME(autoAddTime,INTERVAL '10' SECOND) as we from test " +
                "group by TUMBLE(autoAddTime,INTERVAL '10' SECOND),id";
        Table table  = tableEnv.sqlQuery(sql);
        tableEnv.toRetractStream(table,Row.class).print();
        env.execute("process window");
    }
}
