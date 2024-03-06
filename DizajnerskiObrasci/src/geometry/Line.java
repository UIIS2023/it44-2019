package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape{
	
	private Point startPoint = new Point();
	private Point endPoint = new Point();

	public Line() {
		
	}
	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		setSelected(selected);
	}
	public Line(Point startPoint, Point endPoint, boolean selected, Color color) {
		this(startPoint, endPoint, selected);
		this.setColor(color);
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Line) {
			if(this.getStartPoint().getX() == ((Line) o).getStartPoint().getX())
				if(this.getStartPoint().getY() == ((Line) o).getStartPoint().getY())
					if(this.getEndPoint().getX() == ((Line) o).getEndPoint().getX())
						if(this.getEndPoint().getY() == ((Line) o).getEndPoint().getY())
						return 1;
		}
		return 0;
	}
	@Override
	public void moveBy(int byX, int byY) {
		this.startPoint.moveBy(byX, byY);
		this.endPoint.moveBy(byX, byY);
	}
	
	public Point middleOfLine() {
		int middleByX = (this.startPoint.getX() + this.endPoint.getX()) / 2;
		int middleByY = (this.startPoint.getY() + this.endPoint.getY()) / 2;
		Point middlePoint = new Point(middleByX, middleByY);
		return middlePoint;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(this.startPoint.getX(), this.startPoint.getY(), this.endPoint.getX(), this.endPoint.getY());
		//pamti boju koja je prethodno koriscena pa svaki put postavljamo opet tj. resetovanje boje
		
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.startPoint.getX()-3, this.startPoint.getY()-3, 6, 6);
			g.drawRect(this.endPoint.getX()-3, this.endPoint.getY()-3, 6, 6);
			g.drawRect(this.middleOfLine().getX()-3, this.middleOfLine().getY()-3, 6, 6);
		}	
	}
	public boolean contains(int x, int y) {
		if((startPoint.distance(x, y) + endPoint.distance(x, y)) - lenght() <= 0.05){
			return true;
		} else
			return false;
	}
	public double lenght() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Line) {
			Line temp = (Line) obj;
			if(this.startPoint.equals(temp.startPoint) && this.endPoint.equals(temp.endPoint)) 
				return true;
				else
					return false;
			
		} else
			return false;
	}
	public Point getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	public Point getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	@Override
	public String toString(){
		//(x, y) --> (x, y)
		return "Line:" + "StartPoint = " + startPoint + ", " + "EndPoint = " + endPoint;
	}
	
	public Line clone() {
		Line l = new Line();
		
		l.getStartPoint().setX(this.getStartPoint().getX()); 
		l.getStartPoint().setY(this.getStartPoint().getY()); 
		l.getEndPoint().setX(this.getEndPoint().getX()); 
		l.getEndPoint().setY(this.getEndPoint().getY()); 
		l.setColor(this.getColor()); //this uzima vrednost oldstate-a
		
		return l; //postavlja vrednost originala na l
	}
	
}