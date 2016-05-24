package ict.bean;

import java.util.ArrayList;

public class CartBean {
    String userid,status;
    ArrayList al;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setAl(ArrayList al) {
        this.al = al;
    }

    public String getUserid() {
        return userid;
    }

    public ArrayList getAl() {
        return al;
    }
}
