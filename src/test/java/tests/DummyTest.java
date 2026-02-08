package tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class DummyTest {

    private static final Logger log =
        LoggerFactory.getLogger(DummyTest.class);

    @Test
    public void logTest() {
        log.info("🔥 LOGGING SANITY CHECK 🔥");
    }
}
