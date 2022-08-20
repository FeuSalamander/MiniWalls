package me.feusalamander.miniwalls.listeners;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DMGlistener implements Listener {
    private MiniWalls main;
    public DMGlistener(MiniWalls main) {
        this.main = main;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Entity victim = event.getEntity();
        if(main.getPlayers().contains(victim)){
            if(victim instanceof Player){
                Player player = (Player)victim;
                if(main.isState(MWstates.WAITING)){
                    event.setCancelled(true);
                }else if(main.isState(MWstates.STARTING)){
                        event.setCancelled(true);
                    }else{
                    if(player.getHealth() <= event.getDamage()){
                        event.setDamage(0);
                        main.eliminate(player);
                }
            }
        }
    }
}
}
