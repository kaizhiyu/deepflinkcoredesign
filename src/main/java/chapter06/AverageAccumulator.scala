package chapter06

class AverageAccumulator{
  var word = ""
  var count:Long = 0L
  var sum : Long = 0L

  override def toString: String = {
    "word = " + word + ",sum= " + sum + ",count=" + count
  }
}
