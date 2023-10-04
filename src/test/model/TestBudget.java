package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestBudget {
    Budget testBudget;
    Transaction t1;
    Transaction t2;
    Transaction t3;
    Transaction t4;
    Transaction t5;
    TransactionList testTransactionList;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");

    @BeforeEach
    public void setup() {
        testBudget = new Budget(0);
        testTransactionList = new TransactionList(new ArrayList<>());

        t1 = new Transaction("Dinner at Cactus Club", 20, "Eating out", "13/08/2023");
        t2 = new Transaction("Buying Electric Guitar", 190, "Shopping", "14/08/2023");
        t3 = new Transaction("Paying MSP", 75, "Bills", "12/08/2023");
        t4 = new Transaction("Paying phone bill", 45, "Bills", "01/08/2023");
        t5 = new Transaction("Paying phone bill", 45, "Bills", "01/07/2023");
    }

    // Tests for isOverBudget()
    @Test
    public void testIsOverBudget1() {
        testTransactionList.addTransaction(t1);
        testTransactionList.addTransaction(t2);
        testTransactionList.addTransaction(t3);
        testTransactionList.addTransaction(t4);

        testBudget.setBudget(1000);
        assertEquals(1000, testBudget.getMonthlyBudget());
        assertEquals(dtf.format(LocalDateTime.now()), testBudget.getMonthYear());
        assertFalse(testBudget.isOverBudget(testTransactionList));
    }

    @Test
    public void testIsOverBudget2() {
        testTransactionList.addTransaction(t1);
        testTransactionList.addTransaction(t2);
        testTransactionList.addTransaction(t3);
        testTransactionList.addTransaction(t4);

        testBudget.setBudget(200);
        assertEquals(200, testBudget.getMonthlyBudget());
        assertEquals(dtf.format(LocalDateTime.now()), testBudget.getMonthYear());
        assertTrue(testBudget.isOverBudget(testTransactionList));
    }

    @Test
    public void testIsOverBudget3() {
        testTransactionList.addTransaction(t1);
        testTransactionList.addTransaction(t2);
        testTransactionList.addTransaction(t3);

        testBudget.setBudget(200);
        assertEquals(200, testBudget.getMonthlyBudget());
        assertEquals(dtf.format(LocalDateTime.now()), testBudget.getMonthYear());
        assertTrue(testBudget.isOverBudget(testTransactionList));

        testBudget.setBudget(500);
        assertEquals(500, testBudget.getMonthlyBudget());
        assertEquals(dtf.format(LocalDateTime.now()), testBudget.getMonthYear());
        assertFalse(testBudget.isOverBudget(testTransactionList));
    }

    @Test
    public void testIsOverBudget4() {
        testTransactionList.addTransaction(t1);
        testTransactionList.addTransaction(t2);
        testTransactionList.addTransaction(t3);

        testBudget.setBudget(600);
        assertEquals(600, testBudget.getMonthlyBudget());
        assertEquals(dtf.format(LocalDateTime.now()), testBudget.getMonthYear());
        assertFalse(testBudget.isOverBudget(testTransactionList));

        testBudget.setBudget(100);
        assertEquals(100, testBudget.getMonthlyBudget());
        assertEquals(dtf.format(LocalDateTime.now()), testBudget.getMonthYear());
        assertTrue(testBudget.isOverBudget(testTransactionList));
    }

    // Tests for getMoneyLeft()

    @Test
    public void testGetMoneyLeft1() {
        testBudget.setBudget(500);
        testTransactionList.addTransaction(t1);

        int testMoneyLeft = testBudget.getMoneyLeft(testTransactionList);
        assertEquals(testMoneyLeft, 480);
    }

    @Test
    public void testGetMoneyLeft2() {
        testBudget.setBudget(500);
        testTransactionList.addTransaction(t5);

        int testMoneyLeft = testBudget.getMoneyLeft(testTransactionList);
        assertEquals(testMoneyLeft, 500);
    }

    @Test
    public void testGetMoneyLeft3() {
        testBudget.setBudget(100);
        testTransactionList.addTransaction(t2);

        int testMoneyLeft = testBudget.getMoneyLeft(testTransactionList);
        assertEquals(testMoneyLeft, 0);
    }

    @Test
    public void testGetMoneyLeft4() {
        testBudget.setBudget(500);
        testTransactionList.addTransaction(t2);

        int testMoneyLeft1 = testBudget.getMoneyLeft(testTransactionList);
        assertEquals(testMoneyLeft1, 310);

        testTransactionList.addTransaction(t4);
        int testMoneyLeft2 = testBudget.getMoneyLeft(testTransactionList);
        assertEquals(testMoneyLeft2, 265);
    }

    @Test
    public void testGetMoneyLeft5() {
        testBudget.setBudget(500);
        testTransactionList.addTransaction(t2);

        int testMoneyLeft1 = testBudget.getMoneyLeft(testTransactionList);
        assertEquals(testMoneyLeft1, 310);

        testTransactionList.addTransaction(t4);
        testTransactionList.addTransaction(t2);
        testTransactionList.addTransaction(t3);
        testTransactionList.addTransaction(t4);
        int testMoneyLeft2 = testBudget.getMoneyLeft(testTransactionList);
        assertEquals(testMoneyLeft2, 0);
    }

    @Test
    public void testGetMoneyLeft6() {
        testBudget.setBudget(500);
        testTransactionList.addTransaction(t2);
        testTransactionList.addTransaction(t5);

        int testMoneyLeft1 = testBudget.getMoneyLeft(testTransactionList);
        assertEquals(testMoneyLeft1, 310);

        testTransactionList.addTransaction(t4);
        testTransactionList.addTransaction(t2);
        testTransactionList.addTransaction(t3);
        testTransactionList.addTransaction(t4);
        int testMoneyLeft2 = testBudget.getMoneyLeft(testTransactionList);
        assertEquals(testMoneyLeft2, 0);
    }
}
