package mvc;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Component;
import javax.swing.JLabel;

public class DrawingFrame extends JFrame {
	
	//DrawingFrame kreira JPanel(View), jer je on top level container
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	
	private final ButtonGroup btnGroup = new ButtonGroup();
	private final ButtonGroup btnGroup2 = new ButtonGroup();
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnDonut;
	private JToggleButton tglbtnHexagon;
	private JToggleButton tglbtnSelection;
	private JToolBar toolBar;
	private JButton tglbtnDelete;
	private JButton tglbtnModify;
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnSave;
	
	private JTextArea textArea;
	Icon iconPoint = new ImageIcon("C:\\Users\\Djankarlo\\git\\IT44--2019-Danijel-Vilovski\\DizajnerskiObrasci\\assets\\point.png");
	Icon iconLine = new ImageIcon("C:\\Users\\Djankarlo\\git\\IT44--2019-Danijel-Vilovski\\DizajnerskiObrasci\\assets\\line.png");
	Icon iconRectangle = new ImageIcon("C:\\Users\\Djankarlo\\git\\IT44--2019-Danijel-Vilovski\\DizajnerskiObrasci\\assets\\rectangle.png");
	Icon iconCircle = new ImageIcon("C:\\Users\\Djankarlo\\git\\IT44--2019-Danijel-Vilovski\\DizajnerskiObrasci\\assets\\circle.png");
	Icon iconDonut = new ImageIcon("C:\\Users\\Djankarlo\\git\\IT44--2019-Danijel-Vilovski\\DizajnerskiObrasci\\assets\\donut.png");
	Icon iconHexagon = new ImageIcon("C:\\Users\\Djankarlo\\git\\IT44--2019-Danijel-Vilovski\\DizajnerskiObrasci\\assets\\hexagon.png");

	private JButton btnImport;
	private JButton btnNext;
	private JToolBar toolBar_1;
	private JToolBar toolBar_2;
	private JLabel lblInterior;
	private JLabel lblExterior;
	private JButton btnInterior;
	private JButton btnExterior;
	private JButton btnToFront;
	private JButton btnToBack;
	private JButton btnBringToFront;
	private JButton btnBringToBack;
	
	public DrawingFrame() {
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});
		setTitle("Vilovski Danijel IT44-2019");
		setIconImage(new ImageIcon("Icon.png").getImage());
		view.setBorder(new EmptyBorder(10, 10, 10, 10));
		view.setLayout(new BorderLayout(0, 0));
		setContentPane(view);
		getContentPane().setBackground(new Color(238, 238, 238));
		
		toolBar = new JToolBar();
		toolBar.setAlignmentY(Component.CENTER_ALIGNMENT);
		toolBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		toolBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		view.add(toolBar, BorderLayout.NORTH);
		
		tglbtnModify = new JButton("Modify");
		tglbtnModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.modify();
			}
		});
		
		tglbtnSelection = new JToggleButton("Selection");
		tglbtnSelection.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				controller.check(6);
			}
		});
		toolBar.add(tglbtnSelection);
		
		btnGroup2.add(tglbtnSelection);

		toolBar.add(tglbtnModify);
		
		tglbtnDelete = new JButton("Delete");
		tglbtnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.delete();
			}
		});
		toolBar.add(tglbtnDelete);
		
		btnRedo = new JButton("Redo");
		btnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.redo(true);
			}
		});
		
		btnUndo = new JButton("Undo");
		toolBar.add(btnUndo);
		btnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.undo(true);
			}
		});
		toolBar.add(btnRedo);
		
		tglbtnDelete.setEnabled(false);
		tglbtnModify.setEnabled(false);
		
		btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				try {
					controller.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		toolBar.add(btnSave);
		
		btnImport = new JButton("Import");
		btnImport.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				try {
					controller.importFile();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		toolBar.add(btnImport);
		
		btnNext = new JButton("Next");
		btnNext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				controller.next();
			}
		});
		toolBar.add(btnNext);
		
		lblInterior = new JLabel("Interior color: ");
		toolBar.add(lblInterior);
		btnInterior = new JButton("       ");
		btnInterior.setBackground(Color.YELLOW);
		btnInterior.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				Color color1 = JColorChooser.showDialog(null, "Choose a color", Color.BLUE);
				btnInterior.setBackground(color1);
			}
		});
		toolBar.add(btnInterior);
		
		lblExterior = new JLabel("Exterior color: ");
		toolBar.add(lblExterior);
		
		btnExterior = new JButton("       ");
		btnExterior.setBackground(Color.BLACK);
		btnExterior.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				Color color = JColorChooser.showDialog(null, "Choose a color", Color.BLUE);
				btnExterior.setBackground(color);
			}
		});
		toolBar.add(btnExterior);
		
		btnToFront = new JButton("To Front");
		btnToFront.setEnabled(false);
		btnToFront.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				controller.toFront(1);
			}
		});
		toolBar.add(btnToFront);
		
		btnToBack = new JButton("To back");
		btnToBack.setEnabled(false);
		btnToBack.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				controller.toBack(1);
			}
		});
		toolBar.add(btnToBack);
		
		btnBringToFront = new JButton("Bring to Front");
		btnBringToFront.setEnabled(false);
		btnBringToFront.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				controller.bringToFront(1);
			}
		});
		toolBar.add(btnBringToFront);
		
		btnBringToBack = new JButton("Bring to Back");
		btnBringToBack.setEnabled(false);
		btnBringToBack.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				controller.bringToBack(1);
			}
		});
		toolBar.add(btnBringToBack);
		
		textArea = new JTextArea(6, 6);
		textArea.setEditable(false);
		view.add(textArea, BorderLayout.SOUTH);
		
		JScrollPane jSP = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		view.add(jSP, BorderLayout.SOUTH);
		
		toolBar_2 = new JToolBar();
		toolBar_2.setOrientation(SwingConstants.VERTICAL);
		view.add(toolBar_2, BorderLayout.WEST);
		
		tglbtnPoint = new JToggleButton(iconPoint);
		toolBar_2.add(tglbtnPoint);
		tglbtnPoint.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				controller.check(1);	
			}
		});
		
		tglbtnLine = new JToggleButton(iconLine);
		toolBar_2.add(tglbtnLine);
		tglbtnLine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				controller.check(2);
			}
		});
		
		btnGroup.add(tglbtnPoint);
		btnGroup.add(tglbtnLine);
		
		tglbtnRectangle = new JToggleButton(iconRectangle);
		toolBar_2.add(tglbtnRectangle);
		tglbtnRectangle.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				controller.check(3);
			}
		});
		btnGroup.add(tglbtnRectangle);
		
		tglbtnCircle = new JToggleButton(iconCircle);
		toolBar_2.add(tglbtnCircle);
		tglbtnCircle.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				controller.check(4);
			}
		});
		btnGroup.add(tglbtnCircle);
		
		tglbtnDonut = new JToggleButton(iconDonut);
		toolBar_2.add(tglbtnDonut);
		tglbtnDonut.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				controller.check(5);
			}
		});
		btnGroup.add(tglbtnDonut);
		
		tglbtnHexagon = new JToggleButton(iconHexagon);
		toolBar_2.add(tglbtnHexagon);
		tglbtnHexagon.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				controller.check(7);
			}
		});
		btnGroup.add(tglbtnHexagon);
		
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public void setTglbtnPoint(JToggleButton tglbtnPoint) {
		this.tglbtnPoint = tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public void setTglbtnLine(JToggleButton tglbtnLine) {
		this.tglbtnLine = tglbtnLine;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public void setTglbtnRectangle(JToggleButton tglbtnRectangle) {
		this.tglbtnRectangle = tglbtnRectangle;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public void setTglbtnCircle(JToggleButton tglbtnCircle) {
		this.tglbtnCircle = tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public void setTglbtnDonut(JToggleButton tglbtnDonut) {
		this.tglbtnDonut = tglbtnDonut;
	}

	public JToggleButton getTglbtnSelection() {
		return tglbtnSelection;
	}

	public void setTglbtnSelection(JToggleButton tglbtnSelection) {
		this.tglbtnSelection = tglbtnSelection;
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public ButtonGroup getBtnGroup() {
		return btnGroup;
	}

	public ButtonGroup getBtnGroup2() {
		return btnGroup2;
	}
	
	public JButton getTglbtnDelete() {
		return tglbtnDelete;
	}

	public JButton getTglbtnModify() {
		return tglbtnModify;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public DrawingView getView() {
		return view;
	}

	public JButton getBtnImport() {
		return btnImport;
	}

	public void setBtnImport(JButton btnImport) {
		this.btnImport = btnImport;
	}

	public JButton getBtnInterior() {
		return btnInterior;
	}

	public void setBtnInterior(JButton btnInterior) {
		this.btnInterior = btnInterior;
	}

	public JButton getBtnExterior() {
		return btnExterior;
	}

	public void setBtnExterior(JButton btnExterior) {
		this.btnExterior = btnExterior;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public void setBtnToFront(JButton btnToFront) {
		this.btnToFront = btnToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public void setBtnToBack(JButton btnToBack) {
		this.btnToBack = btnToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public void setBtnBringToFront(JButton btnBringToFront) {
		this.btnBringToFront = btnBringToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public void setBtnBringToBack(JButton btnBringToBack) {
		this.btnBringToBack = btnBringToBack;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	}		
}
