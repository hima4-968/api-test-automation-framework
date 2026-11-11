package com.apiframework.tests.booker;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiframework.clients.AuthClient;
import com.apiframework.clients.BookingClient;
import com.apiframework.config.ConfigManager;
import com.apiframework.data.DataFactory;
import com.apiframework.models.request.Booking;
import com.apiframework.models.response.CreateBookingResponse;

import io.restassured.response.Response;

public class BookingCrudTests {

	private Booking sentBooking;
	private int bookingId;
	private String token;
	private final BookingClient bookingClient = new BookingClient();
	private final AuthClient authclient = new AuthClient();

	@Test(groups = "smoke", description = "A new booking is created and returned with an id")
	void createBookingShouldReturnIdAndSameData() {
		sentBooking = DataFactory.randomBooking();
		CreateBookingResponse created = bookingClient.createBooking(sentBooking);
		bookingId = created.getBookingid();
		Assert.assertTrue(bookingId > 0, "Booking id should be positive");
		Assert.assertEquals(created.getBooking().getFirstname(), sentBooking.getFirstname());
		Assert.assertEquals(created.getBooking().getTotalprice(), sentBooking.getTotalprice());
		Assert.assertEquals(created.getBooking().getBookingdates().getCheckin(),
				sentBooking.getBookingdates().getCheckin());
	}

	@Test(dependsOnMethods = "createBookingShouldReturnIdAndSameData", description = "Reading the booking returns exactly what was created")
	void getBookingShouldMatchCreatedBooking() {
		Booking fetched = bookingClient.getBooking(bookingId);
		Assert.assertEquals(fetched.getFirstname(), sentBooking.getFirstname());
		Assert.assertEquals(fetched.getLastname(), sentBooking.getLastname());
		Assert.assertEquals(fetched.getTotalprice(), sentBooking.getTotalprice());
		Assert.assertEquals(fetched.getDepositpaid(), sentBooking.getDepositpaid());
		Assert.assertEquals(fetched.getBookingdates().getCheckout(), sentBooking.getBookingdates().getCheckout());
	}

	@Test(dependsOnMethods = "getBookingShouldMatchCreatedBooking", description = "Updating a booking replaces all its details")
	void updateBookingandshouldmatch() {
		sentBooking = DataFactory.randomBooking();
		token = authclient.createToken(ConfigManager.bookerUsername(), ConfigManager.bookerPassword());
		Booking updated = bookingClient.updateBooking(bookingId, sentBooking, token);
		Assert.assertEquals(updated.getFirstname(), sentBooking.getFirstname());
		Assert.assertEquals(updated.getLastname(), sentBooking.getLastname());
		Assert.assertEquals(updated.getTotalprice(), sentBooking.getTotalprice());
		Assert.assertEquals(updated.getDepositpaid(), sentBooking.getDepositpaid());
		Assert.assertEquals(updated.getBookingdates().getCheckout(), sentBooking.getBookingdates().getCheckout());
	}

	@Test(dependsOnMethods = "updateBookingandshouldmatch", description = "Deleting a booking returns 201")
	void deleteBookingShouldReturn201() {
		Response response = bookingClient.deleteBooking(bookingId, token);
		Assert.assertEquals(response.getStatusCode(), 201);
	}
	
	@Test(dependsOnMethods = "deleteBookingShouldReturn201", description = "A deleted booking can no longer be found")
	void deleteBookingShouldReturn404() {
		Response response = bookingClient.getBookingResponse(bookingId);
		Assert.assertEquals(response.getStatusCode(), 404);
	}
	
	
}