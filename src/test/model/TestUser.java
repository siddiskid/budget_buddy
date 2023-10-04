package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
    User testUser;
    ArrayList<Transaction> testEssentials;
    TransactionList testTransactionList;
    Transaction t1;
    Transaction t2;
    Transaction t3;
    Transaction t4;
    Transaction t5;

    @BeforeEach
    public void setup() {
        testEssentials = new ArrayList<>();
        ArrayList<Transaction> temp = new ArrayList<>();
        testTransactionList = new TransactionList(temp);
        testUser = new User("Sidd", 1000, testEssentials, testTransactionList);


        testTransactionList = new TransactionList(new ArrayList<>());

        t1 = new Transaction("Dinner at Cactus Club", 20, "Eating out", "13/08/2023");
        t2 = new Transaction("Buying Electric Guitar", 190, "Shopping", "14/08/2023");
        t3 = new Transaction("Paying MSP", 75, "Bills", "12/08/2023");
        t4 = new Transaction("Paying phone bill", 1000, "Bills", "01/08/2023");
        t5 = new Transaction("Paying internet bill", 45, "Bills", "01/07/2023");
    }

    // Test for constructor
    @Test
    public void testConstructor() {
        assertEquals("Sidd", testUser.getName());
        assertEquals(1000, testUser.getMonthlyBudget());

        ArrayList<Transaction> toCheckEssentials = new ArrayList<>();
        assertEquals(toCheckEssentials, testUser.getEssentials());
        assertEquals(0, testUser.getTransactionList().getSize());
    }

    // Tests for setMonthlyBudget()
    @Test
    public void testSetMonthlyBudget1() {
        testUser.setMonthlyBudget(2000);
        assertEquals(2000, testUser.getMonthlyBudget());
    }

    @Test
    public void testSetMonthlyBudget2() {
        testUser.setMonthlyBudget(2000);
        assertEquals(2000, testUser.getMonthlyBudget());

        testUser.setMonthlyBudget(4000);
        assertEquals(4000, testUser.getMonthlyBudget());
    }

    // Tests for getMonthYear()
    @Test
    public void testGetMonthYear() {
        assertEquals(testUser.getMonthYear(), dtf.format(LocalDateTime.now()));
    }

    // Tests for setEssentials()
    @Test
    public void testSetEssentials1() {
        testEssentials.add(t1);
        testEssentials.add(t2);
        testEssentials.add(t3);
        testUser.setEssentials(testEssentials);
        assertEquals(3, testUser.getEssentials().size());
        assertEquals(t1, testUser.getEssentials().get(0));
        assertEquals(t2, testUser.getEssentials().get(1));
        assertEquals(t3, testUser.getEssentials().get(2));
    }

    // Tests for addTransaction()
    @Test
    public void testAddTransaction1() {
        testUser.addTransaction(t1);
        assertEquals(1, testUser.getTransactionList().getSize());
        assertEquals(t1, testUser.getTransactionList().getTransaction(0));
    }

    @Test
    public void testAddTransaction2() {
        testUser.addTransaction(t1);
        assertEquals(1, testUser.getTransactionList().getSize());
        assertEquals(t1, testUser.getTransactionList().getTransaction(0));

        testUser.addTransaction(t2);
        assertEquals(2, testUser.getTransactionList().getSize());
        assertEquals(t2, testUser.getTransactionList().getTransaction(1));
    }

    @Test
    public void testAddEssential1() {
        testUser.addEssential(t1);
        assertEquals(1, testUser.getTransactionList().getSize());
        assertEquals(1, testUser.getEssentials().size());
    }

    @Test
    public void testAddEssential2() {
        testUser.addEssential(t2);
        assertEquals(1, testUser.getTransactionList().getSize());
        assertEquals(1, testUser.getEssentials().size());

        testUser.addEssential(t3);
        assertEquals(2, testUser.getTransactionList().getSize());
        assertEquals(2, testUser.getEssentials().size());

        testUser.addTransaction(t4);
        assertEquals(3, testUser.getTransactionList().getSize());
        assertEquals(2, testUser.getEssentials().size());
    }

    // Tests for getMoneyLeft()
    @Test
    public void testGetMoneyLeft1() {
        testUser.addTransaction(t1);
        assertEquals(980, testUser.getMoneyLeft());
    }

    @Test
    public void testGetMoneyLeft2() {
        testUser.addTransaction(t1);
        assertEquals(980, testUser.getMoneyLeft());

        testUser.addTransaction(t2);
        assertEquals(790, testUser.getMoneyLeft());
    }

    @Test
    public void testGetMoneyLeft3() {
        testUser.addTransaction(t1);
        assertEquals(980, testUser.getMoneyLeft());

        testUser.addTransaction(t2);
        assertEquals(790, testUser.getMoneyLeft());

        testUser.addTransaction(t4);
        assertEquals(0, testUser.getMoneyLeft());
    }

    @Test
    public void testGetMoneyLeft4() {
        testUser.addTransaction(t4);
        assertEquals(0, testUser.getMoneyLeft());
    }

    @Test
    public void testGetMoneyLeft5() {
        testUser.addTransaction(t1);
        testUser.addTransaction(t3);
        testUser.addTransaction(t5);
        assertEquals(905, testUser.getMoneyLeft());
    }

    @Test
    public void testGetMonthlyTotal1() {
        testUser.addTransaction(t1);
        testUser.addTransaction(t2);
        assertEquals(210, testUser.getMonthlyTotal());
    }

    @Test
    public void testGetMonthlyTotal2() {
        testUser.addTransaction(t2);
        testUser.addTransaction(t5);
        assertEquals(190, testUser.getMonthlyTotal());
    }

    @Test
    public void testGetAllTimeTotal1() {
        testUser.addTransaction(t1);
        testUser.addTransaction(t2);
        assertEquals(210, testUser.getAllTimeTotal());
    }

    @Test
    public void testGetAllTimeTotal2() {
        testUser.addTransaction(t1);
        testUser.addTransaction(t2);
        testUser.addTransaction(t5);
        assertEquals(255, testUser.getAllTimeTotal());
    }

    @Test
    public void testGetBetweenDatesTotal1() {
        testUser.addTransaction(t1);
        testUser.addTransaction(t2);

        // GOOD
        assertEquals(210, testUser.getBetweenDatesTotal("01/08/2023", "31/08/2023"));

        // INCORRECT YEAR
        assertEquals(0 , testUser.getBetweenDatesTotal("01/08/2022", "31/08/2022"));
        assertEquals(0 , testUser.getBetweenDatesTotal("01/08/2024", "31/08/2024"));

        // INCORRECT MONTH
        assertEquals(0, testUser.getBetweenDatesTotal("01/07/2023", "31/07/2023"));
        assertEquals(0, testUser.getBetweenDatesTotal("01/09/2023", "31/09/2023"));

        // INCORRECT DATE
        assertEquals(0, testUser.getBetweenDatesTotal("01/08/2023", "12/08/2023"));
        assertEquals(0, testUser.getBetweenDatesTotal("15/08/2023", "31/08/2023"));

        // ONE CORRECT ONE INCORRECT
        assertEquals(20, testUser.getBetweenDatesTotal("01/08/2023", "13/08/2023"));
        assertEquals(190, testUser.getBetweenDatesTotal("14/08/2023", "31/08/2023"));

        // BORDER CASE
        assertEquals(210, testUser.getBetweenDatesTotal("13/08/2023", "14/08/2023"));
    }
 }
