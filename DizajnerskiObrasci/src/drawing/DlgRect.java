package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgRect extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtY;
	private JTextField txtWidth;
	private JTextField txtHeight;
	private boolean isOk;
	private JButton btnColor;
	private JButton btnInnerColor;
	private Color color;
	private Color innerColor;
	private JTextField txtX;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRect dialog = new DlgRect();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRect() {
		setBounds(100, 100, 450, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setTitle("Draw rectangle");
		color = Color.BLACK;
		innerColor = Color.BLACK;
		setResizable(false);
		setModal(true);
		{
			JPanel pnlWest = new JPanel();
			contentPanel.add(pnlWest, BorderLayout.WEST);
			pnlWest.setLayout(new GridLayout(0, 1));
			{
				JLabel lblXCoordinate = new JLabel("X coordinate:");
				pnlWest.add(lblXCoordinate);
			}
			{
				JLabel lblYCoordinate = new JLabel("Y coordinate:");
				pnlWest.add(lblYCoordinate);
			}
			{
				JLabel lblWidth = new JLabel("Width:");
				pnlWest.add(lblWidth);
			}
			{
				JLabel lblHeight = new JLabel("Height:");
				pnlWest.add(lblHeight);
			}
		}
		{
			JPanel pnlCenter = new JPanel();
			contentPanel.add(pnlCenter, BorderLayout.CENTER);
			{
				txtWidth = new JTextField();
				txtWidth.setColumns(10);
			}
			{
				setTxtY(new JTextField());
				getTxtY().setColumns(10);
			}
			{
				txtHeight = new JTextField();
				txtHeight.setColumns(10);
			}
			{
				btnColor = new JButton("Color");
				btnColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						color = JColorChooser.showDialog(null, "Choose a color", Color.BLUE);
						btnColor.setBackground(color);
					}
				});
			}
			
			btnInnerColor = new JButton("Inner color");
			btnInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					innerColor = JColorChooser.showDialog(null, "Choose a color", Color.GRAY);
					btnInnerColor.setBackground(innerColor);
				}
			});
			
			txtX = new JTextField();
			txtX.setColumns(10);
			GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
			gl_pnlCenter.setHorizontalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGap(35)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addComponent(txtHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlCenter.createSequentialGroup()
									.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
								.addGroup(gl_pnlCenter.createSequentialGroup()
									.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
										.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
									.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
										.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnInnerColor))
									.addContainerGap(40, Short.MAX_VALUE)))))
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGap(22)
						.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGap(37)
								.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGap(8)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.TRAILING)
							.addComponent(btnInnerColor, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(34)
						.addComponent(txtHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(21))
			);
			pnlCenter.setLayout(gl_pnlCenter);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try
						{
							if (getTxtX().getText().trim().isEmpty() ||
									getTxtY().getText().trim().isEmpty() ||
									getTxtWidth().getText().trim().isEmpty() ||
									getTxtHeight().getText().trim().isEmpty()) {
								isOk = false;
								getToolkit().beep();
								setVisible(true);
								JOptionPane.showMessageDialog(null, "You need to enter all values!");
							} else if (Integer.parseInt(getTxtX().getText().toString()) <= 0 || 
									Integer.parseInt(getTxtY().getText().toString()) <= 0 ||
									Integer.parseInt(getTxtWidth().getText().toString()) <= 0 ||
									Integer.parseInt(getTxtHeight().getText().toString()) <= 0) {
								JOptionPane.showMessageDialog(null, "You need to enter values greater than 0!");
							} else if (Integer.parseInt(getTxtX().getText().toString()) >= 700 ||
									Integer.parseInt(getTxtY().getText().toString()) >= 700)
								JOptionPane.showMessageDialog(null, "You need to enter values for x coordinate between 0 and 700 and between 0 and 700 for y!");
							else {
								isOk = true;
								dispose();
							}
						}
						catch (Exception e)
						{
							JOptionPane.showMessageDialog(null, "You need to enter numbers!");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JTextField getTxtY() {
		return txtY;
	}

	public void setTxtY(JTextField txtY) {
		this.txtY = txtY;
	}

	public JTextField getTxtWidth() {
		return txtWidth;
	}

	public void setTxtWidth(JTextField txtWidth) {
		this.txtWidth = txtWidth;
	}

	public JTextField getTxtHeight() {
		return txtHeight;
	}

	public void setTxtHeight(JTextField txtHeight) {
		this.txtHeight = txtHeight;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public JTextField getTxtX() {
		return txtX;
	}

	public void setTxtX(JTextField txtX) {
		this.txtX = txtX;
	}

	public JButton getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(JButton btnColor) {
		this.btnColor = btnColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}


}
