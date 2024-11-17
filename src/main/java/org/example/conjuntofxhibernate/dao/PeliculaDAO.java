package org.example.conjuntofxhibernate.dao;

import org.example.conjuntofxhibernate.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class PeliculaDAO implements DAO<Pelicula>{
    private final SessionFactory sessionFactory;

    public PeliculaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Pelicula findByTitle(String title) {
        try(Session session = sessionFactory.openSession()) {
            String hql = "FROM Pelicula p WHERE p.titulo = :titulo";
            Query<Pelicula> query = session.createQuery(hql, Pelicula.class);
            query.setParameter("titulo", title);
            return query.uniqueResult();
        }
    }

    public List<String> findNames() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select p.titulo from Pelicula p").list();
        }
    }

    @Override
    public List<Pelicula> findAll() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("select p from Pelicula p", Pelicula.class).list();
        }
    }

    @Override
    public Pelicula findById(Long id) {
        return null;
    }

    @Override
    public void save(Pelicula pelicula) {
        try(Session session = sessionFactory.openSession()){
            session.save(pelicula);
//            String hql = "INSERT INTO Copia (titulo, genero, anio, descripcion, director) VALUES (:titulo, :genero, :anio, :descripcion, :director)";
//            Query query = session.createQuery(hql);
//            query.setParameter("titulo", pelicula.getTitulo());
//            query.setParameter("genero", pelicula.getGenero());
//            query.setParameter("anio", pelicula.getAnio());
//            query.setParameter("descripcion", pelicula.getDescripcion());
//            query.setParameter("director", pelicula.getDirector());
//            query.executeUpdate();

        }

    }

    @Override
    public void update(Pelicula pelicula) {

    }

    @Override
    public void delete(Pelicula pelicula) {

    }
}
