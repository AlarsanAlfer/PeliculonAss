package org.example.conjuntofxhibernate.dao;

import org.example.conjuntofxhibernate.models.Copia;
import org.example.conjuntofxhibernate.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {
    private final SessionFactory sessionFactory;

    public UsuarioDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Copia> copiasUsuario(Long idUsuario) {
        try(Session session = sessionFactory.getCurrentSession()){
            return session.createQuery("FROM Copia WHERE Usuario.id = :idUsuario", Copia.class)
                    .setParameter("idUsuario", idUsuario)
                    .list();
        }


    }

    public Usuario validar(String user, String pass) {
        try(Session session = sessionFactory.openSession()) {
            String hql = "SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.contrasena = :contrasena";
            Query<Usuario> query = session.createQuery(hql, Usuario.class);
            query.setParameter("nombre", user);
            query.setParameter("contrasena", pass);

            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error al validar usuario", e);
        }
    }

    @Override
    public List<Usuario> findAll() {
        return List.of();
    }

    @Override
    public Usuario findById(Long id) {
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT u FROM Usuario u WHERE u.id = :idUsuario";
            Query query = session.createQuery(hql);
            query.setParameter("idUsuario", id);
            return (Usuario) query.uniqueResult();
        }
    }

    @Override
    public void save(Usuario usuario) {

    }

    @Override
    public void update(Usuario usuario) {

    }

    @Override
    public void delete(Usuario usuario) {

    }
}
