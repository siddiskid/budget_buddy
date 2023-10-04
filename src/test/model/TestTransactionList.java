package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTransactionList {
    TransactionList testTransactionList;
    Transaction t1;
    Transaction t2;
    Transaction t3;
    Transaction t4;
    Transaction t5;
    Transaction t6;
    Transaction t7;
    Transaction t8;
    Transaction t9;
    Transaction t10;
    Transaction t11;
    Transaction t12;
    Transaction t13;
    Transaction t14;
    Transaction t15;
    Transaction t16;
    ArrayList<ArrayList<Transaction>> toBeEqual;
    ArrayList<Transaction> dummy1;
    ArrayList<Transaction> dummy2;
    ArrayList<Transaction> dummy3;
    ArrayList<Transaction> dummy4;
    ArrayList<Transaction> dummy5;
    ArrayList<Transaction> dummy6;

    @BeforeEach
    public void setup() {
        testTransactionList = new TransactionList(new ArrayList<>());
        dummy1 = new ArrayList<>();
        dummy2 = new ArrayList<>();
        dummy3 = new ArrayList<>();
        dummy4 = new ArrayList<>();
        dummy5 = new ArrayList<>();
        dummy6 = new ArrayList<>();
        toBeEqual = new ArrayList<>();

        t1 = new Transaction("Paying MSP", 75, "Bills", "12/07/2023");
        t2 = new Transaction("Paying phone bill", 45, "Bills", "01/07/2023");

        t3 = new Transaction("Pills", 20, "Health", "10/07/2023");
        t4 = new Transaction("Surgery", 1000, "Health", "15/07/2023");
        t5 = new Transaction("Bandages", 10, "Health", "16/07/2023");
        t6 = new Transaction("More pills", 10, "Health", "16/07/2023");

        t7 = new Transaction("Mangoes", 5, "Groceries", "01/07/2023");
        t8 = new Transaction("Chicken", 20, "Groceries", "01/07/2023");
        t9 = new Transaction("Rice", 10, "Groceries", "01/07/2023");

        t10 = new Transaction("Dinner at Cactus Club", 20, "Eating out", "13/07/2023");
        t11 = new Transaction("McDonald's", 10, "Eating out", "07/07/2023");
        t12 = new Transaction("Pizza", 5, "Eating out", "16/07/2023");

        t13 = new Transaction("Buying Electric Guitar", 190, "Shopping", "14/07/2023");

        t14 = new Transaction("Lost Money", 10, "Miscellaneous", "03/07/2023");
        t15 = new Transaction("Lent to a friend", 50, "Miscellaneous", "04/07/2023");

        t16 = new Transaction("Paying internet bill", 45, "Bills", "01/06/2023");
    }

    // Tests for addTransaction()
    @Test
    public void testAddTransaction1() {
        testTransactionList.addTransaction(t1);
        assertEquals(1, testTransactionList.getSize());
        assertEquals(t1, testTransactionList.getTransaction(0));
    }

    @Test
    public void testAddTransaction2() {
        testTransactionList.addTransaction(t1);
        assertEquals(1, testTransactionList.getSize());
        assertEquals(t1, testTransactionList.getTransaction(0));

        testTransactionList.addTransaction(t2);
        assertEquals(2, testTransactionList.getSize());
        assertEquals(t2, testTransactionList.getTransaction(1));
    }

    // Tests for removeTransaction()
    @Test
    public void testRemoveTransaction1() {
        testTransactionList.addTransaction(t1);
        assertEquals(1, testTransactionList.getSize());
        assertEquals(t1, testTransactionList.getTransaction(0));

        testTransactionList.removeTransaction(0);
        assertEquals(0, testTransactionList.getSize());
    }

    @Test
    public void testRemoveTransaction2() {
        testTransactionList.addTransaction(t1);
        testTransactionList.addTransaction(t2);
        testTransactionList.addTransaction(t3);
        assertEquals(3, testTransactionList.getSize());
        assertEquals(t1, testTransactionList.getTransaction(0));
        assertEquals(t2, testTransactionList.getTransaction(1));
        assertEquals(t3, testTransactionList.getTransaction(2));

        testTransactionList.removeTransaction(1);
        assertEquals(2, testTransactionList.getSize());
        assertEquals(t1, testTransactionList.getTransaction(0));
        assertEquals(t3, testTransactionList.getTransaction(1));
    }

    @Test
    public void testRemoveTransaction3() {
        testTransactionList.addTransaction(t1);
        testTransactionList.addTransaction(t2);
        testTransactionList.addTransaction(t3);
        assertEquals(3, testTransactionList.getSize());
        assertEquals(t1, testTransactionList.getTransaction(0));
        assertEquals(t2, testTransactionList.getTransaction(1));
        assertEquals(t3, testTransactionList.getTransaction(2));

        testTransactionList.removeTransaction(1);
        assertEquals(2, testTransactionList.getSize());
        assertEquals(t1, testTransactionList.getTransaction(0));
        assertEquals(t3, testTransactionList.getTransaction(1));

        testTransactionList.addTransaction(t4);
        assertEquals(3, testTransactionList.getSize());
        assertEquals(t1, testTransactionList.getTransaction(0));
        assertEquals(t3, testTransactionList.getTransaction(1));
        assertEquals(t4, testTransactionList.getTransaction(2));
    }

    // Tests for testGetListOfTransaction()
    @Test
    public void testGetListOfTransaction() {
        testTransactionList.addTransaction(t1);
        testTransactionList.addTransaction(t4);
        testTransactionList.addTransaction(t7);
        testTransactionList.addTransaction(t10);
        assertEquals(t1, testTransactionList.getListOfTransaction().get(0));
        assertEquals(t4, testTransactionList.getListOfTransaction().get(1));
        assertEquals(t7, testTransactionList.getListOfTransaction().get(2));
        assertEquals(t10, testTransactionList.getListOfTransaction().get(3));
    }

    // Tests for sortByDate()
    @Test
    public void testSortByDate() {
        testTransactionList.addTransaction(t1);
        testTransactionList.addTransaction(t2);
        testTransactionList.addTransaction(t3);
        testTransactionList.addTransaction(t4);
        testTransactionList.addTransaction(t5);
        testTransactionList.addTransaction(t6);
        testTransactionList.addTransaction(t7);
        testTransactionList.addTransaction(t8);
        testTransactionList.addTransaction(t9);
        testTransactionList.addTransaction(t10);
        testTransactionList.addTransaction(t11);
        testTransactionList.addTransaction(t12);
        testTransactionList.addTransaction(t13);
        testTransactionList.addTransaction(t14);
        testTransactionList.addTransaction(t15);

        TreeMap<String, ArrayList<Transaction>> toCheck = new TreeMap<>();
        toCheck.put("07/01/2023", new ArrayList<>(Arrays.asList(t2, t7, t8, t9)));
        toCheck.put("07/03/2023", new ArrayList<>(Arrays.asList(t14)));
        toCheck.put("07/04/2023", new ArrayList<>(Arrays.asList(t15)));
        toCheck.put("07/07/2023", new ArrayList<>(Arrays.asList(t11)));
        toCheck.put("07/10/2023", new ArrayList<>(Arrays.asList(t3)));
        toCheck.put("07/12/2023", new ArrayList<>(Arrays.asList(t1)));
        toCheck.put("07/13/2023", new ArrayList<>(Arrays.asList(t10)));
        toCheck.put("07/14/2023", new ArrayList<>(Arrays.asList(t13)));
        toCheck.put("07/15/2023", new ArrayList<>(Arrays.asList(t4)));
        toCheck.put("07/16/2023", new ArrayList<>(Arrays.asList(t5, t6, t12)));

        assertEquals(toCheck, testTransactionList.sortByDate());
    }

    @Test
    public void testGetMonthlyTotal1() {
        testTransactionList.addTransaction(t10);
        testTransactionList.addTransaction(t13);
        assertEquals(210, testTransactionList.getMonthlyTotal("07/2023"));
    }

    @Test
    public void testGetMonthlyTotal2() {
        testTransactionList.addTransaction(t13);
        testTransactionList.addTransaction(t16);
        assertEquals(190, testTransactionList.getMonthlyTotal("07/2023"));
    }

    @Test
    public void testGetAllTimeTotal1() {
        testTransactionList.addTransaction(t10);
        testTransactionList.addTransaction(t13);
        assertEquals(210, testTransactionList.getAllTimeTotal());
    }

    @Test public void testGetAllTimeTotal2() {
        testTransactionList.addTransaction(t10);
        testTransactionList.addTransaction(t13);
        testTransactionList.addTransaction(t16);
        assertEquals(255, testTransactionList.getAllTimeTotal());
    }

    @Test
    public void testGetBetweenDatesTotal1() {
        testTransactionList.addTransaction(t10);
        testTransactionList.addTransaction(t13);

        // GOOD
        assertEquals(210, testTransactionList.getBetweenDatesTotal("01/07/2023", "31/07/2023"));

        // INCORRECT YEAR
        assertEquals(0 , testTransactionList.getBetweenDatesTotal("01/07/2022", "31/07/2022"));
        assertEquals(0 , testTransactionList.getBetweenDatesTotal("01/07/2024", "31/07/2024"));

        // INCORRECT MONTH
        assertEquals(0, testTransactionList.getBetweenDatesTotal("01/06/2023", "31/06/2023"));
        assertEquals(0, testTransactionList.getBetweenDatesTotal("01/08/2023", "31/08/2023"));

        // INCORRECT DATE
        assertEquals(0, testTransactionList.getBetweenDatesTotal("01/07/2023", "12/07/2023"));
        assertEquals(0, testTransactionList.getBetweenDatesTotal("15/07/2023", "31/07/2023"));

        // ONE CORRECT ONE INCORRECT
        assertEquals(20, testTransactionList.getBetweenDatesTotal("01/07/2023", "13/07/2023"));
        assertEquals(190, testTransactionList.getBetweenDatesTotal("14/07/2023", "31/07/2023"));

        // BORDER CASE
        assertEquals(210, testTransactionList.getBetweenDatesTotal("13/07/2023", "14/07/2023"));
    }
}
