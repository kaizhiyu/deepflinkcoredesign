package chapter04

import org.apache.flink.api.common.functions.{RichFilterFunction, RichMapFunction}
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
 * create 2021-01-09
 * author zy
 * desc 任务链flink程序
 */
object ChainTaskPractise {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(3)
    val source = env.addSource(new ChainSource)
    val filtered = source.filter(new RichFilterFunction[(String, Integer)] {
      override def filter(t: (String, Integer)): Boolean = {
        println(Thread.currentThread().getId)
        println("filter操作所属子任务名称：" + getRuntimeContext.getTaskNameWithSubtasks + ",element=" + t)
        true
      }
    })
    val mapOne = filtered.map(new RichMapFunction[(String,Integer),(String,Integer)] {
      override def map(in: (String, Integer)): (String,Integer) = {
        println("map one操作所属子任务名称：" + getRuntimeContext.getTaskNameWithSubtasks + ",element=" + in)
        in
      }
    })

    val mapTwo = mapOne.map(new RichMapFunction[(String,Integer),(String,Integer)] {
      override def map(in: (String, Integer)): (String, Integer) = {
        println("map two操作所属子任务名称：" + getRuntimeContext.getTaskNameWithSubtasks + ",element=" + in)
        in
      }
    })
    mapTwo.print("数据结果")
    env.execute("chain")
  }
}
