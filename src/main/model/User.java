package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;

// The User class represents some basic information about a user. It has fields for
// the user's name, monthly budget, a list of essential items they want automatically
// added every month, and their transaction history.
public class User implements Writable {
    private final String name;
    private Budget monthlyBudget;
    private ArrayList<Transaction> essentials;
    private TransactionList transactionList;

    // REQUIRES: monthlyBudget > 0
    // EFFECTS: initializes a User Object with name, monthly budget, an initial transaction history of essentials
    //          (if any), and the given transaction list (empty if user is initialized for the first time).
    public User(String name, int monthlyBudget, ArrayList<Transaction> essentials, TransactionList transactionList) {
        this.name = name;
        this.monthlyBudget = new Budget(monthlyBudget);
        this.essentials = essentials;
        this.transactionList = transactionList;
        Collections.addAll(essentials);
    }

    // EFFECTS: returns name of the user
    public String getName() {
        return name;
    }

    // MODIFIES: this, budget
    // EFFECTS: sets monthly budget of the user
    public void setMonthlyBudget(int monthlyBudget) {
        this.monthlyBudget.setBudget(monthlyBudget);
        EventLog.getInstance().logEvent(new Event("Budget changed to " + monthlyBudget));
    }

    // EFFECTS: returns the total money spent by user for this month
    public int getMonthlyTotal() {
        return this.transactionList.getMonthlyTotal(this.getMonthYear());
    }

    // EFFECTS: returns the all-time total money spent by user
    public int getAllTimeTotal() {
        return this.transactionList.getAllTimeTotal();
    }

    // REQUIRES: upperDate <= lowerDate
    // EFFECTS: returns the total money spent by user for between upperDate and lowerDate
    public int getBetweenDatesTotal(String lowerDate, String upperDate) {
        return this.transactionList.getBetweenDatesTotal(lowerDate, upperDate);
    }

    // EFFECTS: returns monthly budget of the user
    public int getMonthlyBudget() {
        return monthlyBudget.getMonthlyBudget();
    }

    // EFFECTS: returns the current month and year
    public String getMonthYear() {
        return monthlyBudget.getMonthYear();
    }

    // EFFECTS: returns amount of money left in the month for the user
    public int getMoneyLeft() {
        return monthlyBudget.getMoneyLeft(transactionList);
    }

    // EFFECTS: returns the list of transactions (transaction history) of the user
    public TransactionList getTransactionList() {
        return transactionList;
    }

    // MODIFIES: this, transactionList
    // EFFECTS: adds a transaction to the list of transactions (transaction history)
    //          of the user
    public void addTransaction(Transaction transaction) {
        transactionList.addTransaction(transaction);
        EventLog.getInstance().logEvent(new Event("Added transaction: \nName:" + transaction.getName()
                + "\nCost: " + transaction.getCost() + "\nDate: " + transaction.getDate() + "\nCategory: "
                + transaction.getCategory()));
    }

    // MODIFIES: this
    // EFFECTS: sets the essential items user wants added automatically every month
    public void setEssentials(ArrayList<Transaction> essentials) {
        this.essentials = essentials;
    }

    // MODIFIES: this
    // EFFECTS: adds an essential transaction to the list of essential transactions
    public void addEssential(Transaction transaction) {
        transactionList.addTransaction(transaction);
        this.essentials.add(transaction);
        EventLog.getInstance().logEvent(new Event("Added recurring transaction: \nName:"
                + transaction.getName() + "\nCost: " + transaction.getCost() + "\nDate: "
                + transaction.getDate() + "\nCategory: " + transaction.getCategory()));
    }

    // EFFECTS: returns the list of essential items set by the user
    public ArrayList<Transaction> getEssentials() {
        return this.essentials;
    }

    @Override
    // EFFECTS: converts the User Object to a JSON Object and returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("monthlyBudget", this.monthlyBudget.getMonthlyBudget());
        json.put("essentials", essentialsToJson());
        json.put("transactionList", transactionListToJson());
        return json;
    }

    // EFFECTS: returns a JSON Array of all Transaction Objects in the essentials list
    private JSONArray essentialsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : essentials) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns a JSON Array of all Transaction Objects in the transactionList list
    private JSONArray transactionListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : transactionList.getListOfTransaction()) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
