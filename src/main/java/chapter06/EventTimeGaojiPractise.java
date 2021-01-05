package chapter06;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * create 2021-01-15
 * author zy
 */
public class EventTimeGaojiPractise {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStream<EventBean> source = env.addSource(new SourceWithTimestampWatermarks());
        DataStream<EventBean> result = source.windowAll(TumblingEventTimeWindows.of(Time.seconds(30)))
                .reduce(new ReduceFunction<EventBean>() {
                    @Override
                    public EventBean reduce(EventBean eventBean, EventBean t1) throws Exception {
                        eventBean.getList().add(t1.getList().get(0));
                        return eventBean;
                    }
                });

        result.print("输出结果");
        env.execute("event_time");
    }
}
