package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.*;

// class for the BudgetBuddy (console) app
public class BudgetBuddyApp {
    private static final String JSON_STORE = "./data/budgetBuddy.json";
    Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    private User user;


    // MODIFIES: this
    // EFFECTS: checks if user has previously saved their data. If yes, gives the option to continue,
    //          or start fresh. If no, creates a new user.
    public BudgetBuddyApp() {
        try {
            user = jsonReader.read();
            if (user.getName().equals("newUser")) {
                welcomeNewUser();
            } else {
                String m = "I have detected a previous save file, do you want to continue from that? (yes/no)";
                String w = "(Warning! Selecting no will erase the previous save)";
                System.out.println(m);
                System.out.println(w);
                String choice = scanner.next();
                if (choice.equals("yes")) {
                    System.out.println("Welcome back " + user.getName() + "!");
                    System.out.println(" ");
                } else {
                    user = makeNewUser();
                    saveBudgetBuddy();
                    welcomeNewUser();
                }
            }
        } catch (IOException e) {
            System.out.println("File not readable");
        }
        runBudgetBuddyApp();
    }

    // MODIFIES: this
    // EFFECTS: runs the main app. This is a while loop which does not terminate until the user
    //          wishes so. It prints out the user information on every iteration, asks user to
    //          select an option and handles the selection accordingly.
    public void runBudgetBuddyApp() {
        while (true) {
            printUserInfo();
            printSelection();
            String selectedOption = scanner.next();
            if (selectedOption.equals("5")) {
                System.out.println("Do you want to save your data? (yes/no)");
                String choice = scanner.next();
                if (choice.equals("yes")) {
                    saveBudgetBuddy();
                    System.out.println("Your data has been saved.");
                }
                System.out.println("See you later " + user.getName() + "!");
                break;
            } else {
                handleSelection(selectedOption);
            }
            System.out.println(" ");
            System.out.println("Press enter to go back to main menu");
            scanner.next();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the user's monthly budget
    public void setMonthlyBudget() {
        System.out.println(" ");
        System.out.println("How much do you want to change your monthly budget to?");

        String toSetBudget = scanner.next();
        user.setMonthlyBudget(Integer.parseInt(toSetBudget));

        System.out.println(" ");
        System.out.println("Your monthly budget has been set to " + toSetBudget + "$");
    }

    // MODIFIES: this
    // EFFECTS: sets the list of things (essentials) that the user wants automatically added
    //          on the start of every month
    public void setEssentials() {
        System.out.println(" ");
        ArrayList<Transaction> essentials = new ArrayList<>();

        while (true) {
            Transaction toAdd = handleAddTransaction("e");
            essentials.add(toAdd);

            System.out.println(" ");
            System.out.println("Do you want to add another transaction? (yes/no)");
            String cont = scanner.next();
            if (cont.equals("yes")) {
                continue;
            } else {
                break;
            }
        }

        for (Transaction t : essentials) {
            user.addTransaction(t);
        }
        user.setEssentials(essentials);

        System.out.println("Your essential transactions will be added on the first of every month!");
    }

    // MODIFIES: this
    // EFFECTS: adds transaction(s) to the transaction history
    public void addTransac() {
        System.out.println(" ");
        while (true) {
            user.addTransaction(handleAddTransaction("t"));

            System.out.println(" ");
            System.out.println("Do you want to add another transaction? (yes/no)");
            String cont = scanner.next();
            if (cont.equals("yes")) {
                continue;
            } else {
                break;
            }
        }

        System.out.println("Your transactions have been added!");
    }

    // REQUIRES: String isTOrE is one of "t" or "e"
    // EFFECTS: handles adding essential items or transactions to the user
    private Transaction handleAddTransaction(String isTOrE) {
        String date;
        if (isTOrE.equals("e")) {
            System.out.println("What is the name of the transaction you would like to have automatically added?");
        } else {
            System.out.println("What is the name of the transaction you would like to add?");
        }
        String name = scanner.next();

        System.out.println("How much money does this cost?");
        int cost = Integer.parseInt(scanner.next());

        System.out.println("What category does this transaction come under?");
        System.out.println("(Bills, Health, Groceries, Eating out, Shopping, Miscellaneous)");
        String category = scanner.next();

        if (isTOrE.equals("e")) {
            date = "01/" + user.getMonthYear();
        } else {
            System.out.println("What date did this transaction happen? (DD/MM/YYYY)");
            date = scanner.next();
        }

        return new Transaction(name, cost, category, date);
    }

    // EFFECTS: shows the user's spending history
    public void showHistory() {
        TransactionList transactionList = user.getTransactionList();
        for (Map.Entry<String, ArrayList<Transaction>> e : transactionList.sortByDate().entrySet()) {
            ArrayList<Transaction> toPrint = e.getValue();
            System.out.println(" ");
            System.out.println("Date: " + changeDateFormat(e.getKey()));
            for (int i = 0; i < toPrint.size(); i++) {
                Transaction temp = toPrint.get(i);
                System.out.println(i + 1 + ". " + temp.getName());
                System.out.println("Cost: " + temp.getCost() + "$");
                System.out.println("Category: " + temp.getCategory());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: welcomes and creates a new user
    private void welcomeNewUser() {
        System.out.println("Hey there! I'm Budget Buddy! What's your name?");
        String userName = scanner.next();
        System.out.println("Lovely to meet you " + userName + "! Let's set your monthly budget.");
        System.out.println("How much would you like for your monthly budget to be?");
        int monthlyBudget = Integer.parseInt(scanner.next());
        ArrayList<Transaction> essentials = new ArrayList<>();
        user = new User(userName, monthlyBudget, essentials, new TransactionList(new ArrayList<>()));
        System.out.println("Great! Welcome to budget buddy " + userName);
        System.out.println(" ");
    }

    // EFFECTS: returns a dummy User object
    private User  makeNewUser() {
        ArrayList<Transaction> tempEssentials = new ArrayList<>();
        ArrayList<Transaction> temp = new ArrayList<>();
        TransactionList tempTransactionList = new TransactionList(temp);

        return new User("newUser", 0, tempEssentials, tempTransactionList);
    }

    // EFFECTS: prints the name of the user, their monthly budget limit, and how much money they have left
    //          that month
    private void printUserInfo() {
        System.out.println("User: " + user.getName());
        System.out.println("Monthly budget: " + user.getMonthlyBudget() + "$");
        System.out.println("Remaining money: " + user.getMoneyLeft() + "$");
    }

    // EFFECTS: prints selection options
    private void printSelection() {
        System.out.println(" ");
        System.out.println("What can I do for you today?");
        System.out.println(" ");
        System.out.println("1. Change my monthly budget limit");
        System.out.println("2. Set up monthly auto-pay transactions");
        System.out.println("3. Add a transaction to my monthly spending tracker");
        System.out.println("4. Show me my transaction history");
        System.out.println("5. Exit Budget Buddy");
        System.out.println("(Enter the option number)");
    }

    // REQUIRES: selectedOption is one of "1", "2", "3", or "4"
    // MODIFIES: this
    // EFFECTS: handles the option that the user selected
    private void handleSelection(String selectedOption) {
        switch (selectedOption) {
            case "1":
                setMonthlyBudget();
                break;
            case "2":
                setEssentials();
                break;
            case "3":
                addTransac();
                break;
            case "4":
                showHistory();
                break;
            default:
        }
    }

    // REQUIRES: Date is in the format "MM/DD/YYYY"
    // EFFECTS: returns date in the format "DD/MM/YYYY"
    private String changeDateFormat(String date) {
        return date.substring(3, 6) + date.substring(0, 3) + date.substring(6);
    }

    // EFFECTS: saves the current user info to the target JSON file
    private void saveBudgetBuddy() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
        } catch (IOException e) {
            System.out.println("Unable to save");
        }
    }
}
