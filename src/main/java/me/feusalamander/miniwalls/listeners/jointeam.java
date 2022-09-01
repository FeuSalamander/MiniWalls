package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
public class jointeam implements Listener {
    private MiniWalls main;
    public jointeam(MiniWalls main) {
        this.main = main;
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
    @EventHandler
    public void ondmg(EntityDamageByEntityEvent e){
        Entity victim = e.getEntity();
        Entity damager = e.getDamager();
        if(damager instanceof Player){
            Player player = (Player)damager;
            if(main.getPlayers().contains(player)){
                if(victim.getName().equalsIgnoreCase("§9Blue Villager")){
                    if(!main.scoreboard.getTeam("Blue").hasPlayer(player)){
                        main.blife--;
                        if(main.blife == 0){
                            for(Player list : main.getPlayers()) {
                                list.sendMessage("§9The Blue Villager died");
                            }
                            e.setDamage(800);
                        }
                    }else{
                        e.setCancelled(true);
                    }
                }else if(victim.getName().equalsIgnoreCase("§cRed Villager")){
                    if(!main.scoreboard.getTeam("Red").hasPlayer(player)){
                        main.rlife--;
                        if(main.rlife == 0){
                            for(Player list : main.getPlayers()) {
                                list.sendMessage("§cThe Red Villager died");
                            }
                            e.setDamage(800);
                        }
                    }else{
                        e.setCancelled(true);
                    }
                }else if(victim.getName().equalsIgnoreCase("§aGreen Villager")){
                    if(!main.scoreboard.getTeam("Green").hasPlayer(player)){
                        main.glife--;
                        if(main.glife == 0){
                            for(Player list : main.getPlayers()) {
                                list.sendMessage("§aThe Green Villager died");
                            }
                            e.setDamage(800);
                        }
                    }else{
                        e.setCancelled(true);
                    }
                }else if(victim.getName().equalsIgnoreCase("§eYellow Villager")){
                    if(!main.scoreboard.getTeam("Yellow").hasPlayer(player)){
                        main.ylife--;
                        if(main.ylife == 0){
                            for(Player list : main.getPlayers()) {
                                list.sendMessage("§eThe Yellow Villager died");
                            }
                            e.setDamage(800);
                        }
                    }else{
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}