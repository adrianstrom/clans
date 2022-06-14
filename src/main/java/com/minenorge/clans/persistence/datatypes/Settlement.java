package com.minenorge.clans.persistence.datatypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "SETTLEMENT")
@Table(name = "SETTLEMENT")
public class Settlement extends Location {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "SettlementId")
    public int settlementId;

    @Column(name = "Name")
    public String name;

    @ManyToOne
	private Clan clan;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }
}
