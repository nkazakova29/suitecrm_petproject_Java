package tests;

import BaseTest.APIBaseTest;
import data.LoginData;
import data.UsersData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Указываем, что порядок тестов важен
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class APIUserCRUDTests extends APIBaseTest {

      // Сохраняем состояния полей между тестами

        private String authCookie;
        private int empNumber; // Хранилище для полученного номера
        private String csrfToken;

        @BeforeAll
        public void setupAuth() {
            // Логин выносим в BeforeAll, чтобы не дублировать в каждом тесте,
            // но сами CRUD операции остаются полноценными тестами.
            LoginData.LoginCredentials credentials = LoginData.validUser();

            Response loginPageResponse = given().get("/web/index.php/auth/login");
            String initialCookie = loginPageResponse.getCookie("orangehrm");
            Matcher matcher = Pattern.compile(":token=\"&quot;([^&]+)&quot;\"")
                    .matcher(loginPageResponse.getBody().asString());
            csrfToken = matcher.find() ? matcher.group(1) : "";

            Response loginResponse = given()
                    .redirects().follow(false)
                    .cookie("orangehrm", initialCookie)
                    .contentType(ContentType.URLENC)
                    .formParam("_token", csrfToken)
                    .formParam("username", credentials.username())
                    .formParam("password", credentials.password())
                    .post("/web/index.php/auth/validate");

            authCookie = loginResponse.getCookie("orangehrm");
        }

        @Test
        @Order(1)
        @DisplayName("1. Создание сотрудника и получение empNumber")
        public void test1_createEmployee() {
            UsersData.UsersDataCreation forCreation = UsersData.userForCreating();

            // Это ПОЛНОЦЕННЫЙ тест с валидацией статуса и тела
            empNumber = given()
                    .cookie("orangehrm", authCookie)
                    .contentType(ContentType.JSON)
                    .body(forCreation)
                    .when()
                    .post("/web/index.php/api/v2/pim/employees")
                    .then()
                    .statusCode(200)
                    .body("data.firstName", equalTo(forCreation.firstName()))
                    .body("data.empNumber", notNullValue())
                    .extract()
                    .path("data.empNumber");

            Assertions.assertTrue(empNumber > 0, "empNumber должен быть больше 0");
        }

        @Test
        @Order(2)
        @DisplayName("2. Поиск созданного сотрудника по empNumber")
        public void test2_getEmployeeById() {
            UsersData.UsersDataCreation forCreation = UsersData.userForCreating();
            // Используем empNumber, полученный в Test 1
            given()
                    .cookie("orangehrm", authCookie)
                    //.queryParam("_token", csrfToken)
                    .when()
                    .get("/web/index.php/api/v2/pim/employees/" + empNumber)
                    .then()
                    .statusCode(200)
                    .body("data.empNumber", equalTo(empNumber))
                    .body("data.lastName", equalTo("Buslaev"));
        }

        @Test
        @Order(3)
        @DisplayName("3. Удаление сотрудника по empNumber")
        public void test3_deleteEmployee() {
            // Переиспользуем empNumber для проверки удаления
            given()
                    .cookie("orangehrm", authCookie)
                    .header("X-Non-Cookie-Token", csrfToken)
                    .contentType(ContentType.JSON)
                    .body(Collections.singletonMap("ids", Collections.singletonList(empNumber)))
                    .when()
                    .delete("/web/index.php/api/v2/pim/employees")
                    .then()
                    .statusCode(200);
        }
}
