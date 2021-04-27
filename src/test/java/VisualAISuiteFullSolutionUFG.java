import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.*;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(JUnit4.class)
public class VisualAISuiteFullSolutionUFG {
    public WebDriver driver;
    public boolean isOriginalApp=false;
    public final String OriginalAppURL="https://demo.applitools.com/hackathon.html";
    public final String NewAppURL="https://demo.applitools.com/hackathonV2.html";
    public Eyes eyes;
    public static EyesRunner runner;
    public static BatchInfo batchInfo;

    @Rule
    public final TestName testName = new TestName();

    @BeforeClass
    public static void classSetup(){
        WebDriverManager.chromedriver().setup();
        batchInfo = new BatchInfo("VisualAITests Ultra Fast Grid");
        batchInfo.setNotifyOnCompletion(true);
        runner = new VisualGridRunner(new RunnerOptions().testConcurrency(10));
    }

    @Before
    public void testSetup() {
        driver = new ChromeDriver();
        
        if(null!=System.getProperty("isOriginalApp")){
            isOriginalApp=Boolean.valueOf(System.getProperty("isOriginalApp"));
        }

        if(isOriginalApp){
            driver.get(OriginalAppURL);
        }
        else{
            driver.get(NewAppURL);
        }

        eyes=new Eyes(runner);
        Configuration conf = eyes.getConfiguration();
        conf.setBatch(batchInfo);
        // set the viewport size of the local browser
        conf.setViewportSize(new RectangleSize(1000,600));

        // Setting the required Browsers and viewport sizes
        conf.setViewportSize(new RectangleSize(1000,600));
        conf.addBrowser(1024,768, BrowserType.CHROME);
        conf.addBrowser(1024,768,BrowserType.FIREFOX);
        conf.addBrowser(1200,800,BrowserType.SAFARI);
        conf.addBrowser(1920,1080,BrowserType.EDGE_CHROMIUM);
        conf.addBrowser(1000,800,BrowserType.IE_11);


        conf.addBrowser(new ChromeEmulationInfo(DeviceName.Galaxy_S5, ScreenOrientation.PORTRAIT));
        conf.addBrowser(new ChromeEmulationInfo(DeviceName.Galaxy_S5, ScreenOrientation.LANDSCAPE));
        conf.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_11, ScreenOrientation.PORTRAIT));
        conf.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_11, ScreenOrientation.LANDSCAPE));

//        conf.setApiKey("SET_YOUR_API_KEY");
//        conf.setServerUrl("SET_YOUR_DEDICATED_CLOUD_URL");
        eyes.setConfiguration(conf);

        eyes.open(driver,"VisualTest",testName.getMethodName());
        // Baseline is defined by 5 properties
        // 1 - AppName - String
        // 2 - TestName - String
        // 3- OS - taken from the WebDriver
        // 4- Browser - taken from the WebDriver
        // 5- Viewport size - new RectangleSize(1000,600)

        // The first time running on a uniquw combination of the above 5 arguments a baseline will be created.
        // Subsequent runs will be compared with the above baseline.
    }

    @Test
    public void UIElementTest()  {
        // Add visual validation here replacing all 21 assertions in the following tests:
        // validateLabels
        // validateImages
        // validateCheckBox
        eyes.check("LoginPage", Target.window().fully(true));
    }

    @Test
    public void usernameAndPasswordMustPresentTest()  {
        submitForm();
        eyes.check("Username and password must be present", Target.window().fully());
    }

    @Test
    public void usernameMustPresentTest()  {
        driver.findElement(By.cssSelector("#username")).sendKeys("John Smith");
        submitForm();
        eyes.check("Username must be present", Target.window().fully());
    }

    @Test
    public void passwordMustPresentTest()  {
        driver.findElement(By.cssSelector("#password")).sendKeys("ABC$1@");
        submitForm();
        eyes.check("Password must be present", Target.window().fully());
    }

    @Test
    public void successfulLoginTest()  {
        driver.findElement(By.cssSelector("#username")).sendKeys("John Smith");
        driver.findElement(By.cssSelector("#password")).sendKeys("ABC$1@");
        submitForm();
        eyes.check("Successful login", Target.window().fully());
    }

    public void submitForm(){
        driver.findElement(By.cssSelector("#log-in")).click();
    }


    @After
    public void TearDown(){
        try{
            driver.quit();
            eyes.closeAsync();
        }
        finally {
            eyes.abortAsync();
        }

    }

    @AfterClass
    public static void finalTearDown(){
        TestResultsSummary allTestResults = runner.getAllTestResults(true);
        System.out.println(allTestResults.getAllResults()[0].getTestResults());
    }


}


