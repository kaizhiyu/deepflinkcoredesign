package chapter04;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class GenerationAbarse {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env  = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<Long,Long>> ds = env.fromElements(Tuple2.of(1L,1L));
        ds.map(i -> i).returns(Types.TUPLE(Types.LONG,Types.LONG)).print();
        env.execute("ff");
    }
}
