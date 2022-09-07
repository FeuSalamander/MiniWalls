package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.io.File;
import java.io.IOException;
public class PlayerData implements Listener{
    private static MiniWalls main;
    public PlayerData(MiniWalls main) {
        this.main = main;
    }
    public static boolean existPlayerData(Player p){
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
            config.set("loses", 0);
            config.set("kills", 0);
            config.set("finalkills", 0);
            config.set("deaths", 0);
            try{
                config.save(f);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public static File getPlayerDataFile(Player p){
        if(existPlayerData(p)){
            return new File(main.getDataFolder()+"/player-data/", p.getUniqueId()+".yml");
        }else{
            return null;
        }
    }
    public static int getWins(Player p){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("wins").toString());
    }
    public static void setWins(Player p, int wins){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("wins", wins);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static int getkills(Player p){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("kills").toString());
    }
    public static void setkills(Player p, int kills){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("kills", kills);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static int getdeath(Player p){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("deaths").toString());
    }
    public static void setdeath(Player p, int death){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("deaths", death);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static int getloses(Player p){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("loses").toString());
    }
    public static void setloses(Player p, int death){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("loses", death);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static int getfinal(Player p){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("finalkills").toString());
    }
    public static void setfinal(Player p, int death){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("finalkills", death);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!existPlayerData(e.getPlayer())){
            createPlayerData(e.getPlayer());
        }
    }
}
