package main_page_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.praktkum_services.qa_scooter.page_object_model.MainPage;
import ru.praktkum_services.qa_scooter.page_object_model.OrderPage;

import static org.junit.jupiter.api.Assertions.*;
import static ru.praktkum_services.qa_scooter.constants.Constants.MAIN_PAGE_URL;

public class MainPageTests {
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(MAIN_PAGE_URL); // Открыли браузер
        driver.findElement(By.id("rcc-confirm-button")).click(); // Закрываем согласие с куками
    }
    @ParameterizedTest
    @CsvSource(value = {
            "0| Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "1| Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "2| Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, " +
                    "когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "3| Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "4| Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "5| Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "6| Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "7| Да, обязательно. Всем самокатов! И Москве, и Московской области.",

    }, delimiter = '|')
    public void checkImportantAnswer(int index, String texting) {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.scrollToElementByCssSelector(".Home_FourPart__1uthg");
        assertEquals(texting,objMainPage.getImportantAnswerElementTexting(index),"Текстовка неправильная");

    }
    // Проверка точки входа в сценарий заказа самоката по кнопке в заголовке
    @Test
    public void checkingTheOrderButtonInTheHeader(){
        MainPage objMainPage = new MainPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);
        objMainPage.clickTheOrderButtonInTheHeader();
        assertTrue(objOrderPage.isScooterOrderFormAvailable(),"Форма заказа не отобразилась");
    }

    @Test
    public void checkingOrderButtonAtTheBottomOfThePage(){
        MainPage objMainPage = new MainPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);
        objMainPage.theOrderButtonAtTheBottomOfThePage();
        assertTrue(objOrderPage.isScooterOrderFormAvailable(),"Форма заказа не отобразилась");
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрытие браузера
        }
    }
}
