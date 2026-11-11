package com.apiframework.tests.booker;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiframework.clients.BookingClient;
import com.apiframework.data.DataFactory;
import com.apiframework.models.request.Booking;
import com.apiframework.models.response.CreateBookingResponse;

public class BookingCrudTests {

	private Booking sentBooking;
	private int bookingId;
	private final BookingClient bookingClient = new BookingClient();

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

}