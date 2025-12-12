import constans.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreationTest {

    ProfileCourier profile;
    Courier courier;

    @Before
    public void setUp(){
        RestAssured.baseURI = Constants.URLFORTESTS;
        profile = new ProfileCourier("QAS","1q2q3","Aleksey");
        courier = new Courier(profile);
    }

    @Test
    @DisplayName("Create new courier of /api/v1/courier")
    @Description("Basic test for /api/v1/courier")
    public void createNewCourierRespBodyOkTrue(){
        courier.createCourier()
                .then()
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Creating a courier of /api/v1/courier and checking the response body")
    @Description("Checking the response body \"{\"ok\": true}\" after creating a new courier")
    public void createNewCourierOkTrueInResponse(){
        courier.createCourier()
                .then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Creating identical courier of /api/v1/courier")
    @Description("Checking the error message text \"Этот логин уже используется\"|code 409 when creating a courier with an identical login")
    public void createIdenticalCourCode409For2IterAndErrMessage(){
        courier.createCourier();
        courier.createCourier()
                .then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and().statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Creating a courier without a password of /api/v1/courier and checking status code")
    @Description("Creating a courier without a password in the request body and checking the error text \"Недостаточно данных для создания учетной записи\" in response body")
    public void createNewCourierWithoutPassParamErrorResponseMessage(){
        profile.setPassword(null);

        courier.createCourier()
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Creating a courier without a login of /api/v1/courier")
    @Description("Creating a courier without a login in the request body and checking the error text \"Недостаточно данных для создания учетной записи\" in response body")
    public void createNewCourierWithoutLoginParamErrorResponseMessage(){
        profile.setLogin(null);

        courier.createCourier()
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Creating a courier without a firstName of /api/v1/courier")
    @Description("Creating a courier without a firstName in the request body and checking the error text \"Недостаточно данных для создания учетной записи\" in response body")
    public void createNewCourierWithoutFirstNameErrorResponseMessage(){
        profile.setFirstName(null);

        courier.createCourier()
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Creating courier without a required parameter of /api/v1/courier and checking error text")
    @Description("Creating a courier without a required parameter and checking the error text \"Недостаточно данных для создания учетной записи\"|code 400")
    public void crNewCourWithoutOneParamErTextAndCodeInResponse(){
        profile.setLogin(null);

        courier.createCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(SC_BAD_REQUEST);
    }

    @After
    public void delCourier(){
        if (profile.getLogin() != null
                && profile.getPassword() != null){
            courier.deleteCourier();
        }
    }
}
