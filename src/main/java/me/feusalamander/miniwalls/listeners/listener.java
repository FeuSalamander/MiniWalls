package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import me.feusalamander.miniwalls.tasks.MWautostart;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class listener implements Listener {
    private MiniWalls main;
    public listener(MiniWalls main) {
        this.main = main;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        Player player = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("/mw join")){
            Location spawn  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Spawn.x"), main.getConfig().getInt("Locations.Spawn.y"), main.getConfig().getInt("Locations.Spawn.z"));
            player.teleport(spawn);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.getInventory().clear();
            if(!main.isState(MWstates.WAITING))
            {
                if(!main.getPlayers().contains(player)){
                    Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
                    player.teleport(lobby);
                    player.sendMessage("§7The game is already started !");
                    return;
                }
            }
            if(!main.getPlayers().contains(player)) {
                main.getPlayers().add(player);
                Bukkit.broadcastMessage(player.getName() + "§a joined the MiniWalls game ! §7<§f" + main.getPlayers().size() + "§7/§f8§7>");
                player.setGameMode(GameMode.ADVENTURE);
                ItemStack bwool = new ItemStack(Material.BLUE_WOOL);
                ItemMeta woolb = bwool.getItemMeta();
                woolb.setDisplayName("§1Click to join the §9Blue Team !");
                bwool.setItemMeta(woolb);
                player.getInventory().setItem(5, bwool);
                ItemStack rwool = new ItemStack(Material.RED_WOOL);
                ItemMeta woolr = bwool.getItemMeta();
                woolr.setDisplayName("§4Click to join the §cRed Team !");
                rwool.setItemMeta(woolr);
                player.getInventory().setItem(6, rwool);
                ItemStack gwool = new ItemStack(Material.GREEN_WOOL);
                ItemMeta woolg = gwool.getItemMeta();
                woolg.setDisplayName("§2Click to join the §aGreen Team !");
                gwool.setItemMeta(woolg);
                player.getInventory().setItem(7, gwool);
                ItemStack ywool = new ItemStack(Material.YELLOW_WOOL);
                ItemMeta wooly = bwool.getItemMeta();
                wooly.setDisplayName("§6Click to join the §eYellow Team !");
                ywool.setItemMeta(wooly);
                player.getInventory().setItem(8, ywool);
            }
            if(main.isState(MWstates.WAITING)&& main.getPlayers().size() >= main.getConfig().getInt("MinPlayers"))
            {
                MWautostart start = new MWautostart(main);
                start.runTaskTimer(main, 0, 20);
                main.setState(MWstates.STARTING);

            }
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
            Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
            player.teleport(lobby);
            if(main.getPlayers().contains(player)){
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
                    main.eliminate(player);
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
            player.teleport(lobby);
            if(main.getPlayers().contains(player)){
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
                    main.eliminate(player);
                    for(Player list : main.getPlayers()) {
                        list.sendMessage("§e"+player.getName()+"§eleft the game MiniWalls");
                    }
                }
        }
    }
}

    @EventHandler
    public void onReload(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if(e.getMessage().equalsIgnoreCase("/mw reload")){
            if(player.hasPermission("mw.reload")){
                player.sendMessage("The configuration file of MiniWalls got successfully reloaded");
                main.reloadConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }
    }
}

