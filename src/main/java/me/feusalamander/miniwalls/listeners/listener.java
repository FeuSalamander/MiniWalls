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
import org.bukkit.event.player.PlayerInteractEvent;
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
                Location spawn  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Spawn.x"), main.getConfig().getInt("Locations.Spawn.y"), main.getConfig().getInt("Locations.Spawn.z"));
                player.teleport(spawn);
                player.setHealth(20);
                player.setFoodLevel(20);
                player.getInventory().clear();
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
                ItemMeta wooly = ywool.getItemMeta();
                wooly.setDisplayName("§6Click to join the §eYellow Team !");
                ywool.setItemMeta(wooly);
                player.getInventory().setItem(8, ywool);
                ItemStack leave = new ItemStack(Material.IRON_TRAPDOOR);
                ItemMeta leave2 = leave.getItemMeta();
                leave2.setDisplayName("§cClick to leave");
                leave.setItemMeta(leave2);
                player.getInventory().setItem(0, leave);
                e.getPlayer().setScoreboard(main.scoreboard);
                main.scoreboard.getTeam("playerss").setSuffix("§a" +main.getPlayers().size()+ "/§a8");
                main.scoreboard.getTeam("playerss").setPrefix("Waiting ");
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
    public void onReload(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if(e.getMessage().equalsIgnoreCase("/mw reload")){
            if(player.hasPermission("mw.admin")){
                player.sendMessage("The configuration file of MiniWalls got successfully reloaded");
                main.reloadConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }
    }
    @EventHandler
    public  void jointeam(PlayerInteractEvent e){
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        if(main.getPlayers().contains(player)){
            if(player.getItemInHand().getType() == Material.BLUE_WOOL){
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§1Click to join the §9Blue Team !")){
                    if(main.scoreboard.getTeam("Blue").getSize() < 2){
                        main.scoreboard.getTeam("Blue").addPlayer(player);
                        player.sendMessage("§9You joined the Blue Team");
                    }else{
                        player.sendMessage("This team is full");
                    }
                }
            }else if(player.getItemInHand().getType() == Material.RED_WOOL){
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§4Click to join the §cRed Team !")){
                    if(main.scoreboard.getTeam("Red").getSize() < 2){
                        main.scoreboard.getTeam("Red").addPlayer(player);
                        player.sendMessage("§cYou joined the Red Team");
                    }else{
                        player.sendMessage("This team is full");
                    }
                }
            }else if(player.getItemInHand().getType() == Material.GREEN_WOOL){
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§2Click to join the §aGreen Team !")){
                    if(main.scoreboard.getTeam("Green").getSize() < 2){
                        main.scoreboard.getTeam("Green").addPlayer(player);
                        player.sendMessage("§aYou joined the Green Team");
                    }else{
                        player.sendMessage("This team is full");
                    }
                }
            }else if(player.getItemInHand().getType() == Material.YELLOW_WOOL){
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6Click to join the §eYellow Team !")){
                    if(main.scoreboard.getTeam("Yellow").getSize() < 2){
                        main.scoreboard.getTeam("Yellow").addPlayer(player);
                        player.sendMessage("§eYou joined the Yellow Team");
                    }else{
                        player.sendMessage("This team is full");
                    }
                }
            }else if(player.getItemInHand().getType() == Material.IRON_TRAPDOOR){
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§cClick to leave")){
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
                            main.eliminate(player);
                            for(Player list : main.getPlayers()) {
                                list.sendMessage("§e"+player.getName()+"§eleft the game MiniWalls");
                                }
                            }
                        }
                    }
                }
            }
        }
}