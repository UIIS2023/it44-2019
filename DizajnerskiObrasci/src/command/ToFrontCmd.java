package command;

import geometry.Rectangle;
import mvc.DrawingController;
import mvc.DrawingModel;
import observer.Observable;
import observer.Observer;

public class ToFrontCmd implements Command {
	
	DrawingController controller;
	
	public ToFrontCmd(DrawingController controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		controller.toFront(2);
	}

	@Override
	public void unexecute() {
		controller.toBack(2);
	}
}
