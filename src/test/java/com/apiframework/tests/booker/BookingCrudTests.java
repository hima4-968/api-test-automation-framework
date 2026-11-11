package com.apiframework.tests.booker;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiframework.data.DataFactory;
import com.apiframework.models.request.Booking;
import com.apiframework.models.response.CreateBookingResponse;
import com.apiframework.routes.Routes;
import com.apiframework.specs.Specs;

public class BookingCrudTests {

    private Booking sentBooking;      // what we created: later tests compare against it
    private int bookingId;            // the id the API gave us

    @Test(groups = "smoke", description = "A new booking is created and returned with an id")
    void createBookingShouldReturnIdAndSameData()
    {
        sentBooking = DataFactory.randomBooking();                    // ① fresh data

        CreateBookingResponse created = given()
                .spec(Specs.booker())                                 // ② common setup
                .body(sentBooking)
            .when()
                .post(Routes.BOOKINGS)                                // ③ the path
            .then()
                .statusCode(200)
                .extract().as(CreateBookingResponse.class);           // ④ the wrapper → object

        bookingId = created.getBookingid();

        Assert.assertTrue(bookingId > 0, "Booking id should be positive");
        Assert.assertEquals(created.getBooking().getFirstname(), sentBooking.getFirstname());
        Assert.assertEquals(created.getBooking().getTotalprice(), sentBooking.getTotalprice());
        Assert.assertEquals(created.getBooking().getBookingdates().getCheckin(),
                            sentBooking.getBookingdates().getCheckin());
    }

    @Test(dependsOnMethods = "createBookingShouldReturnIdAndSameData",
          description = "Reading the booking returns exactly what was created")
    void getBookingShouldMatchCreatedBooking()
    {
        Booking fetched = given()
                .spec(Specs.booker())
                .pathParam("id", bookingId)
            .when()
                .get(Routes.BOOKING_BY_ID)
            .then()
                .statusCode(200)
                .extract().as(Booking.class);                         // no wrapper on GET

        Assert.assertEquals(fetched.getFirstname(), sentBooking.getFirstname());
        Assert.assertEquals(fetched.getLastname(), sentBooking.getLastname());
        Assert.assertEquals(fetched.getTotalprice(), sentBooking.getTotalprice());
        Assert.assertEquals(fetched.getDepositpaid(), sentBooking.getDepositpaid());
        Assert.assertEquals(fetched.getBookingdates().getCheckout(),
                            sentBooking.getBookingdates().getCheckout());
    }
}