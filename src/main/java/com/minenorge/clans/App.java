package com.minenorge.clans;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class App 
{
    public static void main( String[] args )
    {
        Configuration con = new Configuration().configure("hibernate");
    }
}
