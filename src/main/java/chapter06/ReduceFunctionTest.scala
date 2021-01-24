package chapter06

import com.sun.jersey.core.util.StringIgnoreCaseKeyComparator
import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * create 2020-12-28
 * author zy
 * desc reduce函数练习
 */
object ReduceFunctionTest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val streamSource = env.addSource(new SourceForWindow(1000,false))
    val reduceStream =  streamSource.keyBy("f0")
      .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
      .reduce((old,current) =>{
        if(old.f1 > current.f1){
          current
        }else{
          old
        }
      })
    reduceStream.print()
    env.execute("reduct_function")
  }
}
