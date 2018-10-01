import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.Arc2D;

class Dashboard extends JPanel{
	GridBagConstraints cons = new GridBagConstraints();

	public Dashboard(DB database){
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridy = 0;
		cons.gridx = 0;
		// Chart title
		add(new JLabel("Gender Balance Chart",JLabel.CENTER),cons);
		// Chart postion
		
		// gender_ratio is the female to male ratio according to the database
		double gender_ratio = (double) database.CountRows("`gender`='1'")/database.CountRows("`gender`='0'");
		
		cons.gridy = 1;add(new JLabel("Female to Male Ratio  1 : "+Double.toString(gender_ratio),JLabel.CENTER));
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