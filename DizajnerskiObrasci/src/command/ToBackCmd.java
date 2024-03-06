package command;

import mvc.DrawingController;

public class ToBackCmd implements Command {
	
	DrawingController controller;
	
	public ToBackCmd(DrawingController controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		controller.toBack(2); // prosledjujemo false jer prililom undo i redo ne zelimo da nam se ispise log za ToBack
	}

	@Override
	public void unexecute() {
		controller.toFront(2);
	}

}
