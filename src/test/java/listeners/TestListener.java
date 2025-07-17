package listeners;

import drivers.DriverFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class TestListener implements ITestListener {

    private void attachScreenshotToTest(String name, byte[] screenshot) {
        String attachmentUuid = UUID.randomUUID().toString();
        AllureLifecycle lifecycle = Allure.getLifecycle();

        // Convertir byte[] a InputStream para escribir el archivo
        ByteArrayInputStream inputStream = new ByteArrayInputStream(screenshot);
        lifecycle.writeAttachment(attachmentUuid, inputStream);

        // Asociar el attachment al test actual
        lifecycle.updateTestCase(result -> {
            io.qameta.allure.model.Attachment attachment = new io.qameta.allure.model.Attachment();
            attachment.setName(name);
            attachment.setType("image/png");
            attachment.setSource(attachmentUuid);
            result.getAttachments().add(attachment);
        });
    }

    private void captureScreenshot(ITestResult result, String status) {
        System.out.println("📸 Capturando screenshot para Allure");

        WebDriver driver = DriverFactory.getDriver();

        if (driver != null) {
            // Guardar backup en carpeta
            String methodName = result.getMethod().getMethodName();
            String timestamp = String.valueOf(System.currentTimeMillis());
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                File dest = new File("test-output/screenshots/" + methodName + "_" + status + "_" + timestamp + ".png");
                FileUtils.copyFile(screenshotFile, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Capturar y adjuntar a Allure
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            attachScreenshotToTest("Screenshot - " + status, screenshot);
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