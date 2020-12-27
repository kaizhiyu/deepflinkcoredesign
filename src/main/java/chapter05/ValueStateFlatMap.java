package chapter05;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

public class ValueStateFlatMap extends RichFlatMapFunction<Tuple2<Integer,Integer>,Tuple2<Integer,Integer>> {
    private transient ValueState<Tuple2<Integer,Integer>> valueState;

    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<Tuple2<Integer,Integer>> descriptor = new ValueStateDescriptor<Tuple2<Integer, Integer>>("valueStateFlatMap",
                TypeInformation.of(new TypeHint<Tuple2<Integer, Integer>>() {
                }));
        valueState = getRuntimeContext().getState(descriptor);
        
    }

    @Override
    public void flatMap(Tuple2<Integer, Integer> input, Collector<Tuple2<Integer, Integer>> collector) throws Exception {
        Tuple2<Integer,Integer> currentSum = valueState.value();
        if(currentSum == null){
            currentSum = input;
        }else{
            currentSum.f1 = currentSum.f1 + input.f1;
        }

        if(currentSum.f1 % 10 >= 6){
            collector.collect(currentSum);
//            valueState.clear();
        }else{
            valueState.update(currentSum);
        }
    }
}
