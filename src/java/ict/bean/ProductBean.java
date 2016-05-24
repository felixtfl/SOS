package ict.bean;

import java.io.Serializable;
public class ProductBean implements Serializable{

    String ProductID;
    String ProductCat;
    String ProductName;
    String ProductBrand;
    String Photourl;
    String Descriptions;
    Double Price;
    int Qty;
    boolean isGift;
    public ProductBean() {
    }

    public boolean isIsGift() {
        return isGift;
    }

    public void setIsGift(boolean isGift) {
        this.isGift = isGift;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductCat() {
        return ProductCat;
    }

    public void setProductCat(String ProductCat) {
        this.ProductCat = ProductCat;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getProductBrand() {
        return ProductBrand;
    }

    public void setProductBrand(String ProductBrand) {
        this.ProductBrand = ProductBrand;
    }

    public String getPhotourl() {
        return Photourl;
    }

    public void setPhotourl(String Photourl) {
        this.Photourl = Photourl;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(String Descriptions) {
        this.Descriptions = Descriptions;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double Price) {
        this.Price = Price;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int Qty) {
        this.Qty = Qty;
    }
}