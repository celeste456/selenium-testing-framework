package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import model.CheckoutData;
import model.UserData;

import java.util.Map;

public class AllureUtils {

    @Attachment(value = "Assertion Detail", type = "text/plain")
    public static String logAssertionMessage(String message) {
        return message;
    }
}
