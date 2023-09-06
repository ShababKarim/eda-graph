package com.edagraph.salesservice.feature;

public record ClientId(String id) {
    private static final String EMAIL_REGEX_PATTERN = "^.+@.+\\..+$";

    public static ClientId fromEmail(String email) {
        if (isNullOrInvalidEmail(email)) {
            throw new RuntimeException("Invalid email address provided");
        }

        return new ClientId(email);
    }

    public static Boolean isNullOrInvalidEmail(String email) {
        return email == null || !email.matches(EMAIL_REGEX_PATTERN);
    }
}
