package chapter09;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.List;

public class ClickSource extends RichSourceFunction<ClickBean> {
    @Override
    public void run(SourceContext<ClickBean> ctx) throws Exception {
        while (true){
            List<ClickBean> list = PrepareData.getClickData();
            for (int i =0 ;i< list.size();i++){
                ctx.collect(list.get(i));
            }
            Thread.sleep(300);
        }
    }

    @Override
    public void cancel() {

    }
}
