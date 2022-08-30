package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import java.util.LinkedList;
import java.util.List;
public class MapReset implements Listener {
    private MiniWalls main;
    public MapReset(MiniWalls main) {
        this.main = main;
    }
    private List<String> CHANGES = new LinkedList<String>();
    private List<String> CHANGES2 = new LinkedList<String>();
    public void restore(){
        int blockss = 0;
        for(String b : CHANGES2){
            String[] blockdata = b.split(":");
            World world = Bukkit.getWorld(blockdata[0]);
            int x = Integer.parseInt(blockdata[1]);
            int y = Integer.parseInt(blockdata[2]);
            int z = Integer.parseInt(blockdata[3]);
            world.getBlockAt(x, y, z).setType(Material.AIR);
            blockss++;
        }
        int blocks = 0;
        for(String b : CHANGES){
            String[] blockdata = b.split(":");
            Material id = Material.valueOf((blockdata[0]));
            World world = Bukkit.getWorld(blockdata[1]);
            int x = Integer.parseInt(blockdata[2]);
            int y = Integer.parseInt(blockdata[3]);
            int z = Integer.parseInt(blockdata[4]);
            world.getBlockAt(x, y, z).setType(id);
            blocks++;
        }
    }
    @EventHandler
    public void onBlockb(BlockBreakEvent e){
        Block b = e.getBlock();
        Player p = e.getPlayer();
        if(main.getPlayers().contains(p)){
            String block = b.getType() + ":" + b.getWorld().getName() +
                    ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();
            CHANGES.add(block);
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            if(main.scoreboard.getTeam("Blue").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.BLUE_WOOL));
            }else if(main.scoreboard.getTeam("Red").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.RED_WOOL));
            }else if(main.scoreboard.getTeam("Green").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.GREEN_WOOL));
            }else if(main.scoreboard.getTeam("YELLOW").hasPlayer(p)){
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.YELLOW_WOOL));
            }
        }
    }
    @EventHandler
    public void onBlockp(BlockPlaceEvent e){
        Block b = e.getBlock();
        Player p = e.getPlayer();
        if(main.getPlayers().contains(p)){
            if(e.getBlock().getType() == Material.BLACK_WOOL){
                String block = b.getWorld().getName() +
                        ":" + b.getX() + ":" + b.getY() + ":" + b.getZ();
                CHANGES2.add(block);
        }
    }
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        Player player = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("/mw join")){
            if(main.getConfig().getInt("reset") == 1){
                main.getConfig().set("reset", 0);
                restore();
            }
        }


}
}
