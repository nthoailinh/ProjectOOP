package DataCrawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class CountSubPage {
    public static void main(String[] args) {
        // Open a Chrome browser window
        WebDriver driver = new ChromeDriver();

    // Navigate to the webpage
        driver.get("https://thuvienlichsu.com/nhan-vat");

    // Find all of the links on the page
//        List<WebElement> links = driver.findElements(By.tagName("a"));
        List <WebElement> links = driver.findElements(By.xpath("//div[contains(@class,'mb-3')]//div[contains(@class,'card-body')]//p[contains(@class,'card-text')]"));

    // Initialize a counter variable
        int subPageCount = 0;

    // Iterate over the list of links
        for (WebElement link : links) {
            // Check if the link is a subpage by examining its href attribute
            if (link.getAttribute("href") != null) {
                // Increment the counter if it is a subpage
                System.out.println(link.getAttribute("href"));
            }
        }

        // Print the total number of sub pages
        System.out.println("Number of sub pages: " + subPageCount);

        // Close the browser window
        driver.quit();
    }
}
