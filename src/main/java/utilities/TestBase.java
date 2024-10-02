package utilities;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class TestBase {

    public static String favsTableName = "userfavorites";
    public static SoftAssert softAssert = new SoftAssert();
    public static WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    protected static Connection connection;
    protected static Statement st;
    protected static ResultSet rs;
    protected static PreparedStatement ps;
    public final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZmRmNjk0OGI5NWRkM2ZlMjIwYTM3ODIwNTAzNWZjYiIsIm5iZiI6MTcyNjgxNzAwNi4yMDI1MTEsInN1YiI6IjY2ZWMzYzkxOWJkNDI1MDQzMDc0YjUyZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cO9cduuFrnxq_m885-KeHxTjr1vT6YklpGZ7Ap4KZCg";
    public final int accountId = 21527979;
    public final String api_key = "bfdf6948b95dd3fe220a378205035fcb";

    @BeforeClass
    public void setUp() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/TMDBTestDatabase";
            String user = "postgres";
            String password = "1234567"; //ALTER USER postgres WITH PASSWORD 'yeni sifre'; sifre degistirmek icin
            connection = DriverManager.getConnection(url, user, password);
            st = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() throws Exception {

        if (connection != null) { //-->Connection dolu ise kapatacak
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @BeforeTest
        public static void setupAllureResultsDirectory() {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            System.setProperty("allure.results.directory", STR."target/allure-results/Result-\{timestamp}");
        }
}