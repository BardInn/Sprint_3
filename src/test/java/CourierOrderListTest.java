import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotEquals;

public class CourierOrderListTest {
	private CourierClient courierClient;
	private Courier courier;
	OrderClient orderClient;
	private int courierId;


	@Before
	public void createCourier() {
		orderClient = new OrderClient();
		courierClient = new CourierClient();
		courier = Courier.getRandom();
		courierClient.create(courier);
		courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
	}

	@After
	public void tearDown() {
		courierClient.delete(courierId);
	}

	@Test
	@DisplayName("Get list of orders")
	@Description("Try to received orders without passing any parameters")
	public void getListOfAllOrders() {
		
		ValidatableResponse getListOfOrders = orderClient.receivingListOfAllOrders();
		ArrayList<String> listOfOrders = getListOfOrders.extract().path("orders");
		int statusCode = getListOfOrders.extract().statusCode();

		assertThat("Order received", statusCode, equalTo(200));
		assertNotEquals(null, listOfOrders);
	}


	@Test
	@DisplayName("Orders available for courier")
	@Description("Try to get list of orders for current courier")
	public void getListOfActiveCouriersOrders() {
		String parameters = "?courierId=" + courierId;
		ValidatableResponse getTenOrders = orderClient.receivingListOfOrders(parameters);
		ArrayList<String> listOfOrders = getTenOrders.extract().path("orders");
		int statusCode = getTenOrders.extract().statusCode();

		assertThat("Order received", statusCode, equalTo(200));
		assertNotEquals(null, listOfOrders);
	}

	@Test
	@DisplayName("Orders available for courier in certain area")
	@Description("Try to get list of orders for courier in certain metro station")
	public void getListOfAvailableCouriersOrderNearTheStation() {
		String parameters = "?" + courierId + "&nearestStation=[\"5\", \"6\"]";
		ValidatableResponse getTenOrders = orderClient.receivingListOfOrders(parameters);
		ArrayList<String> listOfOrders = getTenOrders.extract().path("orders");
		int statusCode = getTenOrders.extract().statusCode();

		assertThat("Order received", statusCode, equalTo(200));
		assertNotEquals(null, listOfOrders);
	}
}
