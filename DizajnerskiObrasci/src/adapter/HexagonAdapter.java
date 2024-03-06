package adapter;

import java.awt.Graphics;
import java.awt.Color;

import geometry.Point;
import geometry.Shape;
import geometry.SurfaceShape;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	
	private Hexagon hexagon = new Hexagon(0, 0, 0);

	public HexagonAdapter() {
		
	}
	
	public HexagonAdapter(Point p, int radius, boolean selected, Color c, Color innerC) {
		this.hexagon.setX(p.getX());
		this.hexagon.setY(p.getY());
		this.hexagon.setR(radius);
		this.hexagon.setSelected(selected);
		this.hexagon.setBorderColor(c);
		this.hexagon.setAreaColor(innerC);
	} 

	@Override
	public int compareTo(Object o) {
		if(o instanceof HexagonAdapter) {
			if(this.hexagon.getR() == ((HexagonAdapter) o).getHexagon().getR())
				if(this.hexagon.getX() == ((HexagonAdapter) o).getHexagon().getX())
					if(this.hexagon.getY() == ((HexagonAdapter) o).getHexagon().getY())
						return 1;
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
	}

	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public boolean isSelected() {
		return this.hexagon.isSelected();
	}
	
	public void setSelected(boolean selected) {
		this.hexagon.setSelected(selected);
	}
	
	public Color getColor() {
		return this.hexagon.getBorderColor();
	}
	
	public Color getInnerColor() {
		return this.hexagon.getAreaColor();
	}
	
	public String toString() {
		return "Hexagon:" + "Center=Point:" + hexagon.getX() + "," + hexagon.getY() + ",radius=" + hexagon.getR();
	}

	@Override
	public boolean contains(Point P) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fill(Graphics s) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		
	}
	
	public HexagonAdapter clone() {
		HexagonAdapter h = new HexagonAdapter();
		
		h.getHexagon().setX(this.getHexagon().getX());
		h.getHexagon().setY(this.getHexagon().getY());
		try {
			h.getHexagon().setR(this.getHexagon().getR());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		h.getHexagon().setBorderColor(this.getHexagon().getBorderColor());
		h.getHexagon().setAreaColor(this.getHexagon().getAreaColor()); //this uzima vrednost oldstate-a
		
		return h; //postavlja vrednost originala na h
	}

}
