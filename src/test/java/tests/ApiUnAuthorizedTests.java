package tests;

import BaseTest.APIBaseTest;
import data.LoginData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiUnAuthorizedTests extends APIBaseTest {

    @Test
    @DisplayName("GET /auth/login - Check login page availability")
    public void checkLoginEndpointAvailability(){
        given()
                .when()
                .get("/web/index.php/auth/login")
                .then()
                .statusCode(200)
                .contentType(containsString("text/html"));
    }

    @Test
    @DisplayName("POST /auth/validate - Successful Authorization")
    public void testSuccessfulAuth(){
        LoginData.LoginCredentials credentials = LoginData.validUser();
        Response loginPageResponse = given()
                        .get("/web/index.php/auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        String sessionCookies = loginPageResponse.getCookie("orangehrm");
        String htmlBody = loginPageResponse.getBody().asString();
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(":token=\"&quot;([^&]+)&quot;\"").matcher(htmlBody);

        String csrfToken = "";
        if (matcher.find()) {
            csrfToken = matcher.group(1);
        }

        given()
                .redirects().follow(false)
                .cookie("orangehrm", sessionCookies)
                .contentType(ContentType.URLENC)
                .formParam("_token", csrfToken)
                .formParam("username",credentials.username())
                .formParam("password", credentials.password())
                .when()
                .post("/web/index.php/auth/validate")
                .then()
                .statusCode(302)
                .header("Location", containsString("/dashboard"));
    }

    @Test
    @DisplayName("POST /auth/validate - Failed Authorization")
    public void testFailedAuth(){
        LoginData.LoginCredentials credentials = LoginData.validUser();
        Response loginPageResponse = given()
                .get("/web/index.php/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String sessionCookies = loginPageResponse.getCookie("orangehrm");
        String htmlBody = loginPageResponse.getBody().asString();
        Matcher matcher = Pattern.compile(":token=\"&quot;([^&]+)&quot;\"")
                .matcher(loginPageResponse.getBody().asString());
        String csrfToken = matcher.find() ? matcher.group(1) : "";
        if (matcher.find()) {
            csrfToken = matcher.group(1);
        }
        given()
                .cookie(sessionCookies)
                .contentType(ContentType.URLENC)
                .formParam("_token", csrfToken)
                .formParam("username", credentials.username())
                .formParam("password", credentials.password())
                .when()
                .post("/web/index.php/auth/validate")
                .then()
                .statusCode(302)
                .header("Location", containsString("/auth/login"));
    }
}
