import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;

class Dashboard extends JPanel{
	public Dashboard(DB database,int width,int height){
		GridBagConstraints cons = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setBackground(Color.BLUE);
		setPreferredSize(new Dimension(width,height));
		cons.insets = new Insets(5,5,0,0);
		cons.fill = GridBagConstraints.VERTICAL;
		cons.gridy = 0;cons.gridx = 1;
		// Chart title
		JLabel title = new JLabel("Gender Chart",JLabel.CENTER);
		title.setFont(new Font("Times New Roman",Font.PLAIN,30));add(title,cons);
		

		// gender_ratio is the female to male ratio according to the database
		double gender_ratio = database.GetRatio();
		cons.gridy = 1;
		JLabel sub_title = new JLabel("Female to Male Ratio",JLabel.CENTER);
		sub_title.setFont(new Font("Serif",Font.PLAIN,15));add(sub_title);
		cons.gridy = 2;
		JLabel sub_title2 = new JLabel("1: "+Double.toString(gender_ratio),JLabel.CENTER);
		sub_title2.setFont(new Font("Serif",Font.PLAIN,11));add(sub_title2);
		// checking if ratio is vaild
		cons.gridy = 3;
		cons.insets = new Insets(30,30,0,0);
		if(gender_ratio > 0){
			//showing chart
			Chart c = new Chart(gender_ratio,300,300);
			add(c,cons);
		}else{
			//in case gender_ratio is invalid a text to show error
			add(new JLabel("Unable to show Chart",JLabel.CENTER),cons);
		}
	}
}

class Chart extends JComponent{
	double female_to_male;
	public Chart(double genderRatio,int width,int height){
		female_to_male = genderRatio;
		setPreferredSize(new Dimension(width,height));
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		int female_size = (int) (360.0/(1.0+female_to_male));

		Arc2D arc = new Arc2D.Double(Arc2D.PIE);
		arc.setArc(0.0,0.0,300,300,0,female_size,Arc2D.PIE);
		g2.setColor(Color.YELLOW);g2.draw(arc);g2.fill(arc);

		Arc2D marc = new Arc2D.Double(Arc2D.PIE);
		marc.setArc(0.0,0.0,300,300,female_size,360-female_size,Arc2D.PIE);
		g2.setColor(Color.GREEN);g2.draw(marc);g2.fill(marc);
	}
}