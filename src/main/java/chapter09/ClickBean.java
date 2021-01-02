package chapter09;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * create 2021-01-02
 * author zy
 */
public class ClickBean {

    public ClickBean(){

    }

    public ClickBean(long id, String user, Timestamp time,String url){
        this.id = id;
        this.user = user;
        this.time = time;
        this.url = url;
    }


    private Long id;
    private String user;
    private Timestamp time;
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
