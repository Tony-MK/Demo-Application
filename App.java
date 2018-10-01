import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class App{
	final boolean DEBUG = true;

	//Test Variables
	char[] correct_pass = {'W','k','i'};
	char[] correct_name = {'T','o','n','y'};

	JFrame frame = new JFrame("Login");
	JPanel panel = new JPanel();
	//Login Panel and compenents
	JPanel login_panel = new JPanel();
	JLabel login_text = new JLabel("Login to Countinue",JLabel.CENTER);
	//Fields
	JTextField name_field = new JTextField("UserName",30);
	JPasswordField pass_field = new JPasswordField("Password",30);
	
	//LButtons
	JButton login_button = new JButton("Login");
	JButton exit_button = new JButton("Exit");

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

	String[] user_features = {"First Name","Last Name", "Telephone Number","Gender"};
	public static void main(String[] args){

		new App();
		
	}
	
	private void constructLoginPanel(){
		login_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				boolean auth = (checkInput(name_field.getText().toCharArray(),correct_name)) && (checkInput(pass_field.getPassword(),correct_pass));
				if (auth){
					frame.setVisible(false);
					panel.remove(login_panel);
					panel.add(app_panel);
					frame.setVisible(true);
				}else{
					JOptionPane p = new JOptionPane();
					p.showMessageDialog(null,"Wrong Password. Try Again");
				}
			}
		});


		exit_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.dispose();System.exit(0);
			}

		});
		pass_field.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		    	pass_field.setText("");
 		    }
		    public void focusLost(FocusEvent e) {}
		});

		name_field.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        name_field.selectAll();
		    }

		    public void focusLost(FocusEvent e) {}
		});
		login_text.setFont(new Font("Serif", Font.PLAIN, 29));
		login_panel.setLayout(new GridBagLayout());
		login_panel.setBackground(Color.BLUE);
		
		cons.insets = new Insets(60,0,0,0);cons.fill = GridBagConstraints.VERTICAL;
		cons.gridy = 0;login_panel.add(login_text,cons);

		cons.insets = new Insets(20,0,0,0);
		cons.gridy = 1;login_panel.add(name_field,cons);
		cons.gridy = 2;login_panel.add(pass_field,cons);

		cons.insets = new Insets(20,0,0,0);cons.fill = GridBagConstraints.VERTICAL;
		cons.gridy = 4;login_panel.add(login_button,cons);
		cons.gridy = 5;login_panel.add(exit_button,cons);

		login_panel.setPreferredSize(new Dimension(440,380));
		panel.setLayout(new CardLayout());
		panel.add(login_panel);

	}

	public App(){
		register_panel.setLayout(new GridBagLayout());
		register_panel.setBackground(Color.BLUE);
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

		//male_checkbox.setPreferredSize(new Dimension(25,20));
		//female_checkbox.setPreferredSize(new Dimension(25,20));
		//female_checkbox.setBackground(Color.WHITE);
		female_checkbox.setFont(new Font("Female", Font.PLAIN, 9));
		female_checkbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				male_checkbox.setSelected(false);
				female_checkbox.setSelected(true);
			}
		});

		male_checkbox.setBackground(Color.WHITE);
		male_checkbox.setFont(new Font("Serif", Font.PLAIN, 9));
		male_checkbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				female_checkbox.setSelected(false);
				male_checkbox.setSelected(true);
			}
		});
		ButtonGroup group = new ButtonGroup();
		group.add(female_checkbox);
		group.add(male_checkbox);
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
								if (database.CheckUser(fname,lname,tele,gender)){
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


		// View Users' table
		JPanel users_planel = new JPanel();
		cons.gridx = 1;cons.gridy = 0;
		JLabel users_text = new JLabel("View Registered Users");
		users_planel.add(users_text,cons);

		//Table of the register users
		cons.gridx = 1;cons.gridy = 1;
		String[][] users = database.GetUsers();
		users_planel.add(new JTable(users,user_features),cons);


		
		app_panel.addTab("View Users",users_planel);
		app_panel.addTab("Register User",register_panel);
		app_panel.setPreferredSize(new Dimension(640,480));
		constructLoginPanel();

		panel.setPreferredSize(new Dimension(640,480));

		frame.add(panel);
		frame.setSize(640,480);
		frame.setVisible(true);
	}
	private boolean checkInput(char[] input,char[] correct_input){
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