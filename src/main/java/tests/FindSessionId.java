package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import tests.AddMovieFavoriteTests.C01_KullaniciGirisi;
import utilities.Driver;
import utilities.TestBase;

public class FindSessionId extends TestBase {

    public String createSessionId() { /*Kullanıcı girişi yapılmadan çalışmaz*/

        String session_id = "";

        //GET https://api.themoviedb.org/3/authentication/token/new
        RestAssured.baseURI = "https://api.themoviedb.org";

        Response response = RestAssured
                .given()
                .header("Authorization", token)
                .when()
                .get("/3/authentication/token/new")
                .thenReturn();

        //Token alabildiğimizi doğrulayalım
        response.then()
                .assertThat()
                .statusCode(200);

        //Aldığımız token 60 dakika geçerlidir. Kaydedelim
        JSONObject jsonResponse = new JSONObject(response.asString());
        String request_token = jsonResponse.getString("request_token");

        System.out.println("request token: " +request_token);

        //Get the user to authorize the request token
        String authorizeToken = "https://www.themoviedb.org/authenticate/" + request_token;

        Driver.getDriver().get(authorizeToken);
        page.allowAuth.click();

        wait.until(ExpectedConditions.visibilityOf(page.allowed));
        String expTitle = "Kimlik Doğrulaması Onaylandı";

        Assert.assertEquals(page.allowed.getText(), expTitle);

        //Create a new session id with the athorized request token
        //POST https://api.themoviedb.org/3/authentication/session/new
        JSONObject requestBody = new JSONObject();
        requestBody.put("request_token", request_token);
        response = RestAssured
                .given()
                .header("Authorization", token)
                .contentType("application/json")  // İçeriğin JSON formatında olduğunu belirtiyoruz
                .body(requestBody.toString())  // request_token'ı JSON formatında gönderiyoruz
                .when()
                .post("/3/authentication/session/new")
                .thenReturn();

        //Token alabildiğimizi doğrulayalım
        response.then()
                .assertThat()
                .statusCode(200);

        //Aldığımız token 60 dakika geçerlidir. Kaydedelim
        jsonResponse = new JSONObject(response.asString());
        session_id = jsonResponse.getString("session_id");

        System.out.println("session id: " + session_id);

        return session_id;
    }
}
