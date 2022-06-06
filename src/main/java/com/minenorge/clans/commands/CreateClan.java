package com.minenorge.clans.commands;

import com.minenorge.clans.App;
import com.minenorge.clans.persistence.DatabaseContext;
import com.minenorge.clans.persistence.datatypes.Clan;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateClan implements CommandExecutor {
    private App plugin;
    private DatabaseContext ctx;

	public CreateClan(App plugin, DatabaseContext ctx) {
		this.plugin = plugin;
        this.ctx = ctx;
		plugin.getCommand("klan").setExecutor(this);
	}

    @Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = (Player) sender;
		
		if (args.length == 0) {
			player.sendMessage("Hvilken kommando ønsker du å bruke?");
			return true;
        } 
        else if(args.length == 1) {
            player.sendMessage("message");
            return true;
        }
        else if(args.length == 2) {
            String action = args[0];
			String clanName = args[1];

            Clan clan = ctx.getClanByName(clanName);
            // TODO: Map clan on player
            boolean isMemberOfClan = false;

            if(action.equals("opprett")) {
                // Ensure player is not member of a clan before trying to create clan
                if(isMemberOfClan) {
                    player.sendMessage("Du er allerede medlem av en klan");
                    return true;
                }

                // Ensure clan doesn't exist so we don't end up with clans with same name
                if(clan != null){
                    player.sendMessage("Denne klanen eksisterer allerede");
                    return true;
                }

                clan = new Clan();
                clan.setName(clanName);
                // clan.addPlayer(player);
                // clan.setLeader(player);
                boolean result = ctx.create(clan);
                if(result) {
                    player.sendMessage("Klanen " + clan.getName() + " ble opprettet med deg som leder");
                    return true;
                }
                player.sendMessage("Klanen ble ikke opprettet");
                return true;
            } else if(action.equals("fjern")) {
                if(clan != null) {
                    clan.setDeleted(true);
                    player.sendMessage("Klanen " + clan.getName() + " ble slettet");
                    return true;
                }
                player.sendMessage("Klanen eksisterer ikke");
                return true;
            } else if(action.equals("base")) {
                if(!isMemberOfClan) {
                    player.sendMessage("Du er ikke medlem av en klan");
                    return true;
                }
                if(clan != null) {
                    player.teleport(clan.getLocation());
                    player.sendMessage("Du ble teleportert til " + clan.getName() + " sin base.");
                    return true;
                }
                player.sendMessage("Denne klanen har ingen base");
                return true;
            }
            player.sendMessage("message");
            return true;
        }
        return false;
    }
}
