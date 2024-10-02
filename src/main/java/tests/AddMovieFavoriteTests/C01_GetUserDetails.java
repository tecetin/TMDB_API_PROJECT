package tests.AddMovieFavoriteTests;


import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBase;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
@Epic("Epic-01")
@Feature("Kullanici Bilgilerini Doğrulama")
public class C01_GetUserDetails extends TestBase {

    @Test (description = "kullanici bilgileri api testi")

    @Description("TMDB API ile Kullanici Bilgilerini Doğrulama")
    @Severity(SeverityLevel.NORMAL)
    @AllureId("T01")
    public void test01() {

        //Getting user Details
        //Required token and account_id --> GET https://api.themoviedb.org/3/account/{account_id}
        //Give authorization with header
        RestAssured.baseURI = "https://api.themoviedb.org";

        Response response = RestAssured
                .given().filter(new AllureRestAssured())
                    .pathParam("account_id", accountId)
                    .header("Authorization", token)
                .when()
                    .get("/3/account/{account_id}")
                .thenReturn();

        //Assert if details are correct
        response.then()
                .assertThat()
                    .statusCode(300)
                    .contentType(ContentType.JSON)
                    .body("id", equalTo(accountId),
                            "name", equalTo(ConfigReader.getProperty("name")),
                            "username", equalTo(ConfigReader.getProperty("userName"))
                    );

        Driver.quitDriver();
    }
}
