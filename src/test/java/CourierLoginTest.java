import constans.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    ProfileCourier profile;
    Courier courier;

    @Before
    public void setUp(){
        RestAssured.baseURI = Constants.URLFORTESTS;
        profile = new ProfileCourier("QAS1","1q2q3","Aleksey");
        courier = new Courier(profile);
    }

    @Test
    @DisplayName("Log In courier of /api/v1/courier/login")
    @Description("Basic test log in courier for /api/v1/courier/login")
    public void courierLogInCode200(){
        courier.createCourier();
        courier.logInCourier()
                .then().statusCode(200);

        courier.deleteCourier();
    }

    @Test
    @DisplayName("Log In a courier without a LOGIN of /api/v1/courier/login and checking status code")
    @Description("Log In a courier without a login in the request body, status code 400")
    public void logInCourierWithoutLoginParamCode400(){
        profile.setLogin(null);

        courier.logInCourier()
                .then().statusCode(400);
    }

    //Проблемный тест, завершается по таймауту с 504 кодом.
    @Test
    @DisplayName("Log In a courier without a PASSWORD of /api/v1/courier/login and checking status code")
    @Description("Log In a courier without a password in the request body, status code 400")
    public void logInCourierWithoutPassParamCode400(){
        profile.setPassword(null);

        courier.logInCourier()
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Log In a courier with incorrect LOGIN of /api/v1/courier/login")
    @Description("Log In a courier with incorrect login and checking the error code 404|text \"Учетная запись не найдена\"")
    public void logInCourWithIncorrectLoginCode404AndErrorTextInResponse(){
        profile.setLogin("QAS2");

        courier.logInCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Log In a courier with incorrect PASSWORD of /api/v1/courier/login")
    @Description("Log In a courier with incorrect password and checking the error code 404|text \"Учетная запись не найдена\"")
    public void logInCourWithIncorrectPassCode404AndErrorTextInResponse(){
        courier.createCourier();
        profile.setPassword("incorrect");

        courier.logInCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

        courier.deleteCourier();
    }

    @Test
    @DisplayName("Log In a courier without a required parameter of /api/v1/courier/login and checking error text")
    @Description("Log In a courier without a required parameter and checking the error text \"Недостаточно данных для входа\"|code 400")
    public void logInCourWithoutOneParamErrorTextAndCodeInResponse(){
        profile.setLogin(null);

        courier.logInCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Log In a Non Existent courier of /api/v1/courier/login")
    @Description("Log In a Non Existent courier and checking the error text \"Учетная запись не найдена\"|code 404|id = null")
    public void logInNonExistentCourierErrorTextAndCodeInResponse(){
        profile.setLogin("MeNonExists");

        courier.logInCourier()
                .then().assertThat().body("id", equalTo(null))
                .and()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Test
    @DisplayName("Checking return ID of /api/v1/courier/login")
    @Description("Checking the return of the ID after successful logging. for /api/v1/courier/login")
    public void courierLogInReturnIdAndCode200(){
        courier.createCourier();

        courier.logInCourier()
                .then().assertThat().body("id", notNullValue());

        courier.deleteCourier();
    }

}
