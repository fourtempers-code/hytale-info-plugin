package com.example.info;

import io.hytale.server.mod.Plugin;
import io.hytale.server.mod.PluginContext;

public class InfoMod implements Plugin {

    private static InfoConfig config;

    @Override
    public void onLoad(PluginContext context) {
        reload(context);

        context.getCommandManager().register(new InfoCommand(context));
        context.getEventBus().register(new PlayerJoinListener(context));
    }

    public static void reload(PluginContext context) {
        config = InfoConfig.load(context);
    }

    public static InfoConfig getConfig() {
        return config;
    }
}
