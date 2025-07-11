package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import java.time.Duration;

public class CartTest extends BaseTest {

    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify that the correct product is added to the cart")
    public void testAddProductToCartAndValidateItAppears() {
        HomePage homePage = new HomePage(driver);
        String expectedProductName = homePage.getSecondProductTitle();
        Allure.step("Expected product: " + expectedProductName);

        homePage.clickAddToCartOnSecondProduct();

        //verify notification
        By successNotification = By.cssSelector(".bar-notification.success");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successNotification));

        //Close pop up
        By closeNotification = By.cssSelector(".bar-notification .close");
        wait.until(ExpectedConditions.elementToBeClickable(closeNotification)).click();

        //Scroll up
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

        //Go to cart
        driver.findElement(By.cssSelector("span.cart-label")).click();

        CartPage cartPage = new CartPage(driver);
        String actualProductName = cartPage.getProductName();

        Allure.step("Actual product in cart: " + actualProductName);
        Assert.assertTrue(actualProductName.contains(expectedProductName),
                "Expected product in cart to be: " + expectedProductName + ", but found: " + actualProductName);
    }

    @Story("Remove from Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify that a user can remove a product from the cart")
    public void testRemoveProductFromCart() {
        HomePage homePage = new HomePage(driver);
        String expectedProductName = homePage.getSecondProductTitle();
        homePage.clickAddToCartOnSecondProduct();

        // Wait and close notification
        By successNotification = By.cssSelector(".bar-notification.success");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successNotification));

        By closeNotification = By.cssSelector(".bar-notification .close");
        wait.until(ExpectedConditions.elementToBeClickable(closeNotification)).click();

        // Go to cart
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        driver.findElement(By.cssSelector("span.cart-label")).click();

        // Remove product
        CartPage cartPage = new CartPage(driver);
        cartPage.removeProductFromCart();

        String cartMessage = cartPage.getCartMessage();
        Allure.step("Cart message after removal: " + cartMessage);
        Assert.assertTrue(cartMessage.contains("Your Shopping Cart is empty"),
                "Expected cart to be empty after removing the product.");
    }

    @Story("Update Cart Quantity")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify that updating the quantity in the cart updates the subtotal")
    public void testUpdateProductQuantityInCart() {
        HomePage homePage = new HomePage(driver);
        homePage.clickAddToCartOnSecondProduct();

        // Wait and close notification
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bar-notification.success")));
        driver.findElement(By.cssSelector(".bar-notification .close")).click();

        //Go to cart
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        driver.findElement(By.cssSelector("span.cart-label")).click();

        CartPage cartPage = new CartPage(driver);

        // Capture subtotal before updating
        String subtotalBefore = cartPage.getSubtotalText();
        Allure.step("Subtotal before update: " + subtotalBefore);

        // Change quantity
        cartPage.updateProductQuantity(3);

        // Capture subtotal after
        String subtotalAfter = cartPage.getSubtotalText();
        Allure.step("Subtotal after update: " + subtotalAfter);

        // Validations
        Assert.assertNotEquals(subtotalBefore, subtotalAfter, "Expected subtotal to change after quantity update");
        Assert.assertEquals(cartPage.getQuantity(), 3, "Quantity should be updated to 3");
    }

}
