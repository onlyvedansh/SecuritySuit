
static class virusScan extends JFrame {
    private JTextArea outputArea;
    private JButton selectFileButton;
    private JButton scanButton;
    private JLabel fileLabel;
    private File selectedFile;

    public virusScan() {
        // Frame setup
        setTitle("Virus Scan");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        // Use BorderLayout for better component management
        setLayout(new BorderLayout(10, 10));

        // Top panel for file selection and buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        selectFileButton = new JButton("Select File");
        scanButton = new JButton("Scan");
        fileLabel = new JLabel("No file selected");
        scanButton.setEnabled(false); // Disabled until a file is selected

        topPanel.add(selectFileButton);
        topPanel.add(scanButton);
        topPanel.add(fileLabel);
        add(topPanel, BorderLayout.NORTH);

        // Text area for output
        outputArea = new JTextArea(20, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        outputArea.setBackground(new Color(115, 199, 199));
        outputArea.setForeground(new Color(7, 122, 125));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Select file button action
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose File");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                fileLabel.setText(selectedFile.getName());
                scanButton.setEnabled(true);
                outputArea.setText(""); // Clear previous output
            }
        });

        // Scan button action
        scanButton.addActionListener(e -> {
            if (selectedFile == null) {
                JOptionPane.showMessageDialog(this, "Please select a file first.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            outputArea.setText("Starting scan...\n");
            scanButton.setEnabled(false);
            selectFileButton.setEnabled(false);

            // Run Python script in a separate thread to avoid blocking EDT
            new Thread(() -> runPythonScript()).start();
        });
    }

    private void runPythonScript() {
        try {
            // Ensure virus_check.py exists
            File pythonScript = new File("virus_check.py");
            if (!pythonScript.exists()) {
                appendOutput("Error: virus_check.py not found in the current directory.\n");
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            "virus_check.py not found in the current directory.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    resetButtons();
                });
                return;
            }

            // Build command: python virus_check.py "file_path"
            String command = String.format("python virus_check.py \"%s\"",
                    selectedFile.getAbsolutePath().replace("\"", "\\\""));
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true); // Combine stdout and stderr
            Process process = builder.start();

            // Read output in a separate thread
            try (Scanner scanner = new Scanner(process.getInputStream())) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    appendOutput(line + "\n");
                }
            }

            // Wait for the process to complete and check exit code
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                appendOutput("Error: Python script exited with code " + exitCode + "\n");
            } else {
                appendOutput("Scan completed.\n");
            }

        } catch (IOException e) {
            appendOutput("IO Error: " + e.getMessage() + "\n");
        } catch (InterruptedException e) {
            appendOutput("Process interrupted: " + e.getMessage() + "\n");
        } finally {
            SwingUtilities.invokeLater(this::resetButtons);
        }
    }

    private void appendOutput(String text) {
        // Update TextArea on EDT for thread safety
        SwingUtilities.invokeLater(() -> outputArea.append(text));
    }

    private void resetButtons() {
        scanButton.setEnabled(selectedFile != null);
        selectFileButton.setEnabled(true);
    }
}
