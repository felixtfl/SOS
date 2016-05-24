<%@page import="java.util.ArrayList,ict.bean.ProductBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Page</title><jsp:include page="MDL.jsp" />
        <style>
            .mdl-card__media{
                background-color: #FFF !important;
                height:248px
            }
            .mdl-card__media img{
                height:auto;
                max-width:300px;
                //max-height: 248px;
                //_width:expression(this.width > 300 ? "300px" : this.width);
            }.mdl-card__actions input{
                max-width: 88px;
            }
            .mdl-card__supporting-text {
                height: 55px;
            }
        </style>
    </head>
    <body>
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="header.jsp" />
            <div class="android-more-section">
                <div class="mdl-grid">
                    <%
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
                    %>
                </div>
            </div>
        </div>
    </body>
</html>
