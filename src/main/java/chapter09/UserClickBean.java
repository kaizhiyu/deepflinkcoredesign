package chapter09;

public class UserClickBean {
    private String user;
    private Long num;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "UserClickBean{" +
                "user='" + user + '\'' +
                ", num=" + num +
                '}';
    }
}
