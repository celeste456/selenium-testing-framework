package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage {
    private WebDriver driver;

    private By updateCartButton = By.name("updatecart");
    private By productNameInCart = By.xpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/div/form/table/tbody/tr/td[3]/a");
    private By removeCheckbox = By.cssSelector("input[name^='removefromcart']");
    private By emptyCartMessage = By.cssSelector(".order-summary-content");
    private By quantityInput = By.cssSelector("input.qty-input");
    private By subtotal = By.cssSelector("span.product-subtotal");
    private By termsCheckbox = By.id("termsofservice");
    private By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getProductName() {
        return driver.findElement(productNameInCart).getText();
    }

    public void removeProductFromCart() {
        driver.findElement(removeCheckbox).click();
        driver.findElement(updateCartButton).click();
    }

    public String getCartMessage() {
        return driver.findElement(emptyCartMessage).getText();
    }

    public void updateProductQuantity(int quantity) {
        WebElement qtyField = driver.findElement(quantityInput);
        qtyField.clear();
        qtyField.sendKeys(String.valueOf(quantity));
        driver.findElement(updateCartButton).click();
    }

    public String getSubtotalText() {
        return driver.findElement(subtotal).getText();
    }

    public int getQuantity() {
        return Integer.parseInt(driver.findElement(quantityInput).getAttribute("value"));
    }

    public void acceptTermsAndConditions() {
        driver.findElement(termsCheckbox).click();
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }

}