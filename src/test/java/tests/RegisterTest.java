package tests;

import base.BaseTest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.*;
import model.UserData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;
import utils.FakerUtils;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Epic("User Registration")
@Feature("Register")
public class RegisterTest extends BaseTest {

    @Story("Valid Registration")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify that a user can register successfully with valid data")
    public void testValidRegistration() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();

        String gender = "male";
        String firstName = FakerUtils.generateFirstName();
        String lastName = FakerUtils.generateLastName();
        String email = FakerUtils.generateEmail();
        String password = FakerUtils.generatePassword();

        UserData user = new UserData(gender, firstName, lastName, email, password);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(user);
        Allure.addAttachment("UserData", "application/json",
                new ByteArrayInputStream(userJson.getBytes(StandardCharsets.UTF_8)), ".json");

        RegisterPage page = new RegisterPage(driver);
        page.selectGender(user.getGender());
        page.setFirstName(user.getFirstName());
        page.setLastName(user.getLastName());
        page.setEmail(user.getEmail());
        page.setPassword(user.getPassword());
        page.setConfirmPassword(user.getPassword());
        page.clickRegister();

        Assert.assertTrue(page.getSuccessMessage().contains("Your registration completed"));
    }

    @Story("Invalid Registration")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify registration fails when required fields are empty")
    public void testRegisterWithEmptyFields() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();

        UserData user = new UserData("(empty)", "", "", "", "");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(user);
        Allure.addAttachment("UserData", "application/json",
                new ByteArrayInputStream(userJson.getBytes(StandardCharsets.UTF_8)), ".json");

        RegisterPage page = new RegisterPage(driver);
        page.clickRegister();

        Assert.assertTrue(page.getFirstNameError().contains("First name is required."));
        Assert.assertTrue(page.getEmailError().contains("Email is required."));
        Assert.assertTrue(page.getPasswordError().contains("Password is required."));
    }

    @Story("Invalid Registration")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify registration fails with an invalid email format")
    public void testRegisterWithInvalidEmail() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();

        String gender = "female";
        String firstName = FakerUtils.generateFirstName();
        String lastName = FakerUtils.generateLastName();
        String email = "invalidemail"; // Invalid
        String password = FakerUtils.generatePassword();

        UserData user = new UserData(gender, firstName, lastName, email, password);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(user);
        Allure.addAttachment("UserData", "application/json",
                new ByteArrayInputStream(userJson.getBytes(StandardCharsets.UTF_8)), ".json");

        RegisterPage page = new RegisterPage(driver);
        page.selectGender(user.getGender());
        page.setFirstName(user.getFirstName());
        page.setLastName(user.getLastName());
        page.setEmail(user.getEmail());
        page.setPassword(user.getPassword());
        page.setConfirmPassword(user.getPassword());
        page.clickRegister();

        Assert.assertTrue(page.getEmailError().contains("Wrong email"));
    }

    @Story("Invalid Registration")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify registration fails with a password shorter than allowed")
    public void testRegisterWithShortPassword() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegisterLink();

        String gender = "female";
        String firstName = FakerUtils.generateFirstName();
        String lastName = FakerUtils.generateLastName();
        String email = FakerUtils.generateEmail();
        String password = "123"; // Too short

        UserData user = new UserData(gender, firstName, lastName, email, password);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(user);
        Allure.addAttachment("UserData", "application/json",
                new ByteArrayInputStream(userJson.getBytes(StandardCharsets.UTF_8)), ".json");

        RegisterPage page = new RegisterPage(driver);
        page.selectGender(user.getGender());
        page.setFirstName(user.getFirstName());
        page.setLastName(user.getLastName());
        page.setEmail(user.getEmail());
        page.setPassword(user.getPassword());
        page.setConfirmPassword(user.getPassword());
        page.clickRegister();

        Assert.assertTrue(page.getPasswordError().contains("The password should have at least 6 characters."));
    }
}