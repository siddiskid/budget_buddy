package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTransaction {
    Transaction testTransaction;

    @BeforeEach
    public void setup() {
        testTransaction = new Transaction("Dinner at Cactus Club", 20, "Eating out",
                "13/07/2023");
    }

    // Test Constructor
    @Test
    public void testConstructor() {
        assertEquals("Dinner at Cactus Club", testTransaction.getName());
        assertEquals(20, testTransaction.getCost());
        assertEquals("Eating out", testTransaction.getCategory());
        assertEquals("13/07/2023", testTransaction.getDate());
    }
}
