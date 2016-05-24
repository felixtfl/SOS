package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import ict.bean.ProductBean;

public final class listProduct_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Product Page</title>");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MDL.jsp", out, false);
      out.write("\n");
      out.write("        <style>\n");
      out.write("            .mdl-card__media{\n");
      out.write("                background-color: #FFF !important;\n");
      out.write("                height:248px\n");
      out.write("            }\n");
      out.write("            .mdl-card__media img{\n");
      out.write("                height:auto;\n");
      out.write("                max-width:300px;\n");
      out.write("                //max-height: 248px;\n");
      out.write("                //_width:expression(this.width > 300 ? \"300px\" : this.width);\n");
      out.write("            }.mdl-card__actions input{\n");
      out.write("                max-width: 88px;\n");
      out.write("            }\n");
      out.write("            .mdl-card__supporting-text {\n");
      out.write("                height: 55px;\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div class=\"mdl-layout mdl-js-layout mdl-layout--fixed-header\">\n");
      out.write("            ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
      out.write("\n");
      out.write("            <div class=\"android-more-section\">\n");
      out.write("                <div class=\"mdl-grid\">\n");
      out.write("                    ");

                        ArrayList<ProductBean> product = (ArrayList) session.getAttribute("Product");
                        for (int i = 0; i < product.size(); i++) {
                            String Price=""+product.get(i).getPrice();
                            if(product.get(i).isIsGift())
                                Price="Point : "+product.get(i).getPrice()+" P";
                            else{
                                Price="Price : HK$"+product.get(i).getPrice();
                            }
                            out.println("<div class=\"mdl-cell mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-card mdl-shadow--3dp\">\n"
                                    + "            <div class=\"mdl-card__media\">\n"
                                    + "             <img src=\"images/product/" + product.get(i).getPhotourl() + "\">"
                                    + "            </div>\n"
                                    + "            <div class=\"mdl-card__title\">\n"
                                    + "               <h4 class=\"mdl-card__title-text\"><a href='product?list=detail&productId="+product.get(i).getProductID()+"'>" + product.get(i).getProductName() + "</a></h4>"
                                    + "            </div>\n"
                                    + "            <div class=\"mdl-card__supporting-text\">"
                                    + "              <span class=\"mdl-typography--font-light mdl-typography--subhead\">Descriptions : " + product.get(i).getDescriptions() + "<br >"+Price+ "</span>\n"
                                    + "            </div>\n"
                                    + "            <div class=\"mdl-card__actions\"><form action='cart' method='POST'>"
                                    + "<input type='hidden' name='ProductId' value='" + product.get(i).getProductID() + "'>"
                                    + "<input type='number' name='qty' value='1' ><input type='hidden' name='action' value='add' >"
                                    + "               <button class=\"mdl-button mdl-js-button mdl-typography--text-uppercase\" type='submit'>"
                                    + "                 <i class=\"material-icons\">add_shopping_cart</i>Add to cart</button></form>"
                                    + "            </div>"
                                    + "          </div>");
                        }
                    
      out.write("\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
