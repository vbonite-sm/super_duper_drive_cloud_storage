package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    private final JavascriptExecutor js;
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement buttonSubmit;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public void inputFirstName(String firstName) {
        js.executeScript("arguments[0].value='"+ firstName +"';", inputFirstName);
    }

    public void inputLastName(String lastName) {
        js.executeScript("arguments[0].value='"+ lastName +"';", inputLastName);
    }

    public void inputUsername(String userName) {
        js.executeScript("arguments[0].value='"+ userName +"';", inputUsername);
    }

    public void inputPassword(String password) {
        js.executeScript("arguments[0].value='"+ password +"';", inputPassword);
    }

    public void clickSubmitButton() {
        js.executeScript("arguments[0].click();", buttonSubmit);
    }
}
