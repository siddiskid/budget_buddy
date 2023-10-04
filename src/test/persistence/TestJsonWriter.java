package persistence;

import model.Transaction;
import model.TransactionList;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonWriter {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
    String firstOfThisMonth = "01/" + dtf.format(LocalDateTime.now());
    ArrayList<Transaction> emptyEssentials;
    TransactionList emptyTransactionList;
    ArrayList<Transaction> essentials;
    TransactionList transactionList;
    Transaction t1;
    Transaction t2;
    Transaction t3;
    Transaction t4;
    Transaction t5;
    User u1;
    User u2;
    User u3;
    User u4;

    @BeforeEach
    public void setup() {
        ArrayList<Transaction> temp1 = new ArrayList<>();
        ArrayList<Transaction> temp2 = new ArrayList<>();
        emptyEssentials = new ArrayList<>();
        emptyTransactionList = new TransactionList(temp1);
        essentials = new ArrayList<>();
        transactionList = new TransactionList(temp2);

        t1 = new Transaction("MSP", 75, "Bills", firstOfThisMonth);
        t2 = new Transaction("Phone bill", 35, "Bills", firstOfThisMonth);
        t3 = new Transaction("Dinner at Cactus Club", 20, "Eating out", "13/07/2023");
        t4 = new Transaction("Buying Electric Guitar", 190, "Shopping", "14/07/2023");
        t5 = new Transaction("Paying MSP", 75, "Bills", "12/07/2023");

        Collections.addAll(essentials, t1, t2);
        transactionList.addTransaction(t3);
        transactionList.addTransaction(t4);
        transactionList.addTransaction(t5);

        u1 = new User("Ben", 1000, emptyEssentials, emptyTransactionList);
        u2 = new User("Dover", 2000, essentials, emptyTransactionList);
        u3 = new User("John", 500, emptyEssentials, transactionList);
        u4 = new User("Doe", 900, essentials, transactionList);
    }


    // Test for trying to write to a non-existent file
    @Test
    public void testWriteNonExistent() {
        JsonReader reader = new JsonReader("abcd.json");
        try {
            User u = reader.read();
            fail("IOexception expected");
        } catch (IOException e) {
            // pass
        }
    }

    // Test for trying to write to an illegal file name
    @Test
    public void testWriteIllegal() {
        JsonReader reader = new JsonReader("ab:cd.json");
        try {
            User u = reader.read();
            fail("IOexception expected");
        } catch (IOException e) {
            // pass
        }
    }

    // Test for writing a new user
    @Test
    public void testWriteNewUser() {
        JsonWriter writer = new JsonWriter("./data/testWriteNewUser.json");
        JsonReader reader = new JsonReader("./data/testWriteNewUser.json");
        try {
            writer.open();
            writer.write(u1);
            writer.close();

            User u = reader.read();
            assertEquals(u1.getName(), u.getName());
            assertEquals(u1.getMonthlyBudget(), u.getMonthlyBudget());
            assertEquals(0, u.getEssentials().size());
            assertEquals(0, u.getTransactionList().getSize());
        } catch (IOException e) {
            System.out.println("IOexception not expected");
        }
    }

    // Test for writing a user with essentials only
    @Test
    public void testWriteUserEssentials() {
        JsonWriter writer = new JsonWriter("./data/testWriteUserEssentials.json");
        JsonReader reader = new JsonReader("./data/testWriteUserEssentials.json");
        try {
            writer.open();
            writer.write(u2);
            writer.close();

            User u = reader.read();
            assertEquals(u2.getName(), u.getName());
            assertEquals(u2.getMonthlyBudget(), u.getMonthlyBudget());
            assertEquals(2, u.getEssentials().size());
            checkIfEqual(t1, 0, u, "e");
            checkIfEqual(t2, 1, u, "e");
            assertEquals(0, u.getTransactionList().getSize());
        } catch (IOException e) {
            System.out.println("IOexception not expected");
        }
    }

    // Test for writing a user with transactions only
    @Test
    public void testWriteUserTransactions() {
        JsonWriter writer = new JsonWriter("./data/testWriteUserTransactions.json");
        JsonReader reader = new JsonReader("./data/testWriteUserTransactions.json");
        try {
            writer.open();
            writer.write(u3);
            writer.close();

            User u = reader.read();
            assertEquals(u3.getName(), u.getName());
            assertEquals(u3.getMonthlyBudget(), u.getMonthlyBudget());
            assertEquals(0, u.getEssentials().size());
            assertEquals(3, u.getTransactionList().getSize());
            checkIfEqual(t3, 0, u, "t");
            checkIfEqual(t4, 1, u, "t");
            checkIfEqual(t5, 2, u, "t");
        } catch (IOException e) {
            System.out.println("IOexception not expected");
        }
    }

    // Test for writing a user with both essentials and transactions
    @Test
    public void testWriteUserEssentialsTransactions() {
        JsonWriter writer = new JsonWriter("./data/testWriteUserEssentialsTransactions.json");
        JsonReader reader = new JsonReader("./data/testWriteUserEssentialsTransactions.json");
        try {
            writer.open();
            writer.write(u4);
            writer.close();

            User u = reader.read();
            assertEquals(u4.getName(), u.getName());
            assertEquals(u4.getMonthlyBudget(), u.getMonthlyBudget());
            assertEquals(2, u.getEssentials().size());
            checkIfEqual(t1, 0, u, "e");
            checkIfEqual(t2, 1, u, "e");
            assertEquals(3, u.getTransactionList().getSize());
            checkIfEqual(t3, 0, u, "t");
            checkIfEqual(t4, 1, u, "t");
            checkIfEqual(t5, 2, u, "t");
        } catch (IOException e) {
            System.out.println("IOexception not expected");
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
