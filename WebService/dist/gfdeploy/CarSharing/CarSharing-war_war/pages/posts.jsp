<%-- 
    Document   : posts
    Created on : 14-mar-2016, 10:18:00
    Author     : Sergio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="top.jsp"/>
<div class="main-container">
    <%
        // Get an array of Cookies associated with this domain
        Cookie[] cookies = null;
        cookies = request.getCookies();
        Cookie username = null;
        if( cookies != null ){
           for (int i = 0; i < cookies.length; i++){
              if(cookies[i].getName().equals("email")){
                  username = cookies[i];
              }
           }
        }
        if(username != null){   //If user has logged in
            %>
            <div class="main-container-signin" id="grey-background">
                <form action="../startOrder" method="POST">
                    <h2> Choose your transport</h2></br>
                    <div class="vehicle">
                        <label class="padded-label">Transport type: </label>
                        <select name="transport" id="selector">
                            <option value="car">Car</option>
                            <option value="taxi">Taxi</option>
                        </select>
                    </div>
                    <label class="sr-only">Date</label>
                    <input name="date" class="form-control" type="date" autofocus="" required placeholder="Date"/>
                    <label class="sr-only">Departure</label>
                    <input name="departure" class="form-control" type="text" autofocus="" required placeholder="Departure"/>
                    <div id="destination-block">
                        <label class="sr-only">Destination</label>
                        <input name="destination" class="form-control" type="text" autofocus="" required placeholder="Destination"/>
                    </div>
                    <div id="type-block">
                        <label class="sr-only">CarType</label>
                        <input name="cartype" class="form-control" type="text" autofocus="" required placeholder="Car type"/>
                    </div>
                    <div id="year-block">
                        <label class="sr-only">CarYear</label>
                        <input name="caryear" class="form-control" type="number" autofocus="" required placeholder="Car year"/>
                    </div>
                 
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>

                </form>
            </div>
            <%
        }
        else{
            response.sendRedirect("login.jsp?error=1");
        }
    %>
</div> 
    <script>
        $(document).ready(function(){
            $('#selector').on('change',function(){
                if()
            });
        });
    </script>
<jsp:include page="bottom.html"/>