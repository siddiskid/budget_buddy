package persistence;

import model.Transaction;
import model.TransactionList;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads data about a User from data stored in JSON format
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a User from source file and returns it,
    //          throws IOException if an error occurs during this process
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads the source file and returns it as a String
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses User from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int monthlyBudget = jsonObject.getInt("monthlyBudget");
        ArrayList<Transaction> essentials = parseEssentialsOrTransactionList("essentials", jsonObject);
        ArrayList<Transaction> tempTL = parseEssentialsOrTransactionList("transactionList", jsonObject);
        TransactionList transactionList = new TransactionList(tempTL);
        return new User(name, monthlyBudget, essentials, transactionList);
    }


    // REQUIRES: String essOrTL either "essentials" or "transactionList"
    // EFFECTS: parses essentials or transactionList from the JSON object and returns it
    private ArrayList<Transaction> parseEssentialsOrTransactionList(String essOrTL, JSONObject jsonObject) {
        ArrayList<Transaction> temp = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray(essOrTL);

        for (Object o : jsonArray) {
            JSONObject next = (JSONObject) o;
            temp.add(addTransaction(next));
        }

        return temp;
    }

    // EFFECTS: parses a Transaction from the JSON object and returns it
    private Transaction addTransaction(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int cost = jsonObject.getInt("cost");
        String category = jsonObject.getString("category");
        String date = jsonObject.getString("date");

        return new Transaction(name, cost, category, date);
    }
}
