package listeners;

import drivers.DriverFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private void attachScreenshot(String name, WebDriver driver) {
        if (driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            AllureLifecycle lifecycle = Allure.getLifecycle();
            lifecycle.addAttachment(name, "image/png", "png", screenshot);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverFactory.getDriver();
        attachScreenshot("Screenshot on Failure - " + result.getMethod().getMethodName(), driver);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        WebDriver driver = DriverFactory.getDriver();
        attachScreenshot("Screenshot on Success - " + result.getMethod().getMethodName(), driver);
    }

    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
