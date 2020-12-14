import com.applitools.eyes.*;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class ApplitoolsAccesability {
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
        batchInfo = new BatchInfo("VisualAI Advanced Capabilities");
        runner = new ClassicRunner();
    }

    @Before
    public void testSetup() {
        driver = new ChromeDriver();

        eyes=new Eyes(runner);
        Configuration conf = eyes.getConfiguration();
        conf.setBatch(batchInfo);
        // set the viewport size of the local browser
        conf.setViewportSize(new RectangleSize(700,600));
//        conf.setApiKey("SET_YOUR_API_KEY");
//        conf.setServerUrl("SET_YOUR_DEDICATED_CLOUD_URL");

        // Accessibility Setup

        conf.setAccessibilityValidation(new AccessibilitySettings(AccessibilityLevel.AA, AccessibilityGuidelinesVersion.WCAG_2_0));
//        conf.setAccessibilityValidation(new AccessibilitySettings(AccessibilityLevel.AA, AccessibilityGuidelinesVersion.WCAG_2_1));
//        conf.setAccessibilityValidation(new AccessibilitySettings(AccessibilityLevel.AAA, AccessibilityGuidelinesVersion.WCAG_2_0));
//        conf.setAccessibilityValidation(new AccessibilitySettings(AccessibilityLevel.AAA, AccessibilityGuidelinesVersion.WCAG_2_1));

        eyes.setConfiguration(conf);
        //eyes.setLogHandler(new StdoutLogHandler(true));
        eyes.open(driver,"VisualTest",testName.getMethodName());
        // Baseline is defined by 5 properties
        // 1 - AppName - String
        // 2 - TestName - String
        // 3- OS - taken from the WebDriver
        // 4- Browser - taken from the WebDriver
        // 5- Viewport size - new RectangleSize(1000,600)

        // The first time running on a unique combination of the above 5 arguments a baseline will be created.
        // Subsequent runs will be compared with the above baseline.
    }

    @Test
    public void endToEndAccesabilityTesting()  {
        driver.get(OriginalAppURL);
        //
        eyes.check("Empty Form", Target.window().fully(true));
        submitForm();
        // Form with Error message
        eyes.check("Form with Error message", Target.window().fully());

        // dashboard page
        driver.findElement(By.cssSelector("#username")).sendKeys("John Smith");
        driver.findElement(By.cssSelector("#password")).sendKeys("ABC$1@");
        submitForm();
        eyes.check("dashboard page", Target.window().fully());


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
        for (TestResultContainer summary : allTestResults) {
            TestResults result = summary.getTestResults();
            if (result == null) {
                System.out.printf("No test results information available");
            } else {
                System.out.printf(
                        "URL = %s, \nAppName = %s, testname = %s, Browser = %s,OS = %s, viewport = %dx%d, acessibility = %s\n",
                        result.getUrl(),
                        result.getAppName(),
                        result.getName(),
                        result.getHostApp(),
                        result.getHostOS(),
                        result.getHostDisplaySize().getWidth(),
                        result.getHostDisplaySize().getHeight(),
                        ( result.getAccessibilityStatus() == null ? "Accessability validation not enabled"
                                : ((result.getAccessibilityStatus().getStatus() == AccessibilityStatus.Passed
                                ? "Passed accessibility"
                                : (result.getAccessibilityStatus().getStatus() == AccessibilityStatus.Failed
                                ? "Failed accessibility" : "undefined")))
                        )
                );
            }
        }



        }


}


