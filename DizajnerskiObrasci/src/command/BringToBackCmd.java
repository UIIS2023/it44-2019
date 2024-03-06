package command;

import mvc.DrawingController;

public class BringToBackCmd implements Command {
	
	DrawingController controller;
	private int i;
	
	public BringToBackCmd(DrawingController controller, int i) {
		this.controller = controller;
		this.i = i;
	}	

	@Override
	public void execute() {
		controller.bringToBack(2);
	}

	@Override
	public void unexecute() {
		for(int j = 0; j < i; j++) {
			controller.toFront(2);
		}
	}

}
