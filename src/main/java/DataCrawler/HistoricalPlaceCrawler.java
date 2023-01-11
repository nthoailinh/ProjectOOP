package DataCrawler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoricalPlaceCrawler {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // Set the path to the ChromeDriver executable
        // Diff: link to path chromedriver
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        String page_url = "https://thuvienlichsu.com/dia-diem";
        String previous_page_url = "";

        // Create a new ChromeDriver instance
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriver page_driver = new ChromeDriver(chromeOptions);

        // jsonArray to save jsonObject
        JSONArray jsonArray = new JSONArray();

        // Location of Vietnam
        String[] provinces = {"Hà Nội", "Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Cần Thơ", "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái"};

        do {
            // Navigate to the target URL in page 1, 2, ...
            page_driver.get(page_url);

            // Get list_figure_url from tag "click"
            List<WebElement> list_elements = page_driver.findElements(By.className("click"));
            List<String> list_figure_url = new ArrayList<String>();
            for (WebElement element : list_elements) {
                list_figure_url.add(element.getAttribute("href"));
            }

            // Get url for each figure
            for (String url : list_figure_url) {
                // Navigate to the target URL in list_figure_url
                driver.get(url);

                // Locate the element containing the desired data, e1 for the name and dates, e2 for the description
                WebElement e1 = driver.findElement(By.className("header-edge"));
                List<WebElement> e2 = driver.findElements(By.xpath("/html/body/div[1]/div[3]/div[2]/div[1]/div[3]/div/div[2]"));

                // Get text from the element e1
                String data = e1.getText();

                // Save data in jsonObject
                JSONObject jsonObject = new JSONObject();

                // Split data. For example "Trần Hưng Đạo (1228 - 1300)" -> "Trần Hưng Đạo" and "(1228 - 1300)"
                String[] parts = data.split("\\(", 2);
                String name = parts[0].trim();
//                String dates = "? - ?";
//                // If the dates is empty in data. Set dates = ""
//                try {
//                    dates = parts[1].trim().replace(")", "");
//                } catch (Exception ignored) {}

                // put name, location and description into json object
                jsonObject.put("name", name);
//                jsonObject.put("dates", dates);

                // Get text from the element e2
                StringBuilder description = new StringBuilder();
                for (WebElement e : e2) {
                    description.append(e.getText());
                }
                for (String province : provinces) {
                    if (description.toString().contains(province)) {
                        jsonObject.put("location", province);
                        break;
                    }
                }
                jsonObject.put("description", description.toString());


                // put jsonObject into jsonArray
                jsonArray.put(jsonObject);
                System.out.println("The crawl of the website: " + url + " was successful.");
                System.out.println(jsonObject.toString());
            }
            previous_page_url = page_url;
            // go to the next page
            try {
                WebElement nextElement = page_driver.findElement(By.xpath("//li[@class='next']/a"));
                page_url = nextElement.getAttribute("href");
            } catch (NoSuchElementException ignored) {

            }
        } while (!page_url.equals(previous_page_url));

        // Save jsonArray to file
        try (FileWriter file = new FileWriter("data/HistoricalPlace.json", true)) {
            file.write(jsonArray.toString());
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the browser
        driver.quit();

        System.out.println("Time: " + ((System.currentTimeMillis() - start)) / 1000);
    }
}