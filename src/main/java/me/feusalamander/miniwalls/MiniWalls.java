package me.feusalamander.miniwalls;
import me.feusalamander.miniwalls.commands.MWTab;
import me.feusalamander.miniwalls.commands.mw;
import me.feusalamander.miniwalls.datafile.PlayerData;
import me.feusalamander.miniwalls.listeners.MapReset;
import me.feusalamander.miniwalls.listeners.bow;
import me.feusalamander.miniwalls.listeners.commands;
import me.feusalamander.miniwalls.listeners.jointeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import java.util.ArrayList;
import java.util.List;
public final class MiniWalls extends JavaPlugin{
    private List<Player> players = new ArrayList<>();
    public List<String> activeteams = new ArrayList<>();
    public int blife = 20;
    public int rlife = 20;
    public int glife = 20;
    public int ylife = 20;
    private MWstates state;
    public Scoreboard scoreboard;
    Team blue;
    Team red;
    Team green;
    Team yellow;
    Team playerss;
    Team bv;
    Team rv;
    Team gv;
    Team yv;
    @Override
    public void onEnable() {
        getLogger().info( "Mini Walls by FeuSalamander is working !");
        PluginManager pm = Bukkit.getPluginManager();
        getCommand("mw").setExecutor(new mw());
        getCommand("mw").setTabCompleter(new MWTab());
        getServer().getPluginManager().registerEvents(new jointeam(this), this);
        getServer().getPluginManager().registerEvents(new MapReset(this), this);
        getServer().getPluginManager().registerEvents(new commands(this), this);
        getServer().getPluginManager().registerEvents(new bow(this), this);
        getServer().getPluginManager().registerEvents(new PlayerData(this), this);
        setState(MWstates.WAITING);
        saveDefaultConfig();
        saveConfig();
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        blue = scoreboard.registerNewTeam("Blue");
        blue.setPrefix("§9Blue ");
        blue.setAllowFriendlyFire(false);
        red = scoreboard.registerNewTeam("Red");
        red.setPrefix("§cRed ");
        red.setAllowFriendlyFire(false);
        green = scoreboard.registerNewTeam("Green");
        green.setPrefix("§aGreen ");
        green.setAllowFriendlyFire(false);
        yellow = scoreboard.registerNewTeam("Yellow");
        yellow.setPrefix("§eYellow ");
        yellow.setAllowFriendlyFire(false);
        playerss = scoreboard.registerNewTeam("playerss");
        playerss.addEntry("players: ");
        bv = scoreboard.registerNewTeam("bv");
        bv.addEntry("§7");
        rv = scoreboard.registerNewTeam("rv");
        rv.addEntry("§d");
        gv = scoreboard.registerNewTeam("gv");
        gv.addEntry("§b");
        yv = scoreboard.registerNewTeam("yv");
        yv.addEntry("§5");
        Objective objective = scoreboard.registerNewObjective("MiniWalls", "dummy", "   §e§lMiniWalls   ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore("").setScore(12);
        objective.getScore("Map: §a"+getConfig().getString("map")).setScore(11);
        objective.getScore("players: ").setScore(10);
        objective.getScore("§9").setScore(9);
        objective.getScore("§7").setScore(8);
        objective.getScore("§d").setScore(7);
        objective.getScore("§b").setScore(6);
        objective.getScore("§5").setScore(5);
        objective.getScore("§e").setScore(4);
        objective.getScore("Version: §7v1.4").setScore(3);
        objective.getScore("§a").setScore(2);
        objective.getScore("§e"+getConfig().getString("server")).setScore(1);
    }
    public void setState(MWstates state){
        this.state = state;
    }
    public boolean isState(MWstates state){
        return this.state == state;
    }
    public List<Player> getPlayers(){
        return players;
    }
    public void eliminate(Player player) {
        if(players.contains(player)) players.remove(player);
        player.sendMessage("§4You got eliminated");
        Location lobby  = new Location(Bukkit.getWorld("world"), getConfig().getInt("Locations.Lobby.x"), getConfig().getInt("Locations.Lobby.y"), getConfig().getInt("Locations.Lobby.z"));
        player.teleport(lobby);
        scoreboard.getTeam("Blue").removePlayer(player);
        scoreboard.getTeam("Red").removePlayer(player);
        scoreboard.getTeam("Green").removePlayer(player);
        scoreboard.getTeam("Yellow").removePlayer(player);
        player.getInventory().clear();
        player.setLevel(0);
        player.setHealth(20);
        if(scoreboard.getTeam("Blue").getSize() == 0){
            activeteams.remove("Blue");
        }
        if(scoreboard.getTeam("Red").getSize() == 0){
            activeteams.remove("Red");
        }
        if(scoreboard.getTeam("Green").getSize() == 0){
            activeteams.remove("Green");
        }
        if(scoreboard.getTeam("Yellow").getSize() == 0){
            activeteams.remove("Yellow");
        }
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
    @Override
    public void onDisable() {
        getLogger().info( "MiniWalls by FeuSalamander is shutting down !");
    }
}