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

	//Register Panel and compenents
	JPanel register_panel = new JPanel();
	JLabel register_text = new JLabel("Fill in form add a new User",JLabel.CENTER);

	//Labels
	JLabel firstName_label = new JLabel("First Name",JLabel.LEFT);
	JLabel lastName_label = new JLabel("Second Name",JLabel.LEFT);
	JLabel telephone_label = new JLabel("Telephone",JLabel.LEFT);

	//Fields
	JTextField firstName_field = new JTextField("Hello",30);
	JTextField lastName_field = new JTextField(30);
	JTextField telephone_field = new JTextField(10);
	JCheckBox male_checkbox = new JCheckBox("Male");
	JCheckBox female_checkbox = new JCheckBox("Female");

	JButton register_button = new JButton("Register User");
	DB database = new DB("demo","users");
	GridBagConstraints cons = new GridBagConstraints();
	JPanel login_panel = new JPanel();
	JPanel users_panel = new JPanel();
	JPanel dashboard = new JPanel();



	String[] user_features = {"First Name","Last Name", "Telephone Number","Gender"};
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new App();
         }
      });
    }
	public void newDashboard(){
		setDefaults(dashboard);
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridy = 0;
		cons.gridx = 0;
		// Chart title
		dashboard.add(new JLabel("Gender Balance Chart"),cons);
		// Chart postion
		
		// gender_ratio is the female to male ratio according to the database
		double gender_ratio = (double) database.CountRows("`gender`='1'")/database.CountRows("`gender`='0'");
		
		cons.gridy = 1;dashboard.add(new JLabel("Female to Male Ratio  1 : "+Double.toString(gender_ratio)));
		// checking if ratio is vaild
		cons.gridy = 2;
		cons.gridwidth = 200;
		cons.insets = new Insets(30,30,0,0);
		if(gender_ratio > 0){
			//showing chart
			Chart c = new Chart(gender_ratio);
			c.setPreferredSize(new Dimension(300,300));
			c.setBackground(Color.BLACK);
			dashboard.add(c,cons);
		}else{
			//in case gender_ratio is invalid a text to show error
			dashboard.add(new JLabel("Unable to show Chart"),cons);
		}
	}
	private void newRegisterForm(){
		setDefaults(register_panel);
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.VERTICAL;

		cons.gridy = 0;
		cons.gridx = 1;
		cons.insets = new Insets(0,0,5,10);
		register_panel.add(register_text,cons);
		register_panel.setPreferredSize(new Dimension(440,380));
		cons.gridx = 0;
		cons.gridy = 1;register_panel.add(firstName_label,cons);
		cons.gridx = 1;register_panel.add(firstName_field,cons);
		cons.gridx = 0;
		cons.gridy = 2;register_panel.add(lastName_label,cons);
		cons.gridx = 1;register_panel.add(lastName_field,cons);

		cons.gridy = 3;
		
		cons.gridx = 0;register_panel.add(telephone_label,cons);
		cons.gridx = 1;register_panel.add(telephone_field,cons);

		female_checkbox.setFont(new Font("Female", Font.PLAIN, 9));
		female_checkbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				male_checkbox.setSelected(false);
				female_checkbox.setSelected(true);
			}});

		male_checkbox.setFont(new Font("Serif", Font.PLAIN, 9));
		male_checkbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				female_checkbox.setSelected(false);
				male_checkbox.setSelected(true);
			}
		});
		cons.gridy = 4;
		cons.gridx = 0;register_panel.add(new JLabel("Gender"),cons);
		cons.gridx = 1;register_panel.add(female_checkbox,cons);
		cons.gridx = 2;register_panel.add(male_checkbox,cons);
		register_button.setFont(new Font("Serif", Font.PLAIN, 9));
		register_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Checking insersted parameters
				JOptionPane p = new JOptionPane();
				if (male_checkbox.isSelected() || female_checkbox.isSelected()){
					String gender = male_checkbox.isSelected()?"0":"1";
					String fname = firstName_field.getText();
					if ((fname.length() > 0)){
						String lname = lastName_field.getText();
						if (((lname.length() > 0))){
							String tele = telephone_field.getText();
							if ((tele.length() == 10)  && (tele.charAt(0) == '0')){
								for(int i=1;i<tele.length();i++){
									if (!Character.isDigit(tele.charAt(i))){
										p.showMessageDialog(null,"Telephone should not include letters");
										return;
									}
								}
								if (!database.CheckUser(fname,lname,tele,gender)){
									database.CreateUser(fname,lname,tele,gender);
									lastName_field.setText("");
									firstName_field.setText("");
									telephone_field.setText("");
									male_checkbox.setSelected(false);
									female_checkbox.setSelected(false);
									p.showMessageDialog(null,"New User Registered Successfully");
								}else{
									p.showMessageDialog(null,"Already User is Registered");
								}
							}else{
								p.showMessageDialog(null,"Telephone format is Wrong");
							}
						}else{
							p.showMessageDialog(null,"Last Name is empty");
						}
					}else{
						p.showMessageDialog(null,"First Name is empty");
					}
				}else{
					p.showMessageDialog(null,"Please Select the user's Gender");
				}
			}
		});

		cons.gridx = 1;
		cons.gridy = 6;register_panel.add(register_button,cons);
	}
	public void setDefaults(JPanel p){
		p.setLayout(new GridBagLayout());
		p.setBackground(Color.BLUE);
		p.setPreferredSize(new Dimension(440,380));
	}
	public void newUsersTab(){
		// View Users' table
		setDefaults(users_panel);
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.VERTICAL;
		cons.gridx = 1;cons.gridy = 0;
		JLabel users_text = new JLabel("View Registered Users");
		users_panel.add(users_text,cons);

		//Table of the register users
		cons.gridy = 3;
		try{
			users_panel.add(new JTable(database.GetUsers(),user_features),cons);
		}catch(Exception e){
			users_panel.add(new JLabel("No Users Found"),cons);
		}
		
	}
	public App(){
		
		setDefaults(login_panel);
		
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
		// Login fields  functionality
		password.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {password.setText("");}
		    public void focusLost(FocusEvent e) {}});
		username.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {username.selectAll();}
		    public void focusLost(FocusEvent e) {}});
	
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridy = 0;cons.gridx = 1;
		cons.insets = new Insets(60,0,0,0);cons.fill = GridBagConstraints.VERTICAL;
		login_text.setFont(new Font("Serif", Font.PLAIN, 29));
		login_panel.add(login_text,cons);
		//adding username 
		cons.insets = new Insets(20,0,0,0);
		cons.gridy = 1;login_panel.add(username,cons);
		cons.gridy = 2;login_panel.add(password,cons);

		cons.insets = new Insets(30,0,0,0);
		cons.gridy = 4;login_panel.add(login_button,cons);
		cons.gridy = 5;login_panel.add(exit_button,cons);

		panel.add(login_panel);
		// setting size of main_panel
		panel.setLayout(new CardLayout());
		panel.setPreferredSize(new Dimension(640,480));
		frame.add(panel);
		frame.setSize(640,480);
		frame.setVisible(true);
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

	JLabel login_text = new JLabel("Login to Countinue",JLabel.CENTER);
	//Fields
	JTextField username = new JTextField("UserName",30);
	JPasswordField password = new JPasswordField("Password",30);
	
	//LButtons
	JButton login_button = new JButton("Login");
	JButton exit_button = new JButton("Exit");
	
}
	
class Chart extends JComponent{
	double female_to_male;
	Color male_Color = Color.RED;
	Color female_Color = Color.PINK;
	Rectangle area;
	public Chart(double genderRatio){
		female_to_male = genderRatio;
	}
	public Chart(double genderRatio,Color femaleColor,Color maleColor){
		female_to_male = genderRatio;
		male_Color = maleColor;
		female_Color = femaleColor;
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		int female_size = (int) (360.0/(1.0+female_to_male));
		Arc2D arc = new Arc2D.Double(Arc2D.PIE);
		arc.setArc(0.0,0.0,100,100,0,female_size,Arc2D.PIE);
		g2.setColor(female_Color);
		g2.draw(arc);g2.fill(arc);
		Arc2D marc = new Arc2D.Double(Arc2D.PIE);
		marc.setArc(0.0,0.0,100,100,female_size,360,Arc2D.PIE);
		g2.setColor(male_Color);
      	g2.draw(marc);
	}
}