import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class test extends JFrame {
    private final Color BG_COLOR = new Color(103, 174, 110);
    private final Color FG_COLOR = new Color(39, 68, 93);
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public test() {
        setTitle("Cyber Security Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main container with sidebar and content
        JPanel container = new JPanel(new BorderLayout());
        container.add(createSidebar(), BorderLayout.WEST);
        container.add(createHeader(), BorderLayout.NORTH);
        container.add(createMainContent(), BorderLayout.CENTER);
        
        add(container);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        try {
            ImageIcon logo = new ImageIcon("logo.png"); // Add your logo path
            JLabel logoLabel = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            header.add(logoLabel, BorderLayout.WEST);
        } catch (Exception e) {
            System.out.println("Logo not found, using text instead");
            JLabel title = new JLabel("Cyber Security Suite");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setForeground(Color.WHITE);
            header.add(title, BorderLayout.WEST);
        }

        JLabel clock = new JLabel();
        clock.setForeground(Color.WHITE);
        clock.setFont(new Font("Arial", Font.BOLD, 18));
        new Timer(1000, e -> clock.setText(new java.util.Date().toString())).start();
        header.add(clock, BorderLayout.EAST);

        return header;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(FG_COLOR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        String[][] buttons = {
            {"network", "Network Scan"},
            {"osint", "OSINT Tools"},
            {"honeypot", "Create Honeypot"},
            {"system", "System Detection"},
            {"malware", "Malware Scanner"},
            {"about", "About"}
        };

        for (String[] btn : buttons) {
            JButton button = new JButton(btn[1]);
            button.setName(btn[0]);
            styleButton(button);
            button.addActionListener(this::handleMenuClick);
            sidebar.add(button);
            sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        return sidebar;
    }

    private void styleButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(FG_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 50));
        
        // Hover effects
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BG_COLOR);
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(FG_COLOR);
                button.setForeground(Color.WHITE);
            }
        });
    }

    private JPanel createMainContent() {
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Add different views
        mainPanel.add(createNetworkScanPanel(), "network");
        mainPanel.add(createOSINTPanel(), "osint");
        mainPanel.add(createHoneypotPanel(), "honeypot");
        mainPanel.add(createSystemPanel(), "system");
        mainPanel.add(createMalwarePanel(), "malware");
        mainPanel.add(createAboutPanel(), "about");

        return mainPanel;
    }

    private void handleMenuClick(ActionEvent e) {
        String command = ((JButton) e.getSource()).getName();
        cardLayout.show(mainPanel, command);
    }

    // Different Function Panels
    private JPanel createNetworkScanPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextArea results = new JTextArea();
        JButton scanButton = new JButton("Start Network Scan");
        scanButton.addActionListener(e -> results.setText("Scanning network...\n"));
        
        panel.add(new JScrollPane(results), BorderLayout.CENTER);
        panel.add(scanButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createOSINTPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        
        String[] tools = {"Whois Lookup", "DNS Records", "IP Geolocation", "Social Media Search"};
        for (String tool : tools) {
            JButton btn = new JButton(tool);
            styleToolButton(btn);
            panel.add(btn);
        }
        return panel;
    }

    private JPanel createHoneypotPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        panel.add(new JLabel("Honeypot Configuration"));
        panel.add(new JTextField("Enter port number"));
        panel.add(new JButton("Deploy Honeypot"));
        return panel;
    }

    private JPanel createSystemPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextArea sysInfo = new JTextArea();
        sysInfo.setText("OS: " + System.getProperty("os.name") + "\n" +
                      "Arch: " + System.getProperty("os.arch") + "\n" +
                      "CPU Cores: " + Runtime.getRuntime().availableProcessors());
        
        panel.add(new JScrollPane(sysInfo), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMalwarePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.WHITE);
        
        JButton fileChooser = new JButton("Select File to Scan");
        fileChooser.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                JOptionPane.showMessageDialog(this, "Scanning: " + file.getName());
            }
        });
        
        panel.add(fileChooser);
        return panel;
    }

    private JPanel createAboutPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JTextArea about = new JTextArea();
        about.setText("Cyber Security Dashboard\nVersion 1.0\n\nDeveloped for comprehensive security operations");
        about.setEditable(false);
        
        panel.add(about, BorderLayout.CENTER);
        return panel;
    }

    private void styleToolButton(JButton btn) {
        btn.setBackground(FG_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new test().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}