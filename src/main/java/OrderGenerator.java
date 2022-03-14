import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderGenerator {

	private String firstName = "Сергей";
	private String lastName = "Смирнов";
	private String address = "Москва, Кремль 1";
	private String metroStation = "77";
	private String phone = "88888888888";
	private int rentTime = 3;
	private String deliveryDate = "2022.05.01";
	private String comment = RandomStringUtils.randomAlphabetic(10);
	private List<String> color = new ArrayList<>(1);


	public OrderGenerator() {
	}

}
