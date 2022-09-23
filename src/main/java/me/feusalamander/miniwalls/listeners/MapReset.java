package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.*;
import org.bukkit.block.Block;
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
    private int t = 100;
    private MiniWalls main;
    public MapReset(MiniWalls main) {
        this.main = main;
    }
    public static List<String> CHANGES = new LinkedList<String>();
    private final List<String> CHANGES2 = new LinkedList<String>();
    public static List<String> CHANGES3 = new LinkedList<String>();
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
        for(String b : CHANGES3){
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
            }else if(main.scoreboard.getTeam("Yellow").hasPlayer(p)){
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
        player.setFoodLevel(20);
        PlayerData.setloses(player, PlayerData.getloses(player)+1);
        player.setGameMode(GameMode.SURVIVAL);
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
        World world = Bukkit.getWorld("world");
        if (main.activeteams.size() <= 1) {
            Bukkit.broadcastMessage(main.scoreboard.getTeam(main.activeteams.get(0)).getPrefix() + "Team §6Won the game");
            main.setState(MWstates.WAITING);
            main.activeteams.clear();
            main.scoreboard.getTeam("bv").setSuffix("");
            main.scoreboard.getTeam("rv").setSuffix("");
            main.scoreboard.getTeam("gv").setSuffix("");
            main.scoreboard.getTeam("yv").setSuffix("");
            restore();
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
        }
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        Entity damagers = event.getDamager();
        Entity victim = event.getEntity();
        if(main.getPlayers().contains(victim)){
            if(victim instanceof Player){
                if(damagers instanceof Player){
                    Player player = (Player)victim;
                    Player damager = (Player)damagers;
                    if(main.isState(MWstates.WAITING)){
                        event.setCancelled(true);
                    }else if(main.isState(MWstates.STARTING)){
                        event.setCancelled(true);
                    }else if(player.getHealth() <= event.getDamage()){
                        if(main.scoreboard.getTeam("Blue").hasPlayer(player)){
                            if(main.blife <= 0){
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setfinal(damager, PlayerData.getfinal(damager)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§9"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                killb(player, damager);
                            }
                        }
                        if(main.scoreboard.getTeam("Red").hasPlayer(player)){
                            if(main.rlife <= 0){
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setfinal(damager, PlayerData.getfinal(damager)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§c"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                killr(player, damager);
                            }
                        }
                        if(main.scoreboard.getTeam("Green").hasPlayer(player)){
                            if(main.glife <= 0){
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setfinal(damager, PlayerData.getfinal(damager)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§a"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                killg(player, damager);
                            }
                        }
                        if(main.scoreboard.getTeam("Yellow").hasPlayer(player)){
                            if(main.ylife <= 0){
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setfinal(damager, PlayerData.getfinal(damager)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§e"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                killy(player, damager);
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
                                eliminate(player);
                                PlayerData.setfinal(s, PlayerData.getfinal(s)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§9"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                killb(player, s);
                            }
                        }else if(main.scoreboard.getTeam("Red").hasPlayer(player)){
                            if(main.rlife <= 0){
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setfinal(s, PlayerData.getfinal(s)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§c"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                killr(player, s);
                            }
                        }else if(main.scoreboard.getTeam("Green").hasPlayer(player)){
                            if(main.glife <= 0){
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setfinal(s, PlayerData.getfinal(s)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§a"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                killg(player, s);
                            }
                        }else if(main.scoreboard.getTeam("Yellow").hasPlayer(player)){
                            if(main.ylife <= 0){
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setfinal(s, PlayerData.getfinal(s)+1);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§e"+player.getName()+" has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                killy(player, s);
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
            if (victim instanceof Player) {
                Player player = (Player) victim;
                if (main.isState(MWstates.WAITING)) {
                    event.setCancelled(true);
                }else if (main.isState(MWstates.STARTING)) {
                    event.setCancelled(true);
                }else if (player.getHealth() <= event.getDamage()) {
                    if (event.getCause() == EntityDamageEvent.DamageCause.VOID){
                        Location spawnblue = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.BlueBase.x"), main.getConfig().getInt("Locations.Bases.BlueBase.y"), main.getConfig().getInt("Locations.Bases.BlueBase.z"));
                        Location spawnred = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.RedBase.x"), main.getConfig().getInt("Locations.Bases.RedBase.y"), main.getConfig().getInt("Locations.Bases.RedBase.z"));
                        Location spawngreen = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.GreenBase.x"), main.getConfig().getInt("Locations.Bases.GreenBase.y"), main.getConfig().getInt("Locations.Bases.GreenBase.z"));
                        Location spawnyellow = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.YellowBase.x"), main.getConfig().getInt("Locations.Bases.YellowBase.y"), main.getConfig().getInt("Locations.Bases.YellowBase.z"));
                        if(main.scoreboard.getTeam("Blue").hasPlayer(player)) {
                            if (main.blife <= 0) {
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§9" + player.getName() + " has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                player.setHealth(20);
                                player.setGameMode(GameMode.SPECTATOR);
                                player.getInventory().setItem( 8, new ItemStack(Material.ARROW, 5));
                                player.setFoodLevel(20);
                                player.setFallDistance(0);
                                player.teleport(spawnblue);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                player.sendTitle("§cYou will respawn in", "§c5s", 1, 20, 1);
                                Bukkit.getScheduler().runTaskLater(main, () -> {
                                    player.sendTitle("§c4", "", 1, 20, 1);
                                    Bukkit.getScheduler().runTaskLater(main, () -> {
                                        player.sendTitle("§c3", "", 1, 20, 1);
                                        Bukkit.getScheduler().runTaskLater(main, () -> {
                                            player.sendTitle("§c2", "", 1, 20, 1);
                                            Bukkit.getScheduler().runTaskLater(main, () -> {
                                                player.sendTitle("§c1", "", 1, 20, 1);
                                            }, 20);
                                        }, 20);
                                    }, 20);
                                }, 20);
                                Bukkit.getScheduler().runTaskLater(main, () -> {
                                    player.teleport(spawnblue);
                                    player.setGameMode(GameMode.SURVIVAL);
                                }, t);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§9" + player.getName() + " fell in the void");
                                }
                            }
                        }else if(main.scoreboard.getTeam("Red").hasPlayer(player)) {
                            if (main.rlife <= 0) {
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§c" + player.getName() + " has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                player.setHealth(20);
                                player.setGameMode(GameMode.SPECTATOR);
                                player.getInventory().setItem( 8, new ItemStack(Material.ARROW, 5));
                                player.setFoodLevel(20);
                                player.setFallDistance(0);
                                player.teleport(spawnred);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                player.sendTitle("§cYou will respawn in", "§c5s", 1, 20, 1);
                                Bukkit.getScheduler().runTaskLater(main, () -> {
                                    player.sendTitle("§c4", "", 1, 20, 1);
                                    Bukkit.getScheduler().runTaskLater(main, () -> {
                                        player.sendTitle("§c3", "", 1, 20, 1);
                                        Bukkit.getScheduler().runTaskLater(main, () -> {
                                            player.sendTitle("§c2", "", 1, 20, 1);
                                            Bukkit.getScheduler().runTaskLater(main, () -> {
                                                player.sendTitle("§c1", "", 1, 20, 1);
                                            }, 20);
                                        }, 20);
                                    }, 20);
                                }, 20);
                                Bukkit.getScheduler().runTaskLater(main, () -> {
                                    player.teleport(spawnred);
                                    player.setGameMode(GameMode.SURVIVAL);
                                }, t);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§c" + player.getName() + " fell in the void");
                                }
                            }
                        }else if (main.scoreboard.getTeam("Green").hasPlayer(player)) {
                            if(main.glife <= 0) {
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§a" + player.getName() + " has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                player.setHealth(20);
                                player.setGameMode(GameMode.SPECTATOR);
                                player.getInventory().setItem( 8, new ItemStack(Material.ARROW, 5));
                                player.setFoodLevel(20);
                                player.setFallDistance(0);
                                player.teleport(spawngreen);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                player.sendTitle("§cYou will respawn in", "§c5s", 1, 20, 1);
                                Bukkit.getScheduler().runTaskLater(main, () -> {
                                    player.sendTitle("§c4", "", 1, 20, 1);
                                    Bukkit.getScheduler().runTaskLater(main, () -> {
                                        player.sendTitle("§c3", "", 1, 20, 1);
                                        Bukkit.getScheduler().runTaskLater(main, () -> {
                                            player.sendTitle("§c2", "", 1, 20, 1);
                                            Bukkit.getScheduler().runTaskLater(main, () -> {
                                                player.sendTitle("§c1", "", 1, 20, 1);
                                            }, 20);
                                        }, 20);
                                    }, 20);
                                }, 20);
                                Bukkit.getScheduler().runTaskLater(main, () -> {
                                    player.teleport(spawngreen);
                                    player.setGameMode(GameMode.SURVIVAL);
                                }, t);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§a" + player.getName() + " fell in the void");
                                }
                            }
                        }else if(main.scoreboard.getTeam("Yellow").hasPlayer(player)) {
                            if (main.ylife <= 0) {
                                event.setDamage(0);
                                eliminate(player);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§e" + player.getName() + " has been eliminated");
                                }
                            }else{
                                event.setDamage(0);
                                player.setHealth(20);
                                player.setGameMode(GameMode.SPECTATOR);
                                player.getInventory().setItem( 8, new ItemStack(Material.ARROW, 5));
                                player.setFoodLevel(20);
                                player.setFallDistance(0);
                                player.teleport(spawnyellow);
                                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                                player.sendTitle("§cYou will respawn in", "§c5s", 1, 20, 1);
                                Bukkit.getScheduler().runTaskLater(main, () -> {
                                    player.sendTitle("§c4", "", 1, 20, 1);
                                    Bukkit.getScheduler().runTaskLater(main, () -> {
                                        player.sendTitle("§c3", "", 1, 20, 1);
                                        Bukkit.getScheduler().runTaskLater(main, () -> {
                                            player.sendTitle("§c2", "", 1, 20, 1);
                                            Bukkit.getScheduler().runTaskLater(main, () -> {
                                                player.sendTitle("§c1", "", 1, 20, 1);
                                            }, 20);
                                        }, 20);
                                    }, 20);
                                }, 20);
                                Bukkit.getScheduler().runTaskLater(main, () -> {
                                    player.teleport(spawnyellow);
                                    player.setGameMode(GameMode.SURVIVAL);
                                }, t);
                                for (Player list : main.getPlayers()) {
                                    list.sendMessage("§e" + player.getName() + " fell in the void");
                                }
                            }
                        }
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
                player.setFoodLevel(20);
                player.getInventory().clear();
            }else if(main.isState(MWstates.STARTING)){
                main.getPlayers().remove(player);
                Bukkit.broadcastMessage(player.getName()+"§a left the MiniWalls game ! §7<§f" +main.getPlayers().size()+"§7/§f8§7>");
                player.setLevel(0);
                player.setFoodLevel(20);
                player.getInventory().clear();
            }else{
                eliminate(player);
                PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
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
                    player.setFoodLevel(20);
                    player.getInventory().clear();
                }else if(main.isState(MWstates.STARTING)){
                    main.getPlayers().remove(player);
                    Bukkit.broadcastMessage(player.getName()+"§a leaved the MiniWalls game ! §7<§f" +main.getPlayers().size()+"§7/§f8§7>");
                    player.setLevel(0);
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.getInventory().clear();
                }else{
                    eliminate(player);
                    PlayerData.setdeath(player, PlayerData.getdeath(player)+1);
                    for(Player list : main.getPlayers()) {
                        list.sendMessage("§e"+player.getName()+"§eleft the game MiniWalls");
                    }
                }
            }
        }
    }
    private void killb(Player p, Player d){
        Location spawnblue  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.BlueBase.x"), main.getConfig().getInt("Locations.Bases.BlueBase.y"), main.getConfig().getInt("Locations.Bases.BlueBase.z"));
        p.setHealth(20);
        p.setGameMode(GameMode.SPECTATOR);
        p.getInventory().setItem( 8, new ItemStack(Material.ARROW, 5));
        p.setFoodLevel(20);
        PlayerData.setkills(d, PlayerData.getkills(d)+1);
        PlayerData.setdeath(p, PlayerData.getdeath(p)+1);
        p.sendTitle("§cYou will respawn in", "§c5s", 1, 20, 1);
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
            p.teleport(spawnblue);
            p.setGameMode(GameMode.SURVIVAL);
        }, t);
        for(Player list : main.getPlayers()) {
            list.sendMessage("§9"+p.getName()+" has been killed by "+d.getName());
        }
    }
    private void killr(Player p, Player d){
        Location spawnred  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.RedBase.x"), main.getConfig().getInt("Locations.Bases.RedBase.y"), main.getConfig().getInt("Locations.Bases.RedBase.z"));
        p.setHealth(20);
        p.setGameMode(GameMode.SPECTATOR);
        p.getInventory().setItem( 8, new ItemStack(Material.ARROW, 5));
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
            p.teleport(spawnred);
            p.setGameMode(GameMode.SURVIVAL);
        }, t);
        for(Player list : main.getPlayers()) {
            list.sendMessage("§c"+p.getName()+" has been killed by "+d.getName());
        }
    }
    private void killg(Player p, Player d){
        Location spawngreen  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.GreenBase.x"), main.getConfig().getInt("Locations.Bases.GreenBase.y"), main.getConfig().getInt("Locations.Bases.GreenBase.z"));
        p.setHealth(20);
        p.setGameMode(GameMode.SPECTATOR);
        p.getInventory().setItem( 8, new ItemStack(Material.ARROW, 5));
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
            p.teleport(spawngreen);
            p.setGameMode(GameMode.SURVIVAL);
        }, t);
        for(Player list : main.getPlayers()) {
            list.sendMessage("§a"+p.getName()+" has been killed by "+d.getName());
        }
    }
    private void killy(Player p, Player d){
        Location spawnyellow  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.YellowBase.x"), main.getConfig().getInt("Locations.Bases.YellowBase.y"), main.getConfig().getInt("Locations.Bases.YellowBase.z"));
        p.setHealth(20);
        p.setGameMode(GameMode.SPECTATOR);
        p.getInventory().setItem( 8, new ItemStack(Material.ARROW, 5));
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
            p.teleport(spawnyellow);
            p.setGameMode(GameMode.SURVIVAL);
        }, t);
        for(Player list : main.getPlayers()) {
            list.sendMessage("§c"+p.getName()+" has been killed by "+d.getName());
        }
    }
}
