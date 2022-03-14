import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotEquals;


public class CreatingAnOrderColorTest {
    OrderClient orderClient;
    private int track;

    @Before
    public void setUp() {orderClient = new OrderClient();}

    @After
    public void tearDown() {orderClient.cancelOrder(track);}

	@Test
	@DisplayName("Order with black scooter")
	@Description("Try to create new order with black color of scooter")
	public void customerCanChooseBlackColor() {
		 OrderGenerator orderGenerator = new OrderGenerator();
		 orderGenerator.getColor().add("BLACK");
		 ValidatableResponse orderResponse = orderClient.createOrder(orderGenerator);
		 int statusCode = orderResponse.extract().statusCode();
		 track = orderResponse.extract().path("track");

		 assertThat("Order created", statusCode, equalTo(201));
		 assertNotEquals(0, track);
	}

	@Test
	@DisplayName("Order with gray scooter")
	@Description("Try to create new order with gray color of scooter")
	public void customerCanChooseGrayColor() {
		OrderGenerator orderGenerator = new OrderGenerator();
		orderGenerator.getColor().add("GRAY");
		ValidatableResponse orderResponse = orderClient.createOrder(orderGenerator);
		int statusCode = orderResponse.extract().statusCode();
		track = orderResponse.extract().path("track");

		assertThat("Order created", statusCode, equalTo(201));
		assertNotEquals(0, track);
	}

	@Test
	@DisplayName("Order any scooter")
	@Description("Try to create new order without specifying the color of scooter")
	public void customerCanNotToChooseColor() {
		OrderGenerator orderGenerator = new OrderGenerator();
		ValidatableResponse orderResponse = orderClient.createOrder(orderGenerator);
		int statusCode = orderResponse.extract().statusCode();
		track = orderResponse.extract().path("track");

		assertThat("Order created", statusCode, equalTo(201));
		assertNotEquals(0, track);
	}

	@Test
	@DisplayName("Order both: black and gray scooter")
	@Description("Try to create new order with black and gray color of scooter")
	public void customerCanChooseBothColor() {
		OrderGenerator orderGenerator = new OrderGenerator();
		orderGenerator.getColor().add("BLACK");
		orderGenerator.getColor().add("GRAY");
		ValidatableResponse orderResponse = orderClient.createOrder(orderGenerator);
		int statusCode = orderResponse.extract().statusCode();
		track = orderResponse.extract().path("track");

		assertThat("Order created", statusCode, equalTo(201));
		assertNotEquals(0, track);
	}
	
}
