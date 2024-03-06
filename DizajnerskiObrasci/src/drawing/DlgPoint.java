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

public class DlgPoint extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtX;
	private JTextField txtY;
	private boolean isOk;
	private Color color;
	private JButton btnColor;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgPoint dialog = new DlgPoint();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgPoint() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setResizable(false);
		color = Color.BLACK;
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
		}
		{
			JPanel pnlCenter = new JPanel();
			contentPanel.add(pnlCenter, BorderLayout.CENTER);
			{
				txtX = new JTextField();
				txtX.setColumns(10);
			}
			{
				txtY = new JTextField();
				txtY.setColumns(10);
			}
		btnColor = new JButton("Color");
			btnColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					color = JColorChooser.showDialog(null, "Choose a color", Color.BLUE);
					btnColor.setBackground(color);
				}
			});
			GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
			gl_pnlCenter.setHorizontalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGap(47)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(32, Short.MAX_VALUE))
					.addGroup(Alignment.TRAILING, gl_pnlCenter.createSequentialGroup()
						.addContainerGap(261, Short.MAX_VALUE)
						.addComponent(btnColor)
						.addGap(33))
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGap(43)
						.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(27)
						.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
						.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(38))
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
									getTxtY().getText().trim().isEmpty()) {
								isOk = false;
								getToolkit().beep();
								setVisible(true);
								JOptionPane.showMessageDialog(null, "You need to enter all values!");
							} else if (Integer.parseInt(getTxtX().getText().toString()) <= 0 || 
									Integer.parseInt(getTxtY().getText().toString()) <= 0) {
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

	public JTextField getTxtX() {
		return txtX;
	}

	public void setTxtX(JTextField txtX) {
		this.txtX = txtX;
	}

	public JTextField getTxtY() {
		return txtY;
	}

	public void setTxtY(JTextField txtY) {
		this.txtY = txtY;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public JButton getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(JButton btnColor) {
		this.btnColor = btnColor;
	}

}
