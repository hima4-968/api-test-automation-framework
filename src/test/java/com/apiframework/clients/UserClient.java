package com.apiframework.clients;

import static io.restassured.RestAssured.given;

import com.apiframework.models.request.CreateUserRequest;
import com.apiframework.models.response.UserResponse;
import com.apiframework.routes.Routes;
import com.apiframework.specs.Specs;

import io.restassured.response.Response;

public class UserClient {

	public UserResponse createUser(CreateUserRequest user) {
		return given().spec(Specs.goRest()).body(user).when().post(Routes.USERS).then().statusCode(201).extract()
				.as(UserResponse.class);
	}

	public UserResponse getUser(long userId) {
		return given().spec(Specs.goRest()).pathParam("id", userId).when().get(Routes.USER_BY_ID).then().statusCode(200)
				.extract().as(UserResponse.class);
	}

	public Response deleteUser(long userId) {
		return given().spec(Specs.goRest()).pathParam("id", userId).when().delete(Routes.USER_BY_ID);
	}

	public Response getUserResponse(long userId) {
		return given().spec(Specs.goRest()).pathParam("id", userId).when().get(Routes.USER_BY_ID);
	}
	
	public Response createUserWithoutToken(CreateUserRequest user) {
		return given().spec(Specs.goRestNoAuth()).body(user).when().post(Routes.USERS);
	}
	
	public Response createUserWithToken(CreateUserRequest user, String token) {
	    return given().spec(Specs.goRestNoAuth()).auth().oauth2(token).body(user).when().post(Routes.USERS);
	}
	
	public Response createUserResponse(CreateUserRequest user) {
	    return given().spec(Specs.goRest()).body(user).when().post(Routes.USERS);
	}
	

}
