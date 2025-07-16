package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import model.CheckoutData;
import model.UserData;

import java.util.Map;

public class AllureUtils {

    @Attachment(value = "User Data", type = "text/plain")
    public static String logUserData(UserData user) {
        return "Gender: " + user.getGender() + "\n"
                + "First Name: " + user.getFirstName() + "\n"
                + "Last Name: " + user.getLastName() + "\n"
                + "Email: " + user.getEmail() + "\n"
                + "Password: " + user.getPassword();
    }

    @Attachment(value = "Login Credentials", type = "text/plain")
    public static String logLoginData(UserData user) {
        return "Email: " + user.getEmail() + "\n"
                + "Password: " + user.getPassword();
    }

    @Attachment(value = "Checkout Data", type = "text/plain")
    public static String logCheckoutData(CheckoutData data) {
        return "First Name: " + data.getFirstName() + "\n"
                + "Last Name: " + data.getLastName() + "\n"
                + "Email: " + data.getEmail() + "\n"
                + "Company: " + data.getCompany() + "\n"
                + "Country: " + data.getCountry() + "\n"
                + "City: " + data.getCity() + "\n"
                + "Address: " + data.getAddress() + "\n"
                + "ZIP: " + data.getZip() + "\n"
                + "Phone: " + data.getPhone() + "\n"
                + "Fax: " + data.getFax();
    }

    @Attachment(value = "Assertion Detail", type = "text/plain")
    public static String logAssertionMessage(String message) {
        return message;
    }
}
