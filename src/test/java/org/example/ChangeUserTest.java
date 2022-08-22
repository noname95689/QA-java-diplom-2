package org.example;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;


public class ChangeUserTest {

    public String email = "noname95689@yandex.ru";
    public String password = "qwerty123";
    public String name = "jeka";
    public String newEmail = "test@yandex.ru";
    public String newPassword = "123qwerty";
    public String newName = "valentin";

    @Before
    public void beforeTests() {
        Requests.checkIfUserCreatedAndDelete(email, password);
    }

    @After
    public void afterTests() {
        Requests.checkIfUserCreatedAndDelete(email, password);
    }


    //Response response = Requests.sendPatchAuthUser();
    @Test
    public void changingNameReturnsCorrectCode() {
        Requests.sendPostRegister(email, password, name);
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPatchAuthUser(accessToken, email, password, newName);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void changingNameReturnsCorrectMessage() {
        Requests.sendPostRegister(email, password, name);
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPatchAuthUser(accessToken, email, password, newName);
        response.then().assertThat().body("success", equalTo( true));
    }

    @Test
    public void changingEmailReturnsCorrectCode() {
        Requests.sendPostRegister(email, password, name);
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPatchAuthUser(accessToken, newEmail, password, name);
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void changingEmailReturnsCorrectMessage() {
        Requests.sendPostRegister(email, password, name);
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPatchAuthUser(accessToken, newEmail, password, name);
        response.then().assertThat().body("success", equalTo( true));
    }

    @Test
    public void changingPasswordReturnsCorrectCode() {
        Requests.sendPostRegister(email, password, name);
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPatchAuthUser(accessToken, email, newPassword, name);
        response.then().assertThat().statusCode(200);
        Requests.sendPatchAuthUser(accessToken, email, password, name);
    }

    @Test
    public void changingPasswordReturnsCorrectMessage() {
        Requests.sendPostRegister(email, password, name);
        String accessToken = Requests.getAccessToken(email, password);
        Response response = Requests.sendPatchAuthUser(accessToken, email, newPassword, name);
        response.then().assertThat().body("success", equalTo( true));
        Requests.sendPatchAuthUser(accessToken, email, password, name);
    }

    @Test
    public void changingNameWithoutAuthReturnsCorrectCode() {
        Requests.sendPostRegister(email, password, name);
        Response response = Requests.sendPatchWithNoAuthUser(email, password, newName);
        response.then().assertThat().statusCode(401);
    }

    @Test
    public void changingNameWithoutAuthReturnsCorrectMessage() {
        Requests.sendPostRegister(email, password, name);
        Response response = Requests.sendPatchWithNoAuthUser(email, password, newName);
        response.then().assertThat().body("message", equalTo( "You should be authorised"));
    }

    @Test
    public void changingEmailWithoutAuthReturnsCorrectCode() {
        Requests.sendPostRegister(email, password, name);
        Response response = Requests.sendPatchWithNoAuthUser(newEmail, password, name);
        response.then().assertThat().statusCode(401);
    }

    @Test
    public void changingEmailWithoutAuthReturnsCorrectMessage() {
        Requests.sendPostRegister(email, password, name);
        Response response = Requests.sendPatchWithNoAuthUser(newEmail, password, name);
        response.then().assertThat().body("message", equalTo( "You should be authorised"));
    }

    @Test
    public void changingPasswordWithoutAuthReturnsCorrectCode() {
        Requests.sendPostRegister(email, password, name);
        Response response = Requests.sendPatchWithNoAuthUser(email, newPassword, name);
        response.then().assertThat().statusCode(401);
    }

    @Test
    public void changingPasswordWithoutAuthReturnsCorrectMessage() {
        Requests.sendPostRegister(email, password, name);
        Response response = Requests.sendPatchWithNoAuthUser(email, newPassword, name);
        response.then().assertThat().body("message", equalTo( "You should be authorised"));
    }
}
