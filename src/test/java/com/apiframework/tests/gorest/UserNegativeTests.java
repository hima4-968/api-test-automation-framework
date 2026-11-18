package com.apiframework.tests.gorest;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.apiframework.clients.UserClient;
import com.apiframework.data.DataFactory;
import com.apiframework.models.request.CreateUserRequest;

import io.restassured.response.Response;

public class UserNegativeTests {

	private CreateUserRequest sentUser;
	private final UserClient userClient = new UserClient();
	private static final String FAKE_TOKEN = "fake-token-123";

	@Test(description = "Creating a user without a token is rejected with 401")
	void createUserWithoutTokenShouldReturn401() {
		sentUser = DataFactory.randomUser();
		Response response = userClient.createUserWithoutToken(sentUser);
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.jsonPath().getString("message"), "Authentication failed");
	}

	@Test(description = "Creating a user without invalid token rejected with 401")
	void createUserWithFakeTokenShouldReturn401() {
		sentUser = DataFactory.randomUser();
		Response response = userClient.createUserWithToken(sentUser, FAKE_TOKEN);
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.jsonPath().getString("message"), "Invalid token");
	}

	@DataProvider(name = "invalidUsers")
	public Object[][] invalidUsers() {
		return new Object[][] {
				// name email gender status badField
				{ "", "hima" + System.nanoTime() + "@mail.com", "male", "active", "name" },
				{ "Hima", "not-an-email", "male", "active", "email" },
				{ "Hima", "hima" + System.nanoTime() + "@mail.com", "", "active", "gender" },
				{ "Hima", "hima" + System.nanoTime() + "@mail.com", "male", "", "status" }, };
	}

	@Test(dataProvider = "invalidUsers", description = "Creating a user with invalid data is rejected with 422 naming the bad field")
	void createUserWithInvalidDataShouldReturn422(String name, String email, String gender, String status,
			String badField) {

		CreateUserRequest user = new CreateUserRequest();
		user.setEmail(email);
		user.setGender(gender);
		user.setName(name);
		user.setStatus(status);
		Response response = userClient.createUserResponse(user);
		Assert.assertEquals(response.getStatusCode(), 422);
		Assert.assertTrue(response.jsonPath().getList("field").contains(badField),
				"Expected the error to name the field: " + badField);
	}
}
