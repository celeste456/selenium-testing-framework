package listeners;

import drivers.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private void captureScreenshot(ITestResult result, String status) {
        System.out.println("📸 Capturando screenshot para Allure");

        WebDriver driver = DriverFactory.getDriver();

        if (driver != null) {
            // Guardar respaldo en disco
            String methodName = result.getMethod().getMethodName();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                File destFile = new File("test-output/screenshots/" + methodName + "_" + status + "_" + timestamp + ".png");
                FileUtils.copyFile(screenshotFile, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Adjuntar directamente al test con Allure (¡esto sí lo asocia bien!)
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot - " + status, "image/png", new ByteArrayInputStream(screenshot), ".png");
        }
    }

    @Override public void onTestFailure(ITestResult result) {
        captureScreenshot(result, "FAILURE");
    }

    @Override public void onTestSuccess(ITestResult result) {
        captureScreenshot(result, "SUCCESS");
    }

    @Override public void onTestSkipped(ITestResult result) {
        captureScreenshot(result, "SKIPPED");
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
