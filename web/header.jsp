
<%@page import="ict.bean.*"%>
<header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
        <!-- Title -->
        <span class="mdl-layout-title"><a href="index.jsp"> Stationery Online Station</a></span>
        <!-- Add spacer, to align navigation to the right -->
        <div class="mdl-layout-spacer"></div>
        <!-- Navigation. We hide it in small screens. -->
        <nav class="mdl-navigation mdl-layout--large-screen-only">

            <a class="mdl-navigation__link" href="product">All</a>
            <a class="mdl-navigation__link" href="product?list=product">Product</a>
            <a class="mdl-navigation__link" href="product?list=gift">Gift</a>


            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable
                 mdl-textfield--floating-label mdl-textfield--align-right">
                <label class="mdl-button mdl-js-button mdl-button--icon"
                       for="fixed-header-drawer-exp">
                    <i class="material-icons">search</i>
                </label>
                <div class="mdl-textfield__expandable-holder">
                    <form id="search" method="POST" action="product">
                        <input type="hidden" name="list" value="key">
                        <input class="mdl-textfield__input" type="text" name="keyword" id="fixed-header-drawer-exp">
                    </form>
                </div>
            </div>
            <button class="mdl-button mdl-js-button mdl-button--icon">
                <a href="cart.jsp"><i class="material-icons">shopping_cart</i></a>
            </button>
            <button id="more-button" class="mdl-button mdl-js-button mdl-button--icon">
                <i class="material-icons">more_vert</i>
            </button>
            <ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect"
                for="more-button">
                <%
                    if (session.getAttribute("userInfo") != null) {
                        
                        UserInfo user=(UserInfo)session.getAttribute("userInfo");
                        if(user.getIntType()==2){
                             out.println("<li class=\"mdl-menu__item\"><a href=\"admin.jsp\">Admin</a></li>");
                        }
                        out.println("<li class=\"mdl-menu__item\"><a href=\"user.jsp\">user page</a></li>");
                        out.println("<li class=\"mdl-menu__item\"><a href=\"main?action=logout\">Logout</a></li>");
                    } else {
                        out.println(" <li class=\"mdl-menu__item\"><a href='signup.jsp'>signup</a></li>");
                        out.println(" <li class=\"mdl-menu__item\"><a href='login.jsp'>login</a></li>");
                    }
                %>
            </ul>
        </nav>
    </div>
    <script>
        $("input").keypress(function (event) {
            if (event.which == 13) {
                event.preventDefault();
                $("#search").submit();
            }
        });
    </script>
</header>
<div class="mdl-layout__drawer">
    <span class="mdl-layout-title">SOS</span>
    <nav class="mdl-navigation">
        <a class="mdl-navigation__link" href="product">All</a>
        <%
            if (session.getAttribute("userInfo") != null) {
                out.println("<a class=\"mdl-navigation__link\" href=\"main?action=logout\">Logout</a>");
            } else {
                out.println(" <a class=\"mdl-navigation__link\" href='signup.jsp'>signup</a>");
                out.println(" <a class=\"mdl-navigation__link\" href='login.jsp'>login</a>");
            }
        %>
    </nav>
</div>
