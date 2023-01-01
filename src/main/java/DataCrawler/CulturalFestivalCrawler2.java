package DataCrawler;

import org.json.JSONArray;
import org.json.JSONObject;
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

public class CulturalFestivalCrawler2 {

    public static void main(String[] args) throws NoSuchElementException {
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

        // jsonArray to save jsonObject
        JSONArray jsonArray = new JSONArray();

        main_page_driver.get(main_page_url);

        List<WebElement> festivals = main_page_driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/table[2]/tbody/tr"));
//        System.out.println(festivals.get(1).getAttribute("outerHTML"));
//        System.out.println(festivals.get(2).getAttribute("outerHTML"));
        WebElement cell1 = null;
        WebElement cell2 = null;
        for (int i = 1; i < festivals.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            cell1 = festivals.get(i).findElement(By.xpath("td[1]"));
//            System.out.println(cell1.getAttribute("outerHTML"));
            String festival_date = cell1.getText();
            jsonObject.put("dates", festival_date);

            cell2 = festivals.get(i).findElement(By.xpath("td[2]")).findElement(By.xpath("a"));
//            System.out.println(cell2.getAttribute("outerHTML"));
            String festival_name = cell2.getText();
            jsonObject.put("name", festival_name);

            try {
                String festival_description = "";
                festival_description_link = cell2.getAttribute("href");
//                System.out.println(festival_description_link);

                subpage_driver.get(festival_description_link);
                WebElement festival_description_element = subpage_driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div[1]/p[1]"));
                festival_description = festival_description_element.getText();
//                System.out.println(festival_description);
                jsonObject.put("description", festival_description);
            } catch (NoSuchElementException e) {
                // Handle the exception
            }

            jsonArray.put(jsonObject);
        }

        // Save jsonArray to file
        try (FileWriter file = new FileWriter("data/CulturalFestival2.json", false)) {
            file.write(jsonArray.toString());
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the browser
        main_page_driver.quit();
        subpage_driver.quit();

        System.out.println("Time: " + ((System.currentTimeMillis() - start)) / 1000);
    }
}
