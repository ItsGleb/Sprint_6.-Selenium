package order_page_tests;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.praktkum_services.qa_scooter.page_object_model.AboutRentPage;
import ru.praktkum_services.qa_scooter.page_object_model.OrderPage;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.praktkum_services.qa_scooter.constants.Constants.*;

public class OrderPageTests {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(ORDER_PAGE_URL); // Открыли браузер
        driver.findElement(By.id("rcc-confirm-button")).click(); // Закрываем согласие с куками
    }

    @ParameterizedTest
    @MethodSource("dataForTheForm")
    public void fillingOutTheForm(String name, String surname, String adress, String phoneNumber, int index) {
        OrderPage objOrderPage = new OrderPage(driver);
        objOrderPage.theStepOfFillingOutTheForm(name, surname, adress, phoneNumber, index);
        objOrderPage.clickOnContinueButton();
        AboutRentPage objAboutRentPage = new AboutRentPage(driver);
        assertEquals("Про аренду", objAboutRentPage.appearRentForm(), "Переход к следующей форме не произошел");
    }

    private static Stream<Arguments> dataForTheForm() {
        return Stream.of(
                Arguments.of("Иван", "Иванов", "Москва Стартовая 1,кв 15", "89102202095", 5),
                Arguments.of("Ivan", "Ivanov", "Moscow Startovaya 1,kv 1", "89102202095", 3));
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрытие браузера
        }
    }
}
