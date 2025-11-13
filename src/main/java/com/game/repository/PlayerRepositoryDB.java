package com.game.repository;

import com.game.entity.Player;
import jakarta.persistence.PreRemove;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository(value = "db")
public class PlayerRepositoryDB implements IPlayerRepository {

    private final SessionFactory sessionFactory;

     public PlayerRepositoryDB() {

           sessionFactory = new Configuration().addAnnotatedClass(Player.class).buildSessionFactory();

    }

    @Override
    public List<Player> getAll(int pageNumber, int pageSize) {

        try (Session session = sessionFactory.openSession()) {
            NativeQuery<Player> nativeQuery = session.createNativeQuery("SELECT * FROM rpg.player");
            nativeQuery.setFirstResult(pageNumber * pageSize);
            nativeQuery.setMaxResults(pageSize);
            return nativeQuery.list();
        }

    }

    @Override
    public int getAllCount() {

        try (Session session = sessionFactory.openSession()) {
            NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM rpg.player");

            return nativeQuery.getResultList().size();
        }

    }

    @Override
    public Player save(Player player) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(player);
            session.getTransaction().commit();
            return player;
        }

    }

    @Override
    public Player update(Player player) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(player);
            session.getTransaction().commit();
            return player;
        }

    }

    @Override
    public Optional<Player> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Player player) {

    }

   @PreRemove
    public void beforeStop() {
        sessionFactory.close();

    }
}