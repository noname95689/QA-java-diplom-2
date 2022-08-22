package org.example;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest {

    public String email = "noname95689@yandex.ru";
    public String password = "qwerty123";
    public String name = "jeka";
    public String[] correctIngredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa74"};

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
    public void getOrderWithoutAuthReturnsCorrectCode() {
        Response response = Requests.sendGetWithoutAuthOrders();
        response.then().assertThat().statusCode(401);
    }

    @Test
    public void getOrderWithoutAuthReturnsCorrectMessage() {
        Response response = Requests.sendGetWithoutAuthOrders();
        response.then().assertThat().body("success", equalTo( "You should be authorised"));
    }

    //Добавить десериализацию ответа и сравнивание с notnullvalue
    @Test
    public void getOrderWithAuthorizationReturnsCorrectCode() {
        String accessToken = Requests.getAccessToken(email, password);
        Requests.sendPostWithAuthOrders(correctIngredients, accessToken);
        Response response = Requests.sendGetWithAuthOrders(accessToken);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void getOrderWithAuthorizationReturnsCorrectMessage() {
        String accessToken = Requests.getAccessToken(email, password);
        Requests.sendPostWithAuthOrders(correctIngredients, accessToken);
        Response response = Requests.sendGetWithAuthOrders(accessToken);
        response.then().assertThat().body("success", equalTo( true));
    }


}
