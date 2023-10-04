package persistence;

import model.Transaction;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonReader {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
    String firstOfThisMonth = "01/" + dtf.format(LocalDateTime.now());
    Transaction t1;
    Transaction t2;
    Transaction t3;
    Transaction t4;
    Transaction t5;


    @BeforeEach
    public void setup() {
        t1 = new Transaction("MSP", 75, "Bills", firstOfThisMonth);
        t2 = new Transaction("Phone bill", 35, "Bills", firstOfThisMonth);
        t3 = new Transaction("Dinner at Cactus Club", 20, "Eating out", "13/08/2023");
        t4 = new Transaction("Buying Electric Guitar", 190, "Shopping", "14/08/2023");
        t5 = new Transaction("Paying MSP", 75, "Bills", "12/08/2023");
    }

    // Test for trying to read non-existent file
    @Test
    public void testReadNonExistent() {
        JsonReader reader = new JsonReader("abcd.json");
        try {
            User u = reader.read();
            fail("IOexception expected");
        } catch (IOException e) {
            // pass
        }
    }

    // Test for trying to read illegal file name
    @Test
    public void testReadIllegal() {
        JsonReader reader = new JsonReader("ab:cd.json");
        try {
            User u = reader.read();
            fail("IOexception expected");
        } catch (IOException e) {
            // pass
        }
    }

    // Test for new user with no transactions
    @Test
    public void testReadNewUser() {
        JsonReader reader = new JsonReader("./data/testReadNewUser.json");
        try {
            User u = reader.read();
            assertEquals("Ben", u.getName());
            assertEquals(1000, u.getMonthlyBudget());
            assertEquals(0, u.getEssentials().size());
            assertEquals(0, u.getTransactionList().getSize());
        } catch (IOException e) {
            fail("IOexception not expected");
        }
    }

    // Test for user with some essentials but no transactions
    @Test
    public void testReadUserEssentials() {
        JsonReader reader = new JsonReader("./data/testReadUserEssentials.json");
        try {
            User u = reader.read();
            assertEquals("Dover", u.getName());
            assertEquals(2000, u.getMonthlyBudget());
            assertEquals(2, u.getEssentials().size());
            checkIfEqual(t1, 0, u, "e");
            checkIfEqual(t2, 1, u, "e");
            assertEquals(0, u.getTransactionList().getSize());
        } catch (IOException e) {
            fail("IOexception not expected");
        }
    }

    // Test for user with some transactions but no essentials
    @Test
    public void testReadUserTransactions() {
        JsonReader reader = new JsonReader("./data/testReadUserTransactions.json");
        try {
            User u = reader.read();
            assertEquals("John", u.getName());
            assertEquals(500, u.getMonthlyBudget());
            assertEquals(0, u.getEssentials().size());
            assertEquals(3, u.getTransactionList().getSize());
            checkIfEqual(t3, 0, u, "t");
            checkIfEqual(t4, 1, u, "t");
            checkIfEqual(t5, 2, u, "t");
        } catch (IOException e) {
            fail("IOexception not expected");
        }
    }

    // Test for user with some transactions and some essentials
    @Test
    public void testReadUserEssentialsTransactions() {
        JsonReader reader = new JsonReader("./data/testReadUserEssentialsTransactions.json");
        try {
            User u = reader.read();
            assertEquals("Doe", u.getName());
            assertEquals(900, u.getMonthlyBudget());
            assertEquals(2, u.getEssentials().size());
            checkIfEqual(t1, 0, u, "e");
            checkIfEqual(t2, 1, u, "e");
            assertEquals(3, u.getTransactionList().getSize());
            checkIfEqual(t3, 0, u, "t");
            checkIfEqual(t4, 1, u, "t");
            checkIfEqual(t5, 2, u, "t");
        } catch (IOException e) {
            fail("IOexception not expected");
        }
    }

    // REQUIRES: String isTOrE is one of "t" or "e"
    // EFFECTS: checks if two list of essentials or transactionList objects are equal
    private void checkIfEqual(Transaction t, int i, User u, String isTOrE) {
        if (isTOrE.equals("t")) {
            assertEquals(t.getName(), u.getTransactionList().getTransaction(i).getName());
            assertEquals(t.getCost(), u.getTransactionList().getTransaction(i).getCost());
            assertEquals(t.getCategory(), u.getTransactionList().getTransaction(i).getCategory());
            assertEquals(t.getDate(), u.getTransactionList().getTransaction(i).getDate());
        } else {
            assertEquals(t.getName(), u.getEssentials().get(i).getName());
            assertEquals(t.getCost(), u.getEssentials().get(i).getCost());
            assertEquals(t.getCategory(), u.getEssentials().get(i).getCategory());
            assertEquals(t.getDate(), u.getEssentials().get(i).getDate());
        }
    }
}
