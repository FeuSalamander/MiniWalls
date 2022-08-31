package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MiniWalls;
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
        }else if(e.getMessage().equalsIgnoreCase("/mw setlobby")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Lobby.x",player.getLocation().getX());
                main.getConfig().set("Locations.Lobby.y", player.getLocation().getY());
                main.getConfig().set("Locations.Lobby.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }else if(e.getMessage().equalsIgnoreCase("/mw setbluebase")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Bases.BlueBase.x",player.getLocation().getX());
                main.getConfig().set("Locations.Bases.BlueBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.BlueBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }else if(e.getMessage().equalsIgnoreCase("/mw setredbase")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Bases.RedBase.x",player.getLocation().getX());
                main.getConfig().set("Locations.Bases.RedBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.RedBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }else if(e.getMessage().equalsIgnoreCase("/mw setgreenbase")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Bases.GreenBase.x",player.getLocation().getX());
                main.getConfig().set("Locations.Bases.GreenBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.GreenBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }else if(e.getMessage().equalsIgnoreCase("/mw setyellowbase")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Bases.YellowBase.x",player.getLocation().getX());
                main.getConfig().set("Locations.Bases.YellowBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.YellowBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }else if(e.getMessage().equalsIgnoreCase("/mw setbluevillager")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Villagers.Bluevillager.x",player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Bluevillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Bluevillager.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }else if(e.getMessage().equalsIgnoreCase("/mw setredvillager")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Villagers.Redvillager.x",player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Redvillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Redvillager.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }else if(e.getMessage().equalsIgnoreCase("/mw setgreenvillager")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Villagers.Greenvillager.x",player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Greenvillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Greenvillager.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }else if(e.getMessage().equalsIgnoreCase("/mw setyellowvillager")){
            if(player.hasPermission("mw.admin")){
                main.getConfig().set("Locations.Villagers.Yellowvillager.x",player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Yellowvillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Yellowvillager.z", player.getLocation().getZ());
                main.saveConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }
    }
}