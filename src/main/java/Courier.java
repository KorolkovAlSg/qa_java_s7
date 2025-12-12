import constans.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Courier {

    private ProfileCourier profile;
    Response response;

    public Courier(ProfileCourier profile) {
        this.profile = profile;
    }

    @Step("Send POST request to /api/v1/courier")
    public Response createCourier(){
        response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(profile)
                        .when()
                        .post(Endpoints.COURIER);
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response logInCourier(){
        response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(profile)
                        .when()
                        .post(Endpoints.COURIER_LOGIN);
        return response;
    }

    @Step("Send DELETE request to /api/v1/courier/:id")
    public void deleteCourier(){
        response = logInCourier();
        given()
                .when()
                .delete(Endpoints.COURIER + "/" + response.body().path("id"));

    }
}
