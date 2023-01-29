package crawlers;

import com.google.gson.Gson;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Crawler<T> {
    protected static int ID = 0;
    protected List<T> objects;
    protected WebDriver driver;
    protected WebDriver homePageDriver;
    protected final Gson gson;
    protected final String WEBDRIVER_NAME = "webdriver.chrome.driver";
    protected final String DRIVER_PATH = "/usr/bin/chromedriver";
    protected final List<String> PAGE_URLs = new ArrayList<>();
    protected final String JSON_FILE_PATH;
    protected String previousPageURL = "";

    public Crawler(String json_file_path, String ...page_urls) {
        System.setProperty(WEBDRIVER_NAME, DRIVER_PATH);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
        homePageDriver = new ChromeDriver(chromeOptions);
        gson = new Gson();
        objects = new ArrayList<>();
        this.JSON_FILE_PATH = json_file_path;
        Collections.addAll(this.PAGE_URLs, page_urls);
    }

    public void run() throws IOException {
        crawlData();
        saveDataToFile();
        driver.quit();
        homePageDriver.quit();
    }

    public void saveDataToFile() throws IOException {
        // convert the list to a JSON array
        String json = gson.toJson(objects);

        // write the JSON array to a file
        FileWriter writer = new FileWriter(JSON_FILE_PATH);
        writer.write(json);
        writer.close();
    }
    public abstract void crawlData();
}
