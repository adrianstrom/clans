package com.minenorge.clans.persistence.datatypes;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "PLAYER")
@Table(name = "PLAYER")
@Where(clause = "deleted = false")
public class ClanPlayer extends EntityBase {
    @Transient
    private Player player;

    @Id
    @Column(name = "PlayerUniqueId")
    private UUID playerUniqueId;
    
    @ManyToOne
    private Clan clan;

    public ClanPlayer(Player player) {
        this.player = player;
    }

    public void setPlayerUniqueId(UUID id) {
        this.playerUniqueId = id;
    }

    public Clan getClan() {
        return this.clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }
}