package chapter06;

import java.io.Serializable;

public class ClickBean implements Serializable {
    public ClickBean(){

    }

    public ClickBean(long id,String user,String url,String dateTime){
        this.id = id;
        this.user= user;
        this.url = url;
        this.dateTime = dateTime;
    }

    private long id;
    private String user;
    private String url;
    private String dateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
