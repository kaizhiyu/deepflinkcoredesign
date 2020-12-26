package chapter05;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create 2020-12-26
 * author zy
 */
public class StateSource implements SourceFunction<Tuple2<Integer,Integer>> {
    static Logger logger = LoggerFactory.getLogger(StateSource.class);

    private int counter = 0;

    @Override
    public void run(SourceContext<Tuple2<Integer, Integer>> ctx) throws Exception {
        while (true){
            ctx.collect(new Tuple2<>(counter % 5,counter));
            logger.info("send data:{},{}",counter%5,counter);
            counter++;
            Thread.sleep(500);
        }
    }

    @Override
    public void cancel() {

    }
}
