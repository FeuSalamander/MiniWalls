package me.feusalamander.miniwalls.datafile;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class PlayerData implements Listener{
    private MiniWalls main;
    public PlayerData(MiniWalls main) {
        this.main = main;
    }
    public boolean existPlayerData(Player p){
        File f = new File(main.getDataFolder()+"/player-data/", p.getUniqueId().toString()+".yml");
        if(f.exists()){
            return  true;
        }else{
            return false;
        }
    }
    public void createPlayerData(Player p){
        if(!existPlayerData(p)){
            File f = new File(main.getDataFolder()+"/player-data/", p.getUniqueId().toString()+".yml");
            File folder = new File(main.getDataFolder()+"/player-data/", "");
            folder.mkdirs();
            try{
                f.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
            config.set("playername", p.getName());
            config.set("wins", 0);
            try{
                config.save(f);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public File getPlayerDataFile(Player p){
        if(existPlayerData(p)){
            return new File(main.getDataFolder()+"/player-data/", p.getUniqueId().toString()+".yml");
        }else{
            return null;
        }
    }
    public int getWins(Player p){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("wins").toString());
    }
    public void setWins(Player p, int wins){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("wins", wins);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @EventHandler
    public void setspawn(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("/mw wins")) {
            player.sendMessage("Your wins "+getWins(player));
        }else if (e.getMessage().equalsIgnoreCase("/mw setwins1")) {
            setWins(player.getPlayer(), 1);
        }else if (e.getMessage().equalsIgnoreCase("/mw setwins2")) {
            setWins(player.getPlayer(), 2);
        }else if (e.getMessage().equalsIgnoreCase("/mw setwins3")) {
            setWins(player.getPlayer(), 3);
        }else if (e.getMessage().equalsIgnoreCase("/mw setwins4")) {
            setWins(player.getPlayer(), 4);
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!existPlayerData(e.getPlayer())){
            createPlayerData(e.getPlayer());
        }
    }
}
