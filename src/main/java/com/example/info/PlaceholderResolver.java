package com.example.info;

import io.hytale.server.entity.player.Player;
import io.hytale.server.mod.PluginContext;

public class PlaceholderResolver {

    public static String resolve(
            String input,
            Player player,
            PluginContext context,
            String section
    ) {
        return input
                .replace("{player}", player.getName())
                .replace("{uuid}", player.getUuid().toString())
                .replace("{server}", context.getServer().getName())
                .replace("{online}", String.valueOf(context.getServer().getOnlinePlayerCount()))
                .replace("{max}", String.valueOf(context.getServer().getMaxPlayers()))
                .replace("{section}", section);
    }
}
