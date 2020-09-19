package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.pokemon.dto.ShakespeareDTO;
import com.company.pokespeare.testconfig.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ResponseSelectorShakespeareTest extends BaseTest {
	@Inject
	private ResponseSelector responseSelector;

	private static ObjectMapper mapper = new ObjectMapper();

	private final static String shakespeareResponse1_path = "/fixture/shakespeare/shakespeare_response_1.json";
	private final static String shakespeareResponse2_path = "/fixture/shakespeare/shakespeare_response_no_contents.json";
	private final static String shakespeareResponse3_path = "/fixture/shakespeare/shakespeare_response_no_translated.json";
	private final static String shakespeareResponse4_path = "/fixture/shakespeare/shakespeare_response_empty_translated.json";

	private static String shakespeareResponse_1;    // Valid JSON
	private static String shakespeareResponse_2;    // No field "Contents"
	private static String shakespeareResponse_3;    // No field "Contents.Translated"
	private static String shakespeareResponse_4;    // Field "Contents.Translated" is empty string

	private static ShakespeareDTO shakespeareDTO_1;
	private static ShakespeareDTO shakespeareDTO_2;
	private static ShakespeareDTO shakespeareDTO_3;
	private static ShakespeareDTO shakespeareDTO_4;

	@BeforeAll
	static void setuo() throws Exception {
		shakespeareResponse_1 = IOUtils.toString(ResponseSelectorShakespeareTest.class.getResourceAsStream(shakespeareResponse1_path), UTF_8);
		shakespeareResponse_2 = IOUtils.toString(ResponseSelectorShakespeareTest.class.getResourceAsStream(shakespeareResponse2_path), UTF_8);
		shakespeareResponse_3 = IOUtils.toString(ResponseSelectorShakespeareTest.class.getResourceAsStream(shakespeareResponse3_path), UTF_8);
		shakespeareResponse_4 = IOUtils.toString(ResponseSelectorShakespeareTest.class.getResourceAsStream(shakespeareResponse4_path), UTF_8);

		shakespeareDTO_1 = mapper.readValue(shakespeareResponse_1, ShakespeareDTO.class);
		shakespeareDTO_2 = mapper.readValue(shakespeareResponse_2, ShakespeareDTO.class);
		shakespeareDTO_3 = mapper.readValue(shakespeareResponse_3, ShakespeareDTO.class);
		shakespeareDTO_4 = mapper.readValue(shakespeareResponse_4, ShakespeareDTO.class);
	}

	@Test
	void selectShakespeareTranslation_Input_Test() {

		// Test_1: Null Input
		RuntimeException exp_1 = Assertions.assertThrows(RuntimeException.class, () -> responseSelector.selectShakespeareTranslation(null));
		Assertions.assertEquals("Cannot select a shakespeare translation from ShakespeareDTO", exp_1.getMessage());
		Assertions.assertEquals("ShakespeareDTO is null", exp_1.getCause().getMessage());

		// Test_2: Contents not present in JSON
		RuntimeException exp_2 = Assertions.assertThrows(RuntimeException.class, () -> responseSelector.selectShakespeareTranslation(shakespeareDTO_2));
		Assertions.assertEquals("Cannot select a shakespeare translation from ShakespeareDTO", exp_2.getMessage());
		Assertions.assertEquals("ShakespeareDTO is null", exp_2.getCause().getMessage());

		// Test_3: Flavor_Text_Entries not present in JSON
		RuntimeException exp_3 = Assertions.assertThrows(RuntimeException.class, () -> responseSelector.selectShakespeareTranslation(shakespeareDTO_3));
		Assertions.assertEquals("Cannot select a shakespeare translation from ShakespeareDTO", exp_3.getMessage());
		Assertions.assertEquals("ShakespeareDTO is null", exp_3.getCause().getMessage());

		// Test_3: Flavor_Text_Entries not present in JSON
		RuntimeException exp_4 = Assertions.assertThrows(RuntimeException.class, () -> responseSelector.selectShakespeareTranslation(shakespeareDTO_4));
		Assertions.assertEquals("Cannot select a shakespeare translation from ShakespeareDTO", exp_4.getMessage());
		Assertions.assertEquals("Empty translation received", exp_4.getCause().getMessage());
	}


	@Test
	void selectShakespeareTranslation_Test() {
		String translation = responseSelector.selectShakespeareTranslation(shakespeareDTO_1);
		Assertions.assertNotNull(translation);
		Assertions.assertEquals("Thee did giveth mr. Tim a hearty meal,  but unfortunately what he did doth englut did maketh him kicketh the bucket.",
				translation);
	}

}
