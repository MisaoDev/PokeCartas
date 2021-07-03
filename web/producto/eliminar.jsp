<%--
    Document   : login
    Author     : MisaoDev <{@link https://github.com/MisaoDev}>
--%>

<%@page import="dao.ProductoDAO"%>
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
  <link rel="shortcut icon" type="image/png" href="/img/favicon.png" />

  <!-- Bootstrap (customized) -->
  <link rel="stylesheet" href="/styles/bootstrap.css">
</head>

<%
  pageContext.setAttribute("productList", new ProductoDAO().getList());
%>

<body>
  <%-- Variable para determinar link activo en la barra de navegación --%>
  <c:set var="activePage" value="productos" />
  <%-- Barra de navegación --%>
  <%@include file="/navbar.jsp" %>


  <div class="container">
    <div class="jumbotron mt-3 py-3 mx-5 text-center">
      <p>Se eliminarán los siguientes productos de la base de datos.</p>

      <div class="w-100">
        <form action="/ProductoController" method="post" novalidate class="inline-form">
          <button type="submit" name="action" value="confirmarEliminar" class="btn btn-danger mr-5">Eliminar</button>
          <a class="btn btn-primary" href="/producto/lista.jsp">Cancelar</a>
        </form>
      </div>
    </div>

    <div class="table-responsive">
      <table class="table table-sm table-hover table-striped">
        <colgroup>
          <col />
          <col />
          <col />
          <col />
          <col />
          <col />
          <col />
        </colgroup>
        <thead class="thead-dark">
          <tr>
            <c:forTokens items = "ID,Producto,Descripción,Tipo,Precio,Dcto,Stock" delims = "," var = "texto">
              <th scope="col"><c:out value = "${texto}"/></th>
            </c:forTokens>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="pr" items="${selectedItems}">
            <tr>
              <td>${pr.id}</td>
              <td>${pr.nombre}</td>
              <td>${pr.descripción}</td>
              <td>${pr.tipo.nombre}</td>
              <td>\$${pr.precio}</td>
              <td>${pr.descuento}%</td>
              <td>${pr.stock}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>


  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
  <!-- Bootstrap -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>

</html>
