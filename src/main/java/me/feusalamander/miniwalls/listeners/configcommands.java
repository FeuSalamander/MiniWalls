package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
public class configcommands implements Listener {
    private MiniWalls main;
    public configcommands(MiniWalls main) {
        this.main = main;
    }
    @EventHandler
    public void setspawn(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if(e.getMessage().equalsIgnoreCase("/mw setspawn")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Spawn.x",player.getLocation().getX());
                main.getConfig().set("Locations.Spawn.y", player.getLocation().getY());
                main.getConfig().set("Locations.Spawn.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }
        if(e.getMessage().equalsIgnoreCase("/mw setlobby")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Lobby.x",player.getLocation().getX());
                main.getConfig().set("Locations.Lobby.y", player.getLocation().getY());
                main.getConfig().set("Locations.Lobby.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }
        if(e.getMessage().equalsIgnoreCase("/mw setbluebase")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Bases.BlueBase.x",player.getLocation().getX());
                main.getConfig().set("Locations.Bases.BlueBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.BlueBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }
        if(e.getMessage().equalsIgnoreCase("/mw setredbase")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Bases.RedBase.x",player.getLocation().getX());
                main.getConfig().set("Locations.Bases.RedBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.RedBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }
        if(e.getMessage().equalsIgnoreCase("/mw setgreenbase")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Bases.GreenBase.x",player.getLocation().getX());
                main.getConfig().set("Locations.Bases.GreenBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.GreenBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }
        if(e.getMessage().equalsIgnoreCase("/mw setyellowbase")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Bases.YellowBase.x",player.getLocation().getX());
                main.getConfig().set("Locations.Bases.YellowBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.YellowBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }
    }
}