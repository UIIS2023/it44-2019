package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.RemoveShapeCmd;
import command.SelectCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UnselectCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRect;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.SurfaceShape;
import hexagon.Hexagon;
import observer.Buttons;
import observer.ButtonsUpdate;
import strategy.DrawSaving;
import strategy.LogSaving;
import strategy.SavingManager;

public class DrawingController {
	
	private DrawingModel model;
	private DrawingFrame frame;
	int check;
	Point startPoint;
	
	//observable i observer
	private Buttons buttons;
	private ButtonsUpdate buttonsUpdate;
	
	//Strategy (log)
	private LogSaving logSaving;
	private DrawSaving drawSaving;
	private SavingManager savingManager;
	private SavingManager savingManager2;
	private FileInputStream fileInputStream;
	private JFileChooser fileChooser;
	
	//Undo i redo stack
	private Stack<Command> commands = new Stack<Command>(); //stek komandi
	private Stack<Command> temp = new Stack<Command>(); //pomocni stek komandi (za undo i redo f-ju)
	private Stack<String> logStack = new Stack<String>(); // stek logova
	
	private ArrayList<Shape> tempSelected = new ArrayList<Shape>();
	
	private int i = 0;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		buttons = new Buttons();
		buttonsUpdate = new ButtonsUpdate(frame);
		buttons.addObserver(buttonsUpdate);
	}
	
	public void check(int c) {
		check = c;
		frame.getBtnGroup().clearSelection();
		frame.getBtnGroup2().clearSelection();
	}
	
	public void mouseClicked(MouseEvent e) {
		if (check == 6)
			this.selection(frame.getView().getGraphics(), e.getX(), e.getY());
		else
			this.paint(frame.getView().getGraphics(), e.getX(), e.getY());
	}
	
	//PAINT FUNCTION
	public void paint(Graphics g, int x, int y) {
		Shape s = null;
		Color c = frame.getBtnExterior().getBackground();
		Color innerC = frame.getBtnInterior().getBackground();
		if (check == 1)
		{
			s = new Point(x, y, false, frame.getBtnExterior().getBackground());
			
		} else if (check == 2) {
			if (startPoint == null)
			{
				startPoint = new Point(x, y);
			}
			else
			{
				Point eP = new Point(x, y);
				s = new Line(startPoint, eP);
				s.setColor(frame.getBtnExterior().getBackground());
				startPoint = null;
			}

		} else if (check == 3) {
			DlgRect dlgR = new DlgRect();			
			dlgR.getTxtX().setText(String.valueOf(x));
			dlgR.getTxtY().setText(String.valueOf(y));
			dlgR.getTxtX().setEditable(false);
			dlgR.getTxtY().setEditable(false);
			dlgR.getBtnColor().setVisible(false);
			dlgR.getBtnInnerColor().setVisible(false);
			dlgR.setVisible(true);
			if (dlgR.isOk()) {
				try {
					String width = dlgR.getTxtWidth().getText().toString();
					int intWidth = Integer.parseInt(width);
					String height = dlgR.getTxtHeight().getText().toString();
					int intHeight = Integer.parseInt(height);	
					s = new Rectangle(new Point(x, y), intWidth, intHeight, false, c, innerC);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, "You need to enter numbers!");
				}
						
			}	
			
		} else if (check == 4) {
			DlgCircle dlgC = new DlgCircle();
			dlgC.getTxtXc().setText(String.valueOf(x));
			dlgC.getTxtYc().setText(String.valueOf(y));
			dlgC.getTxtXc().setEditable(false);
			dlgC.getTxtYc().setEditable(false);
			dlgC.getBtnColor().setVisible(false);
			dlgC.getBtnInnerColor().setVisible(false);
			dlgC.setVisible(true);
			
			if (dlgC.isOk()) {
				String radius = dlgC.getTxtR().getText().toString();
				int intRadius = Integer.parseInt(radius);	
				s = new Circle(new Point(x, y), intRadius, false, c, innerC);
			}
		} else if (check == 5) {
			DlgDonut dlgD = new DlgDonut();
			dlgD.getTxtXc().setText(String.valueOf(x));
			dlgD.getTxtYc().setText(String.valueOf(y));
			dlgD.getTxtXc().setEditable(false);
			dlgD.getTxtYc().setEditable(false);
			dlgD.getBtnColor().setVisible(false);
			dlgD.getBtnInnerColor().setVisible(false);
			dlgD.setVisible(true);
			
			if (dlgD.isOk()) {
				s = new Donut(new Point(x, y), Integer.parseInt(dlgD.getTxtR().getText().toString()), Integer.parseInt(dlgD.getTxtIR().getText().toString()), false, c, innerC);
			}
		} else if(check == 6) {
			this.selection(frame.getView().getGraphics(), x, y);;
		} else if(check == 7) {
			DlgHexagon dlgH = new DlgHexagon();
			dlgH.getTxtX().setText(String.valueOf(x));
			dlgH.getTxtY().setText(String.valueOf(y));
			dlgH.getTxtX().setEditable(false);
			dlgH.getTxtY().setEditable(false);
			dlgH.getBtnColor().setVisible(false);
			dlgH.getBtnInnerColor().setVisible(false);
			dlgH.setVisible(true); 
			if (dlgH.isOk()) {
				s = new HexagonAdapter(new Point(Integer.parseInt(dlgH.getTxtX().getText().toString()), 
						Integer.parseInt(dlgH.getTxtY().getText().toString())), 
						Integer.parseInt(dlgH.getTxtR().getText().toString()), false, c, innerC);
			}
		}
		if (s != null) {
			model.add(s);
			AddShapeCmd aSC = new AddShapeCmd(s, model);
			commands.push(aSC);
			frame.repaint();
		}
		frame.getBtnRedo().setEnabled(false); 
		if (!(s instanceof SurfaceShape) && s != null) {
			logStack.push(s.toString() + "," + s.getColor());  //upisivanje loga na stack
			frame.getTextArea().append(s.toString() + "," + s.getColor() + "\n"); //ispisivanje loga na textarea
		} else if (s instanceof SurfaceShape && s != null) {
			logStack.push(s.toString() + ", " + c + ", " + innerC);
			frame.getTextArea().append(s.toString() + ", " + c + ", " + innerC + "\n");
		} 
		temp.clear();
	}
	
	//DELETE FUNCTION
	public void delete() {	
		if (model.getShapes().isEmpty()) //ako nemamo dodatih oblika
			JOptionPane.showMessageDialog(null, "You need to add shape first!");
		else if (model.getSelected() == null) //ako nijedan oblik nije selektovan
			JOptionPane.showMessageDialog(null, "You need to select a shape!");
		else
		{
			int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure?",
					"Delete shape", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (confirmation == 0) {
				for (int i = 0; i < model.getShapes().size(); i++) { //prolazimo kroz listu oblika      
					if (model.getShapes().get(i).isSelected()) { //trazimo oblik koji je selektovan
						if(model.getShapes().get(i) instanceof Point) {
							logStack.push("Point deleted:" + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor());
							frame.getTextArea().append("Point deleted:" + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor() + "\n");
						} else if(model.getShapes().get(i) instanceof Line) {
							logStack.push("Line deleted: " + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor());
							frame.getTextArea().append("Line deleted: " + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor() + "\n");
						} else if(model.getShapes().get(i) instanceof Rectangle) {
							logStack.push("Rectangle deleted: " + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor() + ", " + ((Rectangle) model.getShapes().get(i)).getInnerColor());
							frame.getTextArea().append("Rectangle deleted: " + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor() + ((Rectangle) model.getShapes().get(i)).getInnerColor() + "\n");
						} else if(model.getShapes().get(i) instanceof Donut) {
							logStack.push("Donut deleted: " + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor() + ", " + ((Donut) model.getShapes().get(i)).getInnerColor());
							frame.getTextArea().append("Donut deleted: " + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor() + ", " + ((Donut) model.getShapes().get(i)).getInnerColor() + "\n");
						} else if(model.getShapes().get(i) instanceof Circle) {
							logStack.push("Circle deleted: " + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor() + ", " + ((Circle) model.getShapes().get(i)).getInnerColor());
							frame.getTextArea().append("Circle deleted: " + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor() + ", " + ((Circle) model.getShapes().get(i)).getInnerColor() + "\n");
						}  else if(model.getShapes().get(i) instanceof HexagonAdapter) {
							logStack.push("Hexagon deleted: " + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor() + ", " + ((HexagonAdapter) model.getShapes().get(i)).getInnerColor());
							frame.getTextArea().append("Hexagon deleted: " + model.getShapes().get(i).toString() + ", " + model.getShapes().get(i).getColor() + ", " + ((HexagonAdapter) model.getShapes().get(i)).getInnerColor() + "\n");
						} 
						RemoveShapeCmd rSC = new RemoveShapeCmd(model.getShapes().get(i), model, buttons, i); //komanda za brisanje oblika
						commands.push(rSC); // pushovanje komande na stek komandi kako bismo mogli da uradimo undo
					}	
			    }
				int size = model.getSelected().size(); //prebrojavamo broj selektovanih oblika
				for (int i = size - 1; i >= 0; i--) {
					Shape s = model.getSelected().get(i);
					model.getShapes().remove(s);   //brisemo selektovani oblik iz liste oblika
					model.getSelected().remove(s); //brisemo selektovani oblik iz liste selektovanih oblika
				}
				frame.repaint();
				buttons.setCounter(model.getSelected().size()); //observable buttons poziva setCounter i obavestava observere o broju selektovanih oblika
				frame.getBtnRedo().setEnabled(false); //Redo jedino moze da se koristi kada uradimo prvo undo, zato ga postavljamo na false 
			}
		}
		frame.getBtnGroup().clearSelection();
		frame.getBtnGroup2().clearSelection();
		temp.clear();
	}

	//MODIFY FUNCTION
	public void modify() {		
		if (model.getShapes().isEmpty()) // ako nemamo nijedan iscrtan oblik
			JOptionPane.showMessageDialog(null, "You need to add shape first!");
		else if (model.getSelected() == null) //ako nijedan oblik nije selektovan
			JOptionPane.showMessageDialog(null, "You need to select a shape!");
		else
		{
			for (int i = 0; i < model.getShapes().size(); i++) { //prolazimo kroz listu oblika		      
				if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof Circle && (model.getShapes().get(i) instanceof Donut == false)) {
					DlgCircle dlg = new DlgCircle();
					Circle temp = (Circle) model.getShapes().get(i);		
					dlg.getTxtXc().setText(String.valueOf(temp.getCenter().getX()));
					dlg.getTxtYc().setText(String.valueOf(temp.getCenter().getY()));
					dlg.getTxtR().setText(String.valueOf(temp.getRadius()));
					dlg.getBtnColor().setBackground(temp.getColor());
					dlg.getBtnInnerColor().setBackground(temp.getInnerColor());
					dlg.setTitle("Modify circle");
					dlg.setVisible(true);
					if (dlg.isOk()) {
						Circle c = new Circle(new Point(Integer.parseInt(dlg.getTxtXc().getText().toString()), Integer.parseInt(dlg.getTxtYc().getText().toString())), Integer.parseInt(dlg.getTxtR().getText().toString()), false, dlg.getBtnColor().getBackground(), dlg.getBtnInnerColor().getBackground());
						UpdateCircleCmd uCC = new UpdateCircleCmd(temp, c);
						commands.push(uCC);
						logStack.push("Circle modified:" + temp.toString() + "," + temp.getColor() + "," + temp.getInnerColor()
						+ " to " + c.toString() + "," + c.getColor() + "," + c.getInnerColor());
						frame.getTextArea().append("Circle modified:" + temp.toString() + "," + temp.getColor() + "," + temp.getInnerColor()
						+ " to " + c.toString() + "," + c.getColor() + "," + c.getInnerColor() + " \n");
						uCC.execute();
					}
				} else if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof Donut) {				
					DlgDonut dlg = new DlgDonut();
					Donut temp = (Donut) model.getShapes().get(i);		
					dlg.getTxtXc().setText(String.valueOf(temp.getCenter().getX()));
					dlg.getTxtYc().setText(String.valueOf(temp.getCenter().getY()));
					dlg.getTxtR().setText(String.valueOf(temp.getRadius()));
					dlg.getTxtIR().setText(String.valueOf(temp.getInnerRadius()));
					dlg.getBtnColor().setBackground(temp.getColor());
					dlg.getBtnInnerColor().setBackground(temp.getInnerColor());
					dlg.setTitle("Modify donut");
					dlg.setVisible(true);
					if (dlg.isOk()) {
						Donut d = new Donut(new Point(Integer.parseInt(dlg.getTxtXc().getText().toString()), Integer.parseInt(dlg.getTxtYc().getText().toString())), Integer.parseInt(dlg.getTxtR().getText().toString()), Integer.parseInt(dlg.getTxtIR().getText().toString()), false, dlg.getBtnColor().getBackground(), dlg.getBtnInnerColor().getBackground());
						UpdateDonutCmd uDC = new UpdateDonutCmd(temp, d);
						commands.push(uDC);
						logStack.push("Donut modified:" + temp.toString() + "," + temp.getColor() + "," + temp.getInnerColor()
						+ " to " + d.toString() + "," + d.getColor() + "," + d.getInnerColor());
						frame.getTextArea().append("Donut modified:" + temp.toString() + "," + temp.getColor() + "," + temp.getInnerColor()
						+ " to " + d.toString() + "," + d.getColor() + "," + d.getInnerColor() + " \n");
						uDC.execute();
					}
				} else if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof Rectangle) {
					DlgRect dlg = new DlgRect();			
					Rectangle temp = (Rectangle) model.getShapes().get(i);			
					dlg.getTxtY().setText(String.valueOf(temp.getUpperLeftPoint().getY()));
					dlg.getTxtX().setText(String.valueOf(temp.getUpperLeftPoint().getX()));
					dlg.getTxtWidth().setText(String.valueOf(temp.getWidth()));
					dlg.getTxtHeight().setText(String.valueOf(temp.getHeight()));
					dlg.getBtnColor().setBackground(temp.getColor());
					dlg.getBtnInnerColor().setBackground(temp.getInnerColor());
					dlg.setTitle("Modify rectangle");
					dlg.setVisible(true);
					if (dlg.isOk()) {
						Rectangle r = new Rectangle(new Point(Integer.parseInt(dlg.getTxtX().getText().toString()), Integer.parseInt(dlg.getTxtY().getText().toString())), Integer.parseInt(dlg.getTxtWidth().getText().toString()), Integer.parseInt(dlg.getTxtHeight().getText().toString()), false, dlg.getBtnColor().getBackground(), dlg.getBtnInnerColor().getBackground());
						UpdateRectangleCmd uRC = new UpdateRectangleCmd(temp, r);
						commands.push(uRC);
						logStack.push("Rectangle modified:" + temp.toString() + "," + temp.getColor() + "," + temp.getInnerColor()
						+ " to " + r.toString() + "," + r.getColor() + "," + r.getInnerColor());
						frame.getTextArea().append("Rectangle modified:" + temp.toString() + "," + temp.getColor() + "," + temp.getInnerColor()
						+ " to " + r.toString() + "," + r.getColor() + "," + r.getInnerColor() + " \n");
						uRC.execute();
					}
				} else if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof Point) {
					DlgPoint dlg = new DlgPoint();
					Point temp = (Point) model.getShapes().get(i);		
					dlg.getTxtX().setText(String.valueOf(temp.getX()));
					dlg.getTxtY().setText(String.valueOf(temp.getY()));
					dlg.getBtnColor().setBackground(temp.getColor());
					dlg.setTitle("Modify point");
					dlg.setVisible(true);
					if (dlg.isOk()) {
						Point p3 = new Point(Integer.parseInt(dlg.getTxtX().getText().toString()), Integer.parseInt(dlg.getTxtY().getText().toString()), false, dlg.getBtnColor().getBackground());
						UpdatePointCmd uPC = new UpdatePointCmd(temp, p3);
						commands.push(uPC);
						logStack.push("Point modified:" + temp.toString() + "," + temp.getColor() 
						+ "to " + p3.toString() + "," + p3.getColor());
						frame.getTextArea().append("Point modified:" + temp.toString() + "," + temp.getColor() 
						+ "to " + p3.toString() + "," + p3.getColor() + " \n");
						uPC.execute();
					}
				} else if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof Line) {
					DlgLine dlg = new DlgLine();
					Line temp = (Line) model.getShapes().get(i);		
					dlg.getTxtX1().setText(String.valueOf(temp.getStartPoint().getX()));
					dlg.getTxtX2().setText(String.valueOf(temp.getEndPoint().getX()));
					dlg.getTxtY1().setText(String.valueOf(temp.getStartPoint().getY()));
					dlg.getTxtY2().setText(String.valueOf(temp.getEndPoint().getY()));
					dlg.getBtnColor().setBackground(temp.getColor());
					dlg.setTitle("Modify line");
					dlg.setVisible(true);
					if (dlg.isOk()) {
						Line l = new Line(new Point(Integer.parseInt(dlg.getTxtX1().getText().toString()), Integer.parseInt(dlg.getTxtY1().getText().toString())), new Point(Integer.parseInt(dlg.getTxtX2().getText().toString()), Integer.parseInt(dlg.getTxtY2().getText().toString())), false, dlg.getBtnColor().getBackground());
						UpdateLineCmd uLC = new UpdateLineCmd(temp, l);
						commands.push(uLC);
						logStack.push("Line modified:" + temp.toString() + "," + temp.getColor() 
						+ " to " + l.toString() + "," + l.getColor());
						frame.getTextArea().append("Line modified: " + temp.toString() + "," + temp.getColor() 
						+ " to " + l.toString() + "," + l.getColor() + " \n");
						uLC.execute();
				}
				} else if (model.getShapes().get(i).isSelected() && model.getShapes().get(i) instanceof HexagonAdapter) {
					DlgHexagon dlg = new DlgHexagon();
					HexagonAdapter temp = (HexagonAdapter) model.getShapes().get(i);	
					dlg.getTxtX().setText(String.valueOf(temp.getHexagon().getX()));
					dlg.getTxtY().setText(String.valueOf(temp.getHexagon().getY()));
					dlg.getTxtR().setText(String.valueOf(temp.getHexagon().getR()));
					dlg.getBtnColor().setBackground(temp.getHexagon().getBorderColor());
					dlg.getBtnInnerColor().setBackground(temp.getHexagon().getAreaColor());
					dlg.setTitle("Modify hexagon");
					dlg.setVisible(true);
					if (dlg.isOk()) {
						Color c = dlg.getBtnColor().getBackground();
						Color innerC = dlg.getBtnInnerColor().getBackground();
						HexagonAdapter h = new HexagonAdapter(new Point(Integer.parseInt(dlg.getTxtX().getText().toString()), 
								Integer.parseInt(dlg.getTxtY().getText().toString())), 
								Integer.parseInt(dlg.getTxtR().getText().toString()), false, c, innerC);
						UpdateHexagonCmd uHC = new UpdateHexagonCmd(temp, h);
						commands.push(uHC);
						logStack.push("Hexagon modified:" + temp.toString() + "," + temp.getColor() + "," + temp.getInnerColor()
						+ " to " + h.toString() + "," + h.getColor() + "," + h.getInnerColor());
						frame.getTextArea().append("Hexagon modified:" + temp.toString() + "," + temp.getColor() + "," + temp.getInnerColor()
						+ " to " + h.toString() + "," + h.getColor() + "," + h.getInnerColor() + " \n");
						uHC.execute();
					}
				}
			} 
			frame.repaint();
			frame.getBtnRedo().setEnabled(false); //redo je dostupan jedino ako smo prvo uradili undo
		}
		frame.getBtnGroup().clearSelection();
		frame.getBtnGroup2().clearSelection();
		temp.clear();
	}
	
	//SELECTION FUNCTION
	public void selection(Graphics g, int x, int y) {
		int counter = 0;
		for (int i = 0; i < model.getShapes().size(); i++) { 	//prolazak kroz listu svih oblika	      
			if (model.getShapes().get(i).contains(x, y)) { //proverava da li se tacka klika sadrzi u obliku
				if (!model.getSelected().contains(model.getShapes().get(i))) { //proveravamo da li se oblik NE nalazi u listi selektovanih
					model.addSelected(model.getShapes().get(i)); //dodajemo oblike u listu selektovanih
					SelectCmd sC = new SelectCmd(model.getShapes().get(i), model, buttons);
					commands.push(sC);
					if (model.getShapes().get(i) instanceof Circle && !(model.getShapes().get(i) instanceof Donut)) {
						logStack.push("Circle selected: " + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor() + ", " + ((Circle) model.getShapes().get(i)).getInnerColor());
						frame.getTextArea().append("Circle selected: " + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor() + ((Circle) model.getShapes().get(i)).getInnerColor() + "\n");
					} else if (model.getShapes().get(i) instanceof Point) {
						logStack.push("Point selected:" + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor());
						frame.getTextArea().append("Point selected:" + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor() + "\n");
					} else if (model.getShapes().get(i) instanceof Line) {
						logStack.push("Line selected: " + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor());
						frame.getTextArea().append("Line selected: " + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor() + "\n");
					} else if (model.getShapes().get(i) instanceof Rectangle) {
						logStack.push("Rectangle selected: " + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor() + ", " + ((Rectangle) model.getShapes().get(i)).getInnerColor());
						frame.getTextArea().append("Rectangle selected: " + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor() + ((Rectangle) model.getShapes().get(i)).getInnerColor() + "\n");
					} else if (model.getShapes().get(i) instanceof Donut) {
						logStack.push("Donut selected: " + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor() + ", " + ((Donut) model.getShapes().get(i)).getInnerColor());
						frame.getTextArea().append("Donut selected: " + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor() + ((Donut) model.getShapes().get(i)).getInnerColor() + "\n");
					} else if (model.getShapes().get(i) instanceof HexagonAdapter) {
						logStack.push("Hexagon selected: " + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor() + ", " + ((HexagonAdapter) model.getShapes().get(i)).getInnerColor());
						frame.getTextArea().append("Hexagon selected: " + model.getShapes().get(i).toString() + ", " + 
					model.getShapes().get(i).getColor() + ((HexagonAdapter) model.getShapes().get(i)).getInnerColor() + "\n");
					} 
				}
				frame.getBtnRedo().setEnabled(false); // redo je dostupan jedino ako smo prvo uradili undo
			} else if (!model.getShapes().get(i).contains(x, y)) { //proverava da li se tacka klika ne sadrzi u obliku
					counter++;
			} 
			if (counter == model.getShapes().size()) { //counter postaje velicina liste jedino ako nijedan oblik nije selektovan
				ArrayList<Shape> shapes = new ArrayList<Shape>();
				for(int a = 0; a < model.getSelected().size(); a++) {
					shapes.add(model.getSelected(a));
				}
				if(model.getSelected().size() != 0) { //ako je do sad bilo selektovanih oblika
					UnselectCmd uSC = new UnselectCmd(shapes, model, buttons);
					commands.push(uSC);
					logStack.push("All shapes unselected");
					frame.getTextArea().append("All shapes unselected\n");
				}
				model.getSelected().clear(); //praznimo listu selektovanih oblika
				for (int j = 0; j < model.getShapes().size(); j++) //postavljamo sve oblike na false selected
					model.getShapes().get(j).setSelected(false);
			}
		}
		frame.repaint();
		buttons.setCounter(model.getSelected().size()); //observable buttons poziva setCounter i obavestava observere o broju selektovanih oblika
		frame.getBtnGroup().clearSelection();
		frame.getBtnGroup2().clearSelection();
		temp.clear();
	}
	
	//UNDO FUNCTION
	public void undo(boolean b) {
		try {
			temp.push(commands.peek());
			commands.pop().unexecute();
			frame.repaint();
			frame.getBtnRedo().setEnabled(true);
			if(b==true) {
				logStack.push("UnDo -> command");
				frame.getTextArea().append("UnDo -> command" + "\n");
			}
		} catch(EmptyStackException e) {
			JOptionPane.showMessageDialog(frame, "There are no commands to be undoed", "Inane warning",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//REDO FUNCTION
	public void redo(boolean b) {
		try {
			commands.push(temp.peek());
			temp.pop().execute();
			frame.repaint();
			if(b==true) {
				logStack.push("ReDo -> command");
				frame.getTextArea().append("ReDo -> command" + "\n");
			}
		} catch(EmptyStackException e) {
			JOptionPane.showMessageDialog(frame, "There are no commands to be redoed", "Inane warning",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//SAVE FUNCTION
	public void save() throws IOException{
		logSaving = new LogSaving();
		drawSaving = new DrawSaving();
		
		savingManager = new SavingManager(logSaving);
		savingManager2 = new SavingManager(drawSaving);
		
		savingManager.save(logStack);
		savingManager2.save(model.getShapes()); 
	}
	
	//IMPORT FUNCTION
	public void importFile() throws IOException, ClassNotFoundException {
		fileChooser = new JFileChooser();
		int d = fileChooser.showOpenDialog(null);
		if(d == JFileChooser.APPROVE_OPTION) {
			fileInputStream = new FileInputStream(fileChooser.getSelectedFile().getAbsolutePath());
			ObjectInputStream in = new ObjectInputStream(fileInputStream);
			logStack = (Stack<String>) in.readObject(); //u logstack smestamo ono sto smo procitali iz fajla
			in.close(); 
			fileInputStream.close(); 
			
			model.getShapes().clear();
			model.getSelected().clear();
			commands.clear();
			temp.clear();
			
			frame.getTextArea().setText("");
			
			frame.repaint();
			
			for(int i = 0; i < logStack.size(); i++) {
				frame.getTextArea().append(logStack.elementAt(i) + "\n");
			}
		}
	}
	
	//TO FRONT FUNCTION
	public void toFront(int number) {
		Shape selected = model.getSelected().get(0);
		for(int i = 0; i < model.getShapes().size(); i++) {
			if(selected.compareTo(model.getShapes().get(i)) == 1) {
				if(model.getShapes().get(i) == model.getShapes().get(model.getShapes().size()-1)) //ako je na vrhu 
					JOptionPane.showMessageDialog(frame, "The shape is in front of all shapes", "Inane warning",JOptionPane.WARNING_MESSAGE);
				else {
					Shape temp = model.getShapes().get(i+1); //sledeci oblik u listi
					model.getShapes().set(i+1, selected); //oblik sa sledece pozicije ide poziciju unazad
					model.getShapes().set(i, temp); //oblik sa indeksom i ide na sledecu poziciju
					//SLUCAJ KADA SE METODA POZIVA IZ FRAMEA
					if(number==1) {			
						logStack.push("To Front:" + selected);
						frame.getTextArea().append("To Front:" + selected + '\n');
						ToFrontCmd tFC = new ToFrontCmd(this);
						commands.push(tFC);
					}
					//SLUCAJ KADA SE METODA POZIVA IZ METODE NEXT
					if(number==0) {
						ToFrontCmd tFC = new ToFrontCmd(this);
						commands.push(tFC);
					}
					break;
				}				
			}
		}
		frame.repaint();
	}
	
	//TO BACK FUNCTION
	public void toBack(int number) {
		Shape selected = model.getSelected().get(0);
		for(int i = 0; i < model.getShapes().size(); i++) {
			if(selected.compareTo(model.getShapes().get(i)) == 1) {
				if(model.getShapes().get(i) == model.getShapes().get(0)) //ako je poslednji
					JOptionPane.showMessageDialog(frame, "Shape is behind all shapes", "Inane warning",JOptionPane.WARNING_MESSAGE);
				else {
					Shape temp = model.getShapes().get(i-1);
					model.getShapes().set(i-1, selected);
					model.getShapes().set(i, temp);		
					//SLUCAJ KADA SE METODA POZIVA IZ FRAMEA
					if(number==1) {
						logStack.push("To Back:" + selected);	
						frame.getTextArea().append("To Back:" + selected + '\n');
						ToBackCmd tBC = new ToBackCmd(this);
						commands.push(tBC);
					}	
					//SLUCAJ KADA SE METODA POZIVA IZ METODE NEXT
					if(number==0) {
						ToBackCmd tBC = new ToBackCmd(this);
						commands.push(tBC);
					}
					break;
				}				
			}
		}
		frame.repaint();
	}
	
	//BRING TO FRONT FUNCTION
	public void bringToFront(int number) {
		Shape selected = model.getSelected().get(0);
		int index = model.getShapes().indexOf(selected);
		for(int i = 0; i < model.getShapes().size(); i++) {
			if(selected.compareTo(model.getShapes().get(i)) == 1) {
				if (model.getShapes().get(i) != model.getShapes().get(model.getShapes().size()-1)) { //ako nije na vrhu
					for(int j = i ; j < model.getShapes().size()-1; j++) { //od i-te pozicije pomeramo svaki oblik poziciju unazazad
						model.getShapes().set(j, model.getShapes().get(++j));
						j--;
					}
					model.getShapes().set(model.getShapes().size()-1, selected); // na mesto poslednjeg u listi stavljamo selektovani oblik
					//SLUCAJ KADA SE METODA POZIVA IZ FRAMEA
					if(number==1) {
						logStack.push("Bring to Front:" + selected);
						frame.getTextArea().append("Bring to Front:" + selected + '\n');
						BringToFrontCmd bTFC = new BringToFrontCmd(this, index, model.getShapes().size());
						commands.push(bTFC);
					}	
					//SLUCAJ KADA SE METODA POZIVA IZ METODE NEXT
					if(number==0 ) {
						BringToFrontCmd bTFC = new BringToFrontCmd(this, index, model.getShapes().size());
						commands.push(bTFC);
					}
					
					break;
				} else 
					JOptionPane.showMessageDialog(frame, "Shape is in front of all shapes", "Inane warning",JOptionPane.WARNING_MESSAGE);
			}
		}
		frame.repaint();
		
	}
	
	//BRING TO BACK FUNCTION
	public void bringToBack(int number) {
		Shape selected = model.getSelected().get(0);
		int index = model.getShapes().indexOf(selected);
		for(int i = 0; i < model.getShapes().size(); i++) {
			if(selected.compareTo(model.getShapes().get(i)) == 1) {
				if(model.getShapes().get(i) == model.getShapes().get(0))
					JOptionPane.showMessageDialog(frame, "Shape is behind of all shapes", "Inane warning",JOptionPane.WARNING_MESSAGE);
				else {
					for(int j = i ; j > 0; j--) {
						model.getShapes().set(j, model.getShapes().get(--j));
						j++;
					}
					model.getShapes().set(0, selected);
					//SLUCAJ KADA SE METODA POZIVA IZ FRAMEA
					if(number==1) {
						logStack.push("Bring to Back:" + selected);
						frame.getTextArea().append("Bring to Back:" + selected + '\n');
						BringToBackCmd bTBC = new BringToBackCmd(this, index);
						commands.push(bTBC);
					}
					//SLUCAJ KADA SE METODA POZIVA IZ METODE NEXT
					if(number==0) {
						BringToBackCmd bTBC = new BringToBackCmd(this, index);
						commands.push(bTBC);
					}
					//break;
				}
			} 
		}
		frame.repaint();
	}
	
	//NEXT FUNCTION
	public void next() {
		if (i == logStack.size())
			JOptionPane.showMessageDialog(frame, "There are no next commands in log!", "Inane warning",JOptionPane.WARNING_MESSAGE);
		else {
			String[] array = logStack.elementAt(i).split(":");
			if(array[0].equals("Point")) {
				String[] array1 = array[1].split(",");
				String[] r = array1[2].split("=");
				String[] g = array1[3].split("=");
				String[] b = array1[4].split("=");
				String[] b1 = b[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]), Integer.parseInt(g[1]), Integer.parseInt(b1[0]));
				Point point = new Point(Integer.parseInt(array1[0]), Integer.parseInt(array1[1]), false, color);
				model.add(point);
				frame.repaint();
				AddShapeCmd aSC = new AddShapeCmd(point, model);
				commands.push(aSC);
				
			} else if (array[0].equals("Line")) {
				String[] array1 = array[2].split(",");
				String[] array2 = array[3].split(",");
				String[] r = array2[2].split("=");
				String[] g = array2[3].split("=");
				String[] b = array2[4].split("=");
				String[] b1 = b[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Line line = new Line(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), 
						new Point(Integer.parseInt(array2[0]),Integer.parseInt(array2[1])), false, color);
				model.add(line);
				frame.repaint();
				AddShapeCmd aSC = new AddShapeCmd(line, model);
				commands.push(aSC);
				
			} else if(array[0].equals("Rectangle")) {
				String[] array1 = array[2].split(",");
				String[] width = array1[2].split("=");
				String[] height = array1[3].split("=");
				String[] r = array1[4].split("=");
				String[] g = array1[5].split("=");
				String[] b = array1[6].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[7].split("=");
				String[] g2 = array1[8].split("=");
				String[] b2 = array1[9].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Rectangle rectangle = new Rectangle(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), 
						Integer.parseInt(width[1]), Integer.parseInt(height[1]), false, color, innerColor);
				model.add(rectangle);
				frame.repaint();
				AddShapeCmd aSC = new AddShapeCmd(rectangle, model);
				commands.push(aSC);
				
			} else if(array[0].equals("Donut")) {
				String[] array1 = array[2].split(",");
				String[] radius = array1[2].split("=");
				String[] innerRadius = array1[3].split("=");
				String[] r = array1[4].split("=");
				String[] g = array1[5].split("=");
				String[] b = array1[6].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[7].split("=");
				String[] g2 = array1[8].split("=");
				String[] b2 = array1[9].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Donut donut = new Donut(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), 
						Integer.parseInt(radius[1]), Integer.parseInt(innerRadius[1]), false, color, innerColor);
				model.add(donut);
				frame.repaint();
				AddShapeCmd aSC = new AddShapeCmd(donut, model);
				commands.push(aSC);

			} else if(array[0].equals("Circle")) {
				String[] array1 = array[2].split(",");
				String[] radius = array1[2].split("=");
				String[] r = array1[3].split("=");
				String[] g = array1[4].split("=");
				String[] b = array1[5].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[6].split("=");
				String[] g2 = array1[7].split("=");
				String[] b2 = array1[8].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Circle circle = new Circle(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])),
						Integer.parseInt(radius[1]), false, color, innerColor);
				model.add(circle);
				frame.repaint();
				AddShapeCmd aSC = new AddShapeCmd(circle, model);
				commands.push(aSC);
				
			} else if(array[0].equals("Hexagon")) {
				String[] array1 = array[2].split(",");
				String[] radius = array1[2].split("=");
				String[] r = array1[3].split("=");
				String[] g = array1[4].split("=");
				String[] b = array1[5].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[6].split("=");
				String[] g2 = array1[7].split("=");
				String[] b2 = array1[8].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				HexagonAdapter h = new HexagonAdapter(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), 
						Integer.parseInt(radius[1]), false, color, innerColor);
				model.add(h);
				frame.repaint();
				AddShapeCmd aSC = new AddShapeCmd(h, model);
				commands.push(aSC);
				
			} else if(array[0].equals("Point deleted")) {
				String[] array1 = array[2].split(",");
				String[] r = array1[2].split("=");
				String[] g = array1[3].split("=");
				String[] b = array1[4].split("=");
				String[] b1 = b[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]), Integer.parseInt(g[1]), Integer.parseInt(b1[0]));
				Point point = new Point(Integer.parseInt(array1[0]), Integer.parseInt(array1[1]), false, color);
				
				RemoveShapeCmd rSC = new RemoveShapeCmd(compareShapes(point), model, buttons, model.getShapes().indexOf(compareShapes(point)));
				rSC.execute();
				commands.push(rSC);
				
				frame.repaint();
				
			} else if(array[0].equals("Line deleted")) {
				String[] array1 = array[3].split(",");
				String[] array2 = array[4].split(",");
				String[] r = array2[2].split("=");
				String[] g = array2[3].split("=");
				String[] b = array2[4].split("=");
				String[] b1 = b[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Line line = new Line(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), 
						new Point(Integer.parseInt(array2[0]),Integer.parseInt(array2[1])), false, color);
				
				RemoveShapeCmd rSC = new RemoveShapeCmd(compareShapes(line), model, buttons, model.getShapes().indexOf(compareShapes(line)));
				rSC.execute();
				commands.push(rSC);
				
				frame.repaint();
				
			} else if(array[0].equals("Rectangle deleted")) {
				String[] array1 = array[3].split(",");
				String[] width = array1[2].split("=");
				String[] height = array1[3].split("=");
				String[] r = array1[4].split("=");
				String[] g = array1[5].split("=");
				String[] b = array1[6].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[7].split("=");
				String[] g2 = array1[8].split("=");
				String[] b2 = array1[9].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Rectangle rectangle = new Rectangle(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), Integer.parseInt(width[1]),
						Integer.parseInt(height[1]), false, color, innerColor);
				
				RemoveShapeCmd rSC = new RemoveShapeCmd(compareShapes(rectangle), model, buttons, model.getShapes().indexOf(compareShapes(rectangle)));
				rSC.execute();
				commands.push(rSC);
				
				frame.repaint();
				
			} else if(array[0].equals("Donut deleted")) {
				String[] array1 = array[3].split(",");
				String[] radius = array1[2].split("=");
				String[] innerRadius = array1[3].split("=");
				String[] r = array1[4].split("=");
				String[] g = array1[5].split("=");
				String[] b = array1[6].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[7].split("=");
				String[] g2 = array1[8].split("=");
				String[] b2 = array1[9].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Donut donut = new Donut(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), Integer.parseInt(radius[1]),
						Integer.parseInt(innerRadius[1]), false, color, innerColor);

				RemoveShapeCmd rSC = new RemoveShapeCmd(compareShapes(donut), model, buttons, model.getShapes().indexOf(compareShapes(donut)));
				rSC.execute(); 
				commands.push(rSC);
				
				frame.repaint();
				
			} else if(array[0].equals("Circle deleted")) {
				String[] array1 = array[3].split(",");
				String[] radius = array1[2].split("=");
				String[] r = array1[3].split("=");
				String[] g = array1[4].split("=");
				String[] b = array1[5].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[6].split("=");
				String[] g2 = array1[7].split("=");
				String[] b2 = array1[8].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Circle circle = new Circle(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), Integer.parseInt(radius[1]), false, color, innerColor);
					
				RemoveShapeCmd rSC = new RemoveShapeCmd(compareShapes(circle), model, buttons, model.getShapes().indexOf(compareShapes(circle)));
				rSC.execute(); //brisanje kruga iz liste Shape
				commands.push(rSC);
				
				frame.repaint();

				
			} else if(array[0].equals("Hexagon deleted")) {
				String[] array1 = array[3].split(",");
				String[] radius = array1[2].split("=");
				String[] r = array1[3].split("=");
				String[] g = array1[4].split("=");
				String[] b = array1[5].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[6].split("=");
				String[] g2 = array1[7].split("=");
				String[] b2 = array1[8].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				HexagonAdapter h = new HexagonAdapter(new Point(Integer.parseInt(array1[0]), Integer.parseInt(array1[1])),
						Integer.parseInt(radius[1]), false, color, innerColor);
				
				RemoveShapeCmd rSC = new RemoveShapeCmd(compareShapes(h), model, buttons, model.getShapes().indexOf(compareShapes(h)));
				rSC.execute();
				commands.push(rSC);
				
				frame.repaint();
					
			} else if(array[0].equals("Point modified")) {
				String[] array1 = array[2].split(",");
				String[] r = array1[2].split("=");
				String[] g = array1[3].split("=");
				String[] b = array1[4].split("=");
				String[] b1 = b[1].split("]");
				
				String[] array1N = array[3].split(",");
				String[] rN = array1N[2].split("=");
				String[] gN = array1N[3].split("=");
				String[] bN = array1N[4].split("=");
				String[] b1N = bN[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]), Integer.parseInt(g[1]), Integer.parseInt(b1[0]));
				Color color2 = new Color(Integer.parseInt(rN[1]), Integer.parseInt(gN[1]), Integer.parseInt(b1N[0]));
				Point point = new Point(Integer.parseInt(array1[0]), Integer.parseInt(array1[1]), true, color);
				Point point2 = new Point(Integer.parseInt(array1N[0]), Integer.parseInt(array1N[1]), true, color2);
				
				Point p = (Point) compareShapes(point);
				
				UpdatePointCmd uPC = new UpdatePointCmd(p, point2);
				p.setSelected(true);
				uPC.execute();
				commands.push(uPC); // dodajemo na stack kako bismo mogli da uradimo undo
				frame.repaint();
				
			} else if(array[0].equals("Line modified")) {
				String[] array1 = array[3].split(",");
				String[] array2 = array[4].split(",");
				String[] r = array2[2].split("=");
				String[] g = array2[3].split("=");
				String[] b = array2[4].split("=");
				String[] b1 = b[1].split("]");
				
				String[] array1N = array[6].split(",");
				String[] array2N = array[7].split(",");
				String[] rN = array2N[2].split("=");
				String[] gN = array2N[3].split("=");
				String[] bN = array2N[4].split("=");
				String[] b1N = bN[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Line line = new Line(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])),
						new Point(Integer.parseInt(array2[0]),Integer.parseInt(array2[1])), false, color);
				Color color2 = new Color(Integer.parseInt(rN[1]),Integer.parseInt(gN[1]),Integer.parseInt(b1N[0]));
				Line line2 = new Line(new Point(Integer.parseInt(array1N[0]),Integer.parseInt(array1N[1])),
						new Point(Integer.parseInt(array2N[0]),Integer.parseInt(array2N[1])), false, color2);
				
				Line l = (Line) compareShapes(line);
				
				UpdateLineCmd uLC = new UpdateLineCmd(l, line2);
				l.setSelected(true);
				uLC.execute();
				commands.push(uLC); // dodajemo na stack kako bismo mogli da uradimo undo
				frame.repaint();		
				
			} else if(array[0].equals("Rectangle modified")) {
				String[] array1 = array[3].split(",");
				String[] width = array1[2].split("=");
				String[] height = array1[3].split("=");
				String[] r = array1[4].split("=");
				String[] g = array1[5].split("=");
				String[] b = array1[6].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[7].split("=");
				String[] g2 = array1[8].split("=");
				String[] b2 = array1[9].split("=");
				String[] b3 = b2[1].split("]");
				
				String[] array1N = array[5].split(",");
				String[] widthN = array1N[2].split("=");
				String[] heightN = array1N[3].split("=");
				String[] rN = array1N[4].split("=");
				String[] gN = array1N[5].split("=");
				String[] bN = array1N[6].split("=");
				String[] b1N = bN[1].split("]");
				String[] r2N = array1N[7].split("=");
				String[] g2N = array1N[8].split("=");
				String[] b2N = array1N[9].split("=");
				String[] b3N = b2N[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Rectangle rectangle = new Rectangle(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), Integer.parseInt(width[1]),
						Integer.parseInt(height[1]), false, color, innerColor);
				Color color2 = new Color(Integer.parseInt(rN[1]),Integer.parseInt(gN[1]),Integer.parseInt(b1N[0]));
				Color innerColor2 = new Color(Integer.parseInt(r2N[1]),Integer.parseInt(g2N[1]),Integer.parseInt(b3N[0]));
				Rectangle rectangle2 = new Rectangle(new Point(Integer.parseInt(array1N[0]),Integer.parseInt(array1N[1])), Integer.parseInt(widthN[1]),
						Integer.parseInt(heightN[1]), false, color2, innerColor2);
				
				Rectangle re = (Rectangle) compareShapes(rectangle);
				
				UpdateRectangleCmd uRC = new UpdateRectangleCmd(re, rectangle2);
				re.setSelected(true);
				uRC.execute();
				commands.push(uRC); // dodajemo na stack kako bismo mogli da uradimo undo
				frame.repaint();
				
			} else if(array[0].equals("Donut modified")) {
				String[] array1 = array[3].split(",");
				String[] radius = array1[2].split("=");
				String[] innerRadius = array1[3].split("=");
				String[] r = array1[4].split("=");
				String[] g = array1[5].split("=");
				String[] b = array1[6].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[7].split("=");
				String[] g2 = array1[8].split("=");
				String[] b2 = array1[9].split("=");
				String[] b3 = b2[1].split("]");
				
				String[] array1N = array[5].split(",");
				String[] radiusN = array1N[2].split("=");
				String[] innerRadiusN = array1N[3].split("=");
				String[] rN = array1N[4].split("=");
				String[] gN = array1N[5].split("=");
				String[] bN = array1N[6].split("=");
				String[] b1N = bN[1].split("]");
				String[] r2N = array1N[7].split("=");
				String[] g2N = array1N[8].split("=");
				String[] b2N = array1N[9].split("=");
				String[] b3N = b2N[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Donut donut = new Donut(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), Integer.parseInt(radius[1]),
						Integer.parseInt(innerRadius[1]), false, color, innerColor);
				Color color2 = new Color(Integer.parseInt(rN[1]),Integer.parseInt(gN[1]),Integer.parseInt(b1N[0]));
				Color innerColor2 = new Color(Integer.parseInt(r2N[1]),Integer.parseInt(g2N[1]),Integer.parseInt(b3N[0]));
				Donut donut2 = new Donut(new Point(Integer.parseInt(array1N[0]),Integer.parseInt(array1N[1])), Integer.parseInt(radiusN[1]),
						Integer.parseInt(innerRadiusN[1]), false, color2, innerColor2);
				
				Donut d = (Donut) compareShapes(donut);
				
				UpdateDonutCmd uDC = new UpdateDonutCmd(d, donut2);
				d.setSelected(true);
				uDC.execute();
				commands.push(uDC); // dodajemo na stack kako bismo mogli da uradimo undo
				frame.repaint();
				
			} else if(array[0].equals("Circle modified")) {
				String[] array1 = array[3].split(",");
				String[] radius = array1[2].split("="); //dobro
				String[] r = array1[3].split("=");
				String[] g = array1[4].split("=");
				String[] b = array1[5].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[6].split("=");
				String[] g2 = array1[7].split("=");
				String[] b2 = array1[8].split("=");
				String[] b3 = b2[1].split("]");
				
				String[] array1N = array[5].split(",");
				String[] radiusN = array1N[2].split("=");
				String[] rN = array1N[3].split("=");
				String[] gN = array1N[4].split("=");
				String[] bN = array1N[5].split("=");
				String[] b1N = bN[1].split("]");
				String[] r2N = array1N[6].split("=");
				String[] g2N = array1N[7].split("=");
				String[] b2N = array1N[8].split("=");
				String[] b3N = b2N[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Circle circle = new Circle(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), 
						Integer.parseInt(radius[1]), false, color, innerColor);
				Color color2 = new Color(Integer.parseInt(rN[1]),Integer.parseInt(gN[1]),Integer.parseInt(b1N[0]));
				Color innerColor2 = new Color(Integer.parseInt(r2N[1]),Integer.parseInt(g2N[1]),Integer.parseInt(b3N[0]));
				Circle circle2 = new Circle(new Point(Integer.parseInt(array1N[0]),Integer.parseInt(array1N[1])), 
						Integer.parseInt(radiusN[1]), false, color2, innerColor2);
				
				Circle c = (Circle) compareShapes(circle);
				
				UpdateCircleCmd uCC = new UpdateCircleCmd(c, circle2);
				c.setSelected(true);
				uCC.execute();
				commands.push(uCC); // dodajemo na stack kako bismo mogli da uradimo undo
				frame.repaint();

				
			} else if(array[0].equals("Hexagon modified")) {
				String[] array1 = array[3].split(",");
				String[] radius = array1[2].split("=");
				String[] r = array1[3].split("=");
				String[] g = array1[4].split("=");
				String[] b = array1[5].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[6].split("=");
				String[] g2 = array1[7].split("=");
				String[] b2 = array1[8].split("=");
				String[] b3 = b2[1].split("]");
				
				String[] array1N = array[5].split(",");
				String[] radiusN = array1N[2].split("=");
				String[] rN = array1N[3].split("=");
				String[] gN = array1N[4].split("=");
				String[] bN = array1N[5].split("=");
				String[] b1N = bN[1].split("]");
				String[] r2N = array1N[6].split("=");
				String[] g2N = array1N[7].split("=");
				String[] b2N = array1N[8].split("=");
				String[] b3N = b2N[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				HexagonAdapter hexA = new HexagonAdapter(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), 
						Integer.parseInt(radius[1]), false, color, innerColor);
				
				Color color2 = new Color(Integer.parseInt(rN[1]),Integer.parseInt(gN[1]),Integer.parseInt(b1N[0]));
				Color innerColor2 = new Color(Integer.parseInt(r2N[1]),Integer.parseInt(g2N[1]),Integer.parseInt(b3N[0]));
				HexagonAdapter hexA2 = new HexagonAdapter(new Point(Integer.parseInt(array1N[0]),Integer.parseInt(array1N[1])),
						Integer.parseInt(radiusN[1]), false, color2, innerColor2);

				HexagonAdapter h = (HexagonAdapter) compareShapes(hexA);
				
				UpdateHexagonCmd uHC = new UpdateHexagonCmd(h, hexA2);
				h.setSelected(true);
				uHC.execute();
				commands.push(uHC); // dodajemo na stack kako bismo mogli da uradimo undo
				frame.repaint();

				
			} else if(array[0].equals("Point selected")) {
				String[] array1 = array[2].split(",");
				String[] r = array1[2].split("=");
				String[] g = array1[3].split("=");
				String[] b = array1[4].split("=");
				String[] b1 = b[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]), Integer.parseInt(g[1]), Integer.parseInt(b1[0]));
				Point point = new Point(Integer.parseInt(array1[0]), Integer.parseInt(array1[1]), true, color);
				
				Point p = (Point) compareShapes(point);
				
				model.addSelected(p);
				p.setSelected(true);
				
				SelectCmd sC = new SelectCmd(p, model, buttons);
				commands.push(sC);
				
				frame.repaint();
				
			} else if(array[0].equals("Line selected")) {
				String[] array1 = array[3].split(",");
				String[] array2 = array[4].split(",");
				String[] r = array2[2].split("=");
				String[] g = array2[3].split("=");
				String[] b = array2[4].split("=");
				String[] b1 = b[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Line line = new Line(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), 
						new Point(Integer.parseInt(array2[0]),Integer.parseInt(array2[1])), true, color);
				
				Line l = (Line) compareShapes(line);
				
				model.addSelected(l);
				l.setSelected(true);
				
				SelectCmd sC = new SelectCmd(l, model, buttons);
				commands.push(sC);
				
				frame.repaint();
				
			} else if(array[0].equals("Rectangle selected")) {
				String[] array1 = array[3].split(",");
				String[] width = array1[2].split("=");
				String[] height = array1[3].split("=");
				String[] r = array1[4].split("=");
				String[] g = array1[5].split("=");
				String[] b = array1[6].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[7].split("=");
				String[] g2 = array1[8].split("=");
				String[] b2 = array1[9].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Rectangle rectangle = new Rectangle(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), Integer.parseInt(width[1]),
						Integer.parseInt(height[1]), true, color, innerColor);
				
				Rectangle re = (Rectangle) compareShapes(rectangle);
				
				model.addSelected(re);
				re.setSelected(true);
				
				SelectCmd sC = new SelectCmd(re, model, buttons);
				commands.push(sC);
				
				frame.repaint();
				
			} else if(array[0].equals("Donut selected")) {
				String[] array1 = array[3].split(",");
				String[] radius = array1[2].split("=");
				String[] innerRadius = array1[3].split("=");
				String[] r = array1[4].split("=");
				String[] g = array1[5].split("=");
				String[] b = array1[6].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[7].split("=");
				String[] g2 = array1[8].split("=");
				String[] b2 = array1[9].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Donut donut = new Donut(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), Integer.parseInt(radius[1]),
						Integer.parseInt(innerRadius[1]), true, color, innerColor);
				
				Donut d = (Donut) compareShapes(donut);
				
				model.addSelected(d);
				d.setSelected(true);
				
				SelectCmd sC = new SelectCmd(d, model, buttons);
				commands.push(sC);
				
				frame.repaint();
				
			} else if(array[0].equals("Circle selected")) {
				String[] array1 = array[3].split(",");
				String[] radius = array1[2].split("=");
				String[] r = array1[3].split("=");
				String[] g = array1[4].split("=");
				String[] b = array1[5].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[6].split("=");
				String[] g2 = array1[7].split("=");
				String[] b2 = array1[8].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				Circle circle = new Circle(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])), 
						Integer.parseInt(radius[1]), true, color, innerColor);
				
				Circle c = (Circle) compareShapes(circle);

				model.addSelected(c);
				c.setSelected(true);
				
				SelectCmd sC = new SelectCmd(c, model, buttons);
				commands.push(sC);
				
				frame.repaint();

			} else if(array[0].equals("Hexagon selected")) {
				String[] array1 = array[3].split(",");
				String[] radius = array1[2].split("=");
				String[] r = array1[3].split("=");
				String[] g = array1[4].split("=");
				String[] b = array1[5].split("=");
				String[] b1 = b[1].split("]");
				String[] r2 = array1[6].split("=");
				String[] g2 = array1[7].split("=");
				String[] b2 = array1[8].split("=");
				String[] b3 = b2[1].split("]");
				
				Color color = new Color(Integer.parseInt(r[1]),Integer.parseInt(g[1]),Integer.parseInt(b1[0]));
				Color innerColor = new Color(Integer.parseInt(r2[1]),Integer.parseInt(g2[1]),Integer.parseInt(b3[0]));
				HexagonAdapter hexA = new HexagonAdapter(new Point(Integer.parseInt(array1[0]),Integer.parseInt(array1[1])),
						Integer.parseInt(radius[1]), false, color, innerColor);
				hexA.setSelected(true);
				
				HexagonAdapter h = (HexagonAdapter) compareShapes(hexA);
				
				model.addSelected(h);
				h.setSelected(true);
				
				SelectCmd sC = new SelectCmd(h, model, buttons);
				commands.push(sC);
				
				frame.repaint();
				
			} else if(array[0].equals("UnDo -> command")) {
				undo(false);
				
			} else if(array[0].equals("ReDo -> command")) {
				redo(false);
				
			} else if(array[0].equals("To Back")) {
				toBack(0);
				
			} else if(array[0].equals("To Front")) {
				toFront(0);
				
			} else if(array[0].equals("Bring to Front")) {
				bringToFront(0);
				
			} else if(array[0].equals("Bring to Back")) {
				bringToBack(0);
			} else if(array[0].equals("All shapes unselected")) {
				for(int i = 0; i < model.getSelected().size(); i++) {
					model.getSelected(i).setSelected(false);
				}
				
				ArrayList<Shape> shapes = new ArrayList<Shape>();
				for(int a = 0; a < model.getSelected().size(); a++) {
					shapes.add(model.getSelected(a));
				}
				
				model.getSelected().clear();
				UnselectCmd uSC = new UnselectCmd(shapes, model, buttons);
				commands.push(uSC);
				
				frame.repaint();
			}
			i++;
		}
		
	}
	
	public Shape compareShapes(Shape shape) {
		for(int i = 0; i < model.getShapes().size(); i++) {
			if(shape.compareTo(model.getShapes().get(i)) == 1) {	
				return model.getShapes().get(i);
			}
		}
		return null;
	}

	public ArrayList<Shape> getTempSelected() {
		return tempSelected;
	}
	
	
	
	

}
