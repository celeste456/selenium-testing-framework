package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    private WebDriver driver;

    private By firstName = By.id("FirstName");
    private By lastName = By.id("LastName");
    private By email = By.id("Email");
    private By password = By.id("Password");
    private By confirmPassword = By.id("ConfirmPassword");
    private By registerButton = By.id("register-button");
    private By resultMessage = By.className("result");
    private By firstNameError = By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div/div[2]/div[2]/div[2]/div[2]/span[2]");
    private By emailError = By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div/div[2]/div[2]/div[2]/div[4]/span[2]");
    private By passwordError = By.xpath("/html/body/div[4]/div[1]/div[4]/div[2]/form/div/div[2]/div[3]/div[2]/div[1]/span[2]");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("male")) {
            driver.findElement(By.id("gender-male")).click();
        } else if (gender.equalsIgnoreCase("female")) {
            driver.findElement(By.id("gender-female")).click();
        } else {
            throw new IllegalArgumentException("Unsupported gender: " + gender);
        }
    }

    public void setFirstName(String name) {driver.findElement(firstName).sendKeys(name);}

    public void setLastName(String name) {driver.findElement(lastName).sendKeys(name);}

    public void setEmail(String emailAddress) {driver.findElement(email).sendKeys(emailAddress);}

    public void setPassword(String pass) {driver.findElement(password).sendKeys(pass);}

    public void setConfirmPassword(String pass) {driver.findElement(confirmPassword).sendKeys(pass);}

    public void clickRegister() {driver.findElement(registerButton).click();}

    public String getSuccessMessage() {return driver.findElement(resultMessage).getText();}

    public String getFirstNameError() {return driver.findElement(firstNameError).getText();}

    public String getEmailError() {return driver.findElement(emailError).getText();}

    public String getPasswordError() {return driver.findElement(passwordError).getText();}
}
