package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.GUI.MWconfigGui;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import me.feusalamander.miniwalls.timers.MWautostart;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
public class commands implements Listener {
    double number = 1;
    private MiniWalls main;

    public commands(MiniWalls main) {
        this.main = main;
    }

    @EventHandler
    public void setspawn(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        if(e.getMessage().equalsIgnoreCase("/mw join")){
            if(!(main.isState(MWstates.STARTING) || main.isState(MWstates.WAITING))) {
                Location lobby  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Destruction.center.x"), main.getConfig().getInt("Destruction.center.y"), main.getConfig().getInt("Destruction.center.z"));
                p.teleport(lobby);
                p.setGameMode(GameMode.SPECTATOR);
                main.getSpec().add(p);
                p.sendMessage(ChatColor.DARK_RED+"The game is already running");
            }else if(!main.getPlayers().contains(p)){
                main.getPlayers().add(p);
                Location spawn  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Spawn.x"), main.getConfig().getInt("Locations.Spawn.y"), main.getConfig().getInt("Locations.Spawn.z"));
                p.teleport(spawn);
                p.setHealth(20);
                p.setFoodLevel(20);
                p.getInventory().clear();
                Bukkit.broadcastMessage(p.getName() + "§a joined the MiniWalls game ! §7<§f" + main.getPlayers().size() + "§7/§f8§7>");
                p.setGameMode(GameMode.ADVENTURE);
                ItemStack bwool = new ItemStack(Material.BLUE_WOOL);
                ItemMeta woolb = bwool.getItemMeta();
                woolb.setDisplayName("§1Click to join the §9Blue Team !");
                bwool.setItemMeta(woolb);
                p.getInventory().setItem(5, bwool);
                ItemStack rwool = new ItemStack(Material.RED_WOOL);
                ItemMeta woolr = bwool.getItemMeta();
                woolr.setDisplayName("§4Click to join the §cRed Team !");
                rwool.setItemMeta(woolr);
                p.getInventory().setItem(6, rwool);
                ItemStack gwool = new ItemStack(Material.GREEN_WOOL);
                ItemMeta woolg = gwool.getItemMeta();
                woolg.setDisplayName("§2Click to join the §aGreen Team !");
                gwool.setItemMeta(woolg);
                p.getInventory().setItem(7, gwool);
                ItemStack ywool = new ItemStack(Material.YELLOW_WOOL);
                ItemMeta wooly = ywool.getItemMeta();
                wooly.setDisplayName("§6Click to join the §eYellow Team !");
                ywool.setItemMeta(wooly);
                p.getInventory().setItem(8, ywool);
                ItemStack leave = new ItemStack(Material.IRON_TRAPDOOR);
                ItemMeta leave2 = leave.getItemMeta();
                leave2.setDisplayName("§cClick to leave");
                leave.setItemMeta(leave2);
                p.getInventory().setItem(0, leave);
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
            lore.add("§7Wins: §f"+PlayerData.getWins(p));
            lore.add("§7Loses: §f"+PlayerData.getloses(p));
            lore.add("§7Kills: §f"+PlayerData.getkills(p));
            lore.add("§7Final Kills: §f"+PlayerData.getfinal(p));
            lore.add("§7Deaths: §f"+PlayerData.getdeath(p));
            if(PlayerData.getdeath(p) == 0){
                lore.add("§7Ratio K/D: §f"+PlayerData.getkills(p));
            }else{
                number = (float)PlayerData.getkills(p)/PlayerData.getdeath(p);
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
                        if(Bukkit.getPluginManager().getPlugin("luckperms") != null && LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(f.getKey())).getCachedData().getMetaData().getPrefix() != null){
                            String prefix = LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(f.getKey())).getCachedData().getMetaData().getPrefix().replaceAll("&", "§");
                            lo.add("§6"+pos+". §8"+prefix+s.getName()+" §e"+points);
                        }else{
                            lo.add("§6"+pos+". §8"+s.getName()+" §e"+points);
                        }
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
                        if(Bukkit.getPluginManager().getPlugin("luckperms") != null && LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(f.getKey())).getCachedData().getMetaData().getPrefix() != null){
                            String prefix = LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(f.getKey())).getCachedData().getMetaData().getPrefix().replaceAll("&", "§");
                            low.add("§6"+pos1+". §8"+prefix+s.getName()+" §e"+points);
                        }else{
                            low.add("§6"+pos1+". §8"+s.getName()+" §e"+points);
                        }
                        pos1.getAndIncrement();
                    });
            wM.setLore(low);
            wM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            w.setItemMeta(wM);
            inv.setItem(23, w);
            p.openInventory(inv);
        }else if (e.getMessage().equalsIgnoreCase("/mw configgui")) {
            if (p.hasPermission("mw.admin")) {
                MWconfigGui.config(p);
            }else{
                p.sendMessage("You don't have the permission");
            }
        }else if (e.getMessage().equalsIgnoreCase("/mw help")) {
            p.sendMessage("§1§l---------------------------------");
            p.sendMessage("    §4All the commands of the mini walls plugin");
            p.sendMessage("");
            p.sendMessage("§5mw gui: §cOpen the Gui to see the leaderboard, the stats and play");
            p.sendMessage("§5mw help: §cShow this help");
            p.sendMessage("§5mw join: §cJoin the MiniWalls game");
            p.sendMessage("§5mw leave: §cLeave the MiniWalls game");
            p.sendMessage("§5mw reload: §cReload the config");
            p.sendMessage("§1§l---------------------------------");
        }else if(e.getMessage().equalsIgnoreCase("/mw reload")){
            if(p.hasPermission("mw.admin")){
                p.sendMessage("The configuration file of MiniWalls got successfully reloaded");
                main.reloadConfig();
            }else{
                p.sendMessage("You don't have the permission");
            }
        }
    }
}