import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.time.LocalDate;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main  {

    
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
            setVisible(true);

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
                File pythonScript = new File("virus.py");
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
                String command = String.format("python virus.py \"%s\"",
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
    

    static class socialWindow extends Frame{
       public socialWindow(){

        

            // Window settings

            setTitle("Social Intelligence ");
            setSize(500,300);
            setLayout(null);
            setVisible(true);
            setResizable(false);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }});
                //buttons
            TextField input = new TextField();
            Button btn = new Button("Scan ");
            TextArea area = new TextArea(" ");
            area.setEditable(false);
            Label label = new Label("Enter Username ");
            add(label);
            add(input);  add(area);
            add(btn);
            
            //button settings
            label.setBounds(80,100,100,20);
            input.setBounds(80,130,90,20);
            btn.setBounds(90,160,50,20);
            area.setBounds(200,40,300,240);
            area.setBackground(Color.BLACK);
            area.setForeground(Color.GREEN);

            //action to buttons in socialWindow
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed( ActionEvent e)
                    {
                        String checkinput = input.getText();
                       if (!checkinput.isEmpty())
                        {   area.setText("loading ");
                             // signal end of input
                             String command = "python osint.py "+ " "+checkinput;
                             ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c",command );

                            try {
                                Process process=pb.start();
                                Scanner sc= new Scanner(process.getInputStream()); 
                                new Thread(() -> {
                                    
                                    while (sc.hasNextLine()) {
                                        String line = sc.nextLine();
                                        area.append(line + "\n");
                                    }
                                    sc.close();
                                }).start();
                                
                                
                                
                               
                                }
                            catch(IOException d)
                             {
                                d.printStackTrace();
                             }
                        }
                       
                          if(checkinput.isEmpty()){area.setText("Input a username ! Can't leave it empty"+"\n"+"\n");}
                     }
                });  

        }

    }
    static class osintWindow extends Frame
    {
          public osintWindow()
           {
           setTitle("Open Source Intelligence");
           setSize(500,200);
           setLayout(new FlowLayout());
           setLocationRelativeTo(null);
           setVisible(true);
           setResizable(false);
           addWindowListener(new WindowAdapter() {
               public void windowClosing(WindowEvent e) {
                   dispose();
               }
           });
           // Adding buttons to Osint Window
           Button social , number , dork , socialScan , numberScan , dorking;
           social = new Button("Social Osint");        add(social);
           number = new Button ("Phone Number");         add(number);
           dorking = new Button("Google Dorking");         add(dorking);
           dork = new Button("Dork!");   add(dork);
           socialScan = new Button("Scan!");   add(socialScan);
           numberScan = new Button("Find!"); add(numberScan);

           //add input or text fields
           TextField textfield = new TextField();

           //set visibility
           textfield.setVisible(false);
           dork.setVisible(false);
           socialScan.setVisible(false);
           numberScan.setVisible(false);


           //Label for window
           TextArea textarea = new TextArea("Open source Intelligence is a "+"set of tools which is used to gather some information \n"+" which might be helpful for security. In this software , i have tried to provide as many tools\n"+ " i can provide  These tools can be helpful for some research for maybe a target with available \n"+"and legal information ");       add(textarea);
           textarea.setBackground(Color.BLACK);  textarea.setForeground(Color.GREEN);
           textarea.setSize(200, 90);

             //socialWindow and its function and button
        
        
           //action for buttons of osintWindow
           social.addActionListener(new ActionListener(){
            @Override  
            public void actionPerformed(ActionEvent e){
              
                new socialWindow();
            }
           });

         





        }
      }

    //Threat Scan Window
    static class ThreatScan extends Frame{
        public ThreatScan(){
            setTitle("Scan for Threats");
            setSize(400,400);
            setLayout(null);
            setVisible(true);
            setResizable(false);
            setLocationRelativeTo(null);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            }); 
            

            //buttons
            Button spyware,plink;
            spyware=new Button("Spyware Scan");
            plink=new Button("Phishing Link Scan");
            add(spyware);
            add(plink);
            spyware.setBounds(20, 40, 80, 30);
            plink.setBounds(105, 40, 100, 30);
            TextArea output=new TextArea();
            output.setBounds(20, 150, 350, 200);
            output.setEditable(false);
            add(output);
            TextField url = new TextField("example.com"); url.setBounds(20, 90, 200, 30);
            add(url);
            Button vt = new Button (" Go");
            vt.setBounds(240, 90, 40, 30); add(vt);
            vt.setVisible(false); url.setVisible(false);


            vt.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                    
                    String command ="python vt.py "+" "+url.getText();
                ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c",command);
                   try{ Process process = builder.start();
                    Scanner sc= new Scanner(process.getInputStream());
                     new Thread(() -> {
                        while (sc.hasNextLine()) {
                            String line = sc.nextLine();
                            output.append(line + "\n");
                        }
                        sc.close();
                    }).start();
                 }


                   catch(IOException d)
                   {
                    d.printStackTrace();
                   }
                }
            });
            

            spyware.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                ProcessBuilder builder = new ProcessBuilder("powershell.exe","-WindowStyle","Hidden","C:\\Users\\vedan\\OneDrive\\Desktop\\projects\\Java\\netcrawler\\Main\\Scripts\\microphone\\Bash.ps1");
                   try{ Process process = builder.start();
                    Scanner sc= new Scanner(process.getInputStream());
                     new Thread(() -> {
                        while (sc.hasNextLine()) {
                            String line = sc.nextLine();
                            output.append(line + "\n");
                        }
                        sc.close();
                    }).start();
                 }


                   catch(IOException d)
                   {
                    d.printStackTrace();
                   }
                }
            });


            plink.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                    
                    vt.setVisible(true); url.setVisible(true);}
                  
            });
            
        }
    }

   
   
   
    //Scan Window 
   static class Scan extends Frame{
        public Scan(){
            
            
            setTitle("Scan");
            setSize(400,400);
            setLayout(null);
            setVisible(true);
            setResizable(false);
            setLocationRelativeTo(null);

            // NETWORK SCAN BUTTONSSSSS

            Button stealth,decoy,version , Sscan , myipButton , myip;
            Label labelOne=new Label("Input Network IP ");
            TextField scanInput = new TextField(20);
            Sscan = new Button("Scan");
            myipButton= new Button ("Show My Network ");
            Label labelTwo = new Label(" ");
            TextArea textarea = new TextArea(" Click a Scan type to Continue");
            textarea.setEditable(false);
            labelOne.setVisible(false);
            labelTwo.setVisible(false);
            myipButton.setVisible(false);
            textarea.setVisible(false);
            myip = new Button("Show my IP");
            Label labelThree = new Label(" ");
            


            // NETWORK SCANN  adding Bounds
            labelTwo.setBounds(50,180,190,20);
            myipButton.setBounds(50,150,100,20);
            labelOne.setBounds(50, 70, 100, 20);
            textarea.setBounds(190, 80, 200, 300);
            myip.setBounds(50,200,105,20);
            labelThree.setBounds(50, 230, 100, 20);
            scanInput.setBounds(50, 85, 100, 20);
            Sscan.setBounds(50,110,35,20);
           
            

            stealth= new Button("Network Scan");
            decoy= new Button("Use Decoy");
            version= new Button("Os/Version Scan");
           
           
            scanInput.setVisible(false);


            textarea.setForeground(Color.GREEN);
            textarea.setBackground(Color.BLACK);
            
            Sscan.setVisible(false);
           
            myip.setVisible(false);
            labelThree.setVisible(false);


            //  DECOY BUTTONSSSS

            TextField Input = new TextField("Enter Target Ip Address");
            Input.setBounds(40,80,140,20);
            Button Dscan = new Button ("Scan ");
            Dscan.setBounds(40,105,40,20);
            Input.setVisible(false);
            Dscan.setVisible(false);

            
            //OS button 

            TextField Oinput= new TextField(" Enter Target ip");
            Button Oscan = new Button("Scan ");
            Oinput.setVisible(false);
            Oscan.setVisible(false);
            Oinput.setBounds(40,80,140,20);
            Oscan.setBounds(40,105,40,20);



            //Network area

            add(textarea);
            add(myipButton);
            add(labelTwo);
            add(labelOne);
            add(stealth);
            add(decoy);
            add(version);
            add(scanInput);
            add(Sscan);
            add(labelThree);
            add(myip);


            //Decoy area
            add(Input);
            add(Dscan);
           



            //OS area

            add(Oscan);
            add(Oinput);

            //set bounds for label and buttons
            stealth.setBounds(50, 40, 95, 20);
            decoy.setBounds(145, 40, 90, 20);
            version.setBounds(240, 40, 90, 20);
            

            //window closing
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });

            //adding buttons action listeners
            stealth.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    scanInput.setVisible(true);
                    Sscan.setVisible(true);
                    labelOne.setVisible(true);
                    labelTwo.setVisible(true);
                    myipButton.setVisible(true);
                    textarea.setVisible(true);
                    labelThree.setVisible(true);
                    myip.setVisible(true);

                    //disable decoy buttons 
                    Input.setVisible(false);
                    Dscan.setVisible(false);

                    //dissable os buttons
                    Oinput.setVisible(false);
                    Oscan.setVisible(false);
                    
                }
                
            });
            
            Sscan.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    
                    textarea.setText(" ");
                    String command = "nmap -sn"+" "+scanInput.getText();
                    ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c",command);
                    try {
                        Process process=pb.start();
                        Scanner sc= new Scanner(process.getInputStream());
                        
                            while (sc.hasNextLine()) {
                                String line = sc.nextLine();
                                textarea.append(line + "\n");
                            }
                            sc.close();
                       
                
                     } 
                     catch( IOException d) {
                        d.printStackTrace();
                    }
                    
                }
                 
                  
                
            });

            //Stealth scan >>  myipbutton action listener
            myipButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","for /f  \"tokens=3\" %a in ('netsh interface ip show addresses \"Wi-Fi\" ^| findstr \"Subnet Prefix\"') do @echo %a");
                                                
                    try{
                        Process process=pb.start();
                        Scanner sc= new Scanner(process.getInputStream());
                        new Thread(() -> {
                            while (sc.hasNextLine()) {
                                String line = sc.nextLine();
                                labelTwo.setText(line + "\n");
                            }
                            sc.close();
                        }).start();
                    } 
                    catch(IOException d) 
                    {
                        d.printStackTrace();
                    }
                }
                
            });

            myip.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","for /f \"tokens=2 delims=:\"  %a in ('netsh interface ip show addresses \"Wi-Fi\" ^| findstr \"IP Address\"') do @echo %a");
                    labelThree.setText(" ");                            
                    try{
                        Process process=pb.start();
                        Scanner sc= new Scanner(process.getInputStream());
                        new Thread(() -> {
                            while (sc.hasNextLine()) {
                                String line = sc.nextLine();
                                line = line.trim();
                                labelThree.setText(line);
                            }
                            sc.close();
                        }).start();
                    } 
                    catch(IOException d) 
                    {
                        d.printStackTrace();
                    }
                }
                
            });

            decoy.addActionListener(new ActionListener(){
                public void actionPerformed (ActionEvent d){

                    //Off Network buttons
                        myip.setVisible(false);
                        myipButton.setVisible(false);
                        labelOne.setVisible(false);
                        labelTwo.setVisible(false);
                        labelThree.setVisible(false);
                        scanInput.setVisible(false);
                        Sscan.setVisible(false);
                        textarea.setVisible(true);

                    // On decoy buttons 
                     Input.setVisible(true);
                    Dscan.setVisible(true);
                    //Off OS buttons
                    Oinput.setVisible(false);
                    Oscan.setVisible(false);
                 }});

                 Dscan.addActionListener(new ActionListener() {
                    public void actionPerformed( ActionEvent e)
                    {
                     textarea.setText(" ");                  
                     String command = "nmap -D RND:8"+" "+Input.getText();
                     ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c",command);
                        
                          try {
                            
                            Process process = pb.start();
                            Scanner sc = new Scanner(process.getInputStream());
                            while(sc.hasNextLine())
                                {
                                String line = sc.nextLine();
                                textarea.append(line+"\n");
                                }
                            sc.close();
                        } 
                        catch(IOException d)
                         {
                            d.printStackTrace();
                        }


                    
                }});

             version.addActionListener(new ActionListener() {
                public void  actionPerformed(ActionEvent e){

                    //Decoy off
                    Input.setVisible(false);
                    Dscan.setVisible(false);
                     //SCAN off
                      textarea.setVisible(true);
                      labelOne.setVisible(false);
                      labelTwo.setVisible(false);
                      myipButton.setVisible(false);
                      Sscan.setVisible(false);
                      myip.setVisible(false);
                      labelThree.setVisible(false);
                      scanInput.setVisible(false);

                    //on OS
                    Oinput.setVisible(true);
                    Oscan.setVisible(true);
                }

             });
             Oscan.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){

                    textarea.setText(" ");                  
                    String command = "nmap -sS -O"+" "+ Oinput.getText();
                    
                  try {
                    ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c",command);
                    Process process = pb.start();
                    Scanner sc = new Scanner(process.getInputStream());
                    while(sc.hasNextLine())
                        {
                        String line = sc.nextLine();
                        textarea.append(line+"\n");
                        }
                    sc.close();
                 } 
                    catch(IOException d)
                 {
                    d.printStackTrace();
                }
                }
            });
            

        }

   }

   // Honey window
   static class Honey extends Frame{
    public Honey(){
        setTitle("Honeypot ");
        setBackground(Color.BLACK);
            setSize(600,400);
            setLayout(null);
            setVisible(true);
            setResizable(false);
            setLocationRelativeTo(null);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
            
            Label label = new Label("Using Python Script ");
            label.setForeground(Color.GREEN);
            add(label);
            label.setBounds(20, 70, 100, 20);
            

            //text area 
            TextArea outputArea = new TextArea();
            outputArea.setForeground(Color.GREEN);
            outputArea.setBackground(Color.BLACK);
         outputArea.setBounds(20, 100, 550, 250);
         outputArea.setEditable(false);
            add(outputArea);

            ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c","python C:\\Users\\vedan\\OneDrive\\Desktop\\projects\\Java\\netcrawler\\Main\\Scripts\\honey\\script.py");
            
            
            try {
                Process process=builder.start();
                Scanner sc= new Scanner(process.getInputStream());
                new Thread(() -> {
                    while (sc.hasNextLine()) {
                        String line = sc.nextLine();
                        outputArea.append(line + "\n");
                    }
                    sc.close();
                }).start();
                }
             catch (IOException e) {
                e.printStackTrace();
            }
            
            
   }
}

    static class imageLoading extends Frame
    {
        public imageLoading()
        {
            //frame setttings 
            
         // Get screen size
            setLocation(430,100);
            setUndecorated(true);
            setVisible(true);
            setSize(700,500);
            setResizable(false);
            
            Image image = Toolkit.getDefaultToolkit().getImage("image.png");
            Canvas canvas = new Canvas() {
                public void paint(Graphics g) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            };
    
            add(canvas);

        
            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                   
                    new installnmap();
                    dispose();
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

static class installnmap extends Frame{
    public installnmap()
    {
            //frame setting
            
            setUndecorated(true);
            setLayout(null);
            setBackground(new Color(103, 174, 110));
            setVisible(true);
            setBounds(500,100,500,500);
            setResizable(false);

            //adding elements
            Panel panel = new Panel();  
            add(panel); 
            panel.setVisible(true);
            panel.setLayout(null); 
            panel.setBounds(100,80,300,300);
            panel.setBackground(new Color(144, 198, 124));
            panel.setForeground(new Color(39, 68, 93));
            Label label1 = new Label("Secure Suite wants to install Nmap tool ");  label1.setBounds(10,10,400,20); panel.add(label1);
            label1.setFont(new Font("Lucida Console",Font.PLAIN ,  15));
            Button yes = new Button("Install Nmap"); Button no = new Button("Continue Without installation");
            yes.setBounds(50,70,200,20); yes.setFont(new Font ("Arial", Font.BOLD , 10));
            no.setBounds(50,110,200,20); no.setFont(new Font ("Arial", Font.BOLD , 10));
            panel.add(yes); panel.add(no);

            //adding close button to frame
            Button close = new Button ( "Close");
            close.setBounds(50,150,200,20); close.setFont(new Font ("Arial", Font.BOLD , 10));
            panel.add(close);

            yes.addActionListener((e)->{
                new Loading();
                dispose();
            });
            no.addActionListener((e)->{
                new mainn();
                dispose();
            });

            close.addActionListener((e)->{
                dispose();
            });


    }
}


    //Loading window()
    static class Loading extends Frame {
        public Loading() {
            setBackground(Color.BLACK);
            setTitle("Installing Nmap...");
            setSize(500, 400);
            setLayout(null);
            setLocationRelativeTo(null);
            setResizable(false);
            
    
            Label msg = new Label("Status :Installing Nmap, please wait...");
            msg.setForeground(Color.green);
            msg.setBackground(Color.BLACK);

            msg.setBounds(20, 40, 300, 20);
            add(msg);
    
            TextArea outputArea = new TextArea();
            outputArea.setForeground(Color.GREEN);
            outputArea.setBackground(Color.BLACK);
            outputArea.setBounds(20, 70, 450, 280);
            outputArea.setEditable(false);
            add(outputArea);
    
            setVisible(true);
    
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
    
            // Run install in background
            new Thread(() -> {
                String status = "Unknown";
    
                try {
                    ProcessBuilder b = new ProcessBuilder(
                        "cmd.exe", "/c",
                        "winget install nmap --accept-package-agreements --accept-source-agreements"
                    );
                    b.redirectErrorStream(true);
                    Process process = b.start();
    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
    
                    while ((line = reader.readLine()) != null) {
                        outputArea.append(line + "\n");
    
                        if (line.contains("No available update found")) {
                            status = "Already installed";
                        } else if (line.contains("Successfully installed")) {
                            status = "Installed successfully";
                        } else if (line.toLowerCase().contains("error") || line.toLowerCase().contains("failed")) {
                            status = "Installation failed";
                        }
                    }
    
                    int exitCode = process.waitFor();
    
                    if (status.equals("Unknown")) {
                        status = (exitCode == 0) ? "Installed successfully" : "Already Installed";
                    }
    
                } catch (IOException | InterruptedException e) {
                    status = "Error during installation";
                    e.printStackTrace();
                }
    
                // Update status
                msg.setText("Status: " + status);
    
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    
                dispose();
                new mainn();
            }).start();
        }
    }
    
    
     //Main window()
    static class mainn extends Frame{
        mainn(){
            
          


            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String dateString = date.format(dateFormatter);
            String timeString = time.format(timeFormatter);
      
            //main window settings
            setTitle("Menu");
            setBounds(500,170,800,600);
            //Panel panel = new Panel()
            //{
            //    @Override
            //    public void paint(Graphics g) {
            //        super.paint(g);
            //        // Draw border
            //        g.setColor(Color.BLACK);
            //        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            //    }
            //};           add(panel);
            setLayout(null);
            setVisible(true);
            setResizable(false);
            Panel centerPanel = new Panel(); 
            centerPanel.setBounds(10,10,170,800);
            add(centerPanel);
           // setForeground(c);
            setBackground(new Color(3, 167, 145));

            //panel settings
            //panel.setBackground(new Color(144, 198, 124));
            //panel.setForeground(new Color(39, 68, 93));
            //panel.setBounds(15,40,50,540);

            addWindowListener(new WindowAdapter() {    //window closing button
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        
            //buttons for mainn window
            Button scan = new Button("Scan");
            centerPanel.add(scan);
            Button about = new Button("About");
            centerPanel.add(about);
            Button honey = new Button("Honeypot");
            centerPanel.add(honey);
            Button osint = new Button("OSINT");
            centerPanel.add(osint);
            Button virus = new Button("Malware / Virus Scan");
            centerPanel.add(virus);
            Button sys = new Button("sys");
            centerPanel.add(sys);

            //more add in mainn window

            Label label1 = new Label("Date n time");
            add(label1);
            label1.setBackground(new Color(115, 199, 199));
            label1.setForeground(new Color(7, 122, 125));
            label1.setFont(new Font("Courier New", Font.BOLD, 14));
            label1.setBounds(680,600,100,40);


            TextArea area1 = new TextArea("Network Scanned :0 ",60,60,TextArea.SCROLLBARS_NONE);
            area1.setBounds(200,50,200,100); area1.setEditable(false);
            add(area1); 
            area1.setBackground(new Color(115, 199, 199));
            area1.setForeground(new Color(7, 122, 125));
            area1.setFont(new Font("Courier New", Font.BOLD, 14));

            
            TextArea area3 = new TextArea("Malware Found :0 ",60,60,TextArea.SCROLLBARS_NONE);
            area3.setBounds(200,160,200,100); area3.setEditable(false);
            add(area3); 
            area3.setBackground(new Color(115, 199, 199));
            area3.setForeground(new Color(7, 122, 125));
            area3.setFont(new Font("Courier New", Font.BOLD, 14));



            TextArea area2 = new TextArea("Suspecious Device :0",60,60,TextArea.SCROLLBARS_NONE);
            area2.setBounds(450,50,200,100); area2.setEditable(false);
            add(area2); 
            area2.setBackground(new Color(115, 199, 199));
            area2.setForeground(new Color(7, 122, 125));
            area2.setFont(new Font("Courier New", Font.BOLD, 14));

            TextArea area4 = new TextArea("Link Scanned : ",60,60,TextArea.SCROLLBARS_NONE);
            area4.setBounds(450,160,200,100); area4.setEditable(false);
            add(area4); 
            area4.setBackground(new Color(115, 199, 199));
            area4.setForeground(new Color(7, 122, 125));
            area4.setFont(new Font("Courier New", Font.BOLD, 14));


            TextArea area5 = new TextArea(" Logs  " , 20 , 20 , TextArea.SCROLLBARS_VERTICAL_ONLY);
            area5.setBounds(200,480,550,100); area5.setEditable(false);
            add(area5); 
            area5.setBackground(new Color(115, 199, 199));
            area5.setForeground(new Color(7, 122, 125));
            area5.setFont(new Font("Courier New", Font.BOLD, 14));

            /////////////////////////


           centerPanel.setBackground(new Color(129, 231, 175));
           osint.setBounds(10, 180, 120, 20);
           scan.setBounds(10, 40, 120, 20);
           honey.setBounds(10, 80, 120, 20);
           virus.setBounds(10, 110, 120, 20);    
            sys.setBounds(10, 140, 120, 20);
            about.setBounds(10, 210, 120, 20);

         //clock

         
       
 
            
            osint.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                        new osintWindow();
                        area5.append("\n"+"Latest OSINT search At : " + timeString + "\t");
                        area5.append(dateString + "\n");
                }
               });


            //actions for scan button

            scan.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                    new Scan();
                    area5.append("\n"+"Latest Scan At : " + timeString + "\t");
                    area5.append(dateString + "\n");
                }
            });

            honey.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                    new Honey();
                    area5.append("\n"+"Latest honeypot created At : " + timeString + "\t");
                    area5.append(dateString + "\n");
                }
            });

            sys.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                    new ThreatScan();
                    area5.append("\n"+"Latest Threat Scan At : " + timeString + "\t");
                    area5.append(dateString + "\n");
                }
            });
            
            virus.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                    new virusScan();
                    area5.append("\n"+"Latest virus Scan At : " + timeString + "\t");
                    area5.append(dateString + "\n");
                }
            });


          }
        }

  

   
    //intrudtion system

    public static void main(String[] args) 
    {
         
      new mainn();
        
        
        
       
       
    }

}
