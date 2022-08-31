package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import java.util.LinkedList;
import java.util.List;
public class MapReset implements Listener {
    private MiniWalls main;
    public MapReset(MiniWalls main) {
        this.main = main;
    }
    private List<String> CHANGES = new LinkedList<String>();
    private List<String> CHANGES2 = new LinkedList<String>();
    public void restore(){
        int blockss = 0;
        for(String b : CHANGES2){
            String[] blockdata = b.split(":");
            World world = Bukkit.getWorld(blockdata[0]);
            int x = Integer.parseInt(blockdata[1]);
            int y = Integer.parseInt(blockdata[2]);
            int z = Integer.parseInt(blockdata[3]);
            world.getBlockAt(x, y, z).setType(Material.AIR);
            blockss++;
        }
        int blocks = 0;
        for(String b : CHANGES){
            String[] blockdata = b.split(":");
            Material id = Material.valueOf((blockdata[0]));
            World world = Bukkit.getWorld(blockdata[1]);
            int x = Integer.parseInt(blockdata[2]);
            int y = Integer.parseInt(blockdata[3]);
            int z = Integer.parseInt(blockdata[4]);
            world.getBlockAt(x, y, z).setType(id);
            blocks++;
        }
    }
    @EventHandler
    public void onBlockb(BlockBreakEvent e){
        Block b = e.getBlock();
        Player p = e.getPlayer();
        if(main.getPlayers().contains(p)){
            String block = b.getType() + ":" + b.getWorld().getName() +
                    ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();
            CHANGES.add(block);
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            if(main.scoreboard.getTeam("Blue").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.BLUE_WOOL));
            }else if(main.scoreboard.getTeam("Red").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.RED_WOOL));
            }else if(main.scoreboard.getTeam("Green").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.GREEN_WOOL));
            }else if(main.scoreboard.getTeam("YELLOW").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.YELLOW_WOOL));
            }
        }
    }
    @EventHandler
    public void onBlockp(BlockPlaceEvent e){
        Block b = e.getBlock();
        Player p = e.getPlayer();
        if(main.getPlayers().contains(p)){
            if(e.getBlock().getType() == Material.BLUE_WOOL){
                String block = b.getWorld().getName() +
                        ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();
                CHANGES2.add(block);
            }else if(e.getBlock().getType() == Material.RED_WOOL){
                String block = b.getWorld().getName() +
                        ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();
                CHANGES2.add(block);
            }else if(e.getBlock().getType() == Material.GREEN_WOOL){
                String block = b.getWorld().getName() +
                        ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();
                CHANGES2.add(block);
            }else if(e.getBlock().getType() == Material.YELLOW_WOOL){
                String block = b.getWorld().getName() +
                        ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();
                CHANGES2.add(block);
            }
        }
    }
    public void eliminate(Player player) {
        if(main.getPlayers().contains(player)) main.getPlayers().remove(player);
        player.sendMessage("§4You got eliminated");
        Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
        player.teleport(lobby);
        main.scoreboard.getTeam("Blue").removePlayer(player);
        main.scoreboard.getTeam("Red").removePlayer(player);
        main.scoreboard.getTeam("Green").removePlayer(player);
        main.scoreboard.getTeam("Yellow").removePlayer(player);
        player.getInventory().clear();
        player.setLevel(0);
        player.setHealth(20);
        if(main.scoreboard.getTeam("Blue").getSize() == 0){
            main.activeteams.remove("Blue");
        }
        if(main.scoreboard.getTeam("Red").getSize() == 0){
            main.activeteams.remove("Red");
        }
        if(main.scoreboard.getTeam("Green").getSize() == 0){
            main.activeteams.remove("Green");
        }
        if(main.scoreboard.getTeam("Yellow").getSize() == 0){
            main.activeteams.remove("Yellow");
        }
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        checkWin();
    }
    public void checkWin() {
        if(main.activeteams.size() <= 1){
            Bukkit.broadcastMessage(main.scoreboard.getTeam(main.activeteams.get(0)).getPrefix()+"Team §6Won the game");
            main.setState(MWstates.WAITING);
            main.activeteams.clear();
            restore();
            if(main.getConfig().getInt("reset") == 0){
                main.getConfig().set("reset", 1);
            }
            for(int i = 0; i < main.getPlayers().size(); i++)
            {
                Player winner = main.getPlayers().get(i);
                Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
                winner.teleport(lobby);
                main.getPlayers().remove(winner);
                winner.getInventory().clear();
                winner.setLevel(0);
                winner.setHealth(20);
                winner.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Entity victim = event.getEntity();
        if(main.getPlayers().contains(victim)){
            if(victim instanceof Player){
                Player player = (Player)victim;
                if(main.isState(MWstates.WAITING)){
                    event.setCancelled(true);
                }else if(main.isState(MWstates.STARTING)){
                    event.setCancelled(true);
                }else{
                    if(player.getHealth() <= event.getDamage()){
                        event.setDamage(0);
                        eliminate(player);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
        player.teleport(lobby);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        if(main.getPlayers().contains(player)){
            player.teleport(lobby);
            main.scoreboard.getTeam("Blue").removePlayer(player);
            main.scoreboard.getTeam("Red").removePlayer(player);
            main.scoreboard.getTeam("Green").removePlayer(player);
            main.scoreboard.getTeam("Yellow").removePlayer(player);
            if(main.isState(MWstates.WAITING)){
                main.getPlayers().remove(player);
                Bukkit.broadcastMessage(player.getName()+"§a left the MiniWalls game ! §7<§f" +main.getPlayers().size()+"§7/§f8§7>");
                player.setLevel(0);
                player.getInventory().clear();
            }else if(main.isState(MWstates.STARTING)){
                main.getPlayers().remove(player);
                Bukkit.broadcastMessage(player.getName()+"§a left the MiniWalls game ! §7<§f" +main.getPlayers().size()+"§7/§f8§7>");
                player.setLevel(0);
                player.getInventory().clear();
            }else{
                eliminate(player);
                for(Player list : main.getPlayers()) {
                    list.sendMessage("§e"+player.getName()+"§eleft the game MiniWalls");
                }
            }
        }
    }
    @EventHandler
    public void onLeave(PlayerCommandPreprocessEvent e){
        Player player = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("/mw leave")){
            Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
            if(main.getPlayers().contains(player)){
                player.teleport(lobby);
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                main.scoreboard.getTeam("Blue").removePlayer(player);
                main.scoreboard.getTeam("Red").removePlayer(player);
                main.scoreboard.getTeam("Green").removePlayer(player);
                main.scoreboard.getTeam("Yellow").removePlayer(player);
                if(main.isState(MWstates.WAITING)){
                    main.getPlayers().remove(player);
                    Bukkit.broadcastMessage(player.getName()+"§a left the MiniWalls game ! §7<§f" +main.getPlayers().size()+"§7/§f8§7>");
                    player.setLevel(0);
                    player.setHealth(20);
                    player.getInventory().clear();
                }else if(main.isState(MWstates.STARTING)){
                    main.getPlayers().remove(player);
                    Bukkit.broadcastMessage(player.getName()+"§a leaved the MiniWalls game ! §7<§f" +main.getPlayers().size()+"§7/§f8§7>");
                    player.setLevel(0);
                    player.setHealth(20);
                    player.getInventory().clear();
                }else{
                    eliminate(player);
                    for(Player list : main.getPlayers()) {
                        list.sendMessage("§e"+player.getName()+"§eleft the game MiniWalls");
                    }
                }
            }
        }
    }

}
