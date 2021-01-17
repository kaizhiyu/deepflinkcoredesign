package chapter05;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import scala.math.Ordering;

import java.util.Iterator;

/**
 * @author zy
 * @date 2021-01-17
 */
public class ListStateFlatMap extends RichFlatMapFunction<Tuple2<Integer,Integer>,String> {
    public transient ListState<Tuple2<Integer,Integer>> listState;

    @Override
    public void open(Configuration parameters) throws Exception {
        ListStateDescriptor<Tuple2<Integer,Integer>> descriptor = new ListStateDescriptor<Tuple2<Integer, Integer>>(
                "listStateFlatmap", TypeInformation.of(new TypeHint<Tuple2<Integer, Integer>>(){})
        );

        listState = getRuntimeContext().getListState(descriptor);
    }

    @Override
    public void flatMap(Tuple2<Integer, Integer> input, Collector<String> output) throws Exception {
        listState.add(input);
        Iterator<Tuple2<Integer,Integer>> iterator = listState.get().iterator();
        int number =0;
        StringBuffer stringBuffer = new StringBuffer();
        while (iterator.hasNext()){
            stringBuffer.append(":" + iterator.next());
            number++;
            if(number == 3){
                listState.clear();
                output.collect(stringBuffer.toString());
            }
        }
    }


    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        KeyedStream<Tuple2<Integer,Integer>, Tuple> keyedStream = KeyedStatBase.before(env);
        DataStream<String> ds = keyedStream.flatMap(new ListStateFlatMap());
        ds.print("result is:");
        env.execute("list state.");
    }


}
