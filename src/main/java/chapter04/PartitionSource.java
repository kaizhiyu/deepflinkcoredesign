package chapter04;

import common.Trade;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * create 2020-12-25
 * author zy
 */
public class PartitionSource extends RichSourceFunction<Trade> {
    @Override
    public void run(SourceContext<Trade> sourceContext) throws Exception {
        List<Trade> list = new ArrayList<>();
        list.add(new Trade("185XXX",899,"2018"));
        list.add(new Trade("155XXX",1111,"2019"));
        list.add(new Trade("155XXX",1199,"2019"));
        list.add(new Trade("185XXX",899,"2018"));
        list.add(new Trade("138XXX",19,"2019"));
        list.add(new Trade("138XXX",399,"2020"));
        for(int i =0;i<list.size();i++){
            Trade trade = list.get(i);
            sourceContext.collect(trade);
        }
        String subtaskName = getRuntimeContext().getTaskNameWithSubtasks();
        System.out.println("source操作所属子任务名称:" + subtaskName);

    }

    @Override
    public void cancel() {

    }
}
