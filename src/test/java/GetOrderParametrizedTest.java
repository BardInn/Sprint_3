import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class GetOrderParametrizedTest {
	OrderClient orderClient = new OrderClient();
	private final String parameters;
	private int statusCode;

	public GetOrderParametrizedTest (String parameters) {
		this.parameters = parameters;
	}

	@Parameterized.Parameters
	public static Object[][] getURLParameters() {
		return new Object[][]{
				{"?limit=10&page=0"},
				{"?limit=10&page=0&nearestStation=[\"110\"]"},
		};
	}

	@Test
	@DisplayName("Get list of orders")
	@Description("Try to get list of orders with limiting parameters")
	public void getListOfAvailableOrders() {
		ValidatableResponse getTenOrders = orderClient.receivingListOfOrders(parameters);
		ArrayList<String> listOfOrders = getTenOrders.extract().path("orders");
		int statusCode = getTenOrders.extract().statusCode();
		assertThat("Order received", statusCode, equalTo(200));
		assertNotEquals(null, listOfOrders);

	}
}
