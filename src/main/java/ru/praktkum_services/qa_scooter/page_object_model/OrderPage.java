package ru.praktkum_services.qa_scooter.page_object_model;

import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    // Сама форма заказа
    private By scooterOrderForm = By.className("Order_Content__bmtHS");
    // Поле ввода имени
    private By inputName = By.xpath("//input[@placeholder='* Имя']");
    // Поле ввода Фамилии
    private By inputSurname = By.xpath("//input[@placeholder='* Фамилия']");
    // Поле ввода адреса
    private By inputAdress = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле ввода номера телефона
    private By inputPhoneNumber = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Аккордеон станций метро
    private By selectMetroStation = By.className("select-search__input");
    // Кнопка "далее"
    private By continueButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    private By metroStationList = By.xpath("//ul/li");


    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isScooterOrderFormAvailable() {
        return driver.findElement(scooterOrderForm).isDisplayed();
    }

    public void clickOnContinueButton() {
        driver.findElement(continueButton).click();
    }

    public void enterUserName(String name) {
        driver.findElement(inputName).sendKeys(name);

    }

    public void enterSurname(String surname) {
        driver.findElement(inputSurname).sendKeys(surname);
    }

    public void enterAdress(String adress) {
        driver.findElement(inputAdress).sendKeys(adress);
    }

    public void enterPhoneNumber(String phoneNumber) {
        driver.findElement(inputPhoneNumber).sendKeys(phoneNumber);
    }

    public void selectMetroStation(int index) {
        WebElement element = driver.findElement(selectMetroStation);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
        driver.findElement(selectMetroStation).click();
        try {
            driver.findElements(metroStationList).get(index).click();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Неправильный индекс станции. Кол-во станции= " + driver.findElements(metroStationList).size() + " шт " + e.getMessage());
        }
    }

    // Шаг заполнения формы заказа
    public void theStepOfFillingOutTheForm(String name, String surname, String adress, String phoneNumber, int index) {
        enterUserName(name);
        enterSurname(surname);
        enterAdress(adress);
        enterPhoneNumber(phoneNumber);
        selectMetroStation(index);
    }


}
