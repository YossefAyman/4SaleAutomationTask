import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
//import io.appium.java_client.MobileElement;




import java.net.MalformedURLException;
import java.net.URL;

//import static sun.security.util.KnownOIDs.EC;

//import static jdk.internal.logger.DefaultLoggerFinder.SharedLoggers.system;

public class FirstAndroidTest {

    AppiumDriver driver;
    public void allowPermission() throws InterruptedException {
        while ( driver.findElement(By.id("com.android.permissioncontroller:id/permission_allow_button")).isDisplayed()) {
            Thread.sleep(3000);
            driver.findElement(By.id("com.android.permissioncontroller:id/permission_allow_button")).click();
            break;
        }
    }
    public void login() throws InterruptedException {
        driver.findElement(By.xpath("//android.view.View[@resource-id=\"phoneNumberField\"]/android.view.View/android.widget.EditText")).sendKeys("00000111");
        driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText")).sendKeys("4saleTest");
        driver.findElement(By.xpath("//android.view.View[@resource-id=\"loginBtn\"]/android.widget.Button")).click();
    }
    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName","Android");
        caps.setCapability("automationName" , "UiAutomator2");
        caps.setCapability("platformVersion" , "15.0");
        caps.setCapability("deviceName" , "Android_Emulator");
        caps.setCapability("app" , System.getProperty("user.dir") + "/apps/4Sale.apk");
        caps.setCapability("appPackage" , "com.forsale.forsale");
        caps.setCapability("appActivity" , "com.forsale.app.features.maincontainer.LauncherActivity");

        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub") , caps);
    }

    @Test
    public void verifyingTheValidationMessageInCaseProceedWithoutData() throws InterruptedException {
        //Call the function to allow notification permission
        allowPermission();
        Thread.sleep(3000);
        driver.findElement(By.id("com.forsale.forsale:id/navgraph_postad")).click();
        login();
        Thread.sleep(1000);
        driver.findElement(By.id("com.forsale.forsale:id/chooseCategoryField")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.forsale.forsale:id/tvCategoryName\" and @text=\"Property\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.forsale.forsale:id/tvCategoryName\" and @text=\"For Sale\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.forsale.forsale:id/tvCategoryName\" and @text=\"House For Sale\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView[@resource-id=\"com.forsale.forsale:id/compose_view\"]/android.view.View/android.view.View/android.widget.Button")).click();
        Thread.sleep(500);
        String pageSource = driver.getPageSource();
        // Define the expected validation message
        String expectedToastMessage = "Kindly Fill all the required information!";
        // Check if the expected toast message is present in the page source
        boolean isToastMessagePresent = pageSource.contains(expectedToastMessage);
        // Assert that the toast message is displayed
        Assert.assertTrue(isToastMessagePresent, "Validation message not found in page source!");
        driver.findElement(By.id("com.forsale.forsale:id/adTitleField")).sendKeys("Test Title1");
        driver.findElement(By.id("com.forsale.forsale:id/adPriceField")).sendKeys("1000");
        driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView[@resource-id=\"com.forsale.forsale:id/compose_view\"]/android.view.View/android.view.View/android.widget.Button")).click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text='Add Media']")).isDisplayed(), "'Add Media' element is not displayed!");
    }


    @AfterTest
    public void tearDown(){
        if (null != driver) {
            driver.quit();
        }
    }
}
