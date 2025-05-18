import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertTrue;

public class ApiTest {

    public static class LoginInfo {
        public int code;
        public String type;
        public String message;
    }

    @Test
    public void test() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080/v2/")
                .build();

        Map<String, Object> createUser = new HashMap<>();
        createUser.put("id", 1);
        createUser.put("username", "AlexKuzTest");
        createUser.put("firstName", "User");
        createUser.put("lastName", "ForTestov");
        createUser.put("email", "test.user@gmail.com");
        createUser.put("password", "12345");
        createUser.put("phone", "+777");
        createUser.put("userStatus", 0);

        RestAssured.given().spec(requestSpec).contentType(ContentType.JSON)
                .body(createUser).when().post("user").then().statusCode(200);

        RestAssured.given()
                .queryParams("username", "AlexKuzTest", "password", "12345").when().get("http://localhost:8080/v2/user/login").then().body("message", containsString("logged in user session"));

        RestAssured.given()
                .when().get("http://localhost:8080/v2/user/logout").then().body("message", equalTo("ok"));

        RestAssured.given()
                .queryParams("username", "AlexKuzTest", "password", "12345").when().get("http://localhost:8080/v2/user/login").then().body("message", containsString("logged in user session"));

        createUser.replace("phone", "+999");

        RestAssured.given().spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(createUser)
                .when()
                .put("user/AlexKuzTest")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when().get("http://localhost:8080/v2/user/AlexKuzTest").
                then().body("phone", equalTo("+999"));

        RestAssured.given()
                .when().get("http://localhost:8080/v2/user/logout").then().body("message", equalTo("ok"));

        String message = RestAssured.given().spec(requestSpec)
                .queryParams("username", "AlexKuzTest", "password", "12345").when()
                .get("user/login").then()
                .extract()
                .path("message");

        assertTrue(message.contains("logged in user session"));

        RestAssured.given()
                .spec(requestSpec)
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
                .post("pet")
                .then().statusCode(200);

        RestAssured.given()
                .when().get("http://localhost:8080/v2/pet/1").
                then().statusCode(200);

        RestAssured.given()
                .when().get("http://localhost:8080/v2/user/logout").then()
                .body("message", equalTo("ok"));

        LoginInfo info = RestAssured.given()
                .queryParams("username", "AlexKuzTest", "password", "12345").when()
                .get("http://localhost:8080/v2/user/login").then()
                .body("message", containsString("logged in user session")).extract().as(LoginInfo.class);

        System.out.println("Десериализация: " + info.message);

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

