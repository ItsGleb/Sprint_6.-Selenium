package ru.praktkum_services.qa_scooter.page_object_model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AboutRentPage {
    WebDriver driver;
    // Заголовок формы
    private By rentFormHeader = By.cssSelector(".Order_Header__BZXOb");
    // Поле с датой доставки
    private By whenToBringTheScooter = By.xpath("//input[@placeHolder='* Когда привезти самокат']");
    // Календарь
    private By datePickerWeeks = By.xpath("//div[@class='react-datepicker__month']/div");
    // Поле срока аренды
    private By rentalPeriod = By.xpath("//div[@class='Dropdown-placeholder']");
    // Дроп даун меню срока аренды
    private By rentalPeriodMenu = By.xpath("//div[@class='Dropdown-menu']/div");
    // Чек-боксы цвета
    private By orderCheckBoxes = By.xpath("//div[@class='Order_Checkboxes__3lWSI']/label");
    // Поле комментария
    private By inputComment = By.xpath("//input[@placeholder='Комментарий для курьера']");
    // Кнопка "заказать"
    private By orderButton = By.xpath("//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");
    // Модальное окно "Хотите оформить заказ"
    private By orderModal = By.className("Order_Modal__YZ-d3");
    // Кнопка "да"
    private By orderModalButtonYes = By.xpath("//div[@class='Order_Modal__YZ-d3']/div/button[text()='Да']");
    // Заголовок модального окна
    private By orderModalHeader = By.xpath("//div[@class='Order_Modal__YZ-d3']/div[text()='Заказ оформлен']");

    public AboutRentPage(WebDriver driver) {
        this.driver = driver;
    }

    // Выбор даты на календаре
    public void setDateOnCalendar(int weekIndex, int dayOfWeekIndex) {
        driver.findElement(whenToBringTheScooter).click();
        WebElement element = driver.findElement(By.cssSelector(".react-datepicker-popper"));
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
        if (weekIndex < 0 || weekIndex > driver.findElements(datePickerWeeks).size()) {
            throw new IndexOutOfBoundsException("Неправильный индекс недели. Кол-во недель= " + driver.findElements(datePickerWeeks).size());
        } else if (dayOfWeekIndex < 0 || dayOfWeekIndex > 7) {
            throw new IndexOutOfBoundsException("Неправильный индекс дня недели. Кол-во дней= " + 7);
        } else {
            String xpath = String.format("./div[%d]", dayOfWeekIndex);
            WebElement week = driver.findElements(datePickerWeeks).get(weekIndex);
            WebElement day = week.findElement(By.xpath(xpath));
            day.click();
            //driver.findElements(datePickerWeeks).get(weekIndex).findElements(By.xpath("/div")).get(dayOfWeekIndex).click();
        }
    }

    // Клик на строку срока аренды
    public void clickOnRentalPeriod() {
        driver.findElement(rentalPeriod).click();
    }

    // Выбор срока аренды
    public void setRentalPeriod(int index) {
        WebElement element = driver.findElement(rentalPeriodMenu);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
        try {
            driver.findElements(rentalPeriodMenu).get(index).click();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Неправильное значение срока аренды " + e.getMessage());
        }
    }

    // Выбор цвета самоката
    public void checkScooterColor(int index) {
        try {
            driver.findElements(orderCheckBoxes).get(index).click();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Неправильное значение количества чек-боксов цвета " + e.getMessage());
        }
    }

    // Ввод комментария
    public void enterComment(String comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Комментарий не должен быть null");
        } else driver.findElement(inputComment).sendKeys(comment);
    }

    // Нажать на кнопку "Заказать"
    public void clickOnOrderButton() {
        driver.findElement(orderButton).click();
    }

    // Отображение формы
    public String appearRentForm() {
        return driver.findElement(rentFormHeader).getText();
    }

    public void fillingOutTheForm(int weekIndex, int dayOfWeekIndex, int numberOfDays, int indexColor, String comment) {
        setDateOnCalendar(weekIndex, dayOfWeekIndex);
        clickOnRentalPeriod();
        setRentalPeriod(numberOfDays);
        checkScooterColor(indexColor);
        enterComment(comment);
    }
    //Появилась модалка или нет
    public boolean isTheModalWindowDisplayed(){
        return driver.findElement(orderModal).isDisplayed();
    }

    // Клик на кнопку "Да"
    public void clickOnOrderModalButtonYes(){
        WebElement element = driver.findElement(orderModalButtonYes);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
        driver.findElement(orderModalButtonYes).click();
    }
    public String checkOrderModalHeader(){
        WebElement element = driver.findElement(orderModalHeader);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
        // Не получалось вытащить заголовок только. Пришлось делать split
        return driver.findElement(orderModalHeader).getText().split("\\n")[0].trim();
    }
}
