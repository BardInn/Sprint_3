import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class CreatingAnOrderColorTest {
    OrderClient orderClient;
    private int track;
	private final String color;

	public CreatingAnOrderColorTest(String color){
		this.color = color;
	}

	@Parameterized.Parameters
	public static Object[] setColor() {
		return new Object[]{               //наборы тестовых данных
				"BLACK",
				"GRAY"
		};
	}

    @Before
    public void setUp() {orderClient = new OrderClient();}

    @After
    public void tearDown() {orderClient.cancelOrder(track);}

	@Test
	@DisplayName("Order scooter")
	@Description("Try to create new order with black or gray color of scooter")
	public void customerCanChooseColor() {
		 OrderGenerator orderGenerator = new OrderGenerator();
		 orderGenerator.getColor().add(color);
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
		orderGenerator.getColor().add(color);
		orderGenerator.getColor().add(color);
		ValidatableResponse orderResponse = orderClient.createOrder(orderGenerator);
		int statusCode = orderResponse.extract().statusCode();
		System.out.println(statusCode);
		System.out.println(orderResponse.extract().asString());
		track = orderResponse.extract().path("track");

		assertThat("Order created", statusCode, equalTo(201));
		assertNotEquals(0, track);
	}
	
}
