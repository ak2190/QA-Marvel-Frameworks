package qamarvel.framework.auth.session;

import org.openqa.selenium.Cookie;
import java.util.Map;
import java.util.Set;

public class AuthSession {

	
    private Set<Cookie> cookies;
    private Map<String, String> localStorage;
    private Map<String, String> sessionStorage;

    public Set<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(Set<Cookie> cookies) {
        this.cookies = cookies;
    }

    public Map<String, String> getLocalStorage() {
        return localStorage;
    }

    public void setLocalStorage(Map<String, String> localStorage) {
        this.localStorage = localStorage;
    }

    public Map<String, String> getSessionStorage() {
        return sessionStorage;
    }

    public void setSessionStorage(Map<String, String> sessionStorage) {
        this.sessionStorage = sessionStorage;
    }
}

