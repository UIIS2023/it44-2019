package command;

import adapter.HexagonAdapter;
import geometry.Point;
import hexagon.Hexagon;
import java.awt.Color;

public class UpdateHexagonCmd implements Command {
	
	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original = new HexagonAdapter();
	
	
	
	public UpdateHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		
		oldState.getHexagon().setX(newState.getHexagon().getX());
		oldState.getHexagon().setY(newState.getHexagon().getY());
		oldState.getHexagon().setR(newState.getHexagon().getR());
		oldState.getHexagon().setBorderColor(newState.getHexagon().getBorderColor());
		oldState.getHexagon().setAreaColor(newState.getHexagon().getAreaColor());
	}

	@Override
	public void unexecute() {
		oldState.getHexagon().setX(original.getHexagon().getX());
		oldState.getHexagon().setY(original.getHexagon().getY());
		try {
			oldState.getHexagon().setR(original.getHexagon().getR());
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be a value greater than 0");
		}
		oldState.getHexagon().setBorderColor(original.getHexagon().getBorderColor());
		oldState.getHexagon().setAreaColor(original.getHexagon().getAreaColor());
		
	}

}
