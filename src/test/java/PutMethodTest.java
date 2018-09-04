import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author abelikov
 */
public class PutMethodTest extends Base {

    @Test
    public void putDataTest() {
        Map<String, String> userDetails1 = new HashMap<>();
        userDetails1.put("firstName", "Ivan");
        userDetails1.put("lastName", "Ivanov");

        Map<String, String> userDetails2 = new HashMap<>();
        userDetails2.put("firstName", "Petr");
        userDetails2.put("lastName", "Petrov");

        int userId = createUserAndGetIdFromResponse(userDetails1);
        given().headers(userDetails2)
            .pathParam("userId", userId)
            .when()
            .put("/users/{userId}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .header("firstName", equalTo("Petr"))
            .header("lastName", equalTo("Petrov"))
            .header("userId", equalTo(String.valueOf(userId)));
    }

    @Test
    public void putEmptyDataTest() {
        Map<String, String> userDetails1 = new HashMap<>();
        userDetails1.put("firstName", "Ivan");
        userDetails1.put("lastName", "Ivanov");

        Map<String, String> userDetails2 = new HashMap<>();

        int userId = createUserAndGetIdFromResponse(userDetails1);
        given().headers(userDetails2)
            .pathParam("userId", userId)
            .when()
            .put("/users/{userId}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .header("firstName", equalTo(null))
            .header("lastName", equalTo(null))
            .header("userId", equalTo(String.valueOf(userId)));
    }

    @Test
    public void putEmptyLastNameTest() {
        Map<String, String> userDetails1 = new HashMap<>();
        userDetails1.put("firstName", "Ivan");
        userDetails1.put("lastName", "Ivanov");

        Map<String, String> userDetails2 = new HashMap<>();
        userDetails2.put("firstName", "Petr");

        int userId = createUserAndGetIdFromResponse(userDetails1);
        given().headers(userDetails2)
            .pathParam("userId", userId)
            .when()
            .put("/users/{userId}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .header("firstName", equalTo("Petr"))
            .header("lastName", equalTo(null))
            .header("userId", equalTo(String.valueOf(userId)));
    }

    @Test
    public void putDuplicateTest() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "Ivan");
        userDetails.put("lastName", "Ivanov");

        int userId = createUserAndGetIdFromResponse(userDetails);
        given().headers(userDetails)
            .pathParam("userId", userId)
            .when()
            .put("/users/{userId}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .header("firstName", equalTo("Ivan"))
            .header("lastName", equalTo("Ivanov"))
            .header("userId", equalTo(String.valueOf(userId)));
    }

    @Test
    public void putNegativeIdTest() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "Ivan");
        userDetails.put("lastName", "Ivanov");

        createUserAndGetIdFromResponse(userDetails);
        given()
            .when()
            .put("/users/-1")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void putUserWithEmptyIdTest() {
        given()
            .when()
            .put("/users")
            .then()
            .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void putUserSpecialSymbolsIdTest() {
        given()
            .when()
            .put("/users/!@#")
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getUserLatinIdTest() {
        given()
            .when()
            .put("/users/qwerty")
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
