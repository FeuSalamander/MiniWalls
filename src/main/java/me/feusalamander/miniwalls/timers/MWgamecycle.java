package me.feusalamander.miniwalls.timers;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
public class MWgamecycle extends BukkitRunnable {
    private MiniWalls main;
    private int timer = 300;
    public MWgamecycle(MiniWalls main) {
        this.main = main;
    }

    @Override
    public void run() {
        for(Player pls : main.getPlayers()){
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
        if(timer == 280){
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
    }
    private void wall(Material m){
        World w = Bukkit.getWorld("world");
        int x = main.getConfig().getInt("Walls.wall1.cord1.x");
        int y = main.getConfig().getInt("Walls.wall1.cord1.y");
        int z = main.getConfig().getInt("Walls.wall1.cord1.z");
        int x2 = main.getConfig().getInt("Walls.wall1.cord2.x");
        int y2 = main.getConfig().getInt("Walls.wall1.cord2.y");
        int z2 = main.getConfig().getInt("Walls.wall1.cord2.z");
        int zi = Math.abs(z-z2);
        int xi = Math.abs(x-x2);
        int yi = Math.abs(y-y2);
        for(int i = 0; i <= zi; i++){
            assert w != null;
            w.getBlockAt(x, y, z+i).setType(m);
            for(int u = 0; u <= xi; u++){
                w.getBlockAt(x+u, y, z+i).setType(m);
                for(int n = 0; n <= yi; n++){
                    w.getBlockAt(x+u, y+n, z+i).setType(m);
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
        int zi = Math.abs(z-z2);
        int xi = Math.abs(x-x2);
        int yi = Math.abs(y-y2);
        for(int i = 0; i < zi; i++){
            w.getBlockAt(x, y, z+i).setType(m);
            for(int u = 0; u < xi; u++){
                w.getBlockAt(x+u, y, z+i).setType(m);
                for(int n = 0; n < yi; n++){
                    w.getBlockAt(x+u, y+n, z+i).setType(m);
                }
            }
        }
    }
}
