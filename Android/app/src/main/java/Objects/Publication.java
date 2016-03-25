package Objects;

import java.util.Date;

/**
 * Created by Sergio on 18/2/16.
 */
public class Publication {

    private String date, from, to, carname, caryear, username;

    public Publication(String date, String city, String username){
        this.date = date;
        this.from = city;
        this.username = username;
        this.to = "";
        this.carname = "";
        this.caryear = "";
    }

    public Publication(String date, String from, String to, String carname, String caryear, String username) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.carname = carname;
        this.caryear = caryear;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getCaryear() {
        return caryear;
    }

    public void setCaryear(String caryear) {
        this.caryear = caryear;
    }

    public String toString(){
        return date + ":" + from + ":" + to + ":" + carname + ":" + caryear + ":" + username;
    }
}
