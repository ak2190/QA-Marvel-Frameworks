package qamarvel.framework.auth.acquire;

import qamarvel.framework.auth.AuthState;

public interface AuthAcquirer {
    AuthState acquire();
}
