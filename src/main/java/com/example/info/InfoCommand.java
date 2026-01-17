package com.example.info;

import io.hytale.server.command.Command;
import io.hytale.server.command.CommandContext;
import io.hytale.server.entity.player.Player;
import io.hytale.server.mod.PluginContext;

import java.util.List;
import java.util.Map;

public class InfoCommand extends Command {

    private final PluginContext context;

    public InfoCommand(PluginContext context) {
        super("info");
        this.context = context;
        setDescription("Displays server information");
        setPermission("info.use");
    }

    @Override
    public void execute(CommandContext ctx) {
        if (!(ctx.getSender() instanceof Player player)) {
            ctx.getSender().sendMessage("Players only.");
            return;
        }

        String[] args = ctx.getArgs();

        // Reload config
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!ctx.hasPermission("info.reload")) {
                player.sendMessage(TextFormatter.color("&cYou do not have permission."));
                return;
            }

            InfoMod.reload(context);
            player.sendMessage(TextFormatter.color("&aInfo config reloaded."));
            return;
        }

        InfoConfig config = InfoMod.getConfig();
        Map<String, List<String>> sections = config.getSections();

        String section = args.length == 0
                ? config.getDefaultSection()
                : args[0].toLowerCase();

        if (!sections.containsKey(section)) {
            player.sendMessage(TextFormatter.color("&cUnknown info section."));
            player.sendMessage(TextFormatter.color("&7Available: &f" + String.join(", ", sections.keySet())));
            return;
        }

        for (String line : sections.get(section)) {
            String resolved = PlaceholderResolver.resolve(
                    line,
                    player,
                    context,
                    section
            );

            player.sendMessage(TextFormatter.color(resolved));
        }
    }
}
