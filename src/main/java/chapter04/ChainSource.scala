package chapter04

import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}

/**
 * create 2021-01-09
 * author zy
 * desc 任务链使用的源
 */
class ChainSource extends RichSourceFunction[Tuple2[String,Integer]]{
  val sleep = 30 * 1000
  override def run(ctx: SourceFunction.SourceContext[(String, Integer)]): Unit = {
    println(Thread.currentThread().getId)
    ctx.collect(Tuple2("185xxx",899))
    println("source操作所属子任务名称：" + getRuntimeContext.getTaskNameWithSubtasks + ";element=" + Tuple2("185xxx",899))
    Thread.sleep(sleep)
    println(Thread.currentThread().getId)
    ctx.collect(Tuple2("155xxx",1199))
    println("source操作所属子任务名称：" + getRuntimeContext.getTaskNameWithSubtasks + ";element=" + Tuple2("155xxx",1199))
    Thread.sleep(sleep)
    println(Thread.currentThread().getId)
    ctx.collect(Tuple2("138xxx",19))
    println("source操作所属子任务名称：" + getRuntimeContext.getTaskNameWithSubtasks + ";element=" + Tuple2("138xxx",19))
    Thread.sleep(sleep)
  }

  override def cancel(): Unit = {

  }
}
