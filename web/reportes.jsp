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

<body>
  <%-- Variable para determinar link activo en la barra de navegación --%>
  <c:set var="activePage" value="reportes"/>
  <%-- Barra de navegación --%>
  <%@include file="/navbar.jsp" %>

  <div class="container">
    <div class="jumbotron mt-4 py-4">

      <h1 class="text-center mb-4">Reportes por fecha</h1>

      <form action="/VentaController" class="needs-validation" novalidate method="get">
        <legend>Seleccione el periodo de ventas</legend>

        <div class="form-row">
          <div class="form-group col-md-6">
            <label for="iFechaDesde" class="req">Desde</label>
            <input type="date" name="iFechaDesde" id="iFechaDesde" class="form-control" />
          </div>
          <div class="form-group col-md-6">
            <label for="iFechaHasta" class="req">Hasta</label>
            <input type="date" name="iFechaHasta" id="iFechaHasta" class="form-control" />
          </div>
        </div>

        <div class="col mt-3 text-center">
          <button type="submit" class="btn btn-success" name="action" value="generarReporte">
            Generar reporte
          </button>
        </div>

      </form>

    </div>
  </div>

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
  <!-- Bootstrap -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

  <script src="/scripts/validarBootstrap.js"></script>

</body>

</html>
