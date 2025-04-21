import java.awt.*;
import java.awt.event.*;

public class test {
    public static void main(String[] args) {
        Frame frame = new Frame("Dashboard");

        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Header
        Label title = new Label("My Dashboard", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(title, BorderLayout.NORTH);

        // Center Panel (like main content)
        Panel centerPanel = new Panel(new GridLayout(2, 2, 10, 10)); // 2x2 grid

        centerPanel.add(new Button("View Reports"));
        centerPanel.add(new Button("Manage Users"));
        centerPanel.add(new Button("Settings"));
        centerPanel.add(new Button("Logout"));

        frame.add(centerPanel, BorderLayout.CENTER);

        // Footer
        Label footer = new Label("© 2025 Dashboard Inc.", Label.CENTER);
        footer.setFont(new Font("Arial", Font.ITALIC, 12));
        frame.add(footer, BorderLayout.SOUTH);

        // Event to close window
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
}
