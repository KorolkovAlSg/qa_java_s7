import constans.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetOrdersTest {

    @Before
    public void setUp(){
        RestAssured.baseURI = Constants.URLFORTESTS;
    }

    @Test
    @DisplayName("Get list orders of /api/v1/orders")
    @Description("Check that list of orders returned in response body")
    @Step("Send GET request to /api/v1/orders")
    public void getOrders(){
        given()
                .get("/api/v1/orders")
                .then().assertThat()
                .body("orders", hasSize(notNullValue()));
    }
}
