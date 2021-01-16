package chapter04;

import common.Trade;
import org.apache.flink.api.common.functions.Partitioner;

/**
 * create 2021-01-10
 * author zy
 * desc
 */
public class TradePartitioner implements Partitioner<Trade> {
    @Override
    public int partition(Trade trade, int i) {
        return 0;
    }
}
