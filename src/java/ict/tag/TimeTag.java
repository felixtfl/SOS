/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.tag;

import java.sql.Date;
import javax.servlet.jsp.*;
import java.io.*;
import java.util.Calendar;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TimeTag extends SimpleTagSupport {
    private String orderid;
    private Date orderDate;

    public void doTag() {
        try {
            checkdate(orderDate,orderid);
        } catch (Exception e) {
        }
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderid() {
        return orderid;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void checkdate(Date Orderdate,String id) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        Date today = new java.sql.Date(currentDate.getTime());
        Date orderday = new Date(Orderdate.getYear(), Orderdate.getMonth(), orderDate.getDate() + 1);
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out=pageContext.getOut();
        try{
        if (today.compareTo(orderday) > 0) {
            out.print("<td></td>");
        } else if (today.compareTo(orderday) < 0) {
            // now show button
            out.println("<td><form id='cancel' action='cart'><input type=\"hidden\" name=\"action\" value=\"cancelorder\"><input type=\"hidden\" name=\"orderId\" value='"+orderid+"' ><button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"button\" onclick='cancel()' >Cancel</button></form></td>");
        } else if (today.compareTo(orderday) == 0) {
            System.out.println("Date1 is equal to Date2");
        }
        }catch(IOException e){}
    }
}
