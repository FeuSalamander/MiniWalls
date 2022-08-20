package me.feusalamander.miniwalls.subcommands;
import me.feusalamander.miniwalls.MiniWalls;
import me.feusalamander.miniwalls.commands.SubCommands;
import org.bukkit.entity.Player;


public class LeaveCommand extends SubCommands {
    private MiniWalls main;
    public LeaveCommand(MiniWalls main){
        this.main = main;
    }
    public LeaveCommand() {

    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leave the MiniWalls game";
    }

    @Override
    public String getSyntax() {
        return "/mw leave";
    }

    @Override
    public void perform(Player player, String[] args) {

    }
}