import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


@RunWith(JUnit4.class)
public class VisualAISuite {
    public static boolean isOriginalApp=true;
    public WebDriver driver;
    public final String OriginalAppURL="https://demo.applitools.com/hackathon.html";
    public final String NewAppURL="https://demo.applitools.com/hackathonV2.html";
    public Eyes eyes;
    public static EyesRunner runner;
    public static BatchInfo batchInfo;

    @Rule
    public final TestName testName = new TestName();

    @BeforeClass
    public static void classSetup(){
        if(null!=System.getProperty("isOriginalApp")){
            isOriginalApp=Boolean.valueOf(System.getProperty("isOriginalApp"));
        }

        batchInfo = new BatchInfo("VisualAITests");
        runner = new ClassicRunner();
    }

    @Before
    public void testSetup(){
        driver = new ChromeDriver();

        if(isOriginalApp){
            driver.get(OriginalAppURL);
        }
        else{
            driver.get(NewAppURL);
        }

        eyes=new Eyes(runner);
        Configuration conf = eyes.getConfiguration();
        conf.setBatch(batchInfo);
        conf.setViewportSize(new RectangleSize(1000,600));
//        conf.setApiKey("SET_YOUR_API_KEY");
//        conf.setServerUrl("SET_YOUR_DEDICATED_CLOUD_URL");

        eyes.setConfiguration(conf);

        eyes.open(driver,"VisualTest",testName.getMethodName());

    }


    @Test
    public void UIElementTest()  {
        // Add visual validation here replacing all 21 assertions in the following tests:
        // validateLabels
        // validateImages
        // validateCheckBox


    }

    @Test
    public void usernameAndPasswordMustPresentTest()  {
        submitForm();
        // Add visual validation here

    }

    @Test
    public void usernameMustPresentTest()  {
        driver.findElement(By.cssSelector("#username")).sendKeys("John Smith");
        submitForm();
        // Add visual validation here

    }

    @Test
    public void passwordMustPresentTest()  {
        driver.findElement(By.cssSelector("#password")).sendKeys("ABC$1@");
        submitForm();
        // Add visual validation here

    }

    @Test
    public void successfulLoginTest()  {
        driver.findElement(By.cssSelector("#username")).sendKeys("John Smith");
        driver.findElement(By.cssSelector("#password")).sendKeys("ABC$1@");
        submitForm();
        // Add visual validation here

    }

    public void submitForm(){
        driver.findElement(By.cssSelector("#log-in")).click();
    }


    @After
    public void TearDown(){
        driver.quit();
        eyes.closeAsync();

    }

    @AfterClass
    public static void finalTearDown(){
        System.out.println(runner.getAllTestResults(false));
    }



}


