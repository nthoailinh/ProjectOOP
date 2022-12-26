package DataCrawler;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
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
        WebElement e1 = driver.findElement(By.className("header-edge"));
        List<WebElement> e2 = driver.findElements(By.xpath("//div[contains(@class,'mb-3')]//div[contains(@class,'card-body')]//p[contains(@class,'card-text')]"));

        // Get text from the element e1
        String data = e1.getText();

        // Save data in json file
        JSONObject dataJson = new JSONObject();

        // Split data. For example "Trần Hưng Đạo (1228 - 1300)" -> "Trần Hưng Đạo" and "(1228 - 1300)"
        String[] parts = data.split("\\(", 2);
        String name = parts[0].trim();
        String dates = parts[1].trim().replace(")", "");

        dataJson.put("name", name);
        dataJson.put("dates", dates);

        // Get text from the element e4
        StringBuilder description = new StringBuilder();
        for (WebElement e : e2) {
            description.append(e.getText());
        }

        dataJson.put("description", description.toString());

        // Close the browser
        driver.quit();

        // Save dataJson to file
        try (FileWriter file = new FileWriter("data/HistoricalFigure.json")) {
            file.write(dataJson.toString());
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}