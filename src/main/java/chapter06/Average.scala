package chapter06

import org.apache.flink.api.java.tuple.Tuple3

class Average extends org.apache.flink.api.common.functions.AggregateFunction[Tuple3[String,Integer,String],AverageAccumulator,AverageAccumulator]{
  override def createAccumulator(): AverageAccumulator = {
    new AverageAccumulator()
  }

  override def add(in: Tuple3[String, Integer, String], acc: AverageAccumulator): AverageAccumulator = {
    acc.word = in.f0
    acc.sum = acc.sum + in.f1
    acc.count = acc.count + 1
    acc
  }

  override def getResult(acc: AverageAccumulator): AverageAccumulator = {
    acc
  }

  override def merge(acc: AverageAccumulator, acc1: AverageAccumulator): AverageAccumulator = {
    acc.count = acc.count + acc1.count
    acc.sum = acc.sum + acc1.sum
    acc
  }
}
