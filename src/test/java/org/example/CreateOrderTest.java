package org.example;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest {

    public String email = "noname95689@yandex.ru";
    public String password = "qwerty123";
    public String name = "jeka";
    public String[] correctIngredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa74"};
    public String[] unCorrectIngredients = {"qwerty", "123456", "фывапр"};

    @Before
    public void beforeTests() {
        Requests.checkIfUserCreatedAndDelete(email, password);
        Requests.sendPostRegister(email, password, name);
    }

    @After
    public void afterTests() {
        Requests.checkIfUserCreatedAndDelete(email, password);
    }

    @Test
    public void createOrderWithAuthorizationReturnsCorrectCode() {
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPostWithAuthOrders(correctIngredients, accessToken);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void createOrderWithAuthorizationReturnsCorrectMessage() {
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPostWithAuthOrders(correctIngredients, accessToken);
        response.then().assertThat().body("success", equalTo( true));
    }

    @Test
    public void createOrderWithoutAuthorizationReturnsCorrectCode() {
        Response response = Requests.sendPostWithoutAuthOrders(correctIngredients);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void createOrderWithoutAuthorizationReturnsCorrectMessage() {
        Response response = Requests.sendPostWithoutAuthOrders(correctIngredients);
        response.then().assertThat().body("success", equalTo( true));
    }

    @Test
    public void createOrderWithIngredientsReturnsCorrectCode() {
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPostWithAuthOrders(correctIngredients, accessToken);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void createOrderWithIngredientsReturnsCorrectMessage() {
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPostWithAuthOrders(correctIngredients, accessToken);
        response.then().assertThat().body("success", equalTo( true));
    }

    @Test
    public void createOrderWithoutIngredientsReturnsCorrectCode() {
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPostWithAuthOrders(null, accessToken);
        response.then().assertThat().statusCode(400);
    }

    @Test
    public void createOrderWithoutIngredientsReturnsCorrectMessage() {
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPostWithAuthOrders(null, accessToken);
        response.then().assertThat().body("message", equalTo( "Ingredient ids must be provided"));
    }

    @Test
    public void createOrderWithWrongIngredientsReturnsCorrectCode() {
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPostWithAuthOrders(unCorrectIngredients, accessToken);
        response.then().assertThat().statusCode(500);
    }

}
