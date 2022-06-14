package com.minenorge.clans.persistence.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "PLAYER")
@Table(name = "PLAYER")
@Where(clause = "deleted = false")
public class ClanPlayer extends EntityBase {
    public ClanPlayer() {}

    @Transient
    private Player player;

    @Id
    @Column(name = "PlayerUniqueId")
    private UUID playerUniqueId;
    
    @ManyToOne
    @JoinColumn(name="ClanId", nullable=false)
    private Clan clan;

    @ManyToMany(mappedBy = "invitedPlayers")
    private List<Clan> clanInvitations = new ArrayList<>();

    public ClanPlayer(Player player) {
        this.player = player;
    }

    public String getDisplayName() {
        return this.getPlayer().getDisplayName();
    }

    public void setPlayerUniqueId(UUID id) {
        this.playerUniqueId = id;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Clan getClan() {
        return this.clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }
    
    public List<Clan> getClanInvitations() {
        return this.clanInvitations;
    }
}