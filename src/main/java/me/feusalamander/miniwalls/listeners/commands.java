package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import me.feusalamander.miniwalls.timers.MWautostart;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
public class commands implements Listener {
    double number = 1;
    private MiniWalls main;

    public commands(MiniWalls main) {
        this.main = main;
    }

    @EventHandler
    public void setspawn(PlayerCommandPreprocessEvent e){
        Player player = e.getPlayer();
        if(e.getMessage().equalsIgnoreCase("/mw join")){
            if(!(main.isState(MWstates.STARTING) || main.isState(MWstates.WAITING))) {
                Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Lobby.x"), main.getConfig().getInt("Locations.Lobby.y"), main.getConfig().getInt("Locations.Lobby.z"));
                player.teleport(lobby);
                player.sendMessage("§7The game is already started !");
                return;
            }else if(!main.getPlayers().contains(player)) {
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
        }else if (e.getMessage().equalsIgnoreCase("/mw gui")){
            Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "MiniWalls");
            //join
            ItemStack br = new ItemStack(Material.BEDROCK);
            ItemMeta brM = br.getItemMeta();
            brM.setDisplayName(ChatColor.GOLD+"Click to join MiniWalls");
            brM.addEnchant(Enchantment.DURABILITY, 1, true);
            brM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            br.setItemMeta(brM);
            inv.setItem(13, br);
            //stats
            ItemStack stat = new ItemStack(Material.PAPER);
            ItemMeta statM = stat.getItemMeta();
            statM.setDisplayName(ChatColor.GREEN+"Your Stats");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Wins: §f"+PlayerData.getWins(player));
            lore.add("§7Loses: §f"+PlayerData.getloses(player));
            lore.add("§7Kills: §f"+PlayerData.getkills(player));
            lore.add("§7Final Kills: §f"+PlayerData.getfinal(player));
            lore.add("§7Deaths: §f"+PlayerData.getdeath(player));
            if(PlayerData.getdeath(player) == 0){
                lore.add("§7Ratio K/D: §f"+PlayerData.getkills(player));
            }else{
                number = (float)PlayerData.getkills(player)/PlayerData.getdeath(player);
                DecimalFormat format = new DecimalFormat("0.00");
                String output = format.format(number);
                lore.add("§7Ratio K/D: §f"+output);
            }
            statM.setLore(lore);
            stat.setItemMeta(statM);
            inv.setItem(4, stat);
            //kills
            AtomicInteger pos = new AtomicInteger(1);
            ItemStack k = new ItemStack(Material.GOLDEN_SWORD);
            ItemMeta kM = k.getItemMeta();
            kM.setDisplayName(ChatColor.GOLD+"Kills Leaderboard");
            ArrayList<String> lo = new ArrayList<>();
            File fi = new File(main.getDataFolder(), "stats.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(fi);
            ConfigurationSection cf = config.getConfigurationSection("players");
            cf.getValues(false)
                    .entrySet()
                    .stream()
                    .sorted((a1, a2) -> {
                        int points1 = ((MemorySection) a1.getValue()).getInt("kills");
                        int points2 = ((MemorySection) a2.getValue()).getInt("kills");
                        return points2 - points1;
                    })
                    .limit(5)
                    .forEach(f -> {
                        int points = ((MemorySection) f.getValue()).getInt("kills");
                        OfflinePlayer s = Bukkit.getOfflinePlayer(UUID.fromString(f.getKey()));
                        lo.add("§6"+pos+". §b"+s.getName()+" §e"+points);
                        pos.getAndIncrement();
                    });
            kM.setLore(lo);
            kM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            k.setItemMeta(kM);
            inv.setItem(21, k);
            //wins
            AtomicInteger pos1 = new AtomicInteger(1);
            ItemStack w = new ItemStack(Material.GOLDEN_HELMET);
            ItemMeta wM = w.getItemMeta();
            wM.setDisplayName(ChatColor.GOLD+"Wins Leaderboard");
            ArrayList<String> low = new ArrayList<>();
            cf.getValues(false)
                    .entrySet()
                    .stream()
                    .sorted((a1, a2) -> {
                        int points1 = ((MemorySection) a1.getValue()).getInt("wins");
                        int points2 = ((MemorySection) a2.getValue()).getInt("wins");
                        return points2 - points1;
                    })
                    .limit(5)
                    .forEach(f -> {
                        int points = ((MemorySection) f.getValue()).getInt("wins");
                        OfflinePlayer s = Bukkit.getOfflinePlayer(UUID.fromString(f.getKey()));
                        low.add("§6"+pos1+". §b"+s.getName()+" §e"+points);
                        pos1.getAndIncrement();
                    });
            wM.setLore(low);
            wM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            w.setItemMeta(wM);
            inv.setItem(23, w);
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
        }else if (e.getMessage().equalsIgnoreCase("/mw setbluebase")) {
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
            player.sendMessage("§5mw gui: §cOpen the Gui to see the leaderboard, the stats and play");
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
        if(e.getView().getTitle().equals(ChatColor.GOLD + "MiniWalls")){
            Player player = (Player) e.getWhoClicked();
            ItemStack current = e.getCurrentItem();
            if(current == null) return;
            if(current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Click to join MiniWalls")){
                e.setCancelled(true);
                player.chat("/mw join");
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"Your Stats")) {
                e.setCancelled(true);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Kills Leaderboard")) {
                e.setCancelled(true);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Wins Leaderboard")) {
                e.setCancelled(true);
            }
        }
    }
}