<%@page import="java.util.ArrayList,ict.bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title><jsp:include page="MDL.jsp" />
        <script type="text/javascript">  
          $(document).ready(function(){
                  var totalepoint=$("input[name='totalepoint']");
                  var totalprice=$("input[name='totalprice']");
                  var userid=$("input[name='userId']").val();
                      $(".status").html("<font color=gray> Checking availability...</font>");  
                       $.ajax({  
                          type: "POST",  
                          url: "main",  
                          data: "action=checkmoney&table=userinfo&f=amount&f2=point&userId="+userid+"&where="+ totalprice.val() +"&where2="+totalepoint.val(),  
                          success: function(msg){  
                              $(".status").ajaxComplete(function(event, request, settings){
                                  $(".status").html(msg);
                              });  
                          }  
                      });
          });  
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp">
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Product Category</th>
                    <th>Price/Point</th>
                    <th>Quantity</th>
                    <th >Action</th>
                </tr>
            </thead>
            <tb>
                <%
                    ArrayList<ProductBean> cart = new ArrayList<ProductBean>();
                    ArrayList<CategoryBean> cat = new ArrayList<CategoryBean>();
                    double totalprice = 0.0;
                    int totalepoint = 0;
                    int totalqty = 0;
                    try {
                        cart = (ArrayList) session.getAttribute("Cart");
                        cat = (ArrayList) session.getAttribute("cat");
                        
                        for (int index = 0; index < cart.size(); index++) {
                            String pid=cart.get(index).getProductCat();
                            out.println("<tr><td><a href='product?list=detail&productId="+cart.get(index).getProductID()+"'>" + cart.get(index).getProductName() + "</a></td>"
                                    + "<td> <a href='product?list=cat&productCat="+cart.get(index).getProductCat()+"'>" + cat.get(Integer.parseInt(pid)-1).getCatname() + "</a></td>");
                            String Price = "";
                            if (cart.get(index).isIsGift()) {
                                totalepoint += cart.get(index).getPrice()* cart.get(index).getQty();
                                Price = cart.get(index).getPrice() * cart.get(index).getQty() + "P (" + cart.get(index).getPrice() + ") ";
                            } else if (!cart.get(index).isIsGift()) {
                                Price = "HK$ " + cart.get(index).getPrice() * cart.get(index).getQty() + " (" + cart.get(index).getPrice() + ")";
                                totalprice += cart.get(index).getPrice() * cart.get(index).getQty();
                            }
                            out.println("<td>" + Price + "</td>");
                            out.println("<td><form id='" + cart.get(index).getProductID() + "' method=\"POST\" action=\"cart\"><input class=\"mdl-textfield__input\" type=\"number\" name='qty' id=\"qty\" onchange='this.form.submit()' value='" + cart.get(index).getQty() + "'></td>");
                            out.println("<input type=\"hidden\" name=\"action\" value=\"update\"><input type=\"hidden\" name=\"ProductId\" value=\"" + cart.get(index).getProductID() + "\"></form>"
                                    + "<td><form method=\"POST\" action=\"cart\"><input type=\"hidden\" name=\"action\" value=\"delete\"><input type=\"hidden\" name=\"ProductId\" value=\"" + cart.get(index).getProductID() + "\"><button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Delete</button></form></td></tr>");
                            totalqty += cart.get(index).getQty();
                            //out.print(cart.size()+" index "+index);
                        }
                      
                %>
            </tb><tfoot>
                <%      UserInfo user = (UserInfo) session.getAttribute("userInfo");
                        session.setAttribute("checkout", cart);
                        out.println("<tr>");
                        out.println("<td><b>Total</b></td><td></td><td>HK$" + totalprice + "<br>" + totalepoint + " P</td><td>" + totalqty + "</td><td>");
                        out.println("<form method=\"POST\" action=\"orderConfirmation.jsp\"><input type=\"hidden\" name=\"userId\" value=\""+user.getId()+"\">"
                                + "<input type='hidden' name='totalprice' value='"+totalprice+"'><input type='hidden' name='totalepoint' value='"+totalepoint+"'><select id='cstatus' name='status'><option value='delivery'>delivery</option><option value='pickup'>pick-up</option></select><span class=\"status\"></span></form></td></tr>");
            
                    } catch (Exception e) {
                        response.sendRedirect("product");
                    }
                %>
            </tfoot>
        </table>
    </body>
</html>
