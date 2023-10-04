package ui;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// Represents a Home Screen Gui which is what the user sees when they first log on to the app
public class HomeScreenGui extends JPanel {
    private User user;
    private final String userName;
    private int monthlyBudget;
    private int remainingMoney;
    private JPanel totalInfo;
    Font headerFont;
    Font otherFont;

    // MODIFIES: this
    // EFFECTS: creates a new Home Screen Panel with welcome messages and some quick view info about the user's budget
    public HomeScreenGui(User user) {
        createHeaderFont();
        createOtherFont();
        this.user = user;
        this.userName = user.getName();
        this.monthlyBudget = user.getMonthlyBudget();
        this.remainingMoney = user.getMoneyLeft();
        this.totalInfo = totalInfo();
        this.setLayout(new BorderLayout());
        this.add(topFiller(), BorderLayout.NORTH);
        this.add(totalInfo, BorderLayout.CENTER);
    }

    // EFFECTS: returns a JLabel with a welcome message
    private JLabel welcomeMessage() {
        JLabel welcomeMessage = new JLabel("Welcome " + this.userName + "!");
        welcomeMessage.setFont(headerFont);
        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        return welcomeMessage;
    }

    // EFFECTS: returns a JLabel with the user's currently set monthly budget
    private JLabel monthlyBudget() {
        JLabel monthlyBudget = new JLabel("Your monthly budget is currently set to " + this.monthlyBudget + "$");
        monthlyBudget.setFont(otherFont);
        monthlyBudget.setAlignmentX(Component.CENTER_ALIGNMENT);
        return monthlyBudget;
    }

    // EFFECTS: returns a JLabel with the user's currently remaining budget
    private JLabel remainingMoney() {
        JLabel remainingMoney = new JLabel("You have " + this.remainingMoney + "$ left to spend this month");
        remainingMoney.setFont(otherFont);
        remainingMoney.setAlignmentX(Component.CENTER_ALIGNMENT);
        return remainingMoney;
    }

    // EFFECTS: returns a wrapper JPanel containing all the messages and quick view info
    private JPanel totalInfo() {
        JPanel totalInfo = new JPanel();
        totalInfo.setLayout(new BoxLayout(totalInfo, BoxLayout.Y_AXIS));
        totalInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        totalInfo.add(welcomeMessage());
        totalInfo.add(Box.createVerticalStrut(10));
        totalInfo.add(monthlyBudget());
        totalInfo.add(Box.createVerticalStrut(10));
        totalInfo.add(remainingMoney());
        totalInfo.add(Box.createVerticalStrut(10));

        JPanel temp = new JPanel();
        temp.add(changeBudgetButton());

        totalInfo.add(temp);

        return totalInfo;
    }

    // MODIFIES: this
    // EFFECTS: returns a JButton which changes the user's budget on click
    private JButton changeBudgetButton() {
        JButton changeBudgetButton = new JButton("Change budget");
        changeBudgetButton.setFont(otherFont);
        changeBudgetButton.setMaximumSize(new Dimension(180,60));
        changeBudgetButton.addActionListener(e -> handleChangeBudget());
        return changeBudgetButton;
    }

    // MODIFIES: this
    // EFFECTS: changes the user's current budget
    private void handleChangeBudget() {
        try {
            String newBudget = JOptionPane.showInputDialog("What would you like your new budget to be");
            //String newBudget = JOptionPane.sh
            user.setMonthlyBudget(Integer.parseInt(newBudget));
            this.monthlyBudget = user.getMonthlyBudget();
            this.remainingMoney = user.getMoneyLeft();
            new MessageFrameGui("Success", "Your budget has been reset!");
            this.remove(totalInfo);
            this.totalInfo = totalInfo();
            this.add(totalInfo, BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
        } catch (Exception e) {
            //
        }
    }

    // EFFECTS: returns a JPanel that functions as a filler
    private JPanel topFiller() {
        JPanel topFiller = new JPanel();
        topFiller.setPreferredSize(new Dimension(100, 150));
        return topFiller;
    }

    // MODIFIES: this
    // EFFECTS: creates a new headerFont
    private void createHeaderFont() {
        try {
            File path = new File("data/JUST Sans ExBold.otf");
            headerFont = Font.createFont(Font.TRUETYPE_FONT, path).deriveFont(55f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(headerFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new otherFont
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
}
