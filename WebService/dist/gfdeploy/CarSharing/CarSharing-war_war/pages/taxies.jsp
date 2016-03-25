<%-- 
    Document   : taxies
    Created on : 14-mar-2016, 10:17:44
    Author     : Sergio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="top.jsp" %>

<div class="main-container">
    
</div>
    
<script>
    $(document).ready(function(){
        $.get("../ListPostServlet?type=taxi",function(data){
            console.log("HOLA");
            $('.main-container').html(data); 
        }); 
    });
</script>
