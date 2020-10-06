import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


@RunWith(JUnit4.class)
public class MyTraditionalSuite {
    public static boolean isOriginalApp=true;
    public WebDriver driver;
    public final String OriginalAppURL="https://demo.applitools.com/hackathon.html";
    public final String NewAppURL="https://demo.applitools.com/hackathonV2.html";
    public final By errorLocator = By.cssSelector(".alert.alert-warning");

    @BeforeClass
    public static void classSetup(){
        if(null!=System.getProperty("isOriginalApp")){
            isOriginalApp=Boolean.valueOf(System.getProperty("isOriginalApp"));
        }
    }

    @Before
    public void testSetup() {
        driver = new ChromeDriver();
        if(isOriginalApp){
            driver.get(OriginalAppURL);
        }
        else{
            driver.get(NewAppURL);
        }
    }

    @Test
    public void validateLabels() {

        // Assert Text of Login Form
        Assert.assertTrue(driver.findElement(By.cssSelector(".auth-header")).getText().contains("Login Form"));
        Assert.assertTrue(driver.findElement(By.cssSelector(".auth-header")).isDisplayed());

        // Assert Text of UserName Label
        Assert.assertTrue(driver.findElement(By.cssSelector("form > div:nth-child(1) > label")).getText().contains("Username"));
        Assert.assertTrue(driver.findElement(By.cssSelector("form > div:nth-child(1) > label")).isDisplayed());

        // Assert Text of UserName Element
        Assert.assertTrue(driver.findElement(By.cssSelector("#username")).getAttribute("placeholder").contains("Enter your username"));
        Assert.assertTrue(driver.findElement(By.cssSelector("#username")).isDisplayed());

        // Assert Text of Password Label
        Assert.assertTrue(driver.findElement(By.cssSelector("form > div:nth-child(2) > label")).getText().contains("Password"));
        Assert.assertTrue(driver.findElement(By.cssSelector("form > div:nth-child(2) > label")).isDisplayed());

        // Assert Text of Password Element
        Assert.assertTrue(driver.findElement(By.cssSelector("#password")).getAttribute("placeholder").contains("Enter your password"));
        Assert.assertTrue(driver.findElement(By.cssSelector("#password")).isDisplayed());

        // Assert Text of Login Element
        Assert.assertTrue(driver.findElement(By.cssSelector("#log-in")).getText().contains("Log In"));
        Assert.assertTrue(driver.findElement(By.cssSelector("#log-in")).isDisplayed());

        // Assert Text of Remember Me Element
        Assert.assertTrue(driver.findElement(By.cssSelector(".form-check-label")).getText().contains("Remember Me"));
        Assert.assertTrue(driver.findElement(By.cssSelector(".form-check-label")).isDisplayed());
    }
    @Test
    public void validateImages() {
        // Assert Logo Icon is Visible
        Assert.assertTrue(driver.findElement(By.cssSelector(".logo-w>a>img")).isDisplayed());

        // Assert User Icon is Visible
        Assert.assertTrue(driver.findElement(By.cssSelector(".pre-icon.os-icon.os-icon-user-male-circle")).isDisplayed());

        // Assert Fingerprint Icon is Visible
        Assert.assertTrue(driver.findElement(By.cssSelector(".pre-icon.os-icon.os-icon-fingerprint")).isDisplayed());

        // Assert Twitter Icon is Visible
        Assert.assertTrue(driver.findElement(By.cssSelector("a:nth-child(1) > img")).isDisplayed());

        // Assert Facebook Icon is Visible
        Assert.assertTrue(driver.findElement(By.cssSelector("a:nth-child(2) > img")).isDisplayed());

        // Assert Linkdin Icon is Visible
        Assert.assertTrue(driver.findElement(By.cssSelector("a:nth-child(3) > img")).isDisplayed());
    }
    @Test
    public void validateCheckBox() {
        // Assert CheckBox isn't selected
        Assert.assertFalse(driver.findElement(By.cssSelector(".form-check-input")).isSelected());

    }

    //Both Username and Password must be present
    @Test
    public void usernameAndPasswordMustPresentTest()  {
        submitForm();
        Assert.assertTrue(driver.findElement(errorLocator).isDisplayed());
        Assert.assertTrue(driver.findElement(errorLocator).getText().contains("Both Username and Password must be present"));
    }
    //Password must be present
    @Test
    public void usernameMustPresentTest()  {
        driver.findElement(By.cssSelector("#username")).sendKeys("John Smith");
        submitForm();
        Assert.assertTrue(driver.findElement(errorLocator).isDisplayed());
        Assert.assertTrue(driver.findElement(errorLocator).getText().contains("Password must be present"));
    }
    //Username must be present
    @Test
    public void passwordMustPresentTest()  {
        driver.findElement(By.cssSelector("#password")).sendKeys("ABC$1@");
        submitForm();
        Assert.assertTrue(driver.findElement(errorLocator).isDisplayed());
        Assert.assertTrue(driver.findElement(errorLocator).getText().contains("Username must be present"));
    }
    //Successful login
    @Test
    public void succsfullLoginTest()  {
        driver.findElement(By.cssSelector("#username")).sendKeys("John Smith");
        driver.findElement(By.cssSelector("#password")).sendKeys("ABC$1@");
        submitForm();
        Assert.assertTrue(driver.getTitle().contains("ACME demo app"));
    }

    public void submitForm(){
        driver.findElement(By.cssSelector("#log-in")).click();
    }


    @After
    public void TearDown(){
        driver.quit();

    }

    @AfterClass
    public static void finalTearDown(){

    }




}


