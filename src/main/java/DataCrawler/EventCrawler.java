package DataCrawler;

import VietnameseHistorical.Event;
import com.google.gson.Gson;
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

public class EventCrawler {
    public static int ID = 0;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // Set the path to the ChromeDriver executable
        System.setProperty("web-" +
                "driver.chrome.driver", "/usr/bin/chromedriver");

        String page_url = "https://thuvienlichsu.com/su-kien";
        String previous_page_url = "";

        // Create a new ChromeDriver instance
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriver page_driver = new ChromeDriver(chromeOptions);

        Gson gson = new Gson();
        List<Event> events = new ArrayList<>();

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
                List<WebElement> e3 = driver.findElements(By.className("/html/body/div[1]/div[3]/div[2]/div[1]/div[5]/div//div[@class='card']"));
                // Get text from the element e1
                String data = e1.getText();
                String description = e2.getText();

                // Split data. For example "Trần Hưng Đạo (1228 - 1300)" -> "Trần Hưng Đạo" and "(1228 - 1300)"
                String[] parts = data.split("\\(", 2);
                String name = parts[0].trim();
                String dates = "? - ?";
                // If the dates is empty in data. Set dates = ""
                try {
                    dates = parts[1].trim().replace(")", "");
                } catch (Exception ignored) {
                }

                if (parts[1].trim().charAt(0) == '-') {
                    dates = "? " + dates;
                }

                for (WebElement e : e3) {
                    String figure = e.findElement(By.xpath("./div/div[2]/div[1]/a/h3")).getText();

                }

                events.add(new Event(ID, name, dates, description));
                ID++;

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


        // convert the list to a JSON array
        String json = gson.toJson(events);

        // write the JSON array to a file
        FileWriter writer = new FileWriter("data/Event1.json");
        writer.write(json);
        writer.close();

        // Close the browser
        driver.quit();

        System.out.println("Time: " + ((System.currentTimeMillis() - start)) / 1000);
    }
}