package command;

import java.util.ArrayList;

import geometry.Shape;
import mvc.DrawingModel;
import observer.Buttons;

public class UnselectCmd implements Command {
	
	private DrawingModel model;
	private ArrayList<Shape> selectedShapes;
	private Buttons buttons;
	
	public UnselectCmd(ArrayList<Shape> selectedShapes, DrawingModel model, Buttons buttons) {
		this.model = model;
		this.selectedShapes = selectedShapes;
		this.buttons = buttons;
		
	}

	@Override
	public void execute() {
		for(int i = 0; i < selectedShapes.size(); i++) {
			selectedShapes.get(i).setSelected(false);
			model.removeSelected(selectedShapes.get(i));
		}
	}

	@Override
	public void unexecute() {
		for(int i = 0; i < selectedShapes.size(); i++) {
			model.addSelected(selectedShapes.get(i));
		}
	}
}
