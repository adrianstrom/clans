package com.minenorge.clans.persistence.datatypes;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.hibernate.annotations.Where;

import com.minenorge.utils.Utils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity(name = "CLAN")
@Table(name = "CLAN")
@Where(clause = "deleted = false")
public class Clan extends EntityBase {
    public Clan() { }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @Column(name = "Name")
    private String name;

    private Location location;

    @OneToMany(mappedBy = "clan")
    private List<Settlement> settlements = new ArrayList<>(); 

    @OneToOne
    @JoinColumn(name = "LeaderId", referencedColumnName = "playerUniqueId")
    private ClanPlayer leader;

    @OneToOne
    @JoinColumn(name = "EnemyId", referencedColumnName = "id")
    private Clan enemy;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ClanPlayer> invitedPlayers = new ArrayList<>();

    @OneToMany(mappedBy = "clan")
    private List<ClanPlayer> players = new ArrayList<>();

    @Column(name = "Name")
    public String getName() {
        return this.name;
    }

    public List<ClanPlayer> getInvitedPlayers() {
        return this.invitedPlayers;
    }

    public void invitePlayer(ClanPlayer player) {
        invitedPlayers.add(player);
        player.getClanInvitations().add(this);
    }

    public void removeInvitation(ClanPlayer player) {
        invitedPlayers.remove(player);
        player.getClanInvitations().remove(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSettlement(Settlement settlement) {
        settlements.add(settlement);
        settlement.setClan(this);
    }

    public void removeSettlement(Settlement settlement) {
        settlements.remove(settlement);
        settlement.setClan(null);
    }

    public List<Settlement> getSettlements() {
        return this.settlements;
    }

    public ClanPlayer getLeader() {
        return this.leader;
    }

    public String getLeaderDisplayName() {
        return this.getLeader().getPlayer().getDisplayName();
    }

    public void setLeader(ClanPlayer leader) {
        this.leader = leader;
    }

    public List<ClanPlayer> getPlayers() {
        List<ClanPlayer> players = new ArrayList<>();
        for (ClanPlayer player : this.players) {
            players.add(player);
        }
        return players;
    }

    public void addPlayer(ClanPlayer player) {
        players.add(player);
        player.setClan(this);
    }

    public void removePlayer(ClanPlayer player) {
        players.remove(player);
        player.setClan(null);
    }

    public void broadcastMessage(String message, ClanPlayer except) {
        for (ClanPlayer clanPlayer : players) {
            if(!except.getPlayer().getUniqueId().equals(clanPlayer.getPlayer().getUniqueId())) {
                clanPlayer.getPlayer().sendMessage(message);
            }
        }
    }

    public void broadcastMessage(String message) {
        for (ClanPlayer clanPlayer : players) {
            clanPlayer.getPlayer().sendMessage(message);
        }
    }

    private String getFormattedPlayers() {
        String formattedNames = "";
        for (ClanPlayer clanPlayer : players) {
            String playerName = clanPlayer.getDisplayName();
            formattedNames += playerName + ", ";
        }
        formattedNames.substring(0, formattedNames.length() - 4);
        return formattedNames;
    }

    private String getClanSpawnCoordinates(Location loc) {
        if(loc == null) {
            return "Klanen har ikke satt et basespawn";
        }
        return (int)this.location.getX() + ", " +  (int)this.location.getY() + ", " + (int)this.location.getZ();
    }

    public String getClanInfo() {
		String formattedString = 
        Utils.chat("&8---------------------[ &c" + this.name + " &8]---------------------\n" +
        "&7Leder: &f" + this.getLeader().getDisplayName() + "\n" +
        "&7Medlemmer: &f" + getFormattedPlayers() + "\n" +
        "&7Base: &f(" + getClanSpawnCoordinates(location) + ")" + "\n" +
        "&7Dager gammel: &f" + (Duration.between(getDateCreated(), Instant.now()).toDays()) + "\n" +
        "&7Fiende: &f" + "kommer");
		return formattedString;
	}

    public String getFormattedSettlements() {
        String formattedNames = "";
        for (Settlement settlement : settlements) {
            String name = settlement.name;
            formattedNames += name + ", ";
        }
        formattedNames.substring(0, formattedNames.length() - 4);
        return formattedNames;
    }
}