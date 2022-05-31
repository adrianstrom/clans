package com.minenorge.clans.persistence.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "PLAYER")
@Table(name = "PLAYER")
@Where(clause = "deleted = false")
public class Player extends EntityBase {
    @Id
    @Column(name = "PlayerId")
    private int playerId;

    @Column(name = "PlayerUniqueId")
    private UUID playerUniqueId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Clan> memberOfClans = new ArrayList<>();
}
