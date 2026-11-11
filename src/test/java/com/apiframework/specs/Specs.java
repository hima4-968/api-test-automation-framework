package com.apiframework.specs;

import com.apiframework.config.ConfigManager;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class Specs {

    private Specs() {
        // only static methods: no objects needed
    }

    /** Common setup for every Restful Booker request. */
    public static RequestSpecification booker() {
        return new RequestSpecBuilder()                       
                .setBaseUri(ConfigManager.bookerBaseUrl())    
                .setContentType(ContentType.JSON)            
                .addHeader("Accept", "application/json")      
                .build();                                    
    }

    /** Common setup for every GoRest request, including the Bearer token. */
    public static RequestSpecification goRest() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.goRestBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + ConfigManager.goRestToken())   // token from the environment 🔒
                .build();
    }

    /** Common setup for every Petstore request. */
    public static RequestSpecification petstore() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.petstoreBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();
    }
}