package crawlers;

import models.Event;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventCrawler extends CrawlerWithHomePage<Event> {
    private static final String URL = "https://thuvienlichsu.com/su-kien";

    private static final String JSON_PATH = "data/Event.json";

    public EventCrawler(String json_file_path, String... page_urls) {
        super(json_file_path, page_urls);
    }

    public static void main(String[] args) throws IOException {
        EventCrawler eventCrawler = new EventCrawler(JSON_PATH, URL);
        eventCrawler.run();
    }

    @Override
    public void crawlData() {
        for (String PAGE_URL : PAGE_URLs) {
            do {
                // Navigate to the target URL in page 1, 2, ...
                homePageDriver.get(PAGE_URL);

                // Get listFigureURL from tag "click"
                List<WebElement> listElements = homePageDriver.findElements(By.className("click"));
                List<String> listFigureURL = new ArrayList<String>();
                for (WebElement element : listElements) {
                    listFigureURL.add(element.getAttribute("href"));
                }

                // Get url for each figure
                for (String url : listFigureURL) {
                    // Navigate to the target URL in listFigureURL
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
                    
                    objects.add(new Event(ID, name, dates, description));
                    ID++;

                    System.out.println("Website: " + url + " crawl successful");
                }
                previousPageURL = PAGE_URL;
                // go to the next page
                try {
                    WebElement nextElement = homePageDriver.findElement(By.xpath("//li[@class='next']/a"));
                    PAGE_URL = nextElement.getAttribute("href");
                } catch (NoSuchElementException ignored) {

                }
            } while (!PAGE_URL.equals(previousPageURL));
        }
    }
}
