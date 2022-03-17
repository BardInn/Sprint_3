import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient{
	private static final String COURIER_PATH = "/api/v1/courier/";

	@Step("Login courier")
	public ValidatableResponse login(CourierCredentials credentials) {
		return given()
				.spec(getBaseSpec())
				.body(credentials)
				.when()
				.post(COURIER_PATH + "login")
				.then();
	}


	@Step("Delete courier {courierId}")
	public ValidatableResponse delete(int courierId) {
		 return given()
				 .spec(getBaseSpec())
				 .body(courierId)
				 .when()
				 .delete(COURIER_PATH + ":id")
				 .then();
	}

	@Step("Create courier without login")
	public String createCourierWithMissingField  (Courier courier) {
		return given()
				.spec(getBaseSpec())
				.body(courier)
				.when()
				.post(COURIER_PATH)
				.then().log().all()
				.assertThat()
				.statusCode(400)
				.extract()
				.path("message");
	}

	@Step("Create new courier")
	public ValidatableResponse create  (Courier courier) {
		return given()
				.spec(getBaseSpec())
				.body(courier)
				.when()
				.post(COURIER_PATH)
				.then();
	}

}
