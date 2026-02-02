package qamarvel.framework.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;

public class AuthApiClient {

	public Response login(String email, String password) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		Map<String, String> payload = new HashMap<>();
		payload.put("userEmail", email);
		payload.put("userPassword", password);

		return given().contentType("application/json").body(payload).when().post("/api/ecom/auth/login");
	}

	//NEW (small, safe addition)
	public String getToken(String email, String password) {
		Response response = login(email, password);
		return response.jsonPath().getString("token");
	}
}