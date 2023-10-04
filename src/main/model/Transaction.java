package model;

import org.json.JSONObject;
import persistence.Writable;

// The Transaction class represents some basic information about a transaction. It has fields
// for the transaction's name, cost, category, and date on which it occurred.
public class Transaction implements Writable {
    private final String name;
    private final int cost;
    private final String category;
    private final String date;

    // REQUIRES: cost > 0
    //           category is one of "Bills", "Health", "Groceries", "Eating out", "Shopping", "Miscellaneous"
    // EFFECTS: initializes a new Transaction object
    public Transaction(String name, int cost, String category, String date) {
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.date = date;
    }

    // EFFECTS: returns name of the Transaction object
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns cost of the Transaction object
    public int getCost() {
        return this.cost;
    }

    // EFFECTS: returns category of the Transaction object
    public String getCategory() {
        return this.category;
    }

    // EFFECTS: returns date on which the Transaction was made in DD/MM/YYYY format
    public String getDate() {
        return this.date;
    }

    @Override
    // EFFECTS: converts the Transaction Object to a JSON Object and returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("cost", this.cost);
        json.put("category", this.category);
        json.put("date", this.date);

        return json;
    }
}
