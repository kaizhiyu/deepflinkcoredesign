package userbehavioranaly


import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AscendingTimestampExtractor
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author zy
 * @date 2021-01-23
 */
object HotItemAnalysis {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val source : DataStream[UserBehavior] = env.readTextFile("C:\\Users\\Administrator\\IdeaProjects\\deepflinkcoredesign\\src\\main\\resources")
      .map(line =>{
        val splitStr = line.split(",")
        UserBehavior(splitStr(0).toLong,splitStr(1).toLong,splitStr(2).toLong,splitStr(3),splitStr(4).toLong * 1000)
      })

    val assigned = source.assignAscendingTimestamps(_.time)
    val filtered = assigned.filter(_.behavior == "pv")
    val keyed = filtered.keyBy(_.itemId)
    val windowed = keyed.timeWindow(Time.seconds(300),Time.seconds(30))
    windowed.aggregate(new HotAggregateFunction(),new CountWindow()).keyBy(_.windowEnd).process(new TopNHotItems(3)).print()

    env.execute("hot item analysis.")
  }
}

case class UserBehavior(userId:Long,itemId:Long,categoryId:Long,behavior:String,time:Long)
case class ItemViewCount(itemId:Long,windowEnd:Long,count:Long)
