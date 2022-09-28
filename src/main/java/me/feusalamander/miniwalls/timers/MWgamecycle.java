package me.feusalamander.miniwalls.timers;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class MWgamecycle extends BukkitRunnable {
    private MiniWalls main;
    private int timer = 300;
    private int d = 0;
    public MWgamecycle(MiniWalls main) {
        this.main = main;
    }

    @Override
    public void run() {
        if(d == 10){
            d = 0;
            for(Player p : main.getPlayers()){
                Inventory i = p.getInventory();
                if(i.contains(Material.ARROW)){
                    if(i.getItem(8).getAmount() < 3){
                        i.getItem(8).add(1);
                    }
                }else{
                    i.setItem(8, new ItemStack(Material.ARROW));
                }
            }
        }
        for(Player pls : main.getPlayers()){
            if(pls.getFoodLevel() < 20){
                pls.setFoodLevel(20);
            }
            pls.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 25, 0));
            pls.setLevel(timer);
            main.scoreboard.getTeam("playerss").setSuffix("§a" +main.getPlayers().size()+ "/§a8");
            main.scoreboard.getTeam("playerss").setPrefix("Alive ");
            main.scoreboard.getTeam("bv").setSuffix("§9Villager: "+main.blife+"§9♥");
            main.scoreboard.getTeam("rv").setSuffix("§cVillager: "+main.rlife+"§c♥");
            main.scoreboard.getTeam("gv").setSuffix("§aVillager: "+main.glife+"§a♥");
            main.scoreboard.getTeam("yv").setSuffix("§eVillager: "+main.ylife+"§e♥");                                                                        
        }
        if(main.getPlayers().size() <= 1){
            cancel();
        }
        if(timer == 300){
            wall(Material.BEDROCK);
            wall2(Material.BEDROCK);
        }
        if(timer == 290){
            for(Player list : main.getPlayers()) {
                list.sendMessage("§4The Walls have fallen");
            }
            wall(Material.AIR);
            wall2(Material.AIR);
        }
        if(timer == 0){
            MWdestroy cycle = new MWdestroy(main);
            cycle.runTaskTimer(main, 0, 60);
            main.r = main.getConfig().getInt("Destruction.radius");
            for(Player list : main.getPlayers()) {
                list.sendMessage("§0Death match enabled");
            }
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
                main.blife = 0;
                main.rlife = 0;
                main.glife = 0;
                main.ylife = 0;
            }
            cancel();
        }
        timer--;
        d++;
    }
    private void wall(Material m){
        World w = Bukkit.getWorld("world");
        int x = main.getConfig().getInt("Walls.wall1.cord1.x");
        int y = main.getConfig().getInt("Walls.wall1.cord1.y");
        int z = main.getConfig().getInt("Walls.wall1.cord1.z");
        int x2 = main.getConfig().getInt("Walls.wall1.cord2.x");
        int y2 = main.getConfig().getInt("Walls.wall1.cord2.y");
        int z2 = main.getConfig().getInt("Walls.wall1.cord2.z");
        int mx = Math.max(x, x2);
        int my = Math.max(y, y2);
        int mz = Math.max(z, z2);
        int mix = Math.min(x, x2);
        int miy = Math.min(y, y2);
        int miz = Math.min(z, z2);
        int zi = Math.abs(mz - miz);
        int xi = Math.abs(mx - mix);
        int yi = Math.abs(my - miy);
        for(int i = 0; i <= zi; i++){
            assert w != null;
            w.getBlockAt(mix, miy, miz+i).setType(m);
            for(int u = 0; u <= xi; u++){
                w.getBlockAt(mix+u, miy, miz+i).setType(m);
                for(int n = 0; n <= yi; n++){
                    w.getBlockAt(mix+u, miy+n, miz+i).setType(m);
                }
            }
        }
    }
    private void wall2(Material m){
        World w = Bukkit.getWorld("world");
        int x = main.getConfig().getInt("Walls.wall2.cord1.x");
        int y = main.getConfig().getInt("Walls.wall2.cord1.y");
        int z = main.getConfig().getInt("Walls.wall2.cord1.z");
        int x2 = main.getConfig().getInt("Walls.wall2.cord2.x");
        int y2 = main.getConfig().getInt("Walls.wall2.cord2.y");
        int z2 = main.getConfig().getInt("Walls.wall2.cord2.z");
        int mx = Math.max(x, x2);
        int my = Math.max(y, y2);
        int mz = Math.max(z, z2);
        int mix = Math.min(x, x2);
        int miy = Math.min(y, y2);
        int miz = Math.min(z, z2);
        int zi = Math.abs(mz - miz);
        int xi = Math.abs(mx - mix);
        int yi = Math.abs(my - miy);
        for(int i = 0; i <= zi; i++){
            assert w != null;
            w.getBlockAt(mix, miy, miz+i).setType(m);
            for(int u = 0; u <= xi; u++){
                w.getBlockAt(mix+u, miy, miz+i).setType(m);
                for(int n = 0; n <= yi; n++){
                    w.getBlockAt(mix+u, miy+n, miz+i).setType(m);
                }
            }
        }
    }
}
