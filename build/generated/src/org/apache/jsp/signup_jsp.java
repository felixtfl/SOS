package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class signup_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("        <title>Sign up</title>\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MDL.jsp", out, false);
      out.write("    \n");
      out.write("        <script type=\"text/javascript\">  \n");
      out.write("          $(document).ready(function(){  \n");
      out.write("              $(\".uname\").change(function(){  \n");
      out.write("                  var uname = $(this).val();  \n");
      out.write("                  if(uname.length >= 3){\n");
      out.write("                      $(\".status\").html(\"<font color=gray> Checking availability...</font>\");  \n");
      out.write("                       $.ajax({  \n");
      out.write("                          type: \"POST\",  \n");
      out.write("                          url: \"main\",  \n");
      out.write("                          data: \"action=check&table=userinfo&where=\"+ uname,  \n");
      out.write("                          success: function(msg){  \n");
      out.write("  \n");
      out.write("                              $(\".status\").ajaxComplete(function(event, request, settings){  \n");
      out.write("                                  $(\".status\").html(msg);\n");
      out.write("                              });  \n");
      out.write("                          }  \n");
      out.write("                      });   \n");
      out.write("                  }  \n");
      out.write("                  else{  \n");
      out.write("                         \n");
      out.write("                      $(\".status\").html(\"<font color=red>Username should be <b>3</b> character long.</font>\");  \n");
      out.write("                  }  \n");
      out.write("                    \n");
      out.write("              });  \n");
      out.write("          });  \n");
      out.write("        </script>   \n");
      out.write("       <style>\n");
      out.write("            .c{\n");
      out.write("                width: 356px;\n");
      out.write("                padding: 10px;\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("        ");

        if(session.getAttribute("userInfo")!=null){
           response.sendRedirect("index.jsp");
        }
        
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body><div class=\"mdl-layout mdl-js-layout mdl-layout--fixed-header\">\n");
      out.write("        \n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
      out.write("\n");
      out.write("            <div class=\"page-content\">\n");
      out.write("        <div class=\"c mdl-grid mdl-card mdl-shadow--2dp\">\n");
      out.write("            <div class=\"c mdl-cell mdl-cell--10-col\">\n");
      out.write("                <form method=\"POST\" action=\"main\">\n");
      out.write("                    <input type=\"hidden\" name=\"action\" value=\"reg\"/>\n");
      out.write("                    \n");
      out.write("                    <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\">\n");
      out.write("                        <input class=\"mdl-textfield__input\" type=\"text\" id=\"name\" name=\"name\" required>\n");
      out.write("                        <label class=\"mdl-textfield__label\" for=\"name\">Name</label>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\">\n");
      out.write("                        <input class=\"uname mdl-textfield__input\" type=\"text\" id=\"username\" name=\"username\" required>\n");
      out.write("                        <label class=\"mdl-textfield__label\" for=\"username\">Login name</label>                     \n");
      out.write("                    </div>\n");
      out.write("                   <span class=\"status\"></span>\n");
      out.write("                   \n");
      out.write("                    <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\">\n");
      out.write("                        <input class=\"mdl-textfield__input\" type=\"password\" id=\"password\" name=\"password\" required>\n");
      out.write("                        <label class=\"mdl-textfield__label\" for=\"password\">Password</label>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\">\n");
      out.write("                        <input class=\"mdl-textfield__input\" type=\"text\" pattern=\"-?[0-9]*(\\.[0-9]+)?\" id=\"phone\" name=\"phone\">\n");
      out.write("                        <label class=\"mdl-textfield__label\" for=\"phone\">Phone No</label>\n");
      out.write("                        <span class=\"mdl-textfield__error\">Input is not a number!</span>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\">\n");
      out.write("                        <textarea class=\"mdl-textfield__input\" rows= \"3\" id=\"address\" name=\"address\" required></textarea>\n");
      out.write("                        <label class=\"mdl-textfield__label\" for=\"address\">Address</label>\n");
      out.write("                    </div>\n");
      out.write("                    \n");
      out.write("                    <button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >\n");
      out.write("                        Submit\n");
      out.write("                    </button>\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("        \n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "footer.jsp", out, false);
      out.write("\n");
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
