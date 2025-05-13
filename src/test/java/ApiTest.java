import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;


public class ApiTest {

    @Test
    public void test() {
        RestAssured.given().baseUri("http://localhost:8080/v2/user").contentType(ContentType.JSON).body(
                "{"
                        + "\"id\": 1,"
                        + "\"username\": \"AlexKuzTest\","
                        + "\"firstName\": \"User\","
                        + "\"lastName\": \"ForTestov\","
                        + "\"email\": \"test.user@gmail.com\","
                        + "\"password\": \"12345\","
                        + "\"phone\": \"+777\","
                        + "\"userStatus\": 0"
                        + "}"
        ).when().post().then().statusCode(200);

        RestAssured.given()
                .queryParams("username", "AlexKuzTest", "password", "12345").when().get("http://localhost:8080/v2/user/login").then().body("message", containsString("logged in user session"));

        RestAssured.given()
                .when().get("http://localhost:8080/v2/user/logout").then().body("message", equalTo("ok"));

        RestAssured.given()
                .queryParams("username", "AlexKuzTest", "password", "12345").when().get("http://localhost:8080/v2/user/login").then().body("message", containsString("logged in user session"));

        RestAssured.given()
                .baseUri("http://localhost:8080/v2")
                .contentType(ContentType.JSON)
                .body(
                        "{"
                                + "\"id\": 1,"
                                + "\"username\": \"AlexKuzTest\","
                                + "\"firstName\": \"User\","
                                + "\"lastName\": \"ForTestov\","
                                + "\"email\": \"test.user@gmail.com\","
                                + "\"password\": \"12345\","
                                + "\"phone\": \"+999\","
                                + "\"userStatus\": 0"
                                + "}"
                )
                .when()
                .put("/user/AlexKuzTest")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when().get("http://localhost:8080/v2/user/AlexKuzTest").
                then().body("phone", equalTo("+999"));

        RestAssured.given()
                .when().get("http://localhost:8080/v2/user/logout").then().body("message", equalTo("ok"));

        RestAssured.given()
                .queryParams("username", "AlexKuzTest", "password", "12345").when()
                .get("http://localhost:8080/v2/user/login").then()
                .body("message", containsString("logged in user session"));

        RestAssured.given()
                .baseUri("http://localhost:8080/v2/pet")
                .contentType(ContentType.JSON)
                .body(
                        "{"
                                + "\"id\": 1,"
                                + "\"category\": {\"id\": 2, \"name\": \"Worm\"},"
                                + "\"name\": \"Worms\","
                                + "\"photoUrls\": [\"https://example.com/photo1.jpg\"],"
                                + "\"tags\": [{\"id\": 3, \"name\": \"cute\"}],"
                                + "\"status\": \"available\""
                                + "}"
                )
                .when()
                .post()
                .then().statusCode(200);


        RestAssured.given()
                .when().get("http://localhost:8080/v2/pet/1").
                then().statusCode(200);

        RestAssured.given()
                .when().get("http://localhost:8080/v2/user/logout").then()
                .body("message", equalTo("ok"));

        RestAssured.given()
                .queryParams("username", "AlexKuzTest", "password", "12345").when()
                .get("http://localhost:8080/v2/user/login").then()
                .body("message", containsString("logged in user session"));

        RestAssured.given()
                .baseUri("http://localhost:8080/v2/store/order")
                .contentType(ContentType.JSON)
                .body(
                        "{"
                        + "\"id\": 5,"
                        + "\"petId\": 1,"
                        + "\"quantity\": 3,"
                        + "\"shipDate\": \"2026-05-12T23:19:45.628Z\","
                        + "\"status\": \"placed\","
                        + "\"complete\": true"
                        + "}"
                )
                .when()
                .post();

        RestAssured.given()
                .when().get("http://localhost:8080/v2/store/order/5").
                then().statusCode(200);

        RestAssured.given()
                .when().get("http://localhost:8080/v2/user/logout").then().body("message", equalTo("ok"));

        RestAssured.given()
                .queryParams("username", "AlexKuzTest", "password", "12345").when()
                .get("http://localhost:8080/v2/user/login").then()
                .body("message", containsString("logged in user session"));

        RestAssured.given()
                .when().delete("http://localhost:8080/v2/store/order/5").
                then().statusCode(200);

        RestAssured.given()
                .header("api_key", "special-key")
                .when().delete("http://localhost:8080/v2/pet/1").
                then().statusCode(200);

        RestAssured.given()
                .when().get("http://localhost:8080/v2/store/order/5").
                then().statusCode(404);

        RestAssured.given()
                .header("api_key", "special-key")
                .when().delete("http://localhost:8080/v2/store/pet/1").
                then().statusCode(404);

        RestAssured.given()
                .when().delete("http://localhost:8080/v2/user/AlexKuzTest").
                then().statusCode(200);

        RestAssured.given()
                .when().get("http://localhost:8080/v2/user/AlexKuzTest").
                then().statusCode(404);
    }
}

