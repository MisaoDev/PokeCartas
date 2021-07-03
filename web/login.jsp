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

  <link rel="stylesheet" href="styles/login.css" />
  <link rel="stylesheet" href="styles/main.css" />
  <link rel="shortcut icon" type="image/png" href="img/favicon.png" />

  <!-- Bootstrap (customized) -->
  <link rel="stylesheet" href="styles/bootstrap.css">
  <link href="open-iconic/font/css/open-iconic-bootstrap.css" rel="stylesheet">
</head>

<body>
  <%-- Variable para determinar link activo en la barra de navegación --%>
  <c:set var="activePage" value="index"/>
  <%-- Barra de navegación --%>
  <%@include file="navbar.jsp" %>

  <%-- divs para el fondo --%>
  <div id="background" class="bg-image">
    <div class="mask d-flex h-100" style="background-color: rgba(0, 0, 0, 0.4);">

      <form class="form-signin" action="LoginController" method="post">
        <div class="jumbotron mt-4 pb-4 opacity-4">
          <h1 class="mb-4 text-center">Login</h1>
          <label class="text-center w-100 ${message == null ? 'd-none' : 'd-inline-block'}">
            <span class="oi oi-warning text-warning"></span> ${message}
          </label>
          <div class="form-label-group">
            <input type="text" name="iUsername" id="iUsername" class="form-control" placeholder="Nombre de usuario" />
            <label for="iUsername">Nombre de usuario</label>
          </div>
          <div class="form-label-group">
            <input type="password" name="iPassword" id="iPassword" class="form-control" placeholder="Contraseña" />
            <label for="iPassword">Contraseña</label>
          </div>
          <br />
          <button type="submit" class="btn btn-primary"
            name="action" value="login">Iniciar sesión</button>
          <button type="submit" class="btn btn-primary float-right"
            name="action" value="register">Registrarse</button>
        </div>
      </form>

    </div>
  </div>

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
  <!-- Bootstrap -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>

</html>
