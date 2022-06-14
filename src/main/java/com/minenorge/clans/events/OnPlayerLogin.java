package com.minenorge.clans.events;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.minenorge.clans.App;
import com.minenorge.clans.persistence.DatabaseContext;
import com.minenorge.clans.persistence.datatypes.ClanPlayer;

public class OnPlayerLogin implements Listener {
    private App plugin;
    private DatabaseContext ctx;

	public OnPlayerLogin(App plugin, DatabaseContext ctx) {
		this.plugin = plugin;
        this.ctx = ctx;
	}

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        UUID playerId = e.getPlayer().getUniqueId();
        ClanPlayer player = ctx.getPlayerByPlayerId(playerId);
        if(player == null) {
            player = new ClanPlayer();
            player.setPlayer(e.getPlayer());
            player.setPlayerUniqueId(playerId);
            ctx.create(player);
        }
    }
}