package DataCrawler;

import VietnameseHistorical.Festival;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
        String festival_description_link;
        try {
            objects = gson.fromJson(new FileReader("data/Festival.json"), new TypeToken<List<Festival>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ID = objects.size();

        driver.get(URL);
        List<WebElement> records = driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/table[2]/tbody/tr"));
        for (int i = 1; i < records.size(); i++) {
            String festival_date = records.get(i).findElement(By.xpath("td[1]")).getText();
            String festival_name = records.get(i).findElement(By.xpath("td[2]")).findElement(By.xpath("a")).getText();
            StringBuilder festival_description = new StringBuilder("Địa điểm tổ chức: " + records.get(i).findElement(By.xpath("td[3]")).findElement(By.xpath("a")).getText() + ".\n");
            try {
                festival_description_link = records.get(i).findElement(By.xpath("td[2]")).findElement(By.xpath("a")).getAttribute("href");
                page_driver.get(festival_description_link);
                WebElement festival_description_element = page_driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/p[1]"));
                festival_description.append(festival_description_element.getText());
            } catch (NoSuchElementException ignored) {

            }
            objects.add(new Festival(ID, festival_name, festival_date, festival_description.toString()));
            ID++;
            System.out.println("Crawl successful: " + festival_name + ".");
        }
        page_driver.quit();
    }
}
