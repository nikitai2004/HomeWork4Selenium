import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import java.io.File;
import javax.imageio.ImageIO;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class HomeWork4SeleniumTest {
    private final Logger log = LogManager.getLogger(HomeWork4SeleniumTest.class);
    private WebDriver driver;
    private final String login = "ceyogo9446@bitvoo.com";
    private final String pas = "Ceyogo9446!";

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @AfterEach
    public void closeAll() {
        if (driver != null) {
            driver.close();
            driver.quit();
            log.info("Драйвер закрыт");
        }
    }

    @Test
    public void testOpenHeadlessChromeOtus() {
        ChromeOptions chromeOptions01 = new ChromeOptions();
        chromeOptions01.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions01);
        log.info("Драйвер 01 ChromeDriver headless starting");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://duckduckgo.com/");
        WebElement element1 = driver.findElement(By.cssSelector("input.searchbox_input__bEGm3"));
        element1.sendKeys("ОТУС");
        element1.sendKeys(Keys.ENTER);
        // Сохраним HTML страницу в лог
        // log.info(driver.getPageSource());
        // И сделаем скриншот
        // Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        // ImageIO.write(screenshot.getImage(), "jpg", new File("c:\\111\\ElementScreenshot.jpg"));
        WebElement element2 = driver.findElement(By.cssSelector("h2 a[href=\"https://otus.ru/\"]"));
        // Сохраним в лог полученные данные для анализа
        // log.info("Text 02 = ");
        // log.info(element2.getText());
        Assertions.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным ...", element2.getText());
    }

    @Test
    public void openChromeKioskModeAndCheckImg() {
        ChromeOptions chromeOptions02 = new ChromeOptions();
        chromeOptions02.addArguments("—kiosk");
        driver = new ChromeDriver(chromeOptions02);
        log.info("Драйвер 02 kiosk ChromeDriver starting");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://otus.home.kartushin.su/lesson/index.htm");

        // Через ожидания проверяем загрузку страницы
        WebElement element02 = driver.findElement(By.xpath("(//img[@alt='portfolio-img'])[1]"));
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(element02));

        // Проверяем, что НЕ открылось модальное окно
        Assertions.assertNotEquals(driver.getPageSource(), "pp_overlay");
        // Кликаем с помощью JS
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element02);

        //Проверяем, что открылось модальное окно
        WebElement element03 = driver.findElement(By.cssSelector(".pp_overlay"));
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOf(element03));
    }

    @Test
    public void openChromeFullScreenAuthOtusAndLogCookies() {
        ChromeOptions chromeOptions03 = new ChromeOptions();
        chromeOptions03.addArguments("--start-fullscreen");
        driver = new ChromeDriver(chromeOptions03);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        log.info("Драйвер 03 fullscreen ChromeDriver starting");
        driver.get("https://otus.ru");
        loginInOtus();
        Set<Cookie> cookies = driver.manage().getCookies();
        log.info("Cookies:");
        log.info(cookies);
    }

    private void loginInOtus() {
        driver.findElement(By.xpath("//button[@class='sc-mrx253-0 enxKCy sc-945rct-0 iOoJwQ']")).click();
        clearAndEnter(By.xpath("//input[@name='email']"), login);
        clearAndEnter(By.xpath("//input[@type='password']"), pas);
        driver.findElement(By.cssSelector("div.sc-10p60tv-2.bQGCmu > div > button > div")).click();
    }

    private void clearAndEnter(By by, String text) {
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }
}