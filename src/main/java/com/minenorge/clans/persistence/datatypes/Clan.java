package com.minenorge.clans.persistence.datatypes;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.crypto.Data;

import org.bukkit.Bukkit;
import org.hibernate.annotations.Where;

import com.minenorge.clans.persistence.DatabaseContext;
import com.minenorge.utils.Utils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity(name = "CLAN")
@Table(name = "CLAN")
@Where(clause = "deleted = false")
public class Clan extends EntityBase {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @Column(name = "Name")
    private String name;

    private Location location;

    private UUID leader;

    @OneToMany(mappedBy = "clan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClanPlayer> players = new ArrayList<>();

    @Column(name = "Name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public org.bukkit.Location getLocation() {
        Location loc = this.location;
		return new org.bukkit.Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}

    public void setLocation(org.bukkit.Location location) {
        this.location = new Location();
        location.setWorld(location.getWorld());
        location.setX(location.getX());
        location.setY(location.getY());
        location.setZ(location.getZ());
        location.setPitch(location.getPitch());
        location.setYaw(location.getYaw());
    }

    public UUID getLeader() {
        return this.leader;
    }

    public ClanPlayer getLeader(DatabaseContext ctx) {
        return ctx.getPlayerByPlayerId(this.leader);
    }

    public void setLeader(UUID leader) {
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

    private String getFormattedPlayers() {
        String formattedNames = "";
        Bukkit.getLogger().info("getFormattedPlayers");
        for (ClanPlayer clanPlayer : players) {
            String playerName = clanPlayer.getPlayer().getDisplayName();
            Bukkit.getLogger().info(playerName);
            formattedNames += playerName;
        }
        Bukkit.getLogger().info("ferdig med getFormattedPlayers");
        return formattedNames;
    }

    private String getClanSpawnCoordinates(Location loc) {
        if(loc == null) {
            return "Klanen har ikke satt et basespawn";
        }
        return this.location.getX() + ", " +  this.getLocation().getY() + ", " + this.getLocation().getZ();
    }

    public String getClanInfo(DatabaseContext ctx) {
		String formattedString = 
        Utils.chat("&6-------------&a[ " + this.name + " ]&6-------------\n" +
        "&7Leder: &f" + this.getLeader(ctx).getPlayer().getDisplayName() + "\n" +
        "&7Medlemmer: &f" + getFormattedPlayers() + "\n" +
        "&7Base: &f(" + getClanSpawnCoordinates(location) + ")" + "\n" +
        "&7Dager gammel: &f" + (Duration.between(Instant.now(), getDateCreated()).toDays()) + "\n" +
        "&7Fiende: &f" + "kommer");
		return formattedString;
	}
}