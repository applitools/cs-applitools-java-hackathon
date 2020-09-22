import com.applitools.eyes.*;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import org.apache.tools.ant.taskdefs.Tar;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(JUnit4.class)
public class VisualAISuite {
    public WebDriver driver;
    public boolean isOriginalApp=true;
    public final String OriginalAppURL="https://demo.applitools.com/hackathon.html";
    public final String NewAppURL="https://demo.applitools.com/hackathonV2.html";
    public Eyes eyes;
    public static EyesRunner runner;
    public static BatchInfo batchInfo;

    @Rule
    public final TestName testName = new TestName();

    @BeforeClass
    public static void classSetup(){
        batchInfo = new BatchInfo("VisualAITests");
        runner = new ClassicRunner();
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

        eyes=new Eyes(runner);
        Configuration conf = eyes.getConfiguration();
        conf.setBatch(batchInfo);
        conf.setViewportSize(new RectangleSize(1000,600));
//        conf.setApiKey("SET_YOUR_API_KEY");
        eyes.setConfiguration(conf);
        eyes.open(driver,"VisualTest",testName.getMethodName());

    }


    @Test
    public void UIElementTest()  {
        eyes.check("LoginPage", Target.window().fully());
    }

    @Test
    public void usernameAndPasswordMustPresentTest()  {
        submitForm();
        eyes.check("Username and password must be present", Target.window().fully());
    }

    @Test
    public void usernameMustPresentTest()  {
        driver.findElement(By.cssSelector("#username")).sendKeys("MyUserName");
        submitForm();
        eyes.check("Username must be present", Target.window().fully());
    }

    @Test
    public void passwordMustPresentTest()  {
        driver.findElement(By.cssSelector("#password")).sendKeys("MyPasswrod");
        submitForm();
        eyes.check("Password must be present", Target.window().fully());
    }

    @Test
    public void successfulLoginTest()  {
        driver.findElement(By.cssSelector("#username")).sendKeys("MyUserName");
        driver.findElement(By.cssSelector("#password")).sendKeys("MyPasswrod");
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
        TestResultsSummary allTestResults = runner.getAllTestResults(false);
        System.out.println(allTestResults);



    }











}


