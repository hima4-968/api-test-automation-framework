package com.apiframework.clients;

import com.apiframework.models.request.AuthRequest;
import com.apiframework.routes.Routes;
import com.apiframework.specs.Specs;

import static io.restassured.RestAssured.given;

public class AuthClient {
  
	 public String createToken(String username,String password) 
	 {
		 AuthRequest request = new AuthRequest();
		 request.setUsername(username);
		 request.setPassword(password);
		 
		  return given()
	                .spec(Specs.booker())
	                .body(request)
	            .when()
	                .post(Routes.BOOKER_AUTH)
	            .then()
	                .statusCode(200)
	                .extract().path("token");
		
	 }
}
