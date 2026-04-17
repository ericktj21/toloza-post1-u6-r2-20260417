<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>${empty producto.id or producto.id == 0 ? 'Nuevo Producto' : 'Editar Producto'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<h1>${empty producto.id or producto.id == 0 ? 'Registrar Producto' : 'Editar Producto'}</h1>

<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<form method="post" action="<c:url value='/productos' />" class="form-grid">
    <c:if test="${not empty producto.id and producto.id > 0}">
        <input type="hidden" name="id" value="${producto.id}">
    </c:if>

    <label for="nombre">Nombre</label>
    <input id="nombre" type="text" name="nombre" required value="${producto.nombre}">

    <label for="categoria">Categoria</label>
    <input id="categoria" type="text" name="categoria" required value="${producto.categoria}">

    <label for="precio">Precio</label>
    <input id="precio" type="number" min="0" step="0.01" name="precio" required value="${producto.precio}">

    <label for="stock">Stock</label>
    <input id="stock" type="number" min="0" name="stock" required value="${producto.stock}">

    <button type="submit">${empty producto.id or producto.id == 0 ? 'Guardar' : 'Actualizar'}</button>
    <a href="<c:url value='/productos' />">Cancelar</a>
</form>
</body>
</html>
