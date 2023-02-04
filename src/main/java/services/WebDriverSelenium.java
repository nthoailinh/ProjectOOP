package services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WebDriverSelenium implements IWebDriver<WebElement, By>{
    protected WebDriver driver;
    @Override
    public void get(String url) {
        driver.get(url);
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    @Override
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

}
