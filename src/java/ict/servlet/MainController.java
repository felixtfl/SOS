/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import java.sql.*;
import ict.bean.*;
import ict.db.DB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MainController", urlPatterns = {"/main"})
public class MainController extends HttpServlet {

    DB db;

    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new DB(dbUrl, dbUser, dbPassword);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String action = request.getParameter("action");
        try {
            if ("reg".equals(action)) {
                doReg(request, response);
            } //            else if (!isAuthenticated(request)
            //                    && !("authenticate".equals(action))) {
            //                doLogin(request, response);
            //                return;
            //            }
            else if ("authenticate".equals(action)) {
                doAuthenticate(request, response);
            } else if ("logout".equals(action)) {
                doLogout(request, response);
            } else if ("check".equals(action)) {
                doCheck(request, response);
            } else if ("update".equals(action)) {
                doUpdate(request, response);
            } else if ("changepwd".equals(action)) {
                doChangepwd(request, response);
            } else if ("adminupdate".equals(action)) {
                doAdminUpdate(request, response);
            } else if ("updatecat".equals(action)) {
                doUpdateCat(request, response);
            } else if ("addcat".equals(action)) {
                doAddCat(request, response);
            } else if("productupdate".equals(action)){
                doProductUpdate(request,response);
            }else if("checkmoney".equals(action)){
                doCheckmoney(request,response);
            }else if("orderdetail".equals(action)){
                doOrderDetail(request,response);
            }else {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
            }
        } catch (IllegalStateException e) {

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public boolean isAuthenticated(HttpServletRequest request) {
        boolean result = false;
        HttpSession session = request.getSession();
        if (session.getAttribute("userInfo") != null) {
            result = true;
        }
        return result;
    }

    public void doReg(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        int type = 0;
        db.addAccount(name, username, phone, address, password, type);
        response.sendRedirect("index.jsp");
    }

    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String targetURL = "login.jsp";
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    public void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("userInfo");
            session.invalidate();
        }
        doLogin(request, response);
    }

    public void doAuthenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String targetURL;
        if (db.isValidUser(username, password)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = db.getUserdetail(username, password);
            session.setAttribute("userInfo", bean);
            ArrayList<OrderBean> order=new ArrayList<OrderBean>();
            order=db.showOrder(bean.getId());
            session.setAttribute("order", order);
            targetURL = "/index.jsp";
            if (bean.getType().equals("Manager")) {
                doQueryUser(request, response);
                getCategory(request, response);
                session.setAttribute("order", db.showallOrder());
                targetURL = "/admin.jsp";
            }
        } else {
            targetURL = "/signup.jsp";
        }
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }
    private void doCheckmoney(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            double where =Double.parseDouble(request.getParameter("where"));
            double where2 = Double.parseDouble(request.getParameter("where2"));
            String table= request.getParameter("table");
            String f=request.getParameter("f");
            String f2=request.getParameter("f2");
            String userId=request.getParameter("userId");
            Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement("select "+f+","+f2+" from "+table+" where userId=?");
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            String button="";
            if (rs.next()) {
                double ff=rs.getDouble(f);
                double ff2=rs.getDouble(f2);
                if(where>ff){
                    button="Your amount is not enought";
                }else if(where2>ff2){
                    button="Your point is not enought";
                }else{
                    button="<button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Chackout</button>";
                }
                out.println(button);
            } else {
                out.println("Error...");
            }
            
            out.println();

        } catch (SQLException ex) {
            out.println(ex);
            out.println("Error ->" + ex.getMessage());
        } finally {
            out.close();
        }
    }
    private void doCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String where = request.getParameter("where");
            String table= request.getParameter("table");
            String f=request.getParameter("f");
            Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement("select "+f+" from "+table+" where "+f+"=?");
            ps.setString(1, where);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                out.println("<font color=\"green\"><b>" + where + "</b> is avaliable</font>");
            } else {
                out.println("<font color=\"red\"><b>" + where + "</b> is already in use</font>");
            }
            
            out.println();

        } catch (Exception ex) {
            out.println("Error ->" + ex.getMessage());
        } finally {
            out.close();
        }
    }

    private void doUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("uid");
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        UserInfo u = new UserInfo();
        u.setId(id);
        u.setName(name);
        u.setAddress(address);
        u.setPhone(phone);
        String targetURL = "index.jsp";
        if (db.updateInfo(u)) {
            HttpSession session = request.getSession(true);
            UserInfo bean = db.getUserdetail(username, password);
            session.setAttribute("userInfo", bean);
            targetURL = "user.jsp";
        }
        response.sendRedirect(targetURL);
    }

    private void doChangepwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("uid");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        if (db.changePwd(id, password, password1)) {
            HttpSession session = request.getSession(true);
            session.removeAttribute("userInfo");
            response.sendRedirect("login.jsp");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("you inputed wrong password");
        }
    }

    private void doQueryUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        ArrayList al = db.queryCust();
        session.setAttribute("users", al);
    }
    private void doQueryProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        ArrayList al = db.queryProduct();
        session.setAttribute("Products", al);
    }

    public void getCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        ArrayList<CategoryBean> al = db.queryCat();
        if(!al.isEmpty()){
            doQueryProduct(request,response);
        }
        session.setAttribute("cat", al);
    }

    private void doAdminUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("uid");
        int type = Integer.parseInt(request.getParameter("type"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        UserInfo user = new UserInfo();
        user.setAmount(amount);
        user.setType(type);
        user.setId(id);
        if (db.adminupdate(user)) {
            HttpSession session = request.getSession(true);
            session.removeAttribute("users");
            doQueryUser(request, response);
            response.sendRedirect("admin.jsp");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("Error");
        }
    }

    private void doUpdateCat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String catName = request.getParameter("CategoryName");
        String catid=request.getParameter("CategoryId");
        CategoryBean cb=new CategoryBean();
        cb.setCatid(catid);
        cb.setCatname(catName);
        if (db.updateCat(cb)) {
            HttpSession session = request.getSession(true);
            session.removeAttribute("cat");
            getCategory(request, response);
            response.sendRedirect("admin.jsp#Category-panel");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("Error");
        }

    }
    private void doAddCat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String catName = request.getParameter("CategoryName");
        CategoryBean cb=new CategoryBean();
        cb.setCatname(catName);
        if (db.addCat(cb)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("cat", db.queryCat());
            response.sendRedirect("admin.jsp#Category-panel");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("Error");
        }
    }

    private void doProductUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ProductName= request.getParameter("ProductName");
        int ProductCat = Integer.parseInt(request.getParameter("ProductCat"));
        String Brand =request.getParameter("Brand");
        String Descriptions=request.getParameter("Descriptions");
        double proce =Double.parseDouble(request.getParameter("Price"));
        String ProductId=request.getParameter("Productid");
        String photoname=ProductId+".jpg";
        ProductBean pb=new ProductBean();
        if(request.getParameter("isGift")!=null){
                 pb.setIsGift(true);
        }
        pb.setDescriptions(Descriptions);
        pb.setPhotourl(photoname);
        pb.setPrice(proce);
        pb.setProductBrand(Brand);
        pb.setProductCat(ProductCat+"");
        pb.setProductID(ProductId);
        pb.setProductName(ProductName);
        
        if(db.updateProduct(pb)){
            HttpSession session = request.getSession(true);
            session.setAttribute("Products", db.queryProduct());
            response.sendRedirect("admin.jsp");
        }else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("Error");
        }
    }

    private void doOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
       String orderid=request.getParameter("orderId");
        ArrayList<ProductBean> al=db.showOrderDetail(orderid);
        HttpSession session = request.getSession(true);
        session.setAttribute("Cart", al);
        response.sendRedirect("detailOrder.jsp");
    }
}
