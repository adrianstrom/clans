package com.minenorge.clans.persistence.datatypes;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Where;

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

    private Location location = new Location();

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

    public void setLocation(Location location) {
        this.location = location;
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
    }

    public void removePlayer(ClanPlayer player) {
        players.remove(player);
    }

    private String getFormattedPlayers() {
        String formattedNames = "";
        for (ClanPlayer clanPlayer : players) {
            String playerName = clanPlayer.getPlayer().getDisplayName();
            formattedNames += playerName;
        }
        return formattedNames;
    }

    public String getClanInfo() {
		String formattedString = "";
        Utils.chat("&6" + this.name + "\n" +
        "Medlemmer: " + getFormattedPlayers() + "\n" +
        "Dager gammel: " + (Duration.between(Instant.now(), getDateCreated()).toDays()));
		return formattedString;
	}
}