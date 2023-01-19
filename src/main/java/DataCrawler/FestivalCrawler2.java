package DataCrawler;

import VietnameseHistorical.Festival;
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

public class FestivalCrawler2 {
    public static int ID = 1;
    public static void main(String[] args) throws NoSuchElementException, IOException {
        long start = System.currentTimeMillis();
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

        String main_page_url = "https://vi.wikipedia.org/wiki/C%C3%A1c_ng%C3%A0y_l%E1%BB%85_%E1%BB%9F_Vi%E1%BB%87t_Nam";
        String festival_description_link = "";

        // Create a new ChromeDriver instance
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        WebDriver main_page_driver = new ChromeDriver(chromeOptions);
        WebDriver subpage_driver = new ChromeDriver(chromeOptions);

        Gson gson = new Gson();
        List<Festival> festivals = new ArrayList<>();

        main_page_driver.get(main_page_url);

        List<WebElement> e1 = main_page_driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/table[2]/tbody/tr"));
        WebElement cell1 = null;
        WebElement cell2 = null;
        for (int i = 1; i < e1.size(); i++) {
            cell1 = e1.get(i).findElement(By.xpath("td[1]"));
            String festival_date = cell1.getText();

            cell2 = e1.get(i).findElement(By.xpath("td[2]")).findElement(By.xpath("a"));
            String festival_name = cell2.getText();
            String festival_description = "";
            try {
                festival_description_link = cell2.getAttribute("href");
                subpage_driver.get(festival_description_link);
                WebElement festival_description_element = subpage_driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/p[1]"));
                festival_description = festival_description_element.getText();
            } catch (NoSuchElementException e) {
                // Handle the exception
            }
            festivals.add(new Festival(ID, festival_name, festival_date, festival_description));
            ID++;
        }

        // convert the list to a JSON array
        String json = gson.toJson(festivals);

        // write the JSON array to a file
        FileWriter writer = new FileWriter("data/Festival2.json");
        writer.write(json);
        writer.close();

        // Close the browser
        main_page_driver.quit();
        subpage_driver.quit();

        System.out.println("Time: " + ((System.currentTimeMillis() - start)) / 1000);
    }
}
