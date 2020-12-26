package chapter05;


import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * create 2020-12-26
 * author zy
 */
public class KeyedStatBase {
    public static void main(String[] args) {

    }

    public static KeyedStream<Tuple2<Integer,Integer>, Tuple> before(StreamExecutionEnvironment env) throws Exception{
        env.setParallelism(2);
        DataStream<Tuple2<Integer,Integer>> inputStream = env.addSource(new StateSource());
        KeyedStream<Tuple2<Integer,Integer>,Tuple> keyedStream = inputStream.keyBy(0);
        return keyedStream;
    }
}
