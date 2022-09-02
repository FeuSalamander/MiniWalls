package me.feusalamander.miniwalls.timers;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
public class MWautostart extends BukkitRunnable{
    private int timer = 15;
    private MiniWalls main;

    public MWautostart(MiniWalls main) {
        this.main = main;
    }


    @Override
    public void run() {
        for(Player pls : main.getPlayers()){
            pls.setLevel(timer);
        }
        main.scoreboard.getTeam("playerss").setSuffix("§a" +main.getPlayers().size()+ "/§a8");
        main.scoreboard.getTeam("playerss").setPrefix("Waiting ");
        if(main.getPlayers().size() < main.getConfig().getInt("MinPlayers")){
            cancel();
            Bukkit.broadcastMessage("§4Not enough players to start");
            main.setState(MWstates.WAITING);
        }
        if(timer == 10)
        {
            for(Player list : main.getPlayers()) {
                list.sendMessage("§aThe Game is starting in 10s");
                list.sendTitle("§a10", "", 1, 10, 1);
            }
        }
        if(timer == 3)
        {
            for(Player list : main.getPlayers()) {
                list.sendMessage("§eThe Game is starting in 3s");
                list.sendTitle("§e3", "", 1, 20, 1);
            }
        }
        if(timer == 2)
        {
            for(Player list : main.getPlayers()) {
                list.sendMessage("§6The Game is starting in 2s");
                list.sendTitle("§62", "", 1, 20, 1);
            }
        }
        if(timer == 1)
        {
            for(Player list : main.getPlayers()) {
                list.sendMessage("§4The Game is starting in 1s");
                list.sendTitle("§41", "", 1, 20, 1);
            }
        }
        if(timer == 0)
        {
            for(Player list : main.getPlayers()) {
                list.sendMessage("§0The Game is starting, §5Prepare yourself for the battle");
            }
            main.setState(MWstates.PLAYING);
            for(int i = 0; i < main.getPlayers().size(); i++) {
                Player player = main.getPlayers().get(i);
                Location spawnblue  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.BlueBase.x"), main.getConfig().getInt("Locations.Bases.BlueBase.y"), main.getConfig().getInt("Locations.Bases.BlueBase.z"));
                Location spawnred  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.RedBase.x"), main.getConfig().getInt("Locations.Bases.RedBase.y"), main.getConfig().getInt("Locations.Bases.RedBase.z"));
                Location spawngreen  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.GreenBase.x"), main.getConfig().getInt("Locations.Bases.GreenBase.y"), main.getConfig().getInt("Locations.Bases.GreenBase.z"));
                Location spawnyellow  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.YellowBase.x"), main.getConfig().getInt("Locations.Bases.YellowBase.y"), main.getConfig().getInt("Locations.Bases.YellowBase.z"));
                player.setGameMode(GameMode.SURVIVAL);
                player.getInventory().clear();
                ItemStack ss = new ItemStack(Material.STONE_SWORD);
                ItemMeta zz = ss.getItemMeta();
                zz.setUnbreakable(true);
                ss.setItemMeta(zz);
                player.getInventory().setItem( 0, ss);
                ItemStack rr = new ItemStack(Material.BOW);
                ItemMeta tt = rr.getItemMeta();
                tt.setUnbreakable(true);
                rr.setItemMeta(tt);
                player.getInventory().setItem( 1, rr);
                player.getInventory().setItem( 8, new ItemStack(Material.ARROW, 3));
                player.getInventory().setItem( 2, new ItemStack(Material.STONE_PICKAXE));
                player.getInventory().setItem( 3, new ItemStack(Material.WOODEN_AXE ));
                player.getInventory().setItem( 4, new ItemStack(Material.WOODEN_SHOVEL));
                if(main.scoreboard.getTeam("Blue").hasPlayer(player)){
                }else if(main.scoreboard.getTeam("Red").hasPlayer(player)){
                }else if(main.scoreboard.getTeam("Green").hasPlayer(player)){
                }else if(main.scoreboard.getTeam("Yellow").hasPlayer(player)){
                }else{
                    if(main.scoreboard.getTeam("Blue").getSize() < 1){
                        main.scoreboard.getTeam("Blue").addPlayer(player);
                    }else if(main.scoreboard.getTeam("Red").getSize() < 1){
                        main.scoreboard.getTeam("Red").addPlayer(player);
                    }else if(main.scoreboard.getTeam("Green").getSize() < 1){
                        main.scoreboard.getTeam("Green").addPlayer(player);
                    }else if(main.scoreboard.getTeam("Yellow").getSize() < 1){
                        main.scoreboard.getTeam("Yellow").addPlayer(player);
                    }else if(main.scoreboard.getTeam("Blue").getSize() < 2){
                        main.scoreboard.getTeam("Blue").addPlayer(player);
                    }else if(main.scoreboard.getTeam("Red").getSize() < 2){
                        main.scoreboard.getTeam("Red").addPlayer(player);
                    }else if(main.scoreboard.getTeam("Green").getSize() < 2){
                        main.scoreboard.getTeam("Green").addPlayer(player);
                    }else if(main.scoreboard.getTeam("Yellow").getSize() < 2){
                        main.scoreboard.getTeam("Yellow").addPlayer(player);
                    }
                }
                if(main.scoreboard.getTeam("Blue").hasPlayer(player)){
                    ItemStack LeatherHelmet = new ItemStack(Material.LEATHER_HELMET);
                    LeatherArmorMeta meta = (LeatherArmorMeta) LeatherHelmet.getItemMeta();
                    meta.setColor(Color.BLUE);
                    meta.setUnbreakable(true);
                    LeatherHelmet.setItemMeta(meta);
                    player.getInventory().setHelmet(LeatherHelmet);
                    ItemStack LeatherCP = new ItemStack(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta metac = (LeatherArmorMeta) LeatherCP.getItemMeta();
                    metac.setColor(Color.BLUE);
                    metac.setUnbreakable(true);
                    LeatherCP.setItemMeta(metac);
                    player.getInventory().setChestplate(LeatherCP);
                    ItemStack LeatherL = new ItemStack(Material.LEATHER_LEGGINGS);
                    LeatherArmorMeta metal = (LeatherArmorMeta) LeatherL.getItemMeta();
                    metal.setColor(Color.BLUE);
                    metal.setUnbreakable(true);
                    LeatherL.setItemMeta(metal);
                    player.getInventory().setLeggings(LeatherL);
                }else if(main.scoreboard.getTeam("Red").hasPlayer(player)) {
                    ItemStack LeatherHelmet = new ItemStack(Material.LEATHER_HELMET);
                    LeatherArmorMeta meta = (LeatherArmorMeta) LeatherHelmet.getItemMeta();
                    meta.setColor(Color.RED);
                    meta.setUnbreakable(true);
                    LeatherHelmet.setItemMeta(meta);
                    player.getInventory().setHelmet(LeatherHelmet);
                    ItemStack LeatherCP = new ItemStack(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta metac = (LeatherArmorMeta) LeatherCP.getItemMeta();
                    metac.setColor(Color.RED);
                    metac.setUnbreakable(true);
                    LeatherCP.setItemMeta(metac);
                    player.getInventory().setChestplate(LeatherCP);
                    ItemStack LeatherL = new ItemStack(Material.LEATHER_LEGGINGS);
                    LeatherArmorMeta metal = (LeatherArmorMeta) LeatherL.getItemMeta();
                    metal.setColor(Color.RED);
                    metal.setUnbreakable(true);
                    LeatherL.setItemMeta(metal);
                    player.getInventory().setLeggings(LeatherL);
                }else if(main.scoreboard.getTeam("Green").hasPlayer(player)) {
                    ItemStack LeatherHelmet = new ItemStack(Material.LEATHER_HELMET);
                    LeatherArmorMeta meta = (LeatherArmorMeta) LeatherHelmet.getItemMeta();
                    meta.setColor(Color.GREEN);
                    meta.setUnbreakable(true);
                    LeatherHelmet.setItemMeta(meta);
                    player.getInventory().setHelmet(LeatherHelmet);
                    ItemStack LeatherCP = new ItemStack(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta metac = (LeatherArmorMeta) LeatherCP.getItemMeta();
                    metac.setColor(Color.GREEN);
                    metac.setUnbreakable(true);
                    LeatherCP.setItemMeta(metac);
                    player.getInventory().setChestplate(LeatherCP);
                    ItemStack LeatherL = new ItemStack(Material.LEATHER_LEGGINGS);
                    LeatherArmorMeta metal = (LeatherArmorMeta) LeatherL.getItemMeta();
                    metal.setColor(Color.GREEN);
                    metal.setUnbreakable(true);
                    LeatherL.setItemMeta(metal);
                    player.getInventory().setLeggings(LeatherL);
                }else if(main.scoreboard.getTeam("Yellow").hasPlayer(player)){
                    ItemStack LeatherHelmet = new ItemStack(Material.LEATHER_HELMET);
                    LeatherArmorMeta meta = (LeatherArmorMeta) LeatherHelmet.getItemMeta();
                    meta.setColor(Color.YELLOW);
                    meta.setUnbreakable(true);
                    LeatherHelmet.setItemMeta(meta);
                    player.getInventory().setHelmet(LeatherHelmet);
                    ItemStack LeatherCP = new ItemStack(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta metac = (LeatherArmorMeta) LeatherCP.getItemMeta();
                    metac.setColor(Color.YELLOW);
                    metac.setUnbreakable(true);
                    LeatherCP.setItemMeta(metac);
                    player.getInventory().setChestplate(LeatherCP);
                    ItemStack LeatherL = new ItemStack(Material.LEATHER_LEGGINGS);
                    LeatherArmorMeta metal = (LeatherArmorMeta) LeatherL.getItemMeta();
                    metal.setColor(Color.YELLOW);
                    metal.setUnbreakable(true);
                    LeatherL.setItemMeta(metal);
                    player.getInventory().setLeggings(LeatherL);
                }
                ItemStack db = new ItemStack(Material.DIAMOND_BOOTS);
                ItemMeta bd = db.getItemMeta();
                bd.setUnbreakable(true);
                db.setItemMeta(bd);
                player.getInventory().setBoots(db);
                player.updateInventory();
                if(main.scoreboard.getTeam("Blue").hasPlayer(player)){
                    player.teleport(spawnblue);
                }else if(main.scoreboard.getTeam("Red").hasPlayer(player)){
                    player.teleport(spawnred);
                }else if(main.scoreboard.getTeam("Green").hasPlayer(player)){
                    player.teleport(spawngreen);
                }else if(main.scoreboard.getTeam("Yellow").hasPlayer(player)){
                    player.teleport(spawnyellow);
                }else{
                    main.eliminate(player);
                    player.sendMessage("§4You didn't choose a team");
                }
            }
            MWgamecycle cycle = new MWgamecycle(main);
            cycle.runTaskTimer(main, 0, 20);
            main.activeteams.add("Blue");
            main.activeteams.add("Red");
            main.activeteams.add("Green");
            main.activeteams.add("Yellow");
            World world = Bukkit.getWorld("world");
            Location locb = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Villagers.Bluevillager.x"), main.getConfig().getInt("Locations.Villagers.Bluevillager.y"), main.getConfig().getInt("Locations.Villagers.Bluevillager.z"));
            Location locr = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Villagers.Redvillager.x"), main.getConfig().getInt("Locations.Villagers.Redvillager.y"), main.getConfig().getInt("Locations.Villagers.Redvillager.z"));
            Location locg = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Villagers.Greenvillager.x"), main.getConfig().getInt("Locations.Villagers.Greenvillager.y"), main.getConfig().getInt("Locations.Villagers.Greenvillager.z"));
            Location locy = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Villagers.Yellowvillager.x"), main.getConfig().getInt("Locations.Villagers.Yellowvillager.y"), main.getConfig().getInt("Locations.Villagers.Yellowvillager.z"));
            Villager vb = (Villager) world.spawnEntity(locb, EntityType.VILLAGER);
            Villager vr = (Villager) world.spawnEntity(locr, EntityType.VILLAGER);
            Villager vg = (Villager) world.spawnEntity(locg, EntityType.VILLAGER);
            Villager vy = (Villager) world.spawnEntity(locy, EntityType.VILLAGER);
            vb.setAI(false);
            vb.setRemoveWhenFarAway(false);
            vb.setCustomName("§9Blue Villager");
            vb.setMaxHealth(200);
            vb.setHealth(200);
            vr.setAI(false);
            vr.setRemoveWhenFarAway(false);
            vr.setCustomName("§cRed Villager");
            vr.setMaxHealth(200);
            vr.setHealth(200);
            vg.setAI(false);
            vg.setRemoveWhenFarAway(false);
            vg.setCustomName("§aGreen Villager");
            vg.setMaxHealth(200);
            vg.setHealth(200);
            vy.setAI(false);
            vy.setRemoveWhenFarAway(false);
            vy.setCustomName("§eYellow Villager");
            vy.setMaxHealth(200);
            vy.setHealth(200);
            main.blife = 20;
            main.rlife = 20;
            main.glife = 20;
            main.ylife = 20;
            cancel();
        }
        timer--;
    }
}
