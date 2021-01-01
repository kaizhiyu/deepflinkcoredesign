package chapter08;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

/**
 * create 2021-01-01
 * author zy
 */
public class First {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnvironment = TableEnvironment.getTableEnvironment(env);
        DataStream<Long> stream = env.fromElements(1L,2L,3L,4L);
        tableEnvironment.registerDataStream("test",stream);
        Table result = tableEnvironment.sqlQuery("select * from test");
        tableEnvironment.toAppendStream(result, Row.class).print();
        env.execute("first");
    }
}
