package common;

import java.io.Serializable;

/**
 * create 2020-12-25
 * author zy
 */
public class Trade implements Serializable {

    public Trade(){

    }

    public Trade(String cardNum,int trade,String time){
        this.cardNum = cardNum;
        this.trade = trade;
        this.time = time;
    }

    public Trade(String user,int trade,String cardNum,String time){
        this.user = user;
        this.cardNum = cardNum;
        this.trade = trade;
        this.time = time;
    }


    public String cardNum;
    public int trade;
    public String time;
    public String user;

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public int getTrade() {
        return trade;
    }

    public void setTrade(int trade) {
        this.trade = trade;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "cardNum='" + cardNum + '\'' +
                ", trade=" + trade +
                ", time='" + time + '\'' +
                '}';
    }
}
