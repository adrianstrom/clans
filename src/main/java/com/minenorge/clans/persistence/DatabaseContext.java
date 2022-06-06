package com.minenorge.clans.persistence;

import java.util.List;

import com.minenorge.clans.persistence.datatypes.Clan;
import com.minenorge.clans.persistence.datatypes.Player;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class DatabaseContext {
    private EntityManager em;

    public DatabaseContext() {
        em = getJpaEntityManager();
    }

    private static class EntityManagerHolder {
        private static final EntityManager ENTITY_MANAGER = new JpaEntityManagerFactory(new Class[] {Clan.class, Player.class}).getEntityManager();
    }
    
    public static EntityManager getJpaEntityManager() {
        return EntityManagerHolder.ENTITY_MANAGER;
    }

    public Clan getClanByName(String name) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Clan> criteria = builder.createQuery(Clan.class);
        Root<Clan> root = criteria.from(Clan.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("name"), name));
        List<Clan> clans = em.createQuery(criteria).getResultList();
        return clans.get(0);
    }

    public boolean create(Object obj) {
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        return true;
    }

    public void remove(Object obj) {
        em.remove(obj);
    }

    public void update(Object obj) {
        em.refresh(obj);
    }

    public <T> T get(Class<T> type, int id) {
        return em.find(type, id);
    }
}