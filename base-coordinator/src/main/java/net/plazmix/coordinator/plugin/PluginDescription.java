package net.plazmix.coordinator.plugin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class PluginDescription {

    public static PluginDescription create(String name, String author) {
        return new PluginDescription(name, author);
    }

    public static PluginDescription create(String name, String author, String description) {
        return new PluginDescription(name, author, description);
    }

    String name;
    String author;

    @NonFinal
    String description;
}
