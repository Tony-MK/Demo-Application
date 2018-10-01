import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.Arc2D;

class RegisterPanel extends JPanel{
	GridBagConstraints cons = new GridBagConstraints();

	//Labels
	JLabel firstName_label = new JLabel("First Name",JLabel.LEFT);
	JLabel lastName_label = new JLabel("Second Name",JLabel.LEFT);
	JLabel telephone_label = new JLabel("Telephone",JLabel.LEFT);

	//Register Panel and compenents
	JPanel register_panel = new JPanel();
	JLabel register_text = new JLabel("Fill in form add a new User",JLabel.CENTER);

	//Fields
	JTextField firstName_field = new JTextField("Hello",30);
	JTextField lastName_field = new JTextField(30);
	JTextField telephone_field = new JTextField(10);
	JCheckBox male_checkbox = new JCheckBox("Male");
	JCheckBox female_checkbox = new JCheckBox("Female");

	JButton register_button = new JButton("Register User");

	public RegisterPanel(DB database){
		cons.fill = GridBagConstraints.VERTICAL;
		cons.insets = new Insets(0,0,5,10);

		cons.gridy = 0;cons.gridx = 1;
		add(register_text,cons);
		cons.gridx = 0;
		cons.gridy = 1;add(firstName_label,cons);
		cons.gridx = 1;add(firstName_field,cons);

		cons.gridx = 0;
		cons.gridy = 2;add(lastName_label,cons);
		cons.gridx = 1;add(lastName_field,cons);

		cons.gridy = 3;
		cons.gridx = 0;add(telephone_label,cons);
		cons.gridx = 1;add(telephone_field,cons);

		female_checkbox.setFont(new Font("Female", Font.PLAIN, 9));
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

		cons.gridy = 4;
		cons.gridx = 0;add(new JLabel("Gender"),cons);
		cons.gridx = 1;add(female_checkbox,cons);
		cons.gridx = 2;add(male_checkbox,cons);

		register_button.setFont(new Font("Serif", Font.PLAIN, 9));
		register_button.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
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
				}}});

		cons.gridx = 1;
		cons.gridy = 6;add(register_button,cons);
	}
}