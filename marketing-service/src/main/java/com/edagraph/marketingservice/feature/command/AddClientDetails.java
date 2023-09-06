package com.edagraph.marketingservice.feature.command;

public record AddClientDetails(String email, String firstName,
        String lastName, String phoneNumber) {

}
