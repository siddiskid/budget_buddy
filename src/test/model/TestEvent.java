package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class TestEvent {
    private Event e1;
    private Event e2;
    private Event e3;
    private Event e4;

    @BeforeEach
    private void setup() {
        e1 = new Event("Budget changed to 1000");
        e2 = new Event("Added transaction: \n" +
                "Name:McDonald's\n" +
                "Cost: 20\n" +
                "Date: 07/08/2023\n" +
                "Category Eating out");
        e3 = new Event("Added recurring transaction: \n" +
                "Name:Rent\n" +
                "Cost: 1000\n" +
                "Date: 01/08/2023\n" +
                "Category Bills");
    }

    @Test
    public void testConstructor() {
        assertEquals("Budget changed to 1000", e1.getDescription());
        assertEquals(Calendar.getInstance().getTime().toString(), e1.getDate().toString());
        assertEquals("Added transaction: \n" +
                "Name:McDonald's\n" +
                "Cost: 20\n" +
                "Date: 07/08/2023\n" +
                "Category Eating out", e2.getDescription());
        assertEquals(Calendar.getInstance().getTime().toString(), e2.getDate().toString());
        assertEquals("Added recurring transaction: \n" +
                "Name:Rent\n" +
                "Cost: 1000\n" +
                "Date: 01/08/2023\n" +
                "Category Bills", e3.getDescription());
        assertEquals(Calendar.getInstance().getTime().toString(), e3.getDate().toString());
    }

    @Test
    public void testEquals1() {
        assertFalse(e1.equals(null));
    }

    @Test
    public void testEquals3() {
        assertTrue(e1.equals(e1));
    }

    @Test
    public void testEquals4() {
        assertFalse(e1.equals(e2));
    }

    @Test
    public void testEquals5() {
        try {
            Thread.sleep(5000);
            e4 = new Event("Budget changed to 1000");
            assertFalse(e1.equals(e4));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEquals6() {
        try {
            Thread.sleep(5000);
            e4 = new Event("Budget changed to 1000");
            assertFalse(e2.equals(e4));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEquals7() {
        assertFalse(e1.equals(new Transaction("McD", 20, "Eating out", "12/12/2003")));
    }

    @Test
    public void testToString() {
        assertEquals(e1.getDate() + "\n" + e1.getDescription(),
                e1.toString());
    }

    @Test
    public void testHash1() {
        assertEquals(e1.hashCode(), e1.hashCode());
    }

    @Test
    public void testHash2() {
        assertNotEquals(e2.hashCode(), e1.hashCode());
    }
}
