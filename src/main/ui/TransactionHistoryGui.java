package ui;

import model.Transaction;
import model.TransactionList;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

// Represents a Transaction History Gui that contains a panel for the user's transaction history
public class TransactionHistoryGui extends JPanel {
    private User user;

    Font headerFont;
    Font lighterHeaderFont;
    Font otherFont;
    Font lighterOtherFont;

    private JTextField enterName;
    private JTextField enterDate;
    private JTextField enterCost;
    private JTextField upperDate;
    private JTextField lowerDate;

    private JRadioButton isEssential;
    private JRadioButton isNotEssential;
    private JRadioButton betweenDates;
    private JRadioButton thisMonth;
    private JRadioButton allTime;

    private JButton calculateTotalButton;

    private JComboBox chooseCategory;

    private JScrollPane transactions;
    private JPanel actions;

    // MODIFIES: this
    // EFFECTS: creates a new Transaction History Panel for the Budget Buddy App
    public TransactionHistoryGui(User user) {
        createHeaderFont();
        createLighterHeaderFont();
        createOtherFont();
        createLighterOtherFont();
        this.user = user;
        transactions = new JScrollPane(transactions());
        actions = actions();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(100, 500));
        this.add(transactions, BorderLayout.CENTER);
        this.add(actions, BorderLayout.EAST);
        this.setVisible(true);
    }

    // EFFECTS: returns a JPanel with all the transactions in user's history
    private JPanel transactions() {
        JPanel transactions = new JPanel();
        transactions.setLayout(new BoxLayout(transactions, BoxLayout.PAGE_AXIS));
        TransactionList transactionList = this.user.getTransactionList();
        for (Map.Entry<String, ArrayList<Transaction>> e : transactionList.sortByDate().entrySet()) {
            ArrayList<Transaction> toPrint = e.getValue();
            JLabel temp = new JLabel(changeDateFormat(e.getKey()));
            temp.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.GRAY));
            temp.setAlignmentX(Component.CENTER_ALIGNMENT);
            temp.setFont(lighterHeaderFont);
            transactions.add(temp);
            for (Transaction t : toPrint) {
                transactions.add(transaction(t.getName(), t.getCategory(), t.getCost()));
            }
        }
        return transactions;
    }

    // EFFECTS: returns a new date string with format changed from DD/MM/YYYY to MM/DD/YYYY
    private String changeDateFormat(String date) {
        return date.substring(3, 6) + date.substring(0, 3) + date.substring(6);
    }

    // EFFECTS: returns a wrapper JPanel for the name and category Labels
    private JPanel nameCategoryWrapper(JLabel name, JLabel category) {
        JPanel nameCategoryWrapper = new JPanel();
        nameCategoryWrapper.setLayout(new BoxLayout(nameCategoryWrapper, BoxLayout.PAGE_AXIS));
        nameCategoryWrapper.add(name);
        nameCategoryWrapper.add(Box.createVerticalStrut(10));
        nameCategoryWrapper.add(category);
        return nameCategoryWrapper;
    }

    // EFFECTS: returns a wrapper JPanel for a transaction containing its name, category, and cost
    private JPanel transaction(String name, String category, int cost) {
        JPanel transaction = new JPanel();
        transaction.setMaximumSize(new Dimension(350, 150));
        transaction.setMinimumSize(new Dimension(350, 150));
        transaction.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.GRAY));
        transaction.setLayout(new BoxLayout(transaction, BoxLayout.LINE_AXIS));
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(lighterOtherFont);
        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(lighterOtherFont);
        transaction.add(nameCategoryWrapper(nameLabel, categoryLabel));
        transaction.add(Box.createHorizontalStrut(100));
        JLabel costLabel = new JLabel(String.valueOf(cost) + "$");
        costLabel.setFont(lighterOtherFont);
        transaction.add(costLabel);
        return transaction;
    }

    // MODIFIES: this
    // EFFECTS: returns a wrapper JPanel representing the right side of the transactions screen
    //          which contains all the actions user can perform
    private JPanel actions() {
        JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.PAGE_AXIS));
        actions.setPreferredSize(new Dimension(350, 100));
        actions.add(Box.createVerticalStrut(10));
        actions.add(addTransactionPanel());
        actions.add(Box.createVerticalStrut(20));
        actions.add(totalCalculatorPanel());
        return actions;
    }

    // MODIFIES: this
    // EFFECTS: returns a wrapper JPanel which has all the add transaction actions
    private JPanel addTransactionPanel() {
        JPanel addTransactionPanel = new JPanel();
        addTransactionPanel.setLayout(new BoxLayout(addTransactionPanel, BoxLayout.PAGE_AXIS));
        addTransactionPanel.setMaximumSize(new Dimension(299, 300));
        JLabel title = new JLabel("Add a Transaction");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(headerFont);
        addTransactionPanel.add(title);
        addTransactionPanel.add(Box.createVerticalStrut(10));
        addTransactionPanel.add(nameField());
        addTransactionPanel.add(isEssentialField());
        addTransactionPanel.add(dateField());
        addTransactionPanel.add(costField());
        addTransactionPanel.add(categoryField());
        addTransactionPanel.add(addTransactionButton());
        return addTransactionPanel;
    }

    // EFFECTS: returns a wrapper JPanel for the transaction name field of the add transaction menu
    private JPanel nameField() {
        JPanel nameField = new JPanel();
        JLabel nameText = new JLabel("Name");
        nameText.setFont(otherFont);
        enterName = new JTextField();
        enterName.setPreferredSize(new Dimension(150, 35));
        nameField.add(nameText);
        nameField.add(enterName);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return nameField;
    }

    // EFFECTS: returns a wrapper JPanel for the is essential field of the add transaction menu
    private JPanel isEssentialField() {
        JPanel isEssentialField = new JPanel();
        isEssentialField.setLayout(new BoxLayout(isEssentialField, BoxLayout.PAGE_AXIS));

        JLabel isEssentialText = new JLabel("Recurring transaction?");
        isEssentialText.setFont(otherFont);
        isEssentialText.setAlignmentX(Component.CENTER_ALIGNMENT);

        isEssential = new JRadioButton("yes");
        isNotEssential = new JRadioButton("no");
        isEssential.addActionListener(e -> {
            enterDate.setText("01/" + user.getMonthYear());
            enterDate.setEditable(false);
        });
        isNotEssential.addActionListener(e -> {
            enterDate.setText("");
            enterDate.setEditable(true);
        });

        ButtonGroup essentialGroup = new ButtonGroup();
        essentialGroup.add(isEssential);
        essentialGroup.add(isNotEssential);
        isEssentialField.add(isEssentialText);
        isEssentialField.add(radioButtonWrapper());
        isEssentialField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return isEssentialField;
    }

    // EFFECTS: returns a wrapper JPanel for the is essential or not radio buttons
    private JPanel radioButtonWrapper() {
        JPanel radioButtonWrapper = new JPanel();

        radioButtonWrapper.setLayout(new BoxLayout(radioButtonWrapper, BoxLayout.LINE_AXIS));
        radioButtonWrapper.add(isEssential);
        radioButtonWrapper.add(isNotEssential);
        return radioButtonWrapper;
    }

    // EFFECTS: returns a wrapper JPanel for the date field of the add transaction menu
    private JPanel dateField() {
        JPanel dateField = new JPanel();
        JLabel dateText = new JLabel("Date (DD/MM/YYYY)");
        dateText.setFont(otherFont);
        enterDate = new JTextField();
        enterDate.setPreferredSize(new Dimension(100, 35));
        if (isEssential.isSelected()) {
            enterDate.setText("01/" + user.getMonthYear());
            enterDate.setEditable(false);
        }
        dateField.add(dateText);
        dateField.add(enterDate);
        dateField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return dateField;
    }

    // EFFECTS: returns a wrapper JPanel for the cost field of the add transaction menu
    private JPanel costField() {
        JPanel costField = new JPanel();
        JLabel costText = new JLabel("Cost");
        costText.setFont(otherFont);
        enterCost = new JTextField();
        enterCost.setPreferredSize(new Dimension(100, 35));
        costField.add(costText);
        costField.add(enterCost);
        costField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return costField;
    }

    // EFFECTS: returns a wrapper JPanel for the category field of the add transaction menu
    private JPanel categoryField() {
        JPanel categoryField = new JPanel();
        JLabel categoryText = new JLabel("Category");
        categoryText.setFont(otherFont);
        String[] categories = {"Bills", "Health", "Groceries", "Eating out", "Shopping", "Miscellaneous"};
        chooseCategory = new JComboBox(categories);
        chooseCategory.setPreferredSize(new Dimension(100, 35));
        categoryField.add(categoryText);
        categoryField.add(chooseCategory);
        categoryField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return categoryField;
    }

    // MODIFIES: this
    // EFFECTS: returns a JButton which adds a transaction to history on click
    private JButton addTransactionButton() {
        JButton addTransactionButton = new JButton("Add Transaction");
        addTransactionButton.setFont(otherFont);
        addTransactionButton.setMaximumSize(new Dimension(200, 60));
        addTransactionButton.addActionListener(e -> {
            addTransaction();
            String message;
            if (isEssential.isSelected()) {
                message = "Your transaction will automatically be added on the first of every month!";
            } else {
                message = "Your transaction has been added!";
            }
            new MessageFrameGui("Success", message);
        });
        addTransactionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return addTransactionButton;
    }

    // MODIFIES: this
    // EFFECTS: adds a transaction to transaction history of user
    private void addTransaction() {
        String transactionName = enterName.getText();
        String transactionDate = enterDate.getText();
        int transactionCost = Integer.parseInt(enterCost.getText());
        String transactionCategory = chooseCategory.getSelectedItem().toString();
        Transaction toAdd = new Transaction(transactionName, transactionCost, transactionCategory, transactionDate);
        if (isEssential.isSelected()) {
            this.user.addEssential(toAdd);
        } else {
            this.user.addTransaction(toAdd);
        }
        this.remove(transactions);
        transactions = new JScrollPane(transactions());
        this.user.getTransactionList().getSize();
        this.add(transactions, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    // EFFECTS: returns a wrapper JPanel which has all the total calculator actions
    private JPanel totalCalculatorPanel() {
        JPanel totalCalculatorPanel = new JPanel();
        totalCalculatorPanel.setLayout(new BoxLayout(totalCalculatorPanel, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Total Calculator");
        title.setFont(headerFont);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup bg = new ButtonGroup();
        thisMonth = new JRadioButton();
        allTime = new JRadioButton();
        betweenDates = new JRadioButton();
        bg.add(thisMonth);
        bg.add(allTime);
        bg.add(betweenDates);
        totalCalculatorPanel.add(title);
        totalCalculatorPanel.add(selectOption());
        calculateTotalButton = new JButton("Calculate your total");
        setTotalButtonActions();
        totalCalculatorPanel.add(calculateTotalButton);
        return totalCalculatorPanel;
    }

    private void setTotalButtonActions() {
        calculateTotalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculateTotalButton.setMaximumSize(new Dimension(220, 40));
        calculateTotalButton.setFont(otherFont);
        calculateTotalButton.addActionListener(e -> {
            if (thisMonth.isSelected()) {
                new MessageFrameGui("Stats", "Your spending for this month is: " + user.getMonthlyTotal()
                        + "$");
            }
            if (allTime.isSelected()) {
                new MessageFrameGui("Stats", "Your all time spending is: " + user.getAllTimeTotal()
                        + "$");
            }
            if (betweenDates.isSelected()) {
                new MessageFrameGui("Stats", "Your spending between " + lowerDate.getText() + " and "
                        + upperDate.getText() + " is: "
                        + user.getBetweenDatesTotal(lowerDate.getText(), upperDate.getText()) + "$");
            }
        });
    }

    // EFFECTS: returns a wrapper JPanel containing the different total calculators user can use
    private JPanel selectOption() {
        JPanel selectOption = new JPanel();
        selectOption.setLayout(new BoxLayout(selectOption, BoxLayout.PAGE_AXIS));
        selectOption.add(thisMonthPanel());
        selectOption.add(allTimePanel());
        selectOption.add(betweenDatesWrapper());
        return selectOption;
    }

    // EFFECTS: returns a wrapper JPanel containing the current month total calculator
    private JPanel thisMonthPanel() {
        JPanel thisMonthPanel = new JPanel();
        thisMonthPanel.setLayout(new BoxLayout(thisMonthPanel, BoxLayout.LINE_AXIS));
        thisMonthPanel.setMaximumSize(new Dimension(200, 50));
        thisMonth.setSelected(true);
        JLabel thisMonthText = new JLabel("This month");
        thisMonthText.setFont(otherFont);
        thisMonthPanel.add(thisMonthText);
        thisMonthPanel.add(Box.createHorizontalStrut(65));
        thisMonthPanel.add(thisMonth);
        thisMonthPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return thisMonthPanel;
    }

    // EFFECTS: returns a wrapper JPanel containing the all-time total calculator
    private JPanel allTimePanel() {
        JPanel allTimePanel = new JPanel();
        allTimePanel.setMaximumSize(new Dimension(200, 50));
        allTimePanel.setLayout(new BoxLayout(allTimePanel, BoxLayout.LINE_AXIS));
        JLabel allTimeText = new JLabel("All time");
        allTimeText.setFont(otherFont);
        allTimePanel.add(allTimeText);
        allTimePanel.add(Box.createHorizontalStrut(102));
        allTimePanel.add(allTime);
        allTimePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //allTime.addActionListener(e -> this.message = "Your all time spending is: " + user.getAllTimeTotal());
        return allTimePanel;
    }

    // EFFECTS: returns a wrapper JPanel containing the between dates total calculator
    private JPanel betweenDatesWrapper() {
        JPanel betweenDatesWrapper = new JPanel();
        betweenDatesWrapper.setLayout(new BoxLayout(betweenDatesWrapper, BoxLayout.PAGE_AXIS));
        lowerDate = new JTextField();
        lowerDate.setMaximumSize(new Dimension(100, 35));
        upperDate = new JTextField();
        upperDate.setMaximumSize(new Dimension(100, 35));
        betweenDatesWrapper.add(betweenDatesPanel());
        betweenDatesWrapper.add(betweenDatesInput());
        betweenDatesWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        return betweenDatesWrapper;
    }

    // EFFECTS: returns a wrapper JPanel containing the radio button for the between dates calculator
    private JPanel betweenDatesPanel() {
        JPanel betweenDatesPanel = new JPanel();
        betweenDatesPanel.setMaximumSize(new Dimension(200, 50));
        betweenDatesPanel.setLayout(new BoxLayout(betweenDatesPanel, BoxLayout.LINE_AXIS));
        JLabel betweenDatesText = new JLabel("Between dates");
        betweenDatesText.setFont(otherFont);
        betweenDatesPanel.add(betweenDatesText);
        betweenDatesPanel.add(Box.createHorizontalStrut(30));
        betweenDatesPanel.add(betweenDates);
        betweenDatesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return betweenDatesPanel;
    }

    // EFFECTS: returns a wrapper JPanel containing the date input fields for the between dates
    //          total calculator
    private JPanel betweenDatesInput() {
        JPanel betweenDatesInput = new JPanel();
        betweenDatesInput.setLayout(new BoxLayout(betweenDatesInput, BoxLayout.PAGE_AXIS));

        JLabel lowerDateText = new JLabel("From:");
        lowerDateText.setFont(otherFont);
        lowerDate.setPreferredSize(new Dimension(100, 30));
        JLabel upperDateText = new JLabel("To:");
        upperDateText.setFont(otherFont);
        upperDate.setPreferredSize(new Dimension(100, 30));

        JPanel temp1 = new JPanel();
        temp1.setLayout(new BoxLayout(temp1, BoxLayout.LINE_AXIS));
        temp1.add(lowerDateText);
        //temp1.add(Box.createHorizontalStrut(10));
        temp1.add(lowerDate);

        JPanel temp2 = new JPanel();
        temp2.setLayout(new BoxLayout(temp2, BoxLayout.LINE_AXIS));
        temp2.add(upperDateText);
        //temp2.add(Box.createHorizontalStrut(10));
        temp2.add(upperDate);

        betweenDatesInput.add(temp1);
        betweenDatesInput.add(temp2);
        betweenDatesInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        return betweenDatesInput;
    }

    // MODIFIES: this
    // EFFECTS: creates new headerFont
    private void createHeaderFont() {
        try {
            File path = new File("data/JUST Sans ExBold.otf");
            headerFont = Font.createFont(Font.TRUETYPE_FONT, path).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(headerFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates new lighterHeaderFont
    private void createLighterHeaderFont() {
        try {
            File path = new File("data/JUST Sans ExBold.otf");
            lighterHeaderFont = Font.createFont(Font.TRUETYPE_FONT, path).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lighterHeaderFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    // MODIFIES: this
    // EFFECTS: creates new otherFont
    private void createOtherFont() {
        try {
            File path = new File("data/JUST Sans Regular.otf");
            otherFont = Font.createFont(Font.TRUETYPE_FONT, path).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(otherFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates new lighterOtherFont
    private void createLighterOtherFont() {
        try {
            File path = new File("data/JUST Sans Regular.otf");
            lighterOtherFont = Font.createFont(Font.TRUETYPE_FONT, path).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lighterOtherFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
