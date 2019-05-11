package com.astromc.stfu;

import com.astromc.jason.Jason;
import com.astromc.jason.type.Resource;
import com.astromc.stfu.configuration.Conf;
import com.astromc.type.Reloadable;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

public class STFU extends JavaPlugin implements Reloadable, Listener {

    private static STFU instance;

    private Jason jason;

    @Override
    public void onEnable() {
        this.jason = new Jason((instance = this).getDataFolder());
        this.jason.prey(new Conf());
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onReload() {
        this.jason.getResources().forEach(Resource::load);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getPlayer().hasPermission("stfu.bypass")) {
            return;
        }
        String message = event.getMessage().replaceAll("[^A-Za-z\\s]", "").toLowerCase();
        boolean failed = false;
        for (String blacklisted : getConf().getBlacklist()) {
            if (message.contains(blacklisted.toLowerCase())) {
                failed = true;
                break;
            }
        }
        if (!failed) {
            return;
        }
        event.getRecipients().clear();
        event.getRecipients().add(event.getPlayer());
        event.getRecipients().addAll(Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission("stfu.receive"))
                .collect(Collectors.toList()));
    }

    public Conf getConf() {
        return (Conf) this.jason.get(Conf.class).getObject();
    }

    public Jason getJason() {
        return jason;
    }

    public static STFU getInstance() {
        return instance;
    }

}
