<%--
    Document   : login
    Author     : MisaoDev <{@link https://github.com/MisaoDev}>
--%>

<%@page import="dao.VentaDAO"%>
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
  if (request.getAttribute("isReporte") == null) {
    pageContext.setAttribute("listaVentas", new VentaDAO().getList());
  }
%>

<body>
  <%-- Variable para determinar link activo en la barra de navegación --%>
  <c:set var="activePage" value="${isReporte ? 'reportes' : 'ventas'}" />
  <%-- Barra de navegación --%>
  <%@include file="/navbar.jsp" %>

  <form action="/VentaController" method="post" novalidate>

    <c:if test="${! isReporte}">
      <nav class="navbar sticky-top navbar-light bg-light align-right">
        <div class="form-inline w-100">
          <button type="submit" name="action" value="nuevo" class="btn btn-primary mr-auto">Nueva</button>
          <div class="form-inline">
            <button type="submit" name="action" value="editar" class="btn btn-primary mr-sm-2">Editar</button>
            <button type="submit" name="action" value="eliminar" class="btn btn-primary">
              Eliminar
            </button>
          </div>
        </div>
      </nav>
    </c:if>

    <c:if test="${isReporte}">
      <div class="text-center">
        <h3 class="mt-2 mb-3">Reporte ${fechaDesde} - ${fechaHasta}</h1>
      </div>
    </c:if>

    <div class="table-responsive">
      <table class="table table-sm table-hover table-striped">
        <colgroup>
          <col style="width: 1%;" />
          <col />
          <col />
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
            <th scope="col">
              <%-- <div class="form-check">
                <input type="checkbox" id="selectAll" class="form-check-input position-static" />
              </div> --%>
            </th>
            <c:forTokens items = "ID,Usuario,Fecha,Hora,PID,Producto,Precio,Cantidad,Total" delims = "," var = "texto">
              <th scope="col"><c:out value = "${texto}"/></th>
            </c:forTokens>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="ve" items="${listaVentas}">
            <tr>
              <td>
                <div class="form-check">
                  <input type="checkbox" name="idVenta" value="${ve.id}"
                    class="form-check-input position-static product-checkbox" />
                </div>
              </td>
              <td>${ve.id}</td>
              <td>${ve.username}</td>
              <td>${ve.fecha}</td>
              <td>${ve.hora}</td>
              <td>${ve.producto.id}</td>
              <td>${ve.producto.nombre}</td>
              <td>\$${ve.precioUnitario}</td>
              <td>${ve.cantidad}</td>
              <td>\$${ve.precioUnitario * ve.cantidad}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </form>

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
  <!-- Bootstrap -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>

</html>
