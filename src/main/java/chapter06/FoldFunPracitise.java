package chapter06;

import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class FoldFunPracitise {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple3<String,Integer,String>> source = env.addSource(new SourceForWindow(1000,false));
        DataStream<String> ds = source.keyBy("f0")
                .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
                .fold("start", new FoldFunction<Tuple3<String, Integer, String>, String>() {
                    @Override
                    public String fold(String s, Tuple3<String, Integer, String> o) throws Exception {
                        s = s + "--" + o.f1;
                        return s;
                    }
                });
        ds.print("输出结果");
        env.execute("fold function");
    }
}
