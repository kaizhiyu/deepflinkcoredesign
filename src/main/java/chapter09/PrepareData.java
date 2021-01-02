package chapter09;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * create 2021-01-02
 * author zy
 * desc 准备数据（点击流）
 */
public class PrepareData {
    public static int couter = 0;
    public static String[] users = {"张三","李四","王五"};
    public static String[] urls = {"/flink","/spark","intsmaze","sql"};
    public static List<ClickBean> getClickData(){
        List<ClickBean> list = new ArrayList<>();
        list.add(new ClickBean(couter ++,getUser(),new Timestamp(System.currentTimeMillis()),getUrl()));
        list.add(new ClickBean(couter ++,getUser(),new Timestamp(System.currentTimeMillis()),getUrl()));
        list.add(new ClickBean(couter ++,getUser(),new Timestamp(System.currentTimeMillis()),getUrl()));
        list.add(new ClickBean(couter ++,getUser(),new Timestamp(System.currentTimeMillis()),getUrl()));
        list.add(new ClickBean(couter ++,getUser(),new Timestamp(System.currentTimeMillis()),getUrl()));
        list.add(new ClickBean(couter ++,getUser(),new Timestamp(System.currentTimeMillis()),getUrl()));
        list.add(new ClickBean(couter ++,getUser(),new Timestamp(System.currentTimeMillis()),getUrl()));
        list.add(new ClickBean(couter ++,getUser(),new Timestamp(System.currentTimeMillis()),getUrl()));
        list.add(new ClickBean(couter ++,getUser(),new Timestamp(System.currentTimeMillis()),getUrl()));
        return list;
    }

    static String getUser(){
        Random random = new Random();
        int index = random.nextInt(3);
        return users[index];
    }

    static String getUrl(){
        Random random = new Random();
        int index = random.nextInt(4);
        return urls[index];
    }
}
