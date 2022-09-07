package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import me.feusalamander.miniwalls.timers.MWautostart;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
public class commands implements Listener {
    private MiniWalls main;

    public commands(MiniWalls main) {
        this.main = main;
    }

    @EventHandler
    public void setspawn(PlayerCommandPreprocessEvent e) {
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
        }else if (e.getMessage().equalsIgnoreCase("/mw stats")) {
            Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD+"§l"+player.getName()+"'s Stats");
            //kills
            ItemStack sword = new ItemStack(Material.IRON_SWORD);
            ItemMeta swordM = sword.getItemMeta();
            swordM.setDisplayName("§7Kills: §f"+PlayerData.getkills(player));
            sword.setItemMeta(swordM);
            inv.setItem(12, sword);
            //wins
            ItemStack wins = new ItemStack(Material.GOLD_INGOT);
            ItemMeta winsM = wins.getItemMeta();
            winsM.setDisplayName("§7Wins: §f"+PlayerData.getWins(player));
            wins.setItemMeta(winsM);
            inv.setItem(3, wins);
            //loses
            ItemStack loses = new ItemStack(Material.COAL);
            ItemMeta losesM = loses.getItemMeta();
            losesM.setDisplayName("§7Loses: §f"+PlayerData.getloses(player));
            loses.setItemMeta(losesM);
            inv.setItem(5, loses);
            player.openInventory(inv);
            //deaths
            ItemStack death = new ItemStack(Material.WITHER_SKELETON_SKULL);
            ItemMeta deathM = death.getItemMeta();
            deathM.setDisplayName("§7Deaths: §f"+PlayerData.getdeath(player));
            death.setItemMeta(deathM);
            inv.setItem(14, death);
            //finals
            ItemStack finals = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta finalsM = finals.getItemMeta();
            finalsM.setDisplayName("§7Final Kills: §f"+PlayerData.getfinal(player));
            finals.setItemMeta(finalsM);
            inv.setItem(13, finals);
            //ratio
            ItemStack ratio = new ItemStack(Material.GLASS);
            ItemMeta ratioM = ratio.getItemMeta();
            if(PlayerData.getdeath(player) == 0){
                ratioM.setDisplayName("§7Ratio K/D: §f"+PlayerData.getkills(player));
            }else{
                ratioM.setDisplayName("§7Ratio K/D: §f"+PlayerData.getkills(player)/PlayerData.getdeath(player));
            }
            ratio.setItemMeta(ratioM);
            inv.setItem(22, ratio);
            player.openInventory(inv);
        }else if (e.getMessage().equalsIgnoreCase("/mw setspawn")) {
            if (player.hasPermission("mw.admin")) {
                main.getConfig().set("Locations.Spawn.x", player.getLocation().getX());
                main.getConfig().set("Locations.Spawn.y", player.getLocation().getY());
                main.getConfig().set("Locations.Spawn.z", player.getLocation().getZ());
                main.saveConfig();
            } else {
                player.sendMessage("You don't have the permission");
            }
        }else if (e.getMessage().equalsIgnoreCase("/mw setlobby")) {
            if (player.hasPermission("mw.admin")) {
                main.getConfig().set("Locations.Lobby.x", player.getLocation().getX());
                main.getConfig().set("Locations.Lobby.y", player.getLocation().getY());
                main.getConfig().set("Locations.Lobby.z", player.getLocation().getZ());
                main.saveConfig();
            } else {
                player.sendMessage("You don't have the permission");
            }
        } else if (e.getMessage().equalsIgnoreCase("/mw setbluebase")) {
            if (player.hasPermission("mw.admin")) {
                main.getConfig().set("Locations.Bases.BlueBase.x", player.getLocation().getX());
                main.getConfig().set("Locations.Bases.BlueBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.BlueBase.z", player.getLocation().getZ());
                main.saveConfig();
            } else {
                player.sendMessage("You don't have the permission");
            }
        } else if (e.getMessage().equalsIgnoreCase("/mw setredbase")) {
            if (player.hasPermission("mw.admin")) {
                main.getConfig().set("Locations.Bases.RedBase.x", player.getLocation().getX());
                main.getConfig().set("Locations.Bases.RedBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.RedBase.z", player.getLocation().getZ());
                main.saveConfig();
            } else {
                player.sendMessage("You don't have the permission");
            }
        } else if (e.getMessage().equalsIgnoreCase("/mw setgreenbase")) {
            if (player.hasPermission("mw.admin")) {
                main.getConfig().set("Locations.Bases.GreenBase.x", player.getLocation().getX());
                main.getConfig().set("Locations.Bases.GreenBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.GreenBase.z", player.getLocation().getZ());
                main.saveConfig();
            } else {
                player.sendMessage("You don't have the permission");
            }
        } else if (e.getMessage().equalsIgnoreCase("/mw setyellowbase")) {
            if (player.hasPermission("mw.admin")) {
                main.getConfig().set("Locations.Bases.YellowBase.x", player.getLocation().getX());
                main.getConfig().set("Locations.Bases.YellowBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.YellowBase.z", player.getLocation().getZ());
                main.saveConfig();
            } else {
                player.sendMessage("You don't have the permission");
            }
        } else if (e.getMessage().equalsIgnoreCase("/mw setbluevillager")) {
            if (player.hasPermission("mw.admin")) {
                main.getConfig().set("Locations.Villagers.Bluevillager.x", player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Bluevillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Bluevillager.z", player.getLocation().getZ());
                main.saveConfig();
            } else {
                player.sendMessage("You don't have the permission");
            }
        } else if (e.getMessage().equalsIgnoreCase("/mw setredvillager")) {
            if (player.hasPermission("mw.admin")) {
                main.getConfig().set("Locations.Villagers.Redvillager.x", player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Redvillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Redvillager.z", player.getLocation().getZ());
                main.saveConfig();
            } else {
                player.sendMessage("You don't have the permission");
            }
        } else if (e.getMessage().equalsIgnoreCase("/mw setgreenvillager")) {
            if (player.hasPermission("mw.admin")) {
                main.getConfig().set("Locations.Villagers.Greenvillager.x", player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Greenvillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Greenvillager.z", player.getLocation().getZ());
                main.saveConfig();
            } else {
                player.sendMessage("You don't have the permission");
            }
        } else if (e.getMessage().equalsIgnoreCase("/mw setyellowvillager")) {
            if (player.hasPermission("mw.admin")) {
                main.getConfig().set("Locations.Villagers.Yellowvillager.x", player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Yellowvillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Yellowvillager.z", player.getLocation().getZ());
                main.saveConfig();
            } else {
                player.sendMessage("You don't have the permission");
            }
        }else if (e.getMessage().equalsIgnoreCase("/mw help")) {
            player.sendMessage("§1§l---------------------------------");
            player.sendMessage("    §4All the commands of the mini walls plugin");
            player.sendMessage("");
            player.sendMessage("§5mw help: §cShow this help");
            player.sendMessage("§5mw join: §cJoin the MiniWalls game");
            player.sendMessage("§5mw leave: §cLeave the MiniWalls game");
            player.sendMessage("§5mw reload: §cReload the config");
            player.sendMessage("§5mw setspawn: §cSet the loc of the waiting room");
            player.sendMessage("§5mw setlobby: §cSet the loc of the main lobby");
            player.sendMessage("§5mw setbluebase: §cSet the spawn of the §9Blue §cteam");
            player.sendMessage("§5mw setredbase: §cSet the spawn of the §cRed §cteam");
            player.sendMessage("§5mw setgreenbase: §cSet the spawn of the §aGreen §cteam");
            player.sendMessage("§5mw setyellowbase: §cSet the spawn of the §eYellow §cteam");
            player.sendMessage("§5mw setbluevillager: §cSet the spawn of the §9Blue §cvillager");
            player.sendMessage("§5mw setredvillager: §cSet the spawn of the §cRed §cvillager");
            player.sendMessage("§5mw setgreenvillager: §cSet the spawn of the §aGreen §cvillager");
            player.sendMessage("§5mw setyellowvillager: §cSet the spawn of the §eYellow §cvillager");
            player.sendMessage("§1§l---------------------------------");
        }else if(e.getMessage().equalsIgnoreCase("/mw reload")){
            if(player.hasPermission("mw.admin")){
                player.sendMessage("The configuration file of MiniWalls got successfully reloaded");
                main.reloadConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Inventory inv = e.getInventory();
        if(inv.getSize() == 27){
            Player player = (Player) e.getWhoClicked();
            ItemStack current = e.getCurrentItem();
            if(current == null) return;
            if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Kills: §f"+PlayerData.getkills(player))){
                e.setCancelled(true);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Wins: §f"+PlayerData.getWins(player))){
                e.setCancelled(true);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Loses: §f"+PlayerData.getloses(player))){
                e.setCancelled(true);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Deaths: §f"+PlayerData.getdeath(player))){
                e.setCancelled(true);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Final Kills: §f"+PlayerData.getfinal(player))){
                e.setCancelled(true);
            }else if(PlayerData.getdeath(player) == 0){
                if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Ratio K/D: §f"+PlayerData.getkills(player))){
                    e.setCancelled(true);
                }
            }else{
                if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Ratio K/D: §f"+PlayerData.getkills(player)/PlayerData.getdeath(player))){
                    e.setCancelled(true);
                }
            }
        }
    }
}