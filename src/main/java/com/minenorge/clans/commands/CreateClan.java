package com.minenorge.clans.commands;

import com.minenorge.clans.App;
import com.minenorge.clans.persistence.DatabaseContext;
import com.minenorge.clans.persistence.datatypes.Clan;
import com.minenorge.clans.persistence.datatypes.ClanPlayer;
import com.minenorge.clans.persistence.datatypes.Settlement;
import com.minenorge.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
            String action = args[0];

            ClanPlayer clanPlayer = ctx.getPlayerByPlayerId(player.getUniqueId());
            Clan clan = clanPlayer.getClan();
            boolean isMemberOfClan = clan != null;

            if (action.equals("info")) {
                if (isMemberOfClan) {
                    player.sendMessage(clan.getClanInfo());
                    return true;
                }
                player.sendMessage(Utils.fail("Du er ikke medlem av en klan"));
                return true;
            } else if (action.equals("hjelp")) {
                player.sendMessage(Utils.chat("&8-------------------[ &cKlansystem &8]------------------- \n" +
                "&7/klan opprett (navn på klan) - &fOppretter en klan med deg selv som leder \n" +
                "&7/klan inviter (spiller) - &fInviterer spiller til klanen din om du er leder \n" +
                "&7/klan aksepter (klan navn) - &fAksepterer invitasjon til å bli medlem av klan \n " +
                "&7/klan settbase (navn) - &fOppretter en base for klanen din \n" +
                "&7/klan fjernbase (navn) - &fFjerner en base for klanen din \n" +
                "&7/klan base (navn) - &fTeleporterer deg til din klan sin base \n" +
                "&7/klan baser - &fGir deg en oversikt over basene til klanen din \n" +
                "&7/klan info - &fGir informasjon om klanen du er medlem av \n" +
                "&7/klan forlat - &fForlat klanen du er medlem av \n" +
                "&7/klan slett - &fSletter klanen om du er klan leder \n"));
                return true;
            } else if (action.equals("slett")) {
                if (clan != null) {
                    player.sendMessage("nei ikke");
                    return true;
                }
                player.sendMessage(Utils.fail("Klanen eksisterer ikke"));
                return true;
            } else if (action.equals("forlat")) {
                if(!isMemberOfClan) {
                    player.sendMessage(Utils.fail("Du er ikke medlem av en klan"));
                    return true;
                }
                clan.removePlayer(clanPlayer);
                ctx.update(clan);
                Bukkit.broadcastMessage(Utils.success(clanPlayer.getPlayer().getDisplayName() + " forlot klanen " + clan.getName()));
                return true;
            } else if (action.equals("baser")) {
                if(!isMemberOfClan) {
                    player.sendMessage(Utils.fail("Du er ikke medlem av en klan"));
                    return true;
                }
                player.sendMessage(Utils.success(clan.getFormattedSettlements()));
                return true;
            }
            return true;
        }
        else if(args.length == 2) {
            String action = args[0];
            if (action.equals("opprett")) {
                String clanName = args[1];

                Clan clan = ctx.getClanByName(clanName);
                ClanPlayer clanPlayer = ctx.getPlayerByPlayerId(player.getUniqueId());
                boolean isMemberOfClan = clanPlayer.getClan() != null;

                if (isMemberOfClan) {
                    player.sendMessage(Utils.fail("Du er allerede medlem av en klan"));
                    return true;
                }
                if (clan != null) {
                    player.sendMessage(Utils.fail("Denne klanen eksisterer allerede"));
                    return true;
                }
                clan = new Clan();
                clan.setName(clanName);
                clan.addPlayer(clanPlayer);
                clan.setLeader(clanPlayer);
                boolean result = ctx.create(clan);
                if (result) {
                    Bukkit.broadcastMessage(Utils.success("Klanen " + clan.getName() + " ble opprettet med " + clan.getLeaderDisplayName() + " som leder"));
                    return true;
                }
                player.sendMessage(Utils.fail("Klanen ble ikke opprettet"));
                return true;
            } else if (action.equals("inviter")) {
                String playerName = args[1];
                Player invitedPlayer = Bukkit.getPlayer(playerName);
                if(invitedPlayer == null) {
                    player.sendMessage(Utils.fail("Fant ikke denne spilleren"));
                    return true;
                }
                ClanPlayer invitedClanPlayer = ctx.getPlayerByPlayerId(invitedPlayer.getUniqueId());
                if(invitedClanPlayer.getClan() != null) {
                    player.sendMessage(Utils.fail("Du kan ikke invitere en spiller som allerede er medlem av en klan"));
                    return true;
                }
                ClanPlayer clanPlayer = ctx.getPlayerByPlayerId(player.getUniqueId());
                Clan clan = clanPlayer.getClan();
                boolean isClanLeader = clan != null && clan.getLeader() != null && clan.getLeader().getPlayer().getUniqueId().equals(player.getUniqueId());
                if (!isClanLeader) {
                    player.sendMessage(Utils.fail("Du kan ikke invitere folk til denne klanen fordi du ikke er leder av klanen"));
                    return true;
                }                
                clan.invitePlayer(invitedClanPlayer);
                ctx.update(clan);
                invitedPlayer.sendMessage(Utils.success("Du har blitt invitert til å bli med i klanen " + clan.getName()));
                player.sendMessage(Utils.success("Du invitere spilleren " + invitedPlayer.getDisplayName()) + " til " + clan.getName());
                return true;
            } else if (action.equals("aksepter")) {
                String clanName = args[1];

                ClanPlayer clanPlayer = ctx.getPlayerByPlayerId(player.getUniqueId());

                for (Clan clan : clanPlayer.getClanInvitations() ) {
                    if(clan.getName().equals(clanName)) {
                        for(ClanPlayer member : clan.getPlayers()) {
                            if(clanPlayer.getPlayer().getUniqueId().equals(member.getPlayer().getUniqueId())) {
                                Utils.fail("Du er allerede medlem av denne klanen");
                                return true;
                            }
                        }

                        for(ClanPlayer invitedPlayer : clan.getInvitedPlayers()){
                            if(clanPlayer.getPlayer().getUniqueId().equals(invitedPlayer.getPlayer().getUniqueId())) {
                                clan.addPlayer(clanPlayer);
                                clan.removeInvitation(clanPlayer);
                                ctx.update(clan);
                                clan.broadcastMessage(Utils.success(clanPlayer.getDisplayName() + " ble medlem av klanen din " + clan.getName()), clanPlayer);
                                return true;
                            }
                        }
                    }
                }
                player.sendMessage(Utils.fail("Du er ikke invitert til denne klanen"));
                return true;
            } else if(action.equals("settbase")) {
                String baseName = args[1];

                ClanPlayer clanPlayer = ctx.getPlayerByPlayerId(player.getUniqueId());
                Clan clan = clanPlayer.getClan();
                boolean isMemberOfClan = clan != null;

                if(!isMemberOfClan)
                {
                    player.sendMessage(Utils.fail("Du er ikke medlem av en klan"));
                    return true;
                }

                for (Settlement settlement : clan.getSettlements()) {
                    if(settlement.name.equals(baseName)) {
                        player.sendMessage(Utils.fail("Klanen din har allerede en base med samme navn " + baseName));
                        return true;
                    }
                }
                if(clan.getSettlements().isEmpty()) {
                    Settlement settlement = new Settlement();
                    settlement.setLocation(player.getLocation());
                    settlement.setName(baseName);
                    clan.addSettlement(settlement);          
                    ctx.create(settlement);
                    ctx.update(clan);
                    clan.broadcastMessage(Utils.success("Basen " + settlement.getName() + " ble opprettet for klanen din " + clan.getName()));
                    return true;
                } else {
                    Integer numberOfSettlements = clan.getSettlements().size();
                    Integer price = numberOfSettlements <= 4 ? numberOfSettlements * 32 : 128;
                    Material material = Material.DIAMOND; // Make this configurable
                    ItemStack itemstack = new ItemStack(material, price);

                    PlayerInventory inventory = player.getInventory();
                    if (!inventory.contains(itemstack)) {
                        player.sendMessage(Utils.fail("Du trenger " + price + " " + material.toString().toLowerCase() + " for å opprette en ny base"));
                        return true;
                    } else {
                        inventory.remove(itemstack);
                        player.updateInventory();
                        Settlement settlement = new Settlement();
                        settlement.setLocation(player.getLocation());
                        settlement.setName(baseName);
                        clan.addSettlement(settlement);          
                        ctx.create(settlement);
                        ctx.update(clan);
                        clan.broadcastMessage(Utils.success("Basen " + settlement.getName() + " ble opprettet for klanen din " + clan.getName() + " for " + price + " " + material.toString().toLowerCase()));
                        return true;
                    }
                }
            } else if (action.equals("fjernbase")) {
                String baseName = args[1];

                ClanPlayer clanPlayer = ctx.getPlayerByPlayerId(player.getUniqueId());
                Clan clan = clanPlayer.getClan();
                boolean isMemberOfClan = clan != null;

                if(!isMemberOfClan)
                {
                    player.sendMessage(Utils.fail("Du er ikke medlem av en klan"));
                    return true;
                }

                for (Settlement settlement : clan.getSettlements()) {
                    if(settlement.name.equals(baseName)) {
                        clan.removeSettlement(settlement);
                        ctx.update(clan);
                        clan.broadcastMessage(Utils.success("Basen " + settlement.getName() + " ble fjernet av " + player.getDisplayName()));
                        return true;
                    }
                }
                player.sendMessage(Utils.fail("Fant ikke basen med navn " + baseName + " som du prøvde å fjerne"));
                return true;
            } else if (action.equals("base")) {
                String baseName = args[1];

                ClanPlayer clanPlayer = ctx.getPlayerByPlayerId(player.getUniqueId());
                Clan clan = clanPlayer.getClan();
                boolean isMemberOfClan = clan != null;

                if(!isMemberOfClan)
                {
                    player.sendMessage(Utils.fail("Du er ikke medlem av en klan"));
                    return true;
                }

                for (Settlement settlement : clan.getSettlements()) {
                    if(settlement.name.equals(baseName)) {
                        player.teleport(settlement.getLocation());
                        player.sendMessage(Utils.success("Du ble teleportert til " + baseName));
                        return true;
                    }
                }
                player.sendMessage(Utils.fail("Fant ikke basen med navn " + baseName + " som du prøvde å teleportere til"));
                return true;
            }
            return true;
        }
        return false;
    }
}
