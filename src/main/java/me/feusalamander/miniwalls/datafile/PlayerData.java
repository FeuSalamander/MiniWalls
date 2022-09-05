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
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!existPlayerData(e.getPlayer())){
            createPlayerData(e.getPlayer());
        }
    }
}
