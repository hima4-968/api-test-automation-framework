package com.apiframework.clients;

import static io.restassured.RestAssured.given;

import com.apiframework.models.request.Booking;
import com.apiframework.models.response.CreateBookingResponse;
import com.apiframework.routes.Routes;
import com.apiframework.specs.Specs;

import io.restassured.response.Response;

public class BookingClient {

	public CreateBookingResponse createBooking(Booking booking) {
		return given().spec(Specs.booker()).body(booking).when().post(Routes.BOOKINGS).then().statusCode(200).extract()
				.as(CreateBookingResponse.class);
	}

	public Booking getBooking(int bookingId) {
		return given().spec(Specs.booker()).pathParam("id", bookingId).when().get(Routes.BOOKING_BY_ID).then()
				.statusCode(200).extract().as(Booking.class);

	}
	
	public Booking updateBooking(int bookingId, Booking booking, String token) {
		return given().spec(Specs.booker()).pathParam("id", bookingId).cookie("token", token).body(booking).when().put(Routes.BOOKING_BY_ID).then()
				.statusCode(200).extract().as(Booking.class);
	}
		
		public Response deleteBooking(int bookingId,String token) {
			return given().spec(Specs.booker()).pathParam("id", bookingId).cookie("token", token).when().delete(Routes.BOOKING_BY_ID);

	}
		public Response getBookingResponse(int bookingId)
		{
			return given().spec(Specs.booker()).pathParam("id", bookingId).when().get(Routes.BOOKING_BY_ID);
		}
}
