<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-md navbar-dark bg-primary sticky-top">
  <a class="navbar-brand" href="/">
    <img src="/img/favicon.png" alt="PokeCartas" width="50" height="50" />
    PokeCartas
  </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarGroup"
    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarGroup">
    <div class="navbar-nav mr-auto">
      <a href="/tienda.jsp" class="nav-link
        ${user.tipo.isCanViewProducts() ? '' : 'd-none'}
        ${activePage == 'tienda' ? 'active' : ''}">Tienda</a>
      <a href="/producto/lista.jsp" class="nav-link
        ${user.tipo.isCanEditProducts() ? '' : 'd-none'}
        ${activePage == 'productos' ? 'active' : ''}">Productos</a>
      <a href="/venta/lista.jsp" class="nav-link
        ${user.tipo.isCanEditOrders() ? '' : 'd-none'}
        ${activePage == 'ventas' ? 'active' : ''}">Ventas</a>
      <a href="/usuario/lista.jsp" class="nav-link
        ${user.tipo.isCanEditUsers() ? '' : 'd-none'}
        ${activePage == 'usuarios' ? 'active' : ''}">Usuarios</a>
      <a href="/reportes.jsp" class="nav-link
        ${user.tipo.isCanViewReports() ? '' : 'd-none'}
        ${activePage == 'reportes' ? 'active' : ''}">Reportes</a>
    </div>

    <span class="navbar-nav">
      <c:choose>

        <%-- Link para iniciar sesión --%>
        <c:when test="${user.tipo == 'INVITADO'}">
          <a href="/login.jsp" class="nav-link">Iniciar sesión</a>
        </c:when>

        <%-- Dropdown de la cuenta de usuario --%>
        <c:otherwise>
          <a href="" class="nav-link dropdown" id="navbarAccount" role="button"
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            ${user.username}
          </a>
          <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarAccount">
            <a href="" class="dropdown-item">&emsp;</a>
            <a href="" class="dropdown-item">&emsp;</a>
            <a href="" class="dropdown-item">&emsp;</a>
            <div class="dropdown-divider"></div>
            <a href="/LoginController?action=logout" class="dropdown-item">Cerrar sesión</a>
          </div>
        </c:otherwise>

      </c:choose>
    </span>

  </div>
</nav>
