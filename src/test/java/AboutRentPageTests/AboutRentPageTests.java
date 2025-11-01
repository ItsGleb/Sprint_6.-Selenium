package AboutRentPageTests;


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
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktkum_services.qa_scooter.page_object_model.AboutRentPage;
import ru.praktkum_services.qa_scooter.page_object_model.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.praktkum_services.qa_scooter.constants.Constants.*;

public class AboutRentPageTests {
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(ORDER_PAGE_URL); // Открыли браузер
        driver.findElement(By.id("rcc-confirm-button")).click(); // Закрываем согласие с куками
        // Нужно чтобы перейти к форме "Про аренду"
        OrderPage objOrderPage = new OrderPage(driver);
        objOrderPage.theStepOfFillingOutTheForm("Иван", "Иванов", " Москва Стартовая 1,кв 15", "89102202095", 5);
        objOrderPage.clickOnContinueButton();
    }

    @ParameterizedTest
    @MethodSource("dataForTheForm")
    public void formFillingTest(int weekIndex, int dayOfWeekIndex, int numberOfDays, int indexColor, String comment) {
        AboutRentPage objAboutRentPage = new AboutRentPage(driver);
        objAboutRentPage.fillingOutTheForm(weekIndex, dayOfWeekIndex, numberOfDays, indexColor, comment);
        objAboutRentPage.clickOnOrderButton();
        assertTrue(objAboutRentPage.isTheModalWindowDisplayed(), "Модальное окно не отобразилось");
    }

    private static Stream<Arguments> dataForTheForm() {
        return Stream.of(
                Arguments.of(2, 3, 5, 0, "Хочу самокат без тормозов"),
                Arguments.of(3, 7, 1, 1, "Хочу самокат с тормозами"));
    }

    @Test
    public void сheckingTheOrderConfirmation() {
        AboutRentPage objAboutRentPage = new AboutRentPage(driver);
        objAboutRentPage.fillingOutTheForm(2, 3, 5, 0, "Хочу самокат без тормозов");
        objAboutRentPage.clickOnOrderButton();
        objAboutRentPage.clickOnOrderModalButtonYes();
        assertEquals("Заказ оформлен",objAboutRentPage.checkOrderModalHeader(), "Не отработал переход к следующему шагу");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрытие браузера
        }
    }
}
