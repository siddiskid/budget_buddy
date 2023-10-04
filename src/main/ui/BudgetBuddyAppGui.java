package ui;

import model.EventLog;
import model.Event;
import model.User;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

// Represents a BudgetBuddyAppGui class which handles all the Gui tasks of the Budget Buddy App
public class BudgetBuddyAppGui extends JFrame {
    private static final String JSON_STORE = "./data/budgetBuddy.json";
    private final JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private User user;

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 700;
    private static final int SIDE_BAR_WIDTH = 270;

    private static final ImageIcon COIN_ICON = new ImageIcon("data/coin.png");
    private static final ImageIcon HOME_ICON = new ImageIcon("data/home.png");
    private static final ImageIcon TRANSACTIONS_ICON = new ImageIcon("data/transactions.png");
    private static final ImageIcon EXIT_ICON = new ImageIcon("data/exit.png");

    private JPanel homeScreen;
    private JPanel transactionHistory;

    Font headerFont;

    // MODIFIES: this
    // EFFECTS: Creates a new Gui with the given user for the Budget Buddy App
    public BudgetBuddyAppGui(User user) {
        this.user = user;
        transactionHistory = new TransactionHistoryGui(user);
        homeScreen = new HomeScreenGui(user);
        createHeaderFont();
        this.setTitle("Budget Buddy");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event el : EventLog.getInstance()) {
                    System.out.println(el.toString());
                    System.out.println(" ");
                }
                EventLog.getInstance().clear();
            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.getContentPane().setBackground(new Color(239,242,231));
        this.setLayout(new BorderLayout());
        this.add(sideNavPanel(), BorderLayout.WEST);
        this.add(homeScreen);
        this.setVisible(true);
    }

    // EFFECTS: returns a sideNavPanel JPanel
    private JPanel sideNavPanel() {
        JPanel sideNavPanel = new JPanel();
        sideNavPanel.setLayout(new BoxLayout(sideNavPanel, BoxLayout.PAGE_AXIS));
        sideNavPanel.setBackground(new Color(116,205,216));
        sideNavPanel.setPreferredSize(new Dimension(SIDE_BAR_WIDTH, HEIGHT));
        sideNavPanel.add(budgetBuddyIcon());
        sideNavPanel.add(homeButton());
        sideNavPanel.add(transactionsButton());
        sideNavPanel.add(Box.createVerticalStrut(300));
        sideNavPanel.add(exitButton());
        this.setLocationRelativeTo(null);
        return sideNavPanel;
    }

    // EFFECTS: returns a JLabel which is the Budget Buddy Icon
    private JLabel budgetBuddyIcon() {
        JLabel budgetBuddyIcon = new JLabel("Budget Buddy");
        budgetBuddyIcon.setIcon(COIN_ICON);
        budgetBuddyIcon.setBackground(new Color(116,205,216));
        budgetBuddyIcon.setFont(headerFont);
        budgetBuddyIcon.setHorizontalAlignment(JLabel.LEFT);
        budgetBuddyIcon.setVerticalAlignment(JLabel.CENTER);
        budgetBuddyIcon.setIconTextGap(10);
        budgetBuddyIcon.setBorder(new EmptyBorder(25, 20, 10, 0));
        return budgetBuddyIcon;
    }

    // EFFECTS: returns a JButton home button
    private JButton homeButton() {
        JButton home = new JButton("Home");
        home.setFont(headerFont);
        home.setIcon(HOME_ICON);
        home.setBorder(new EmptyBorder(0, 0, 0, 40));
        home.setIconTextGap(10);
        home.setMaximumSize(new Dimension(200, 100));
        home.addActionListener(e -> showHomeScreen());
        return home;
    }

    // EFFECTS: returns a JButton transactions button
    private JButton transactionsButton() {
        JButton transactionsButton = new JButton("Transactions");
        transactionsButton.setFont(headerFont);
        transactionsButton.setIcon(TRANSACTIONS_ICON);
        transactionsButton.setIconTextGap(10);
        transactionsButton.setBorder(new EmptyBorder(0, 0, 0, 30));
        transactionsButton.setMaximumSize(new Dimension(300, 100));
        transactionsButton.addActionListener(e -> showTransactions());
        return transactionsButton;
    }

    // EFFECTS: returns a JButton exit button
    private JButton exitButton() {
        JButton exit = new JButton("Exit");
        exit.setFont(headerFont);
        exit.setIcon(EXIT_ICON);
        exit.setIconTextGap(10);
        exit.setBorder(new EmptyBorder(0, 0, 0, 110));
        exit.setMaximumSize(new Dimension(300, 100));
        exit.addActionListener(e -> handleExit());
        return exit;
    }

    // EFFECTS: saves the current user to the save path
    private void saveBudgetBuddy() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // EFFECTS: asks if the user wants to save their data, saves if yes, does nothing otherwise,
    //          and closes the Budget Buddy App
    private void handleExit() {
        ImageIcon spinningCoin = new ImageIcon("data/spinningCoin.gif");
        int save = JOptionPane.showConfirmDialog(null, "Do you want to save this user?",
                "Save option", JOptionPane.YES_NO_OPTION, 0, spinningCoin);
        for (Event el : EventLog.getInstance()) {
            System.out.println(el.toString());
            System.out.println(" ");
        }
        EventLog.getInstance().clear();
        if (save == 1) {
            System.exit(0);
        } else {
            saveBudgetBuddy();
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: shows Home Screen and removes Transaction History Screen
    private void showHomeScreen() {
        this.remove(transactionHistory);
        this.add(homeScreen);
        this.revalidate();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: shows Transaction History Screen and removes Home Screen
    public void showTransactions() {
        this.remove(homeScreen);
        this.add(transactionHistory);
        this.revalidate();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates new headerFont
    private void createHeaderFont() {
        try {
            File path = new File("data/JUST Sans ExBold.otf");
            headerFont = Font.createFont(Font.TRUETYPE_FONT, path).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(headerFont);
        } catch (Exception e) {
            //
        }
    }
}
