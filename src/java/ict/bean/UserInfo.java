/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;
import java.io.Serializable;

public class UserInfo implements Serializable{
    private String  uid,name,username,password,type,address,phone;
    private int point,amount;

    public void setId(String id) {
        this.uid = id;
    }

    public String getId() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public String getPhone() {
        return phone;
    }

    public int getPoint() {
        return point;
    }

    public String getType() {
        return type;
    }
    public int getIntType(){
        if(type.equals("User")){
            return 1;
        }else if (type.equals("Manager")){
            return 2;
        }else{
            return 0;
        }
    }
    public void setAddress(String address) {
        this.address = address;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setType(int type) {
        switch(type){
            case 0:
                this.type = "";
                break;
            case 1:
                this.type="User";
                break;
            case 2:
                this.type="Manager";
                break;
        }
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public UserInfo() {
    }
}