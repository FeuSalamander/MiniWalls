package me.feusalamander.miniwalls.GUI;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.Objects;

public class MWconfigGui implements Listener{
    private MiniWalls main;

    public MWconfigGui(MiniWalls main) {
        this.main = main;
    }
    private static void item(Inventory inv, String s, Material m, int n, String l){
        ItemStack i = new ItemStack(m);
        ItemMeta iM = i.getItemMeta();
        iM.setDisplayName(s);
        if(!(l.equalsIgnoreCase(""))){
            ArrayList<String> lore = new ArrayList<>();
            lore.add(l);
            iM.setLore(lore);
        }
        i.setItemMeta(iM);
        inv.setItem(n, i);
    }
    public static void config(Player p){
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Config Gui");
        //spawn
        item(inv, "§6Spawns", Material.GRASS_BLOCK, 0, "");
        item(inv, "§7Lobby", Material.BEACON, 1, "§fClick to set the location of the main lobby");
        item(inv, "§7Spawn", Material.ENCHANTING_TABLE, 2, "§fClick to set the location of the waiting room");
        //Spawns
        item(inv, "§6Team Spawns", Material.RED_BED, 9, "§fClick the wools to set the team spawn coods");
        item(inv, "§9Blue Spawn", Material.BLUE_WOOL, 10, "");
        item(inv, "§cRed Spawn", Material.RED_WOOL, 11, "");
        item(inv, "§aGreen Spawn", Material.GREEN_WOOL, 12, "");
        item(inv, "§eYellow Spawn", Material.YELLOW_WOOL, 13, "");
        //Villagers
        item(inv, "§6Team Villagers", Material.VILLAGER_SPAWN_EGG, 18, "§fClick the terracottas to set the team villager coords");
        item(inv, "§9Blue Villager", Material.BLUE_TERRACOTTA, 19, "");
        item(inv, "§cRed Villager", Material.RED_TERRACOTTA, 20, "");
        item(inv, "§aGreen Villager", Material.GREEN_TERRACOTTA, 21, "");
        item(inv, "§eYellow Villager", Material.YELLOW_TERRACOTTA, 22, "");
        //wall1
        item(inv, "§6Wall 1", Material.BRICK_WALL, 27, "");
        item(inv, "§7First pos", Material.BRICK_SLAB, 28, "§fClick to get the item to select the block coords");
        item(inv, "§7Second pos", Material.BRICK_SLAB, 29, "§fClick to get the item to select the block coords");
        //wall2
        item(inv, "§6Wall 2", Material.BRICK_WALL, 36, "");
        item(inv, "§7First pos", Material.BRICK_SLAB, 37, "§fClick to get the item to select the block coords");
        item(inv, "§7Second pos", Material.BRICK_SLAB, 38, "§fClick to get the item to select the block coords");
        //destroy
        item(inv, "§6Death Match destroy", Material.TNT, 45, "");
        item(inv, "§7Center coords", Material.ALLIUM, 46, "§fClick to set the coords, go the config for more infos");
        item(inv, "§7Radius", Material.PAPER, 47, "§fGo the the config to set the radius of the map");
        item(inv, "§7Deep", Material.PAPER, 48, "§fGo the the config to set the deep of the map");
        //other
        item(inv, "§6Minimum Players", Material.PAPER, 35, "§fGo to the config to set the minimum players to start");
        item(inv, "§6Map Name", Material.PAPER, 44, "§fPut an helm on your head with the name of the map and click me");
        item(inv, "§6Server IP", Material.PAPER, 53, "§fGo to the config to set the server ip");
        //open
        p.openInventory(inv);
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getView().getTitle().equals(ChatColor.GOLD + "Config Gui")){
            Player player = (Player) e.getWhoClicked();
            ItemStack current = e.getCurrentItem();
            if(current == null) return;
            e.setCancelled(true);
            if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Lobby")){
                main.getConfig().set("Locations.Lobby.x", player.getLocation().getX());
                main.getConfig().set("Locations.Lobby.y", player.getLocation().getY());
                main.getConfig().set("Locations.Lobby.z", player.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Spawn")){
                main.getConfig().set("Locations.Spawn.x", player.getLocation().getX());
                main.getConfig().set("Locations.Spawn.y", player.getLocation().getY());
                main.getConfig().set("Locations.Spawn.z", player.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§9Blue Spawn")){
                main.getConfig().set("Locations.Bases.BlueBase.x", player.getLocation().getX());
                main.getConfig().set("Locations.Bases.BlueBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.BlueBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§cRed Spawn")){
                main.getConfig().set("Locations.Bases.RedBase.x", player.getLocation().getX());
                main.getConfig().set("Locations.Bases.RedBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.RedBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§aGreen Spawn")){
                main.getConfig().set("Locations.Bases.GreenBase.x", player.getLocation().getX());
                main.getConfig().set("Locations.Bases.GreenBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.GreenBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§eYellow Spawn")){
                main.getConfig().set("Locations.Bases.YellowBase.x", player.getLocation().getX());
                main.getConfig().set("Locations.Bases.YellowBase.y", player.getLocation().getY());
                main.getConfig().set("Locations.Bases.YellowBase.z", player.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§9Blue Villager")){
                main.getConfig().set("Locations.Villagers.Bluevillager.x", player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Bluevillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Bluevillager.z", player.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§cRed Villager")){
                main.getConfig().set("Locations.Villagers.Redvillager.x", player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Redvillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Redvillager.z", player.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§aGreen Villager")){
                main.getConfig().set("Locations.Villagers.Greenvillager.x", player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Greenvillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Greenvillager.z", player.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§eYellow Villager")){
                main.getConfig().set("Locations.Villagers.Yellowvillager.x", player.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Yellowvillager.y", player.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Yellowvillager.z", player.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Map Name")){
                if(player.getInventory().getHelmet() != null){
                    main.getConfig().set("map", Objects.requireNonNull(player.getInventory().getHelmet()).getItemMeta().getDisplayName());
                    main.saveConfig();
                }
            }
        }else if(e.getView().getTitle().equals(ChatColor.GOLD + "MiniWalls")){
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