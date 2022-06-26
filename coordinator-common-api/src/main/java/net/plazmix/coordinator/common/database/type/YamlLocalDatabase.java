package net.plazmix.coordinator.common.database.type;

import net.plazmix.coordinator.common.database.LocalDatabase;
import net.plazmix.coordinator.common.database.service.LocalDatabaseService;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class YamlLocalDatabase implements LocalDatabase {

    private LocalDatabaseService service;
    private Yaml yaml;

    private final Map<String, Object> storageMap = new LinkedHashMap<>();

    public String getString(String path) {
        return (String) storageMap.get(path);
    }

    public List<String> getStringList(String path) {
        return new ArrayList<>((List<String>) storageMap.get(path));
    }

    @Override
    public void store() {
        // todo
    }

    @Override
    public void reload() {
        this.init(service, service.load());
    }

    @Override
    public void init(LocalDatabaseService service, ByteArrayInputStream data) {
        this.service = service;

        // Init yaml constructors.
        if (yaml == null) {
            Constructor constructor = new Constructor();

            PropertyUtils propertyUtils = new PropertyUtils();
            propertyUtils.setSkipMissingProperties(true);

            constructor.setPropertyUtils(propertyUtils);
            this.yaml = new Yaml(constructor);
        }

        // Load yaml storage.
        try {
            storageMap.putAll(yaml.loadAs(data, LinkedHashMap.class));
            data.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
