/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.*;
import ict.db.DB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Felix
 */
@WebServlet(name = "ShoppingCart", urlPatterns = {"/cart"})
public class ShoppingCart extends HttpServlet {

    DB db;

    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new DB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("userInfo") == null) {
            response.sendRedirect("login.jsp");
        } else if (request.getParameter("action") != null) {
            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                String ProductId = request.getParameter("ProductId");
                if (ProductId != null) {
                    ArrayList<ProductBean> al = (ArrayList) session.getAttribute("Cart");
                    for (int i = 0; i < al.size(); i++) {
                        if (al.get(i).getProductID().equals(ProductId)) {
                            al.remove(i);
                        }
                    }
                    session.setAttribute("Cart", al);
                    response.sendRedirect("cart.jsp");
                } else {
                    PrintWriter out = response.getWriter();
                    out.print("Empty Cart");
                    response.sendRedirect("product");
                }
            } else if ("update".equals(action)) {
                String ProductId = request.getParameter("ProductId");

                if (ProductId != null) {
                    int qty = Integer.parseInt(request.getParameter("qty"));
                    ProductBean b = db.QueryProductByID(ProductId);
                    b.setQty(qty);
                    ArrayList<ProductBean> al = (ArrayList) session.getAttribute("Cart");
                    for (int i = 0; i < al.size(); i++) {
                        if (al.get(i).getProductID().equals(ProductId)) {
                            al.remove(i);
                        }
                    }
                    al.add(b);
                    session.setAttribute("Cart", al);
                    response.sendRedirect("cart.jsp");
                } else {
                    response.sendRedirect("product");
                }
            } else if ("add".equals(action)) {
                String ProductId = request.getParameter("ProductId");
                int qty = Integer.parseInt(request.getParameter("qty"));
                ArrayList<ProductBean> cart;
                if (session.getAttribute("Cart") == null) {
                    cart = new ArrayList<ProductBean>();
                } else {
                    cart = (ArrayList) session.getAttribute("Cart");
                }
                for (int i = 0; i < cart.size(); i++) {
                    if (cart.get(i).getProductID().equals(ProductId)) {
                        qty += cart.get(i).getQty();
                        cart.remove(i);
                    }
                }
                ProductBean pb = db.QueryProductByID(ProductId);
                pb.setQty(qty);
                cart.add(pb);
                session.setAttribute("Cart", cart);
                String targetURL = "cart.jsp";
                response.sendRedirect(targetURL);
            } else if ("cancelorder".equals(action)) {
                String orderid = request.getParameter("orderId");
                db.cancelOrder(orderid);
                UserInfo userinfo = (UserInfo) session.getAttribute("userInfo");
                session.setAttribute("order", db.showOrder(userinfo.getId()));
                response.sendRedirect("user.jsp");
            } else if ("changestatus".equals(action)) {
                String status = request.getParameter("status");
                String orderId = request.getParameter("orderId");
                db.orderstatus(orderId,status);
                session.setAttribute("order", db.showallOrder());
                response.sendRedirect("admin.jsp");
            }else if("complete".equals(action)){
                String orderId = request.getParameter("orderId");
                db.CompleteOrder(orderId);
                session.setAttribute("order", db.showallOrder());
                response.sendRedirect("admin.jsp");
            }else if ("checkout".equals(action)) {
                ArrayList<ProductBean> cart = (ArrayList) session.getAttribute("Cart");
                String userId = request.getParameter("userId");
                String status = request.getParameter("status");
                CartBean cb = new CartBean();
                cb.setAl(cart);
                cb.setUserid(userId);
                cb.setStatus(status);
                db.checkout(cb);
                session.removeAttribute("Cart");
                session.setAttribute("order", db.showOrder(userId));
                response.sendRedirect("user.jsp");
            }
        }

//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ShoppingCart</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ShoppingCart at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
