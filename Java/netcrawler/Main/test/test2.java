import java.awt.*;
import java.awt.event.*;

public class test2 {
    public static void main(String[] args) {
        Frame frame = new Frame("TextArea with Buttons");

        // Create TextArea
        TextArea textArea = new TextArea(10, 40);

        // Create Buttons
        Button clearBtn = new Button("Clear");
        Button addTextBtn = new Button("Add Text");

        // Add action listeners
        clearBtn.addActionListener(e -> textArea.setText(""));
        addTextBtn.addActionListener(e -> textArea.append("Some new text...\n"));

        // Panel to hold buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(clearBtn);
        buttonPanel.add(addTextBtn);

        // Set layout and add components
        frame.setLayout(new BorderLayout());
        frame.add(textArea, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Final setup
        frame.setSize(500, 300);
        frame.setVisible(true);

        // Close window
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
            }
        });
    }
}
