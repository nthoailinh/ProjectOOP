package DataCrawler;

import VietnameseHistorical.Dynasty;
import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynastyCrawler {
    public static int ID = 1;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

        String page_url = "https://vi.wikipedia.org/wiki/L%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";

        // Create a new ChromeDriver instance
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);

        Gson gson = new Gson();
        List<Dynasty> dynasties = new ArrayList<>();

        driver.get(page_url);

        List<WebElement> list_dynasties = driver.findElements(By.xpath("//*[name() = \"h3\" or name() = \"h4\"]"));

        for (WebElement element : list_dynasties) {
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
            dynasties.add(new Dynasty(ID, name, dates, description.toString()));
            ID++;
        }

        // convert the list to a JSON array
        String json = gson.toJson(dynasties);

        // write the JSON array to a file
        FileWriter writer = new FileWriter("data/Dynasty.json");
        writer.write(json);
        writer.close();

        // Close the browser
        driver.quit();

        System.out.println("Time: " + ((System.currentTimeMillis() - start)) / 1000);
    }
}
