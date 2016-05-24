package ict.servlet;

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

@WebServlet(name = "ProductController", urlPatterns = {"/product"})
public class ProductController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    DB db;

    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new DB(dbUrl, dbUser, dbPassword);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String targetURL = "listProduct.jsp";
        try {
            HttpSession session = request.getSession(true);
            ArrayList<ProductBean> product = db.queryProduct();
            session.setAttribute("Product", product);
            if (product.isEmpty()) {
                PrintWriter out = response.getWriter();
                out.println("No Product");
            } else if (request.getParameter("list") != null) {
                String list = request.getParameter("list");
                switch (list) {
                    case "gift":
                        ArrayList<ProductBean> gift = db.SearchProductByCat("5");
                        session.setAttribute("Product", gift);
                        ArrayList<CategoryBean> al = new ArrayList();
                        al=db.queryCat();
                        session.setAttribute("cat", al);
                        break;
                    case "product":
                        product = db.queryProductonly();
                        session.setAttribute("Product", product);
                        al=db.queryCat();
                        session.setAttribute("cat", al);
                        break;
                    case "key":
                        String keyword = request.getParameter("keyword");
                        ArrayList<ProductBean> key = db.SearchProductByName(keyword);
                        al=db.queryCat();
                        session.setAttribute("cat", al);
                        session.setAttribute("Product", key);
                        break;
                    case "detail":
                        String productId = request.getParameter("productId");
                        ProductBean pb = new ProductBean();
                        pb = db.QueryProductByID(productId);
                        product = new ArrayList();
                        product.add(pb);
                        session.setAttribute("p", product);
                        al=db.queryCat();
                        session.setAttribute("cat", al);
                        targetURL = "detailProduct.jsp";
                        break;
                    case "cat":
                        String productCat = request.getParameter("productCat");
                        ArrayList<ProductBean> cat = db.SearchProductByCat(productCat);
                        al=db.queryCat();
                        session.setAttribute("cat", al);
                        session.setAttribute("Product", cat);
                        break;
                    case "deleteproduct":
                        String productid=request.getParameter("Productid");
                        db.deleteProduct(productid);
                        session.setAttribute("Products", db.queryProduct());
                        response.sendRedirect("admin.jsp");
                        break;
                    default:
                        product = db.queryProduct();
                        al=db.queryCat();
                        session.setAttribute("cat", al);
                        session.setAttribute("Product", product);
                }
            }
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/" + targetURL);
            rd.forward(request, response);
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println(e);
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
