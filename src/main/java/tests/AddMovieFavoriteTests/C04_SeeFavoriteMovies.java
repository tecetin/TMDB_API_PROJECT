package tests.AddMovieFavoriteTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.TestBase;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class C04_SeeFavoriteMovies extends TestBase {

    @Test
    public void favMoviesAccount() throws SQLException {

        C01_KullaniciGirisi giris = new C01_KullaniciGirisi();
        giris.logIn();

        //GET https://api.themoviedb.org/3/account/{account_id}/favorite/moviesDB
        List<String> moviesAccount = movieListFromAPI();

        String query = "SELECT original_title FROM " + favsTableName + " WHERE media_type = 'movie';";
        List<String> moviesDB = movieListFromDB(query);

        // Boyut kontrolü
        boolean isSizeMismatch = false;
        try {
            Assert.assertEquals(moviesDB.size(), moviesAccount.size(), "Film sayıları eşleşmiyor.");
        } catch (AssertionError e) {
            System.out.println("Boyut kontrolünde hata: " + e.getMessage());
            isSizeMismatch = true;
        }

        // Eğer boyutlar eşleşmiyorsa favori filmleri güncelle
        if (isSizeMismatch) {
            C03_AddFavorite addFavorite = new C03_AddFavorite();
            addFavorite.addFavorites();

            // API ve veritabanı verilerini tekrar al
            moviesAccount = movieListFromAPI();
            moviesDB = movieListFromDB(query);
            // Tekrar boyut kontrolü
            try {
                Assert.assertEquals(moviesDB.size(), moviesAccount.size(), "Film sayıları güncellemeden sonra da eşleşmiyor.");
            } catch (AssertionError e) {
                System.out.println("Boyut kontrolü güncellemeden sonra da başarısız: " + e.getMessage());
                throw e;
            }
        }

        // İçerik kontrolü
        for (String movie : moviesDB) {
            Assert.assertTrue(moviesAccount.contains(movie), "API'de bulunmayan film: " + movie);
        }

    }

    private List<String> movieListFromAPI() {

        RestAssured.baseURI = "https://api.themoviedb.org";

        Response response = RestAssured
                .given()
                .pathParam("account_id", accountId)
                .header("Authorization", token)
                .when()
                .get("/3/account/{account_id}/favorite/movies")
                .thenReturn();

        JSONObject jsonResponse = new JSONObject(response.asString());
        JSONArray results = jsonResponse.getJSONArray("results");

        List<String> moviesAccount = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject moviesApi = results.getJSONObject(i);
            moviesAccount.add(moviesApi.getString("original_title"));
        }
        return moviesAccount;
    }

    private List<String> movieListFromDB(String query) throws SQLException {
        ResultSet moviesDB = st.executeQuery(query);
        List<String> movies = new ArrayList<>();
        while (moviesDB.next()) {
            movies.add(moviesDB.getString("original_title"));
        }
        return movies;
    }


}
