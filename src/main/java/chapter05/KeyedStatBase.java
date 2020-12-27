package chapter05;


import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * create 2020-12-26
 * author zy
 */
public class KeyedStatBase {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(1000);

        env.setParallelism(1);
        KeyedStream<Tuple2<Integer,Integer>,Tuple> keyedStream = before(env);
        DataStream<Tuple2<Integer,Integer>> resutlStream = keyedStream.flatMap(new ValueStateFlatMap()).uid("kewew");
        resutlStream.print();
        env.execute("afd");
    }

    public static KeyedStream<Tuple2<Integer,Integer>, Tuple> before(StreamExecutionEnvironment env) throws Exception{
        env.setParallelism(2);
        DataStreamSource<Tuple2<Integer,Integer>> inputStream = env.addSource(new StateSource());
        inputStream.uid("kkk");
        KeyedStream<Tuple2<Integer,Integer>,Tuple> keyedStream = inputStream.keyBy(0);
        return keyedStream;
    }
}
