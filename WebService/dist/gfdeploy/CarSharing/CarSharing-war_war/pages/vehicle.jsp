

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="top.jsp" %>
<script src="../js/displayInfo.js"></script>
<div class="main-container" id="container">
    
    <select name="selectedPostType" id="postTypeSelector">
        <option value="Car" selected="selected">Car</option>
        <option value="Taxi">Taxi</option>
    </select>
    
    <div class="info-container" id="container-info"></div>
      
</div>
    
<%@ include file="bottom.html" %>