import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Data
@AllArgsConstructor
public class OrderGenerator implements Serializable {

	@JsonIgnore
	private Faker faker = new Faker(new Locale("ru", "RU"));
	@JsonIgnore
	private Random rand = new Random();


	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ");
	private String firstName = faker.name().firstName();
	private String lastName = faker.name().lastName();
	private String address = faker.address().streetAddress();
	private String metroStation = RandomStringUtils.randomNumeric(0, 256);
	private String phone = faker.phoneNumber().phoneNumber();
	private int rentTime = rand.nextInt(7);
	private String deliveryDate = dateFormat.format(faker.date().future(50, TimeUnit.DAYS));
	private String comment = RandomStringUtils.randomAlphabetic(10);
	private List<String> color = new ArrayList<>(1);


	public OrderGenerator() {
	}



}
