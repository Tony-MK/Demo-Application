import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.Arc2D;
class RegisterPanel extends JPanel{
	GridBagConstraints cons = new GridBagConstraints();
	protected final int NAME_MAX_SIZE = 30;
	protected final int DOB_SIZE = 8;
	protected final int TELEPHONE_SIZE = 10;	
	//Labels
	JLabel firstName_label = new JLabel("First Name",JLabel.LEFT);
	JLabel lastName_label = new JLabel("Second Name",JLabel.LEFT);
	JLabel telephone_label = new JLabel("Telephone",JLabel.LEFT);
	JLabel dob_label = new JLabel("Date of Birth (ddmmyyyy)",JLabel.LEFT);

	//Register Panel and compenents
	JPanel register_panel = new JPanel();
	JLabel register_text = new JLabel("Fill in form add a new User",JLabel.CENTER);

	//Fields
	JTextField firstName_field = new JTextField(NAME_MAX_SIZE);
	JTextField lastName_field = new JTextField(NAME_MAX_SIZE);
	JTextField dob_field = new JTextField(DOB_SIZE);
	JTextField telephone_field = new JTextField(TELEPHONE_SIZE);
	JCheckBox male_checkbox = new JCheckBox("Male");
	JCheckBox female_checkbox = new JCheckBox("Female");
	JOptionPane p = new JOptionPane();
	JButton register_button = new JButton("Register User");
	// onlyDigits is a parameter to speficify if the Data should only be digits
	private boolean isValid(String data,int limit,String fieldName,boolean onlyDigits){
		int data_size = data.length();
		if((data_size>0)){
			if (data_size < limit){
				for(int i= 0;i<data_size;i++){
					boolean isDigit = Character.isDigit(data.charAt(i));
					if (!isDigit && onlyDigits){
						p.showMessageDialog(null,"FAILED: "+fieldName+" should not contain digits");
						return false;
					}else if (isDigit && !onlyDigits){
						p.showMessageDialog(null,"FAILED: "+fieldName+" should not contain letters");
						return false;
					}
				}
			return true;
			}else{
				if (onlyDigits)p.showMessageDialog(null,"FAILED: "+fieldName+" should have "+Integer.toString(limit)+" digits");
				else p.showMessageDialog(null,"FAILED: "+fieldName+" is greater than"+Integer.toString(limit));
				return false;
			}
		}else{
			p.showMessageDialog(null,"FAILED: "+fieldName+" is empty");
			return false;
		}
	}
	public RegisterPanel(DB database,int width,int height){
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(width,height));
		setBackground(Color.BLUE);
		cons.fill = GridBagConstraints.VERTICAL;
		cons.insets = new Insets(0,0,5,10);

		cons.gridy = 0;cons.gridx = 1;
		add(register_text,cons);
		cons.gridx = 0;
		cons.gridy = 1;add(firstName_label,cons);
		cons.gridy = 2;add(lastName_label,cons);
		cons.gridy = 3;add(telephone_label,cons);
		cons.gridy = 4;add(dob_label,cons);

		
		cons.gridx = 1;
		cons.gridy = 1;add(firstName_field,cons);
		cons.gridy = 2;add(lastName_field,cons);
		cons.gridy = 3;add(telephone_field,cons);
		cons.gridy = 4;add(dob_field,cons);

		female_checkbox.setFont(new Font("Serif", Font.PLAIN, 9));
		male_checkbox.setFont(new Font("Serif", Font.PLAIN, 9));

		female_checkbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				male_checkbox.setSelected(false);
				female_checkbox.setSelected(true);
			}});
		male_checkbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				female_checkbox.setSelected(false);
				male_checkbox.setSelected(true);
			}});

		cons.gridy = 5;
		cons.gridx = 0;add(new JLabel("Gender"),cons);
		cons.weightx = 0.5;
		cons.gridx = 1;add(female_checkbox,cons);
		cons.gridx = 2;add(male_checkbox,cons);

		register_button.setFont(new Font("Serif", Font.PLAIN, 9));
		register_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Checking passed parameters
				// checking if male or female is Selecte
				if (male_checkbox.isSelected() || female_checkbox.isSelected()){
					String fname = firstName_field.getText();String lname = lastName_field.getText();
					// checking first and last names have no numbers  and if it is too long

					if (isValid(fname,NAME_MAX_SIZE,"First Name",false) && isValid(lname,NAME_MAX_SIZE,"Last Name",false)){
						String tele = telephone_field.getText();String dob = dob_field.getText();
						// checking if telephone number is all digits and checking date entry
						if (isValid(tele,TELEPHONE_SIZE,"Telephone",true)  && isValid(dob,DOB_SIZE,"Date of Birth",true))
						//checking  telephone number starts with 07
						if ((tele.charAt(0) == '0') && (tele.charAt(1) == '7')){
							if (!database.CheckUser(fname,lname,tele,dob,female_checkbox.isSelected())){
									database.CreateUser(fname,lname,tele,dob,female_checkbox.isSelected());
									p.showMessageDialog(null,"SUCCESS: New User Registered");
									//reseting fields
									lastName_field.setText("");firstName_field.setText("");telephone_field.setText("");dob_field.setText("");
									male_checkbox.setSelected(false);female_checkbox.setSelected(false);
								}else{
									p.showMessageDialog(null,"SUCCESS: User already  is Registered");
								}
							}
						}
					}
					p.showMessageDialog(null,"FAILED: Choose the user's gender");
				}
			});

		cons.gridx = 1;
		cons.gridy = 6;add(register_button,cons);
	}
}