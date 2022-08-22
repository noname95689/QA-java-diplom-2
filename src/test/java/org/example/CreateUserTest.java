package org.example;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {

    public String email = "noname95689@yandex.ru";
    public String password = "qwerty123";
    public String name = "jeka";


    @Before
    public void beforeTests() {
        Requests.checkIfUserCreatedAndDelete(email, password);
    }

    @After
    public void afterTests() {
        Requests.checkIfUserCreatedAndDelete(email, password);
    }

    @Test
    //Успешное создание курьера возвращает правильный статускод.
    public void createUserReturnsCorrectCode() {
        Response response = Requests.sendPostRegister(email, password, name);
        response.then().assertThat().statusCode(200);
    }

    //Успешное создание курьера возвращает правильное тело ответа
    @Test
    public void createUserReturnsCorrectMessage() {
        Response response = Requests.sendPostRegister(email, password, name);
        response.then().assertThat().body("success", equalTo(true));
    }

    //Успешное создание курьера возвращает правильное тело ответа
    @Test
    public void createExistingUserReturnsCorrectCode() {
        Requests.sendPostRegister(email, password, name);
        Response response = Requests.sendPostRegister(email, password, name);
        response.then().assertThat().statusCode(403);
    }

    @Test
    public void createExistingUserReturnsCorrectMessage() {
        Requests.sendPostRegister(email, password, name);
        Response response = Requests.sendPostRegister(email, password, name);
        response.then().assertThat().body("message", equalTo("User already exists"));
    }

    @Test
    public void createUserWithoutEmailReturnsCorrectCode() {
        Response response = Requests.sendPostRegister(null, password, name);
        response.then().assertThat().statusCode(403);
    }

    @Test
    public void createUserWithoutEmailReturnsCorrectMessage() {
        Response response = Requests.sendPostRegister(null, password, name);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWithoutPasswordReturnsCorrectCode() {
        Response response = Requests.sendPostRegister(email, null, name);
        response.then().assertThat().statusCode(403);
    }

    @Test
    public void createUserWithoutPasswordReturnsCorrectMessage() {
        Response response = Requests.sendPostRegister(email, null, name);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWithoutNameReturnsCorrectCode() {
        Response response = Requests.sendPostRegister(email, password, null);
        response.then().assertThat().statusCode(403);
    }

    @Test
    public void createUserWithoutNameReturnsCorrectMessage() {
        Response response = Requests.sendPostRegister(email, password, null);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

}
