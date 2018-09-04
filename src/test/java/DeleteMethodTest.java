import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @author abelikov
 */
public class DeleteMethodTest extends Base {

    @Test
    public void deleteUserTest() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "Ivan");
        userDetails.put("lastName", "Ivanov");

        int userId = createUserAndGetIdFromResponse(userDetails);
        given().headers(userDetails)
            .pathParam("userId", userId)
            .when()
            .delete("/users/{userId}")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteUserWithNegativeIdTest() {
        given()
            .when()
            .delete("/users/-5")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteUserWithEmptyIdTest() {
        given()
            .when()
            .delete("/users")
            .then()
            .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void deleteUserSpecialSymbolsIdTest() {
        given()
            .when()
            .delete("/users/!@#")
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getUserLatinIdTest() {
        given()
            .when()
            .delete("/users/qwerty")
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
