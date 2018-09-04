import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author abelikov
 */
public class PostMethodTest extends Base {

    @Test
    public void postDataAsLatinTest() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "Ivan");
        userDetails.put("lastName", "Ivanov");

        given()
            .headers(userDetails)
            .when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .header("firstName", equalTo("Ivan"))
            .header("lastName", equalTo("Ivanov"));
    }

    @Test
    public void postDataAsCyrillicTest() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "Иван");
        userDetails.put("lastName", "Иванов");

        given()
            .headers(userDetails)
            .when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void postDataAsNumericTest() {
        Map<String, Integer> userDetails = new HashMap<>();
        userDetails.put("firstName", 1);
        userDetails.put("lastName", -5);

        given()
            .headers(userDetails)
            .when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .header("firstName", equalTo("1"))
            .header("lastName", equalTo("-5"));
    }

    @Test
    public void postDataAsZeroTest() {
        Map<String, Integer> userDetails = new HashMap<>();
        userDetails.put("firstName", 0);
        userDetails.put("lastName", 0);

        given()
            .headers(userDetails)
            .when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .header("firstName", equalTo("0"))
            .header("lastName", equalTo("0"));
    }

    @Test
    public void postIncompleteDataTest() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "Ivan");

        given()
            .headers(userDetails)
            .when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .header("firstName", equalTo("Ivan"))
            .header("lastName", equalTo(null));
    }

    @Test
    public void postExcessDataTest() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "Ivan");
        userDetails.put("lastName", "Ivanov");
        userDetails.put("address", "Moscow");

        given()
            .headers(userDetails)
            .when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void postEmptyDataTest() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "");
        userDetails.put("lastName", "");

        given()
            .headers(userDetails)
            .when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .header("firstName", equalTo(null))
            .header("lastName", equalTo(null));;
    }

    @Test
    public void postSpecialSymbolsTest() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", "!@#$%^&*()-_=+/?<>{}[]'");
        userDetails.put("lastName", "!@#$%^&*()-_=+/?<>{}[]'");

        given()
            .headers(userDetails)
            .when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
