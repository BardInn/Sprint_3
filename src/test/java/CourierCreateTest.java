
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;



@Story("Creating courier")
public class CourierCreateTest extends FeatureTest {
	private CourierClient courierClient;
	private int courierId;


	@Before
	public void setUp() {
		courierClient = new CourierClient();
	}

	@After
	public void tearDown() {
		courierClient.delete(courierId);
	}

	@Test
	@DisplayName("Create new courier")
	@Description("Create new courier and verify success creations")
	public void courierRegisterTest() {
		Courier courier = Courier.getRandom();
		ValidatableResponse isCreated = courierClient.create(courier);
		ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
		courierId = loginResponse.extract().path("id");
		int statusCode = isCreated.extract().statusCode();

		assertThat("Courier can login", statusCode, equalTo(201));
		assertNotEquals(0, courierId);

	}

	@Test
	@DisplayName("The login is already in use")
	@Description("Try to create two identical courier")
	public void doubleCourierRegisterTest() {
		Courier courier = Courier.getRandom();
		ValidatableResponse createResponse = courierClient.create(courier);
		ValidatableResponse doubleCreate = courierClient.create(courier);
		int statusCodeFirst = createResponse.extract().statusCode();
		int statusCodeDouble = doubleCreate.extract().statusCode();

		assertThat("First courier created", statusCodeFirst, equalTo(201));
		assertThat("Double courier cannot login", statusCodeDouble, equalTo(409));

	}

	@Test
	@DisplayName("Login is required")
	@Description("Try to create new courier without login field")
	public void requestWithoutLoginTest() {
		Courier courier = Courier.builder()
				.password(RandomStringUtils.randomAlphabetic(10))
				.firstName(RandomStringUtils.randomAlphabetic(10))
				.build();
		
		String faultMessage = courierClient.createCourierWithMissingField(courier);
		assertEquals("Недостаточно данных для создания учетной записи", faultMessage);

	}

	@Test
	@DisplayName("Password is required")
	@Description("Try to create new courier without password field")
	public void requestWithoutPasswordTest() {
		Courier courier = Courier.builder()
				.login(RandomStringUtils.randomAlphabetic(10))
				.firstName(RandomStringUtils.randomAlphabetic(10))
				.build();
		
		String faultMessage = courierClient.createCourierWithMissingField(courier);
		assertEquals("Недостаточно данных для создания учетной записи", faultMessage);

	}


	@Test
	@DisplayName("Current message")
	@Description("Create new courier and verify success message")
	public void successRequestTrueTest() {
		Courier courier = Courier.getRandom();
		ValidatableResponse isCreated = courierClient.create(courier);
		String successMessage = isCreated.extract().asString();

		assertEquals("ok:true", successMessage.replaceAll("[{}\"]", ""));
	}

	@Test
	@DisplayName("The login is already in use")
	@Description("Try to create new courier with already registered login and password")
	public void registerNewCourierWithAlreadyUsingLoginPasswordTest() {
		Courier courier = Courier.getRandom();
		ValidatableResponse createResponse = courierClient.create(courier);
		ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
		ValidatableResponse doubleCreate = courierClient.create(courier);
		int statusCodeFirst = createResponse.extract().statusCode();
		courierId = loginResponse.extract().path("id");
		int statusCodeDouble = doubleCreate.extract().statusCode();

		assertThat("First courier created", statusCodeFirst, equalTo(201));
		assertNotEquals(0, courierId);
		assertThat("Double courier cannot login", statusCodeDouble, equalTo(409));
	}

	
}
