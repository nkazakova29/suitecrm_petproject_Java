/*
package tests;

import BaseTest.APIBaseTest;
import data.LoginData;
import data.UsersData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class APITests extends APIBaseTest {


    @Test
    @DisplayName("POST /web/index.php/api/v2/pim/employees - create a new user")
    public void getAllUsers() {
        LoginData.LoginCredentials credentials = LoginData.validUser();

        // 1. Получаем стартовую сессию и CSRF токен
        Response loginPageResponse = given()
                .get("/web/index.php/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String initialSessionCookie = loginPageResponse.getCookie("orangehrm");
        String htmlBody = loginPageResponse.getBody().asString();

        Matcher matcher = Pattern.compile(":token=\"&quot;([^&]+)&quot;\"").matcher(htmlBody);
        String csrfToken = matcher.find() ? matcher.group(1) : "";


        Response loginValidationResponse = given()
                .redirects().follow(false)
                .cookie("orangehrm", initialSessionCookie)
                .contentType(ContentType.URLENC)
                .formParam("_token", csrfToken)
                .formParam("username", credentials.username())
                .formParam("password", credentials.password())
                .when()
                .post("/web/index.php/auth/validate")
                .then()
                .statusCode(302)
                .header("Location", containsString("/dashboard"))
                .extract()
                .response();

        String authSessionCookie = loginValidationResponse.getCookie("orangehrm");


        //Get users part
        UsersData.UsersDataCreation forCreation = UsersData.userForCreating();
        int empNumber = given()
                .redirects().follow(false)
                .cookies("orangehrm", authSessionCookie)
                .contentType(ContentType.JSON)
                .body(forCreation)
                .when()
                .post("/web/index.php/api/v2/pim/employees")
                .then()
                .statusCode(200)
                .extract()
                .path("data.empNumber");

    }


}
*/
