package mvc;

import java.util.ArrayList;
import java.util.Stack;

import command.Command;
import geometry.Shape;

public class DrawingModel {
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>(); // lista oblika 
	private ArrayList<Shape> selected = new ArrayList<Shape>(); // lista selektovanih oblika
	
	public void add(Shape s) {
		shapes.add(s);
	}
	
	public void remove(Shape s) {
		shapes.remove(s);
	}
	
	public Shape get(int index) {
		return shapes.get(index);
	}
	
	public void addSelected(Shape shape) {
		selected.add(shape);
		shape.setSelected(true);
	}
	
	public void removeSelected(Shape shape) {
		selected.remove(shape);
	}
	
	public Shape getSelected(int index) {
		return selected.get(index);
	}
	
	public ArrayList<Shape> getSelected() {
		return selected;
	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}	
}
