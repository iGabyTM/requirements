package me.gabytm.minecraft.util.requirements.tests.requirements;

import me.gabytm.minecraft.util.requirements.bukkit.BukkitRequirementProcessor;
import me.gabytm.minecraft.util.requirements.bukkit.PlayerRequirement;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RequirementProcessor extends BukkitRequirementProcessor<Player> {

    private final YamlConfiguration config;

    public static RequirementProcessor create(@NotNull final String file) {
        return new RequirementProcessor(file);
    }

    private RequirementProcessor(@NotNull final String file) {
        this.config = YamlConfiguration.loadConfiguration(new InputStreamReader(getClass().getResourceAsStream("/" + file + ".yml")));
        register(new NumberComparisonRequirementFactory());
    }

    public @NotNull List<PlayerRequirement> loadRequirements(@NotNull final String sectionName) {
        final List<PlayerRequirement> requirements = new ArrayList<>();

        for (final String it : config.getConfigurationSection(sectionName).getKeys(false)) {
            requirements.add((PlayerRequirement) process(config.getConfigurationSection(sectionName + '.' + it)));
        }

        return requirements;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

}
