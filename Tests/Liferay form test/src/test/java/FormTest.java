import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
public class FormTest {
    ChromeDriver driver;

    @BeforeTest
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

        driver.get("https://forms.liferay.com/web/forms/shared/-/form/122548");
    }

    @Test
    public void firstPageTexts(){
        //Collect all the text to strings
        String pageHeaderTitle = driver.findElement(By.className("lfr-ddm__default-page-header-title")).getText();
        String pageHeaderDesc = driver.findElement(By.className("lfr-ddm__default-page-header-description")).getText();
        String formPageTitle = driver.findElement(By.className("lfr-ddm-form-page-title")).getText();
        String formPageDesc = driver.findElement(By.className("lfr-ddm-form-page-description")).getText();
        String referenceMarkText = driver.findElement(By.className("text-secondary")).getText();
        String firstQuestionText = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/form[1]/div[4]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/label[1]")).getText();
        String secondQuestionText = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/form[1]/div[4]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/label[1]")).getText();
        String thirdQuestionText = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/form[1]/div[4]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/label[1]")).getText();
        String buttonText = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/form[1]/div[4]/div[2]/div[1]/div[1]/div[2]/button[1]")).getText();

        // All should pass
        Assert.assertEquals(pageHeaderTitle,"This is a Liferay Forms");
        Assert.assertEquals(pageHeaderDesc,"This is a description field from our Liferay Forms");
        Assert.assertEquals(formPageTitle,"This is the first page of our forms.");
        Assert.assertEquals(formPageDesc,"Let's party rock.");
        Assert.assertEquals(referenceMarkText,"Indicates Required Fields");
        Assert.assertEquals(firstQuestionText,"What is your favorite soccer player?");
        Assert.assertEquals(thirdQuestionText,"Why did you join the testing area?");
        Assert.assertEquals(buttonText,"Submit");

        // It should fail because of the small initial letter on the page.
        Assert.assertNotEquals(secondQuestionText,"what was the date that Liferay was founded?");
    }

    @Test
    public void happyFlow() {
        // Find and fill out the fields
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/form[1]/div[4]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]")).sendKeys("Test Player");
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/form[1]/div[4]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[2]")).sendKeys("11111991");
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/form[1]/div[4]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/textarea[1]")).sendKeys("I like the high quality stuff");
        // Click to submit
        driver.findElement(By.xpath("//button[@id='ddm-form-submit']")).click();
        // Check the thank you message on the success page
        String thankYouMsg = driver.findElement(By.className("lfr-ddm__default-page-title")).getText();
        Assert.assertEquals(thankYouMsg, "Thank you.");
    }

    @AfterTest
    public void closeAll(){
        driver.quit();
    }
}

