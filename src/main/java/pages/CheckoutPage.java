package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage {
    private WebDriver driver;

    private By firstNameInput = By.id("BillingNewAddress_FirstName");
    private By lastNameInput = By.id("BillingNewAddress_LastName");
    private By emailInput = By.id("BillingNewAddress_Email");
    private By companyInput = By.id("BillingNewAddress_Company");
    private By countrySelect = By.id("BillingNewAddress_CountryId");
    private By stateSelect = By.id("BillingNewAddress_StateProvinceId");
    private By cityInput = By.id("BillingNewAddress_City");
    private By address1Input = By.id("BillingNewAddress_Address1");
    private By zipInput = By.id("BillingNewAddress_ZipPostalCode");
    private By phoneInput = By.id("BillingNewAddress_PhoneNumber");
    private By faxInput = By.id("BillingNewAddress_FaxNumber");
    private By ShippingAdressContinueButton = By.xpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/ol/li[2]/div[2]/div/input");
    private By billingContinueButton = By.xpath("//div[@id='billing-buttons-container']//input[@type='button']");
    private By ShippingMethodContinueButton = By.xpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/ol/li[3]/div[2]/form/div[2]/input");
    private By PaymentMethodContinueButton = By.xpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/ol/li[4]/div[2]/div/input");
    private By PaymentInfoContinueButton = By.xpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/ol/li[5]/div[2]/div/input");
    private By confirmOrderButton = By.xpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/ol/li[6]/div[2]/div[2]/input");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setFirstName(String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void setCompany(String company) {
        driver.findElement(companyInput).sendKeys(company);
    }

    public void selectCountry(String country) {
        new Select(driver.findElement(countrySelect)).selectByVisibleText(country);
    }

    public void setCity(String city) {
        driver.findElement(cityInput).sendKeys(city);
    }

    public void setAddress(String address) {
        driver.findElement(address1Input).sendKeys(address);
    }

    public void setZip(String zip) {
        driver.findElement(zipInput).sendKeys(zip);
    }

    public void setPhone(String phone) {
        driver.findElement(phoneInput).sendKeys(phone);
    }

    public void setFax(String fax) {
        driver.findElement(faxInput).sendKeys(fax);
    }

    public void clickContinueOnShippingAddress() {
        driver.findElement(billingContinueButton).click();
    }

    public void clickContinueOnShippingMethod () {
        driver.findElement(ShippingAdressContinueButton).click();
    }

    public void selectShippingMethodById(String methodId) {driver.findElement(By.id(methodId)).click();}

    public void clickContinueOnPaymentMethod () {driver.findElement(ShippingMethodContinueButton).click();}

    public void selectPaymentMethodById(String methodId) {driver.findElement(By.id(methodId)).click();}

    public void clickContinueOnPaymentInfo() {driver.findElement(PaymentMethodContinueButton).click();}

    public void clickContinueOnConfirmOrder(){driver.findElement(PaymentInfoContinueButton).click();}

    public void clickConfirmOrderButton() {driver.findElement(confirmOrderButton).click();}
}