package persistence.datatypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Clan")
@Where(clause = "deleted = false")
public class Clan extends EntityBase {
    @Id
    public int id;

    public String name;

    public Location location;

	public Set<UUID> players = new HashSet<>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Player> getPlayers() {
        Set<Player> players = new HashSet<>();
        for (UUID playerId : this.players) {
            Player player = Bukkit.getPlayer(playerId);
            players.add(player);
        }
        return players;
    }

    public void addPlayer(UUID playerId) {
        players.add(playerId);
    }

    public void removePlayer(UUID playerId) {
        players.remove(playerId);
    }
}