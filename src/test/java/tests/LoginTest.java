package tests;

import base.BaseTest;
import io.qameta.allure.*;
import model.UserData;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import utils.AllureUtils;
import utils.FakerUtils;

@Epic("User Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify that a registered user can log in successfully")
    public void testValidLogin() {
        // Register a new user
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();

        String gender = "male";
        String firstName = FakerUtils.generateFirstName();
        String lastName = FakerUtils.generateLastName();
        String email = FakerUtils.generateEmail();
        String password = FakerUtils.generatePassword();

        UserData user = new UserData(gender, firstName, lastName, email, password);
        AllureUtils.logUserData(user);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.selectGender(user.getGender());
        registerPage.setFirstName(user.getFirstName());
        registerPage.setLastName(user.getLastName());
        registerPage.setEmail(user.getEmail());
        registerPage.setPassword(user.getPassword());
        registerPage.setConfirmPassword(user.getPassword());
        registerPage.clickRegister();

        // Logout
        driver.findElement(By.className("ico-logout")).click();

        // Go to log in
        homePage.clickLoginLink();

        // Log in with valid data
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        // validation: verify "Log out" appears
        boolean isLoggedIn = driver.findElements(By.className("ico-logout")).size() > 0;
        Assert.assertTrue(isLoggedIn, "User should be logged in successfully.");
    }

    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify login fails with incorrect password")
    public void testLoginWithInvalidPassword() {
        // Register a new user
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();

        String gender = "male";
        String firstName = FakerUtils.generateFirstName();
        String lastName = FakerUtils.generateLastName();
        String email = FakerUtils.generateEmail();
        String password = FakerUtils.generatePassword();

        UserData user = new UserData(gender, firstName, lastName, email, password);
        AllureUtils.logUserData(user);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.selectGender(user.getGender());
        registerPage.setFirstName(user.getFirstName());
        registerPage.setLastName(user.getLastName());
        registerPage.setEmail(user.getEmail());
        registerPage.setPassword(user.getPassword());
        registerPage.setConfirmPassword(user.getPassword());
        registerPage.clickRegister();

        // Logout
        driver.findElement(By.className("ico-logout")).click();

        // go to log in
        homePage.clickLoginLink();

        // Log in with incorrect password
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword("WrongPass123");
        loginPage.clickLoginButton();

        String error = loginPage.getLoginError();
        AllureUtils.logAssertionMessage("Login error message: " + error);

        Assert.assertTrue(error.contains("Login was unsuccessful."), "Expected login failure message.");
    }

    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify login fails with a non-registered user")
    public void testLoginWithNonExistentUser() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLoginLink();

        UserData user = new UserData(
                "(not applicable)",
                "(N/A)",
                "(N/A)",
                FakerUtils.generateEmail(),
                FakerUtils.generatePassword()
        );

        AllureUtils.logLoginData(user);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        String error = loginPage.getLoginError();
        AllureUtils.logAssertionMessage("Login error message: " + error);

        Assert.assertTrue(error.contains("Login was unsuccessful."), "Expected login failure message.");
    }

}
