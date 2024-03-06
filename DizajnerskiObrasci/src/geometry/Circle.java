package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends SurfaceShape{
	
	private Point center = new Point();
	private int radius;
	
	public Circle() {
		
	}
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		setSelected(selected);
	}
	public Circle(Point center, int radius, boolean selected, Color color) {
		this(center, radius, selected);
		this.setColor(color);
	}
	public Circle(Point center, int radius, boolean selected, Color color, Color innerColor) {
		this(center, radius, selected, color);
		this.setInnerColor(innerColor);
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Circle) {
			if(this.getRadius() == ((Circle) o).getRadius())
				if(this.getCenter().getY() == ((Circle) o).getCenter().getY())
					if(this.getCenter().getX() == ((Circle) o).getCenter().getX())
						return 1;
		}
		return 0;
	}
	@Override
	public void moveBy(int byX, int byY) {
		this.center.moveBy(byX, byY);
	}
	
	public boolean contains(int x, int y) {
		return this.center.distance(x, y) <= this.radius;
	}
	public boolean contains(Point p) {
		return this.center.distance(p.getX(), p.getY()) <= this.radius;
	}
	@Override //overriduje metodu nadklase SurfaceShape
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillOval(this.center.getX()-this.radius+1, this.center.getY()-this.radius+1,
				this.radius*2-2, this.radius*2-2); //da se ne bi obojile i ivice
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(this.center.getX()-this.radius, this.center.getY()-this.radius, 
				this.radius*2, this.radius*2);
		//x i y su koordinate gore levo zamisljenog opisanog kvadrata oko kruga
		this.fill(g);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.center.getX()-3, this.center.getY()-3, 6, 6);
			g.drawRect(this.center.getX()-this.radius-3, this.center.getY()-3, 6, 6);
			g.drawRect(this.center.getX()+this.radius-3, this.center.getY()-3, 6, 6);
			g.drawRect(this.center.getX()-3, this.center.getY()-this.radius-3, 6, 6);
			g.drawRect(this.center.getX()-3, this.center.getY()+this.radius-3, 6, 6);
		}
	}
	public double area() {
		return radius * radius * Math.PI;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Circle) {
			Circle temp = (Circle) obj;
			if(this.center.equals(temp.center) && this.radius == temp.radius)
				return true;
			else
				return false;
		} else
			return false;
	}
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) throws Exception {
		if (radius > 0)
		{
			this.radius = radius;
		}
		else
		{
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}	
	}
	public String toString() {
		//Center = (x, y), radius = radius
		return "Circle:" + "Center=" + center + ",radius=" + radius;
	}
	
	public String donutToString() {
		return "Center=" + center + ",radius=" + radius;
	}
	
	public Circle clone() {
		Circle c = new Circle();
		
		c.getCenter().setX(this.getCenter().getX());
		c.getCenter().setY(this.getCenter().getY());
		try {
			c.setRadius(this.getRadius());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		c.setColor(this.getColor());
		c.setInnerColor(this.getInnerColor()); //this uzima vrednost oldstate-a
		
		return c; //postavlja vrednost originala na c
	}
}