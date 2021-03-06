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
public class TraditionalSuite {
    public static boolean isOriginalApp=true;
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
        Assert.assertTrue(driver.findElement(By.cssSelector(".auth-header")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector(".auth-header")).getText().contains("Login Form"));

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
        Assert.assertTrue(driver.findElement(By.cssSelector(".logo-w>a>img")).getAttribute("src").contains("img/logo-big.png"));

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

    // AB Testing
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
            Assert.assertTrue(headerTitle.getText().equals("VARIATION A"));
            // Assert Header Sub Title
            Assert.assertTrue(headerSubTitle.getText().equals("We provide the best services on the planet"));
            // Assert Main Section Title
            Assert.assertTrue(mainSectionTitle.getText().equals("SERVICES"));
        }
        else if (isPageVariationB()){
            // Assert Header Title
            Assert.assertTrue(headerTitle.getText().equals("VARIATION B"));
            // Assert Header Sub Title
            Assert.assertTrue(headerSubTitle.getText().equals("Our products are the best on earth"));
            // Assert Main Section Title
            Assert.assertTrue(mainSectionTitle.getText().equals("PRODUCTS"));
        }

        // Assert the Main Section Title is equal to the value in the Navigation bar

        // Responsive Design
        if(driver.findElement(By.cssSelector("#mainNav > div > button")).isDisplayed()){ // menu is collapsed
            driver.findElement(By.cssSelector("#mainNav > div > button")).click();
        }

        WebElement mainNavigationBarTitle = driver.findElement(By.cssSelector("#navbarResponsive > ul > li:nth-child(2) > a"));
        String mainNavigationBarTitleContent = mainNavigationBarTitle.getText();

        Assert.assertTrue(mainNavigationBarTitleContent.equals(mainSectionTitle.getText()));

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

    // Test Canvas
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
        Assert.assertTrue(expectedList2017.equals(list2017));
        Assert.assertTrue(expectedList2018.equals(list2018));
        Assert.assertTrue(expectedList2019.equals(list2019));

        // Assert Months
        ArrayList<String> expectedMonths = new ArrayList<String>(Arrays.asList("January","February","March","April","May","June","July"));
        ArrayList<String> actualMonths = getMonthsOfGraph();
        Assert.assertTrue(expectedMonths.equals(actualMonths));

        // Assert Years
        ArrayList<String> expectedYears = new ArrayList<String>(Arrays.asList("2017","2018","2019"));
        ArrayList<String> actualYears = getYeasOfGraph();
        Assert.assertTrue(expectedYears.equals(actualYears));


    }

    public ArrayList<String> getMonthsOfGraph(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String command = "var months=window.barChartData.labels; return months";
        return (ArrayList<String>) js.executeScript(command);

    }

    public ArrayList<String> getYeasOfGraph(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String command = "var years = []; window.barChartData.datasets.forEach(element=>years.push(String(element.label))); return years";
        return (ArrayList<String>) js.executeScript(command);

    }

    public ArrayList<Integer> getChartsData(int year) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String command = "return window.myBar.chart.data.datasets.filter(set => set.label == String("+year+"))[0].data;";
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


