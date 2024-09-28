package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class TMDBPage {
    public TMDBPage () {
        PageFactory.initElements(Driver.getDriver(), this);}

    @FindBy (xpath = "//div/div[2]/ul/li[3]/a")
    public WebElement giris;

    @FindBy (id = "username")
    public WebElement userName;

    @FindBy (id = "password")
    public WebElement password;

    @FindBy (xpath = "(//h2/a)[1]")
    public WebElement accountHeader;

    @FindBy (xpath = "//*[@id='new_shortcut_bar']/li[3]/a")
    public WebElement accountLists;

    @FindBy (xpath = "//*[@id='new_shortcut_bar']/li[4]/div/ul/li[1]/a")
    public WebElement ratedMovies;

    @FindBy (xpath = "//*[@id='new_shortcut_bar']/li[4]/div/ul/li[2]/a")
    public WebElement ratedSeries;

    @FindBy (xpath = "//section/div[1]/div/section[1]/p[1]")
    public WebElement moviePageOrgTitle;

    @FindBy (xpath = "//*[@id='onetrust-accept-btn-handler']")
    public WebElement cookie;

    @FindBy (xpath = "//*[@id='allow_authentication']")
    public WebElement allowAuth;

    @FindBy (xpath = "//*[@id='main']/section/h2")
    public WebElement allowed;



















}
