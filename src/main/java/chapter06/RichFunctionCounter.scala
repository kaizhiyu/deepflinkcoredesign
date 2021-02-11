package chapter06

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.java.tuple.Tuple3
import org.apache.flink.configuration.Configuration
import org.apache.flink.metrics.Counter

class RichFunctionCounter extends RichMapFunction[Tuple3[String, Integer, String],Tuple3[String, Integer, String]]{
  var myCounter : Counter = null
  override def open(parameters: Configuration): Unit = {
    myCounter = getRuntimeContext().getMetricGroup().counter("MyCounter")
  }

  override def map(in: Tuple3[String, Integer, String]): Tuple3[String, Integer, String] = {
    myCounter.inc()
    in
  }
}
