package persistence;

import org.json.JSONObject;

// Represents an interface with a method to convert an Object to a JSON Object.
// Any class that intends to write to a JSON file must implement this interface.
public interface Writable {
    // EFFECTS:
    JSONObject toJson();
}
