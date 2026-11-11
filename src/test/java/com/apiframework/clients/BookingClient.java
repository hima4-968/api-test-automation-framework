package com.apiframework.clients;

import static io.restassured.RestAssured.given;

import com.apiframework.models.request.Booking;
import com.apiframework.models.response.CreateBookingResponse;
import com.apiframework.routes.Routes;
import com.apiframework.specs.Specs;

public class BookingClient {

	public CreateBookingResponse createBooking(Booking booking) {
		return given().spec(Specs.booker()).body(booking).when().post(Routes.BOOKINGS).then().statusCode(200).extract()
				.as(CreateBookingResponse.class);
	}

	public Booking getBooking(int bookingId) {
		return given().spec(Specs.booker()).pathParam("id", bookingId).when().get(Routes.BOOKING_BY_ID).then()
				.statusCode(200).extract().as(Booking.class);

	}
}
