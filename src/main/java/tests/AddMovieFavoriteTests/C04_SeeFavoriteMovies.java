//package tests.AddMovieFavoriteTests;
//
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.testng.annotations.Test;
//import utilities.TestBase;
//
//import java.io.IOException;
//
//public class C03_SeeFavoriteMovies extends TestBase {
//
//
//    @Test
//    public void test01() throws IOException {
//
//        //GET https://api.themoviedb.org/3/account/{account_id}/favorite/movies
//        RestAssured.baseURI = "https://api.themoviedb.org";
//
//        Response response = RestAssured
//                .given()
//                .pathParam("account_id", accountId)
//                .header("Authorization", token)
//                .when()
//                .get("/3/account/{account_id}/favorite/movies")
//                .thenReturn();
//
//
//        Sheet sheet = excelUtility.excelDosyasiniAc("Account Details", "Favourite Movies");
//
//        // Gelen response'u JSON formatına çevirelim
//        JSONObject jsonResponse = new JSONObject(response.asString());
//        JSONArray results = jsonResponse.getJSONArray("results");
//
//        // Gelen JSON verisini Excel dosyasına yazıyoruz
//        for (int i = 0; i < results.length(); i++) {
//            JSONObject favMovies = results.getJSONObject(i);
//            int num = i+1;
//            int id = favMovies.getInt("id");
//            String orgTitle = favMovies.getString("original_title");
//            String title = favMovies.getString("title");
//            String overview = favMovies.getString("overview");
//            double voteAverage = favMovies.getDouble("vote_average");
//
//            // Yeni bir satır ekliyoruz ve verileri dolduruyoruz
//            Row newRow = sheet.createRow(+ 1 + i);  // Son satırdan başla ve eklemeye devam et
//            newRow.createCell(0).setCellValue(num);
//            newRow.createCell(1).setCellValue(id);
//            newRow.createCell(2).setCellValue(orgTitle);
//            newRow.createCell(3).setCellValue(title);
//            newRow.createCell(4).setCellValue(overview);
//            newRow.createCell(5).setCellValue(voteAverage);
//        }
//
//        // Dosyayı kapatıyoruz ve kaydediyoruz
//        excelUtility.excelDosyasiniKapat();
//
//    }
//}
