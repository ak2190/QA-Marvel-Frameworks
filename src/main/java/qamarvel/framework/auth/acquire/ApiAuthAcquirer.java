package qamarvel.framework.auth.acquire;


import qamarvel.framework.auth.AuthApiClient;
import qamarvel.framework.auth.AuthState;
import qamarvel.framework.config.ConfigReader;

public class ApiAuthAcquirer implements AuthAcquirer {

    @Override
    public AuthState acquire() {

        AuthApiClient api = new AuthApiClient();
        String token = api.getToken(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );

        AuthState state = new AuthState();
        state.setToken(token);

        return state;
    }
}
