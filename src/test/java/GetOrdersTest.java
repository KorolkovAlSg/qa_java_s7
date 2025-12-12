import constans.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GetOrdersTest {

    Order order;

    @Before
    public void setUp(){
        RestAssured.baseURI = Constants.URLFORTESTS;
        order = new Order();
    }

    @Test
    @DisplayName("Get list orders of /api/v1/orders")
    @Description("Check that list of orders returned in response body")
    public void getOrdersListOrders(){
        order.getOrders()
                .then()
                .assertThat().body("orders", hasSize(notNullValue()));
    }
}
