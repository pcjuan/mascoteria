/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.mascoteria2018.controller;

import cl.inacap.mascoteria2018.beans.ServiceBeanLocal;
import cl.inacap.mascoteria2018.entities.Categoria;
import cl.inacap.mascoteria2018.entities.Producto;
import cl.inacap.mascoteria2018.entities.Usuario;
import cl.inacap.mascoteria2018.utils.Hash;
import cl.inacap.mascoteria2018.utils.NumberUtils;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author alumnossur
 */
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "Controller", urlPatterns = {"/control.do"})
public class Controller extends HttpServlet {

    @EJB
    private ServiceBeanLocal servicio;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String boton = request.getParameter("btn");

        switch (boton) {
            case "login":
                this.login(request, response);
                break;
            case "agregarCategoria":
                this.agregarCategoria(request, response);
                break;
            case "agregarProducto":
                this.agregarProducto(request, response);
                break;
            default:
                break;
        }
    }

    private void agregarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String s_precio = request.getParameter("precio");
        String s_unidad = request.getParameter("unidad");
        String descripcion = request.getParameter("descripcion");
        String s_idCategoria = request.getParameter("idCategoria");

        InputStream stream = null;
        Part foto = request.getPart("foto");

        if (foto != null) {
            stream = foto.getInputStream();
        }

        String errores = "";

        if (nombre.isEmpty()) {
            errores = errores.concat("- Falta Nombre<br>");
        }
        if (descripcion.isEmpty()) {
            errores = errores.concat("- Falta Descripcion<br>");
        }
        if (!NumberUtils.isNumber(s_precio)) {
            errores = errores.concat("- Falta Precio<br>");
        }
        if (!NumberUtils.isNumber(s_unidad)) {
            errores = errores.concat("- Falta Unidad<br>");
        }

        if (errores.isEmpty()) {

            Categoria categoria = this.servicio.buscarCategoria(Integer.parseInt(s_idCategoria));
            Producto producto = new Producto();

            producto.setNombreProducto(nombre);
            producto.setPrecioProducto(Integer.parseInt(s_precio));
            producto.setUnidadesProducto(Integer.parseInt(s_unidad));
            producto.setDescripcionProducto(descripcion);
            producto.setCategoria(categoria);
            
            if(stream != null){
                producto.setFotoProducto(IOUtils.toByteArray(stream));
            }

            this.servicio.guardar(producto);
            categoria.getProductoList().add(producto);
            this.servicio.sincronizar(categoria); //se sincroniza para actualizar el nuevo producto asociado a la categoria

            request.setAttribute("tipo", 2);
            request.setAttribute("msg", "Producto creado con exito");

        } else {
            request.setAttribute("tipo", 1);
            request.setAttribute("msg", errores);
        }

        request.getRequestDispatcher("producto.jsp").forward(request, response);

    }

    private void agregarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombreCategoria = request.getParameter("nombreCategoria");

        if (nombreCategoria != null & !nombreCategoria.isEmpty()) {

            Categoria categoria = new Categoria();
            categoria.setNombreCategoria(nombreCategoria);

            this.servicio.guardar(categoria);

            request.setAttribute("msg", "Categoria creada satisfactoriamente");
            request.setAttribute("tipo", 2);
            request.getRequestDispatcher("categoria.jsp").forward(request, response);

        } else {
            request.setAttribute("tipo", 1);
            request.setAttribute("msg", "Favor ingrese un nombre de categoria valido");
            request.getRequestDispatcher("categoria.jsp").forward(request, response);
        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rut = request.getParameter("rut");
        String clave = request.getParameter("clave");

        if (!rut.isEmpty() && !clave.isEmpty()) {

            Usuario usuario = this.servicio.iniciarSesion(rut, Hash.md5(clave));

            if (usuario != null) {

                if (usuario.getPerfil().getNombrePerfil().equalsIgnoreCase("Administrador")) {
                    request.getSession().setAttribute("admin", usuario);
                } else {
                    request.getSession().setAttribute("person", usuario);
                }

                response.sendRedirect("inicio.jsp");

            } else {
                request.setAttribute("tipo", 1);
                request.setAttribute("mgs", "Usuario rut: " + rut + " no encontrado");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("tipo", 1);
            request.setAttribute("mgs", "Favor ingrese todos los campos");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
