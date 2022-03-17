import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient extends ScooterRestClient {

	private static final String ORDER_PATH = "/api/v1/orders";

	@Step("Create order")
	public ValidatableResponse createOrder(OrderGenerator orderGenerator) {
		return given()
				.spec(getBaseSpec())
				.body(orderGenerator)
				.when()
				.post(ORDER_PATH)
				.then();
	}

	@Step("Cancel order")
	public ValidatableResponse cancelOrder(int track){
		return given()
				.spec(getBaseSpec())
				.body(track)
				.when()
				.put(ORDER_PATH + "/cancel")
				.then();
	}

	@Step("Get list of all orders")
	public ValidatableResponse receivingListOfAllOrders() {
		return given()
				.spec(getBaseSpec())
				.get(ORDER_PATH)
				.then();
	}

	@Step("Get list of 10 order available to courier")
	public ValidatableResponse receivingListOfTenAvailableOrders() {
		return given()
				.spec(getBaseSpec())
				.queryParam("limit", "10")
				.queryParam("page", "0")
				.get(ORDER_PATH)
				.then();
	}

	@Step("Get list of available order for courier near the metrostation")
	public ValidatableResponse listOfAvailableCouriersOrderNearTheStation(int courierId, List<String> nearestStation) {
		return given()
				.spec(getBaseSpec())
				.queryParam("courierId", courierId)
				.queryParam("nearestStation", nearestStation)
				.get(ORDER_PATH)
				.then();
	}

	@Step("Get list of active orders for courier")
	public  ValidatableResponse listOfActiveCouriersOrders(int courierId) {
		return given()
				.spec(getBaseSpec())
				.queryParam("courierId", courierId)
				.get(ORDER_PATH)
				.then();
	}

	@Step("Get list of 10 orders near by the metrostation")
	public ValidatableResponse listOfTenOrdersNearByTheMetroStation(List<String> nearestStation) {
		return given()
				.spec(getBaseSpec())
				.queryParam("limit", "10")
				.queryParam("page", "0")
				.queryParam("nearestStation", nearestStation)
				.get(ORDER_PATH)
				.then();
	}
}
