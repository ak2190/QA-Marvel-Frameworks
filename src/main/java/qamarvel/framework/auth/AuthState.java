package qamarvel.framework.auth;

import java.util.HashMap;
import java.util.Map;

public class AuthState {

    private String token;
    private Map<String, String> headers = new HashMap<>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        headers.put("Authorization", token);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
