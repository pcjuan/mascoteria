/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.mascoteria2018.beans;

import cl.inacap.mascoteria2018.entities.Categoria;
import cl.inacap.mascoteria2018.entities.Producto;
import cl.inacap.mascoteria2018.entities.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alumnossur
 */
@Local
public interface ServiceBeanLocal {

    Usuario iniciarSesion(String rut, String clave);

    void guardar(Object obj);

    void sincronizar(Object obj);

    List<Categoria> getCategorias();

    List<Producto> getProductos();

    Categoria buscarCategoria(int id);
}
