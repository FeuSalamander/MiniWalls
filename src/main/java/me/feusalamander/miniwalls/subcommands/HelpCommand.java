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
        player.sendMessage("§5mw setspawn: §cSet the loc of the waiting room");
        player.sendMessage("§5mw setlobby: §cSet the loc of the main lobby");
        player.sendMessage("§5mw setbluebase: §cSet the spawn of the §9Blue §cteam");
        player.sendMessage("§5mw setredbase: §cSet the spawn of the §cRed §cteam");
        player.sendMessage("§5mw setgreenbase: §cSet the spawn of the §aGreen §cteam");
        player.sendMessage("§5mw setyellowbase: §cSet the spawn of the §eYellow §cteam");
        player.sendMessage("§1§l---------------------------------");
    }
}
