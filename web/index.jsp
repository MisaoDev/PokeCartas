<%--
    Document   : login
    Author     : MisaoDev <{@link https://github.com/MisaoDev}>
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <title>Document</title>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="ie=edge" />

  <link rel="stylesheet" href="styles/main.css" />
  <link rel="shortcut icon" type="image/png" href="img/favicon.png" />

  <!-- Bootstrap (customized) -->
  <link rel="stylesheet" href="styles/bootstrap.css">
</head>

<%
  response.sendRedirect("/tienda.jsp");
%>

<body>
  <%-- Variable para determinar link activo en la barra de navegación --%>
  <c:set var="activePage" value="index"/>
  <%-- Barra de navegación --%>
  <%@include file="/navbar.jsp" %>

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
  <!-- Bootstrap -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>

</html>
