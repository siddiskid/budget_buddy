package persistence;

import model.User;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

// Represents a writer that writes a User object to a JSON file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the JSON file at destination
    public void open() throws IOException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes a User object to the destination file
    public void write(User u) {
        JSONObject json = u.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: saves the changes made to the destination file
    public void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: closes the JSON file at destination
    public void close() {
        writer.close();
    }
}
