package tests.AddMovieFavoriteTests;


import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBase;

import java.time.Duration;


public class C01_KullaniciGirisi extends TestBase {

    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

    @Test
    public void logIn() {

        //TMDM anasayfaya git, sayfada oldugunu dogrula
        Driver.getDriver().get(ConfigReader.getProperty("mainPageUrl"));

        String expHead = "TMDB";
        String actHead = Driver.getDriver().getTitle();
        softAssert.assertTrue(actHead.contains(expHead));

        //Giriş'e tikla
        page.giris.click();

        //Gecerli kullanici adi ve password ile basarili giris yapildigini dogrula
        wait.until(ExpectedConditions.elementToBeClickable(page.userName));

        page.userName.sendKeys(ConfigReader.getProperty("userName").toLowerCase() + Keys.TAB);
        page.password.sendKeys(ConfigReader.getProperty("password") + Keys.TAB + Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOf(page.accountHeader));

        String expUrl = "https://www.themoviedb.org/u/" + ConfigReader.getProperty("userName");
        String actUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertEquals(actUrl, expUrl);

    }
}
