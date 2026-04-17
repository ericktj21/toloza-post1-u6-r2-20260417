<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inventario de Productos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<h1>Inventario de Productos</h1>

<c:if test="${not empty mensaje}">
    <p class="ok">${mensaje}</p>
</c:if>

<p>
    <a href="<c:url value='/productos?accion=formulario' />">+ Nuevo Producto</a>
</p>

<table>
    <thead>
    <tr>
        <th>#</th>
        <th>Nombre</th>
        <th>Categoria</th>
        <th>Precio</th>
        <th>Stock</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="p" items="${productos}" varStatus="s">
        <tr>
            <td>${s.count}</td>
            <td>${p.nombre}</td>
            <td>${p.categoria}</td>
            <td>${p.precio}</td>
            <td>${p.stock}</td>
            <td>
                <a href="<c:url value='/productos?accion=editar&id=${p.id}' />">Editar</a> |
                <a href="<c:url value='/productos?accion=eliminar&id=${p.id}' />"
                   onclick="return confirm('Seguro que deseas eliminar este producto?')">Eliminar</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
