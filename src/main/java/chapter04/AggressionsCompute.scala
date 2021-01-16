package chapter04

import java.util

import common.Trade
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
 * @author zy
 * @dte 2021-01-16
 */
object AggressionsCompute {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val list = new util.ArrayList[Trade]()
    list.add(new Trade("188XXX",30,"2018-07"))
    list.add(new Trade("188XXX",20,"2018-07"))
    list.add(new Trade("155XXX",10,"2018-07"))
    list.add(new Trade("155XXX",90,"2018-07"))
    val source = env.fromCollection(Seq(
       TradeTest("188XXX",30,"2018-07"),
         TradeTest("188XXX",20,"2018-07"),
       TradeTest("155XXX",10,"2018-07"),
     TradeTest("155XXX",90,"2018-07")
    ))

    val keyedStream = source.keyBy("cardNum")

    keyedStream.sum("trade").print("sum")
    keyedStream.max("trade").print("max")
    keyedStream.min("trade").print("min")
    env.execute("aggregation")

  }
}

case class TradeTest(cardNum:String,trade:Int,time:String)
