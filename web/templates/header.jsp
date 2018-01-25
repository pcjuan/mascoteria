<%-- 
    Document   : index
    Created on : Jan 22, 2018, 10:24:11 AM
    Author     : sarayar
--%>
<%@page import="cl.inacap.mascoteria2018.beans.ServiceBeanLocal"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <%!private ServiceBeanLocal service;%>
        <%
            InitialContext ctx = new InitialContext();
            this.service = (ServiceBeanLocal) ctx.lookup("java:global/Mascoteria2018/ServiceBean!cl.inacap.mascoteria2018.beans.ServiceBeanLocal");
        %>

        <script src="https://code.jquery.com/jquery-3.3.1.min.js" 
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
    </head>
    <body>