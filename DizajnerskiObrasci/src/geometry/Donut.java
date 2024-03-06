package geometry;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.UIManager;

public class Donut extends Circle {

	private int innerRadius;
	
	public Donut() {
		
	} 
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius); 
		setSelected(selected);
	}
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected); 
		setColor(color);
	}
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color); 
		setInnerColor(innerColor);
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Donut) {
			if(this.getRadius() == ((Donut) o).getRadius())
				if(this.getInnerRadius() == ((Donut) o).getInnerRadius())
					if(this.getCenter().getY() == ((Donut) o).getCenter().getY())
						if(this.getCenter().getX() == ((Donut) o).getCenter().getX())
							return 1;
		}
		return 0;
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor()); //postavljamo boju unutrasnjosti krofne
		
		Ellipse2D.Double oval1 = new Ellipse2D.Double(); //objekat veceg kruga
		Ellipse2D.Double oval2 = new Ellipse2D.Double();  //objekat manjeg kruga
		oval1.setFrame(this.getCenter().getX()-this.getRadius(), this.getCenter().getY()-this.getRadius(), this.getRadius()*2, this.getRadius()*2);
		//postavljanje x i y vrednosti gornje leve tacke veceg kruga, width, height
		oval2.setFrame(this.getCenter().getX()-this.getInnerRadius(), this.getCenter().getY()-this.getInnerRadius(), this.getInnerRadius()*2, this.getInnerRadius()*2);
		//postavljanje x i y vrednosti gornje leve tacke manjeg kruga, width, height
		
		Area area1 = new Area(oval1);
		Area area2 = new Area(oval2);
		area1.subtract(area2); //oduzimamo manji krug(area2) od veceg(area1)
		//kreiramo 2 objekta klase Area i prosledjujemo konstruktoru oval1 i oval2 (tipa Shape), 
		//da bismo mogli da oduzmemo ove dve povrsine i kasnije ih iscrtamo
		((Graphics2D) g).fill(area1); //iscrtavanje i bojenje dobijene povrsine
	}
	@Override
	public void draw(Graphics g) {
		super.draw(g); 
		g.setColor(getColor()); //postavljamo boju spoljasnje ivice
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI; 
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Donut) {
			Donut temp = (Donut) obj;
			if(this.getCenter().equals(temp.getCenter()) && this.getRadius() == temp.getRadius() && this.innerRadius == temp.innerRadius)
				//super.equals(temp)
				return true;
			else
				return false;
		} else
			return false;
	}
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	public int getInnerRadius() {
		return innerRadius;
	}
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}
	@Override
	public String toString() {
		return "Donut:" + super.donutToString() + ",innerRadius=" + innerRadius;
	}
	
	public Donut clone() {
		Donut d = new Donut();
		
		d.getCenter().setX(this.getCenter().getX());
		d.getCenter().setY(this.getCenter().getY());
		try {
			d.setRadius(this.getRadius());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		d.setInnerRadius(this.getInnerRadius());
		d.setColor(this.getColor());
		d.setInnerColor(this.getInnerColor()); //this uzima vrednost oldstate-a
		
		return d; //postavlja vrednost originala na d
	}
}
