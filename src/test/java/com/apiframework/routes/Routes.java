package com.apiframework.routes;

public final class Routes {

    private Routes() {
        // only holds constants: no objects needed
    }

    // ---------- Restful Booker ----------
    public static final String BOOKER_PING   = "/ping";
    public static final String BOOKER_AUTH   = "/auth";
    public static final String BOOKINGS      = "/booking";
    public static final String BOOKING_BY_ID = "/booking/{id}";

    // ---------- GoRest ----------
    public static final String USERS      = "/users";
    public static final String USER_BY_ID = "/users/{id}";

    // ---------- Petstore ----------
    public static final String PETS          = "/pet";
    public static final String PET_BY_ID     = "/pet/{petId}";
    public static final String ORDERS        = "/store/order";
    public static final String ORDER_BY_ID   = "/store/order/{orderId}";
}