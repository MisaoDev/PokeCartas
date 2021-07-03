<%--
    Document   : login
    Author     : MisaoDev <{@link https://github.com/MisaoDev}>
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <title>PokeCartas</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">

  <link rel="stylesheet" href="/styles/main.css" />
  <link rel="shortcut icon" type="image/png" href="/img/favicon.png" />

  <!-- Bootstrap (customized) -->
  <link rel="stylesheet" href="/styles/bootstrap.css">
  <link href="open-iconic/font/css/open-iconic-bootstrap.css" rel="stylesheet">
</head>

<body>
  <%-- Variable para determinar link activo en la barra de navegación --%>
  <c:set var="activePage" value="productos" />
  <%-- Barra de navegación --%>
  <%@include file="/navbar.jsp" %>

  <div class="container">
    <div class="jumbotron mt-4 py-4">

      <h1 class="text-center mb-4">
        ${modoEditar ? 'Editar el producto' : 'Crear nuevo producto'}
      </h1>

      <form action="/ProductoController" class="needs-validation" method="post" novalidate>

        <legend class="mb-3">Datos del producto</legend>

        <div class="form-row">
          <div class="form-group col-md-2">
            <label for="iPid" class="req">PID</label>
            <input type="text" name="iPid" id="iPid" class="form-control" required value="${iPid}"
              ${modoEditar ? 'readonly' : ''}/>
            <small class="text-danger d-none" id="productoExiste">PID ya existe.</small>
          </div>
          <div class="form-group col-md-10">
            <label for="iNombre" class="req">Nombre del producto</label>
            <input type="text" name="iNombre" id="iNombre" class="form-control" required value="${iNombre}" />
          </div>
        </div>

        <div class="form-group">
          <label for="iDescripción">Descripción del producto</label>
          <input type="text" name="iDescripción" id="iDescripción" class="form-control" value="${iDescripción}" />
        </div>

        <div class="form-group">
          <label for="iTipo" class="req">Tipo de producto</label>
          <select name="iTipo" id="iTipo" required class="form-control">
            <option value="" disabled selected hidden>Seleccione un tipo</option>
            <option value="CARTA"   ${iTipo == 'CARTA'   ? 'selected' : ''}>Cartas sueltas</option>
            <option value="SELLADO" ${iTipo == 'SELLADO' ? 'selected' : ''}>Producto sellado</option>
            <option value="INSUMO"  ${iTipo == 'INSUMO'  ? 'selected' : ''}>Insumo TCG</option>
          </select>
        </div>

        <div class="form-row">
          <div class="form-group col-md-4">
            <label for="iPrecio" class="req">Precio</label>
            <input type="text" name="iPrecio" id="iPrecio" class="form-control" required value="${iPrecio}" />
          </div>
          <div class="form-group col-md-4">
            <label for="iDescuento">Descuento</label>
            <input type="text" name="iDescuento" id="iDescuento" class="form-control" value="${iDescuento}" />
          </div>
          <div class="form-group col-md-4">
            <label for="iStock" class="req">Unidades en stock</label>
            <input type="text" name="iStock" id="iStock" class="form-control" required value="${iStock}" />
          </div>
        </div>

        <div class="inline-form mt-5 w-100">
          <button type="submit" class="btn btn-success ${modoEditar ? 'd-none' : ''}" name="action" value="crearProducto">
            Crear producto
          </button>
          <button type="submit" class="btn btn-primary ${modoEditar ? '' : 'd-none'}" name="action" value="actualizarProducto">
            Editar producto
          </button>
          <a class="btn btn-primary float-right" href="/producto/lista.jsp">
            Cancelar
          </a>
        </div>
      </form>

    </div>
  </div>

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
  <!-- Bootstrap -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

  <script src="/scripts/validarBootstrap.js"></script>
  <script>
    // Procesar errores server-side
    <c:if test="${errProductoExiste}">
      $('#productoExiste').removeClass('d-none');
      $('#iPid').focus();
      $('#iPid').select();
    </c:if>
  </script>

</body>

</html>
