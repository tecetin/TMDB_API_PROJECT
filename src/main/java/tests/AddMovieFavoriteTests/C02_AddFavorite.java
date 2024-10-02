package tests.AddMovieFavoriteTests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;
import tests.FindSessionId;
import tests.KullaniciGirisi;
import tests.personalDataBase;
import utilities.Driver;
import utilities.TestBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Epic("Epic-02")
@Feature("Kullanici Profilinde Favorilere Ekleme")
public class C02_AddFavorite extends TestBase {

    static String session_id = "";

    //method
    public List<Integer> mediaDetailsBul() throws SQLException {

        int icerikSayisi = DBfilmSayisi(favsTableName);
        List<Integer> ids = new ArrayList<>();
        String query;

        for (int i = 0; i < icerikSayisi; i++) {

            //döngü sonunda idler değiştiği için tüm filmleri alamıyor, her seferinde Defaultu 0 olan noya göre sıralanacak -asc
            //Sıranın en başındaki satır alınır
            query = "SELECT no, id, original_language, title, media_type FROM " + favsTableName + " ORDER BY no, id LIMIT 1 OFFSET 0;";
            String titleDB = "";
            String languageDB = "";
            String mediatypeDB = "";
            int idDB = 0;
            int no = 0;

            ResultSet mediaDetails = st.executeQuery(query);
            if (mediaDetails.next()) {
                no = mediaDetails.getInt("no");
                idDB = mediaDetails.getInt("id");
                titleDB = mediaDetails.getString("title");
                languageDB = mediaDetails.getString("original_language");
                mediatypeDB = mediaDetails.getString("media_type");

            } else {
                System.out.println("No results found.");
            }

            //Daha önceden bilgileri alinana filmleri tekrar loopa sokmadan gerekenler ekleniyor
            if (no == 0) {

                //GET https://api.themoviedb.org/3/search/movie query->Film language-> tr-TR ismi ile filmlerin detaylari listelenebiliyor
                RestAssured.baseURI = "https://api.themoviedb.org";
                String endpoint = "";

                if (mediatypeDB.equalsIgnoreCase("movie")) {
                    endpoint = "/3/search/movie";
                } else {
                    endpoint = "/3/search/tv";
                }
                Response response = RestAssured
                        .given()
                        .header("Authorization", token)
                        .queryParam("query", titleDB)
                        .queryParam("language", "tr-TR") //Database isimleri türkçe yazıldığı için tr olarak aratılacak
                        .when()
                        .get(endpoint)
                        .thenReturn();

                // Gelen response'u JSON formatına çevirelim
                JSONObject jsonResponse = new JSONObject(response.asString());
                JSONArray results = jsonResponse.getJSONArray("results");

                //Aradığımız filmi APIdan gelen listede bulalım
                String titleAPI = "";
                String originalTitleAPI = "";
                String languageAPI = "";
                int idAPI = 0;
                String overviewAPI = "";
                double voteAvg = 0;

                try {
                    for (int j = 0; j < results.length(); j++) {
                        JSONObject mediaDetailsAPI = results.getJSONObject(j);

                        if (mediatypeDB.equalsIgnoreCase("movie")) {
                            titleAPI = mediaDetailsAPI.getString("title");
                        } else {
                            titleAPI = mediaDetailsAPI.getString("name");
                        }
                        languageAPI = mediaDetailsAPI.getString("original_language");
                        if (titleAPI.equalsIgnoreCase(titleDB) && (languageAPI.equalsIgnoreCase(languageDB))) {

                            if (mediatypeDB.equalsIgnoreCase("movie")) {
                                originalTitleAPI = mediaDetailsAPI.getString("original_title");
                            } else {
                                originalTitleAPI = mediaDetailsAPI.getString("original_name");
                            }

                            idAPI = mediaDetailsAPI.getInt("id");
                            overviewAPI = mediaDetailsAPI.getString("overview");
                            voteAvg = mediaDetailsAPI.getDouble("vote_average");
                            break;
                        }
                    }

                    // Eğer film bulunamadıysa
                    if (idAPI == 0) {
                        System.out.println("Film bulunamadı: " + titleDB);
                        no = 1000000; //sıralamadan çıkıp devam edebilmesi için
                    }
                } catch (JSONException e) {
                    System.out.println("Birden fazla sonuç mevcut veya başka bir hata oluştu: " + e.getMessage());
                    continue;
                }

                /*Doğrulama gerekmiyor, ekstra yapılmak istenirse açılabilir
                //Filmin sayfasına gidip doğru filmin sayfasında olduğumuzu doğrulayalım
                String formattedName = "-" + originalTitleAPI.toLowerCase().replace(" ", "-");
                String filmSayfasi = "https://www.themoviedb.org/movie/" + idAPI + formattedName; //https://www.themoviedb.org/movie/id-filmin-ismi

                Driver.getDriver().get(filmSayfasi);

                //Sayfada ismi alırken beraberinde Orijinal Başlık yazısı da geldiği için ayırma işlemi yapılıyor
                String[] titlePage = page.moviePageOrgTitle.getText().split("\n");
                String actTitle = titlePage[1].replaceAll(" ", "").toLowerCase();

                //Databasede bulunan isim ile sayfadaki isim aynıysadatabase eksik bilgiler kaydedilir.
                String expTitle = originalTitleAPI.replaceAll(" ", "").toLowerCase();

                if (actTitle.equals(expTitle)) {   */

                //Filmin eksik bilgilerini database'e kaydedelim
                String update = "UPDATE " + favsTableName + " SET no = ?, id = ?, overview = ?, vote_average = ?, original_title = ? WHERE title = ? ";

                try (PreparedStatement ps = connection.prepareStatement(update)) {
                    ps.setInt(1, i + 1);
                    ps.setInt(2, idAPI);
                    ps.setString(3, overviewAPI);
                    ps.setDouble(4, voteAvg);
                    ps.setString(5, originalTitleAPI);
                    ps.setString(6, titleDB);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Error updating database: " + e.getMessage());
                }

                /*     }
                 * */

                ids.add(i, idAPI);
            } else {
                ids.add(idDB); // Eğer film daha önceden detaylandırılmışsa sadece idsi alınacak
            }
        }
        return ids;
    }

    //method
    public int DBfilmSayisi(String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName;
        rs = st.executeQuery(query);
        rs.next(); // İlk satıra git
        // COUNT(*) sonucunu al
        return rs.getInt(1);
    }


    @Test (description = "Kullanici girisi yapilarak database dizi-film verileri favorilere eklenir")
    @Severity(SeverityLevel.CRITICAL)
    //ana test
    public void addFavorites() throws SQLException {

        //Filmi favoriye eklemek için kullanıcı girisi yapmak gerekiyor.
        KullaniciGirisi calistir = new KullaniciGirisi();
        calistir.logIn();

        //Databasede gerekli tablo varlığı kontrol edilir.
        String query = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE table_catalog = 'TMDBTestDatabase' and TABLE_NAME='"+ favsTableName +"'";
        rs = st.executeQuery(query);

        if (!rs.next()){
            personalDataBase test = new personalDataBase();
            test.tablo();
        }

        //POST işlemi için session_id alıyoruz 60 dakika içinde geçerli, tekrar almak için yorumdan çıkarmak gerekli
        FindSessionId test = new FindSessionId();
        session_id = test.createSessionId();

        List<Integer> mediaIDs = mediaDetailsBul();

        for (int id : mediaIDs) {

            //POST https://api.themoviedb.org/3/account/{account_id}/favorite
            //{
            //  "media_type": "movie",
            //  "media_id": 550,
            //  "favorite": true
            //}

            //Database'den media Type çekilir
            query = "SELECT media_type FROM " + favsTableName + " WHERE id = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            // Sorguyu çalıştırıyoruz ve sonucu ResultSet ile alıyoruz
            rs = ps.executeQuery();

            // Tek sonucu alıyoruz
            String mediaType = null;
            if (rs.next()) {  // Sonuç varsa
                mediaType = rs.getString("media_type");  // "media_type" sütunundaki sonucu alıyoruz
            }

            //response body'sini oluşturuyoruz
            JSONObject requestBody = new JSONObject();
            requestBody.put("media_type", mediaType);
            requestBody.put("media_id", id);
            requestBody.put("favorite", true);

            RestAssured.baseURI = "https://api.themoviedb.org";

            Response response = RestAssured
                    .given().filter(new AllureRestAssured())
                    .pathParam("account_id", accountId)
                    .queryParam("session_id", session_id)
                    .header("Authorization", token)
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .body(requestBody.toString())
                    .when()
                    .post("/3/account/{account_id}/favorite")
                    .thenReturn();
        }
        Driver.quitDriver();
    }
}


