package chapter04;


import common.Trade;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * create 2020-12-25
 * author zy
 */
public class CustomTemplate {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(6);
        final String flag = "分区策略前子任务名称：";
        DataStream<Trade> inputStream = env.addSource(new PartitionSource());
        DataStream<Trade> mapOne = inputStream.map(new RichMapFunction<Trade, Trade>() {
            @Override
            public Trade map(Trade trade) throws Exception {
                RuntimeContext context = getRuntimeContext();
                String subTaskName = context.getTaskNameWithSubtasks();
                int subtaskIndexOf = context.getIndexOfThisSubtask();
                System.out.println("元素值：" + trade.getCardNum() + flag + subTaskName + ",子任务编号：" + subtaskIndexOf);
                return trade;
            }
        });
        DataStream<Trade> mapTwo = mapOne.partitionCustom(new MyPartitioner(),"cardNum");

        DataStream<Trade> mapThree = mapTwo.map(new RichMapFunction<Trade, Trade>() {
            @Override
            public Trade map(Trade trade) throws Exception {
                RuntimeContext context = getRuntimeContext();
                String subTaskName = context.getTaskNameWithSubtasks();
                int subtaskIndexOf = context.getIndexOfThisSubtask();
                System.out.println("元素值：" + trade.getCardNum() + " 分区策略后子任务名称：" + subTaskName + ",子任务编号：" + subtaskIndexOf);
                return trade;
            }
        });

        mapThree.print();
        env.execute("first");
    }
}
