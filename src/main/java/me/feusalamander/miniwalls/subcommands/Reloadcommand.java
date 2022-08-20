package me.feusalamander.miniwalls.subcommands;
import me.feusalamander.miniwalls.commands.SubCommands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Reloadcommand extends SubCommands {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload the config file";
    }

    @Override
    public String getSyntax() {
        return "/mw reload";
    }

    @Override
    public void perform(Player player, String[] args) {
    }
}