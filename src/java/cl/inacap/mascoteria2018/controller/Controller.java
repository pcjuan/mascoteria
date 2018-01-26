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
            case "editarUsuario":
                this.editarUsuario(request, response);
                break;
            default:
                break;
        }
    }

    private void editarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rut = request.getParameter("rut");
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");
        String confirmarClave = request.getParameter("confirmarClave");

        String errores = "";

        if (correo.isEmpty()) {
            errores = errores.concat("Favor ingrese el correo<br>");
        }
        if (clave.isEmpty()) {
            errores = errores.concat("Favor ingrese la clave<br>");
        }
        if (confirmarClave.isEmpty()) {
            errores = errores.concat("Favor confirme clave<br>");
        }
        if ((!clave.isEmpty() && !confirmarClave.isEmpty()) && !clave.equals(confirmarClave)) {
            errores = errores.concat("Las claves debem ser iguales<br>");
        }

        if (errores.isEmpty()) {

            /**
             * Opcion 1: Usar el mismo objecto Usuario de la session del cual se
             * obtuvieron los datos para mostrar en el jsp
             */
            Usuario usuario = null;

            if (request.getSession().getAttribute("admin") != null) {
                usuario = (Usuario) request.getSession().getAttribute("admin");
            } else {
                usuario = (Usuario) request.getSession().getAttribute("person");
            }

            /**
             * Opcion 2: Buscar en base de datos el usuario en base al rut
             */
            //usuario = this.servicio.buscarUsuario(rut);
            if (usuario != null) {

                usuario.setEmailUser(correo);
                usuario.setClave(Hash.md5(clave));
                this.servicio.sincronizar(usuario);

                /**
                 * Se vuelve a asignar el atrubuto admin/person para actualizar
                 * los datos del usuario en la sesion, se comento por que se
                 * soluciona con la referencia sobre el objeto usuario de la
                 * sesion y su anterior actualizacion en db
                 */
//                if (usuario.getPerfil().getNombrePerfil().equals("administrador")) {
//                    request.getSession().setAttribute("admin", usuario);
//                } else {
//                    request.getSession().setAttribute("person", usuario);
//                }
                request.setAttribute("tipo", 2);
                request.setAttribute("msg", "Datos editados con exito!!");

            } else {
                errores = errores.concat("ERROR INESPERADO AL BUSCAR EL USUARIO<BR>");
                request.setAttribute("tipo", 1);
                request.setAttribute("msg", errores);
            }

        } else {
            request.setAttribute("tipo", 1);
            request.setAttribute("msg", errores);
        }

        request.getRequestDispatcher("misDatos.jsp").forward(request, response);
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

            if (stream != null) {
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

        /**
         * Cerrar sesiones anteriores
         */
        request.getSession().setAttribute("admin", null);
        request.getSession().setAttribute("person", null);

        String rut = request.getParameter("rut");
        String clave = request.getParameter("clave");
        String errores = "";

        if (rut.isEmpty()) {
            errores = errores.concat("Favor ingresar rut<br>");
        }
        if (clave.isEmpty()) {
            errores = errores.concat("Favor ingresar clave<br>");
        }

        if (errores.isEmpty()) {

            Usuario usuario = this.servicio.iniciarSesion(rut, Hash.md5(clave));

            if (usuario != null) {

                if (usuario.getPerfil().getNombrePerfil().equalsIgnoreCase("Administrador")) {
                    request.getSession().setAttribute("admin", usuario);
                } else {
                    request.getSession().setAttribute("person", usuario);
                }

            } else {
                errores = errores.concat("Problemas para loguear, verifique sus credenciales de acceso<br>");
            }
        }

        if (!errores.isEmpty()) {
            request.setAttribute("tipo", 1);
            request.setAttribute("msg", errores);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            response.sendRedirect("inicio.jsp");
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
