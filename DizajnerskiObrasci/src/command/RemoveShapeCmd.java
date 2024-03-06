package command;

import geometry.Shape;
import mvc.DrawingModel;
import observer.Buttons;

public class RemoveShapeCmd implements Command {
	
	private Shape shape;
	private DrawingModel model;
	private Buttons buttons;
	private int i;
	
	public RemoveShapeCmd(Shape shape, DrawingModel model, Buttons buttons, int i) {
		this.shape = shape;
		this.model = model;
		this.buttons = buttons;
		this.i = i;
	}

	@Override
	public void execute() {
		model.remove(shape);
		model.removeSelected(shape);
		buttons.setCounter(model.getSelected().size());
	}

	@Override
	public void unexecute() {
		//model.add(shape);
		model.addSelected(shape);
		model.getShapes().add(i, shape);
		buttons.setCounter(model.getSelected().size());
	}

}
