package net.plazmix.coordinator.plugin;

import lombok.*;
import net.plazmix.coordinator.PlazmixCoordinator;
import net.plazmix.coordinator.config.YamlConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@RequiredArgsConstructor
public class Plugin {

    private final PluginDescription description;
    private YamlConfiguration config;

    @Setter(AccessLevel.PRIVATE)
    private boolean enabled;

    protected void onLoad(PlazmixCoordinator coordinator) {
        // override me.
    }

    protected void onEnable(PlazmixCoordinator coordinator) {
        // override me.
    }

    protected void onDisable(PlazmixCoordinator coordinator) {
        // override me.
    }

    public final Path getPluginFolder() {
        return PlazmixCoordinator.getInstance().getFolder().resolve(description.getName());
    }

    @SneakyThrows
    public final PluginClasspathResource saveResource(boolean reset, String filepath) {
        Path pluginFolder = getPluginFolder();

        if (!Files.exists(pluginFolder)) {
            Files.createDirectories(pluginFolder);
        }

        // TODO - 04.02.2022 - сделать через classpath.
        return null;
    }

    public final void saveDefaultConfig() {
        String filepath = "config.yml";

        PluginClasspathResource classpathResource = this.saveResource(false, filepath);
        if (classpathResource != null) {
            classpathResource.save(getPluginFolder().resolve(filepath));
        }

        this.reloadConfig();
    }

    public final void reloadConfig() {
        String filepath = "config.yml";

        PluginClasspathResource classpathResource = this.saveResource(false, filepath);
        if (classpathResource != null) {

            classpathResource.load(getPluginFolder().resolve(filepath));
            config = classpathResource.asContentYaml();
        }
    }

    public final void loadPlugin() {
        if (isEnabled()) {
            return;
        }

        onLoad(PlazmixCoordinator.getInstance());
    }

    public final void enablePlugin() {
        if (isEnabled()) {
            return;
        }

        PlazmixCoordinator coordinator = PlazmixCoordinator.getInstance();
        setEnabled(true);

        onEnable(coordinator);
    }

    public final void disablePlugin() {
        if (!isEnabled()) {
            return;
        }

        PlazmixCoordinator coordinator = PlazmixCoordinator.getInstance();
        setEnabled(false);

        onDisable(coordinator);
    }

}
