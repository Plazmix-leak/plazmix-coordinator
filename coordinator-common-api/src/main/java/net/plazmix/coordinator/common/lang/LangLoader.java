package net.plazmix.coordinator.common.lang;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.EnumMap;
import java.util.LinkedHashMap;

@SuppressWarnings("unchecked")
public final class LangLoader {

    private static final Yaml CONFIGURATED_YAML;
    static {
        Constructor constructor = new Constructor();

        PropertyUtils propertyUtils = new PropertyUtils();
        propertyUtils.setSkipMissingProperties(true);

        constructor.setPropertyUtils(propertyUtils);
        CONFIGURATED_YAML = new Yaml(constructor);
    }

    public static final Lang[] LOADED_LOCALES = Lang.values();
    public static final String LOCALES_LINK_PREFIX = ("https://gitlab.com/plazmix-locale");

    private final EnumMap<Lang, LangDatabase> localeDatabasesMap = new EnumMap<>(Lang.class);

    public LangDatabase loadLocaleStorage(Lang lang) {
        LangDatabase database = localeDatabasesMap.get(lang);

        if (database != null) {
            return database;
        }

        // TODO - 04.02.2022 - load all yaml files from directory-url.

        String urlString = LOCALES_LINK_PREFIX.concat(lang.getDirpath());
        try (InputStream inputStream = URI.create(urlString).toURL().openStream()) {

            database = new LangDatabase(lang, CONFIGURATED_YAML.loadAs(inputStream, LinkedHashMap.class));
            localeDatabasesMap.put(lang, database);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        return database;
    }

    public Lang getLang(String langCode, Lang def) {
        for (Lang lang : LOADED_LOCALES) {

            if (lang.getDirpath().substring(1).equalsIgnoreCase(langCode)) {
                return lang;
            }
        }

        return def;
    }

    public Lang getLang(String langCode) {
        return getLang(langCode, null);
    }

    public Lang getLang(int index, Lang def) {
        for (Lang lang : LOADED_LOCALES) {

            if (lang.ordinal() == index) {
                return lang;
            }
        }

        return def;
    }

    public Lang getLang(int index) {
        return getLang(index, null);
    }

}
