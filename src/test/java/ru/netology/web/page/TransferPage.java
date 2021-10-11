package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromCardField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=\"action-transfer\"]");
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public void changeCardBalance(String amount, String cardsNumber) {
        amountField.setValue(amount);
        fromCardField.setValue(cardsNumber);
        transferButton.click();
    }

    public void findErrorMessage() {
        $(withText("Сумма перевода превышает остаток на карте списания"))
                .shouldBe(visible, Duration.ofSeconds(4));
    }
}
