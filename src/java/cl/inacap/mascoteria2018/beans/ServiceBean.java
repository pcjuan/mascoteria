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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alumnossur
 */
@Stateless
public class ServiceBean implements ServiceBeanLocal {

    @PersistenceContext(unitName = "Mascoteria2018PU")
    private EntityManager em;

    @Override
    public Usuario iniciarSesion(String rut, String clave) {
        try {
            return (Usuario) this.em.createNamedQuery("Usuario.iniciarSesion", Usuario.class)
                    .setParameter("rutUser", rut)
                    .setParameter("claveUser", clave)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void guardar(Object obj) {
        this.em.persist(obj);
    }

    @Override
    public List<Categoria> getCategorias() {
        return (List<Categoria>) this.em.createNamedQuery("Categoria.findAll").getResultList();
    }

    @Override
    public List<Producto> getProductos() {
        return (List<Producto>) this.em.createNamedQuery("Producto.findAll").getResultList();
    }

    @Override
    public void sincronizar(Object obj) {
        this.em.merge(obj);
        this.em.flush();
    }

    @Override
    public Categoria buscarCategoria(int id) {
        return (Categoria) this.em.find(Categoria.class, id);
    }
}
