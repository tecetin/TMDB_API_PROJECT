package tests.AddMovieFavoriteTests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.TestBase;

import static org.hamcrest.Matchers.equalTo;

public class C02_GetUserDetails extends TestBase {

    @Test
    public void test01() {

        //Getting user Details
        //Required token and account_id --> GET https://api.themoviedb.org/3/account/{account_id}
        //Give authorization with header
        RestAssured.baseURI = "https://api.themoviedb.org";

        Response response = RestAssured
                .given()
                    .pathParam("account_id", accountId)
                    .header("Authorization", token)
                .when()
                    .get("/3/account/{account_id}")
                .thenReturn();

        //Assert if details are correct
        response.then()
                .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("id", equalTo(accountId),
                            "name", equalTo(ConfigReader.getProperty("name")),
                            "username", equalTo(ConfigReader.getProperty("userName"))
                    );
    }
}
