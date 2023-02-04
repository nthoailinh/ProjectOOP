package crawlers;

import models.Festival;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FestivalCrawler3 extends Crawler<Festival> {
    private static final String URL = "https://vi.wikipedia.org/wiki/C%C3%A1c_ng%C3%A0y_l%E1%BB%85_%E1%BB%9F_Vi%E1%BB%87t_Nam";

    private static final String JSON_PATH = "data/Festival.json";

    public FestivalCrawler3(String json_file_path, String... page_urls) {
        super(json_file_path, page_urls);
    }

    public static void main(String[] args) throws IOException {
        FestivalCrawler3 festivalCrawler = new FestivalCrawler3(JSON_PATH, URL);
        festivalCrawler.run();
    }

    @Override
    public void crawlData() {
        String festivalDescriptionLink = "";
        try {
            objects = gson.fromJson(JSON_PATH, new TypeToken<List<Festival>>() {}.getType());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ID = objects.size();

        homePageDriver.get(URL);
        List<WebElement> records = homePageDriver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/table[2]/tbody/tr"));
        for (int i = 1; i < records.size(); i++) {
            String festivalDate = records.get(i).findElement(By.xpath("td[1]")).getText();
            String festivalName = records.get(i).findElement(By.xpath("td[2]")).findElement(By.xpath("a")).getText();
            StringBuilder festivalDescription = new StringBuilder("Địa điểm tổ chức: " + records.get(i).findElement(By.xpath("td[3]")).findElement(By.xpath("a")).getText() + ".\n");
            try {
                festivalDescriptionLink = records.get(i).findElement(By.xpath("td[2]")).findElement(By.xpath("a")).getAttribute("href");
                driver.get(festivalDescriptionLink);
                WebElement festivalDescriptionElement = driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/p[1]"));
                festivalDescription.append(festivalDescriptionElement.getText());
            } catch (NoSuchElementException ignored) {

            }
            objects.add(new Festival(ID, festivalName, festivalDate, festivalDescription.toString()));
            ID++;
            System.out.println("Crawl successful: " + festivalName + ".");
        }
    }
}
