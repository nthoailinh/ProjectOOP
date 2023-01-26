package DataCrawler;

import com.google.gson.Gson;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Crawler<T> {
    protected static int ID = 0;
    protected final List<T> objects;
    protected final WebDriver driver;
    protected final Gson gson;
    protected final String WEBDRIVER_NAME = "webdriver.chrome.driver";
    protected final String DRIVER_PATH = "/usr/bin/chromedriver";
    protected final String PAGE_URL;
    protected final String JSON_FILE_PATH;

    public Crawler(String page_url, String json_file_path) {
        System.setProperty(WEBDRIVER_NAME, DRIVER_PATH);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
        gson = new Gson();
        objects = new ArrayList<>();
        this.PAGE_URL = page_url;
        this.JSON_FILE_PATH = json_file_path;
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
