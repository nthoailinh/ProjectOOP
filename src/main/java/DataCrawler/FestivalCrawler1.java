package DataCrawler;

import VietnameseHistorical.Festival;
import org.openqa.selenium.By;

import java.io.IOException;

public class FestivalCrawler1 extends Crawler<Festival> {
    private static final String URL = "https://vietnamtravellog.com/";

    private static final String JSON_PATH = "data/Festival.json";

    public FestivalCrawler1(String json_file_path, String ...page_urls) {
        super(json_file_path, page_urls);
    }

    public static void main(String[] args) throws IOException {
        FestivalCrawler1 festivalCrawler1 = new FestivalCrawler1(JSON_PATH,
                "https://vietnamtravellog.com/le_hoi/le-hoi-dien-truong-ba/",
                "https://vietnamtravellog.com/le_hoi/le-hoi-dem-ram-hoi-an-to-diem-sac-mau-lung-linh-noi-pho-co-yen-binh/",
                "https://vietnamtravellog.com/le_hoi/hoi-le-mat/",
                "https://vietnamtravellog.com/le_hoi/hoi-go-dong-da/",
                "https://vietnamtravellog.com/le_hoi/le-hoi-yen-the/",
                "https://vietnamtravellog.com/le_hoi/le-hoi-ba-thu-bon-quang-nam-le-hoi-biet-on-nguoi-me-cua-que-huong/",
                "http://vietnamtravellog.com/le_hoi/le-hoi-dam-o-loan/",
                "http://vietnamtravellog.com/le_hoi/le-hoi-gio-to-nghe-kim-hoan/",
                "http://vietnamtravellog.com/le_hoi/le-hoi-mai-an-tiem/",
                "http://vietnamtravellog.com/le_hoi/le-hoi-ooc-om-bok/",
                "https://vietnamtravellog.com/le_hoi/le-khao-le-the-linh-hoang-sa/",
                "https://vietnamtravellog.com/le_hoi/le-hoi-lam-kinh/",
                "https://vietnamtravellog.com/le_hoi/le-hoi-quan-the-am/",
                "https://vietnamtravellog.com/le_hoi/festival-hue/",
                "https://vietnamtravellog.com/le_hoi/festival-phao-hoa-quoc-te-da-nang/",
                "https://vietnamtravellog.com/le_hoi/hoi-giong/");
        festivalCrawler1.run();
    }

    public void crawlData() {
        for (String page_url : PAGE_URLs) {
            driver.get(page_url);
            String festival_name = driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/div/div/h2")).getText();
            String festival_date = driver.findElement(By.xpath("//*[@id=\"main\"]/div[3]/div/div/div/div[2]/div/div[2]/p[2]/b")).getText();
            String festival_description = driver.findElement(By.xpath("//*[@id=\"main\"]/div[3]/div/div/div/div[2]/div/div[2]/div")).getText();
            objects.add(new Festival(ID, festival_name, festival_date, festival_description));
            ID++;
            System.out.println("Website: " + page_url + " crawl successful");
        }
    }
}
