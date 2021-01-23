package userbehavioranaly


import java.util.Date

import org.apache.flink.streaming.api.functions.{AscendingTimestampExtractor, AssignerWithPeriodicWatermarks}
import org.apache.flink.streaming.api.watermark.Watermark

class MyAscendingAssigner extends AssignerWithPeriodicWatermarks[UserBehavior]{
  override def getCurrentWatermark: Watermark = {
    return new Watermark(new Date().getTime)
  }

  override def extractTimestamp(t: UserBehavior, l: Long): Long = {
    return t.time
  }
}
