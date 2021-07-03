<%--
    Document   : login
    Author     : MisaoDev <{@link https://github.com/MisaoDev}>
--%>

<%@page import="dao.UsuarioDAO"%>
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
  pageContext.setAttribute("listaUsuarios", new UsuarioDAO().getList());
%>

<body>
  <%-- Variable para determinar link activo en la barra de navegación --%>
  <c:set var="activePage" value="usuarios" />
  <%-- Barra de navegación --%>
  <%@include file="/navbar.jsp" %>

  <form action="/UsuarioController" method="post" novalidate>
    <nav class="navbar sticky-top navbar-light bg-light align-right">
      <div class="form-inline w-100">
        <button type="submit" name="action" value="nuevo" class="btn btn-primary mr-auto">Nuevo</button>
        <div class="form-inline">
          <button type="submit" name="action" value="editar" class="btn btn-primary mr-sm-2">Editar</button>
          <button type="submit" name="action" value="eliminar" class="btn btn-primary">
            Eliminar
          </button>
        </div>
      </div>
    </nav>

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
        </colgroup>
        <thead class="thead-dark">
          <tr>
            <th scope="col">
              <%-- <div class="form-check">
                <input type="checkbox" id="selectAll" class="form-check-input position-static" />
              </div> --%>
            </th>
            <c:forTokens items = "&nbsp;,Usuario,Tipo,RUT,Nombre,Apellidos,F.nacimiento,Correo" delims = "," var = "texto">
              <th scope="col">${texto}</th>
            </c:forTokens>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="us" items="${listaUsuarios}">
            <tr>
              <td class="align-middle">
                <div class="form-check">
                  <input type="checkbox" name="cUsername" value="${us.username}"
                    class="form-check-input position-static product-checkbox" />
                </div>
              </td>
              <td class="align-middle"><img src="/img/users/${us.nombreFoto}" alt="" class="img-thumbnail foto-usuario-sm" /></td>
              <td class="align-middle">${us.username}</td>
              <td class="align-middle">${us.tipo.getNombre()}</td>
              <td class="align-middle">${us.rut}</td>
              <td class="align-middle">${us.nombre}</td>
              <td class="align-middle">${us.apellidoPaterno} ${us.apellidoMaterno}</td>
              <td class="align-middle">${us.fechaNacimiento}</td>
              <td class="align-middle">${us.email}</td>
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
