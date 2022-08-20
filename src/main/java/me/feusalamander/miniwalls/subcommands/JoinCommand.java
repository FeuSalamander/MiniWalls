package me.feusalamander.miniwalls.subcommands;
import me.feusalamander.miniwalls.MiniWalls;
import me.feusalamander.miniwalls.commands.SubCommands;
import org.bukkit.entity.Player;


public class JoinCommand extends SubCommands {
    private MiniWalls main;
    public JoinCommand(MiniWalls main){
        this.main = main;
    }
    public JoinCommand() {

    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join the MiniWalls game";
    }

    @Override
    public String getSyntax() {
        return "/mw join";
    }

    @Override
    public void perform(Player player, String[] args) {

    }
}