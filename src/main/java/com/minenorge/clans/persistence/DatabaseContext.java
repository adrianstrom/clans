package com.minenorge.clans.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseContext {
    private EntityManagerFactory emf;
    private EntityManager em;

    public DatabaseContext() {
        emf = Persistence.createEntityManagerFactory("CRM");
        em = emf.createEntityManager();
    }

    public void create(Object obj) {
        em.persist(obj);
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