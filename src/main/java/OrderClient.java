import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;
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

	@Step("Get list of order of courier")
	public ValidatableResponse receivingListOfOrders(String parameters) {
		return given()
				.spec(getBaseSpec())
				.get(ORDER_PATH + parameters)
				.then();
	}
}
