package chapter06;

import java.util.ArrayList;
import java.util.List;

/**
 * create 2021-01-05
 * author zy
 */
public class EventBean {
    private List<String> list;
    private Long time;

    public EventBean(String text,long time){
        list = new ArrayList<>();
        list.add(text);
        this.time = time;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        String content = "";
        for (int i =0;i<list.size();i++){
            content += list.get(i) + ",";
        }
        return "EventBean{" +
                "list=[" + content.substring(0,content.length() - 1) +
                "], time=" + time +
                '}';
    }
}
