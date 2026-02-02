Auth Layer Utility – Usage Guide

This framework provides a pluggable, multi-project authentication layer that supports different application authentication mechanisms (UI login, API login, localStorage JWT, session reuse, etc.) without changing test code.

The goal is:

Authenticate once, reuse everywhere, adapt per application.

1️⃣ Core Design Philosophy

Authentication is treated as a pipeline, not a single implementation.

ACQUIRE  →  APPLY  →  VERIFY

Acquire: How we obtain authentication state
Apply: How we inject it into the browser
Verify: How we confirm login success

Each application chooses the right combination.

2️⃣ Key Concepts & Components
🔹 AuthState

A lightweight object that carries authentication data.

public class AuthState {
    private String token;
}


It may later include:

cookies
localStorage values
headers
session data

🔹 AuthAcquirer

Responsible for getting authentication data.

public interface AuthAcquirer {
    AuthState acquire();
}

Implementations
Class	Purpose
ApiAuthAcquirer	Calls login API and extracts token
UiAuthAcquirer	Uses UI login (future)
CookieFileAuthAcquirer	Loads pre-generated cookies (future)
🔹 AuthApplier

Responsible for injecting auth into the browser.

public interface AuthApplier {
    void apply(WebDriver driver, AuthState state);
}

Implementations
Class	When to use
LocalStorageAuthApplier	Apps storing JWT in localStorage
HeaderAuthApplier	Stateless header-based APIs
SessionAuthApplier	Cookie/session reuse
CookieAuthApplier	Legacy cookie-based apps
🔹 AuthVerifier

Responsible for confirming authentication success.

public interface AuthVerifier {
    void verify(WebDriver driver);
}


Examples:

Dashboard element visible

URL is not /login

Authenticated API returns 200

3️⃣ Supported Auth Modes

Authentication behavior is controlled using configuration, not code changes.

config.properties
auth.mode=SESSION

Supported values
auth.mode	Description
SESSION	UI login + session reuse
API_LOCALSTORAGE	API login + localStorage injection
UI	Full UI login (fallback)
4️⃣ How BaseTest Uses the Auth Layer

All tests extend BaseTest.

public class LoginTest extends BaseTest {
    @Test
    public void validLoginTest() {
        // test logic only
    }
}

Authentication happens automatically in BaseTest
@BeforeTest
public void setUp() {

    DriverFactory.initDriver();
    driver = DriverFactory.getDriver();

    if (auth.mode == SESSION) {
        SessionManager.injectSession(...)
    }

    if (auth.mode == API_LOCALSTORAGE) {
        acquire → inject → navigate → verify
    }
}


👉 Test classes never handle login logic.

5️⃣ Example: API Login + LocalStorage JWT (SPA Apps)

Used for Angular / React apps where:

Login API returns JWT

Token stored in localStorage

App reads token on bootstrap

Flow
API Login
→ Extract token
→ Open app domain (origin only)
→ Inject localStorage token
→ Load SPA
→ Verify dashboard

Why this works

Respects browser security (origin-scoped storage)

Matches real UI login behavior

Stable across browser restarts

6️⃣ Example: UI Login + Session Reuse (Legacy / SSO Apps)

Used when:

App stores session in cookies

API login cannot initialize UI state

MFA / SSO involved

Flow
Suite startup:
→ UI login
→ Capture cookies & storage

Test startup:
→ New browser
→ Inject captured session
→ Navigate directly to app

7️⃣ How to Discover Auth Type for a New App

Always perform Auth Recon before choosing a mode.

Checklist

Login manually

Open DevTools → Application

Inspect:

Cookies

LocalStorage

SessionStorage

Check Network → Request Headers

Decision Matrix
Observation	Use This
Token in localStorage	LocalStorageAuthApplier
Session cookies	SessionAuthApplier
Header-only JWT	HeaderAuthApplier
No persistence	UI login required
8️⃣ Common Pitfalls (and how this framework avoids them)

❌ Hardcoding login in tests
❌ Assuming all JWTs work via headers
❌ Mixing auth logic with test logic
❌ Using sleeps to “fix” auth timing

✅ Centralized auth handling
✅ Config-driven behavior
✅ Explicit verification
✅ Clear failure when auth breaks

9️⃣ Adding Support for a New App

To support a new authentication mechanism:

Identify where auth is stored

Implement:

AuthAcquirer (if needed)

AuthApplier

AuthVerifier

Add config mapping

No test changes required

🔚 Summary

This Auth Layer enables:

Multi-project reuse

Clean test code

Fast execution

CI stability

Enterprise-grade flexibility

Auth is no longer a test concern.
It is framework infrastructure.