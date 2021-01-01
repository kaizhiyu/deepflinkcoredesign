package chapter08

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.scala._
import org.apache.flink.api.scala._
import org.apache.flink.table.api.TableEnvironment
/**
 * create 2021-01-01
 * author zy
 */
object HelloWorld {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tableEnv = TableEnvironment.getTableEnvironment(env)
    val stream = env.fromElements(1L,2L,3L,4L)
    tableEnv.registerDataStream("test",stream)
    val table = tableEnv.sqlQuery("select * from test")
    val result = tableEnv.toAppendStream[Long](table)
    result.print()
    env.execute("hello world")
  }
}
