package com.minenorge.clans.persistence;

import java.util.List;
import java.util.UUID;

import com.minenorge.clans.persistence.datatypes.Clan;
import com.minenorge.clans.persistence.datatypes.ClanPlayer;
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
        private static final EntityManager ENTITY_MANAGER = new JpaEntityManagerFactory(new Class[] {Clan.class, ClanPlayer.class}).getEntityManager();
    }
    
    public static EntityManager getJpaEntityManager() {
        return EntityManagerHolder.ENTITY_MANAGER;
    }

    public ClanPlayer getPlayerByPlayerId(UUID id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<ClanPlayer> criteria = builder.createQuery(ClanPlayer.class);
        Root<ClanPlayer> root = criteria.from(ClanPlayer.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("playerUniqueId"), id));
        List<ClanPlayer> players = em.createQuery(criteria).getResultList();
        if(players.isEmpty()) {
            return null;
        }
        return players.get(0);
    }

    public Clan getClanByName(String name) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Clan> criteria = builder.createQuery(Clan.class);
        Root<Clan> root = criteria.from(Clan.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("name"), name));
        List<Clan> clans = em.createQuery(criteria).getResultList();
        if(clans.isEmpty()) {
            return null;
        }
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
        em.persist(obj);
    }

    public <T> T get(Class<T> type, int id) {
        return em.find(type, id);
    }
}