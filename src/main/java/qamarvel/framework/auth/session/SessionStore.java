package qamarvel.framework.auth.session;

/**
 * SessionStore is a thread-safe, in-memory repository for storing
 * and retrieving the current authenticated browser session.
 *
 * This class acts as a central access point for the AuthSession
 * captured by SessionManager and makes it available across the
 * entire test framework lifecycle.
 *
 * Design Characteristics:
 * - Static storage (global access)
 * - Thread-safe access using synchronized methods
 * - Prevents usage before initialization
 *
 * Typical Usage:
 * - SessionManager.save(...) stores the session
 * - SessionManager.get(...) retrieves the session
 */

public class SessionStore {
	
	/**
     * Holds the currently active authenticated session.
     * Stored statically to allow global framework access.
     */

    private static AuthSession authSession;

    /**
     * Private constructor to prevent instantiation.
     * This class is intended to be used as a static utility.
     */
    private SessionStore() {}

    /**
     * Saves an authenticated session into the store.
     *
     * This method overwrites any existing session.
     * It is synchronized to ensure thread-safe writes
     * in parallel execution environments.
     *
     * @param session AuthSession object containing authentication state
     */
    public static synchronized void save(AuthSession session) {
        authSession = session;
    }

    /**
     * Retrieves the currently stored authenticated session.
     *
     * This method throws an exception if no session
     * has been initialized to prevent silent failures.
     *
     * @return AuthSession containing stored authentication data
     * @throws IllegalStateException if session has not been saved yet
     */
    public static synchronized AuthSession get() {
        if (authSession == null) {
            throw new IllegalStateException("Auth session not initialized");
        }
        return authSession;
    }

    /**
     * Checks whether an authentication session is available.
     *
     * Useful for conditional session injection logic,
     * such as deciding whether to perform a fresh login.
     *
     * @return true if session exists, false otherwise
     */
    public static synchronized boolean isInitialized() {
        return authSession != null;
    }
}
