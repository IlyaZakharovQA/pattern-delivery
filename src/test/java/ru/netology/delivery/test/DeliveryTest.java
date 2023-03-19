package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

        @Test
        @DisplayName("Should successful plan and replan meeting")
        void shouldSuccessfulPlanAndReplanMeeting() {
            var validUser = DataGenerator.Registration.generateUser("ru");
            var daysToAddForFirstMeeting = 4;
            var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
            var daysToAddForSecondMeeting = 7;
            var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
            Configuration.holdBrowserOpen = true;
            $("[data-test-id='city'] input").sendKeys(validUser.getCity());
            $("[data-test-id='date'] input").doubleClick().sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
            $("[data-test-id='name'] input").sendKeys(validUser.getName());
            $("[data-test-id='phone'] input").sendKeys(validUser.getPhone());
            $("[data-test-id='agreement']").click();
            $x("//span[text()='Запланировать']").click();
            $("[data-test-id='success-notification']")
                    .shouldHave(Condition.text("Встреча успешно запланирована на " + DataGenerator.generateDate(daysToAddForFirstMeeting)), Duration.ofSeconds(8))
                    .shouldBe(Condition.visible);
            $("[data-test-id='date'] input").doubleClick().sendKeys(DataGenerator.generateDate(daysToAddForSecondMeeting));
            $x("//span[text()='Запланировать']").click();
            $x("//span[text()='Перепланировать']").click();
            $("[data-test-id='success-notification']")
                    .shouldHave(Condition.text("Встреча успешно запланирована на " + DataGenerator.generateDate(daysToAddForSecondMeeting)), Duration.ofSeconds(8))
                    .shouldBe(Condition.visible);

            // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
            // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
            // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
            // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
            // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
        }
    }