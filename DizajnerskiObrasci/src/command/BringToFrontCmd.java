package command;

import mvc.DrawingController;

public class BringToFrontCmd implements Command {
	
	DrawingController controller;
	private int i;
	private int size;
	
	public BringToFrontCmd(DrawingController controller, int i, int size) {
		this.controller = controller;
		this.i = i;
		this.size = size;
	}

	@Override
	public void execute() {
		controller.bringToFront(2);
	}

	@Override
	public void unexecute() {
		for(int j = size-1; j > i; j--) 
			controller.toBack(2);
	}

}
