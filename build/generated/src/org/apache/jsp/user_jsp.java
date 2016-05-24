package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import ict.bean.UserInfo;

public final class user_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>User Page</title>\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MDL.jsp", out, false);
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div class=\"mdl-layout mdl-js-layout mdl-layout--fixed-header\">\n");
      out.write("            ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
      out.write("\n");
      out.write("            <main class=\"mdl-layout__content\">\n");
      out.write("\n");
      out.write("                <div class=\"mdl-tabs mdl-js-tabs mdl-js-ripple-effect\">\n");
      out.write("                    <div class=\"mdl-tabs__tab-bar\">\n");
      out.write("                        <a href=\"#starks-panel\" class=\"mdl-tabs__tab is-active\">Personal</a>\n");
      out.write("                        <a href=\"#lannisters-panel\" class=\"mdl-tabs__tab\">Order History</a>\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("                    <div class=\"mdl-tabs__panel is-active\" id=\"starks-panel\">\n");
      out.write("                        ");

                            out.println("test");
                            UserInfo customer=null;
                            try{
                                out.println("test2");
                                customer = (UserInfo) request.getAttribute("userInfo");
                                out.println("test3");
                                out.println("<h1>Hello " + customer.getName()+"</h1>");
                                out.println("test4");
                                out.println(customer.getAddress());
                            }catch(Exception e){
                            }
                        
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"mdl-tabs__panel\" id=\"lannisters-panel\">\n");
      out.write("                        here will list 10 order history \n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("            </main>\n");
      out.write("            ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "footer.jsp", out, false);
      out.write("\n");
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
