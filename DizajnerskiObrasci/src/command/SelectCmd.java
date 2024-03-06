package command;
import geometry.Shape;
import mvc.DrawingModel;
import observer.Buttons;

public class SelectCmd implements Command {
	
	private DrawingModel model;
	private Shape shape;
	private Buttons buttons;
	
	public SelectCmd(Shape shape, DrawingModel model, Buttons buttons) {
		this.model = model;
		this.shape = shape;
		this.buttons = buttons;
	}

	@Override
	public void execute() {
		for(int i = 0; i < model.getShapes().size(); i++) {
			if(shape.compareTo(model.getShapes().get(i)) == 1) {
				model.getShapes().get(i).setSelected(true);
				model.addSelected(shape);
				buttons.setCounter(model.getSelected().size());
			}
		}
	}

	@Override
	public void unexecute() {
		model.removeSelected(shape);
		shape.setSelected(false);
		buttons.setCounter(model.getSelected().size());
	}

}
