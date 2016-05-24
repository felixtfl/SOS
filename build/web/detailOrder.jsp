<%@page import="java.util.ArrayList,ict.bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Detail</title><jsp:include page="MDL.jsp" />
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <%
            ArrayList<ProductBean> cart = new ArrayList<ProductBean>();
            ArrayList<CategoryBean> cat = new ArrayList<CategoryBean>();
            double totalprice = 0.0;
            int totalepoint = 0;
            int totalqty = 0;
            try {
                cart = (ArrayList) session.getAttribute("Cart");
                cat = (ArrayList) session.getAttribute("cat");
        %>
        <h4>Order Detail</h4>

        <div class="mdl-grid">
            <table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Product Category</th>
                        <th>Price/Point(unit price)</th>
                        <th>Quantity</th>
                    </tr>
                </thead>
                <tb>
                    <%
                        for (int index = 0; index < cart.size(); index++) {
                            String pid = cart.get(index).getProductCat();
                            out.println("<tr><td><a href='product?list=detail&productId=" + cart.get(index).getProductID() + "'>" + cart.get(index).getProductName() + "</a></td>"
                                    + "<td> <a href='product?list=cat&productCat=" + cart.get(index).getProductCat() + "'>" + cat.get(Integer.parseInt(pid) - 1).getCatname() + "</a></td>");
                            String Price = "";
                            if (cart.get(index).isIsGift()) {
                                totalepoint += cart.get(index).getPrice() * cart.get(index).getQty();
                                Price = cart.get(index).getPrice() * cart.get(index).getQty() + "P ( " + cart.get(index).getPrice() + ") ";
                            } else if (!cart.get(index).isIsGift()) {
                                Price = "HK$ " + cart.get(index).getPrice() * cart.get(index).getQty() + " (HK$" + cart.get(index).getPrice() + ")";
                                totalprice += cart.get(index).getPrice() * cart.get(index).getQty();
                            }
                            out.println("<td>" + Price + "</td>");
                            out.println("<td>" + cart.get(index).getQty() + "</td>");
                            out.println("</tr>");
                            totalqty += cart.get(index).getQty();
                            //out.print(cart.size()+" index "+index);
                        }

                    %>
                </tb>
                <tfoot>
                    <%    out.println("<tr><td><b>Total</b></td><td></td><td>HK$" + totalprice + "<br>" + totalepoint + " P</td><td>" + totalqty + "</td>");
                            out.println("</tr>");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    %>
                <br>
                    
                </tfoot>
            </table>
                <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" onclick='history.back();' type="button" >go back</button>
        </div>
    </body>
</html>
