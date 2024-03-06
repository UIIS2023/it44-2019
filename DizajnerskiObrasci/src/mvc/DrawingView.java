package mvc;

import javax.swing.JPanel;
import geometry.Shape;
import java.awt.Graphics;
import java.util.Iterator;

public class DrawingView extends JPanel{
	
	//pravimo novi objekat modela da model ne bi bio null, iako smo napravili model u klasi DrawingApp
	private DrawingModel model = new DrawingModel();
	
	public void setModel (DrawingModel model) {
		this.model = model;
	}
	
	public void paint (Graphics g) {
		super.paint(g);
		Iterator<Shape> it = model.getShapes().iterator();
		while(it.hasNext())
			it.next().draw(g);
	}
	
}
