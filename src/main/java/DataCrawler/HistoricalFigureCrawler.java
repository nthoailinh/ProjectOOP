package DataCrawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.json.JSONObject;

import java.io.IOException;
import java.io.FileWriter;
import java.util.List;

public class HistoricalFigureCrawler {
    public static void main(String[] args) throws IOException {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

        // Create a new ChromeDriver instance
        WebDriver driver = new ChromeDriver();

        // Navigate to the target URL
        driver.get("https://thuvienlichsu.com/nhan-vat/tran-hung-dao-2");

        // Locate the element containing the desired data
        WebElement element = driver.findElement(By.className("header-edge"));

        // Get text from the element
        String data = element.getText();

        // Close the browser
        driver.quit();

        // Save data in json file
        JSONObject dataJson = new JSONObject();

        // Split data. For example "Trần Hưng Đạo (1228 - 1300)" -> "Trần Hưng Đạo" and "(1228 - 1300)"
        String[] parts = data.split("\\(", 2);
        String name = parts[0].trim();
        String dates = parts[1].trim().replace(")", "");
        dataJson.put("name", name);
        dataJson.put("dates", dates);

        // Save dataJson to file
        try (FileWriter file = new FileWriter("data/HistoricalFigure.json")) {
            file.write(dataJson.toString());
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}