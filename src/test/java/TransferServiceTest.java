import com.example.model.Account;
import com.example.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.service.TransferService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    public void testTransferMoneySufficientFunds() {

        Account sender = new Account();
        sender.setBalance(BigDecimal.valueOf(100));

        Account receiver = new Account();
        receiver.setBalance(BigDecimal.valueOf(50));

        BigDecimal amount = BigDecimal.valueOf(30);

        when(accountRepository.save(sender)).thenReturn(sender);
        when(accountRepository.save(receiver)).thenReturn(receiver);

        transferService.transferMoney(sender, receiver, amount);

        assertEquals(BigDecimal.valueOf(70), sender.getBalance());
        assertEquals(BigDecimal.valueOf(80), receiver.getBalance());

        verify(accountRepository, times(2)).save(any());
    }

    @Test
    public void testTransferMoneyInsufficientFunds() {

        Account sender = new Account();
        sender.setBalance(BigDecimal.valueOf(20));

        Account receiver = new Account();
        receiver.setBalance(BigDecimal.valueOf(50));

        BigDecimal amount = BigDecimal.valueOf(30);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transferService.transferMoney(sender, receiver, amount);
        });

        assertEquals("Отправитель не имеет достаточного баланса для перевода", exception.getMessage());

        verify(accountRepository, never()).save(any());
    }

    @Test
    public void testTransferMoneyNullReceiver() {

        Account sender = new Account();
        sender.setBalance(BigDecimal.valueOf(100));

        BigDecimal amount = BigDecimal.valueOf(30);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transferService.transferMoney(sender, null, amount);
        });

        assertEquals("Получатель не найден", exception.getMessage());

        verify(accountRepository, never()).save(any());
    }
}