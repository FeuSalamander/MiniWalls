package me.feusalamander.miniwalls.listeners;

import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class villagerdmg implements Listener {
    private boolean bc = true;
    private boolean rc = true;
    private boolean gc = true;
    private boolean yc = true;
    private MiniWalls main;
    public villagerdmg(MiniWalls main) {
        this.main = main;
    }
    @EventHandler
    public void ondmg(EntityDamageByEntityEvent e){
        Entity victim = e.getEntity();
        Entity damager = e.getDamager();
        if(damager instanceof Player){
            Player player = (Player)damager;
            if(main.getPlayers().contains(player)){
                if(victim.getName().equalsIgnoreCase("§9Blue Villager")){
                    if(bc){
                        if(!main.scoreboard.getTeam("Blue").hasPlayer(player)){
                            main.blife--;
                            bc = false;
                            Bukkit.getScheduler().runTaskLater(main, () -> bc = true, 30);
                            if(player.getHealth() > 4){
                                player.damage(3);
                            }
                            for(OfflinePlayer team : main.scoreboard.getTeam("Blue").getPlayers()){
                                if(team.isOnline()){
                                    Player p = (Player) team;
                                    p.sendMessage("§9Your Villager is attacked");
                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
                                }
                            }
                            if(main.blife == 0){
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§9The Blue Villager died");
                                }
                                e.setDamage(800);
                            }
                        }else{
                            e.setCancelled(true);
                        }
                    }else{
                        e.setCancelled(true);
                    }
                }else if(victim.getName().equalsIgnoreCase("§cRed Villager")){
                    if(rc){
                        if(!main.scoreboard.getTeam("Red").hasPlayer(player)){
                            main.rlife--;
                            rc = false;
                            Bukkit.getScheduler().runTaskLater(main, () -> rc = true, 30);
                            if(player.getHealth() > 4){
                                player.damage(3);
                            }
                            for(OfflinePlayer team : main.scoreboard.getTeam("Red").getPlayers()){
                                if(team.isOnline()){
                                    Player p = (Player) team;
                                    p.sendMessage("§cYour Villager is attacked");
                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
                                }
                            }
                            if(main.rlife == 0){
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§cThe Red Villager died");
                                }
                                e.setDamage(800);
                            }
                        }else{
                            e.setCancelled(true);
                        }
                    }else{
                        e.setCancelled(true);
                    }
                }else if(victim.getName().equalsIgnoreCase("§aGreen Villager")){
                    if(gc){
                        if(!main.scoreboard.getTeam("Green").hasPlayer(player)){
                            main.glife--;
                            gc = false;
                            Bukkit.getScheduler().runTaskLater(main, () -> gc = true, 30);
                            if(player.getHealth() > 4){
                                player.damage(3);
                            }
                            for(OfflinePlayer team : main.scoreboard.getTeam("Green").getPlayers()){
                                if(team.isOnline()){
                                    Player p = (Player) team;
                                    p.sendMessage("§aYour Villager is attacked");
                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
                                }
                            }
                            if(main.glife == 0){
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§aThe Green Villager died");
                                }
                                e.setDamage(800);
                            }
                        }else{
                            e.setCancelled(true);
                        }
                    }else{
                        e.setCancelled(true);
                    }
                }else if(victim.getName().equalsIgnoreCase("§eYellow Villager")){
                    if(yc){
                        if(!main.scoreboard.getTeam("Yellow").hasPlayer(player)){
                            main.ylife--;
                            yc = false;
                            Bukkit.getScheduler().runTaskLater(main, () -> yc = true, 30);
                            if(player.getHealth() > 4){
                                player.damage(3);
                            }
                            for(OfflinePlayer team : main.scoreboard.getTeam("Yellow").getPlayers()){
                                if(team.isOnline()){
                                    Player p = (Player) team;
                                    p.sendMessage("§eYour Villager is attacked");
                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
                                }
                            }
                            if(main.ylife == 0){
                                for(Player list : main.getPlayers()) {
                                    list.sendMessage("§eThe Yellow Villager died");
                                }
                                e.setDamage(800);
                            }
                        }else{
                            e.setCancelled(true);
                        }
                    }else{
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
