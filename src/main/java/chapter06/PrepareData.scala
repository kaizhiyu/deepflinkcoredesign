package chapter06

import java.util

import common.Trade

/**
 * @create 2021-01-30
 * @author zy
 */
object PrepareData {
  def getClicksData(): java.util.List[ClickBean] ={
    val result = new java.util.ArrayList[ClickBean]()
    result.add(new ClickBean(1,"张三","/intsmze","2019-07-28 12:00:00"))
    result.add(new ClickBean(2,"李四","/flink","2019-07-28 12:05:00"))
    result.add(new ClickBean(3,"张三","/stream","2019-07-28 12:08:00"))
    result.add(new ClickBean(4,"李四","/intsmze","2019-07-28 12:10:00"))
    result.add(new ClickBean(5,"王五","/flink","2019-07-28 12:12:00"))
    result
  }

  def getTradeData(): java.util.List[Trade] ={
    val result = new util.ArrayList[Trade]()
    result.add(new Trade("张三",38,"安卓手机","2019-07-28 12:20:00"))
    result.add(new Trade("王五",45,"安卓手机","2019-07-28 12:30:00"))
    result.add(new Trade("张三",18,"台式机","2019-07-28 13:20:00"))
    result.add(new Trade("王五",23,"笔记本","2019-07-28 13:40:00"))
    result
  }
}
