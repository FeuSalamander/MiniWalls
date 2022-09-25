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
                p.sendMessage("§1§l---------------------------------");
            }
        }
        return true;
    }
}
