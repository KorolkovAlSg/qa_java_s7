import constans.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Order {

    private OrderParameters parameters;
    Response response;

    public Order(OrderParameters parameters) {
        this.parameters = parameters;
    }

    public Order(){

    }
    @Step("Send POST request to /api/v1/orders")
    public Response createOrder(){
        response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(parameters)
                        .when()
                        .post(Endpoints.ORDER);
        return response;
    }

    @Step("Send PUT request to /api/v1/orders/cancel?track=N")
    public void cancelOrder(){
        given()
                .when()
                .put(Endpoints.ORDER + "/cancel?track=" + response.path("track"));
    }

    @Step("Send GET request to /api/v1/orders")
    public Response getOrders() {
        response =
                given()
                        .get(Endpoints.ORDER);
        return response;
    }
}
