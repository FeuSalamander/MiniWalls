package me.feusalamander.miniwalls;

import me.feusalamander.miniwalls.commands.MWTab;
import me.feusalamander.miniwalls.commands.mw;
import me.feusalamander.miniwalls.listeners.DMGlistener;
import me.feusalamander.miniwalls.listeners.listener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public final class MiniWalls extends JavaPlugin{
    private List<Player> players = new ArrayList<>();
    private List<Location> spawns = new ArrayList<>();
    private MWstates state;

    @Override
    public void onEnable() {
        getLogger().info( "Mini Walls by FeuSalamander is working !");
        getCommand("mw").setExecutor(new mw());
        getCommand("mw").setTabCompleter(new MWTab());
        getServer().getPluginManager().registerEvents(new listener(this), this);
        getServer().getPluginManager().registerEvents(new DMGlistener(this), this);
        setState(MWstates.WAITING);
        saveDefaultConfig();
        //load spawns
        World world = Bukkit.getWorld("world");
        spawns.add(new Location(world, getConfig().getInt("Locations.bases.base1.x"), getConfig().getInt("Locations.bases.base1.y"), getConfig().getInt("Locations.bases.base1.z")));
        spawns.add(new Location(world, getConfig().getInt("Locations.bases.base2.x"), getConfig().getInt("Locations.bases.base2.y"), getConfig().getInt("Locations.bases.base2.z")));
        spawns.add(new Location(world, getConfig().getInt("Locations.bases.base3.x"), getConfig().getInt("Locations.bases.base3.y"), getConfig().getInt("Locations.bases.base3.z")));
        spawns.add(new Location(world, getConfig().getInt("Locations.bases.base4.x"), getConfig().getInt("Locations.bases.base4.y"), getConfig().getInt("Locations.bases.base4.z")));
        spawns.add(new Location(world, getConfig().getInt("Locations.bases.base5.x"), getConfig().getInt("Locations.bases.base5.y"), getConfig().getInt("Locations.bases.base5.z")));
        spawns.add(new Location(world, getConfig().getInt("Locations.bases.base6.x"), getConfig().getInt("Locations.bases.base6.y"), getConfig().getInt("Locations.bases.base6.z")));
        spawns.add(new Location(world, getConfig().getInt("Locations.bases.base7.x"), getConfig().getInt("Locations.bases.base7.y"), getConfig().getInt("Locations.bases.base7.z")));
        spawns.add(new Location(world, getConfig().getInt("Locations.bases.base8.x"), getConfig().getInt("Locations.bases.base8.y"), getConfig().getInt("Locations.bases.base8.z")));
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
    public List<Location> getSpawns(){
        return spawns;
    }
    public void eliminate(Player player) {
        if(players.contains(player)) players.remove(player);
        player.sendMessage("§4You got eliminated");
        Location lobby  = new Location(Bukkit.getWorld("world"), getConfig().getInt("Locations.Lobby.x"), getConfig().getInt("Locations.Lobby.y"), getConfig().getInt("Locations.Lobby.z"));
        player.teleport(lobby);
        checkWin();
        player.getInventory().clear();
        player.setLevel(0);
        player.setHealth(20);
    }
    public void checkWin() {
        if(players.size() <= 1){
            Player winner = players.get(0);
            Bukkit.broadcastMessage("§6"+winner.getName()+" §6Won the game");
            Location lobby  = new Location(Bukkit.getWorld("world"), getConfig().getInt("Locations.Lobby.x"), getConfig().getInt("Locations.Lobby.y"), getConfig().getInt("Locations.Lobby.z"));
            winner.teleport(lobby);
            getPlayers().remove(winner);
            setState(MWstates.WAITING);
            winner.getInventory().clear();
            winner.setLevel(0);
            winner.setHealth(20);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info( "MiniWalls by FeuSalamander is shutting down !");
    }
}