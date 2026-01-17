package com.example.info;

import io.hytale.server.entity.player.Player;
import io.hytale.server.event.Subscribe;
import io.hytale.server.event.player.PlayerJoinEvent;
import io.hytale.server.mod.PluginContext;

import java.util.List;
import java.util.Map;

public class PlayerJoinListener {

    private final PluginContext context;

    public PlayerJoinListener(PluginContext context) {
        this.context = context;
    }

    @Subscribe
    public void onPlayerJoin(PlayerJoinEvent event) {
        InfoConfig config = InfoMod.getConfig();

        if (!config.isShowRulesOnJoin()) {
            return;
        }

        int delaySeconds = Math.max(0, config.getJoinDelaySeconds());

        context.getScheduler().schedule(() -> {
            Player player = event.getPlayer();

            // Player may have disconnected during delay
            if (!player.isOnline()) {
                return;
            }

            Map<String, List<String>> sections = config.getSections();

            if (!sections.containsKey("rules")) {
                return;
            }

            for (String line : sections.get("rules")) {
                String resolved = PlaceholderResolver.resolve(
                        line,
                        player,
                        context,
                        "rules"
                );

                player.sendMessage(TextFormatter.color(resolved));
            }

        }, delaySeconds);
    }
}
