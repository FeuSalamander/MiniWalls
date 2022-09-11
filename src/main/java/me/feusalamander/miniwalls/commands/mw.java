package me.feusalamander.miniwalls.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
public class mw implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 0){
                p.sendMessage("§1§l---------------------------------");
                p.sendMessage("    §4All the commands of the mini walls plugin");
                p.sendMessage("");
                p.sendMessage("§5mw gui: §cOpen the Gui to see the leaderboard, the stats and to play");
                p.sendMessage("§5mw help: §cShow this help");
                p.sendMessage("§5mw join: §cJoin the MiniWalls game");
                p.sendMessage("§5mw leave: §cLeave the MiniWalls game");
                p.sendMessage("§5mw reload: §cReload the config");
                p.sendMessage("§5mw setspawn: §cSet the loc of the waiting room");
                p.sendMessage("§5mw setlobby: §cSet the loc of the main lobby");
                p.sendMessage("§5mw setbluebase: §cSet the spawn of the §9Blue §cteam");
                p.sendMessage("§5mw setredbase: §cSet the spawn of the §cRed §cteam");
                p.sendMessage("§5mw setgreenbase: §cSet the spawn of the §aGreen §cteam");
                p.sendMessage("§5mw setyellowbase: §cSet the spawn of the §eYellow §cteam");
                p.sendMessage("§5mw setbluevillager: §cSet the spawn of the §9Blue §cvillager");
                p.sendMessage("§5mw setredvillager: §cSet the spawn of the §cRed §cvillager");
                p.sendMessage("§5mw setgreenvillager: §cSet the spawn of the §aGreen §cvillager");
                p.sendMessage("§5mw setyellowvillager: §cSet the spawn of the §eYellow §cvillager");
                p.sendMessage("§1§l---------------------------------");
            }
        }
        return true;
    }
}
