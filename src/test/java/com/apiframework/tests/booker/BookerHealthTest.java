package com.apiframework.tests.booker;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import com.apiframework.routes.Routes;
import com.apiframework.specs.Specs;

public class BookerHealthTest {

	 @Test(groups = "smoke", description = "Restful Booker is up and responding")
	void pingShouldReturn201()
	{
		given()
		.spec(Specs.booker())
		.when()
		.get(Routes.BOOKER_PING)
		.then()
		.statusCode(201);
	}
}
