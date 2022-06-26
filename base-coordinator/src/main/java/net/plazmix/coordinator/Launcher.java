package net.plazmix.coordinator;

public final class Launcher {
    private Launcher() {}

    public static final String PROPERTY_COORDINATOR_HOST_KEY = ("plazmix.coordinator.host");
    public static final String PROPERTY_COORDINATOR_PORT_KEY = ("plazmix.coordinator.port");

    private static void initSystemProperties() {
        // System.getProperties().setProperty(PROPERTY_COORDINATOR_HOST_KEY, "135.181.39.144");
        System.getProperties().setProperty(PROPERTY_COORDINATOR_HOST_KEY, "127.0.0.1");
        System.getProperties().setProperty(PROPERTY_COORDINATOR_PORT_KEY, "5505");
    }

    public static String getStringProperty(String key) {
        return System.getProperties().getProperty(key);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getStringProperty(key));
    }

    public static double getDoubleProperty(String key) {
        return Double.parseDouble(getStringProperty(key));
    }

    public static void main(String[] args) {
        Launcher.initSystemProperties();

        PlazmixCoordinator plazmixCoordinator = new PlazmixCoordinator();
        plazmixCoordinator.launch();
    }

}
