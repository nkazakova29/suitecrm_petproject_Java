package BaseTest;

import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AllureJunit5.class)
public class APIBaseTest {

    protected static final String API_BASE_URL = "https://opensource-demo.orangehrmlive.com";

    @BeforeAll
    public static void setUpApi() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(API_BASE_URL)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }
}