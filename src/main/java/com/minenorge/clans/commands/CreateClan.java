package com.minenorge.clans.commands;

import com.minenorge.clans.App;
import com.minenorge.clans.persistence.DatabaseContext;
import com.minenorge.clans.persistence.datatypes.Clan;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;

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

            if(action.equals("opprett")) {
                // Ensure clan doesn't exist so we don't end up with clans with same name.
                List<Clan> clans = ctx.getClanByName(clanName);
                if(!clans.isEmpty()){
                    player.sendMessage("Clan already exists.");
                    return true;
                }

                Clan clan = new Clan();
                clan.setName(clanName);

                boolean result = ctx.create(clan);
                if(result) {
                    player.sendMessage("Clan successfully created.");
                    return true;
                } else {
                    player.sendMessage("Clan not created.");
                }
            }
            player.sendMessage("message");
            return true;
        }
        return false;
    }
}
