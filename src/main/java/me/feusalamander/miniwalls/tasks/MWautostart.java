package me.feusalamander.miniwalls.tasks;
import me.feusalamander.miniwalls.MWstates;
import me.feusalamander.miniwalls.MiniWalls;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
public class MWautostart extends BukkitRunnable{
    private int timer = 20;
    private MiniWalls main;

    public MWautostart(MiniWalls main) {
        this.main = main;
    }


    @Override
    public void run() {
        for(Player pls : main.getPlayers()){
            pls.setLevel(timer);
        }
        if(main.getPlayers().size() < main.getConfig().getInt("MinPlayers")){
            cancel();
            Bukkit.broadcastMessage("§4Not enough players to start");
            main.setState(MWstates.WAITING);
        }
        if(timer == 10)
        {
            for(Player list : main.getPlayers()) {
                list.sendMessage("§aThe Game is starting in 10s");
            }
        }
        if(timer == 3)
        {
            for(Player list : main.getPlayers()) {
                list.sendMessage("§eThe Game is starting in 3s");
            }
        }
        if(timer == 2)
        {
            for(Player list : main.getPlayers()) {
                list.sendMessage("§6The Game is starting in 2s");
            }
        }
        if(timer == 1)
        {
            for(Player list : main.getPlayers()) {
                list.sendMessage("§4The Game is starting in 1s");
            }
        }
        if(timer == 0)
        {
            for(Player list : main.getPlayers()) {
                list.sendMessage("§0The Game is starting, §5Prepare yourself for the battle");
            }
            main.setState(MWstates.PLAYING);
            for(int i = 0; i < main.getPlayers().size(); i++)
            {
                Player player = main.getPlayers().get(i);
                Location spawnblue  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.BlueBase.x"), main.getConfig().getInt("Locations.Bases.BlueBase.y"), main.getConfig().getInt("Locations.Bases.BlueBase.z"));
                Location spawnred  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.RedBase.x"), main.getConfig().getInt("Locations.Bases.RedBase.y"), main.getConfig().getInt("Locations.Bases.RedBase.z"));
                Location spawngreen  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.GreenBase.x"), main.getConfig().getInt("Locations.Bases.GreenBase.y"), main.getConfig().getInt("Locations.Bases.GreenBase.z"));
                Location spawnyellow  = new Location(Bukkit.getWorld("world"), main.getConfig().getInt("Locations.Bases.YellowBase.x"), main.getConfig().getInt("Locations.Bases.YellowBase.y"), main.getConfig().getInt("Locations.Bases.YellowBase.z"));
                player.setGameMode(GameMode.SURVIVAL);
                player.getInventory().clear();
                player.getInventory().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
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
            cancel();
        }
        timer--;
    }
}
