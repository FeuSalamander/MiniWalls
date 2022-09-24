package me.feusalamander.miniwalls.timers;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import me.feusalamander.miniwalls.listeners.MapReset;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
public class MWdestroy extends BukkitRunnable {
    private final MiniWalls main;
    private int timer = 10;
    public MWdestroy(MiniWalls main) {
        this.main = main;
    }

    @Override
    public void run() {
        if(main.r > 0){
            for(double t = 0; t<50; t+=0.1){
                float radius = main.r;
                float x = radius*(float)Math.sin(t);
                float z = radius*(float)Math.cos(t);
                Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Destruction.center.x"), main.getConfig().getInt("Destruction.center.y"), main.getConfig().getInt("Destruction.center.z"));
                for(int i = 0; i <= main.getConfig().getInt("Destruction.deep"); i++){
                    Block b = loc.getWorld().getBlockAt((int)loc.getX() + (int)x, (int)loc.getY()-i, (int)loc.getZ() + (int)z);
                    if(!(b.getType() == Material.AIR)){
                        if(!(b.getType() == Material.BLUE_WOOL || b.getType() == Material.RED_WOOL || b.getType() == Material.GREEN_WOOL || b.getType() == Material.YELLOW_WOOL)){
                            MapReset.CHANGES.add(b.getState());
                        }
                        b.setType(Material.AIR);
                    }
                }
            }
            main.r--;
            timer--;
        }else{
            cancel();
        }
        if(timer == 0 && main.r > 0){
            timer = 10;
        }else if(timer == 0){
            cancel();
        }
        if(main.isState(MWstates.WAITING) || main.isState(MWstates.STARTING)){
            cancel();
        }
    }
}
