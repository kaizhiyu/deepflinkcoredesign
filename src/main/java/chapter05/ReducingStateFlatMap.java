package chapter05;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class ReducingStateFlatMap extends RichFlatMapFunction<Tuple2<Integer,Integer>,Tuple2<Integer,Integer>> {

    public transient ReducingState<Tuple2<Integer,Integer>> reducingState;

    @Override
    public void open(Configuration parameters) throws Exception {
        ReducingStateDescriptor<Tuple2<Integer,Integer>> descriptor = new ReducingStateDescriptor<Tuple2<Integer, Integer>>("reducing",
                new ReduceFunction<Tuple2<Integer, Integer>>() {
                    @Override
                    public Tuple2<Integer, Integer> reduce(Tuple2<Integer, Integer> v1, Tuple2<Integer, Integer> v2) throws Exception {
                        return new Tuple2<>(v1.f0,v1.f1 + v2.f1);
                    }
                }, TypeInformation.of(new TypeHint<Tuple2<Integer, Integer>>() {})
        );
        reducingState = getRuntimeContext().getReducingState(descriptor);
    }

    @Override
    public void flatMap(Tuple2<Integer, Integer> input, Collector<Tuple2<Integer,Integer>> output) throws Exception {
        reducingState.add(input);
        output.collect(input);
    }

    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        KeyedStream<Tuple2<Integer,Integer>, Tuple> keyedStream = KeyedStatBase.before(env);
        DataStream<Tuple2<Integer,Integer>> outputed = keyedStream.flatMap(new ReducingStateFlatMap());
        outputed.print("reducing");
        env.execute("reducing state flatmap job");
    }
}
