package qamarvel.framework.auth.session;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SessionManager {
	
	// SessionManager responsible for handling capturing and restoring browser authentication sessions.

	
	private SessionManager() {
	}

	/*
	 * ===================== CAPTURE SESSION =====================
	 */
	public static void captureSession(WebDriver driver) {

		AuthSession session = new AuthSession();

		// 1. Capture browser cookies (authentication + session tracking)
		Set<Cookie> cookies = driver.manage().getCookies();
		session.setCookies(cookies);

		// 2. Capture localStorage (JWT tokens, preferences, app state)
		session.setLocalStorage(readStorage(driver, "localStorage"));

		// 3. Capture sessionStorage (temporary session data)
		session.setSessionStorage(readStorage(driver, "sessionStorage"));

		// 4. Persist the session (in memory, file, or database)
		SessionStore.save(session);
	}

	/*
	 * ===================== RESTORE SESSION =====================
	 * Restores a previously saved authenticated session into the browser.
	 */

	public static void injectSession(WebDriver driver, String baseUrl) {

		// Retrieve previously saved session
		AuthSession session = SessionStore.get();

		// 1. Load base domain (required before adding cookies)
		driver.get(baseUrl);

		// 2. Inject cookies into browser
		for (Cookie cookie : session.getCookies()) {
			driver.manage().addCookie(cookie);
		}

		// 3. Inject browser storage
		writeStorage(driver, "localStorage", session.getLocalStorage());
		writeStorage(driver, "sessionStorage", session.getSessionStorage());

		// 4. Reload application with authentication data present
		driver.navigate().refresh();

	}

	/*
	 * ===================== STORAGE HELPERS =====================
	 * 
     * Reads all values from browser storage (localStorage or sessionStorage).
     *
     * This method executes JavaScript in the browser to extract
     * all key-value pairs from the specified storage type.
     *
     * @param driver Active Selenium WebDriver instance
     * @param type Storage type ("localStorage" or "sessionStorage")
     * @return Map containing all storage key-value pairs
     *
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> readStorage(WebDriver driver, String type) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		Map<String, String> storage = new HashMap<>();

		String script = "var items = {}; " + "for (var i = 0; i < window." + type + ".length; i++) {"
				+ "  var key = window." + type + ".key(i);" + "  items[key] = window." + type + ".getItem(key);"
				+ "} return items;";

		storage.putAll((Map<String, String>) js.executeScript(script));
		return storage;
	}
	/**
     * Writes key-value pairs into browser storage.
     *
     * This method injects data into either localStorage or sessionStorage
     * using JavaScript execution.
     *
     * @param driver Active Selenium WebDriver instance
     * @param type Storage type ("localStorage" or "sessionStorage")
     * @param data Key-value pairs to write into browser storage
     */
	private static void writeStorage(WebDriver driver, String type, Map<String, String> data) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (Map.Entry<String, String> entry : data.entrySet()) {
			js.executeScript("window." + type + ".setItem(arguments[0], arguments[1]);", entry.getKey(),
					entry.getValue());
		}
	}
}
