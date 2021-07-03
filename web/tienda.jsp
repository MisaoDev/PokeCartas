<%--
    Document   : login
    Author     : MisaoDev <{@link https://github.com/MisaoDev}>
--%>

<%@page import="dao.ProductoDAO"%>
<%@page import="model.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="util.Enums.TipoUsuario"%>
<%@page import="model.Usuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <title>Document</title>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="ie=edge" />

  <link rel="stylesheet" href="/styles/main.css" />
  <link rel="stylesheet" href="/styles/tienda.css" />
  <link rel="shortcut icon" type="image/png" href="img/favicon.png" />

  <!-- Bootstrap (customized) -->
  <link rel="stylesheet" href="styles/bootstrap.css">
</head>

<%
  // for testing purposes
  ArrayList<Producto> products = new ProductoDAO().getList();
  pageContext.setAttribute("filteredProducts", products);
%>

<body>
  <%-- Variable para determinar link activo en la barra de navegación --%>
  <c:set var="activePage" value="tienda"/>
  <%-- Barra de navegación --%>
  <%@include file="/navbar.jsp" %>

  <div class="container pt-5">
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5">
      <c:forEach var="pr" items="${filteredProducts}">
        <div class="col p-1 mb-3">

          <div class="w-100 h-100 text-center hoverable">
            <c:if test="${pr.descuento != 0}">
              <span class="discount-label badge">${pr.descuento}%</span>
            </c:if>
            <small class="text-muted">${pr.id}</small>
            <small class="text-muted">${pr.tipo.nombre}</small>
            <a href="">
              <div class="mx-auto" style="height: 266px;">
                <img src="/img/products/${pr.id}.jpg" alt="Imagen del producto"
                  class="img-thumbnail mb-2 mh-100" />
              </div>
              <h6 class="mb-0">${pr.nombre}</h6>
            </a>
            <h5>\$${pr.getValorFinal()}</h5>
          </div>

        </div>
      </c:forEach>
    </div>
  </div>

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
  <!-- Bootstrap -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>

</html>
