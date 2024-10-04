import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Point;
import org.openqa.selenium.Dimension;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DZ3_26 {

    WebDriver driver;

    @BeforeAll
    public static void webDriverInstall() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void webDriverStart(){
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-fullscreen");
        driver = new FirefoxDriver();
        /*driver2 = new FirefoxDriver();*/ //будет работать как 2 разных пользователя, 2 открытия браузера
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); тоже самое,но для версии 4,24 селениум
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS); //это дозагрузка элементов на странице
        //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        //System.out.println(driver.manage().timeouts().getImplicitWaitTimeout());
        // System.out.println(driver.manage().timeouts().getScriptTimeout());
        //System.out.println(driver.manage().timeouts().getPageLoadTimeout());

        // Переход на указанный ресурс
        driver.get("https://otus.home.kartushin.su/training.html");
    }

    @AfterEach
    public void webDriverStop(){
        if (driver!=null) {
            driver.close();
            //driver.quit();
        }
    }

    @Test
    public void fullscreenTest (){
        try {
            // Нахождение полей ввода для имени и почты
            WebElement nameField = driver.findElement(By.id("name")); // поле для имени имеет id "name"
            WebElement emailField = driver.findElement(By.id("email")); // поле для почты имеет id id="email"


            // Ввод данных в поля формы
            String name = "фыв";
            String email = "asdf@sdfg.rt";
            nameField.sendKeys(name);
            emailField.sendKeys(email);

            // Нахождение и нажатие на кнопку отправки формы

            WebElement submitButton = driver.findElement(By.xpath("//button[text()='Отправить']")); // кнопка отправки имеет  "submit"
            submitButton.click();

            // Ожидание появления сообщения о успешной отправке формы
            WebDriverWait wait = new WebDriverWait(driver, 10);
            // сообщение имеет id "messageBox"
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messageBox")));

            // Проверка текста сообщения
            String expectedMessage = "Форма отправлена с именем: " + name + " и email: " + email;
            String actualMessage = successMessage.getText();



            if (expectedMessage.equals(actualMessage)) {
                System.out.println("Тест успешно пройден: сообщение подтверждает отправку формы.");
            } else {
                System.out.println("Тест провален: текст сообщения не совпадает с ожидаемым.");
            }

        } catch (Exception e) {
            System.out.println("Ошибка во время выполнения теста: " + e.getMessage());
        } finally {
            // Закрытие браузера
            driver.quit();
        }

    }

}

