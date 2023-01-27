package DataCrawler;

import VietnameseHistorical.Dynasty;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

public class DynastyCrawler extends Crawler<Dynasty> {
    private static final String URL = "https://vi.wikipedia.org/wiki/L%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";

    private static final String JSON_PATH = "data/Dynasty.json";

    public DynastyCrawler(String json_file_path, String page_url) {
        super(json_file_path, page_url);
    }

    public static void main(String[] args) throws IOException {
        DynastyCrawler dynastyCrawler = new DynastyCrawler(JSON_PATH, URL);
        dynastyCrawler.run();
    }

    @Override
    public void crawlData() {
        for (String PAGE_URL : PAGE_URLs) {
            driver.get(PAGE_URL);

            List<WebElement> list_objects = driver.findElements(By.xpath("//*[name() = \"h3\" or name() = \"h4\"]"));

            for (WebElement element : list_objects) {
                WebElement name_date = element.findElement(By.xpath("./span[@class='mw-headline']"));
                if (name_date.getAttribute("id").equals("Thời_Hồng_Bàng")) {
                    break;
                }
                String header = name_date.getText();

                // Split data. For example "Trần Hưng Đạo (1228 - 1300)" -> "Trần Hưng Đạo" and "(1228 - 1300)"
                String[] parts = header.split("\\(", 2);
                String name = parts[0].trim();
                String dates = "?";
                // If the dates is empty in data. Set dates = ""
                try {
                    dates = parts[1].trim().replace(")", "");
                } catch (Exception ignored) {
                }

                StringBuilder description = new StringBuilder();
                WebElement pElement = element.findElement(By.xpath("following-sibling::*"));
                while (!pElement.getTagName().contains("h")) {
                    if (!(pElement.getTagName().equals("div") || pElement.getTagName().equals("figure"))) {
                        description.append(pElement.getText()).append("\n");
                    }
                    pElement = pElement.findElement(By.xpath("following-sibling::*"));
                }
                objects.add(new Dynasty(ID, name, dates, description.toString()));
                ID++;
            }
        }
    }
}
