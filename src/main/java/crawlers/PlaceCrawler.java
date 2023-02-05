package crawlers;

import models.Place;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceCrawler extends CrawlerWithHomePage<Place> {
    private static final String URL = "https://thuvienlichsu.com/dia-diem";

    private static final String JSON_PATH = "data/Place.json";

    private final String[] provinces = {"Hà Nội", "Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Cần Thơ", "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên-Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Biên Hòa"};

    private final String[] countries = {"Việt Nam", "Pháp", "Canada", "Mỹ", "Anh", "Ý", "Úc", "Nhật Bản", "Hàn Quốc", "Thụy Điển", "Đức", "Italy", "Tây Ban Nha", "Bồ Đào Nha", "Đan Mạch", "Nga", "Phần Lan", "Thổ Nhĩ Kỳ", "Na Uy", "Mexico", "Brazil", "Argentina", "Chile", "Colombia", "Peru", "Ecuador", "Venezuela", "Uruguay", "Paraguay", "Indonesia", "Malaysia", "Singapore", "Thái Lan", "Trung Quốc", "Nhật Bản", "Philippines", "Australia", "New Zealand", "Đài Loan", "Campuchia", "Lào", "Myanmar (Birmania)", "Pakistan", "Bangladesh", "India", "Sri Lanka"};
    Map<String, Integer> provinceCounts = new HashMap<>();
    public PlaceCrawler(String json_file_path, String... page_urls) {
        super(json_file_path, page_urls);
        for (String province : provinces) {
            provinceCounts.put(province, 0);
        }
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
                    // Find location
                    String location = findLocation(name, countries, provinces, description.toString());
                    System.out.println("Location " + location);

                    objects.add(new Place(ID, name, location, description.toString()));


                    //
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
    private static String findLocation(String name, String[] countries, String[] provinces, String description) {
        Map<String, Integer> provinceCounts = new HashMap<>();
        for (String province : provinces) {
            provinceCounts.put(province, 0);
        }

        for (String country : countries) {
            if (name.equals(country)) {
                return country;
            }
        }

        for (String province : provinces) {
            if (name.equals(province)) {
                return province;
            }
        }

        String[] words = description.split(" ");
        List<String> threeWords = new ArrayList<>();
        for (int i = 0; i < words.length - 2; i++) {
            threeWords.add(words[i] + " " + words[i + 1] + " " + words[i + 2]);
        }

        for (String threeWord : threeWords) {
            for (String province : provinces) {
                if (threeWord.contains(province)) {
                    int count = provinceCounts.get(province);
                    provinceCounts.put(province, count + 1);
                }
            }
        }

        int maxCount = 0;
        String maxProvince = "";
        for (Map.Entry<String, Integer> entry : provinceCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxProvince = entry.getKey();
            }
        }

        return maxProvince;
    }
}
