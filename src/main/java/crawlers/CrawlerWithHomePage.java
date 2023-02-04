package crawlers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import services.ChromeDriverSelenium;
import services.IWebDriver;

import java.io.IOException;

public abstract class CrawlerWithHomePage<T> extends Crawler<T>{
    protected IWebDriver<WebElement, By> homePageDriver;
    public CrawlerWithHomePage(String json_file_path, String... page_urls) {
        super(json_file_path, page_urls);
        homePageDriver = new ChromeDriverSelenium();
    }

    @Override
    public void run() throws IOException {
        super.run();
        homePageDriver.quit();
    }

    public abstract void crawlData();
}
