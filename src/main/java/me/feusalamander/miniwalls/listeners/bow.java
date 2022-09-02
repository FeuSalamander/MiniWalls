package me.feusalamander.miniwalls.listeners;

import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
public class bow implements Listener {
    private MiniWalls main;
    public bow(MiniWalls main) {
        this.main = main;
    }
    @EventHandler
    public void arrow(ProjectileHitEvent e) {
        Player player = (Player) e.getEntity().getShooter();
        if (main.getPlayers().contains(player)) {
            if (e.getEntity() instanceof Arrow) {
                Arrow a = (Arrow) e.getEntity();
                World world = e.getEntity().getWorld();
                int radius = 1;
                Block middle = a.getLocation().getBlock();
                for (int x = radius; x >= -radius; x--) {
                    for (int y = radius; y >= -radius; y--) {
                        for (int z = radius; z >= -radius; z--) {
                            if (middle.getRelative(x, y, z).getType() == Material.BLUE_WOOL) {
                                middle.getRelative(x, y, z).setType(Material.AIR);
                                middle.getRelative(x, y, z).getWorld().playSound(middle.getRelative(x, y, z).getLocation(), Sound.BLOCK_WOOL_BREAK, 5, 1);
                            }else if (middle.getRelative(x, y, z).getType() == Material.RED_WOOL) {
                                middle.getRelative(x, y, z).setType(Material.AIR);
                                middle.getRelative(x, y, z).getWorld().playSound(middle.getRelative(x, y, z).getLocation(), Sound.BLOCK_WOOL_BREAK, 5, 1);
                            }else if (middle.getRelative(x, y, z).getType() == Material.GREEN_WOOL) {
                                middle.getRelative(x, y, z).setType(Material.AIR);
                                middle.getRelative(x, y, z).getWorld().playSound(middle.getRelative(x, y, z).getLocation(), Sound.BLOCK_WOOL_BREAK, 5, 1);
                            }else if (middle.getRelative(x, y, z).getType() == Material.YELLOW_WOOL) {
                                middle.getRelative(x, y, z).setType(Material.AIR);
                                middle.getRelative(x, y, z).getWorld().playSound(middle.getRelative(x, y, z).getLocation(), Sound.BLOCK_WOOL_BREAK, 5, 1);
                            }
                        }
                    }
                }
                a.remove();
            }
        }
    }
}
