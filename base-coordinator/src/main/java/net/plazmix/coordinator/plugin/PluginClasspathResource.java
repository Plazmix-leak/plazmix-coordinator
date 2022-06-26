package net.plazmix.coordinator.plugin;

import net.plazmix.coordinator.config.YamlConfiguration;

import java.io.InputStream;
import java.nio.file.Path;

public record PluginClasspathResource(Plugin plugin, InputStream classpathStream) {

    public String asContentString() {
        return null; // TODO - 04.02.2022 - вернуть контент ресурса в виде текста.
    }

    public YamlConfiguration asContentYaml() {
        return null; // TODO - 04.02.2022 - вернуть контент ресурса в виде YAML конфигурации.
    }

    public void save(Path path) {

    }

    public void load(Path path) {

    }

}
