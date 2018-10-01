import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.Arc2D;


class UsersPanel extends JPanel{

	String[] user_features = {"First Name","Last Name", "Telephone Number","Gender"};

	public UsersPanel(DB database,int width,int height){
		setPreferredSize(new Dimension(width,height));
		setBackground(Color.BLUE);
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.VERTICAL;
		cons.gridx = 1;cons.gridy = 0;
		cons.insets = new Insets(20,0,0,0);
		add(new JLabel("View Registered Users",JLabel.CENTER),cons);

		//Table of the register users
		cons.gridy = 3;
		try{
			add(new JTable(database.GetUsers(),user_features),cons);
		}catch(Exception e){
			add(new JLabel("No Users Found",JLabel.CENTER),cons);
		}
	}
}