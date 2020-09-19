package com.company.pokespeare.http.logic;

import com.company.pokespeare.http.exception.InvalidRequestException;
import com.company.pokespeare.http.model.GetRequest;
import com.company.pokespeare.http.model.PostRequest;
import com.company.pokespeare.testconfig.BaseTest;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

class RequestConverterTest extends BaseTest {

	@Inject
	private RequestConverter requestConverter;

	@Test
	void convertGetRequest_Input_Test() {

		// Test_1: Null Input
		InvalidRequestException exp_1 = Assertions
				.assertThrows(InvalidRequestException.class, () -> requestConverter.convertGetRequest(null));
		Assertions.assertEquals("Exception while converting GET request", exp_1.getMessage());
		Assertions.assertTrue(exp_1.getCause() instanceof NullPointerException);

		// Test_2: Null URL
		InvalidRequestException exp_2 = Assertions
				.assertThrows(InvalidRequestException.class, () -> requestConverter.convertGetRequest(new GetRequest(null)));
		Assertions.assertEquals("Exception while converting GET request", exp_2.getMessage());
		Assertions.assertTrue(exp_2.getCause() instanceof NullPointerException);

	}

	@Test
	void convertGetRequest_Test() throws InvalidRequestException {

		GetRequest getRequest = new GetRequest("example.testing.com/qwerty");
		HttpGet convertedGet = requestConverter.convertGetRequest(getRequest);

		Assertions.assertNotNull(convertedGet);

		List<Header> headers = new ArrayList<>(Arrays.asList(convertedGet.getAllHeaders()));

		Assertions.assertNotNull(headers);
		Assertions.assertEquals(3, headers.size());
		makeAssertionOnHeader("Accept", "application/json", headers.get(0));
		makeAssertionOnHeader("Accept-Language", "en-gb", headers.get(1));
		makeAssertionOnHeader("Content-Type", "application/json; charset=utf-8", headers.get(2));

	}

	@Test
	void convertPostRequest_Input_Test() {

		// Test_1: Null Input
		InvalidRequestException exp_1 = Assertions
				.assertThrows(InvalidRequestException.class, () -> requestConverter.convertPostRequest(null));
		Assertions.assertEquals("Exception while converting POST request", exp_1.getMessage());
		Assertions.assertTrue(exp_1.getCause() instanceof NullPointerException);

		// Test_2: Null URL and PARAMS
		InvalidRequestException exp_2 = Assertions
				.assertThrows(InvalidRequestException.class, () -> requestConverter.convertPostRequest(new PostRequest(null, null)));
		Assertions.assertEquals("Exception while converting POST request", exp_2.getMessage());
		Assertions.assertTrue(exp_2.getCause() instanceof NullPointerException);

		// Test_3: Null URL, valid PARAMS
		InvalidRequestException exp_3 = Assertions.assertThrows(InvalidRequestException.class,
				() -> requestConverter.convertPostRequest(new PostRequest(null, new TreeMap())));
		Assertions.assertEquals("Exception while converting POST request", exp_3.getMessage());
		Assertions.assertTrue(exp_3.getCause() instanceof NullPointerException);

		// Test_3: Null URL, valid PARAMS
		InvalidRequestException exp_4 = Assertions.assertThrows(InvalidRequestException.class,
				() -> requestConverter.convertPostRequest(new PostRequest("example.test.com/qwerty", null)));
		Assertions.assertEquals("Exception while converting POST request", exp_4.getMessage());
		Assertions.assertTrue(exp_4.getCause() instanceof NullPointerException);

	}

	@Test
	void convertPostRequest_Test() throws InvalidRequestException, IOException {

		TreeMap params = new TreeMap<String, String>() {{
			put("param_name_1", "param_value_1");
			put("param_name_2", "param_value_2");
			put("param_name_3", "param_value_3");
		}};
		PostRequest postRequest = new PostRequest("example.testing.com/qwerty", params);
		HttpPost convertedPost = requestConverter.convertPostRequest(postRequest);

		Assertions.assertNotNull(convertedPost);

		List<Header> headers = new ArrayList<>(Arrays.asList(convertedPost.getAllHeaders()));

		Assertions.assertNotNull(headers);
		Assertions.assertEquals(2, headers.size());
		makeAssertionOnHeader("Accept", "application/json", headers.get(0));
		makeAssertionOnHeader("Accept-Language", "en-gb", headers.get(1));

		String paramsString = IOUtils.toString(convertedPost.getEntity().getContent(), "UTF-8");
		Assertions.assertNotNull(paramsString);
		Assertions.assertEquals("param_name_1=param_value_1&param_name_2=param_value_2&param_name_3=param_value_3", paramsString);
	}

	private void makeAssertionOnHeader(String expectedName, String expectedValue, Header actualHeader) {
		Assertions.assertEquals(expectedName, actualHeader.getName());
		Assertions.assertEquals(expectedValue, actualHeader.getValue());
	}

}
