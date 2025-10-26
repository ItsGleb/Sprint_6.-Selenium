package ru.praktkum_services.qa_scooter.page_object_model;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private WebDriver driver;
    private By importantQuestions = By.xpath("//div[@class='accordion__button']");
    private By importantAnswer = By.xpath("//div[@class='accordion__panel']");


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void scrollToElementByClassName(String className) {
        WebElement element = driver.findElement(By.className(className));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void clickImportantQuestionElement(int index) {

        try {
            driver.findElements(importantQuestions).get(index).isDisplayed();
            driver.findElements(importantQuestions).get(index).click();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Неверный индекс элемента" + e.getMessage());
        }
    }

    public String getImportantAnswerElementTexting(int index) {
        String answer = null;
        WebElement element = driver.findElements(importantAnswer).get(index);
        try {
            clickImportantQuestionElement(index);
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
            answer = driver.findElements(importantAnswer).get(index).getText();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Неверный индекс элемента" + e.getMessage());
        }
        return answer;
    }

}
