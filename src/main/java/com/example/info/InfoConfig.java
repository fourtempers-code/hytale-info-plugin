package com.example.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.hytale.server.mod.PluginContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class InfoConfig {

    private String defaultSection;
    private boolean showRulesOnJoin = true;
    private int joinDelaySeconds = 5;
    private Map<String, List<String>> sections;

    public String getDefaultSection() {
        return defaultSection;
    }

    public boolean isShowRulesOnJoin() {
        return showRulesOnJoin;
    }

    public int getJoinDelaySeconds() {
        return joinDelaySeconds;
    }

    public Map<String, List<String>> getSections() {
        return sections;
    }

    public static InfoConfig load(PluginContext context) {
        try {
            Path dir = context.getConfigDirectory();
            Path file = dir.resolve("info.json");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            if (!Files.exists(file)) {
                Files.createDirectories(dir);

                InfoConfig defaults = new InfoConfig();
                defaults.defaultSection = "rules";
                defaults.showRulesOnJoin = true;
                defaults.joinDelaySeconds = 5;
                defaults.sections = Map.of(
                        "rules", List.of("&aEdit config/info.json to customize rules.")
                );

                Files.writeString(file, gson.toJson(defaults));
                return defaults;
            }

            return gson.fromJson(Files.readString(file), InfoConfig.class);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load info config", e);
        }
    }
}
