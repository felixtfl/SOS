
package ict.bean;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Bozo
 */
public class OrderBean implements Serializable{
    String orderId,status;
    Date OrderDate;
    int Total;
    int PointTotal;
    String userid;

    public void setPointTotal(int PointTotal) {
        this.PointTotal = PointTotal;
    }

    public int getPointTotal() {
        return PointTotal;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public OrderBean() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date OrderDate) {
        this.OrderDate = OrderDate;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }
    
}