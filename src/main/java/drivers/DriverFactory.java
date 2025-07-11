package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        if (driver.get() == null) {
            ChromeOptions options = new ChromeOptions();

            // remove password manager
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("autofill.profile_enabled", false);
            prefs.put("autofill.credit_card_enabled", false);
            prefs.put("profile.autofill_address_enabled", false);

            options.setExperimentalOption("prefs", prefs);

            // For CI
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-autofill-keyboard-accessory-view[8]");
            options.addArguments("--disable-autofill");
            options.addArguments("--headless=new"); // headless
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu"); // for Linux

            driver.set(new ChromeDriver(options));
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
