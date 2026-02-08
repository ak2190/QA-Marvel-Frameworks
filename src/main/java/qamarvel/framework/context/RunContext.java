package qamarvel.framework.context;

public final class RunContext {

    private static String runDirectory;

    private RunContext() {}

    public static void setRunDirectory(String dir) {
        runDirectory = dir;
    }

    public static String getRunDirectory() {
        return runDirectory;
    }
}
