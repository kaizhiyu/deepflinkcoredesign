package chapter06;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.stream.Stream;

/**
 * create 2020-12-27
 * author zy
 */
public class TumblingWindowTest {
    public static void main(String[] args) throws  Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
        DataStream<Tuple3<String,Integer,String>> source = env.addSource(new SourceForWindow(1,false));
        KeyedStream<Tuple3<String,Integer,String>, Tuple> keyedStream = source.keyBy("f0");
        WindowedStream<Tuple3<String,Integer,String>,Tuple, TimeWindow> windowedStream = keyedStream.window(TumblingEventTimeWindows.of(Time.seconds(3)));
        DataStream<Tuple3<String,Integer,String>> sum = windowedStream.sum("f1");
        sum.print("窗口统计：");
        env.execute("window");

    }
}
