import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;


public class ApiTest {

    @Test
    public void test() {
        RestAssured.given().baseUri("https://petstore.swagger.io/v2/user").contentType(ContentType.JSON).body(
                "{"
                        + "\"id\": 100110,"
                        + "\"username\": \"userfortest\","
                        + "\"firstName\": \"User\","
                        + "\"lastName\": \"ForTestov\","
                        + "\"email\": \"test.user@gmail.com\","
                        + "\"password\": \"12345\","
                        + "\"phone\": \"+7777\","
                        + "\"userStatus\": 0"
                        + "}"
        ).when().post().then().log().body();

        RestAssured.given()
                .queryParams("username", "userfortest", "password", "12345").when().get("https://petstore.swagger.io/v2/user/login").then().body("message", containsString("logged in user session"));

        RestAssured.given()
                .when().get("https://petstore.swagger.io/v2/user/logout").then().body("message", equalTo("ok"));

        
    }
}
