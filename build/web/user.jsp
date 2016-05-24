<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.*;"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>User Page</title>
        <jsp:include page="MDL.jsp"/>
        <script>
            $(document).ready(function () {
                $("#password2").keyup(validate);
                
            });
            validate = function () {
                var password1 = $("#password1").val();
                var password2 = $("#password2").val();

                if (password1 == password2) {
                    $("#validate-status").text("valid");
                }
                else {
                    $("#validate-status").text("invalid");
                }
            }
            function cancel(){
               var c=confirm("Cancel will cost some fee");
               if(c){
                   $("#cancel").submit();
              }
           }
        </script>
        <%
            UserInfo user = new UserInfo();
            try {
                user = (UserInfo) session.getAttribute("userInfo");
            } catch (Exception e) {
                response.sendRedirect("login.jsp");
            }
        %>
    </head>
    <body>
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="header.jsp"/>
            <main class="mdl-layout__content">

                <div class="mdl-tabs mdl-js-tabs mdl-js-ripple-effect">
                    <div class="mdl-tabs__tab-bar">
                        <a href="#starks-panel" class="mdl-tabs__tab is-active">Personal</a>
                        <a href="#lannisters-panel" class="mdl-tabs__tab">Order History</a>

                    </div>
                    <div class="mdl-tabs__panel is-active" id="starks-panel">
                        <%
                            if (user != null) {
                                try {
                                    String action = request.getParameter("action");
                                    if (action.equals("update")) {
                                        out.print("<h4>Update</h4><form method=\"POST\" action=\"main\">"
                                                + "<input type=\"hidden\" name=\"action\" value=\"update\"/><input type=\"hidden\" name=\"uid\" value=\"" + user.getId() + "\"/><input type=\"hidden\" name=\"username\" value=\"" + user.getUsername() + "\"/><input type=\"hidden\" name=\"password\" value=\"" + user.getPassword() + "\"/>"
                                                + "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input class=\"mdl-textfield__input\" type=\"text\" id=\"name\" name=\"name\" value=\"" + user.getName() + "\" required><label class=\"mdl-textfield__label\" for=\"name\">Name</label></div><br>"
                                                + "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input class=\"mdl-textfield__input\" type=\"text\" id=\"phone\" name=\"phone\" value=\"" + user.getPhone() + "\"><label class=\"mdl-textfield__label\" for=\"phone\">Phone No</label></div><br/>"
                                                + "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><textarea class=\"mdl-textfield__input\" rows= \"3\" id=\"address\" name=\"address\" required>" + user.getAddress() + "</textarea><label class=\"mdl-textfield__label\" for=\"address\">Address</label></div><br/>");
                                        out.println("<button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Update</button></form>");
                                    } else if (action.equals("changepwd")) {
                                        out.println("<h4>Change Password</h4><form method=\"POST\" action=\"main\">"
                                                + "<input type=\"hidden\" name=\"action\" value=\"changepwd\"/><input type=\"hidden\" name=\"uid\" value=\"" + user.getId() + "\"/>"
                                                + "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input class=\"mdl-textfield__input\" type=\"password\" id=\"password\" name=\"password\"  required><label class=\"mdl-textfield__label\" for=\"password\">Old Password</label></div><br>"
                                                + "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input class=\"mdl-textfield__input\" type=\"password\" id=\"password1\" name=\"password1\"  required><label class=\"mdl-textfield__label\" for=\"password1\">New Password</label></div><br>"
                                                + "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input class=\"mdl-textfield__input\" type=\"password\" id=\"password2\" name=\"password2\"  required><label class=\"mdl-textfield__label\" for=\"password2\">input again</label></div><br>"
                                                + "<p id=\"validate-status\"></p>"
                                                + "<button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Change</button></form>");
                                    }
                                } catch (Exception e) {
                                    out.println("<h4>Hello " + user.getName() + "</h4>");
                                    out.println("<br/>Phone : " + user.getPhone());
                                    out.println("<br/>Amount : " + user.getAmount());
                                    out.println("<br/>Pont : " + user.getPoint());
                                    out.println("<br/>Address : " + user.getAddress());
                                    out.println("<form method=\"GET\" action=\"user.jsp\">"
                                            + "<input type=\"hidden\" name=\"action\" value=\"update\">"
                                            + "<button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Update</button>"
                                            + "</form>");
                                    out.println("<form method=\"GET\" action=\"user.jsp\">"
                                            + "<input type=\"hidden\" name=\"action\" value=\"changepwd\">"
                                            + "<button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Change Password</button>"
                                            + "</form>");
                                }
                            } else {
                                response.sendRedirect("login.jsp");
                            }

                        %>
                    </div>

                    <div class="mdl-tabs__panel" id="lannisters-panel">
                        <table class="mdl-data-table mdl-js-data-table">
                            <thead>
                                <tr>
                                    <th class="mdl-data-table__cell--non-numeric">Order Id</th>
                                    <th>Total Price/Point</th>
                                    <th>Order Date</th>
                                    <th>Status</th>
                                    <th colspan="2" >Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                ArrayList<OrderBean> order=new ArrayList<OrderBean>();
                                try{
                                    order=(ArrayList) session.getAttribute("order");
                                    
                                    for(int index=0;index<order.size();index++){
                                        
                                        out.println("<tr><td class=\"mdl-data-table__cell--non-numeric\">"+order.get(index).getOrderId()+"</td>");
                                        out.println("<td> HK$ "+order.get(index).getTotal()+"<br>"+order.get(index).getPointTotal()+" P</td>");
                                        out.println("<td>"+order.get(index).getOrderDate()+"</td>");
                                            out.println("<td>"+order.get(index).getStatus()+"</td>");
                                         out.println("<td><form action='main' ><input type=\"hidden\" name=\"action\" value=\"orderdetail\"><input type=\"hidden\" name=\"orderId\" value='"+order.get(index).getOrderId()+"' ><button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Detail</button></form></td>");
                                        %>
                                        
                                        <ict:countdate  orderDate='<%= order.get(index).getOrderDate() %>' orderid='<%= order.get(index).getOrderId() %>'/>
                                        
                                        <%
                                       out.print("</tr>");
                                       if(index==10){
                                           index=order.size();
                                           break;
                                       }
                                    }
                                }catch (Exception e){
                                    
                                }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </main>
            <jsp:include page="footer.jsp"/>
        </div>
    </body>
</html>
