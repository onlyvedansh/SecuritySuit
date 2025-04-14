import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketImplFactory;
import java.util.Scanner;


public class Main  {
    
    static class osintWindow extends Frame
    {
          osintWindow()
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
            textarea.setText(" ");
            ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","cd C:\\Users\\vedan\\OneDrive\\Desktop\\projects\\Java\\netcrawler\\Main\\Scripts\\OSINT","/c", "python osint.py");
                    
                try {
                    Process process  = pb.start();
                    Scanner sc = new Scanner(process.getInputStream());
                    while(sc.hasNextLine())
                    {
                        String line = sc.nextLine();
                        textarea.append(line);
                    }
                    String cmds = textarea.getText();
                  
                    
                } catch(IOException d)
                 { d.printStackTrace();
                }
                
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

            //buttons
            Button spyware,plink;
            spyware=new Button("Spyware Scan");
            plink=new Button("Phishing Link Scan");
            add(spyware);
            add(plink);
            spyware.setBounds(20, 40, 80, 30);
            plink.setBounds(105, 40, 100, 30);
            TextArea output=new TextArea();
            output.setBounds(20, 100, 350, 250);
            output.setEditable(false);
            add(output);

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

            //main window settings
            setTitle("Menu");
            setSize(500,400);
            setLayout(null);
            setLocationRelativeTo(null);
            setVisible(true);
            setResizable(false);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        
            //buttons for scan window
            Button scan = new Button("Scan");
            add(scan);
            Button honey = new Button("Honeypot");
            add(honey);
            Button osint = new Button("OSINT");
            add(osint);
            Button spoof = new Button("Spoof");
            add(spoof);
            Button flood = new Button("Flood");
            add(flood);
            Button browser = new Button("Safe Browser");
            add(browser);
            Button sys = new Button("Threat Scan");
            add(sys);

           
           osint.setBounds(115, 30, 40, 20);
           osint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                    new osintWindow();
            }
           });

    
           scan.setBounds(10, 30, 40, 20);
           honey.setBounds(55, 30, 55, 20);
            spoof.setBounds(165, 30, 40, 20);
            flood.setBounds(205, 30, 40, 20);
            browser.setBounds(250, 30, 90, 20);
            sys.setBounds(345, 30, 100, 20);

           

            //actions for scan button

            scan.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                    new Scan();
                }
            });

            honey.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                    new Honey();
                }
            });

            sys.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                    new ThreatScan();
                }
            });
            
          }
        }

  

    //OSINT window

    //spoof winodw

    //flood window

    //browser window 

    //intrudtion system

    public static void main(String[] args) 
    {
         
        new Loading();
        
        
        
       
       
    }

}