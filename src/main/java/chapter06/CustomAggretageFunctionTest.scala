package chapter06

import org.apache.flink.api.java.tuple.Tuple3
import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * create 2021-01-24
 * author zy
 */
object CustomAggretageFunctionTest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val streamSource = env.addSource(new SourceForWindow(1000, false))

    val wind = streamSource.keyBy(0).window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
    wind.aggregate(new Average()).print("结果")

    env.execute("aggregate funct job")
  }
}
