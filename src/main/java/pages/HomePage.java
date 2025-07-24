package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    private By registerLink = By.className("ico-register");
    private By loginLink = By.className("ico-login");
    private By secondAddToCartButton  = By.xpath("/html/body/div[4]/div[1]/div[4]/div[3]/div/div/div[3]/div[3]/div/div[2]/div[3]/div[2]/input");
    private By secondProductTitle = By.xpath("/html/body/div[4]/div[1]/div[4]/div[3]/div/div/div[3]/div[3]/div/div[2]/h2/a");
    private By logoutButton = By.xpath("/html/body/div[4]/div[1]/div[1]/div[2]/div[1]/ul/li[2]/a");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }

    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    public void clickAddToCartOnSecondProduct() {
        driver.findElement(secondAddToCartButton ).click();
    }

    public String getSecondProductTitle() {
        return driver.findElement(secondProductTitle).getText();
    }

    public void clickLogoutLink() {
        driver.findElement(logoutButton).click();
    }

}
