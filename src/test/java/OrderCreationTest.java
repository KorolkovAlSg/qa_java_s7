import constans.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    OrderParameters parameters;
    Order order;

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;;

    public OrderCreationTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = Constants.URLFORTESTS;
        parameters = new OrderParameters(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        order = new Order(parameters);
    }

    @Parameterized.Parameters(name = "Тест с color = {8}")
    public static Object[][] getOrderInfo(){
        return new Object[][]{
                {"Aleks", "Aleksov", "Ekb", "4", "+7 811 355 35 35", 5, "2025-12-12", "Pobistree pls =)", List.of("GREY")},
                {"Aleks", "Aleksov", "Ekb", "4", "+7 811 355 35 35", 5, "2025-12-12", "Pobistree pls =)", List.of("BLACK")},
                {"Aleks", "Aleksov", "Ekb", "4", "+7 811 355 35 35", 5, "2025-12-12", "Pobistree pls =)", List.of("GREY","BLACK")},
                {"Aleks", "Aleksov", "Ekb", "4", "+7 811 355 35 35", 5, "2025-12-12", "Pobistree pls =)", null},
        };
    }

    @Test
    @DisplayName("Create new order of /api/v1/orders")
    @Description("Basic test for creation order of /api/v1/orders")
    public void createNewOrderCode201andBodyWithTrack(){
        order.createOrder()
                .then()
                .statusCode(SC_CREATED)
                .and()
                .assertThat().body("track", notNullValue());
    }

    @After
    public void cancelOrder(){
        order.cancelOrder();
    }


}
