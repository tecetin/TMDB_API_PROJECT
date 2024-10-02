package utilities;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.TMDBPage;

import java.sql.*;
import java.time.Duration;

public class TestBase {

    public final String token = "";
    public final int accountId = 0;
    public final String api_key = "";
    public static String favsTableName = "userfavorites";
    public static SoftAssert softAssert = new SoftAssert();
    public static WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    protected static Connection connection;
    protected static Statement st;
    protected static ResultSet rs;
    protected static PreparedStatement ps;

    @BeforeClass
    public void setUp() {
        try {
            Class.forName("org.postgresql.Driver");
            String url ="jdbc:postgresql://localhost:5432/TMDBTestDatabase";
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

        if (connection!=null){ //-->Connection dolu ise kapatacak
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        Driver.quitDriver();

    }

}