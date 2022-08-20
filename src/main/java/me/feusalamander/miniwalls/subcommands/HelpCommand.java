package me.feusalamander.miniwalls.subcommands;
import me.feusalamander.miniwalls.commands.SubCommands;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommands {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Show all the commands of the MiniWalls plugin";
    }

    @Override
    public String getSyntax() {
        return "/mw help";
    }

    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage("§1§l---------------------------------");
        player.sendMessage("    §4All the commands of the mini walls plugin");
        player.sendMessage("");
        player.sendMessage("§5mw help: §cShow this help");
        player.sendMessage("§5mw join: §cJoin the MiniWalls game");
        player.sendMessage("§5mw leave: §cLeave the MiniWalls game");
        player.sendMessage("§5mw reload: §cReload the config");
        player.sendMessage("§1§l---------------------------------");
    }
}
