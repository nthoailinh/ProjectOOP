package DataCrawler;

import VietnameseHistorical.Figure;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FigureCrawler extends Crawler<Figure> {
    private static final String URL = "https://thuvienlichsu.com/nhan-vat";

    private static final String JSON_PATH = "data/Figure.json";

    public FigureCrawler(String json_file_path, String page_url) {
        super(json_file_path, page_url);
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
                page_driver.get(PAGE_URL);

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
                previous_page_url = PAGE_URL;
                // go to the next page
                try {
                    WebElement nextElement = page_driver.findElement(By.xpath("//li[@class='next']/a"));
                    PAGE_URL = nextElement.getAttribute("href");
                } catch (NoSuchElementException ignored) {

                }
            } while (!PAGE_URL.equals(previous_page_url));
        }
    }
}
