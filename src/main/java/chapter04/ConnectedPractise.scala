package chapter04

import org.apache.flink.streaming.api.functions.co.CoMapFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
 * @author zy
 * @date 2021-01-16
 *
 */
object ConnectedPractise {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val longStream : DataStream[Long] = env.fromCollection(Seq(
      1L,
      2L
    ))
    val strStream:DataStream[String] = env.fromCollection(Seq(
      "www cnblogs com intsmaze",
      "hello intsmaze",
      "hello java",
      "hello flink"
    ))

    val connectedStream = longStream.connect(strStream)
    val conMaped = connectedStream.map(new CoMapFunction[Long,String,String] {
      override def map1(in1: Long): String = {
        "数据来自Long的流：" + in1
      }

      override def map2(in2: String): String = {
        "来自String的流" +in2
      }
    })
    conMaped.print("comapfunction tempate:")
    env.execute("conneted stream practise")
  }
}
