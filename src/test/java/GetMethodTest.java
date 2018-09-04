import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author abelikov
 */
public class GetMethodTest extends Base {

    @Test
    public void basicPingTest() {
        given()
            .when()
            .get("/users")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getByIdTest() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "Ivan");
        userDetails.put("lastName", "Ivanov");

        int userId = createUserAndGetIdFromResponse(userDetails);

        given().pathParam("userId", userId)
            .when()
            .get("/users/{userId}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .header("userId", equalTo(String.valueOf(userId)));
    }

    @Test
    public void getUserNegativeIdTest() {
        given()
            .when()
            .get("/users/-1")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getUserSpecialSymbolsIdTest() {
        given()
            .when()
            .get("/users/!@#")
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getUserLatinIdTest() {
        given()
            .when()
            .get("/users/qwerty")
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
