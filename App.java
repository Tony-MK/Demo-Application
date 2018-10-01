import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.Arc2D;


public class App{
	final boolean DEBUG = true;

	//Test Variables
	char[] correct_pass = {'W','k','i'};
	char[] correct_name = {'T','o','n','y'};

	JFrame frame = new JFrame("Login");
	JPanel panel = new JPanel();
	
	//Application Panel and compenents
	JFrame app_frame = new JFrame("Demo Application");
	JTabbedPane app_panel = new JTabbedPane(JTabbedPane.BOTTOM,0);


	DB database = new DB("demo","users");
	JPanel login_panel = new JPanel();
	JPanel register_panel = new JPanel();
	JPanel users_panel = new JPanel();
	JPanel dashboard = new JPanel();

	//Fields
	JTextField username = new JTextField("UserName",30);
	JPasswordField password = new JPasswordField("Password",30);
	
	//LButtons
	JButton login_button = new JButton("Login");
	JButton exit_button = new JButton("Exit");



	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new App();
         }
      });
    }
	public void newDashboard(){
		dashboard = new Dashboard(database);
		setDefaults(dashboard);
	}
	private void newRegisterForm(){
		register_panel = new RegisterPanel(database);
		setDefaults(register_panel);
	}
	public void newUsersTab(){
		// View Users' table
		users_panel = new UsersPanel(database);
		setDefaults(users_panel);
	}
	public void setDefaults(JPanel p){
		p.setLayout(new GridBagLayout());
		p.setBackground(Color.BLUE);
		p.setPreferredSize(new Dimension(440,380));
	}
	
	public App(){
		constructLoginPanel();
		addButtonListensers();

		// setting size of main_panel
		panel.add(login_panel);
		panel.setLayout(new CardLayout());
		panel.setPreferredSize(new Dimension(640,480));
		frame.add(panel);
		frame.setSize(640,480);
		frame.setVisible(true);
	}
	private void constructLoginPanel(){
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridy = 0;cons.gridx = 1;
		cons.insets = new Insets(60,0,0,0);cons.fill = GridBagConstraints.HORIZONTAL;
		JLabel login_text = new JLabel("Login to Countinue",JLabel.CENTER);
		login_text.setFont(new Font("Serif", Font.PLAIN, 29));
		login_panel.add(login_text,cons);
		//adding username 
		cons.insets = new Insets(20,0,0,0);
		cons.gridy = 1;login_panel.add(username,cons);
		cons.gridy = 2;login_panel.add(password,cons);

		cons.insets = new Insets(30,0,0,0);
		cons.gridy = 4;login_panel.add(login_button,cons);
		cons.gridy = 5;login_panel.add(exit_button,cons);
		setDefaults(login_panel);
	}
	private void addButtonListensers(){
		// adding login function
		login_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//checking if username and passoword is correct
				boolean auth = (checkInput(username.getText().toCharArray(),correct_name)) && (checkInput(password.getPassword(),correct_pass));
				if (auth){
					//Granting user acess

					
					panel.remove(login_panel);

					frame.setVisible(false);
					


					newDashboard();
					newUsersTab();
					newRegisterForm();

					// adding dashboard panel to application_panel
					app_panel.addTab("Dashboard",dashboard);

					// adding list of users panel to application_panel
					app_panel.addTab("View Users",users_panel);

					// adding register form panel to application_panel
					app_panel.addTab("Register User",register_panel);
					// giving login button a authorization fuctionality (action) 
					
					// setting size of application_panel
					app_panel.setPreferredSize(new Dimension(640,480));


					panel.add(app_panel);
					frame.setVisible(true);
				
				}else{
					JOptionPane p = new JOptionPane();
					p.showMessageDialog(null,"Wrong Password. Try Again");
				}}});
		// closing program when exit action is clicked e
		exit_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.dispose();
				System.exit(0);
			}});
		// giving some functionality for Login fields  
		password.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {password.setText("");}
		    public void focusLost(FocusEvent e) {}});
		username.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {username.selectAll();}
		    public void focusLost(FocusEvent e) {}});
	}
	public boolean checkInput(char[] input,char[] correct_input){
		if (!DEBUG){
			if (correct_input.length == input.length){
				for(int i=0;i<correct_input.length;i++){
					if (correct_input[i] != input[i]) {
						return false;
					}
				}
				return true;
			}
			return false;
		}
		return true;
	}	
}