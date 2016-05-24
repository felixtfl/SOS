<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <jsp:include page="MDL.jsp" />
    </head>
    <body>
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="header.jsp" />
            <div class=" mdl-grid mdl-card mdl-shadow--2dp">
                <div class=" mdl-cell mdl-cell--10-col">
                    <form method="POST" action="main">
                        <input type="hidden" name="action" value="authenticate"/>
                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                            <input class="uname mdl-textfield__input" type="text" id="username" name="username" required>
                            <label class="mdl-textfield__label" for="username">Login name</label>                     
                        </div>
                        <br/>
                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                            <input class="uname mdl-textfield__input" type="password" id="password" name="password" required>
                            <label class="mdl-textfield__label" for="password">Password</label>                     
                        </div>
                        
                        <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" type="submit" >
                        Login
                    </button>
                        <p>Click <a href="signup.jsp">here</a> to create new account</p>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
