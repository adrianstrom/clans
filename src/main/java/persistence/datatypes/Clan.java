package persistence.datatypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Clan")
public class Clan extends EntityBase {
    @Id
    public int id;

    public String name;

    private Location location;

	public Set<UUID> players = new HashSet<>();
}