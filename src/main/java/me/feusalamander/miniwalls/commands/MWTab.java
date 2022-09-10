package me.feusalamander.miniwalls.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
public class MWTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            List<String> arguments = new ArrayList<>();
            arguments.add("help");
            arguments.add("join");
            arguments.add("stats");
            arguments.add("leaderboard");
            arguments.add("leave");
            arguments.add("reload");
            arguments.add("setspawn");
            arguments.add("setlobby");
            arguments.add("setbluebase");
            arguments.add("setredbase");
            arguments.add("setgreenbase");
            arguments.add("setyellowbase");
            arguments.add("setbluevillager");
            arguments.add("setredbasevillager");
            arguments.add("setgreenbasevillager");
            arguments.add("setyellowbasevillager");
            return arguments;
        }
        return null;
    }
}
