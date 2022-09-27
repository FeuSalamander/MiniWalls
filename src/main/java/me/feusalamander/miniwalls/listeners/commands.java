package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.GUI.MWconfigGui;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import me.feusalamander.miniwalls.timers.MWautostart;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        }else if (e.getMessage().equalsIgnoreCase("/mw configgui")) {
            if (player.hasPermission("mw.admin")) {
                MWconfigGui.config(player);
            }else{
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
            player.sendMessage("§1§l---------------------------------");
        }else if(e.getMessage().equalsIgnoreCase("/mw reload")){
            if(player.hasPermission("mw.admin")){
                player.sendMessage("The configuration file of MiniWalls got successfully reloaded");
                main.reloadConfig();
            }else{
                player.sendMessage("You don't have the permission");
            }
        }else if(e.getMessage().equalsIgnoreCase("/test")){
            Inventory inv = Bukkit.createInventory(null, InventoryType.ANVIL, "bozo");
            player.openInventory(inv);
        }
    }
    @EventHandler
    private void anvil(PrepareAnvilEvent e){
        if(e.getResult() != null && e.getResult().hasItemMeta() && !Objects.equals(e.getInventory().getRenameText(), "")){
            ItemStack r = e.getResult();
            ItemMeta m = r.getItemMeta();
            Bukkit.broadcastMessage(m.getDisplayName());
        }
    }
}