package geometry;

public interface Moveable {

	//svaka metoda u interface-u je abstraktna
	
	public abstract void moveBy(int byX, int byY);
	//void moveBy(int byX, int byY);
	//ne mozemo da napravimo objekat ove klase
	//ova klasa se ne prosiruje nego implemenitira
	//jedna klasa moze da implemebnira bezbroj interface-a
	
}
