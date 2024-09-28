package tests.AddMovieFavoriteTests;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import utilities.ExcelUtility;
import utilities.TestBase;

import java.io.IOException;

public class MovieList extends TestBase {

    @Test
    public void test01() throws IOException {

        //GET https://api.themoviedb.org/3/discover/movie

        RestAssured.baseURI = "https://api.themoviedb.org";

        //You can query this method up to 14 days at a time.
        // Use the start_date and end_date query parameters. 100 items are returned per page.
        Response response = RestAssured
                .given()
                    .queryParam("primary_release_year", 2024)
                    .queryParam("sort_by", "vote_average.desc")
                    .queryParam("page", 1)
                .header("Authorization", token)
                .when()
                    .get("/3/discover/movie")
                .thenReturn();

        //Assert if details are correct
        response.then()
                .assertThat()
                .statusCode(200);

        // Gelen response'u JSON formatına çevirelim
        JSONObject jsonResponse = new JSONObject(response.asString());
        JSONArray results = jsonResponse.getJSONArray("results");

        int totalPages = jsonResponse.getInt("total_pages");
        int totalResult = jsonResponse.getInt("total_results");
        System.out.println("Toplam sonuc sayisi : " + totalResult);
        System.out.println("Toplam sayfa sayisi : " + totalPages);

        // Var olan Excel dosyasını açıyoruz ve Sheet'i alıyoruz
        ExcelUtility excelUtility = new ExcelUtility();
        Sheet sheet = excelUtility.excelDosyasiniAc("Movie Details", "Movie Details");

        // Mevcut satır sayısını alıyoruz
        int lastRowNum = sheet.getLastRowNum();

        // Gelen JSON verisini Excel dosyasına yazıyoruz
        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            String orgTitle = movie.getString("original_title");
            String title = movie.getString("title");
            String overview = movie.getString("overview");
            double voteAverage = movie.getDouble("vote_average");

            // Yeni bir satır ekliyoruz ve verileri dolduruyoruz
            Row newRow = sheet.createRow(lastRowNum + 1 + i);  // Son satırdan başla ve eklemeye devam et
            newRow.createCell(0).setCellValue(orgTitle);
            newRow.createCell(1).setCellValue(title);
            newRow.createCell(2).setCellValue(overview);
            newRow.createCell(3).setCellValue(voteAverage);
        }

        // Dosyayı kapatıyoruz ve kaydediyoruz
        excelUtility.excelDosyasiniKapat();

    }
}
