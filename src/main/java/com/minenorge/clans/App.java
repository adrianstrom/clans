package com.minenorge.clans;

import com.minenorge.clans.commands.CreateClan;
import com.minenorge.clans.events.OnPlayerLogin;
import com.minenorge.clans.persistence.DatabaseContext;

import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin
{
    private DatabaseContext ctx;

    @Override
    public void onEnable() {
        ctx = new DatabaseContext();
        InitializeClasses();
        OnPlayerLogin opl = new OnPlayerLogin(this, ctx);
        getServer().getPluginManager().registerEvents(opl, this);
    }

    @Override
    public void onDisable() {
        
    }

    private void InitializeClasses() {
        new CreateClan(this, ctx);
    }
}
