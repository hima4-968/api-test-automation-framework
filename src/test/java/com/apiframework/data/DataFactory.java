package com.apiframework.data;

import java.time.LocalDate;

import com.apiframework.models.request.Booking;
import com.apiframework.models.request.BookingDates;

import net.datafaker.Faker;

public final class DataFactory {

    private static final Faker FAKER = new Faker();      // one fake-data generator, shared

    private DataFactory() {
        // only static methods: no objects needed
    }

    /** A brand-new, realistic booking: different on every call. */
    public static Booking randomBooking() {
        // dates: check-in 10–60 days from today, stay 1–7 nights
        LocalDate checkin  = LocalDate.now().plusDays(FAKER.number().numberBetween(10, 60));
        LocalDate checkout = checkin.plusDays(FAKER.number().numberBetween(1, 7));

        BookingDates dates = new BookingDates();
        dates.setCheckin(checkin.toString());      
        dates.setCheckout(checkout.toString());    

        Booking booking = new Booking();
        booking.setFirstname(FAKER.name().firstName());
        booking.setLastname(FAKER.name().lastName());
        booking.setTotalprice(FAKER.number().numberBetween(100, 5000));
        booking.setDepositpaid(FAKER.bool().bool());
        booking.setBookingdates(dates);
        booking.setAdditionalneeds(
                FAKER.options().option("Breakfast", "Late checkout", "Airport pickup", "Extra bed"));
        return booking;
    }
}