package ru.praktkum_services.qa_scooter.page_object_model;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class MainPage {
    private WebDriver driver;
    // Аккордеон важных вопросов
    private By importantQuestions = By.xpath("//div[@class='accordion__button']");
    // Аккордеон ответов на важные вопросы
    private By importantAnswer = By.xpath("//div[@class='accordion__panel']");
    // Кнопка "заказать" в заголовке страницы
    private By theOrderButtonInTheHeader = By.xpath("//button[@class='Button_Button__ra12g']");
    // Кнопка "заказать" внизу страницы
    // Переписать локатор для нижней кнопки. Какой-нибудь относительный путь по div
    private By theOrderButtonAtTheBottomOfThePage = By.xpath("//div[@class='Home_FinishButton__1_cWm']/button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void scrollToElementByCssSelector(String cssSelector) {
        WebElement element = driver.findElement(By.cssSelector(cssSelector));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }
    public void scrollToElementByXPath(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    // Клик на вопрос в аккордеоне
    public void clickImportantQuestionElement(int index) {
        WebElement element = driver.findElements(importantQuestions).get(index);
        try {
            // Проверяем загрузился ли вопрос
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
            driver.findElements(importantQuestions).get(index).click();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Неверный индекс элемента" + e.getMessage());
        }
    }

    // Получение текстовки ответа
    public String getImportantAnswerElementTexting(int index) {
        String answer = null;
        WebElement element = driver.findElements(importantAnswer).get(index);
        try {
            clickImportantQuestionElement(index);
            // Ждун который ждет отображения конкретного элемента
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
            // Получаем значение текстовки
            answer = driver.findElements(importantAnswer).get(index).getText();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Неверный индекс элемента" + e.getMessage());
        }
        return answer;
    }

    // Метод для клика на кнопку
    public void clickTheOrderButtonInTheHeader() {

        // Кликаем на нее
        driver.findElement(theOrderButtonInTheHeader).click();
    }
    // Переписать локатор для нижней кнопки. Какой-нибудь относительный путь по div
    public void theOrderButtonAtTheBottomOfThePage() {
        // Проверяем что кнопка загрузилась

        WebElement element = driver.findElement(theOrderButtonAtTheBottomOfThePage);
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));

        scrollToElementByXPath("//div[@class='Home_FinishButton__1_cWm']/button");

        // Кликаем на нее
        driver.findElement(theOrderButtonAtTheBottomOfThePage).click();
    }

}
