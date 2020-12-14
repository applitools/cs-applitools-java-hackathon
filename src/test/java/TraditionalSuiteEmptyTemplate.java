import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;


@RunWith(JUnit4.class)
public class TraditionalSuiteEmptyTemplate {
    public static boolean isOriginalApp=false;
    public WebDriver driver;
    public final String OriginalAppURL="https://demo.applitools.com/hackathon.html";
    public final String NewAppURL="https://demo.applitools.com/hackathonV2.html";
    public final String ABTestURL="https://abtestautomation.com/";
    public final By errorLocator = By.cssSelector(".alert.alert-warning");

    @BeforeClass
    public static void classSetup(){
        WebDriverManager.chromedriver().setup();
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

        // Assert Text of UserName Label

        // Assert Text of UserName Element

        // Assert Text of Password Label

        // Assert Text of Password Element

        // Assert Text of Login Element

        // Assert Text of Remember Me Element
    }
    @Test
    public void validateImages() {
        // Assert Logo Icon is Visible

        // Assert User Icon is Visible

        // Assert Fingerprint Icon is Visible

        // Assert Twitter Icon is Visible

        // Assert Facebook Icon is Visible

        // Assert Linkdin Icon is Visible
    }
    @Test
    public void validateCheckBox() {
        // Assert CheckBox isn't selected


    }

    //Both Username and Password must be present
    @Test
    public void usernameAndPasswordMustPresentTest()  {
        submitForm();
        // Assert Error Message
    }
    //Password must be present
    @Test
    public void usernameMustPresentTest()  {
        driver.findElement(By.cssSelector("#username")).sendKeys("John Smith");
        submitForm();
        // Assert Error Message
    }
    //Username must be present
    @Test
    public void passwordMustPresentTest()  {
        driver.findElement(By.cssSelector("#password")).sendKeys("ABC$1@");
        submitForm();
        // Assert Error Message

    }
    //Successful login
    @Test
    public void succsfullLoginTest()  {
        driver.findElement(By.cssSelector("#username")).sendKeys("John Smith");
        driver.findElement(By.cssSelector("#password")).sendKeys("ABC$1@");
        submitForm();
        // Assert Error Message
    }

    @Test
    public void ABTest(){
        driver.get(ABTestURL);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.masthead-heading")));
        WebElement headerTitle = driver.findElement(By.cssSelector("h1.masthead-heading"));
        WebElement headerSubTitle = driver.findElement(By.cssSelector("p.masthead-subheading"));
        WebElement mainSectionTitle = driver.findElement(By.cssSelector("h2.page-section-heading"));

        // Assert Header Title
        Assert.assertTrue(headerTitle.isDisplayed());
        // Assert Header Sub Title
        Assert.assertTrue(headerSubTitle.isDisplayed());
        // Assert Main Section Title
        Assert.assertTrue(mainSectionTitle.isDisplayed());

        if(isPageVariationA()){
            // Assert Header Title

            // Assert Header Sub Title

            // Assert Main Section Title

        }
        else if (isPageVariationB()){
            // Assert Header Title

            // Assert Header Sub Title

            // Assert Main Section Title

        }

        // Assert the Main Section Title is equal to the value in the Navigation bar

        // Responsive Design
        if(driver.findElement(By.cssSelector("#mainNav > div > button")).isDisplayed()){ // menu is collapsed
            driver.findElement(By.cssSelector("#mainNav > div > button")).click();
        }

        WebElement mainNavigationBarTitle = driver.findElement(By.cssSelector("#navbarResponsive > ul > li:nth-child(2) > a"));
        String mainNavigationBarTitleContent = mainNavigationBarTitle.getText();

        // Add Assertion here

    }

    public boolean isPageVariationA(){
        WebElement header = driver.findElement(By.cssSelector("header"));
        String clsVal = header.getAttribute("class");
        for (String i : clsVal.split(" ")) {
            //check the class for specific value
            if (i.equals("bg-primary")) {
                return true;
            }
        }
        return false;
    }

    public boolean isPageVariationB(){
        WebElement header = driver.findElement(By.cssSelector("header"));
        String clsVal = header.getAttribute("class");
        for (String i : clsVal.split(" ")) {
            //check the class for specific value
            if (i.equals("bg-primary-b")) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testCanvas(){
        ArrayList<Long> expectedList2017 = new ArrayList<Long>(Arrays.asList(10L, 20L, 30L, 40L, 50L, 60L, 70L));
        ArrayList<Long> expectedList2018 = new ArrayList<Long>(Arrays.asList(8L, 9L, -10L, 10L, 40L, 60L, 40L));
        ArrayList<Long> expectedList2019 = new ArrayList<Long>(Arrays.asList(5L, 10L, 15L, 20L, 25L, 30L, 35L));

        driver.get(OriginalAppURL);
        driver.findElement(By.cssSelector("#username")).sendKeys("John Smith");
        driver.findElement(By.cssSelector("#password")).sendKeys("ABC$1@");
        submitForm();
        driver.findElement(By.cssSelector("#showExpensesChart")).click();
        driver.findElement(By.cssSelector("#addDataset")).click();

        ArrayList<Integer> list2017 = getChartsData(2017);
        ArrayList<Integer> list2018 = getChartsData(2018);
        ArrayList<Integer> list2019 = getChartsData(2019);

        // Assert Content is the same

        // Assert Months

        // Assert Years


    }

    public ArrayList<String> getMonthsOfGraph(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String command ="Add your command Here";
        return (ArrayList<String>) js.executeScript(command);

    }

    public ArrayList<String> getYeasOfGraph(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String command ="Add your command Here";
        return (ArrayList<String>) js.executeScript(command);

    }

    public ArrayList<Integer> getChartsData(int year) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String command ="Add your command Here";
        return (ArrayList<Integer>) js.executeScript(command);
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


