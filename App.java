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

	public JButton login_button = new JButton("Login");
	public JButton exit_button = new JButton("Exit");
	public JButton logout_button = new JButton("Exit");

	JTextField username = new JTextField("UserName",30);
	JPasswordField password = new JPasswordField("Password",30);

	JPanel login_panel;
	JPanel logout_panel;


	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new App();
         }
      });
    }
	public App(){
		addListensers();
		login_panel = new LoginPanel(640,480);
		logout_panel = new LogoutPanel(640,480);
		// setting size of main_panel
		panel.setLayout(new CardLayout());
		panel.setPreferredSize(new Dimension(640,480));
		panel.add(login_panel);
		frame.add(panel);
		frame.setSize(640,480);
		frame.setVisible(true);
	}
	private void addListensers(){
		// giving login button a authorization fuctionality (action) 
		logout_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//closing program
				panel.remove(app_panel);
				frame.setVisible(false);

				panel.add(login_panel);
				frame.setVisible(true);
				System.exit(0);
			}
		});

		login_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//checking if username and passoword is correct
				boolean auth = (checkInput(username.getText().toCharArray(),correct_name)) && (checkInput(password.getPassword(),correct_pass));
				if (auth){
					//Granting user acess
					panel.remove(login_panel);
					frame.setVisible(false);
					//building panels
					app_panel.addTab("Dashboard",new Dashboard(database,640,480));
					// adding list of users panel to application_panel
					app_panel.addTab("View Users", new UsersPanel(database,640,480));
					// adding register form panel to application_panel
					app_panel.addTab("Register User", new RegisterPanel(database,640,480));
					// adding logout Page to application panel;
					app_panel.addTab("Logout",logout_panel);
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
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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

	class LoginPanel extends JPanel{
	
	//LButtons
	

	//Fields
	public LoginPanel(int width,int height){
		setLayout(new GridBagLayout());
		setBackground(Color.BLUE);
		setPreferredSize(new Dimension(width,height));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridy = 0;cons.gridx = 1;
		cons.insets = new Insets(60,0,0,0);cons.fill = GridBagConstraints.HORIZONTAL;
		JLabel login_text = new JLabel("Login to Countinue",JLabel.CENTER);
		login_text.setFont(new Font("Serif", Font.PLAIN, 29));
		add(login_text,cons);
		//adding username 
		cons.insets = new Insets(20,0,0,0);
		cons.gridy = 1;add(username,cons);
		cons.gridy = 2;add(password,cons);

		cons.insets = new Insets(30,0,0,0);
		cons.gridy = 4;add(login_button,cons);
		cons.gridy = 5;add(exit_button,cons);
		}

	}

	class LogoutPanel extends JPanel{
		public LogoutPanel(int width,int height){
			setLayout(new GridBagLayout());
			setBackground(Color.BLUE);
			setPreferredSize(new Dimension(width,height));
			GridBagConstraints cons = new GridBagConstraints();
			cons.insets = new Insets(60,0,0,0);
			cons.fill = GridBagConstraints.HORIZONTAL;
			cons.gridy = 0;cons.gridx = 0;
			JLabel logout_text = new JLabel("Are you Sure you would like to end this session",JLabel.CENTER);
			add(logout_text,cons);

			cons.gridy = 1;cons.gridx = 0;
			JButton logout_button = new JButton("END SESSION");
			logout_button.setFont(new Font("Serif",Font.PLAIN,21));
			add(logout_button,cons);
		}
	}
}