package chapter09;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import java.util.stream.Stream;

/**
 * create 2021-01-02
 * author zy
 * desc 持续查询例子
 */
public class ContinueQuery {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
        DataStream<ClickBean> source = env.addSource(new ClickSource());
        tableEnv.registerDataStream("click",source,"id,user,time,url");
        Table table = tableEnv.sqlQuery("select user,count(1) as num from click group by user");
        DataStream<Tuple2<Boolean,UserClickBean>> clickStatisticsStream = tableEnv.toRetractStream(table,UserClickBean.class);
        DataStream<UserClickBean> result = clickStatisticsStream.map(tu ->{
            return tu.f1;
        });
        result.print();
        env.execute("user click statistics");
    }
}
