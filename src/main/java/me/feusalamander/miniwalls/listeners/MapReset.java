package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import java.util.LinkedList;
import java.util.List;
public class MapReset implements Listener {
    private final int t = 100;
    private final MiniWalls main;
    public MapReset(MiniWalls main) {
        this.main = main;
    }
    public static List<BlockState> CHANGES = new LinkedList<>();
    private final List<BlockState> CHANGES2 = new LinkedList<>();
    public void restore(){
        for(BlockState b : CHANGES2){
            b.getWorld().setType(b.getX(), b.getY(), b.getZ(), Material.AIR);
        }
        for(BlockState b : CHANGES){
            b.update(true, false);
        }
    }
    @EventHandler
    public void onBlockb(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(main.getPlayers().contains(p)){
            CHANGES.add(e.getBlock().getState());
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            if(main.scoreboard.getTeam("Blue").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.BLUE_WOOL));
            }else if(main.scoreboard.getTeam("Red").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.RED_WOOL));
            }else if(main.scoreboard.getTeam("Green").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.GREEN_WOOL));
            }else if(main.scoreboard.getTeam("Yellow").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.YELLOW_WOOL));
            }
        }else if(p.isOp() && p.getItemInHand().getType().equals(Material.BRICK)){
            if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6Wall 1 First pos selector")){
                e.setCancelled(true);
                p.getItemInHand().setAmount(0);
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 3, 1);
                main.getConfig().set("Walls.wall1.cord1.x", e.getBlock().getX());
                main.getConfig().set("Walls.wall1.cord1.y", e.getBlock().getY());
                main.getConfig().set("Walls.wall1.cord1.z", e.getBlock().getZ());
                main.saveConfig();
            }else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6Wall 1 Second pos selector")){
                e.setCancelled(true);
                p.getItemInHand().setAmount(0);
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 3, 1);
                main.getConfig().set("Walls.wall1.cord2.x", e.getBlock().getX());
                main.getConfig().set("Walls.wall1.cord2.y", e.getBlock().getY());
                main.getConfig().set("Walls.wall1.cord2.z", e.getBlock().getZ());
                main.saveConfig();
            }else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6Wall 2 First pos selector")){
                e.setCancelled(true);
                p.getItemInHand().setAmount(0);
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 3, 1);
                main.getConfig().set("Walls.wall2.cord1.x", e.getBlock().getX());
                main.getConfig().set("Walls.wall2.cord1.y", e.getBlock().getY());
                main.getConfig().set("Walls.wall2.cord1.z", e.getBlock().getZ());
                main.saveConfig();
            }else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6Wall 2 Second pos selector")){
                e.setCancelled(true);
                p.getItemInHand().setAmount(0);
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 3, 1);
                main.getConfig().set("Walls.wall2.cord2.x", e.getBlock().getX());
                main.getConfig().set("Walls.wall2.cord2.y", e.getBlock().getY());
                main.getConfig().set("Walls.wall2.cord2.z", e.getBlock().getZ());
                main.saveConfig();
            }
        }
    }
    @EventHandler
    public void onBlockp(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if(main.getPlayers().contains(p)){
            if(e.getBlock().getType() == Material.BLUE_WOOL){
                CHANGES2.add(e.getBlock().getState());
            }else if(e.getBlock().getType() == Material.RED_WOOL){
                CHANGES2.add(e.getBlock().getState());
            }else if(e.getBlock().getType() == Material.GREEN_WOOL){
                CHANGES2.add(e.getBlock().getState());
            }else if(e.getBlock().getType() == Material.YELLOW_WOOL){
                CHANGES2.add(e.getBlock().getState());
            }
        }
    }
    private void eliminate(Player p, Boolean s) {
        if(main.getPlayers().contains(p)) main.getPlayers().remove(p);
        if(s) main.getSpec().add(p);
        p.sendMessage("§4You got eliminated");
        Location spec  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Destruction.center.x"), main.getConfig().getInt("Destruction.center.y"), main.getConfig().getInt("Destruction.center.z"));
        Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
        if(s){
            p.teleport(spec);
        }else{
            p.teleport(lobby);
        }
        main.scoreboard.getTeam("Blue").removePlayer(p);
        main.scoreboard.getTeam("Red").removePlayer(p);
        main.scoreboard.getTeam("Green").removePlayer(p);
        main.scoreboard.getTeam("Yellow").removePlayer(p);
        p.getInventory().clear();
        p.setLevel(0);
        p.setHealth(20);
        p.setFoodLevel(20);
        PlayerData.setloses(p, PlayerData.getloses(p)+1);
        if(s) {
            p.setGameMode(GameMode.SPECTATOR);
        }else{
            p.setGameMode(GameMode.SURVIVAL);
        }
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
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        checkWin();
    }
    public void checkWin() {
        if (main.activeteams.size() <= 1) {
            restore();
            Bukkit.broadcastMessage(main.scoreboard.getTeam(main.activeteams.get(0)).getPrefix() + "Team §6Won the game");
            main.setState(MWstates.WAITING);
            main.activeteams.clear();
            main.scoreboard.getTeam("bv").setSuffix("");
            main.scoreboard.getTeam("rv").setSuffix("");
            main.scoreboard.getTeam("gv").setSuffix("");
            main.scoreboard.getTeam("yv").setSuffix("");
            for (int i = 0; i < main.getPlayers().size(); i++) {
                Player winner = main.getPlayers().get(i);
                Location lobby = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
                winner.teleport(lobby);
                main.getPlayers().remove(winner);
                winner.getInventory().clear();
                winner.setLevel(0);
                winner.setFoodLevel(20);
                winner.setHealth(20);
                PlayerData.setWins(winner, PlayerData.getWins(winner)+1);
                winner.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                for (Entity entity : Bukkit.getWorld("world").getEntities()) {
                    String entity1 = entity.getName();
                    if(entity1.equals("§9Blue Villager")) {
                        entity.remove();
                    }else if(entity1.equals("§cRed Villager")) {
                        entity.remove();
                    }else if(entity1.equals("§aGreen Villager")) {
                        entity.remove();
                    }else if(entity1.equals("§eYellow Villager")) {
                        entity.remove();
                    }
                }
            }
            for (int i = 0; i < main.getSpec().size(); i++) {
                Player spec = main.getSpec().get(i);
                Location lobby = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
                spec.teleport(lobby);
                main.getSpec().remove(spec);
                spec.getInventory().clear();
                spec.setLevel(0);
                spec.setFoodLevel(20);
                spec.setHealth(20);
                spec.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        Entity damagers = event.getDamager();
        Entity victim = event.getEntity();
        if(main.getPlayers().contains(victim)){
            if(victim instanceof Player){
                if(damagers instanceof Player damager){
                    Player player = (Player)victim;
                    if(main.isState(MWstates.WAITING)){
                        event.setCancelled(true);
                    }else if(main.isState(MWstates.STARTING)){
                        event.setCancelled(true);
                    }else if(player.getHealth() <= event.getDamage()){
                        if(main.scoreboard.getTeam("Blue").hasPlayer(player)){
                            if(main.blife <= 0){
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setfinal(damager, PlayerData.getfinal(damager)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§9"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(false, player, damager, "§9", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.BlueBase.x"), main.getConfig().getInt("Locations.Bases.BlueBase.y"), main.getConfig().getInt("Locations.Bases.BlueBase.z")));
                            }
                        }
                        if(main.scoreboard.getTeam("Red").hasPlayer(player)){
                            if(main.rlife <= 0){
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setfinal(damager, PlayerData.getfinal(damager)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§c"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(false, player, damager, "§c", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.RedBase.x"), main.getConfig().getInt("Locations.Bases.RedBase.y"), main.getConfig().getInt("Locations.Bases.RedBase.z")));
                            }
                        }
                        if(main.scoreboard.getTeam("Green").hasPlayer(player)){
                            if(main.glife <= 0){
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setfinal(damager, PlayerData.getfinal(damager)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§a"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(false, player, damager, "§a", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.GreenBase.x"), main.getConfig().getInt("Locations.Bases.GreenBase.y"), main.getConfig().getInt("Locations.Bases.GreenBase.z")));
                            }
                        }
                        if(main.scoreboard.getTeam("Yellow").hasPlayer(player)){
                            if(main.ylife <= 0){
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setfinal(damager, PlayerData.getfinal(damager)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§e"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(false, player, damager, "§e", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.YellowBase.x"), main.getConfig().getInt("Locations.Bases.YellowBase.y"), main.getConfig().getInt("Locations.Bases.YellowBase.z")));
                            }
                        }
                    }
                }else if(damagers instanceof Arrow){
                    ProjectileSource shooter = ((Arrow)event.getDamager()).getShooter();
                    Player s = (Player)shooter;
                    Player player = (Player)victim;
                    if(main.isState(MWstates.WAITING)){
                        event.setCancelled(true);
                    }else if(main.isState(MWstates.STARTING)){
                        event.setCancelled(true);
                    }else if(player.getHealth() <= event.getDamage()){
                        if(main.scoreboard.getTeam("Blue").hasPlayer(player)){
                            if(main.blife <= 0){
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setfinal(s, PlayerData.getfinal(s)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§9"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(false, player, s, "§9", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.BlueBase.x"), main.getConfig().getInt("Locations.Bases.BlueBase.y"), main.getConfig().getInt("Locations.Bases.BlueBase.z")));
                            }
                        }else if(main.scoreboard.getTeam("Red").hasPlayer(player)){
                            if(main.rlife <= 0){
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setfinal(s, PlayerData.getfinal(s)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§c"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(false, player, s, "§c", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.RedBase.x"), main.getConfig().getInt("Locations.Bases.RedBase.y"), main.getConfig().getInt("Locations.Bases.RedBase.z")));
                            }
                        }else if(main.scoreboard.getTeam("Green").hasPlayer(player)){
                            if(main.glife <= 0){
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setfinal(s, PlayerData.getfinal(s)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§a"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(false, player, s, "§a", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.GreenBase.x"), main.getConfig().getInt("Locations.Bases.GreenBase.y"), main.getConfig().getInt("Locations.Bases.GreenBase.z")));
                            }
                        }else if(main.scoreboard.getTeam("Yellow").hasPlayer(player)){
                            if(main.ylife <= 0){
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setfinal(s, PlayerData.getfinal(s)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§e"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(false, player, s, "§e", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.YellowBase.x"), main.getConfig().getInt("Locations.Bases.YellowBase.y"), main.getConfig().getInt("Locations.Bases.YellowBase.z")));
                            }
                        }
                    }else if(player.getHealth() > event.getDamage()){
                        double h = player.getHealth()-event.getDamage();
                        if(main.scoreboard.getTeam("Blue").hasPlayer(player)){
                            s.sendMessage("§9"+player.getName()+"§7 is on §c"+(int)h+" §7HP!");
                        }else if(main.scoreboard.getTeam("Red").hasPlayer(player)){
                            s.sendMessage("§c"+player.getName()+"§7 is on §c"+(int)h+" §7HP!");
                        }else if(main.scoreboard.getTeam("Green").hasPlayer(player)){
                            s.sendMessage("§a"+player.getName()+"§7 is on §c"+(int)h+" §7HP!");
                        }else if(main.scoreboard.getTeam("Yellow").hasPlayer(player)){
                            s.sendMessage("§e"+player.getName()+"§7 is on §c"+(int)h+" §7HP!");
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onDamagevoid(EntityDamageEvent event) {
        Entity victim = event.getEntity();
        if (main.getPlayers().contains(victim)) {
            if (victim instanceof Player player) {
                if (main.isState(MWstates.WAITING)) {
                    event.setCancelled(true);
                }else if (main.isState(MWstates.STARTING)) {
                    event.setCancelled(true);
                }else if (player.getHealth() <= event.getDamage()) {
                    if (event.getCause() == EntityDamageEvent.DamageCause.VOID){
                        if(main.scoreboard.getTeam("Blue").hasPlayer(player)) {
                            if (main.blife <= 0) {
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§9" + player.getName() + " has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(true, player, null, "§9", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.BlueBase.x"), main.getConfig().getInt("Locations.Bases.BlueBase.y"), main.getConfig().getInt("Locations.Bases.BlueBase.z")));
                            }
                        }else if(main.scoreboard.getTeam("Red").hasPlayer(player)) {
                            if (main.rlife <= 0) {
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§c" + player.getName() + " has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(true, player, null, "§c", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.RedBase.x"), main.getConfig().getInt("Locations.Bases.RedBase.y"), main.getConfig().getInt("Locations.Bases.RedBase.z")));
                            }
                        }else if (main.scoreboard.getTeam("Green").hasPlayer(player)) {
                            if(main.glife <= 0) {
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§a" + player.getName() + " has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(true, player, null, "§a", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.GreenBase.x"), main.getConfig().getInt("Locations.Bases.GreenBase.y"), main.getConfig().getInt("Locations.Bases.GreenBase.z")));
                            }
                        }else if(main.scoreboard.getTeam("Yellow").hasPlayer(player)) {
                            if (main.ylife <= 0) {
                                event.setDamage(0);
                                eliminate(player, true);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§e" + player.getName() + " has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                kill(true, player, null, "§e", new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.YellowBase.x"), main.getConfig().getInt("Locations.Bases.YellowBase.y"), main.getConfig().getInt("Locations.Bases.YellowBase.z")));
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
        p.teleport(lobby);
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        if(main.getPlayers().contains(p)){
            p.teleport(lobby);
            main.scoreboard.getTeam("Blue").removePlayer(p);
            main.scoreboard.getTeam("Red").removePlayer(p);
            main.scoreboard.getTeam("Green").removePlayer(p);
            main.scoreboard.getTeam("Yellow").removePlayer(p);
            if(main.isState(MWstates.WAITING) || main.isState(MWstates.STARTING)){
                main.getPlayers().remove(p);
                Bukkit.broadcastMessage(p.getName()+"§a left the MiniWalls game ! §7<§f" +main.getPlayers().size()+"§7/§f8§7>");
                p.setLevel(0);
                p.setFoodLevel(20);
                p.getInventory().clear();
            }else{
                eliminate(p, false);
                PlayerData.setdeath(p, PlayerData.getdeath(p)+1);
                for(Player list : main.getPlayers()) {
                    list.sendMessage("§e"+ p.getName()+"§eleft the game MiniWalls");
                }
            }
        }else if(main.getSpec().contains(p)){
            p.teleport(lobby);
            p.setGameMode(GameMode.SURVIVAL);
        }
    }
    @EventHandler
    public void onLeave(PlayerCommandPreprocessEvent e){
        if (e.getMessage().equalsIgnoreCase("/mw leave")){
            Player p = e.getPlayer();
            Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
            if(main.getPlayers().contains(p)){
                p.teleport(lobby);
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                main.scoreboard.getTeam("Blue").removePlayer(p);
                main.scoreboard.getTeam("Red").removePlayer(p);
                main.scoreboard.getTeam("Green").removePlayer(p);
                main.scoreboard.getTeam("Yellow").removePlayer(p);
                if(main.isState(MWstates.WAITING) || main.isState(MWstates.STARTING)){
                    main.getPlayers().remove(p);
                    Bukkit.broadcastMessage(p.getName()+"§a left the MiniWalls game ! §7<§f" +main.getPlayers().size()+"§7/§f8§7>");
                    p.setLevel(0);
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.getInventory().clear();
                }else{
                    eliminate(p, false);
                    PlayerData.setdeath(p, PlayerData.getdeath(p)+1);
                    for(Player list : main.getPlayers()) {
                        list.sendMessage("§e"+ p.getName()+"§eleft the game MiniWalls");
                    }
                }
            }else if(main.getSpec().contains(p)){
                p.teleport(lobby);
                p.setGameMode(GameMode.SURVIVAL);
            }
        }
    }
    private void kill(Boolean voids, Player p, Player d, String c, Location spawn){
        if(voids){
            p.setHealth(20);
            p.setGameMode(GameMode.SPECTATOR);
            p.getInventory().setItem( 8, new ItemStack(Material.ARROW, 5));
            p.setFoodLevel(20);
            p.setFallDistance(0);
            p.teleport(spawn);
            PlayerData.setdeath(p, PlayerData.getdeath(p)+1);
            p.sendTitle("§cYou will respawn in", "§c5s", 1, 20, 1);
            Bukkit.getScheduler().runTaskLater(main, () -> {
                p.sendTitle("§c4", "", 1, 20, 1);
                Bukkit.getScheduler().runTaskLater(main, () -> {
                    p.sendTitle("§c3", "", 1, 20, 1);
                    Bukkit.getScheduler().runTaskLater(main, () -> {
                        p.sendTitle("§c2", "", 1, 20, 1);
                        Bukkit.getScheduler().runTaskLater(main, () -> p.sendTitle("§c1", "", 1, 20, 1), 20);
                    }, 20);
                }, 20);
            }, 20);
            Bukkit.getScheduler().runTaskLater(main, () -> {
                p.teleport(spawn);
                p.setGameMode(GameMode.SURVIVAL);
            }, t);
            for (Player list : main.getPlayers()) {
                list.sendMessage(c+p.getName() + " fell in the void");
            }
        }else{
            p.setHealth(20);
            p.setGameMode(GameMode.SPECTATOR);
            p.getInventory().setItem( 8, new ItemStack(Material.ARROW, 3));
            p.setFoodLevel(20);
            PlayerData.setkills(d, PlayerData.getkills(d)+1);
            PlayerData.setdeath(p, PlayerData.getdeath(p)+1);
            p.sendTitle("§cYou will respawn in", "§c5s", 1, 30, 1);
            Bukkit.getScheduler().runTaskLater(main, () -> {
                p.sendTitle("§c4", "", 1, 20, 1);
                Bukkit.getScheduler().runTaskLater(main, () -> {
                    p.sendTitle("§c3", "", 1, 20, 1);
                    Bukkit.getScheduler().runTaskLater(main, () -> {
                        p.sendTitle("§c2", "", 1, 20, 1);
                        Bukkit.getScheduler().runTaskLater(main, () -> {
                            p.sendTitle("§c1", "", 1, 20, 1);
                        }, 20);
                    }, 20);
                }, 20);
            }, 20);
            Bukkit.getScheduler().runTaskLater(main, () -> {
                p.teleport(spawn);
                p.setGameMode(GameMode.SURVIVAL);
            }, t);
            for(Player list : main.getPlayers()) {
                list.sendMessage(c+p.getName()+" has been killed by "+d.getName());
            }
        }
    }
}
