package crawlers;

import models.Place;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaceCrawler extends Crawler<Place> {
    private static final String URL = "https://thuvienlichsu.com/dia-diem";

    private static final String JSON_PATH = "data/Place.json";

    private final String[] provinces = {"Hà Nội", "Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Cần Thơ", "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái"};

    public PlaceCrawler(String json_file_path, String... page_urls) {
        super(json_file_path, page_urls);
    }

    public static void main(String[] args) throws IOException {
        PlaceCrawler PlaceCrawler = new PlaceCrawler(JSON_PATH, URL);
        PlaceCrawler.run();
    }

    @Override
    public void crawlData() {
        for (String PAGE_URL : PAGE_URLs) {
            do {
            // Navigate to the target URL in page 1, 2, ...
            homePageDriver.get(PAGE_URL);

            // Get listPlaceURL from tag "click"
            List<WebElement> listElements = homePageDriver.findElements(By.className("click"));
            List<String> listPlaceURL = new ArrayList<String>();
            for (WebElement element : listElements) {
                listPlaceURL.add(element.getAttribute("href"));
            }

            // Get url for each figure
            for (String url : listPlaceURL) {
                // Navigate to the target URL in listPlaceUrl
                driver.get(url);

                // Locate the element containing the desired data, e1 for the name and dates, e2 for the description
                WebElement e1 = driver.findElement(By.className("header-edge"));
                List<WebElement> e2 = driver.findElements(By.xpath("/html/body/div[1]/div[3]/div[2]/div[1]/div[3]/div/div[2]"));

                // Get text from the element e1
                String data = e1.getText();

                // Split data. For example "Trần Hưng Đạo (1228 - 1300)" -> "Trần Hưng Đạo" and "(1228 - 1300)"
                String[] parts = data.split("\\(", 2);
                String name = parts[0].trim();

                // Get text from the element e2
                StringBuilder description = new StringBuilder();
                for (WebElement e : e2) {
                    description.append(e.getText());
                }
                String location = "";
                for (String province : provinces) {
                    if (description.toString().contains(province)) {
                        location = province;
                        break;
                    }
                }
                objects.add(new Place(ID, name, location, description.toString()));
                ID++;
                System.out.println("The crawl of the website: " + url + " was successful.");
            }
            previousPageURL = PAGE_URL;
            // go to the next page
            try {
                WebElement nextElement = homePageDriver.findElement(By.xpath("//li[@class='next']/a"));
                PAGE_URL = nextElement.getAttribute("href");
            } catch (NoSuchElementException ignored) {

            }
        } while (!PAGE_URL.equals(previousPageURL));
        }
    }
}
