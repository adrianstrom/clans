package persistence.datatypes;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity(name = "Clan")
public class Clan {
    @Id
    public int id;

    public String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Set<Player> players = new HashSet<>();
}

private class Player {
    
}