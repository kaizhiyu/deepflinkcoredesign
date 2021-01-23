package userbehavioranaly

import org.apache.flink.api.common.functions.AggregateFunction

class HotAggregateFunction extends AggregateFunction[UserBehavior,Long,Long]{
  override def createAccumulator(): Long = {
    return  0L
  }

  override def add(in: UserBehavior, acc: Long): Long = {
    acc +1
  }

  override def getResult(acc: Long): Long = {
    return acc
  }

  override def merge(acc: Long, acc1: Long): Long = {
    return acc + acc1
  }
}
