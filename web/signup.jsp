
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up</title>
        <jsp:include page="MDL.jsp"/>    
        <script type="text/javascript">  
          $(document).ready(function(){  
              $(".uname").change(function(){  
                  var uname = $(this).val();  
                  if(uname.length >= 3){
                      $(".status").html("<font color=gray> Checking availability...</font>");  
                       $.ajax({  
                          type: "POST",  
                          url: "main",  
                          data: "action=check&table=userinfo&f=username&where="+ uname,  
                          success: function(msg){  
  
                              $(".status").ajaxComplete(function(event, request, settings){  
                                  $(".status").html(msg);
                              });  
                          }  
                      });   
                  }  
                  else{  
                         
                      $(".status").html("<font color=red>Username should be <b>3</b> character long.</font>");  
                  }  
                    
              });  
          });  
        </script>   
       <style>
            .c{
                width: 356px;
                padding: 10px;
            }
        </style>
        <%
        if(session.getAttribute("userInfo")!=null){
           response.sendRedirect("index.jsp");
        }
        %>
    </head>
    <body><div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
        
        <jsp:include page="header.jsp" />
            <div class="page-content">
        <div class="c mdl-grid mdl-card mdl-shadow--2dp">
            <div class="c mdl-cell mdl-cell--10-col">
                <form method="POST" action="main">
                    <input type="hidden" name="action" value="reg"/>
                    
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input class="mdl-textfield__input" type="text" id="name" name="name" required>
                        <label class="mdl-textfield__label" for="name">Name</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input class="uname mdl-textfield__input" type="text" id="username" name="username" required>
                        <label class="mdl-textfield__label" for="username">Login name</label>                     
                    </div>
                   <span class="status"></span>
                   
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input class="mdl-textfield__input" type="password" id="password" name="password" required>
                        <label class="mdl-textfield__label" for="password">Password</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="phone" name="phone">
                        <label class="mdl-textfield__label" for="phone">Phone No</label>
                        <span class="mdl-textfield__error">Input is not a number!</span>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <textarea class="mdl-textfield__input" rows= "3" id="address" name="address" required></textarea>
                        <label class="mdl-textfield__label" for="address">Address</label>
                    </div>
                    
                    <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect" type="submit" >
                        Submit
                    </button>
                </form>
            </div>
        </div>
        
        <jsp:include page="footer.jsp" />
            </div>
        </div>
    </body>
</html>
