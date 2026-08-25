package BaseTest;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;


@ExtendWith(AllureJunit5.class)
public class APIBaseTest {

    protected static final String API_BASE_URL = "https://opensource-demo.orangehrmlive.com";

    @BeforeAll
    public static void setUpApi() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(API_BASE_URL)
                .addFilter(new AllureRestAssured())
                .build();

        given()
                .when()
                .get("/web/index.php/auth/login")
                .then()
                .statusCode(200)
                .contentType(containsString("text/html"));
    }
}