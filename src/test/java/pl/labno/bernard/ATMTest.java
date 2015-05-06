package pl.labno.bernard;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class ATMTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private ATM atm = Mockito.mock(ATM.class);
    private Bank bank = Mockito.mock(Bank.class);

    @Test
    public void withdrawCash_nullParametrGiven_throwException() {
        exception.expect(IllegalStateException.class);

        //ATM atm = new ATM(b);
        //atm.enterCard(0);
        //atm.enterPin(0);
    }


    @Test
    public void withdrawCash_PinCorrectIsNull_IllegalStateException() throws Exception {
        // Given
        atm.enterCard(0);
        atm.enterPin(0);
        // When

        // Then

    }

    @Test
    public void withdrawCash_cardNoIsNull_throwException() {
        exception.expect(IllegalStateException.class);
        // Given
        Bank bank = Mockito.mock(Bank.class);
        ATM a = new ATM(bank);
        // When
        a.withdrawCash(1);
    }

    @Test
    public void withdrawCash_pinIsNull_throwException() {
        exception.expect(IllegalStateException.class);
        // Given
        Bank bank = Mockito.mock(Bank.class);
        ATM a = new ATM(bank);
        // When
        a.withdrawCash(1);
    }

    @Test
    public void withdrawCash_pinIsIncorrect_throwException() {
        exception.expect(IllegalStateException.class);
        // Given
        Bank bank = Mockito.mock(Bank.class);
        when(bank.isPinCorrect(0, 0)).thenReturn(false);
        ATM a = new ATM(bank);
        // When
        a.withdrawCash(1);
    }

    @Test
    public void withdrawCash_pinIsCorrectAndAccountValid_withdrawCashAmountAndDisconnect() {

        // Given
        Bank bank = Mockito.mock(Bank.class);
        when(bank.isPinCorrect(1111, 1111)).thenReturn(true);
        when(bank.withdraw(1)).thenReturn(1);
        ATM a = new ATM(bank);
        // When
        a.enterCard(1111);
        a.enterPin(1111);
        a.withdrawCash(1);
        // Then
        verify(bank, times(1)).withdraw(1);
        verify(bank, times(1)).disconnect();
    }

    @Test
    public void withdrawCash_pinIsCorrectAndAccountBlocked_throwExceptionAndDisconnect() {
        exception.expect(IllegalStateException.class);

        // Given
        Bank bank = mock(Bank.class);
        when(bank.isPinCorrect(1111, 1111)).thenReturn(true);
        when(bank.withdraw(1)).thenThrow(AccountBlockedException.class);
        ATM a = new ATM(bank);
        // When
        a.enterCard(1111);
        a.enterPin(1111);
        a.withdrawCash(1);
        // Then
        verify(bank, times(1)).withdraw(1);
        verify(bank, times(1)).disconnect();
    }

}
