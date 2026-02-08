package qamarvel.framework.waits;

import java.time.Duration;

public final class WaitConstants {

    public static final Duration SHORT  = Duration.ofSeconds(5);
    public static final Duration MEDIUM = Duration.ofSeconds(15);
    public static final Duration LONG   = Duration.ofSeconds(30);
    public static final Duration LONGER   = Duration.ofSeconds(60);

    private WaitConstants() {
        // prevent instantiation
    }
}

