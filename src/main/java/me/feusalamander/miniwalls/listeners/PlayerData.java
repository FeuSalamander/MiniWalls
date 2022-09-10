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
    public static boolean existPlayerData(){
        File f = new File(main.getDataFolder(), "stats.yml");
        if(f.exists()){
            return  true;
        }else{
            return false;
        }
    }
    public static void createPlayerData(){
        if(!existPlayerData()){
            File f = new File(main.getDataFolder(), "stats.yml");
            try{
                f.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
            config.set("players", 0);
            try{
                config.save(f);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public static File getPlayerDataFile(Player p){
        if(existPlayerData()){
            return new File(main.getDataFolder(), "stats.yml");
        }else{
            return null;
        }
    }
    public static int getWins(Player p){
        File f = new File(main.getDataFolder(), "stats.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("players."+p.getUniqueId()+".wins").toString());
    }
    public static void setWins(Player p, int wins){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("players."+p.getUniqueId()+".wins", wins);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static int getkills(Player p){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("players."+p.getUniqueId()+".kills").toString());
    }
    public static void setkills(Player p, int kills){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("players."+p.getUniqueId()+".kills", kills);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static int getdeath(Player p){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("players."+p.getUniqueId()+".deaths").toString());
    }
    public static void setdeath(Player p, int death){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("players."+p.getUniqueId()+".deaths", death);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static int getloses(Player p){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("players."+p.getUniqueId()+".loses").toString());
    }
    public static void setloses(Player p, int death){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("players."+p.getUniqueId()+".loses", death);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static int getfinal(Player p){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        return Integer.parseInt(config.get("players."+p.getUniqueId()+".finalkills").toString());
    }
    public static void setfinal(Player p, int death){
        File f = getPlayerDataFile(p);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        config.set("players."+p.getUniqueId()+".finalkills", death);
        try{
            config.save(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {
        Player p = e.getPlayer();
        File f = new File(main.getDataFolder(), "stats.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        if(config.get("players."+p.getUniqueId()+".playername") == null){
            config.set("players."+p.getUniqueId()+".playername", p.getName());
            config.set("players."+p.getUniqueId()+".wins", 0);
            config.set("players."+p.getUniqueId()+".loses", 0);
            config.set("players."+p.getUniqueId()+".kills", 0);
            config.set("players."+p.getUniqueId()+".finalkills", 0);
            config.set("players."+p.getUniqueId()+".deaths", 0);
            config.save(f);
        }
    }
}
