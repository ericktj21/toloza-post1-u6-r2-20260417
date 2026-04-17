package com.universidad.mvc.controller;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.service.ProductoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoService service = new ProductoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null || accion.isBlank()) {
            accion = "listar";
        }

        try {
            switch (accion) {
                case "formulario" -> mostrarFormulario(req, resp, null);
                case "editar" -> editar(req, resp);
                case "eliminar" -> eliminar(req, resp);
                default -> listar(req, resp);
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = parseEntero(req.getParameter("id"), 0);
        Producto producto = extraerProducto(req, id);

        try {
            if (id > 0) {
                service.actualizar(producto);
                resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+actualizado");
            } else {
                service.guardar(producto);
                resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+guardado+exitosamente");
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("producto", producto);
            req.getRequestDispatcher("/WEB-INF/views/formulario.jsp").forward(req, resp);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productos", service.obtenerTodos());
        req.setAttribute("mensaje", req.getParameter("mensaje"));
        req.getRequestDispatcher("/WEB-INF/views/lista.jsp").forward(req, resp);
    }

    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp, Producto producto)
            throws ServletException, IOException {
        req.setAttribute("producto", producto == null ? new Producto() : producto);
        req.getRequestDispatcher("/WEB-INF/views/formulario.jsp").forward(req, resp);
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = parseEntero(req.getParameter("id"), -1);
        Producto producto = service.obtenerPorId(id);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
        mostrarFormulario(req, resp, producto);
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parseEntero(req.getParameter("id"), -1);
        service.eliminar(id);
        resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+eliminado");
    }

    private Producto extraerProducto(HttpServletRequest req, int id) {
        return new Producto(
                id,
                req.getParameter("nombre"),
                req.getParameter("categoria"),
                parseDouble(req.getParameter("precio"), 0),
                parseEntero(req.getParameter("stock"), 0)
        );
    }

    private int parseEntero(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private double parseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
