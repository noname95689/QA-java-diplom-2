package org.example;

import com.github.javafaker.Faker;

public class TestData {

    public static String getCorrectEmail() {
        Faker faker = new Faker();
        final String correctEmail = faker.internet().emailAddress();
        return correctEmail;
    }

    public static String getCorrectPassword() {
        Faker faker = new Faker();
        final String correctPassword = faker.internet().password(6, 10);
        return correctPassword;
    }

    public static String getCorrectName() {
        Faker faker = new Faker();
        final String correctName = faker.name().firstName();
        return correctName;
    }

    public static String getWrongIngredient() {
        Faker faker = new Faker();
        final String wrongIngredient = faker.random().toString();
        return wrongIngredient;
    }
}
