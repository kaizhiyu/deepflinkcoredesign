package chapter04

import java.{lang, util}

import common.Trade
import org.apache.flink.streaming.api.collector.selector.OutputSelector
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
 * @author zy
 * @date 2021-01-16
 * @desc 练习split流，交易流，大于100到一个流，小于100到一个流。 DataStream ---> SplitStream
 */
object SplitStreamPractise {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val list = new util.ArrayList[Trade]()
    list.add(new Trade("188XXX",899,"2018-07"))
    list.add(new Trade("138XXX",1199,"2018-07"))
    list.add(new Trade("155XXX",99,"2018-07"))
    //要指定类型
    val source :DataStream[Trade] = env.fromCollection(Seq(
      new Trade("188XXX",899,"2018-07"),
        new Trade("138XXX",1199,"2018-07"),
      new Trade("155XXX",99,"2018-07")
    ))
    val splitFun = new OutputSelector[Trade]() {
      override def select(out: Trade): lang.Iterable[String] = {
        val result = new util.ArrayList[String]()
        if(out.trade < 100){
          result.add("small amount")
        }else if(out.trade >= 100){
          result.add("Large amount")
        }
        return result
      }
    }
    val splitedStream = source.split(splitFun)
    splitedStream.select("small amount").print("small amount")
    splitedStream.select("Large amount").print("large amount")
    env.execute("split stream")
  }
}
