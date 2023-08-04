import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;


public class HomeWork4Selenium {
    private Logger log = LogManager.getLogger(HomeWork4Selenium.class);
    private WebDriver driver;  //драйвер должен быть НЕСТАТИЧНЫЙ - если будет несколько потоков, то сессии вебдрайвера сломаются

    @BeforeEach
    public void downloadDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-fullscreen");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(5000,
                TimeUnit.MILLISECONDS);
        log.info("ChromeDriver starting");
    }

    @AfterEach
    public void closeAll() {
        if (driver != null)
            driver.close();
        log.info("Драйвер закрыт");
    }


    @Test
    public void openAndCheckInFindSiteOtus() throws InterruptedException {

        driver.get("https://duckduckgo.com/");
        WebElement element1 = driver.findElement(By.cssSelector("#search_form_input_homepage"));
        element1.sendKeys("ОТУС");
        WebElement element2 = driver.findElement(By.cssSelector("#search_button_homepage"));
        element2.click();
        WebElement element3 = driver.findElement(By.cssSelector(".react-results--main>li:first-child>article>div:nth-child(2) span:first-child"));
        log.info("Text = ");
        log.info(element3.getText());
        Assertions.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным ...", element3.getText());


        Thread.sleep(5000);

    }

//    @Test
//    public void clickNewsBlockItem() {
//         String findElement = "[input='#search_form_input_homepage']";
//     //   driver.findElement(By.cssSelector(findElement)) ;
//        // String daynewsItemElement = "[class='daynews__item']>.js-topnews__item";
//        //  String itemNewsTitle = $$(By.cssSelector(String.format("%s .photo__title", daynewsItemElement))).get(0).getText();
//
//        //   $(By.xpath(String.format("//span[contains(@class,'js-topnews.item')]")));
//    }


    private WebElement $(By locator) {
        return driver.findElement(locator);

    }

    //  private List<WebElement> $$(By locator) {
    //  return driver.findElement(locator);

    //  }

//1,5 часа урока пройдено

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

