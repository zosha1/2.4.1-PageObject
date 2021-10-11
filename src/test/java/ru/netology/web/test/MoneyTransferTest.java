package ru.netology.web.test;

import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        DashboardPage dashboardPage = new DashboardPage();
        int firstCardStartBalance = dashboardPage.getCardBalance(0);
        int secondCardStartBalance = dashboardPage.getCardBalance(1);
        TransferPage transferPage = dashboardPage.firstCardUp();
        int transferAmount = 5700;
        transferPage.changeCardBalance(Integer.toString(transferAmount), DataHelper.getCardsInfo().getSecond());
        assertEquals(firstCardStartBalance + transferAmount, dashboardPage.getCardBalance(0));
        assertEquals(secondCardStartBalance - transferAmount, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards2() {
        DashboardPage dashboardPage = new DashboardPage();
        int firstCardStartBalance = dashboardPage.getCardBalance(0);
        int secondCardStartBalance = dashboardPage.getCardBalance(1);
        TransferPage transferPage = dashboardPage.secondCardUp();
        int transferAmount = 2700;
        transferPage.changeCardBalance(Integer.toString(transferAmount), DataHelper.getCardsInfo().getFirst());
        assertEquals(firstCardStartBalance - transferAmount, dashboardPage.getCardBalance(0));
        assertEquals(secondCardStartBalance + transferAmount, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldBlockTransferAmountGreaterThenBalance() {
        DashboardPage dashboardPage = new DashboardPage();
        int firstCardStartBalance = dashboardPage.getCardBalance(0);
        int secondCardStartBalance = dashboardPage.getCardBalance(1);
        TransferPage transferPage = dashboardPage.secondCardUp();
        int transferAmount = Math.abs(firstCardStartBalance) + 1;
        transferPage.changeCardBalance(Integer.toString(transferAmount), DataHelper.getCardsInfo().getFirst());
        transferPage.findErrorMessage();
        assertEquals(firstCardStartBalance, dashboardPage.getCardBalance(0));
        assertEquals(secondCardStartBalance, dashboardPage.getCardBalance(1));
    }
}
