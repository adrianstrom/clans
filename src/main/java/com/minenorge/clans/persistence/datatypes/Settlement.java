package com.minenorge.clans.persistence.datatypes;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Settlement extends Location {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "SettlementId")
    public int settlementId;

    @Column(name = "Name")
    public String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
