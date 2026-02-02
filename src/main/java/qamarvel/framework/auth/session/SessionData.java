package qamarvel.framework.auth.session;

import java.util.Map;

import org.openqa.selenium.Cookie;

public class SessionData {

    private final Map<String, Cookie> cookies;
    private final Map<String, String> localStorage;
    private final Map<String, String> sessionStorage;

    public SessionData(
            Map<String, Cookie> cookies,
            Map<String, String> localStorage,
            Map<String, String> sessionStorage) {

        this.cookies = cookies;
        this.localStorage = localStorage;
        this.sessionStorage = sessionStorage;
    }

    public Map<String, Cookie> getCookies() {
        return cookies;
    }

    public Map<String, String> getLocalStorage() {
        return localStorage;
    }

    public Map<String, String> getSessionStorage() {
        return sessionStorage;
    }
}
