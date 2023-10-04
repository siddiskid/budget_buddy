package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Represents a WelcomeNewUser class that welcomes the user upon opening the app and asks in the case where
// it detects a previous save file, asks if the user wants to load from it. If no previous save is detected,
// creates a new user.
public class WelcomeNewUserGui extends JFrame {
    private static final String JSON_STORE = "./data/budgetBuddy.json";
    private final JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private final JsonReader jsonReader = new JsonReader(JSON_STORE);

    private User user;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    private JPanel shouldLoad;
    private JPanel newUserPanel;

    private JTextField nameField;
    private JTextField monthlyBudgetField;

    // MODIFIES: this
    // EFFECTS: creates a new JFrame that greets the user if it detects a previous save, asks user
    //          if they want to load from the save. Creates new user if no previous save is detected.
    public WelcomeNewUserGui() {
        try {
            this.user = jsonReader.read();
            this.setTitle("Budget Buddy");
            this.shouldLoad = shouldLoad(user.getName());
            this.newUserPanel = greetNewUser();
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
            this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
            this.getContentPane().setBackground(new Color(239,242,231));
            this.setLayout(new BorderLayout());
            this.setLocationRelativeTo(null);
            addFillers();
            if (this.user.getName().equals("newUser")) {
                this.add(newUserPanel, BorderLayout.CENTER);
            } else {
                this.add(shouldLoad, BorderLayout.CENTER);
            }
            this.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // EFFECTS: returns a JPanel which asks if the user wants to load from the save file
    private JPanel shouldLoad(String userName) {
        JPanel shouldLoad = new JPanel();
        shouldLoad.setLayout(new BoxLayout(shouldLoad, BoxLayout.PAGE_AXIS));
        String askingText = "I have detected a previous save file by \""
                + userName + "\". Do you want to load from that?";
        String warningText = "Warning! Selecting no will erase all previous saves.";
        JLabel askingTextLabel = new JLabel(askingText);
        askingTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel warningTextLabel = new JLabel(warningText);
        warningTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        shouldLoad.add(askingTextLabel);
        shouldLoad.add(warningTextLabel);
        shouldLoad.add(yesNoWrapper());
        shouldLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
        return shouldLoad;
    }

    // EFFECTS: returns a wrapper JPanel for two buttons
    private JPanel yesNoWrapper() {
        JPanel yesNoWrapper = new JPanel();
        yesNoWrapper.setLayout(new BoxLayout(yesNoWrapper, BoxLayout.LINE_AXIS));

        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");

        yesButton.setMaximumSize(new Dimension(100, 50));
        noButton.setMaximumSize(new Dimension(100, 50));

        yesButton.addActionListener(e -> handleLoadUser());

        noButton.addActionListener(e -> handleNewUser());

        yesNoWrapper.add(yesButton);
        yesNoWrapper.add(noButton);

        yesNoWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        return yesNoWrapper;
    }

    // MODIFIES: this
    // EFFECTS: creates a new BudgetBuddyAppGui with the current user
    private void handleLoadUser() {
        new BudgetBuddyAppGui(this.user);
        this.dispose();
    }

    // MODIFIES: this
    // EFFECTS: handles the creation and adding of a new user
    private void handleNewUser() {
        User tempUser = makeNewUser();
        try {
            jsonWriter.open();
            jsonWriter.write(tempUser);
            jsonWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.remove(shouldLoad);
        this.add(newUserPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    // EFFECTS: makes a new User object
    private User makeNewUser() {
        ArrayList<Transaction> tempEssentials = new ArrayList<>();
        ArrayList<Transaction> temp = new ArrayList<>();
        TransactionList tempTransactionList = new TransactionList(temp);

        return new User("newUser", 0, tempEssentials, tempTransactionList);
    }

    // EFFECTS: returns a JPanel which greets a new user and handles the creation of one
    private JPanel greetNewUser() {
        JPanel greetNewUser = new JPanel();
        greetNewUser.setLayout(new BoxLayout(greetNewUser, BoxLayout.PAGE_AXIS));

        JLabel welcomeTextLabel = new JLabel("Hey there! Welcome to Budget Buddy");
        welcomeTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel getStartedLabel = new JLabel("Let's get you up and running");
        getStartedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        greetNewUser.add(welcomeTextLabel);
        greetNewUser.add(getStartedLabel);

        greetNewUser.add(enterNamePanel());
        greetNewUser.add(enterBudgetPanel());

        JButton createNewButton = new JButton("Create new user");
        createNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createNewButton.addActionListener(e -> handleCreateNewUser(nameField.getText(), monthlyBudgetField.getText()));
        greetNewUser.add(createNewButton);
        greetNewUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        return greetNewUser;
    }

    // MODIFIES: this
    // EFFECTS: creates a new User object and assigns it to the current user
    private void handleCreateNewUser(String name, String monthlyBudget) {
        int budget = Integer.parseInt(monthlyBudget);
        ArrayList<Transaction> essentials = new ArrayList<>();
        this.user = new User(name, budget, essentials, new TransactionList(new ArrayList<>()));
        new BudgetBuddyAppGui(this.user);
    }

    // EFFECTS: returns a wrapper JPanel for a name field
    private JPanel enterNamePanel() {
        JPanel enterNamePanel = new JPanel();
        enterNamePanel.setLayout(new BoxLayout(enterNamePanel, BoxLayout.PAGE_AXIS));
        JLabel nameText = new JLabel("What's your name?");
        nameText.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(150, 40));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterNamePanel.add(nameText);
        enterNamePanel.add(nameField);
        enterNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return enterNamePanel;
    }

    // EFFECTS: returns a wrapper JPanel for a budget field
    private JPanel enterBudgetPanel() {
        JPanel enterBudgetPanel = new JPanel();
        enterBudgetPanel.setLayout(new BoxLayout(enterBudgetPanel, BoxLayout.PAGE_AXIS));
        JLabel monthlyBudget = new JLabel("How much would you like for your monthly budget to be?");
        monthlyBudget.setAlignmentX(Component.CENTER_ALIGNMENT);
        monthlyBudgetField = new JTextField();
        monthlyBudgetField.setMaximumSize(new Dimension(100, 40));
        monthlyBudgetField.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterBudgetPanel.add(monthlyBudget);
        enterBudgetPanel.add(monthlyBudgetField);
        enterBudgetPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return enterBudgetPanel;
    }

    // EFFECTS: returns a filler panel that spans horizontally
    private JPanel horizFiller() {
        JPanel horizFiller = new JPanel();
        horizFiller.setMaximumSize(new Dimension(100, 10));
        setVisible(true);
        return horizFiller;
    }

    // EFFECTS: returns a filler panel that spans vertically
    private JPanel vertFiller() {
        JPanel vertFiller = new JPanel();
        vertFiller.setMaximumSize(new Dimension(10, 100));
        setVisible(true);
        return vertFiller;
    }

    private void addFillers() {
        this.add(horizFiller(), BorderLayout.EAST);
        this.add(horizFiller(), BorderLayout.WEST);
        this.add(vertFiller(), BorderLayout.NORTH);
        this.add(vertFiller(), BorderLayout.SOUTH);
    }
}
