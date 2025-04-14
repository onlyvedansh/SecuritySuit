import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class test {
    public static void main(String[] args) {
        Frame frame = new Frame("Simple AWT Browser");
        TextField urlField = new TextField("https://example.com");
        Button goButton = new Button("Go");
        TextArea displayArea = new TextArea("", 30, 100, TextArea.SCROLLBARS_VERTICAL_ONLY);

        Panel topPanel = new Panel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(urlField, BorderLayout.CENTER);
        topPanel.add(goButton, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(displayArea, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);

        // Close button
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        // Button click action
        goButton.addActionListener(e -> {
            String urlText = urlField.getText().trim();
            if (!urlText.startsWith("http")) {
                urlText = "https://" + urlText;
            }

            try {
                URL url = new URL(urlText);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                reader.close();
                displayArea.setText(content.toString());
            } catch (Exception ex) {
                displayArea.setText("Error: " + ex.getMessage());
            }
        });
    }
}
