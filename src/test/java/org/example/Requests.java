package org.example;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class Requests {

    public static String BaseURI = "https://stellarburgers.nomoreparties.site";

    @Step("Send Post request with email/password/name to /api/auth/register")
    public static Response sendPostRegister(String email, String password, String name) {
        //RestAssured.baseURI= "https://stellarburgers.nomoreparties.site";
        User user = new User(email, password, name);
        Response response = given()
                .baseUri(BaseURI)
                .header("Content-Type", "application/json")
                .body(user)
                .post("/api/auth/register");
        return response;
    }

    @Step("Send Post request with email/password to /api/auth/login")
    public static Response sendPostLogin(String email, String password) {
        //RestAssured.baseURI= "https://stellarburgers.nomoreparties.site";
        AuthUser authUser = new AuthUser(email, password);
        Response response = given()
                .baseUri(BaseURI)
                .header("Content-type", "application/json")
                .body(authUser)
                .post("/api/auth/login");
        return response;
    }

    @Step("Send Post request with email/password to /api/auth/login")
    public static Response sendDeleteRegister(String email, String password) {
        //RestAssured.baseURI= "https://stellarburgers.nomoreparties.site";
        Response response = given()
                .baseUri(BaseURI)
                .auth().oauth2(getAccessToken(email, password))
                .header("Content-type", "application/json")
                .log().all()
                .delete("/api/auth/user");
        return response;
    }

    @Step("Send Patch request to /api/auth/user")
    public static Response sendPatchAuthUser(String accessToken,
                                             String newEmail, String newName, String newPassword) {
        //RestAssured.baseURI= "https://stellarburgers.nomoreparties.site";
        User newUser = new User(newEmail, newName, newPassword);
        Response response = given()
                .baseUri(BaseURI)
                .auth().oauth2(accessToken)
                .header("Content-type", "application/json")
                .body(newUser)
                .log().all()
                .patch("/api/auth/user");
        return response;
    }

    @Step("Send Patch request to /api/auth/user")
    public static Response sendPatchWithNoAuthUser(String newEmail, String newName, String newPassword) {
        User newUser = new User(newEmail, newName, newPassword);
        Response response = given()
                .baseUri(BaseURI)
                .header("Content-type", "application/json")
                .body(newUser)
                .log().all()
                .patch("/api/auth/user");
        return response;
    }

    @Step("Send Post request with email/password to /api/auth/login, return accessToken")
    public static String getAccessToken(String email, String password) {
        Response response = sendPostLogin(email, password);
        AuthAnswer authAnswer =
                response.body().as(AuthAnswer.class);
        String accessToken = authAnswer.getAccessToken().replace("Bearer ", "");
        return accessToken;
    }

    @Step("Send Post request with email/password to /api/auth/login, return answer")
    public static AuthAnswer getAccessAnswer(String email, String password) {
        Response response = sendPostLogin(email, password);
        AuthAnswer authAnswer =
                response.body().as(AuthAnswer.class);
        return authAnswer;
    }

    @Step("Delete user if he exist")
    public static void checkIfUserCreatedAndDelete(String email, String password) {
        if (Requests.sendPostLogin(email, password).statusCode() == 200) {
            Requests.sendDeleteRegister(email, password);
        }
    }

    @Step("Send Post request /api/orders without auth")
    public static Response sendPostWithAuthOrders(String[] ingredients, String accessToken) {
        Order order = new Order(ingredients);
        Response response = given()
                .baseUri(BaseURI)
                .auth().oauth2(accessToken)
                .header("Content-type", "application/json")
                .body(order)
                .log().all()
                .post("/api/orders");
        return response;
    }

    @Step("Send Post request /api/orders without auth")
    public static Response sendPostWithoutAuthOrders(String[] ingredients) {
        Order order = new Order(ingredients);
        Response response = given()
                .baseUri(BaseURI)
                .header("Content-type", "application/json")
                .body(order)
                .post("/api/orders");
        return response;
    }

    @Step("Send Post request /api/orders without Ingredients")
    public static Response sendPostWithoutIngredients() {
        Response response = given()
                .baseUri(BaseURI)
                .header("Content-type", "application/json")
                .post("/api/orders");
        return response;
    }

    @Step("Send Get request /api/orders without auth")
    public static Response sendGetWithoutAuthOrders() {
        Response response = given()
                .baseUri(BaseURI)
                .header("Content-type", "application/json")
                .get("/api/orders");
        return response;
    }

    @Step("Send Get request /api/orders without auth")
    public static Response sendGetWithAuthOrders(String accessToken) {
        Response response = given()
                .baseUri(BaseURI)
                .auth().oauth2(accessToken)
                .header("Content-type", "application/json")
                .get("/api/orders");
        return response;
    }
}
