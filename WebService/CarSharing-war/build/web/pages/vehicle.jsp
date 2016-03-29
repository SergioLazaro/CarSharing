

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="top.jsp" %>
<script src="../js/displayInfo.js"></script>
<div class="main-container" id="container">
    
    <select name="selectedPostType" id="postTypeSelector">
        <option value="car" default>Car</option>
        <option value="taxi">Taxi</option>
    </select>
    
    <div class="info-container" id="container-info"></div>
      
</div>
    
<%@ include file="bottom.html" %>