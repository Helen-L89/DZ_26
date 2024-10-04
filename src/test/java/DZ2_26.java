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

public class DZ2_26 {

    WebDriver driver;

    @BeforeAll
    public static void webDriverInstall() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void webDriverStart(){
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--kiosk");
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
    public void kioskTest (){
        try {
            // Нахождение и нажатие на кнопку "Открыть модальное окно"
            WebElement openModalButton = driver.findElement(By.id("openModalBtn"));
            //  кнопка имеет id "openModalBtn"
            openModalButton.click();

            // Проверка, что модальное окно открылось
            WebElement modalWindow = driver.findElement(By.id("modal_window")); // Предположим, что модальное окно имеет id "modal_window"
            if (modalWindow.isDisplayed()) {
                System.out.println("Тест успешно пройден: модальное окно открылось.");
            } else {
                System.out.println("Тест провален: модальное окно не открылось.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка во время выполнения теста: " + e.getMessage());
        } finally {
            // Закрытие браузера
            driver.quit();
        }

    }

}


