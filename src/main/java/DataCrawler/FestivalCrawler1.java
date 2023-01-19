package DataCrawler;

import VietnameseHistorical.Festival;
import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FestivalCrawler1 {
    public static int ID = 1;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

        List<String> page_urls = new ArrayList<>();
        page_urls.add("https://vietnamtravellog.com/le_hoi/le-hoi-dien-truong-ba/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/le-hoi-dem-ram-hoi-an-to-diem-sac-mau-lung-linh-noi-pho-co-yen-binh/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/hoi-le-mat/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/hoi-go-dong-da/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/le-hoi-yen-the/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/le-hoi-ba-thu-bon-quang-nam-le-hoi-biet-on-nguoi-me-cua-que-huong/");
        page_urls.add("http://vietnamtravellog.com/le_hoi/le-hoi-dam-o-loan/");
        page_urls.add("http://vietnamtravellog.com/le_hoi/le-hoi-gio-to-nghe-kim-hoan/");
        page_urls.add("http://vietnamtravellog.com/le_hoi/le-hoi-mai-an-tiem/");
        page_urls.add("http://vietnamtravellog.com/le_hoi/le-hoi-ooc-om-bok/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/le-khao-le-the-linh-hoang-sa/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/le-hoi-lam-kinh/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/le-hoi-quan-the-am/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/festival-hue/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/festival-phao-hoa-quoc-te-da-nang/");
        page_urls.add("https://vietnamtravellog.com/le_hoi/hoi-giong/");

        // Create a new ChromeDriver instance
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);

        Gson gson = new Gson();
        List<Festival> festivals = new ArrayList<>();

        for (String page_url : page_urls) {
            driver.get(page_url);
            String festival_name = driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/div/div/h2")).getText();
            System.out.println(festival_name);

            String festival_date = driver.findElement(By.xpath("//*[@id=\"main\"]/div[3]/div/div/div/div[2]/div/div[2]/p[2]/b")).getText();
            System.out.println(festival_date);

            String festival_description = driver.findElement(By.xpath("//*[@id=\"main\"]/div[3]/div/div/div/div[2]/div/div[2]/div")).getText();
            System.out.println(festival_description);

            festivals.add(new Festival(ID, festival_name, festival_date, festival_description));
            ID++;
            System.out.println("Website: " + page_url + " crawl successful");
        }


        // convert the list to a JSON array
        String json = gson.toJson(festivals);

        // write the JSON array to a file
        FileWriter writer = new FileWriter("data/Festival1.json");
        writer.write(json);
        writer.close();

        // Close the browser
        driver.quit();

        System.out.println("Time: " + ((System.currentTimeMillis() - start)) / 1000);
    }
}