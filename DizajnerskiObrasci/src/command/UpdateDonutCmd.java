package command;

import geometry.Donut;
import geometry.Point;

public class UpdateDonutCmd implements Command {
	
	private Donut oldState;
	private Donut newState;
	private Donut original = new Donut();
	
	
	
	public UpdateDonutCmd(Donut oldState, Donut newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone(); //prototype obrazac
		
		oldState.getCenter().setX(newState.getCenter().getX());
		oldState.getCenter().setY(newState.getCenter().getY());
		try {
			oldState.setRadius(newState.getRadius());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		oldState.setInnerRadius(newState.getInnerRadius());
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
		oldState.setInnerRadius(original.getInnerRadius());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
	}

}
