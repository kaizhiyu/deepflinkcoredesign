package chapter06;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;

/**
 * create 2021-01-05
 * author zy
 */
public class SourceWithTimestampWatermarks implements SourceFunction<EventBean> {
    private int count  =0 ;
    private volatile boolean isRunning = true;
    @Override
    public void run(SourceContext<EventBean> sourceContext) throws Exception {
        while (isRunning){
            if (count >= 16){
                isRunning = false;
            }else{
                EventBean bean = Data.BEANS[count];
                sourceContext.collectWithTimestamp(bean,bean.getTime());
                System.out.println("send data is :" + bean.getList().get(0) + "==" + bean.getTime());
                if(bean.getList().get(0).indexOf("late") < 0){
                    sourceContext.emitWatermark(new Watermark(System.currentTimeMillis()));
                }
                if(bean.getList().get(0).indexOf("nosleep") < 0){
                    Thread.sleep(10000);
                }
            }
            count ++;
        }
    }

    @Override
    public void cancel() {

    }
}
