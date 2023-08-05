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
    private Logger log = LogManager.getLogger(HomeWork4SeleniumTest.class);
    WebDriver driver;
    private String login = "ceyogo9446@bitvoo.com";
    private String pas = "Ceyogo9446!";

    @BeforeEach
    public void setUp() {
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
    public void test01() {
        ChromeOptions chromeOptions01 = new ChromeOptions();
        chromeOptions01.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions01);
        log.info("Драйвер 01 ChromeDriver headless starting");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://duckduckgo.com/");
        WebElement element1 = driver.findElement(By.cssSelector("input.searchbox_input__bEGm3"));
        element1.sendKeys("ОТУС");
        element1.sendKeys(Keys.ENTER);
        // Сохраним HTML страницу в лог, зачем-то мы его сделали же
        // log.info(driver.getPageSource());
        // И сделаем скриншот, чтобы понять почему селекторы не работают
        // Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        // ImageIO.write(screenshot.getImage(), "jpg", new File("c:\\111\\ElementScreenshot.jpg"));
        WebElement element2 = driver.findElement(By.cssSelector("h2 a[href=\"https://otus.ru/\"]"));
        // Сохраним в лог полученные данные для анализа
        // log.info("Text 02 = ");
        // log.info(element2.getText());
        Assertions.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным ...", element2.getText());
    }

    @Test
    public void test02() {
        ChromeOptions chromeOptions02 = new ChromeOptions();
        chromeOptions02.addArguments("—kiosk");
        driver = new ChromeDriver(chromeOptions02);
        log.info("Драйвер 02 kiosk ChromeDriver starting");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
        // Кликаем с помощью JS
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element02 = driver.findElement(By.cssSelector("ul>li:first-child>span>a>IMG[class=\"img-fluid w3layouts agileits\"]"));
        js.executeScript("arguments[0].click();", element02);
        // Находим само изображение
        WebElement element03 = driver.findElement(By.cssSelector("img[id=\"fullResImage\"]"));
        // И ждем пока оно загрузится
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(element03));
        // Для проверки загрузки - получаем, логируем и проверяем размеры картинки
        // Если картинки нет - то размер изображения 0 или размер замещающей иконки будет менее 200 точек (предполагаем)
        log.info("Размеры картинки: ");
        log.info(driver.findElement(By.cssSelector("img[id=\"fullResImage\"]")).getSize().getHeight());
        log.info(driver.findElement(By.cssSelector("img[id=\"fullResImage\"]")).getSize().getWidth());
        // Для проверки работы Assertions можно отключить явное ожидание в 81,82 стр
        Assertions.assertTrue((driver.findElement(By.cssSelector("img[id=\"fullResImage\"]")).getSize().getHeight() > 200) & (driver.findElement(By.cssSelector("img[id=\"fullResImage\"]")).getSize().getWidth() > 200));
    }

    @Test
    public void test03() {
        ChromeOptions chromeOptions03 = new ChromeOptions();
        chromeOptions03.addArguments("--start-fullscreen");
        driver = new ChromeDriver(chromeOptions03);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        log.info("Драйвер 03 fullscreen ChromeDriver starting");
        driver.get("https://otus.ru");
        loginInOtus();
        Set<Cookie> cookies = driver.manage().getCookies();
        log.info("Coocies:");
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


//        Описание/Пошаговая инструкция выполнения домашнего задания:
//
//        1)
//
//   -    Открыть Chrome в headless режиме
//   +     Перейти на https://duckduckgo.com/
//   +     В поисковую строку ввести ОТУС
//   +     Проверить что в поисковой выдаче
//   +     первый результат
//   +     Онлайн‑курсы для профессионалов, дистанционное обучение
//
//        2)
//
//        Открыть Chrome в режиме киоска
//        Перейти на https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818
//        Нажать на любую картинку
//        Проверить что картинка открылась в модальном окне
//
//        3)
//
//        Открыть Chrome в режиме полного экрана
//        Перейти на https://otus.ru
//        Авторизоваться под каким-нибудь тестовым пользователем(можно создать нового)
//        Вывести в лог все cookie

