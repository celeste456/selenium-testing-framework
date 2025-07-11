package utils;

import io.qameta.allure.Allure;
import model.CheckoutData;
import model.UserData;

import java.util.Map;

public class AllureUtils {

    public static void logUserData(UserData user) {
        String data = "Gender: " + user.getGender() + "\n"
                + "First Name: " + user.getFirstName() + "\n"
                + "Last Name: " + user.getLastName() + "\n"
                + "Email: " + user.getEmail() + "\n"
                + "Password: " + user.getPassword();
        Allure.addAttachment("User Data", data);
    }

    public static void logLoginData(UserData user) {
        String data = "Email: " + user.getEmail() + "\n"
                + "Password: " + user.getPassword();
        Allure.addAttachment("Login Credentials", data);
    }

    public static void logCheckoutData(CheckoutData data) {
        String content = "First Name: " + data.getFirstName() + "\n"
                + "Last Name: " + data.getLastName() + "\n"
                + "Email: " + data.getEmail() + "\n"
                + "Company: " + data.getCompany() + "\n"
                + "Country: " + data.getCountry() + "\n"
                + "City: " + data.getCity() + "\n"
                + "Address: " + data.getAddress() + "\n"
                + "ZIP: " + data.getZip() + "\n"
                + "Phone: " + data.getPhone() + "\n"
                + "Fax: " + data.getFax();
        Allure.addAttachment("Checkout Data", content);
    }

    public static void logAssertionMessage(String message) {
        Allure.addAttachment("Assertion Detail", message);
    }
}
