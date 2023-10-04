package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestEventLog {

    @Test
    public void testGetEventLog() {
        assertNotNull(EventLog.getInstance());
    }

    @Test
    public void testClearEventLog() {
        EventLog.getInstance().logEvent(new Event("Hello"));
        EventLog.getInstance().clear();
        assertNotEquals("Hello", EventLog.getInstance().toString());
    }

    @Test
    public void testGetIterator() {
        assertNotNull(EventLog.getInstance().iterator());
    }
}
