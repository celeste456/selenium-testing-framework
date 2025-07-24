package tests;
import base.BaseTest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.*;
import model.CheckoutData;
import model.UserData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.FakerUtils;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class CheckoutTest extends BaseTest {

    @Story("Full Checkout Flow as Guest")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify that a guest user can complete the full checkout flow successfully")
    public void testFullGuestCheckoutFlow() {
        HomePage homePage = new HomePage(driver);
        homePage.clickAddToCartOnSecondProduct();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bar-notification.success")));
        driver.findElement(By.cssSelector(".bar-notification .close")).click();

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        driver.findElement(By.cssSelector("span.cart-label")).click();

        CartPage cartPage = new CartPage(driver);
        cartPage.acceptTermsAndConditions();
        cartPage.clickCheckout();

        driver.findElement(By.cssSelector(".checkout-as-guest-button")).click();

        // Checkout data
        String firstName = FakerUtils.generateFirstName();
        String lastName = FakerUtils.generateLastName();
        String email = FakerUtils.generateEmail();
        String company = "Fidelitas";
        String country = "Costa Rica";
        String city = "San José";
        String address = "150mts al norte del hotel Holiday Inn";
        String zip = "13241";
        String phone = "62766664";
        String fax = "232134142";

        CheckoutData data = new CheckoutData(firstName, lastName, email, company, country, city, address, zip, phone, fax);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(data);
        Allure.addAttachment("CheckoutData", "application/json",
                new ByteArrayInputStream(userJson.getBytes(StandardCharsets.UTF_8)), ".json");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Allure.step("Filling billing address form...");
        checkoutPage.setFirstName(data.getFirstName());
        checkoutPage.setLastName(data.getLastName());
        checkoutPage.setEmail(data.getEmail());
        checkoutPage.setCompany(data.getCompany());
        checkoutPage.selectCountry(data.getCountry());
        checkoutPage.setCity(data.getCity());
        checkoutPage.setAddress(data.getAddress());
        checkoutPage.setZip(data.getZip());
        checkoutPage.setPhone(data.getPhone());
        checkoutPage.setFax(data.getFax());
        checkoutPage.clickContinueOnShippingAddress();

        // Shipping
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-shipping")));
        checkoutPage.clickContinueOnShippingMethod();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-shipping-method")));
        checkoutPage.selectShippingMethodById("shippingoption_1");
        checkoutPage.clickContinueOnPaymentMethod();

        // Payment method
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-payment-method")));
        checkoutPage.selectPaymentMethodById("paymentmethod_1"); // Check / Money Order
        checkoutPage.clickContinueOnPaymentInfo();

        // Payment info
        WebElement paymentInfoSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-payment-info")));
        Assert.assertTrue(paymentInfoSection.getText().contains("Mail Personal or Business Check"), "Expected payment instructions not found.");
        Allure.step("Payment instructions verified:\n" + paymentInfoSection.getText());

        checkoutPage.clickContinueOnConfirmOrder();

        // Confirm order
        WebElement confirmOrderForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-confirm-order")));
        Assert.assertTrue(confirmOrderForm.isDisplayed(), "Expected to be on Confirm Order step.");

        checkoutPage.clickConfirmOrderButton();

        // Success
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".section.order-completed")));
        String messageText = successMessage.getText();

        Allure.step("Confirmation message:\n" + messageText);

        Assert.assertTrue(messageText.contains("Your order has been successfully processed!"),
                "Expected success message not found after confirming the order.");
    }

    @Story("Full Checkout Flow as Returning Customer")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify that a registered user can complete the full checkout flow successfully")
    public void testFullCheckoutFlowAsReturningCustomer() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Registration
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();

        String gender = "female";
        String firstName = FakerUtils.generateFirstName();
        String lastName = FakerUtils.generateLastName();
        String email = FakerUtils.generateEmail();
        String password = FakerUtils.generatePassword();

        UserData user = new UserData(gender, firstName, lastName, email, password);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(user);
        Allure.addAttachment("UserData", "application/json",
                new ByteArrayInputStream(userJson.getBytes(StandardCharsets.UTF_8)), ".json");

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.selectGender(user.getGender());
        registerPage.setFirstName(user.getFirstName());
        registerPage.setLastName(user.getLastName());
        registerPage.setEmail(user.getEmail());
        registerPage.setPassword(user.getPassword());
        registerPage.setConfirmPassword(user.getPassword());
        registerPage.clickRegister();

        Assert.assertTrue(registerPage.getSuccessMessage().contains("Your registration completed"));

        // LOG OUT and LOG IN
        homePage.clickLogoutLink();
        homePage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        // Add product to cart
        homePage.clickAddToCartOnSecondProduct();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bar-notification.success")));
        driver.findElement(By.cssSelector(".bar-notification .close")).click();

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        driver.findElement(By.cssSelector("span.cart-label")).click();

        CartPage cartPage = new CartPage(driver);
        cartPage.acceptTermsAndConditions();
        cartPage.clickCheckout();

        // Billing details (only extra fields)
        String company = "Fidelitas";
        String country = "Costa Rica";
        String city = "San José";
        String address = "150mts al norte del hotel Holiday Inn";
        String zip = "13241";
        String phone = "62766664";
        String fax = "232134142";

        CheckoutData checkoutData = new CheckoutData(firstName, lastName, email, company, country, city, address, zip, phone, fax);

        String checkoutJson = gson.toJson(checkoutData);
        Allure.addAttachment("checkoutData", "application/json",
                new ByteArrayInputStream(checkoutJson.getBytes(StandardCharsets.UTF_8)), ".json");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Allure.step("Completing billing address as logged-in user...");
        checkoutPage.setCompany(checkoutData.getCompany());
        checkoutPage.selectCountry(checkoutData.getCountry());
        checkoutPage.setCity(checkoutData.getCity());
        checkoutPage.setAddress(checkoutData.getAddress());
        checkoutPage.setZip(checkoutData.getZip());
        checkoutPage.setPhone(checkoutData.getPhone());
        checkoutPage.setFax(checkoutData.getFax());
        checkoutPage.clickContinueOnShippingAddress();

        // SHIPPING
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-shipping")));
        checkoutPage.clickContinueOnShippingMethod();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-shipping-method")));
        checkoutPage.selectShippingMethodById("shippingoption_1");
        checkoutPage.clickContinueOnPaymentMethod();

        // Payment method
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-payment-method")));
        checkoutPage.selectPaymentMethodById("paymentmethod_1");
        checkoutPage.clickContinueOnPaymentInfo();

        WebElement paymentInfoSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-payment-info")));
        Assert.assertTrue(paymentInfoSection.getText().contains("Mail Personal or Business Check"));
        Allure.step("Payment instructions verified:\n" + paymentInfoSection.getText());

        checkoutPage.clickContinueOnConfirmOrder();

        WebElement confirmOrderForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-step-confirm-order")));
        Assert.assertTrue(confirmOrderForm.isDisplayed());

        checkoutPage.clickConfirmOrderButton();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".section.order-completed")));
        String messageText = successMessage.getText();
        Allure.step("Confirmation message:\n" + messageText);

        Assert.assertTrue(messageText.contains("Your order has been successfully processed!"));
    }

    @Story("Cart - Validation Without Terms Agreement")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify that user cannot proceed to checkout without accepting terms and conditions")
    public void testCannotProceedWithoutAcceptingTerms() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        HomePage homePage = new HomePage(driver);
        homePage.clickAddToCartOnSecondProduct();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bar-notification.success")));
        driver.findElement(By.cssSelector(".bar-notification .close")).click();

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        driver.findElement(By.cssSelector("span.cart-label")).click();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        WebElement popupContent = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("terms-of-service-warning-box")));

        String popupText = popupContent.getText().trim();
        Allure.step("TOS Warning Popup Text:\n" + popupText);

        Assert.assertTrue(popupText.contains("Please accept the terms of service before the next step."));

        WebElement closeBtn = driver.findElement(By.cssSelector(".ui-dialog-titlebar-close"));
        closeBtn.click();
    }
}
