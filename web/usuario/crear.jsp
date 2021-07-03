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
  <link href="/open-iconic/font/css/open-iconic-bootstrap.css" rel="stylesheet">
</head>

<body>
  <%-- Variable para determinar link activo en la barra de navegación --%>
  <c:set var="activePage" value="usuarios" />
  <%-- Barra de navegación --%>
  <%@include file="/navbar.jsp" %>

  <div class="container">
    <div class="jumbotron mt-4 py-4">

      <h1 class="text-center mb-4">
        ${modoEditar ? 'Editar el usuario' : 'Crear nuevo usuario'}
      </h1>

      <form action="/UsuarioController" class="needs-validation" novalidate method="post" enctype="multipart/form-data">

        <legend class="mb-3">Datos personales</legend>

        <div class="form-row">
          <div class="form-group col-md-3">
            <label for="iRut">RUT</label>
            <input type="text" name="iRut" id="iRut" class="form-control" value="${iRut}" />
            <div class="valid-feedback">Todo bien</div>
          </div>
          <div class="form-group col-md-9">
            <label for="iNombre" class="req">Nombre</label>
            <input type="text" name="iNombre" id="iNombre" class="form-control" required value="${iNombre}" />
            <div class="valid-feedback">Todo bien</div>
            <div class="invalid-feedback">Ingresa un nombre</div>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group col-md-6">
            <label for="iApellido1">Apellido paterno</label>
            <input type="text" name="iApellido1" id="iApellido1" class="form-control" value="${iApellido1}" />
            <div class="valid-feedback">Todo bien</div>
          </div>
          <div class="form-group col-md-6">
            <label for="iApellido2">Apellido materno</label>
            <input type="text" name="iApellido2" id="iApellido2" class="form-control" value="${iApellido2}" />
            <div class="valid-feedback">Todo bien</div>
          </div>
        </div>

        <div class="form-group">
          <label for="iBirthDate">Fecha de nacimiento</label>
          <input type="date" name="iBirthDate" id="iBirthDate" class="form-control" value="${iBirthDate}" />
          <div class="valid-feedback">Todo bien</div>
        </div>

        <div class="form-group">
          <label for="iEmail" class="req">Correo electrónico</label>
          <input type="email" name="iEmail" id="iEmail" class="form-control" required value="${iEmail}" />
          <div class="valid-feedback">Todo bien</div>
          <div class="invalid-feedback">Ingresa un correo válido</div>
        </div>

        <legend class="mt-4 mb-3">Datos de la cuenta</legend>

        <div class="form-group">
          <label for="iUsername" class="req">Nombre de usuario</label>
          <input type="text" name="iUsername" id="iUsername" class="form-control"
            required ${modoEditar ? 'readonly' : ''} autocomplete="off" value="${iUsername}" />
          <small class="text-danger d-none" id="usuarioExiste">Usuario ya existe.</small>
          <div class="valid-feedback">Todo bien</div>
          <div class="invalid-feedback">Ingresa un nombre de usuario válido (solo letras y números)</div>
        </div>
        <div class="form-group">
          <label for="iTipo" class="req">Nombre de usuario</label>
          <select name="iTipo" id="iTipo" class="form-control">
            <option value="" disabled selected hidden>Seleccione un tipo de usuario</option>
            <option value="CLIENTE"       ${iTipo == 'CLIENTE'       ? 'selected' : ''}>
              Cliente normal (Ver y comprar)</option>
            <option value="OPERARIO"      ${iTipo == 'OPERARIO'      ? 'selected' : ''}>
              Operario de la tienda (Productos y ventas)</option>
            <option value="ADMINISTRADOR" ${iTipo == 'ADMINISTRADOR' ? 'selected' : ''}>
              Administrador (Acceso a todos los módulos)</option>
          </select>
          <div class="valid-feedback">Todo bien</div>
          <div class="invalid-feedback">Seleccione un tipo de usuario</div>
        </div>

        <%-- <div class="form-group">
          <label for="iEmail" class="req">Correo electrónico</label>
          <input type="email" name="iEmail" id="iEmail" class="form-control" required value="${iEmail}" />
          <div class="valid-feedback">Todo bien</div>
          <div class="invalid-feedback">Ingresa un correo válido</div>
        </div> --%>

        <div class="form-group">
          <label for="iFoto">Foto</label>
          <div class="custom-file">
            <input type="file" class="custom-file-input form-control" id="iFoto" name="iFoto"
              accept="image/*">
            <label class="custom-file-label" for="iFoto" data-browse="Examinar...">
              Escoja un archivo de foto
            </label>
          </div>
        </div>

        <div class="text-center">
          <img id="imgFoto" class="img-thumbnail foto-usuario-md ${modoEditar ? '' : 'd-none'}"
            src="/img/users/${modoEditar ? iFoto : ''}" alt="" />
        </div>

        <c:if test="${!modoEditar}">
          <div class="form-row">
            <div class="form-group col-md-6">
              <label for="iPassword" class="req">Contraseña</label>
              <input type="password" name="iPassword" id="iPassword" class="form-control"
                aria-describedby="helpPassword" pattern=".{12,}" required />
              <small id="helpPassword" class="form-text text-muted">
                Debe contener al menos 12 caracteres.
              </small>
              <div class="invalid-feedback">Ingresa una contraseña válida</div>
            </div>

            <div class="form-group col-md-6">
              <label for="iPasswordConfirm" class="req">Repetir contraseña</label>
              <input type="password" name="iPasswordConfirm" id="iPasswordConfirm" class="form-control" required />
              <div class="invalid-feedback">Confirma la contraseña</div>
              <small id="passDontMatch" class="form-text"></small>
            </div>
          </div>
        </c:if>

        <div class="col mt-3 text-center">
          <button type="submit" class="btn btn-success ${modoEditar ? 'd-none' : ''}" name="action" value="crearUsuario">
            Crear usuario
          </button>
          <button type="submit" class="btn btn-primary ${modoEditar ? '' : 'd-none'}" name="action" value="actualizarUsuario">
            Editar usuario
          </button>
        </div>

      </form>

    </div>
  </div>

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
  <!-- Bootstrap -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

  <script src="/scripts/validarBootstrap.js"></script>
  <script src="/scripts/fotoUsuario.js"</script>
  <script>
    // Procesar errores server-side
    <c:if test="${errUsuarioExiste}">
      $('#usuarioExiste').removeClass('d-none');
      $('#iUsername').focus();
      $('#iUsernam').select();
    </c:if>
  </script>

</body>

</html>
