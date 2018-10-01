import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.Arc2D;

class LoginPanel extends JPanel{
	
	//LButtons
	public JButton login_button = new JButton("Login");
	public JButton exit_button = new JButton("Exit");

	//Fields
	JTextField username = new JTextField("UserName",30);
	JPasswordField password = new JPasswordField("Password",30);
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