package tests;

import org.testng.annotations.Test;
import utilities.TestBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class personalDataBase extends TestBase{

    public String tabloOlustur(String tabloAdi, String[][] columns) throws SQLException {

        tabloAdi = tabloAdi.replaceAll(" ", "");

        StringBuilder createQuery = new StringBuilder("CREATE TABLE " + tabloAdi + " (");

        for (int i = 0; i < columns.length; i++) {
            createQuery.append(columns[i][0]) // Sütun adı
                    .append(" ")
                    .append(columns[i][1]); // Sütun veri tipi

            if (i < columns.length - 1) {
                createQuery.append(", ");
            }
        }
        createQuery.append(")");

        // Sorguyu çalıştır
        st.execute(createQuery.toString());
        System.out.println("Tablo " + tabloAdi + " başarıyla oluşturuldu.");

        return tabloAdi;
    }
    public void veriEkleDB(String tableName, String[] columns, List<String[]> valuesList) throws SQLException {

        tableName = tableName.replaceAll(" ", "");
        StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");

        for (int i = 0; i < columns.length; i++) {
            insertQuery.append(columns[i]);
            if (i < columns.length - 1) {
                insertQuery.append(", ");
            } else {
                insertQuery.append(")");
            }
        }

        insertQuery.append(" VALUES ");

        String query = insertQuery.toString() + "(?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (int i = 0; i < valuesList.size(); i++) {
                String[] degerler = valuesList.get(i);

                ps.setString(1, degerler[0]); // language
                ps.setString(2, degerler[1]); // title
                ps.setDouble(3, Double.parseDouble(degerler[2])); // My_Rate
                ps.setString(4, degerler[3]); //media_type
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
    public void tabloKontrol(String tableName) throws SQLException {

        tableName = tableName.toLowerCase();
        // Tablonun olup olmadığını kontrol et, yoksa oluştur
        DatabaseMetaData dbMetaData = connection.getMetaData();
        rs = dbMetaData.getTables(null, null, tableName, null);

        boolean tableExists = rs.next();

        if (tableExists) {
            System.out.println(tableName + " tablosu mevcuttur.");

        } else { // Tablonun var olmadığı durumda
            System.out.println(tableName + " tablosu mevcut değil.");

            favsTable();  // Tabloyu oluştur
            addfavMovies(); // Tabloya film isimlerini ekle
            addFavSeries(); // Tabloya dizi isimlerini ekle
        }
    }
    public void favsTable() throws SQLException {

        //Database'de tablo olusturalim
        String[][] createColumns = {
                {"no", "INTEGER DEFAULT 0"},
                {"id", "SERIAL"},  // Otomatik artan ID
                {"original_language", "varchar(7) DEFAULT 'Unknown'"},
                {"Original_Title", "varchar(250) DEFAULT 'Unknown'"},
                {"Title", "varchar(250) DEFAULT 'Untitled'"},
                {"Overview", "TEXT DEFAULT 'No description available'"},
                {"My_Rate", "NUMERIC(4,2) DEFAULT 0.0"},
                {"Vote_Average", "NUMERIC(4,2) DEFAULT 0.0"},
                {"media_type", "varchar(20) DEFAULT 'Unknown'"},
                {"CONSTRAINT movie_id", "PRIMARY KEY(id)"}
        };

        favsTableName = tabloOlustur(favsTableName, createColumns);
    }
    public void addfavMovies() throws SQLException {
        //Tablodaki bizim girecegimiz verileri girelim
        String[] sutunlar = {"original_language", "Title", "My_Rate", "media_type"};

        List<String[]> sutunDegerleri = new ArrayList<>(Arrays.asList(
                new String[]{"fr", "Amelie", "10", "movie"},
                new String[]{"en", "Charlie'nin Çikolata Fabrikası", "9.8", "movie"},
                new String[]{"en", "Yukarı Bak", "10", "movie"},
                new String[]{"en", "İçime Şeytan Kaçtı", "10", "movie"}
        ));

        veriEkleDB(favsTableName, sutunlar, sutunDegerleri);
    }
    public void addFavSeries() throws SQLException {
        //Tablodaki bizim girecegimiz verileri girelim
        String[] sutunlar = {"original_language", "Title", "My_Rate", "media_type"};

        List<String[]> sutunDegerleri = new ArrayList<>(Arrays.asList(
                new String[]{"en", "Game of Thrones", "9.3", "tv"},
                new String[]{"ja", "Attack on Titan", "9.5", "tv"},
                new String[]{"en", "Mindhunter", "10", "tv"}
        ));

        veriEkleDB(favsTableName, sutunlar, sutunDegerleri);
    }

    @Test
    public void tablo() throws SQLException {

        tabloKontrol(favsTableName);

    }
}

