package crawlers;

import models.Figure;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FigureCrawler extends Crawler<Figure> {
    private static final String URL = "https://thuvienlichsu.com/nhan-vat";

    private static final String JSON_PATH = "data/Figure.json";

    public FigureCrawler(String JSON_PATH, String URL) {
        super(JSON_PATH, URL);
    }

    public static void main(String[] args) throws IOException {
        FigureCrawler figureCrawler = new FigureCrawler(JSON_PATH, URL);
        figureCrawler.run();
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
                    List<WebElement> e2 = driver.findElements(By.xpath("//div[contains(@class,'mb-3')]//div[contains(@class,'card-body')]//p[contains(@class,'card-text')]"));

                    // Get text from the element e1
                    String data = e1.getText();

                    // Split data. For example "Trần Hưng Đạo (1228 - 1300)" -> "Trần Hưng Đạo" and "(1228 - 1300)"
                    String[] parts = data.split("\\(", 2);
                    String name = parts[0].trim();
                    String dates = "? - ?";
                    // If the dates is empty in data. Set dates = ""
                    try {
                        dates = parts[1].trim().replace(")", "");
                    } catch (Exception ignored) {
                    }
                    StringBuilder description = new StringBuilder();
                    for (WebElement e : e2) {
                        description.append(e.getText());
                    }
                    objects.add(new Figure(ID, name, dates, description.toString()));
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
