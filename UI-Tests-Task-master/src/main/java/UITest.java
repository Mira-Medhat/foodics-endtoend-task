import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;


public class UITest {
    static ChromeOptions options = new ChromeOptions();
    public static WebDriver driver=new ChromeDriver(options);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    String mobileNumber="01226279679";
    String password="Mira@123";


    @BeforeTest
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver.get("https://www.amazon.com/");
    }

    public void login(String mobileNumber , String password){

        WebElement loginElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-link-accountList")));
        loginElement.click();
        WebElement signInElement =  wait.until(ExpectedConditions.elementToBeClickable(By.id("ap_email")));
        signInElement.click();
        signInElement.sendKeys(mobileNumber);
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        continueBtn.click();
        WebElement passwordElement =  wait.until(ExpectedConditions.elementToBeClickable(By.id("ap_password")));
        passwordElement.click();
        passwordElement.sendKeys(password);
        WebElement signInBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("signInSubmit")));
        signInBtn.click();
    }

    @Test(priority=0)
    public void addProductsToCart() {

        login(mobileNumber,password);
        WebElement burgerMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-hamburger-menu")));
        burgerMenu.click();
        WebElement seeAll =  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[2]/div/ul[1]/li[11]/a[1]")));
        seeAll.click();
        WebElement videoGamesTab = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-ref-tag='nav_em_1_1_1_27']")));
        videoGamesTab.click();
        WebElement allVideoGames = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='hmenu-content']/ul[26]/li[3]/a")));
        allVideoGames.click();
        WebElement checkBox =  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='s-refinements']/div[8]/ul/li[1]/span/a/div")));
        checkBox.click();
        WebElement sortBy = wait.until(ExpectedConditions.elementToBeClickable(By.id("s-result-sort-select")));
        Select sortByPrice = new Select(driver.findElement(By.id("s-result-sort-select")));
        sortByPrice.selectByVisibleText("Price: High to Low");
        WebElement lowPrice =  wait.until(ExpectedConditions.elementToBeClickable(By.id("low-price")));
        lowPrice.sendKeys("0");
        WebElement highPrice =  wait.until(ExpectedConditions.elementToBeClickable(By.id("high-price")));
        highPrice.sendKeys("15");
        WebElement goBtn =  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='a-list-item']  [class='a-button-inner']")));
        goBtn.click();

        for (int i=1 ; i<2;i++){
            WebElement product  = driver.findElement(By.cssSelector("div[cel_widget_id='MAIN-SEARCH_RESULTS-"+i+"'] [class='a-price-whole']"));
            Integer  productAmount = Integer.parseInt(product.getText());
            Integer total=0;
            if(productAmount<15){
                total=total+productAmount;
                System.out.println(i);
                System.out.println(productAmount);
                driver.findElement(By.cssSelector("div[cel_widget_id='MAIN-SEARCH_RESULTS-"+i+"'] [class='sg-col-inner']")).click();
                WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
                addToCart.click();
                WebElement proceedToCheckOut = wait.until(ExpectedConditions.elementToBeClickable(By.name("proceedToRetailCheckout")));
                proceedToCheckOut.click();
            }


        }
        driver.findElement(By.cssSelector("[class='s-pagination-item s-pagination-next s-pagination-button s-pagination-separator']")).click();




    }
    @AfterTest
    public void quit()
    {
        driver.quit();
    }

}


