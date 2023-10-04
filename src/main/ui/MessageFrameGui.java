package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// Represents a small popup message frame that appears on completion of certain task
public class MessageFrameGui extends JFrame {
    private final String message;

    Font lighterOtherFont;

    // MODIFIES: this
    // EFFECTS: creates a JFrame for a popup message
    public MessageFrameGui(String title, String message) {
        createLighterOtherFont();
        this.message = message;
        this.setLayout(new BorderLayout());
        this.setTitle(title);
        this.setMinimumSize(new Dimension(600, 150));
        this.setMaximumSize(new Dimension(600, 150));
        JLabel coinGif = new JLabel(new ImageIcon("data/spinningCoin.gif"));
        this.add(coinGif, BorderLayout.WEST);
        this.add(wrapper(), BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // EFFECTS: returns a wrapper for the gif, message, and the button
    private JPanel wrapper() {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.LINE_AXIS));
        JPanel textAndButton = new JPanel();
        textAndButton.setLayout(new BoxLayout(textAndButton, BoxLayout.PAGE_AXIS));
        JLabel text = new JLabel(this.message);
        text.setFont(lighterOtherFont);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton returnButton = new JButton("Continue");
        returnButton.setFont(lighterOtherFont);
        returnButton.setMaximumSize(new Dimension(100, 40));
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.addActionListener(e -> dispose());
        textAndButton.add(text);
        textAndButton.add(Box.createVerticalStrut(10));
        textAndButton.add(returnButton);
        wrapper.add(Box.createHorizontalStrut(10));
        wrapper.add(textAndButton);
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        return wrapper;
    }

    // MODIFIES: this
    // EFFECTS: creates new lighterOtherFont
    private void createLighterOtherFont() {
        try {
            File path = new File("data/JUST Sans Regular.otf");
            lighterOtherFont = Font.createFont(Font.TRUETYPE_FONT, path).deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lighterOtherFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
