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
  <title>PokeCartas</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">

  <link rel="stylesheet" href="/styles/main.css" />
  <link rel="shortcut icon" type="image/png" href="/img/favicon.png" />

  <!-- Bootstrap (customized) -->
  <link rel="stylesheet" href="/styles/bootstrap.css">
  <link href="/open-iconic/font/css/open-iconic-bootstrap.css" rel="stylesheet">
</head>

<body>
  <%-- Variable para determinar link activo en la barra de navegación --%>
  <c:set var="activePage" value="ventas" />
  <%-- Barra de navegación --%>
  <%@include file="/navbar.jsp" %>

  <div class="container">
    <div class="jumbotron mt-4 py-4">

      <h1 class="text-center mb-4">
        ${modoEditar ? 'Editar la venta' : 'Crear nueva venta'}
      </h1>

      <form action="/VentaController" class="needs-validation" method="post" novalidate>

        <div class="form-row">
          <div class="form-group col-md-3">
            <label for="iId" class="req">ID de venta</label>
            <input type="text" name="iId" id="iId" class="form-control" required value="${iId}"
              ${modoEditar ? 'readonly' : ''}/>
            <small class="text-danger d-none" id="ventaExiste">ID ya existe.</small>
          </div>
          <div class="form-group col-md-9">
            <label for="iUsername" class="req">Nombre de usuario del cliente</label>
            <input type="text" name="iUsername" id="iUsername" class="form-control" required value="${iUsername}" />
          </div>
        </div>

        <div class="form-row">
          <div class="form-group col-md-6">
            <label for="iFecha" class="req">Fecha de la venta</label>
            <input type="date" name="iFecha" id="iFecha" class="form-control" value="${iFecha}" />
          </div>
          <div class="form-group col-md-6">
            <label for="iHora" class="req">Hora de la venta</label>
            <input type="time" name="iHora" id="iHora" class="form-control" value="${iHora}" />
          </div>
        </div>

        <%
          pageContext.setAttribute("productList", new ProductoDAO().getList());
        %>

        <div class="form-group">
          <label for="iProducto" class="req">Tipo de producto</label>
          <select name="iProducto" id="iProducto" required class="form-control">
            <option value="" disabled selected hidden>Seleccione un producto</option>

            <c:forEach var="pr" items="${productList}">
              <option value="${pr.id}" ${iPid == pr.id ? 'selected' : ''}>
                ${pr.nombre}
              </option>
            </c:forEach>

          </select>
        </div>

        <div class="jumbotron shadow mt-4 py-3 shadow">
          <div class="form-row">
            <div class="form-group col-md-2">
              <label class="mb-0" for="iPid">PID</label>
              <input type="text" name="iPid" id="iPid"
                class="form-control form-control-sm" readonly value="${iPid}" />
            </div>
            <div class="form-group col-md-10">
              <label class="mb-0" for="iProductoTipo">Tipo</label>
              <input type="text" name="iProductoTipo" id="iProductoTipo"
                class="form-control form-control-sm" readonly value="${iProductoTipo}" />
            </div>
          </div>

          <div class="form-group">
            <label class="mb-0" for="iProductoDescripción">Descripción</label>
            <input type="text" name="iProductoDescripción" id="iProductoDescripción"
              class="form-control form-control-sm" readonly value="${iProductoDescripción}" />
          </div>

          <div class="form-row">
            <div class="form-group col-md-4">
              <label class="mb-0" for="iProductoPrecio">Precio</label>
              <input type="text" name="iProductoPrecio" id="iProductoPrecio"
                class="form-control form-control-sm" readonly value="${iProductoPrecio}" />
            </div>
            <div class="form-group col-md-4">
              <label class="mb-0" for="iProductoDescuento">Descuento</label>
              <input type="text" name="iProductoDescuento" id="iProductoDescuento"
                class="form-control form-control-sm" readonly value="${iProductoDescuento}" />
            </div>
            <div class="form-group col-md-4">
              <label class="mb-0" for="iProductoStock">Unidades en stock</label>
              <input type="text" name="iProductoStock" id="iProductoStock"
                class="form-control form-control-sm" readonly value="${iProductoStock}" />
            </div>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group col-md-4">
            <label for="iPrecioUnitario">Precio unitario</label>
            <input type="number" name="iPrecioUnitario" id="iPrecioUnitario" min="0"
              class="form-control" required value="${iPrecioUnitario}" />
          </div>
          <div class="form-group col-md-4">
            <label for="iCantidad">Cantidad</label>
            <input type="number" name="iCantidad" id="iCantidad" min="1"
              class="form-control" required value="${iCantidad != null ? iCantidad : 1}" />
          </div>
          <div class="form-group col-md-4">
            <label for="iTotal">Total</label>
            <input type="text" name="iTotal" id="iTotal"
              class="form-control" readonly value="${iTotal}" />
          </div>
        </div>

        <div class="inline-form mt-5 w-100">
          <button type="submit" class="btn btn-success ${modoEditar ? 'd-none' : ''}" name="action" value="crearVenta">
            Crear venta
          </button>
          <button type="submit" class="btn btn-primary ${modoEditar ? '' : 'd-none'}" name="action" value="actualizarVenta">
            Editar venta
          </button>
          <a class="btn btn-primary float-right" href="/producto/lista.jsp">
            Cancelar
          </a>
        </div>
      </form>

    </div>
  </div>

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
  <!-- Bootstrap -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

  <script src="/scripts/validarBootstrap.js"></script>
  <script src="/scripts/crearVenta.js"></script>
  <script>
    // Procesar errores server-side
    <c:if test="${errVentaExiste}">
      $('#ventaExiste').removeClass('d-none');
      $('#iId').focus();
      $('#iId').select();
    </c:if>
  </script>

</body>

</html>
