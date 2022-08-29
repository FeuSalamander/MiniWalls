package me.feusalamander.miniwalls;

import me.feusalamander.miniwalls.commands.MWTab;
import me.feusalamander.miniwalls.commands.mw;
import me.feusalamander.miniwalls.listeners.DMGlistener;
import me.feusalamander.miniwalls.listeners.listener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public final class MiniWalls extends JavaPlugin{
    private List<Player> players = new ArrayList<>();
    public List<String> activeteams = new ArrayList<>();
    private MWstates state;
    public Scoreboard scoreboard;
    Team blue;
    Team red;
    Team green;
    Team yellow;

    @Override
    public void onEnable() {
        getLogger().info( "Mini Walls by FeuSalamander is working !");
        getCommand("mw").setExecutor(new mw());
        getCommand("mw").setTabCompleter(new MWTab());
        getServer().getPluginManager().registerEvents(new listener(this), this);
        getServer().getPluginManager().registerEvents(new DMGlistener(this), this);
        setState(MWstates.WAITING);
        saveDefaultConfig();
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
        player.getInventory().clear();
        player.setLevel(0);
        player.setHealth(20);
        scoreboard.getTeam("Blue").removePlayer(player);
        scoreboard.getTeam("Red").removePlayer(player);
        scoreboard.getTeam("Green").removePlayer(player);
        scoreboard.getTeam("Yellow").removePlayer(player);
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
        checkWin();
    }
    public void checkWin() {
        if(activeteams.size() <= 1){
            Bukkit.broadcastMessage("§6The "+scoreboard.getTeam(activeteams.get(0)).getPrefix()+"Team §6Won the game");
            setState(MWstates.WAITING);
            for(int i = 0; i < getPlayers().size(); i++)
            {
                Player winner = getPlayers().get(i);
                Location lobby  = new Location(Bukkit.getWorld("world"), getConfig().getInt("Locations.Lobby.x"), getConfig().getInt("Locations.Lobby.y"), getConfig().getInt("Locations.Lobby.z"));
                winner.teleport(lobby);
                getPlayers().remove(winner);
                winner.getInventory().clear();
                winner.setLevel(0);
                winner.setHealth(20);

        }
    }
    }

    @Override
    public void onDisable() {
        getLogger().info( "MiniWalls by FeuSalamander is shutting down !");
    }}