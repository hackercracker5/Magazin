import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;
import java.util.*;


class StoreTest {

    private Stock    cola;
    private User     user;
    private Employee cashier;
    private Store    store;

    @BeforeEach
    void setup() {
        new File("global_receipts_summary.txt").delete();

        cola    = new Stock("S1", "Kola", 50, true,
                LocalDate.now().plusDays(60), 4);
        user    = new User(500);
        cashier = new Employee("E0", "Cash", 1000);

        store = new Store(
                15,
                35,
                50,
                new ArrayList<>(),
                List.of(cashier),
                new ArrayList<>(List.of(cola)),
                user);
    }

    // ───────────────────────────────────────────────────────────────
    @Test
    void buyReducesQuantityAndMoney() throws Exception {
        int    q0 = cola.getQuantitiy();
        double m0 = user.getMoney();

        store.Buy(cola);

        assertEquals(q0 - 1, cola.getQuantitiy());
        assertEquals(m0 - cola.getLocalPrice(), user.getMoney(), 0.0001);
    }

    @Test
    void outOfStockThrows() {
        cola.setQuantitiy(0);
        assertThrows(InsufficientStockException.class, () -> store.Buy(cola));
    }

    @Test
    void insufficientMoneyThrows() {
        user.setMoney(0);
        assertThrows(InsufficientMoneyException.class, () -> store.Buy(cola));
    }

    // ───────────────────────────────────────────────────────────────
    @Test
    void discountCutsPriceHalfWhen12DaysOrLess() {
        Stock milk = new Stock("S2", "Milk", 20, true,
                LocalDate.now().plusDays(5), 2);

        new Store(15, 35, 50,
                new ArrayList<>(),
                List.of(cashier),
                List.of(milk),
                new User(100));

        assertEquals(11.5, milk.getLocalPrice(), 0.0001);   // 50 % of supplier price
    }

    // ───────────────────────────────────────────────────────────────
    @Test
    void receiptNumbersIncreaseInsideSameJvm() {
        Receipt r1 = new Receipt(cashier);
        r1.addToGlobalReceipts();

        Receipt r2 = new Receipt(cashier);
        r2.addToGlobalReceipts();

        assertEquals(r1.getReceiptNumber() + 1, r2.getReceiptNumber());
    }

    @Test
    void employeeCumulativeIncomeAccumulatesAcrossReceipts() {

        Receipt r1 = new Receipt(cashier);
        r1.addItem(cola);
        r1.addToGlobalReceipts();

        Receipt r2 = new Receipt(cashier);
        r2.addItem(cola);
        r2.addToGlobalReceipts();

        assertEquals(115.0, cashier.getCumulativeIncome(), 0.0001);
    }
}
