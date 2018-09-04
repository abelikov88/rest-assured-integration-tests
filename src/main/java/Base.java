import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author abelikov
 */
public class Base {

    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = 28080;
        } else {
            RestAssured.port = Integer.valueOf(port);
        }

        String basePath = System.getProperty("server.path");
        if (basePath == null) {
            basePath = "/rs/";
        }

        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;
    }

    static int createUserAndGetIdFromResponse(Map<String, String> userDetails) {
        String body = String.valueOf(RestAssured.given()
            .headers(userDetails)
            .when()
            .post("/users")
            .then()
            .extract()
            .body()
            .asString());

        Pattern pattern = Pattern.compile("ID=(\\d+)");
        Matcher m = pattern.matcher(body);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }
}
