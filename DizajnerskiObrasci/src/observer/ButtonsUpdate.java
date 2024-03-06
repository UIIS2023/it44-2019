package observer;

import mvc.DrawingFrame;

public class ButtonsUpdate implements Observer {

	//observer osluskuje promene na observable
	
	private int counter;
	DrawingFrame drawingFrame;
	
	public ButtonsUpdate(DrawingFrame drawingFrame) {
		this.drawingFrame = drawingFrame;
	}
	
	@Override
	public void update(int counter) {
		this.counter = counter;
		if(counter == 0) 
			disable();
		else if(counter == 1)
			enable();
		else
			enableDisable();
	}
	
	public void enable() {
		drawingFrame.getTglbtnModify().setEnabled(true);
		drawingFrame.getTglbtnDelete().setEnabled(true);
		
		drawingFrame.getBtnBringToBack().setEnabled(true);
		drawingFrame.getBtnBringToFront().setEnabled(true);
		drawingFrame.getBtnToBack().setEnabled(true);
		drawingFrame.getBtnToFront().setEnabled(true);
	}
	
	public void disable() {
		drawingFrame.getTglbtnModify().setEnabled(false);
		drawingFrame.getTglbtnDelete().setEnabled(false);
		
		drawingFrame.getBtnBringToBack().setEnabled(false);
		drawingFrame.getBtnBringToFront().setEnabled(false);
		drawingFrame.getBtnToBack().setEnabled(false);
		drawingFrame.getBtnToFront().setEnabled(false);
	}

	
	public void enableDisable() {
		drawingFrame.getTglbtnModify().setEnabled(false);
		drawingFrame.getTglbtnDelete().setEnabled(true);
		
		drawingFrame.getBtnBringToBack().setEnabled(false);
		drawingFrame.getBtnBringToFront().setEnabled(false);
		drawingFrame.getBtnToBack().setEnabled(false);
		drawingFrame.getBtnToFront().setEnabled(false);
	}

}
