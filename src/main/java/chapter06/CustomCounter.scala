package chapter06

import org.apache.flink.metrics.Counter

class CustomCounter extends Counter{
  override def inc(): Unit = ???

  override def inc(l: Long): Unit = ???

  override def dec(): Unit = ???

  override def dec(l: Long): Unit = ???

  override def getCount: Long = ???
}
