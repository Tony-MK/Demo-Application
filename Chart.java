import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.Arc2D;

class Chart extends JComponent{
	double female_to_male;
	Color male_Color = Color.GREEN;
	Color female_Color = Color.PINK;
	Rectangle area;
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
		g2.setColor(female_Color);g2.draw(arc);g2.fill(arc);

		Arc2D marc = new Arc2D.Double(Arc2D.PIE);
		marc.setArc(0.0,0.0,300,300,female_size,360-female_size,Arc2D.PIE);
		g2.setColor(male_Color);g2.draw(marc);g2.fill(marc);
	}
}