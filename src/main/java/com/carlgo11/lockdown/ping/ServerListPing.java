package com.carlgo11.lockdown.ping;


import com.carlgo11.lockdown.Main;
import com.carlgo11.lockdown.Mysql;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPing implements Listener {

    Main plugin;

    public ServerListPing(Main plug)
    {
        super();
        this.plugin = plug;
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent e)
    {
        if (Bukkit.hasWhitelist()) {
            e.setMaxPlayers(0);
            String msg = ChatColor.translateAlternateColorCodes('&', Mysql.getMOTD("true"));
            e.setMotd(msg);
        } else {
            String msg = ChatColor.translateAlternateColorCodes('&', Mysql.getMOTD("false"));
            e.setMotd(msg);
        }
    }
}
