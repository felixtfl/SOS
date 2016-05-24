/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;

import java.io.Serializable;

public class CategoryBean implements Serializable{
    String catid,catname;

    public String getCatid() {
        return catid;
    }

    public CategoryBean() {
    }

    public String getCatname() {
        return catname;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }
    
}
