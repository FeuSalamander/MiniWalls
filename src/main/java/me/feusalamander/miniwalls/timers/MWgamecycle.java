package me.feusalamander.miniwalls.timers;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
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
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command = "fill "+main.getConfig().getInt("Walls.wall1.cord1.x")+" "+main.getConfig().getInt("Walls.wall1.cord1.y")+" "+main.getConfig().getInt("Walls.wall1.cord1.z")+" "+main.getConfig().getInt("Walls.wall1.cord2.x")+" "+main.getConfig().getInt("Walls.wall1.cord2.y")+" "+main.getConfig().getInt("Walls.wall1.cord2.z")+" minecraft:bedrock";
            Bukkit.dispatchCommand(console, command);
            String wall2 = "fill "+main.getConfig().getInt("Walls.wall2.cord1.x")+" "+main.getConfig().getInt("Walls.wall2.cord1.y")+" "+main.getConfig().getInt("Walls.wall2.cord1.z")+" "+main.getConfig().getInt("Walls.wall2.cord2.x")+" "+main.getConfig().getInt("Walls.wall2.cord2.y")+" "+main.getConfig().getInt("Walls.wall2.cord2.z")+" minecraft:bedrock";
            Bukkit.dispatchCommand(console, wall2);
        }
        if(timer == 290){
            for(Player list : main.getPlayers()) {
                list.sendMessage("§4The Walls have fallen");
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "fill "+main.getConfig().getInt("Walls.wall1.cord1.x")+" "+main.getConfig().getInt("Walls.wall1.cord1.y")+" "+main.getConfig().getInt("Walls.wall1.cord1.z")+" "+main.getConfig().getInt("Walls.wall1.cord2.x")+" "+main.getConfig().getInt("Walls.wall1.cord2.y")+" "+main.getConfig().getInt("Walls.wall1.cord2.z")+" minecraft:air";
                Bukkit.dispatchCommand(console, command);
                String wall2 = "fill "+main.getConfig().getInt("Walls.wall2.cord1.x")+" "+main.getConfig().getInt("Walls.wall2.cord1.y")+" "+main.getConfig().getInt("Walls.wall2.cord1.z")+" "+main.getConfig().getInt("Walls.wall2.cord2.x")+" "+main.getConfig().getInt("Walls.wall2.cord2.y")+" "+main.getConfig().getInt("Walls.wall2.cord2.z")+" minecraft:air";
                Bukkit.dispatchCommand(console, wall2);
            }
        }
        if(timer == 0){
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
}
