<%-- 
    Document   : car
    Created on : 14-mar-2016, 10:17:36
    Author     : Sergio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="top.jsp" %>
<div class="main-container">
    
</div>
    
<script>
    $(document).ready(function(){
        $.get("../ListPostServlet?type=car",function(data){
          $('.main-container').html(data); 
        }); 
    });
</script>



<%@ include file="bottom.html" %>
