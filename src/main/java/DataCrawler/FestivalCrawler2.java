package DataCrawler;

import VietnameseHistorical.Dynasty;
import VietnameseHistorical.Festival;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.NoSuchElementException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FestivalCrawler2 {
    public static int ID = 0;
    public static void main(String[] args) throws NoSuchElementException, IOException {
        long start = System.currentTimeMillis();
        // Set the path to the ChromeDriver executable
        System.setProperty("web-driver.chrome.driver", "/usr/bin/chromedriver");

        String main_page_url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#Danh_s%C3%A1ch_m%E1%BB%99t_s%E1%BB%91_l%E1%BB%85_h%E1%BB%99i";
        String festival_description_link = "";

        // Create a new ChromeDriver instance
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        WebDriver main_page_driver = new ChromeDriver(chromeOptions);
        WebDriver subpage_driver = new ChromeDriver(chromeOptions);

        Gson gson = new Gson();
        List<Festival> festivals = gson.fromJson(new FileReader("data/Festival.json"), new TypeToken<List<Festival>>() {
        }.getType());
        ID = festivals.size();

        main_page_driver.get(main_page_url);
        List<WebElement> e1 = main_page_driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/table[2]/tbody/tr"));
        WebElement cell = null;
        for (int i = 1; i < e1.size(); i++) {
            StringBuffer festival_description = new StringBuffer();

            cell = e1.get(i).findElement(By.xpath("td[1]"));
            String festival_date = cell.getText();
//            System.out.println(festival_date);

            cell = e1.get(i).findElement(By.xpath("td[2]"));
            festival_description.append("Địa điểm tổ chức: " + cell.getText() + ".\n");
//            System.out.println(festival_description);

            cell = e1.get(i).findElement(By.xpath("td[3]"));
            String festival_name = cell.getText();
            System.out.println(festival_name);

            cell = e1.get(i).findElement(By.xpath("td[4]"));
            festival_description.append("Lần đầu tổ chức: " + cell.getText() + ".\n");

            cell = e1.get(i).findElement(By.xpath("td[5]"));
            festival_description.append("Nhân vật liên quan: " + cell.getText() + ".\n");

            try {
                cell = e1.get(i).findElement(By.xpath("td[6]"));
            } catch (NoSuchElementException e) {

            }
            festival_description.append("Ghi chú: " + cell.getText() + ".\n");

            try {
                cell = e1.get(i).findElement(By.xpath("td[3]")).findElement(By.xpath("a"));
            } catch (NoSuchElementException e){
                cell = e1.get(i).findElement(By.xpath("td[3]")).findElement(By.xpath("b")).findElement(By.xpath("a"));
            }

            try {
                festival_description_link = cell.getAttribute("href");
                subpage_driver.get(festival_description_link);
                try {
                    WebElement festival_description_element = subpage_driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/p[1]"));
                    festival_description.append(festival_description_element.getText());
                } catch (NoSuchElementException e) {
                    // Handle the exception
                    e.printStackTrace();
                }
            } catch (NoSuchElementException e) {
                // Handle the exception
                e.printStackTrace();
            }
            festivals.add(new Festival(ID, festival_name, festival_date, festival_description.toString()));
            ID++;
        }

        // convert the list to a JSON array
        String json = gson.toJson(festivals);

        // write the JSON array to a file
        FileWriter writer = new FileWriter("data/Festival.json");
        writer.write(json);
        writer.close();

        // Close the browser
        main_page_driver.quit();
        subpage_driver.quit();

        System.out.println("Time: " + ((System.currentTimeMillis() - start)) / 1000);
    }
}
