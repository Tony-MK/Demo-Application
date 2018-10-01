import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.Arc2D;

class Dashboard extends JPanel{
	GridBagConstraints cons = new GridBagConstraints();
	public Dashboard(DB database,int width,int height){
		setLayout(new GridBagLayout());
		setBackground(Color.BLUE);
		setPreferredSize(new Dimension(width,height));

		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridy = 0;cons.gridx = 0;
		// Chart title
		String title = new JLabel("Gender Balance Chart",JLabel.CENTER);
		title.setFont(new Font("Serif",Font.Plain,9));
		add(title,cons);
		
		cons.gridy = 0;cons.gridx = 0;
		// gender_ratio is the female to male ratio according to the database
		double gender_ratio = (double) database.CountRows("`gender`='1'")/database.CountRows("`gender`='0'");
		String sub_title = new JLabel("Female to Male Ratio  1 : "+Double.toString(gender_ratio),JLabel.CENTER);
		sub_title.setFont(new Font("Serif",Font.Plain,9));
		cons.gridy = 1;add(sub_title);
		// checking if ratio is vaild
		cons.gridy = 2;
		cons.gridwidth = 200;
		cons.insets = new Insets(30,30,0,0);
		if(gender_ratio > 0){
			//showing chart
			Chart c = new Chart(gender_ratio);
			c.setPreferredSize(new Dimension(300,300));
			add(c,cons);
		}else{
			//in case gender_ratio is invalid a text to show error
			add(new JLabel("Unable to show Chart",JLabel.CENTER),cons);
		}
	}
}