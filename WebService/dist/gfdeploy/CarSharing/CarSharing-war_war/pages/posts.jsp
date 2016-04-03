<%-- 
    Document   : posts
    Created on : 14-mar-2016, 10:18:00
    Author     : Sergio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="top.jsp"/>
<script src="../js/managePosts.js"></script>
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
                <form action="../PostServlet?version=web" method="POST">
                    <h2> Choose your transport</h2></br>
                    <div class="spinner-container">
                        <label class="padded-label">Transport: </label>
                        <select name="transport" id="transport-selector">
                            <option value="Car" default>Car</option>
                            <option value="Taxi">Taxi</option>
                        </select>
                    </div>
                    <div class="vehicle">
                        <label class="sr-only">Date</label>
                        <input name="date" class="form-control" type="date" autofocus="" required placeholder="Date"/>
                    </div>
                    <div class="spinner-container">
                        <label class="padded-label">Departure: </label>
                        <select name="departure" id="selectorDeparture">
                            <option>-</option>
                            <option>Bologna</option>
                            <option>Firenze</option>
                            <option>Genova</option>
                            <option>Milano</option>
                            <option>Napoli</option>
                            <option>Padova</option>
                            <option>Pisa</option>
                            <option>Roma</option>
                            <option>Siena</option>
                            <option>Torino</option>
                            <option>Venezia</option>
                            <option>Verona</option>
                        </select>
                    </div>
                    <div class="spinner-container" id="destination-block">
                        <label class="padded-label">Destination: </label>
                        <select name="destination" id="selectorDestination">
                            <option>-</option>
                            <option>Bologna</option>
                            <option>Firenze</option>
                            <option>Genova</option>
                            <option>Milano</option>
                            <option>Napoli</option>
                            <option>Padova</option>
                            <option>Pisa</option>
                            <option>Roma</option>
                            <option>Siena</option>
                            <option>Torino</option>
                            <option>Venezia</option>
                            <option>Verona</option>
                        </select>
                    </div>
                    <div class="vehicle" id="type-block">
                        <label class="sr-only">CarType</label>
                        <input name="cartype" class="form-control" type="text" autofocus="" placeholder="Car type"/>
                    </div>
                    <div class="vehicle" id="year-block">
                        <label class="sr-only">CarYear</label>
                        <input name="caryear" class="form-control" type="number" autofocus="" placeholder="Car year"/>
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
<jsp:include page="bottom.html"/>