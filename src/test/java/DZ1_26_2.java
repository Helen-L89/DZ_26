import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class DZ1_26_2 {

    public static void main(String[] args) {

        // Указание пути к geckodriver
        System.setProperty("webdriver.gecko.driver", "C:\\work\\geckodriver.exe"); //

        runTest("headless");
        runTest("kiosk");
        runTest("fullscreen");

    }

    public static void runTest(String mode) {
        FirefoxOptions options = new FirefoxOptions();
        switch (mode) {
            case "headless":
                options.addArguments("--headless");
                break;
            case "kiosk":
                options.addArguments("--kiosk");
                break;
            case "fullscreen":
                options.addArguments("--start-fullscreen");
                break;
            default:
                System.err.println("Неизвестный режим: " + mode);
                return;
        }


        WebDriver driver = new FirefoxDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, 2); //явное ожидание


        try {
            driver.get("https://otus.home.kartushin.su/training.html");

            if (mode.equals("headless")) {
                // Тест 1: Headless - Ввод текста
                WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input_text")));
                inputField.sendKeys("ОТУС");
                String enteredText = inputField.getAttribute("value");
                assert enteredText.equals("ОТУС") : "Ошибка ввода текста в headless режиме";
                System.out.println("Тест 1 (headless) пройден успешно!");

            } else if (mode.equals("kiosk")) {
                // Тест 2: Kiosk - Модальное окно
                WebElement modalButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("open_modal")));
                modalButton.click();
                WebElement modalContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("modal_content")));
                assert modalContent.isDisplayed() : "Ошибка открытия модального окна в режиме киоска";
                System.out.println("Тест 2 (kiosk) пройден успешно!");

            } else if (mode.equals("fullscreen")) {
                // Тест 3: Fullscreen - Отправка формы
                WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
                WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
                WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));

                nameField.sendKeys("фыв");
                emailField.sendKeys("asdf@sdfg.rt");
                submitButton.click();


                WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("success-message")));
                String expectedMessage = "Форма отправлена с именем: фыв и email: asdf@sdfg.rt";
                assert successMessage.getText().equals(expectedMessage) : "Ошибка отправки формы в полноэкранном режиме";
                System.out.println("Тест 3 (fullscreen) пройден успешно!");
            }

        } catch (AssertionError e) {
            System.err.println("Тест " + mode + " НЕ пройден: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка в тесте " + mode + ": " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}