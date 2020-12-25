package chapter04;

import org.apache.flink.api.common.functions.Partitioner;

/**
 * create 2020-12-25
 * author zy
 */
public class MyPartitioner implements Partitioner<String> {
    @Override
    public int partition(String key, int numPartitions) {
        if(key.indexOf("185") >= 0){
            return 0;
        }else if(key.indexOf("155") >=0 ){
            return 1;
        }else{
            return 2;
        }
    }
}
