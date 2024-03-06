package command;

import geometry.Circle;
import geometry.Point;

public class UpdateCircleCmd implements Command {
	
	private Circle oldState;
	private Circle newState;
	private Circle original = new Circle(); //koristi se za undo kako ne bismo izgubili vrednosti prvobitnog kruga
	
	public UpdateCircleCmd(Circle oldState, Circle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		
		oldState.getCenter().setX(newState.getCenter().getX());
		oldState.getCenter().setY(newState.getCenter().getY());
		try {
			oldState.setRadius(newState.getRadius());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
	}

	@Override
	public void unexecute() {
		oldState.getCenter().setX(original.getCenter().getX());
		oldState.getCenter().setY(original.getCenter().getY());
		try {
			oldState.setRadius(original.getRadius());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());		
	}

}
