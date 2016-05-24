/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import ict.bean.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class DB {

    String dburl = "", dbUser = "", dbPassword = "";

    public DB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "org.apache.derby.jdbc.ClientDriver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public void createCustTable() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "CREATE TABLE userInfo ("
                    + "UserId VARCHAR(5) CONSTRAINT PK_CUSTOMER PRIMARY KEY, "
                    + "username VARCHAR(25),name VARCHAR(25), password VARCHAR(25),address VARCHAR(50),phone VARCHAR(10),type INTEGER not null,point INTEGER,amount INTEGER)";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void createCategoryTable() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "CREATE TABLE Category ("
                    + "CategoryId VARCHAR(5) CONSTRAINT PK_Category PRIMARY KEY, CategoryName VARCHAR(25))";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void createProductTable() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "CREATE TABLE PRODUCT ("
                    + "ProductId VARCHAR(10) CONSTRAINT PK_PRODUCT PRIMARY KEY, "
                    + "ProductCat VARCHAR(5) ,CONSTRAINT FK_PRODUCT FOREIGN KEY (ProductCat) REFERENCES Category(CategoryId), "
                    + "ProductName VARCHAR(25), Brand VARCHAR(10),Descriptions VARCHAR(50), Price INTEGER NOT NULL,PhotoUrl VARCHAR(100),isGift boolean)";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void createOrderLineTable() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "CREATE TABLE ORDERLINE ("
                    + "OrderId VARCHAR(10), "
                    + "ProductId VARCHAR(10) ,CONSTRAINT FK_ORDERLINE_PID FOREIGN KEY (ProductId) REFERENCES PRODUCT(ProductId), "
                    + "UserId VARCHAR(5), CONSTRAINT FK_ORDERLINE_USERID FOREIGN KEY (UserId) REFERENCES USERINFO(UserId), "
                    + "OrderDate Date,OrderQty INTEGER,Status VARCHAR(10))";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addAccount(String name, String username, String phone, String address, String password, int type) {
        boolean isSuccess = false;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        PreparedStatement getid = null;
        int pid = 0;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO  APP.USERINFO (USERID, NAME, USERNAME, PASSWORD, PHONE, ADDRESS, TYPE) VALUES (?,?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            String sql = "Select COUNT(USERID) as USERID from APP.USERINFO";
            getid = cnnct.prepareStatement(sql);
            ResultSet rs = null;
            rs = getid.executeQuery();
            if (rs.next()) {
                pid = rs.getInt("USERID");
                pid++;
            }
            pStmnt.setString(1, pid + "");
            pStmnt.setString(2, name);
            pStmnt.setString(3, username);
            pStmnt.setString(4, password);
            pStmnt.setString(5, phone);
            pStmnt.setString(6, address);
            pStmnt.setInt(7, type);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }

    public boolean isValidUser(String user, String pwd) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean iss = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "select * from APP.USERINFO WHERE USERNAME=? and PASSWORD=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, user);
            pStmnt.setString(2, pwd);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("type") == 0) {
                    iss = false;
                } else {
                    iss = true;
                }
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return iss;
    }

    public UserInfo getUserdetail(String user, String pwd) {
        UserInfo U = null;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "select * from APP.USERINFO WHERE USERNAME=? and PASSWORD=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, user);
            pStmnt.setString(2, pwd);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            U = new UserInfo();
            if (rs.next()) {
                U.setUsername(user);
                U.setPassword(pwd);
                U.setId(rs.getString("UserId"));
                U.setName(rs.getString("name"));
                U.setPhone(rs.getString("phone"));
                U.setType(rs.getInt("type"));
                U.setPoint(rs.getInt("point"));
                U.setAddress(rs.getString("address"));
                U.setAmount(rs.getInt("amount"));
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return U;
    }

    public boolean updateInfo(UserInfo user) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean iss = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE UserInfo SET Name=?,phone=?,address=? WHERE UserId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, user.getName());
            pStmnt.setString(2, user.getPhone());
            pStmnt.setString(3, user.getAddress());
            pStmnt.setString(4, user.getId());
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                iss = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return iss;
    }

    public boolean changePwd(String id, String password, String newpwd) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean iss = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE UserInfo SET Password=? WHERE UserId=? and Password=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, newpwd);
            pStmnt.setString(2, id);
            pStmnt.setString(3, password);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                iss = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return iss;
    }

    public ArrayList queryCust() {
        Connection cnnct = null;
        Statement stmnt = null;
        ArrayList<UserInfo> al = null;
        try {
            al = new ArrayList<UserInfo>();
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "select * from APP.UserInfo ";
            ResultSet rs = null;
            rs = stmnt.executeQuery(sql);
            while (rs.next()) {
                UserInfo U = new UserInfo();
                U.setUsername(rs.getString("username"));
                U.setPassword(rs.getString("password"));
                U.setId(rs.getString("UserId"));
                U.setName(rs.getString("name"));
                U.setPhone(rs.getString("phone"));
                U.setType(rs.getInt("type"));
                U.setPoint(rs.getInt("point"));
                U.setAddress(rs.getString("address"));
                U.setAmount(rs.getInt("amount"));
                al.add(U);
            }
            stmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return al;
    }

    public ArrayList queryCat() {
        Connection cnnct = null;
        Statement stmnt = null;
        ArrayList al = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "select * from APP.Category ";
            ResultSet rs = null;
            rs = stmnt.executeQuery(sql);
            al = new ArrayList();
            CategoryBean cb;
            while (rs.next()) {
                cb = new CategoryBean();
                cb.setCatid(rs.getString("CategoryId"));
                cb.setCatname(rs.getString("CategoryName"));
                al.add(cb);
            }
            stmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
        }
        return al;
    }

    public ArrayList SearchProductByCat(String name) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList al = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "select * from APP.PRODUCT WHERE ProductCat=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, name);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            al = new ArrayList();
            while (rs.next()) {
                ProductBean Pb = new ProductBean();
                Pb.setProductID(rs.getString("ProductId"));
                Pb.setProductCat(rs.getString("ProductCat"));
                Pb.setProductName(rs.getString("ProductName"));
                Pb.setProductBrand(rs.getString("Brand"));
                Pb.setDescriptions(rs.getString("Descriptions"));
                Pb.setPrice(rs.getDouble("Price"));
                Pb.setPhotourl(rs.getString("PhotoUrl"));
                Pb.setIsGift(rs.getBoolean("isgift"));
                al.add(Pb);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return al;
    }

    public ArrayList queryProduct() {
        Connection cnnct = null;
        Statement stmnt = null;
        ArrayList al = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "select * from APP.PRODUCT";
            ResultSet rs = null;
            rs = stmnt.executeQuery(sql);
            al = new ArrayList<ProductBean>();
            while (rs.next()) {
                ProductBean Pb = new ProductBean();
                Pb.setProductID(rs.getString("ProductId"));
                Pb.setProductCat(rs.getString("ProductCat"));
                Pb.setProductName(rs.getString("ProductName"));
                Pb.setProductBrand(rs.getString("Brand"));
                Pb.setDescriptions(rs.getString("Descriptions"));
                Pb.setPrice(rs.getDouble("Price"));
                Pb.setPhotourl(rs.getString("PhotoUrl"));
                Pb.setIsGift(rs.getBoolean("isgift"));
                al.add(Pb);
            }
            stmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return al;
    }

    public ArrayList queryProductonly() {
        Connection cnnct = null;
        Statement stmnt = null;
        ArrayList al = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "select * from APP.PRODUCT where productcat != '5'";
            ResultSet rs = null;
            rs = stmnt.executeQuery(sql);
            al = new ArrayList<ProductBean>();
            while (rs.next()) {
                ProductBean Pb = new ProductBean();
                Pb.setProductID(rs.getString("ProductId"));
                Pb.setProductCat(rs.getString("ProductCat"));
                Pb.setProductName(rs.getString("ProductName"));
                Pb.setProductBrand(rs.getString("Brand"));
                Pb.setDescriptions(rs.getString("Descriptions"));
                Pb.setPrice(rs.getDouble("Price"));
                Pb.setPhotourl(rs.getString("PhotoUrl"));
                Pb.setIsGift(rs.getBoolean("isgift"));
                al.add(Pb);
            }
            stmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return al;
    }

    public ArrayList SearchProductByName(String name) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList al = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "select * from APP.PRODUCT WHERE ProductName like ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, "%" + name + "%");
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            al = new ArrayList();
            while (rs.next()) {
                ProductBean Pb = new ProductBean();
                Pb.setProductID(rs.getString("ProductId"));
                Pb.setProductCat(rs.getString("ProductCat"));
                Pb.setProductName(rs.getString("ProductName"));
                Pb.setProductBrand(rs.getString("Brand"));
                Pb.setDescriptions(rs.getString("Descriptions"));
                Pb.setPrice(rs.getDouble("Price"));
                Pb.setPhotourl(rs.getString("PhotoUrl"));
                al.add(Pb);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return al;
    }

    public ProductBean QueryProductByID(String ProductID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ProductBean Pb = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "select * from APP.PRODUCT WHERE ProductID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, ProductID);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                Pb = new ProductBean();
                Pb.setProductID(rs.getString("ProductId"));
                Pb.setProductCat(rs.getString("ProductCat"));
                Pb.setProductName(rs.getString("ProductName"));
                Pb.setProductBrand(rs.getString("Brand"));
                Pb.setDescriptions(rs.getString("Descriptions"));
                Pb.setPrice(rs.getDouble("Price"));
                Pb.setPhotourl(rs.getString("PhotoUrl"));
                Pb.setIsGift(rs.getBoolean("isgift"));
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Pb;
    }

    public boolean adminupdate(UserInfo user) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean iss = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE UserInfo SET type=?,amount=? WHERE UserId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, user.getIntType());
            pStmnt.setInt(2, user.getAmount());
            pStmnt.setString(3, user.getId());
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                iss = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return iss;
    }

    public boolean updateCat(CategoryBean cat) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean iss = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE Category SET CategoryName=? WHERE CategoryId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, cat.getCatname());
            pStmnt.setString(2, cat.getCatid());
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                iss = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return iss;
    }

    public boolean addCat(CategoryBean cb) {
        boolean isSuccess = false;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        PreparedStatement getid = null;
        int pid = 0;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO  APP.Category (CategoryID, CategoryNAME) VALUES (?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            String sql = "Select COUNT(CategoryID) as CategoryID from APP.Category";
            getid = cnnct.prepareStatement(sql);
            ResultSet rs = null;
            rs = getid.executeQuery();
            if (rs.next()) {
                pid = rs.getInt("CategoryID");
                pid++;
            }
            pStmnt.setString(1, pid + "");
            pStmnt.setString(2, cb.getCatname());
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }

    public boolean addProduct(ProductBean pb) {
        boolean isSuccess = false;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO  APP.Product (ProductID, ProductCat, ProductName, Brand, Descriptions, Price,PhotoURl,isGift) VALUES (?,?,?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, pb.getProductID());
            pStmnt.setString(2, pb.getProductCat());
            pStmnt.setString(3, pb.getProductName());
            pStmnt.setString(4, pb.getProductBrand());
            pStmnt.setString(5, pb.getDescriptions());
            pStmnt.setDouble(6, pb.getPrice());
            pStmnt.setString(7, pb.getPhotourl());
            pStmnt.setBoolean(8, pb.isIsGift());

            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }

    public void dropAll() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "DROP TABLE OrderLine";
            stmnt.execute(sql);
            sql = "DROP TABLE Product";
            stmnt.execute(sql);
            sql = "DROP Table Category";

            stmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:derby://localhost/SOS_DB";
        String username = "APP";
        String password = "APP";
        DB u = new DB(url, username, password);
        ArrayList<ProductBean> al=u.showOrderDetail("1");
                for(int i=0;i<al.size();i++){
            System.out.println(al.get(i).getProductName());
        }
//        u.dropAll();
//        u.createCustTable();
//        u.createCategoryTable();
//        u.createProductTable();
//        u.createOrderLineTable();
//        u.addAccount("Admin abc","Admin", "66473330", "KT TKO", "123",2);
//        ProductBean pb=new ProductBean();
//        pb.setDescriptions("中文測試 test 123");
//        pb.setIsGift(true);
//        pb.setPhotourl("3.jpg");
//        pb.setPrice(13.0);
//        pb.setProductName("測試 Y 123");
//        pb.setProductBrand("1");
//        pb.setProductCat("1");
//        pb.setProductID("3");
//        u.addProduct(pb);

//            CategoryBean cb = new CategoryBean();
//     cb.setCatname("Stationery");
//        u.addCat(cb);
//        cb.setCatname("File & Filing");
//        u.addCat(cb);
//        cb.setCatname("Office Equipment");
//        u.addCat(cb);
//        cb.setCatname("Display Rack");
//        u.addCat(cb);
//        cb.setCatname("gift");
//        u.addCat(cb);
//
//ProductBean pb = new ProductBean();
//pb.setProductID("1");pb.setProductCat("1");pb.setProductName("pen");pb.setProductBrand("Pilot");pb.setDescriptions("This is a Pilot pen ");pb.setPrice(10.0);pb.setPhotourl("1.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("2");pb.setProductCat("1");pb.setProductName("pen");pb.setProductBrand("UNI");pb.setDescriptions("This is a UNI pen ");pb.setPrice(8.0);pb.setPhotourl("2.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("3");pb.setProductCat("1");pb.setProductName("jellypen");pb.setProductBrand("Zebra");pb.setDescriptions("This is a Zebra jellypen ");pb.setPrice(9.0);pb.setPhotourl("3.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("4");pb.setProductCat("1");pb.setProductName("marker");pb.setProductBrand("Zebra");pb.setDescriptions("This is a Zebra marker ");pb.setPrice(15.0);pb.setPhotourl("4.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("5");pb.setProductCat("1");pb.setProductName("marker");pb.setProductBrand("Zebra");pb.setDescriptions("This is a Zebra marker ");pb.setPrice(15.0);pb.setPhotourl("5.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("6");pb.setProductCat("2");pb.setProductName("bigfile");pb.setProductBrand("Pilot");pb.setDescriptions("This is a Pilot bigfile ");pb.setPrice(15.0);pb.setPhotourl("6.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("7");pb.setProductCat("2");pb.setProductName("file");pb.setProductBrand("UNI");pb.setDescriptions("This is a UNI file ");pb.setPrice(3.0);pb.setPhotourl("7.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("8");pb.setProductCat("2");pb.setProductName("cardfile");pb.setProductBrand("Zebra");pb.setDescriptions("This is a Zebra cardfile ");pb.setPrice(20.0);pb.setPhotourl("8.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("9");pb.setProductCat("2");pb.setProductName("A4 file");pb.setProductBrand("Zebra");pb.setDescriptions("This is a Zebra A4 file ");pb.setPrice(8.0);pb.setPhotourl("9.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("10");pb.setProductCat("2");pb.setProductName("A3 file");pb.setProductBrand("Zebra");pb.setDescriptions("This is a Zebra A3 file ");pb.setPrice(12.0);pb.setPhotourl("10.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("11");pb.setProductCat("3");pb.setProductName("Calculator");pb.setProductBrand("Deli");pb.setDescriptions("This is a Deli Calculator ");pb.setPrice(100.0);pb.setPhotourl("11.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("12");pb.setProductCat("3");pb.setProductName("Calculator(12digit)");pb.setProductBrand("Deli");pb.setDescriptions("This is a Deli Calculator(12digit) ");pb.setPrice(120.0);pb.setPhotourl("12.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("13");pb.setProductCat("3");pb.setProductName("Calculator(14digit)");pb.setProductBrand("Casio");pb.setDescriptions("This is a Casio Calculator(14digit) ");pb.setPrice(140.0);pb.setPhotourl("13.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("14");pb.setProductCat("3");pb.setProductName("binding machine");pb.setProductBrand("Deli");pb.setDescriptions("This is a Deli binding machine ");pb.setPrice(200.0);pb.setPhotourl("14.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("15");pb.setProductCat("3");pb.setProductName("Iron binding machine");pb.setProductBrand("Deli");pb.setDescriptions("This is a Deli Iron binding machine ");pb.setPrice(250.0);pb.setPhotourl("15.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("16");pb.setProductCat("4");pb.setProductName("T-type display(A5)");pb.setProductBrand("Plastic");pb.setDescriptions("This is a Plastic T-type display(A5) ");pb.setPrice(80.0);pb.setPhotourl("16.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("17");pb.setProductCat("4");pb.setProductName("T-type display(A4)");pb.setProductBrand("Plastic");pb.setDescriptions("This is a Plastic T-type display(A4) ");pb.setPrice(60.0);pb.setPhotourl("17.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("18");pb.setProductCat("4");pb.setProductName("Photo display(A4)");pb.setProductBrand("Plastic");pb.setDescriptions("This is a Plastic Photo display(A4) ");pb.setPrice(50.0);pb.setPhotourl("18.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("19");pb.setProductCat("4");pb.setProductName("Photo display(A5)");pb.setProductBrand("Plastic");pb.setDescriptions("This is a Plastic Photo display(A5) ");pb.setPrice(60.0);pb.setPhotourl("19.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("20");pb.setProductCat("4");pb.setProductName("Book display");pb.setProductBrand("Plastic");pb.setDescriptions("This is a Plastic Book display ");pb.setPrice(80.0);pb.setPhotourl("20.jpg");pb.setIsGift(false);u.addProduct(pb);
//pb.setProductID("21");pb.setProductCat("5");pb.setProductName("toy");pb.setProductBrand("LOL");pb.setDescriptions("This is a LOL toy ");pb.setPrice(100.0);pb.setPhotourl("21.jpg");pb.setIsGift(true);u.addProduct(pb);
//pb.setProductID("22");pb.setProductCat("5");pb.setProductName("toy");pb.setProductBrand("IKEA");pb.setDescriptions("This is a IKEA toy ");pb.setPrice(200.0);pb.setPhotourl("22.jpg");pb.setIsGift(true);u.addProduct(pb);
//pb.setProductID("23");pb.setProductCat("5");pb.setProductName("toy");pb.setProductBrand("IKEA");pb.setDescriptions("This is a IKEA toy ");pb.setPrice(300.0);pb.setPhotourl("23.jpg");pb.setIsGift(true);u.addProduct(pb);
//pb.setProductID("24");pb.setProductCat("5");pb.setProductName("iPhone3G");pb.setProductBrand("APPLE");pb.setDescriptions("This is a APPLE iPhone3G ");pb.setPrice(400.0);pb.setPhotourl("24.jpg");pb.setIsGift(true);u.addProduct(pb);
//pb.setProductID("25");pb.setProductCat("5");pb.setProductName("PSP");pb.setProductBrand("SOXY");pb.setDescriptions("This is a SOXY PSP ");pb.setPrice(500.0);pb.setPhotourl("25.jpg");pb.setIsGift(true);u.addProduct(pb);
//
//        ArrayList<OrderBean> al=u.showOrder("1");
//        for(int i=0;i<al.size();i++){
//            System.out.println(al.get(i).getOrderId());
//        }
        //System.out.println(u.isValidUser("Admin", "12223"));
        //System.out.println(u.getUserdetail("Admin", "123").getName());
    }

    public boolean updateProduct(ProductBean pb) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean iss = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE Product SET  ProductCat=?, ProductName=?, Brand=?, Descriptions=?, Price=?,PhotoURl=?,isGift=? WHERE ProductID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, pb.getProductCat());
            pStmnt.setString(2, pb.getProductName());
            pStmnt.setString(3, pb.getProductBrand());
            pStmnt.setString(4, pb.getDescriptions());
            pStmnt.setDouble(5, pb.getPrice());
            pStmnt.setString(6, pb.getPhotourl());
            pStmnt.setBoolean(7, pb.isIsGift());
            pStmnt.setString(8, pb.getProductID());

            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                iss = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return iss;
    }

    public void checkout(CartBean cb) {
        Connection cnnct = null;
        PreparedStatement getid = null;
        PreparedStatement pStmnt = null;
        int orderid = 0;
        try {
            cnnct = getConnection();
            String userid = cb.getUserid();
            ArrayList<ProductBean> al = cb.getAl();
            Calendar calendar = Calendar.getInstance();
            java.util.Date currentDate = calendar.getTime();
            java.sql.Date date = new java.sql.Date(currentDate.getTime());
            String preQueryStatement = "";
            String sql = "Select COUNT(OrderId) as ORDERID from ORDERLINE";
            getid = cnnct.prepareStatement(sql);
            ResultSet rs = null;
            rs = getid.executeQuery();
            if (rs.next()) {
                orderid = rs.getInt("ORDERID");
                orderid++;
            }
            double sum = 0, pointsum = 0;
            for (int i = 0; i < al.size(); i++) {
                preQueryStatement = "INSERT INTO ORDERLINE (OrderId, ProductID, UserId , OrderDate, OrderQty, Status) VALUES (?,?,?,?,?,?)";
                pStmnt = cnnct.prepareStatement(preQueryStatement);
                pStmnt.setString(1, orderid + "");
                pStmnt.setString(2, al.get(i).getProductID());
                pStmnt.setString(3, userid);
                pStmnt.setDate(4, date);
                pStmnt.setInt(5, al.get(i).getQty());
                pStmnt.setString(6, cb.getStatus());
                pStmnt.executeUpdate();
                pointsum = al.get(i).getPrice() * al.get(i).getQty();
                if (al.get(i).isIsGift()) {
                    pointsum = pointsum + (al.get(i).getPrice() * al.get(i).getQty());
                } else {
                    sum = sum + (al.get(i).getPrice() * al.get(i).getQty());
                }
            }
            preQueryStatement = "UPDATE UserInfo SET point=point-? WHERE UserId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, (int) (pointsum));
            pStmnt.setString(2, userid);
            pStmnt.executeUpdate();
            
            preQueryStatement = "UPDATE UserInfo SET amount=amount-? WHERE UserId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, (int) sum);
            pStmnt.setString(2, userid);
            pStmnt.executeUpdate();

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public ArrayList showOrder(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        PreparedStatement getProduct = null;
        ArrayList<OrderBean> al = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT orderID,status,orderdate from ORDERLINE WHERE UserId=? GROUP BY OrderId,status,orderdate";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            al = new ArrayList<OrderBean>();
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                OrderBean ob = new OrderBean();
                int sum = 0;
                int point = 0;
                ob.setOrderId(rs.getString("OrderId"));
                ob.setStatus(rs.getString("Status"));
                ob.setOrderDate(rs.getDate("OrderDate"));
                preQueryStatement = "SELECT * from ORDERLINE WHERE OrderId=?";
                getProduct = cnnct.prepareStatement(preQueryStatement);
                getProduct.setString(1, rs.getString("OrderId"));
                ResultSet rs2 = null;
                rs2 = getProduct.executeQuery();
                while (rs2.next()) {
                    if (QueryProductByID(rs2.getString("ProductId")).isIsGift()) {
                        double price = QueryProductByID(rs2.getString("ProductId")).getPrice();
                        point = point + ((int) price * Integer.parseInt(rs2.getString("OrderQty")));
                    } else {
                        double price = QueryProductByID(rs2.getString("ProductId")).getPrice();
                        sum = sum + ((int) price * Integer.parseInt(rs2.getString("OrderQty")));
                    }
                }
                ob.setTotal(sum);
                ob.setPointTotal(point);
                al.add(ob);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return al;
    }
    public ArrayList showallOrder() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        PreparedStatement getProduct = null;
        ArrayList<OrderBean> al = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT orderID,status,orderdate from ORDERLINE GROUP BY OrderId,status,orderdate";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            
            al = new ArrayList<OrderBean>();
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                OrderBean ob = new OrderBean();
                int sum = 0;
                int point = 0;
                ob.setOrderId(rs.getString("OrderId"));
                ob.setStatus(rs.getString("Status"));
                ob.setOrderDate(rs.getDate("OrderDate"));
                preQueryStatement = "SELECT * from ORDERLINE WHERE OrderId=?";
                getProduct = cnnct.prepareStatement(preQueryStatement);
                getProduct.setString(1, rs.getString("OrderId"));
                ResultSet rs2 = null;
                rs2 = getProduct.executeQuery();
                while (rs2.next()) {
                    if (QueryProductByID(rs2.getString("ProductId")).isIsGift()) {
                        double price = QueryProductByID(rs2.getString("ProductId")).getPrice();
                        point = point + ((int) price * Integer.parseInt(rs2.getString("OrderQty")));
                    } else {
                        double price = QueryProductByID(rs2.getString("ProductId")).getPrice();
                        sum = sum + ((int) price * Integer.parseInt(rs2.getString("OrderQty")));
                    }
                    ob.setUserid(rs2.getString("userid"));
                }
                
                ob.setTotal(sum);
                ob.setPointTotal(point);
                al.add(ob);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return al;
    }
    public ArrayList showOrderDetail(String Orderid) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<ProductBean> al = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * from ORDERLINE WHERE OrderId =?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, Orderid);
            al = new ArrayList<ProductBean>();
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                ProductBean pb = new ProductBean();
                pb=QueryProductByID(rs.getString("ProductId"));
                pb.setQty(rs.getInt("OrderQty"));
                al.add(pb);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return al;
    }
    public void cancelOrder(String orderId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE ORDERLINE SET STATUS=? WHERE OrderId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, "cancelled");
            pStmnt.setString(2, orderId);
            int rowCount = pStmnt.executeUpdate();
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void orderstatus(String orderId,String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE ORDERLINE SET STATUS=? WHERE OrderId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, status);
            pStmnt.setString(2, orderId);
            pStmnt.executeUpdate();
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void CompleteOrder(String orderId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        PreparedStatement getTotal = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE ORDERLINE SET STATUS=? WHERE OrderId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, "Completed");
            pStmnt.setString(2, orderId);
            pStmnt.executeUpdate();
            preQueryStatement = "SELECT * from ORDERLINE WHERE OrderId=?";
            getTotal = cnnct.prepareStatement(preQueryStatement);
            getTotal.setString(1, orderId);
            ResultSet rs2 = null;
            rs2 = getTotal.executeQuery();
            String userid= rs2.getString("UserId");
            int sum = 0;
            while (rs2.next()) {
                if (!QueryProductByID(rs2.getString("ProductId")).isIsGift()) {
                    double price = QueryProductByID(rs2.getString("ProductId")).getPrice();
                    sum = sum + ((int) price * Integer.parseInt(rs2.getString("OrderQty")));
                }
            }
            preQueryStatement = "UPDATE UserInfo SET point=point+? WHERE UserId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, (int) (sum / 10));
            pStmnt.setString(2, userid);
            pStmnt.executeUpdate();
            
            preQueryStatement = "UPDATE UserInfo SET amount=amount+? WHERE UserId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, (int) sum);
            pStmnt.setString(2, userid);
            pStmnt.executeUpdate();

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public ArrayList SearchProductByBrand(String name) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList al = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "select * from PRODUCT WHERE Brand=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, name);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            al = new ArrayList();
            while (rs.next()) {
                ProductBean Pb = new ProductBean();
                Pb.setProductID(rs.getString("ProductId"));
                Pb.setProductCat(rs.getString("ProductCat"));
                Pb.setProductName(rs.getString("ProductName"));
                Pb.setProductBrand(rs.getString("Brand"));
                Pb.setDescriptions(rs.getString("Descriptions"));
                Pb.setPrice(rs.getDouble("Price"));
                Pb.setPhotourl(rs.getString("PhotoUrl"));
                al.add(Pb);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return al;
    }
    public void deleteProduct(String productId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM PRODUCT WHERE ProductId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, productId);
            pStmnt.executeUpdate();
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
