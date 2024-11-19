package de.proxyfile.simplyChest.commands;

import de.proxyfile.simplyChest.essentials.ChestHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class testcmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player ply = (Player) commandSender;
        ChestHelper.open(ply);
        return false;
    }
}
