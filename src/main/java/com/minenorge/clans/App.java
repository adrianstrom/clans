package com.minenorge.clans;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.hibernate.cfg.Configuration;

public class App 
{
    public static void main( String[] args )
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CRM");
        EntityManager em = emf.createEntityManager();


        Configuration con = new Configuration().configure("hibernate");
    }
}
