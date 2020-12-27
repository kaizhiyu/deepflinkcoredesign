package chapter05

import org.apache.flink.contrib.streaming.state.RocksDBStateBackend
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
 * create 2020-12-27
 * author zy
 */
object SavePointedTemplate {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(2)
    env.enableCheckpointing(1000)
    val path = "file:///j:/tmp/test_001"
    val fsStateBackend = new RocksDBStateBackend(path)
    env.setStateBackend(fsStateBackend)
    val source = env.addSource(new StateSource)
    val mapStream = source.map(r => r).uid("map_id")
    mapStream.print()
    env.execute("jbo")

  }
}
