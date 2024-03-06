package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class DlgDonut extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtXc;
	private JTextField txtYc;
	private JTextField txtR;
	private boolean isOk;
	private Color color;
	private Color innerColor;
	private JTextField txtIR;
	private JButton btnColor;
	private JButton btnInnerColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgDonut dialog = new DlgDonut();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgDonut() {
		setBounds(100, 100, 499, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setTitle("Draw donut");
		color = Color.BLACK;
		innerColor = Color.BLACK;
		setResizable(false);
		setModal(true);
		{
			JPanel pnlWest = new JPanel();
			contentPanel.add(pnlWest, BorderLayout.WEST);
			pnlWest.setLayout(new GridLayout(0, 1));
			{
				JLabel lblXCoordinateOf = new JLabel("X coordinate of center:");
				pnlWest.add(lblXCoordinateOf);
			}
			{
				JLabel lblYCoordinateOf = new JLabel("Y coordinate of center:");
				pnlWest.add(lblYCoordinateOf);
			}
			{
				JLabel lblInnerRadius = new JLabel("Inner radius:");
				pnlWest.add(lblInnerRadius);
			}
			{
				JLabel lblRadius = new JLabel("Radius:");
				pnlWest.add(lblRadius);
			}
		}
		{
			JPanel pnlCenter = new JPanel();
			contentPanel.add(pnlCenter, BorderLayout.CENTER);
			{
				txtR = new JTextField();
				txtR.setColumns(10);
			}
			{
				txtYc = new JTextField();
				txtYc.setColumns(10);
			}
			{
				txtXc = new JTextField();
				txtXc.setColumns(10);
			}
			btnColor = new JButton("Color");
			btnColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					color = JColorChooser.showDialog(null, "Choose a color", Color.BLUE);
					btnColor.setBackground(color);
				}
			});
			btnInnerColor = new JButton("Inner color");
			btnInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					innerColor = JColorChooser.showDialog(null, "Choose a color", Color.GRAY);
					btnInnerColor.setBackground(innerColor);
				}
			});
			txtIR = new JTextField();
			txtIR.setColumns(10);
			GroupLayout gl_pnlCenter = new GroupLayout(pnlCenter);
			gl_pnlCenter.setHorizontalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGap(35)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addComponent(txtR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
									.addComponent(txtYc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtXc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtIR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
								.addGroup(gl_pnlCenter.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_pnlCenter.createSequentialGroup()
										.addComponent(btnColor)
										.addGap(32))
									.addGroup(gl_pnlCenter.createSequentialGroup()
										.addComponent(btnInnerColor)
										.addGap(22))))))
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addContainerGap()
						.addComponent(txtXc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(30)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtYc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnInnerColor, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtIR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
						.addComponent(txtR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
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
					public void actionPerformed(ActionEvent e) {
						try
						{
							if (getTxtXc().getText().trim().isEmpty() ||
									getTxtYc().getText().trim().isEmpty() ||
									getTxtR().getText().trim().isEmpty() ||
									getTxtIR().getText().trim().isEmpty()) {
								isOk = false;
								getToolkit().beep();
								setVisible(true);
								JOptionPane.showMessageDialog(null, "You need to enter all values!");
							} else if (Integer.parseInt(getTxtXc().getText().toString()) <= 0 || 
									Integer.parseInt(getTxtYc().getText().toString()) <= 0 ||
									Integer.parseInt(getTxtR().getText().toString()) <= 0 ||
									Integer.parseInt(getTxtIR().getText().toString()) <= 0) {
								JOptionPane.showMessageDialog(null, "You need to enter values greater than 0!");
							} else if (Integer.parseInt(getTxtXc().getText().toString()) >= 700 ||
									Integer.parseInt(getTxtYc().getText().toString()) >= 700)
								JOptionPane.showMessageDialog(null, "You need to enter values for x coordinate between 0 and 700 and between 0 and 700 for y!");
							else if (Integer.parseInt(getTxtR().getText().toString()) <= Integer.parseInt(getTxtIR().getText().toString()))
								JOptionPane.showMessageDialog(null, "Radius need to be greater value than inner radius!");
							else {
								isOk = true;
								dispose();
							}
						}
						catch (Exception ex)
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

	public JTextField getTxtXc() {
		return txtXc;
	}

	public void setTxtXc(JTextField txtXc) {
		this.txtXc = txtXc;
	}

	public JTextField getTxtYc() {
		return txtYc;
	}

	public void setTxtYc(JTextField txtYc) {
		this.txtYc = txtYc;
	}

	public JTextField getTxtR() {
		return txtR;
	}

	public void setTxtR(JTextField txtR) {
		this.txtR = txtR;
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

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public JTextField getTxtIR() {
		return txtIR;
	}

	public void setTxtIR(JTextField txtIR) {
		this.txtIR = txtIR;
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
