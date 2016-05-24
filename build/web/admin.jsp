<%@page import="ict.bean.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <jsp:include page="MDL.jsp"/>
        <%
            UserInfo user = new UserInfo();
            try {
                user = (UserInfo) session.getAttribute("userInfo");
                if (!user.getType().equals("Manager")) {
                    response.sendRedirect("index.jsp");
                }
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
                        <a href="#Admin-panel" class="mdl-tabs__tab">Admin panel</a>
                        <a href="#Product-panel" class="mdl-tabs__tab">Product maintain</a>
                        <a href="#Category-panel" class="mdl-tabs__tab">Category maintain</a>
                        <a href="#Order-panel" class="mdl-tabs__tab">Order maintain</a>
                    </div>
                    <div class="mdl-tabs__panel is-active" id="Admin-panel">
                        <table class="mdl-data-table mdl-js-data-table">
                            <thead>
                                <tr>
                                    <th class="mdl-data-table__cell--non-numeric">Name</th>
                                    <th>Phone</th>
                                    <th>Address</th>
                                    <th>Username</th>
                                    <th>Password</th>
                                    <th>Verifies</th>
                                    <th>Amount</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<UserInfo> al = (ArrayList) session.getAttribute("users");
                                    for (int i = 0; i < al.size(); i++) {
                                        out.println("<form method=\"POST\"  action=\"main\">");
                                        out.println("<tr><td class=\"mdl-data-table__cell--non-numeric\">" + al.get(i).getName() + "</td><td>" + al.get(i).getPhone() + "</td><td>" + al.get(i).getAddress() + "</td>");
                                        out.println("<td>" + al.get(i).getUsername() + "</td><td>" + al.get(i).getPassword() + "</td>"
                                                + "<td><select name='type' id='type'><option value=\"" + al.get(i).getIntType() + "\" selected>" + al.get(i).getType() + "</option><option value=\"0\">Disable</option><option value=\"1\">Normal</option><option value=\"2\">Manager</option></td>"
                                                + "<td><input class=\"mdl-textfield__input\" type=\"number\" name='amount' id=\"amount\" value='" + al.get(i).getAmount() + "'></td>"
                                                + "<td><input type=\"hidden\" name=\"action\" value=\"adminupdate\"><input type=\"hidden\" name=\"uid\" value=\"" + al.get(i).getId() + "\"><button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Update</button></td></tr></form>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                    <div class="mdl-tabs__panel" id="Product-panel">
                        <table class="mdl-data-table mdl-js-data-table">
                            <thead>
                                <tr>
                                    <th class="mdl-data-table__cell--non-numeric">Product Name</th>
                                    <th>Product Category</th>
                                    <th>Brand</th>
                                    <th>Descriptions</th>
                                    <th>Price</th>
                                    <th>IsGift</th>
                                    <th>Product Image</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                        <%
                         ArrayList<ProductBean> products=new ArrayList<ProductBean>();
                         ArrayList<CategoryBean> cat=new ArrayList<CategoryBean>();
                         try{
                             products=(ArrayList) session.getAttribute("Products");
                             cat = (ArrayList) session.getAttribute("cat");
                             for(int index=0;index<products.size();index++){
                                 out.println("<form method=\"POST\"  action=\"main\">"
                                         + "<tr><td class=\"mdl-data-table__cell--non-numeric\"><input class=\"mdl-textfield__input\" onchange='this.form.submit()' type=\"text\" name='ProductName' id=\"ProductName\" value='" + products.get(index).getProductName() + "'></td>"
                                         + "<td><select onchange='this.form.submit()' name='ProductCat'><option value='"+products.get(index).getProductCat()+"'>"+cat.get(Integer.parseInt(products.get(index).getProductCat())-1).getCatname()+"</option>");
                                 for(int v=0;v<cat.size();v++){
                                     out.println("<option value='"+cat.get(v).getCatid()+"'>"+cat.get(v).getCatname()+"</option>");
                                 }
                                 boolean tureorfalse=products.get(index).isIsGift();
                                 String checked="";

                                 if(tureorfalse)
                                     checked="checked";
                                 out.println("</td><td><input class=\"mdl-textfield__input\" type=\"text\" onchange='this.form.submit()' name='Brand' id=\"Brand\" value='" +products.get(index).getProductBrand()+ "'></td>"
                                         + "<td><input onchange='this.form.submit()' class=\"mdl-textfield__input\" type=\"text\" name='Descriptions' id=\"Descriptions\" value='" +products.get(index).getDescriptions()+ "'></td>"
                                         + "<td><input class=\"mdl-textfield__input\" type=\"text\" onchange='this.form.submit()' name='Price' id=\"Price\" value='" +products.get(index).getPrice()+ "'></td><td><label class=\"mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect\" for=\"isGift"+products.get(index).getProductID()+"\"><input onchange='this.form.submit()' type=\"checkbox\"  name='isGift' id=\"isGift"+products.get(index).getProductID()+"\" class=\"mdl-checkbox__input\" "+checked+" ></label></td>"
                                         + "<td><a href='images/product/"+products.get(index).getProductID()+".jpg'>Click here</a></td>");
                                 out.println("<td><input type=\"hidden\" name=\"action\" value=\"productupdate\"><input type=\"hidden\" name=\"Productid\" value=\"" + products.get(index).getProductID() + "\"></form><form method='POST' action='product'><input type=\"hidden\" name=\"list\" value=\"deleteproduct\"><input type=\"hidden\" name=\"Productid\" value=\"" + products.get(index).getProductID() + "\"><button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Delete</button></td></tr></form>");

                             }


                         }catch(Exception e){
                             System.out.println(e);
                                }
                         out.println("<tr><form method=\"POST\"  action=\"fileUploadServlet\" enctype=\"multipart/form-data\">"
                                           + "<tr><td class=\"mdl-data-table__cell--non-numeric\"><input class=\"mdl-textfield__input\" type=\"text\" name='ProductName' id=\"ProductName\" ></td>"
                                         + "<td><select name='ProductCat'><option></option>");
                                 for(int v=0;v<cat.size();v++){
                                     out.println("<option value='"+cat.get(v).getCatid()+"'>"+cat.get(v).getCatname()+"</option>");
                                 }
                                 out.println("</td><td><input class=\"mdl-textfield__input\" type=\"text\" name='Brand' id=\"Brand\" ></td>"
                                         + "<td><input class=\"mdl-textfield__input\" type=\"text\" name='Descriptions' id=\"Descriptions\"></td>"
                                         + "<td><input class=\"mdl-textfield__input\" type=\"text\" name='Price' id=\"Price\"></td><td><label class=\"mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect\" for=\"isGift\"><input type=\"checkbox\"  name='isGift' id=\"isGift\" class=\"mdl-checkbox__input\"></label></td>"
                                         + "<td><input type=\"file\" name=\"file\" /></td><td><button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Add</button></td></form></tr>");
                        %>
                            </tbody>
                        </table>
                    </div>
                    <div class="mdl-tabs__panel" id="Category-panel">
                        <table class="mdl-data-table mdl-js-data-table">
                            <thead>
                                <tr>
                                    <th class="mdl-data-table__cell--non-numeric">Category Name</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    //ArrayList<CategoryBean> cat=new ArrayList<CategoryBean>();
                                try{
                                cat= (ArrayList) session.getAttribute("cat");
                                for(int i=0;i<cat.size();i++){
                                   out.println("<tr><form method=\"POST\"  action=\"main\">"
                                           + "<input type='hidden' name='action' value='updatecat' /><input type='hidden' name='CategoryId' value=\""+cat.get(i).getCatid()+"\" />"
                                           + "<td><input class=\"mdl-textfield__input\" type=\"text\" onchange='this.form.submit()' name='CategoryName' id=\"CategoryName\" value='" +cat.get(i).getCatname()+ "'></td>"
                                           + "<td><button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >update</button></td></tr></form>");
                                }
                                out.println("<tr><form method=\"POST\"  action=\"main\">"
                                           + "<input type='hidden' name='action' value='addcat' />"
                                           + "<td><input class=\"mdl-textfield__input\" type=\"text\" name='CategoryName' id=\"CategoryName\" ></td>"
                                           + "<td><button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Add</button></td></form></tr>");
                                }catch(Exception e){
                                }
                                %>
                            </tbody>
                        </table>
                    </div>
                            <div class="mdl-tabs__panel" id="Order-panel">
                                <table class="mdl-data-table mdl-js-data-table">
                            <thead>
                                <tr>
                                    <th class="mdl-data-table__cell--non-numeric">Order Id</th>
                                    <th>Total Price/Point</th>
                                    <th>Order Date</th>
                                    <th>Status</th>
                                    <th >Action</th>
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
                                        out.println("<td><form action='cart'><input type='hidden' name='action' value='changestatus'><input type='hidden' name='orderId' value='"+order.get(index).getOrderId()+"'><select onchange='this.form.submit()' name='status'><option value='"+order.get(index).getStatus()+"'>"+order.get(index).getStatus()+"</option><option value='delivered'>delivered</option><option value='picked-up'>picked-up</option><option value='canceled'>canceled</option><option value='process'>process</option></select></form></td>");
                                         out.println("<td><form action='cart' ><input type=\"hidden\" name=\"action\" value=\"complete\"><input type=\"hidden\" name=\"orderId\" value='"+order.get(index).getOrderId()+"' ><button class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\" type=\"submit\" >Complete</button></form></td>");
                                       out.print("</tr>");
                                       while(index==11){
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
