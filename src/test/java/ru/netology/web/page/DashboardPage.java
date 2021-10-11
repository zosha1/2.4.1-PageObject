package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement firstCardButton = $("[data-test-id=\"action-deposit\"]");
    private SelenideElement secondCardButton = $$("[data-test-id=\"action-deposit\"]").get(1);

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public TransferPage firstCardUp() {
        firstCardButton.click();
        return new TransferPage();
    }

    public TransferPage secondCardUp() {
        secondCardButton.click();
        return new TransferPage();
    }

    public int getCardBalance(int index) {
        val text = cards.get(index).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}