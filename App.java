import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class App{
	char[] correctPassword = {'W','k','i'};	
	JLabel errText = new JLabel("");
	JLabel welText = new JLabel("Login to Countinue ");
	JLabel lname = new JLabel("UserName");
	JTextField name = new JTextField(30);
	JLabel lpass = new JLabel("Password");
	JPasswordField pass = new JPasswordField(30);
	public App(){
		showLoginWindow();
	}
	private boolean checkPassword(char[] password){
		if (correctPassword.length == password.length){
			for(int i=0;i<password.length;i++){
				if (correctPassword[i] != password[i]){
					return false;
				}
			}
		}
		return true;
	}
	private void showLoginWindow(){
		JWindow win = new JWindow();
		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.BLUE);
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.VERTICAL;
		cons.insets = new Insets(0,0,30,0);

		lname.setLabelFor(name);
		lpass.setLabelFor(pass);

		cons.gridx = 1;

		cons.gridy = 0;
		panel.add(welText,cons);


		cons.insets = new Insets(0,0,5,10);
		
		cons.gridy = 1;
		cons.gridx = 0;
		panel.add(lname,cons);
		cons.gridx = 2;
		panel.add(name,cons);

		cons.gridx = 0;
		cons.gridy = 2;
		panel.add(lpass,cons);
		cons.gridx = 2;
		panel.add(pass,cons);


		JButton loginButt = new JButton("Login");
		JButton exitButt = new JButton("Exit");

		loginButt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String nameT = name.getText();
				if (nameT == "Tony"){
					win.setVisible(false);
					showSuccessWindow();
				}else{
					errText.setText("Incorect Password"+name);
				}
			}
		});

		exitButt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				win.dispose();
			}
		});
		cons.gridy = 3;
		cons.gridx = 0;
		panel.add(errText,cons);
		cons.insets = new Insets(5,0,5,0);

		cons.gridy = 4;
		panel.add(loginButt,cons);
		cons.gridy = 5;
		panel.add(exitButt,cons);

		win.add(panel);
		win.setSize(640,480);
		win.setVisible(true);
	}
	private void showSuccessWindow(){
		JTabbedPane frame = new JTabbedPane(0,0);
		JPanel panel = new JPanel();
		panel.add(new JLabel("Access Granted"));
		frame.add(panel);
		frame.setSize(640,480);
		frame.setVisible(true);
	}
	public static void main(String[] args){
		new App();
	}
}