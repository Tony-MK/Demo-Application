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

	//Labels
	JLabel name_label = new JLabel("UserName",JLabel.LEFT);
	JLabel pass_label = new JLabel("Password",JLabel.LEFT);

	//Fields
	JPasswordField pass_field = new JPasswordField(30);
	JTextField name_field = new JTextField(30);
	
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
	JTextField firstName_field = new JTextField(30);
	JTextField lastName_field = new JTextField(30);
	JTextField telephone_field = new JTextField(10);
	JButton male_button = new JButton("Male");
	JButton female_button = new JButton("Female");

	JButton register_button = new JButton("Register User");

	GridBagConstraints cons = new GridBagConstraints();

	
	private void constructLoginPanel(){
		login_text.setFont(new Font("Serif", Font.PLAIN, 19));
		login_panel.setLayout(new GridBagLayout());
		login_panel.setBackground(Color.BLUE);
		
		cons.fill = GridBagConstraints.VERTICAL;
		cons.insets = new Insets(0,0,30,0);
		name_label.setLabelFor(name_field);
		pass_label.setLabelFor(pass_field);

		cons.gridx = 1;
		cons.gridy = 0;
		login_panel.add(login_text,cons);


		cons.insets = new Insets(0,0,5,10);
		
		cons.gridy = 1;
		cons.gridx = 0;
		login_panel.add(name_label,cons);
		cons.gridx = 2;
		login_panel.add(name_field,cons);

		cons.gridx = 0;
		cons.gridy = 2;
		login_panel.add(pass_label,cons);
		cons.gridx = 2;
		login_panel.add(pass_field,cons);

		cons.insets = new Insets(5,0,5,0);

		cons.gridy = 4;
		login_panel.add(login_button,cons);
		cons.gridy = 5;
		login_panel.add(exit_button,cons);
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
		cons.gridwidth = 1;
		cons.gridx = 0;
		cons.gridy = 1;register_panel.add(firstName_label,cons);
		cons.gridx = 1;register_panel.add(firstName_field,cons);
		cons.gridx = 0;
		cons.gridy = 2;register_panel.add(lastName_label,cons);
		cons.gridx = 1;register_panel.add(lastName_field,cons);

		cons.gridy = 3;
		
		cons.gridx = 0;register_panel.add(telephone_label,cons);
		cons.gridx = 2;register_panel.add(telephone_field,cons);

		cons.gridy = 4;
		cons.gridwidth = 0;
		cons.gridx = 0;register_panel.add(new JLabel("Gender"),cons);
		cons.gridx = 1;register_panel.add(male_button,cons);
		cons.gridx = 2;register_panel.add(female_button,cons);

		male_button.setPreferredSize(new Dimension(25,20));
		female_button.setPreferredSize(new Dimension(25,20));
		female_button.setBackground(Color.WHITE);
		female_button.setFont(new Font("Female", Font.PLAIN, 9));
		female_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				female_button.setBackground(Color.GREEN);
				male_button.setBackground(Color.WHITE);
			}
		});

		male_button.setBackground(Color.WHITE);
		male_button.setFont(new Font("Serif", Font.PLAIN, 9));
		male_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				male_button.setBackground(Color.GREEN);
				female_button.setBackground(Color.WHITE);
			}
		});

		register_button.setBackground(Color.BLUE);
		register_button.setFont(new Font("Serif", Font.PLAIN, 9));
		register_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane p = new JOptionPane();
				p.showMessageDialog(null,"User resigristed");
			}
		});
		cons.gridx = 1;
		cons.gridy = 6;register_panel.add(register_button,cons);

		JPanel tables_planel = new JPanel();
		tables_planel.add(new JLabel("Registered users "));
		app_panel.addTab("View Users",tables_planel);
		app_panel.addTab("Register User",register_panel);
		app_panel.setPreferredSize(new Dimension(640,480));
		constructLoginPanel();

		panel.setPreferredSize(new Dimension(640,480));

		frame.add(panel);
		frame.setSize(640,480);
		frame.setVisible(true);
	}
	public static void main(String[] args){
		new App();
		
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