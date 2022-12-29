package DataCrawler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CountSubPage {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

        List<String> urls = new ArrayList<String>();

        // List url sub page
        for (int i = 1; i <= 2; i++) {
            urls.add("https://thuvienlichsu.com/nhan-vat?page=" + i);
        }

        // Create a new ChromeDriver instance
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);

        JSONArray jsonArray = new JSONArray();

        for (String link_url : urls) {
            // Navigate to the target URL
            driver.get(link_url);

            List<WebElement> list_element = driver.findElements(By.className("click"));

            List<String> list_url_figure = new ArrayList<String>();

            for (WebElement element : list_element) {
                list_url_figure.add(element.getAttribute("href"));
            }

            for (String url : list_url_figure) {
                // Navigate to the target URL in list_url_figure
                driver.get(url);
                // Locate the element containing the desired data, e1 for the name and dates, e2 for the description
                WebElement e1 = driver.findElement(By.className("header-edge"));
                List<WebElement> e2 = driver.findElements(By.xpath("//div[contains(@class,'mb-3')]//div[contains(@class,'card-body')]//p[contains(@class,'card-text')]"));

                // Get text from the element e1
                String data = e1.getText();

                // Save data in json object
                JSONObject jsonObject = new JSONObject();

                // Split data. For example "Trần Hưng Đạo (1228 - 1300)" -> "Trần Hưng Đạo" and "(1228 - 1300)"
                String[] parts = data.split("\\(", 2);
                String name = parts[0].trim();
                String dates = "? - ?";
                // If the dates is empty in data. Set dates = ""
                try {
                    dates = parts[1].trim().replace(")", "");
                } catch (Exception ignored) {

                }
                // put name, dates and description into json object
                jsonObject.put("name", name);
                jsonObject.put("dates", dates);

                // Get text from the element e2
                StringBuilder description = new StringBuilder();
                for (WebElement e : e2) {
                    description.append(e.getText());
                }
                jsonObject.put("description", description.toString());
                // put json object into jsonArray
                jsonArray.put(jsonObject);
                System.out.println("Website: " + url + " crawl successful");
            }
        }
        // Save jsonArray to file
        try (FileWriter file = new FileWriter("data/HistoricalFigure.json", true)) {
            file.write(jsonArray.toString());
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Close the browser
        driver.quit();
        System.out.println("Time: " + ((System.currentTimeMillis() - start)) / 1000);
    }
}