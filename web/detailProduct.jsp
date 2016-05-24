<%@page import="java.util.ArrayList,ict.bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail Page</title><jsp:include page="MDL.jsp" />
    </head>
    <body>
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="header.jsp" />
            <div class="mdl-grid">
                    <%
                        ArrayList<ProductBean> product = (ArrayList) session.getAttribute("p");
                        ArrayList<CategoryBean> cat = (ArrayList) session.getAttribute("cat");
                        for (int i = 0; i < product.size(); i++) {
                            String Price="";
                            if(product.get(i).isIsGift())
                                Price="Point : "+product.get(i).getPrice()+" P";
                            else{
                                Price="Price : HK$"+product.get(i).getPrice();
                            }
                            out.println("<div class=\"mdl-cell mdl-cell--10-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-card mdl-shadow--3dp\">\n"
                                    + "             <img src=\"images/product/" + product.get(i).getPhotourl() + "\">"
                                    + "            <div class=\"mdl-card__title\">\n"
                                    + "               <h4 class=\"mdl-card__title-text\">" + product.get(i).getProductName() + "</h4>"
                                    + "            </div>\n"
                                    + "            <div class=\"mdl-card__supporting-text\">\n"
                                    + "              <span class=\"mdl-typography--font-light mdl-typography--subhead\">Category: "+"<a href='product?list=cat&productCat="+product.get(i).getProductCat()+"'>" + cat.get(Integer.parseInt(product.get(i).getProductCat())-1).getCatname() + "</a><br>Brand : "+product.get(i).getProductBrand()+"<br/>Descriptions : " + product.get(i).getDescriptions() + "<br> "+Price+"</span>\n"
                                    + "            </div>\n"
                                    + "            <div class=\"mdl-card__actions\"><form action='cart' method='POST'>"
                                    + "<input type='hidden' name='ProductId' value='" + product.get(i).getProductID() + "'>"
                                    + "<input type='number' name='qty' value='1' ><input type='hidden' name='action' value='add' >"
                                    + "               <button class=\"mdl-button mdl-js-button mdl-typography--text-uppercase\" type='submit'>"
                                    + "                 <i class=\"material-icons\">add_shopping_cart</i>Add to cart</button></form>"
                                    + "            </div>"
                                    + "          </div>");
                        }
                    %>
                </div>
        </div>
    </body>
</html>
