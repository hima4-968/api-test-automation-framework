package com.apiframework.tests.gorest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiframework.clients.UserClient;
import com.apiframework.data.DataFactory;
import com.apiframework.models.request.CreateUserRequest;
import com.apiframework.models.response.UserResponse;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

public class UserCrudTests {

	private long userId;
	private final UserClient userClient = new UserClient();
	private CreateUserRequest sentUser;

	@Test(groups = "smoke", description = "A new user is created and returned with an id")
	void createUserShouldReturnIdAndSameData() {
		sentUser = DataFactory.randomUser();
		UserResponse created = userClient.createUser(sentUser);
		userId = created.getId();
		Assert.assertTrue(userId > 0, "UserId id should be positive");
		Assert.assertEquals(created.getEmail(), sentUser.getEmail());
		Assert.assertEquals(created.getName(), sentUser.getName());
		Assert.assertEquals(created.getGender(), sentUser.getGender());
		Assert.assertEquals(created.getStatus(), sentUser.getStatus());
	}

	@Test(dependsOnMethods = "createUserShouldReturnIdAndSameData", description = "reading user is created and returned with an id")
	void getUserShouldReturnIdAndcreatedData() {
		UserResponse fetched = userClient.getUser(userId);
		Assert.assertEquals(fetched.getId().longValue(), userId);
		Assert.assertEquals(fetched.getEmail(), sentUser.getEmail());
		Assert.assertEquals(fetched.getName(), sentUser.getName());
		Assert.assertEquals(fetched.getGender(), sentUser.getGender());
		Assert.assertEquals(fetched.getStatus(), sentUser.getStatus());
	}
	
	@Test(dependsOnMethods="getUserShouldReturnIdAndcreatedData", description="The user response matches the user schema")
	void getUserShouldMatchSchema()
	{
		Response response = userClient.getUserResponse(userId);
		response.then()
		        .statusCode(200)
		        .body(matchesJsonSchemaInClasspath("schemas/user.json"));
	}

	@Test(dependsOnMethods = "getUserShouldReturnIdAndcreatedData", description = "Deleting user returns 204")
	void deleteUserShouldReturn204() {
		Response response = userClient.deleteUser(userId);
		Assert.assertEquals(response.getStatusCode(), 204);
	}

	@Test(dependsOnMethods = "deleteUserShouldReturn204", description = "Deleted user returns 404")
	void getDeletedUserShouldReturn404() {
		Response response = userClient.getUserResponse(userId);
		Assert.assertEquals(response.getStatusCode(), 404);
		
	}
	
	

}
