import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class CourierLoginTest {
private CourierClient courierClient;
private Courier courier;
private int courierId;

	@Before
		public void setUp() {
		courierClient = new CourierClient();
		courier = Courier.getRandom();
		courierClient.create(courier);
	}

	@After
	public void tearDown() {
		courierClient.delete(courierId);
	}               

	@Test
	@DisplayName("Login courier")
	@Description("Login courier with valid data")
	public void courierCanLoginWithValidCredentialsTest() {
		ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
		int statusCode = loginResponse.extract().statusCode();
		courierId = loginResponse.extract().path("id");

		assertThat("Courier can login", statusCode, equalTo(SC_OK));
		assertThat("Courier ID is incorrect", courierId, is(not(0))) ;
	}

	@Test
	@DisplayName("Fail login courier")
	@Description("Try to login courier without login")
	public void courierLoginWithMissingLoginTest() {
		CourierCredentials courierCredentials = CourierCredentials.builder()
				.password(courier.getLogin())
				.build();
		ValidatableResponse loginResponse = courierClient.login(courierCredentials);
		int statusCode = loginResponse.extract().statusCode();
		assertThat("Courier cannot login", statusCode, is(not(200)));
	}


	@Test
	@DisplayName("Fail login courier")
	@Description("Try to login courier without password")
	public void courierLoginWithMissingPasswordTest() {
		CourierCredentials courierCredentials = CourierCredentials.builder()
				.login(courier.getLogin())
				.build();
		ValidatableResponse loginResponse = courierClient.login(courierCredentials);
		int statusCode = loginResponse.extract().statusCode();

		assertThat("Courier cannot login", statusCode, equalTo(400));

	}

	@Test
	@DisplayName("Fail with wrong login")
	@Description("Try to login with non-registered login")
	public void courierLoginWithWrongLoginTest() {
		ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("1234", courier.getPassword()));
		int statusCode = loginResponse.extract().statusCode();

		assertThat("Courier not found", statusCode, equalTo(404));
	}

	@Test
	@DisplayName("Fail with wrong password")
	@Description("Try to login with wrong password")
	public void courierLoginWithWrongPasswordTest() {
		ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), "123344"));
		int statusCode = loginResponse.extract().statusCode();

		assertThat("Courier not found", statusCode, equalTo(404));
	}

	@Test
	@DisplayName("Authorization by an unregistered user")
	@Description("Try to login by an unregistered user")
	public void notRegisteredCourierTest() {
		courier = Courier.getRandom();
		ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
		int statusCode = loginResponse.extract().statusCode();

		assertThat("Courier not found", statusCode, equalTo(404));
		
	}

}
