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
        item(inv, "§7Wall 1 First pos", Material.BRICK_SLAB, 28, "§fClick to get the item to select the block coords");
        item(inv, "§7Wall 1 Second pos", Material.BRICK_SLAB, 29, "§fClick to get the item to select the block coords");
        //wall2
        item(inv, "§6Wall 2", Material.BRICK_WALL, 36, "");
        item(inv, "§7Wall 2 First pos", Material.BRICK_SLAB, 37, "§fClick to get the item to select the block coords");
        item(inv, "§7Wall 2 Second pos", Material.BRICK_SLAB, 38, "§fClick to get the item to select the block coords");
        //destroy
        item(inv, "§6Death Match destroy", Material.TNT, 45, "");
        item(inv, "§7Center coords", Material.ALLIUM, 46, "§fClick to set the coords, go the config for more infos");
        item(inv, "§7Radius", Material.PAPER, 47, "§fGo to the config for more infos");
        item(inv, "§7Deep", Material.PAPER, 48, "§fGo to the config for more infos");
        //other
        item(inv, "§6Minimum Players", Material.PAPER, 35, "Click to select the Minimum Players needed to start the game");
        item(inv, "§6Map Name", Material.PAPER, 44, "");
        item(inv, "§6Server IP", Material.PAPER, 53, "");
        //open
        p.openInventory(inv);
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();
        if(main.getPlayers().contains(p) && current != null && current.getType().equals(Material.ARROW)){
            e.setCancelled(true);
        }
        if(e.getView().getTitle().equals(ChatColor.GOLD + "Config Gui")){
            if(current == null) return;
            e.setCancelled(true);
            if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Lobby")){
                main.getConfig().set("Locations.Lobby.x", p.getLocation().getX());
                main.getConfig().set("Locations.Lobby.y", p.getLocation().getY());
                main.getConfig().set("Locations.Lobby.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Spawn")){
                main.getConfig().set("Locations.Spawn.x", p.getLocation().getX());
                main.getConfig().set("Locations.Spawn.y", p.getLocation().getY());
                main.getConfig().set("Locations.Spawn.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§9Blue Spawn")){
                main.getConfig().set("Locations.Bases.BlueBase.x", p.getLocation().getX());
                main.getConfig().set("Locations.Bases.BlueBase.y", p.getLocation().getY());
                main.getConfig().set("Locations.Bases.BlueBase.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§cRed Spawn")){
                main.getConfig().set("Locations.Bases.RedBase.x", p.getLocation().getX());
                main.getConfig().set("Locations.Bases.RedBase.y", p.getLocation().getY());
                main.getConfig().set("Locations.Bases.RedBase.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§aGreen Spawn")){
                main.getConfig().set("Locations.Bases.GreenBase.x", p.getLocation().getX());
                main.getConfig().set("Locations.Bases.GreenBase.y", p.getLocation().getY());
                main.getConfig().set("Locations.Bases.GreenBase.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§eYellow Spawn")){
                main.getConfig().set("Locations.Bases.YellowBase.x", p.getLocation().getX());
                main.getConfig().set("Locations.Bases.YellowBase.y", p.getLocation().getY());
                main.getConfig().set("Locations.Bases.YellowBase.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§9Blue Villager")){
                main.getConfig().set("Locations.Villagers.Bluevillager.x", p.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Bluevillager.y", p.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Bluevillager.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§cRed Villager")){
                main.getConfig().set("Locations.Villagers.Redvillager.x", p.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Redvillager.y", p.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Redvillager.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§aGreen Villager")){
                main.getConfig().set("Locations.Villagers.Greenvillager.x", p.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Greenvillager.y", p.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Greenvillager.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§eYellow Villager")){
                main.getConfig().set("Locations.Villagers.Yellowvillager.x", p.getLocation().getX());
                main.getConfig().set("Locations.Villagers.Yellowvillager.y", p.getLocation().getY());
                main.getConfig().set("Locations.Villagers.Yellowvillager.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Center coords")){
                main.getConfig().set("Destruction.center.x", p.getLocation().getX());
                main.getConfig().set("Destruction.center.y", p.getLocation().getY());
                main.getConfig().set("Destruction.center.z", p.getLocation().getZ());
                main.saveConfig();
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Map Name")){
                p.openAnvil(p.getLocation(), true);
                ItemStack i = new ItemStack(Material.PAPER);
                ItemMeta m = i.getItemMeta();
                ArrayList<String> l = new ArrayList<>();
                l.add("§7Map name");
                m.setLore(l);
                m.setDisplayName("§7"+main.getConfig().getString("map"));
                i.setItemMeta(m);
                p.getOpenInventory().setItem(0, i);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Server IP")){
                p.openAnvil(p.getLocation(), true);
                ItemStack i = new ItemStack(Material.PAPER);
                ItemMeta m = i.getItemMeta();
                ArrayList<String> l = new ArrayList<>();
                l.add("§7Server IP");
                m.setLore(l);
                m.setDisplayName("§7"+main.getConfig().getString("server"));
                i.setItemMeta(m);
                p.getOpenInventory().setItem(0, i);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Minimum Players")){
                p.openAnvil(p.getLocation(), true);
                ItemStack i = new ItemStack(Material.PAPER);
                ItemMeta m = i.getItemMeta();
                ArrayList<String> l = new ArrayList<>();
                l.add("§7Minimum Players");
                m.setLore(l);
                m.setDisplayName("§7"+main.getConfig().getString("MinPlayers"));
                i.setItemMeta(m);
                p.getOpenInventory().setItem(0, i);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Radius")){
                p.openAnvil(p.getLocation(), true);
                ItemStack i = new ItemStack(Material.PAPER);
                ItemMeta m = i.getItemMeta();
                ArrayList<String> l = new ArrayList<>();
                l.add("§7Radius");
                m.setLore(l);
                m.setDisplayName("§7"+main.getConfig().getString("Destruction.radius"));
                i.setItemMeta(m);
                p.getOpenInventory().setItem(0, i);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Deep")){
                p.openAnvil(p.getLocation(), true);
                ItemStack i = new ItemStack(Material.PAPER);
                ItemMeta m = i.getItemMeta();
                ArrayList<String> l = new ArrayList<>();
                l.add("§7Deep");
                m.setLore(l);
                m.setDisplayName("§7"+main.getConfig().getString("Destruction.deep"));
                i.setItemMeta(m);
                p.getOpenInventory().setItem(0, i);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Wall 1 First pos")){
                item(p.getInventory(), "§6Wall 1 First pos selector", Material.BRICK, 0, "§fbreak a block to save his coords in the config");
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Wall 1 Second pos")){
                item(p.getInventory(), "§6Wall 1 Second pos selector", Material.BRICK, 1, "§fbreak a block to save his coords in the config");
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Wall 2 First pos")){
                item(p.getInventory(), "§6Wall 2 First pos selector", Material.BRICK, 2, "§fbreak a block to save his coords in the config");
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§7Wall 2 Second pos")){
                item(p.getInventory(), "§6Wall 2 Second pos selector", Material.BRICK, 3, "§fbreak a block to save his coords in the config");
            }
        }else if(e.getView().getTitle().equals(ChatColor.GOLD + "MiniWalls")){
            if(current == null) return;
            if(current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Click to join MiniWalls")){
                e.setCancelled(true);
                p.chat("/mw join");
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"Your Stats")) {
                e.setCancelled(true);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Kills Leaderboard")) {
                e.setCancelled(true);
            }else if(current.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+"Wins Leaderboard")) {
                e.setCancelled(true);
            }
        }else if(e.getInventory().getType().equals(InventoryType.ANVIL) && e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLore()){
            if(Objects.requireNonNull(e.getCurrentItem().getItemMeta().getLore()).get(0).equalsIgnoreCase("§7Map name")){
                e.setCancelled(true);
                if(e.getSlot() == 2){
                    main.getConfig().set("map", e.getCurrentItem().getItemMeta().getDisplayName());
                    main.saveConfig();
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().getInventory().remove(Material.PAPER);
                }
            }else if(Objects.requireNonNull(e.getCurrentItem().getItemMeta().getLore()).get(0).equalsIgnoreCase("§7Server IP")){
                e.setCancelled(true);
                if(e.getSlot() == 2){
                    main.getConfig().set("server", e.getCurrentItem().getItemMeta().getDisplayName());
                    main.saveConfig();
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().getInventory().remove(Material.PAPER);
                }
            }else if(Objects.requireNonNull(e.getCurrentItem().getItemMeta().getLore()).get(0).equalsIgnoreCase("§7Minimum Players")){
                e.setCancelled(true);
                if(e.getSlot() == 2){
                    main.getConfig().set("MinPlayers", Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName()));
                    main.saveConfig();
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().getInventory().remove(Material.PAPER);
                }
            }else if(Objects.requireNonNull(e.getCurrentItem().getItemMeta().getLore()).get(0).equalsIgnoreCase("§7Radius")){
                e.setCancelled(true);
                if(e.getSlot() == 2){
                    main.getConfig().set("Destruction.radius", Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName()));
                    main.saveConfig();
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().getInventory().remove(Material.PAPER);
                }
            }else if(Objects.requireNonNull(e.getCurrentItem().getItemMeta().getLore()).get(0).equalsIgnoreCase("§7Deep")){
                e.setCancelled(true);
                if(e.getSlot() == 2){
                    main.getConfig().set("Destruction.deep", Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName()));
                    main.saveConfig();
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().getInventory().remove(Material.PAPER);
                }
            }
        }
    }
}