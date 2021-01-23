package chapter06;


import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.source.SourceFunction;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create 2020-12-27
 * author zy
 */
public class SourceForWindow implements SourceFunction<Tuple3<String,Integer,String>> {
    private volatile boolean isRunning = false;

    private Long sleepTime;

    private boolean stopSession = false;

    public SourceForWindow(long sleepTime,boolean stopSession){
        this.sleepTime = sleepTime;
        this.stopSession = stopSession;
        isRunning = true;
    }

    @Override
    public void run(SourceContext<Tuple3<String, Integer, String>> ctx) throws Exception {
        int count = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        while(isRunning){
            String word = WORDS[count % WORDS.length];
            String time = sdf.format(new Date());
            Tuple3<String,Integer,String> result = Tuple3.of(word,count,time);
            ctx.collect(result);
            System.out.println("send data: " + result);

            if(stopSession || count % WORDS.length == 0){
                Thread.sleep(10000);
            }else{
                Thread.sleep(sleepTime);
            }
            count++;
        }
    }

    @Override
    public void cancel() {

    }

    public static final String[] WORDS = new String[]{
      "intsmaze","intsmaze","intsmaze",
      "intsmaze","intsmaze","java",
      "flink","flink","flink",
      "hadoop","spark","streaming"
    };
}
