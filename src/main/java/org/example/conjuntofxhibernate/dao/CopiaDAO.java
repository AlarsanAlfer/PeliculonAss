package org.example.conjuntofxhibernate.dao;

import jakarta.persistence.Query;
import org.example.conjuntofxhibernate.models.Copia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class CopiaDAO implements DAO<Copia> {
    private final SessionFactory sessionFactory;

    public CopiaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Copia> copiasUsuario(Long idUsuario) {
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT c.id , c.id_pelicula, c.estado, c.soporte FROM Copia c WHERE c.id_usuario = :idUsuario";
            Query query = session.createQuery(hql);
            query.setParameter("idUsuario", idUsuario);

            List<Copia> copias = new ArrayList<>();
            List<Object[]> resultados = query.getResultList();

            for (Object[] fila : resultados) {
                Long id = (Long) fila[0];
                Long idPelicula = (Long) fila[1];
                String estado = (String) fila[2];
                String soporte = (String) fila[3];

                String titulo = obtenerTitulo(idPelicula);

                Copia copia = new Copia();
                copia.setId(id);
                copia.setId_pelicula(idPelicula);
                copia.setEstado(estado);
                copia.setSoporte(soporte);
                copia.setTitulo(titulo);

                copias.add(copia);
            }
            return copias;
        }
    }

    public String obtenerTitulo(Long idPelicula) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT p.titulo FROM Pelicula p WHERE p.id = :idPelicula";
            Query query = session.createQuery(hql);
            query.setParameter("idPelicula", idPelicula);
            return (String) query.getSingleResult();
        }
    }

    @Override
    public List<Copia> findAll() {
        return List.of();
    }

    @Override
    public Copia findById(Long id) {
        return null;
    }

    @Override
    public void save(Copia copia) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            String hql = "INSERT INTO Copia (id_pelicula, id_usuario, estado, soporte) VALUES (:id_pelicula, :id_usuario, :estado, :soporte)";
            Query query = session.createQuery(hql);
            query.setParameter("id_pelicula", copia.getId_pelicula());
            query.setParameter("id_usuario", copia.getId_usuario());
            query.setParameter("estado", copia.getEstado());
            query.setParameter("soporte", copia.getSoporte());
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Copia copia) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            String hql = "UPDATE Copia c SET c.estado = :copiaEstado, c.soporte = :copiaSoporte WHERE c.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("copiaEstado", copia.getEstado());
            query.setParameter("copiaSoporte", copia.getSoporte());
            query.setParameter("id", copia.getId());
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

//    @Override
//    public void delete(Copia copia) {
//        try(Session session = sessionFactory.openSession()){
//            session.beginTransaction();
//            session.remove(copia);
//            session.getTransaction().commit();
//        }
//    }
@Override
public void delete(Copia copia) {
    try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();
        String hql = "DELETE FROM Copia WHERE id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", copia.getId());
        System.out.println(copia.getId());

        int result = query.executeUpdate(); // Para comprobar si de vd la esta borrando de la base de datos
        if (result > 0) {
            System.out.println("La copia ha sido eliminada exitosamente.");
        } else {
            System.out.println("No se encontró ninguna copia con el ID proporcionado.");
        }
        session.getTransaction().commit();
    }
}
}
