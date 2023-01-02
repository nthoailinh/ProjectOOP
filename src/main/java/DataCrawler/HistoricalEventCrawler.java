package DataCrawler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.NoSuchElementException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoricalEventCrawler {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:/Users/DELL/Downloads/chromedriver_win32/chromedriver.exe");

        String page_url = "https://thuvienlichsu.com/su-kien";
        String previous_page_url = "";

        // Create a new ChromeDriver instance
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriver page_driver = new ChromeDriver(chromeOptions);
        
        // jsonArray to save jsonObject
        JSONArray jsonArray = new JSONArray();

        do {
            // Navigate to the target URL in page 1, 2, ... 
            page_driver.get(page_url);

            // Get list_figure_url from tag "click"
            List<WebElement> list_elements = page_driver.findElements(By.className("click"));
            List<String> list_figure_url = new ArrayList<String>();
            for (WebElement element : list_elements) {
                list_figure_url.add(element.getAttribute("href"));
            }

            // Get url for each figure
            for (String url : list_figure_url) {
                // Navigate to the target URL in list_figure_url
                driver.get(url);

                // Locate the element containing the desired data, e1 for the name and dates, e2 for the description
                WebElement e1 = driver.findElement(By.className("header-edge"));
                WebElement e2 = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div[1]/div[3]/div/div[2]"));

                // Get text from the element e1
                String data = e1.getText();
                String description = e2.getText();
                // Save data in jsonObject
                JSONObject jsonObject = new JSONObject();

                // Split data. For example "Trần Hưng Đạo (1228 - 1300)" -> "Trần Hưng Đạo" and "(1228 - 1300)"
                String[] parts = data.split("\\(", 2);
                String name = parts[0].trim();
                String dates = "? - ?";
                // If the dates is empty in data. Set dates = ""
                try {
                    dates = parts[1].trim().replace(")", "");
                } catch (Exception ignored) {}
                
            	if(parts[1].trim().charAt(0) == '-') {
            		dates = "? " + dates;
            	}
                // put name, dates and description into json object
                jsonObject.put("name", name);
                jsonObject.put("dates", dates);

                // Get text from the element e2
                jsonObject.put("description", description);

                // put jsonObject into jsonArray
                jsonArray.put(jsonObject);
                System.out.println("Website: " + url + " crawl successful");
            }
            previous_page_url = page_url;
            // go to the next page
            try {
                WebElement nextElement = page_driver.findElement(By.xpath("//li[@class='next']/a"));
                page_url = nextElement.getAttribute("href");
            } catch (NoSuchElementException ignored) {

            }
        } while (!page_url.equals(previous_page_url));


        // Save jsonArray to file
        try (FileWriter file = new FileWriter("C:/Users/DELL/Desktop/ProjectOOP/data/HistoricalEvent.json", true)) {
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
