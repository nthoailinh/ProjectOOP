package services;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverSelenium extends WebDriverSelenium {
    public ChromeDriverSelenium() {
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        this.driver = new ChromeDriver(options);
    }
}
