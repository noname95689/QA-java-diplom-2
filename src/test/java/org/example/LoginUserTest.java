package org.example;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;


public class LoginUserTest {

    public String email = TestData.getCorrectEmail();
    public String password = TestData.getCorrectPassword();
    public String name = TestData.getCorrectName();

    @Before
    public void beforeTests() {
        Requests.checkIfUserCreatedAndDelete(email, password);
    }

    @After
    public void afterTests() {
        Requests.checkIfUserCreatedAndDelete(email, password);
    }

    @Test
    public void loginWithCorrectUserReturnsCorrectCode() {
        Requests.sendPostLogin(email, password);
        Response response = Requests.sendPostLogin(email, password);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void loginWithCorrectUserReturnsCorrectMessage() {
        Requests.sendPostLogin(email, password);
        Response response = Requests.sendPostLogin(email, password);
        response.then().assertThat().body("success", equalTo(true));
    }

    @Test
    public void loginWithWrongPasswordReturnsCorrectCode() {
        Requests.sendPostLogin(email, "пароль");
        Response response = Requests.sendPostLogin(email, "пароль");
        response.then().assertThat().statusCode(401);
    }

    @Test
    public void loginWithWrongPasswordReturnsCorrectMessage() {
        Requests.sendPostLogin(email, "пароль");
        Response response = Requests.sendPostLogin(email, "пароль");
        response.then().assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    public void loginWithWrongEmailReturnsCorrectCode() {
        Requests.sendPostLogin(email, password);
        Response response = Requests.sendPostLogin("email", password);
        response.then().assertThat().statusCode(401);
    }

    @Test
    public void loginWithWrongEmailReturnsCorrectMessage() {
        Requests.sendPostLogin(email, password);
        Response response = Requests.sendPostLogin("email", password);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"));
    }
}
