import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class GUISwitch implements ActionListener{
	public String[] input = new String[4];
	JFrame popup;

	JTextField SepalLength;
	JTextField SepalWidth;
	JTextField PetalLength;
	JTextField PetalWidth;

	private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel loginPanel;

	JPanel F;
	JPanel V;
	JPanel C;
	JPanel R;
	
	String t1;
	String[] t2;
	String answer;
	JLabel classify;
	float [] d;
	Iris IrisIn;
	RandomForest rfIris;
	JPanel inGUI;//takes in Iris inputs, moves to outputGUI
	JPanel outGUI;//displays Iris classification, moves to inputGUI or similarGUI
	JPanel similarGUI;//displays a similar Iris, moves to inputGUI or get another similar Iris
	JTextField get1;
	JTextField get2;
	JTextField get3;
	JTextField get4;
	JTextField get5;
	JTextField get6;
	JTextField get7;
	JTextField get8;
	JTextField get9;
	JTextField get10;
	JTextField get11;
	JTextField get12;
	JTextField get13;
	//display errors using joptionpane
	//create all GUIs

	private void handleLogin() {
        // Implement your login logic here
        // For simplicity, using a hardcoded username and password for testing
        String testUsername = "Group2";
        String testPassword = "password";

        String enteredUsername = usernameField.getText();
        String enteredPassword = new String(passwordField.getPassword());

        if (enteredUsername.equals(testUsername) && enteredPassword.equals(testPassword)) {
            JOptionPane.showMessageDialog(frame, "Login successful!");

		frame.getContentPane().remove(loginPanel);
            switchToCalculatePanel();
        } 
		else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.");
        }
    }

		private void displayIrisFlowerImage(String imagePath, int x, int y, int width, int height, JPanel panel) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();

        // Scale the image to the specified width and height
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Create a new ImageIcon from the scaled image
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create a JLabel to display the scaled image
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(x, y, width, height);

    }

	GUISwitch(RandomForest forest, float [] def) {


		IrisIn = new Iris();
		d = def;
		rfIris = forest;
		popup = new JFrame();
		popup.setSize(690, 420);
		popup.setTitle("Iris Tester");
		popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//popup.setLayout(new GridLayout(7, 3));
		popup.getContentPane().setBackground(new Color(0, 0, 0));
		
		inGUI = new JPanel();
		inGUI.setBackground(new Color(0, 0, 0));
		inGUI.setLayout(new GridLayout(2, 5));
		
		F = new JPanel();
		F.setBackground(new Color(0, 0, 0));
		V = new JPanel();
		V.setBackground(new Color(0, 0, 0));
		C = new JPanel();
		C.setBackground(new Color(0, 0, 0));
		R = new JPanel();
		R.setBackground(new Color(0, 0, 0));
		
		JButton button = new JButton();
		button.setText("Test");
		button.addActionListener(this);
		button.setFocusable(false);
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(128, 128, 128));
		button.setBorder(new LineBorder (new Color(128, 128, 128)));
		
		inGUI.add(new JLabel());
		inGUI.add(new JLabel());
		inGUI.add(new JLabel());
		inGUI.add(new JLabel());
		inGUI.add(new JLabel());
		inGUI.add(F);
		inGUI.add(V);
		inGUI.add(C);
		inGUI.add(R);
		inGUI.add(button);


		frame = new JFrame("Iris Plant Classification App");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblSystem = new JLabel("Iris Species Mastery System");
        lblSystem.setBounds(350, 20, 200, 25);
        //loginPanel.add(lblSystem);
        // Load and display the iris image in the login panel
        displayIrisFlowerImage("C:\\Users\\alexa\\OneDrive\\Documents\\370", 350, 150, 200, 150, loginPanel);

        loginPanel = new JPanel();
        frame.getContentPane().add(loginPanel, "Center");
        loginPanel.setLayout(null);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 50, 80, 25);
        loginPanel.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 150, 25);
        loginPanel.add(usernameField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 100, 80, 25);
        loginPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 150, 25);
        loginPanel.add(passwordField);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 150, 100, 30);
        loginPanel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        frame.setVisible(true);
    }
		


	private void switchToCalculatePanel() {



		JLabel SL = new JLabel("Sepal Length(cm):");
		SepalLength = new JTextField(5);
		SL.setForeground(Color.WHITE);
		SepalLength.setForeground(Color.WHITE);
	    SepalLength.setBackground(new Color(0, 0, 0));
	    SepalLength.setBorder(new LineBorder (new Color(255, 255, 255)));;
		F.add(SL);
		F.add(SepalLength);
		
		
		JLabel SW = new JLabel("Sepal Width(cm):");
		SepalWidth = new JTextField(5);
		SW.setForeground(Color.WHITE);
		SepalWidth.setForeground(Color.WHITE);
		SepalWidth.setBackground(new Color(0, 0, 0));
		SepalWidth.setBorder(new LineBorder (new Color(255, 255, 255)));;
		V.add(SW);
		V.add(SepalWidth);
		
		
		JLabel PL = new JLabel("Petal Length(cm):");
		PetalLength = new JTextField(5);
		PL.setForeground(Color.WHITE);
		PetalLength.setForeground(Color.WHITE);
		PetalLength.setBackground(new Color(0, 0, 0));
		PetalLength.setBorder(new LineBorder (new Color(255, 255, 255)));;
		C.add(PL);
		C.add(PetalLength);
		
		JLabel PW = new JLabel("Petal Width(cm):");
		PetalWidth = new JTextField(5);
		PW.setForeground(Color.WHITE);
		PetalWidth.setForeground(Color.WHITE);
		PetalWidth.setBackground(new Color(0, 0, 0));
		PetalWidth.setBorder(new LineBorder (new Color(255, 255, 255)));;
		R.add(PW);
		R.add(PetalWidth);
		
		
		
		inGUI.setSize(690,400);
		popup.add(inGUI);
		popup.setVisible(true);
		
		outGUI = new JPanel();
		outGUI.setBackground(new Color(128, 128, 128));
		outGUI.setLayout(new GridLayout(3,3));
		outGUI.setSize(699, 499);
		
		JTextArea topT = new JTextArea("      This classifier has predicted your\n\tIris to be");
		topT.setEditable(false);
		topT.setForeground(Color.WHITE);
		topT.setBackground(new Color(128, 128, 128));
		
		classify = new JLabel("",SwingConstants.CENTER);
		classify.setForeground(Color.WHITE);
		classify.setBackground(new Color(128, 128, 128));	
		
		JButton again = new JButton("Try another Iris");
		again.addActionListener(this);
		again.setForeground(Color.WHITE);
		again.setBackground(new Color(128, 128, 128));
		again.setBorder(new LineBorder (new Color(128, 128, 128)));
		
		JButton recom = new JButton("Find similar Iris");
		recom.addActionListener(this);
		recom.setForeground(Color.WHITE);
		recom.setBackground(new Color(227, 83, 117));
		recom.setBorder(new LineBorder (new Color(227, 83, 117)));
		
		
		outGUI.add(new JLabel());
		outGUI.add(topT);
		outGUI.add(new JLabel());
		outGUI.add(new JLabel());
		outGUI.add(classify);
		outGUI.add(new JLabel());
		outGUI.add(again);
		outGUI.add(new JLabel());
		outGUI.add(recom);
		
		similarGUI = new JPanel(new GridLayout(16, 1));
		similarGUI.setSize(800, 300);
		similarGUI.setBackground(new Color(128, 1280, 128));
		
		JLabel disW = new JLabel("A similar Iris has attributes", SwingConstants.CENTER);
		disW.setForeground(Color.WHITE);
		disW.setBackground(new Color(128, 128, 128));
		similarGUI.add(disW);
		
		JPanel sim1 = new JPanel();
		JLabel lab1 = new JLabel("Sepal Length(cm):");
		get1 = new JTextField(5);
		lab1.setForeground(Color.WHITE);
		get1.setForeground(Color.WHITE);
	    get1.setBackground(new Color(128, 128, 128));
	    get1.setBorder(new LineBorder (new Color(0, 0, 0)));;
		sim1.setBackground(new Color(128, 1280, 128));
	    sim1.add(lab1);
		sim1.add(get1);
		get1.setEditable(false);
		similarGUI.add(sim1);
		
		JPanel sim2 = new JPanel();
		JLabel lab2 = new JLabel("Sepal Width(cm):");
		get2 = new JTextField(5);
		lab2.setForeground(Color.WHITE);
		get2.setForeground(Color.WHITE);
	    get2.setBackground(new Color(128, 128, 128));
	    get2.setBorder(new LineBorder (new Color(0, 0, 0)));;
		sim2.setBackground(new Color(128, 1280, 128));
	    sim2.add(lab2);
		sim2.add(get2);
		get2.setEditable(false);
		similarGUI.add(sim2);
		
		JPanel sim3 = new JPanel();
		JLabel lab3 = new JLabel("Species:");
		get3 = new JTextField(5);
		lab3.setForeground(Color.WHITE);
		get3.setForeground(Color.WHITE);
	    get3.setBackground(new Color(128, 128, 128));
	    get3.setBorder(new LineBorder (new Color(0, 0, 0)));;
		sim3.setBackground(new Color(128, 128, 128));
	    sim3.add(lab3);
		sim3.add(get3);
		get3.setEditable(false);
		similarGUI.add(sim3);
		
		JPanel sim4 = new JPanel();
		JLabel lab4 = new JLabel("Petal Length(cm):");
		get4 = new JTextField(5);
		lab4.setForeground(Color.WHITE);
		get4.setForeground(Color.WHITE);
	    get4.setBackground(new Color(128, 128, 128));
	    get4.setBorder(new LineBorder (new Color(0, 0, 0)));;
		sim4.setBackground(new Color(128, 128, 128));
	    sim4.add(lab4);
		sim4.add(get4);
		get4.setEditable(false);
		similarGUI.add(sim4);
		
		JPanel sim5 = new JPanel();
		JLabel lab5 = new JLabel("Petal Width(cm):");
		get5 = new JTextField(5);
		lab5.setForeground(Color.WHITE);
		get5.setForeground(Color.WHITE);
	    get5.setBackground(new Color(128, 128, 128));
	    get5.setBorder(new LineBorder (new Color(0, 0, 0)));;
		sim5.setBackground(new Color(128, 1280, 128));
	    sim5.add(lab5);
		sim5.add(get5);
		get5.setEditable(false);
		similarGUI.add(sim5);
		
		
		get13 = new JTextField(5);
		get13.setForeground(Color.WHITE);
	    get13.setBackground(new Color(128, 128, 128));
	    get13.setBorder(new LineBorder (new Color(0, 0, 0)));;
		get13.setEditable(false);
		get13.setHorizontalAlignment(JTextField.CENTER);
		get13.setText("apply");
	    similarGUI.add(get13);
	    
		JButton back = new JButton("Input new Iris");
		back.addActionListener(this);
		back.setForeground(Color.WHITE);
		back.setBackground(new Color(128, 128, 128));
		back.setBorder(new LineBorder (new Color(0, 0, 0)));
		
		similarGUI.add(new JLabel());
		similarGUI.add(back);
	}

	//GUI action for buttons
	public void actionPerformed(ActionEvent arg0) {
		JButton read = (JButton)arg0.getSource();
		String nameB = read.getText();//get buttons label
		
		if(nameB.equals("Test")) {//button from inputGUI to test Iris and display outputGUI
			input[0] = SepalLength.getText();
			input[1] = SepalWidth.getText();
			input[2] = PetalLength.getText();
			input[3] = PetalWidth.getText();
			
			try {
				
				if(input[0].equals(""))
					IrisIn.setSepalLength(d[0]);
				else
					IrisIn.setSepalLength(Float.parseFloat(input[1]));
				
				if(input[1].equals(""))
					IrisIn.setSepalWidth(d[1]);
				else
					IrisIn.setSepalWidth(Float.parseFloat(input[2]));
				
				if(input[2].equals(""))
					IrisIn.setPetalLength(d[2]);
				else
					IrisIn.setPetalLength(Float.parseFloat(input[3]));
				
				if(input[3].equals(""))
					IrisIn.setPetalWidth(d[3]);
				else
					IrisIn.setPetalWidth(Float.parseFloat(input[3]));
            }
				
			catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Enter values must be a number.\nEmpty inputs will apply default values.");
				return;
			}
			
			if(IrisIn.getSepalLength() < 0 || IrisIn.getSepalWidth() < 0 || IrisIn.getPetalLength() < 0 || IrisIn.getPetalWidth() < 0) {
				JOptionPane.showMessageDialog(null, "Negative values cannot be entered.\nEmpty inputs will apply default values.");
				return;
			}
			
			//if conversion to float not possible or negative value inputted show error message and do nothing
			//get classification and display result
			answer = rfIris.test(IrisIn);
			classify.setText(answer);
			popup.remove(inGUI);
			popup.add(outGUI);
			popup.revalidate();
			popup.setSize(699, 499);
		}
		else if(nameB.equals("Try another Iris")){//button on output GUI return to inputGUI and input new Iris
			popup.remove(outGUI);
			popup.add(inGUI);
			popup.revalidate();
			popup.setSize(700, 500);
		}
		else if(nameB.equals("Find similar Iris")) {//button on output GUI to find 1 similar Iris and display its values
			Iris simli = IrisClassifier.findIris(IrisIn);
			if(simli == null){//a similar Iris not found
				get1.setText("NA");
				get2.setText("NA");
				get3.setText("NA");
				get4.setText("NA");
				get5.setText("NA");
				get13.setText("A similar Iris was not foun in the data set");
			}
			else {//similar Iris is found
				get1.setText(Float.toString(simli.getSepalLength()));
				get2.setText(Float.toString(simli.getSepalWidth()));
				get3.setText(simli.getSpecies());
				get4.setText(Float.toString(simli.getPetalLength()));
				get5.setText(Float.toString(simli.getPetalWidth()));

				String giv = "";
				if(simli.getSpecies() == "setosa")
					giv = "setosa";
				else if(simli.getSpecies() == "versicolor")
					giv = "versicolor";
				else
					giv = "virginica";
					
				get13.setText("The Iris found in the data base has "+giv);
			}
			popup.remove(outGUI);
			popup.add(similarGUI);
			popup.revalidate();
			popup.setSize(400, 500);
			
		}
		else if(nameB.equals("Input new Iris")){//button on similar Iris GUI to return to input GUI
			popup.remove(similarGUI);
			popup.add(inGUI);
			popup.revalidate();
			popup.setSize(700, 500);
		}	
    }    	
}