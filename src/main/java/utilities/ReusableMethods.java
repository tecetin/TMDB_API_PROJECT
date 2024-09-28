package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ReusableMethods {
    private static final Random random = new Random();
    protected static ExtentReports extentReport;//-->raporlamayı başlatır
    protected static ExtentSparkReporter extentSparkReporter;//-->Html formatında rapor oluşturur
    protected static ExtentTest extentTest;
    private static WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

    public static List<String> toStringList(List<WebElement> webElementList) {
        List<String> stringListe = new ArrayList<>();
        for (WebElement each : webElementList) {
            stringListe.add(each.getText());
        }
        return stringListe;
    }

    //HARD WAIT METHOD
    public static void bekle(int saniye) {
        try {
            Thread.sleep(saniye * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Alert ACCEPT
    public static void alertAccept() {
        Driver.getDriver().switchTo().alert().accept();
    }

    //Alert DISMISS
    public static void alertDismiss() {
        Driver.getDriver().switchTo().alert().dismiss();
    }

    //Alert getText()
    public static void alertText() {
        Driver.getDriver().switchTo().alert().getText();
    }

    //Alert promptBox
    public static void alertprompt(String text) {
        Driver.getDriver().switchTo().alert().sendKeys(text);
    }

    public static void ddmVisibleText(WebElement ddm, String secenek) {
        Select select = new Select(ddm);
        select.selectByVisibleText(secenek);
    }

    //DropDown Index
    public static void ddmIndex(WebElement ddm, int index) {
        Select select = new Select(ddm);
        select.selectByIndex(index);
    }

    //DropDown Value
    public static void ddmValue(WebElement ddm, String secenek) {
        Select select = new Select(ddm);
        select.selectByValue(secenek);
    }

    //SwitchToWindow1
    public static void switchToWindow(int sayi) {
        List<String> tumWindowHandles = new ArrayList<String>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(tumWindowHandles.get(sayi));
    }

    //EXPLICIT WAIT METHODS

    //SwitchToWindow2
    public static void window(int sayi) {
        Driver.getDriver().switchTo().window(Driver.getDriver().getWindowHandles().toArray()[sayi].toString());
    }

    //Visible Wait
    public static void visibleWait(WebElement element, int sayi) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(sayi));
        wait.until(ExpectedConditions.visibilityOf(element));

    }

    //VisibleElementLocator Wait
    public static WebElement visibleWait(By locator, int sayi) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(sayi));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    //Alert Wait
    public static void alertWait(int sayi) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(sayi));
        wait.until(ExpectedConditions.alertIsPresent());

    }

    //Tüm Sayfa ScreenShot
    public static void sayfaSS(String screenshotIsmi) {

        //Sayfanın yüklenmesini bekle
        Driver.getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        // 1.adim TakesScreenShot objesi olusturun
        TakesScreenshot tss = (TakesScreenshot) Driver.getDriver();

        File geciciDosya = tss.getScreenshotAs(OutputType.FILE);

        //raporlara tarih etiketi ekleyelim
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("_dd_MM_yy-HH_mm");
        String tarihEtiketi = ldt.format(format1);

        screenshotIsmi = screenshotIsmi.replaceAll("\\s", "");

        String path = System.getProperty("user.dir") + "/target/Screenshots/"
                + screenshotIsmi + tarihEtiketi + ".png";

        File dosya = new File(path);

        // Gecici dosyayi asil kaydetmek istedigimiz dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya, dosya);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String sayfaSSBase64() {

        return ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BASE64);
    }

    public static String WEResmiBase64(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getScreenshotAs(OutputType.BASE64);
    }

    //WebElement ScreenShot
    public static void WEResmi(WebElement element) {

        //Elementin görünmesini bekle
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(element));

        String tarih = new SimpleDateFormat("_dd_MM_yy_HH_mm").format(new Date());
        String dosyaYolu = "TestOutput/screenshot/webElementScreenshot" + tarih + ".png";

        try {
            FileUtils.copyFile(element.getScreenshotAs(OutputType.FILE), new File(dosyaYolu));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //WebTable
    public static void printData(int satir, int sutun) {
        WebElement satirSutun = Driver.getDriver().findElement(By.xpath("(//tbody)[1]//tr[" + satir + "]//td[" + sutun + "]"));
        System.out.println(satirSutun.getText());
    }

    //Click Method
    public static void click(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript("arguments[0].click();", element);
        }
    }

    //JS Scroll
    public static void scroll(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    //JS Sayfa Sonu Scroll
    public static void scrollEnd() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }

    //JS Sayfa Başı Scroll
    public static void scrollHome() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollTo(0,-document.body.scrollHeight)");
    }

    //JS SendKeys
    public static void sendKeysJS(WebElement element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].value='" + text + "'", element);
    }

    //JS SetAttributeValue
    public static void setAttributeJS(WebElement element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].setAttribute('value','" + text + "')", element);
    }

    //JS GetAttributeValue
    public static void getValueByJS(String id, String attributeName) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        String attribute_Value = (String) js.executeScript("return document.getElementById('" + id + "')." + attributeName);
        System.out.println("Attribute Value: = " + attribute_Value);
    }

    //File Upload Robot Class
    public static void uploadFile(String dosyaYolu) {
        try {
            bekle(3);
            StringSelection stringSelection = new StringSelection(dosyaYolu);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.delay(3000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(3000);
        } catch (Exception ignored) {

        }
    }

    public static List<Integer> rastgeleSayilar(int min, int max, int count) {
        if (count > (Math.abs(min - max) + 1)) {
            count = Math.abs(min - max) + 1;
        }

        // Eğer min, max'tan büyükse, yer değiştirme yap
        if (min > max) {
            int temp = max;
            max = min;
            min = temp;
        }

        // Sayı aralığını bir listeye ekle
        List<Integer> sayilar = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            sayilar.add(i);
        }

        // Listeyi karıştır ve istenilen sayıda sayı döndür
        Collections.shuffle(sayilar); //Öncelikle belirli bir aralıktaki tüm sayılar listeye eklenir, sonra liste karıştırılır ve istenilen sayıda ilk öğe seçilir.
        return sayilar.subList(0, count);
    }

    public static Set<Integer> rastgeleSayilarRandomClass(int min, int max, int count) {
        if (count > (Math.abs(min - max) + 1)) {
            throw new IllegalArgumentException("İstenilen sayı adedi, aralıktaki benzersiz sayıların sayısından fazla.");
        }

        // Eğer min, max'tan büyükse, yer değiştirme yap
        if (min > max) {
            int temp = max;
            max = min;
            min = temp;
        }
        Set<Integer> benzersizSayilar = new HashSet<>();

        while (benzersizSayilar.size() < count) {
            int sayi = random.nextInt((max - min) + 1) + min;
            benzersizSayilar.add(sayi);
        }
        return benzersizSayilar;
    }


}
