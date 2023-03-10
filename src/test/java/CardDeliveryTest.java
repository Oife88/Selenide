import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {
    String meetingDay(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;

    }


    @Test
    void shouldTestSomething() {
        $x("//span[@data-test-id='city']//child::input").setValue("Москва");
        $x("//span[@class='menu-item__control']").click();
        $("[data-test-id= date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id= date] input").setValue(meetingDay(5));
        $("[name=name]").setValue("Иванов Иван");
        $("[name=phone]").setValue("+79099606060");
        $("[role=presentation]").click();
        $x("//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        $x("//div[@class='notification__title']").should(text("Успешно!"), Duration.ofSeconds(15));
        $x("//div[@class='notification__content']").should(text("Встреча успешно забронирована на " + meetingDay(5)), Duration.ofSeconds(15));


    }


    @Test
    void shouldTestBoundaryDate() {
        $x(".//span[@data-test-id='city']//child::input").setValue("Симферополь");
        $x("//span[@class='menu-item__control']").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDay(-2));
        $("[name=name]").setValue("Иванов Иван");
        $("[name=phone]").setValue("+79099606060");
        $("[role=presentation]").click();
        $x("//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        $("[data-test-id='date'] .input__sub").should(text("Заказ на выбранную дату невозможен"), Duration.ofSeconds(15));


    }

    @Test
    void shouldTestCity() {
        $x("//span[@data-test-id='city']//child::input").setValue("Электросталь");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDay(5));
        $("[name=name]").setValue("Иванов Иван");
        $("[name=phone]").setValue("+79099606060");
        $("[role=presentation]").click();
        $x("//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        $("[data-test-id=city] .input__sub").should(text("Доставка в выбранный город недоступна"), Duration.ofSeconds(15));


    }

    @Test
    void shouldTestName() {
        $x("//span[@data-test-id='city']//child::input").setValue("Москва");
        $x("//span[@class='menu-item__control']").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDay(5));
        $("[name=name]").setValue("Иванов Ivan");
        $("[name=phone]").setValue("+79099606060");
        $("[role=presentation]").click();
        $x("//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        $("[data-test-id=name] .input__sub").should(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), Duration.ofSeconds(15));


    }

    @Test
    void shouldTestPhone() {
        $x("//span[@data-test-id='city']//child::input").setValue("Москва");
        $x("//span[@class='menu-item__control']").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDay(5));
        $("[name=name]").setValue("Иванов Иван");
        $("[name=phone]").setValue("+790996060600");
        $("[role=presentation]").click();
        $x("//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        $("[data-test-id=phone] .input__sub").should(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(15));


    }
}

