package qamarvel.framework.config;

public final class ConfigManager {

    private ConfigManager() {}

    public static boolean isHighlightEnabled() {
        return Boolean.parseBoolean(ConfigReader.get("ui.highlight.elements"));
    }
}
