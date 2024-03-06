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

public class DlgLine extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtX1;
	private JTextField txtX2;
	private JTextField txtY1;
	private JTextField txtY2;
	private boolean isOk;
	private Color color;
	private JButton btnColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgLine dialog = new DlgLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgLine() {
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
				JLabel lblX1 = new JLabel("X coordinate of start point:");
				pnlWest.add(lblX1);
			}
			{
				JLabel lblY1 = new JLabel("Y coordinate of start point:");
				pnlWest.add(lblY1);
			}
			{
				JLabel lblX2 = new JLabel("X coordinate of end point:");
				pnlWest.add(lblX2);
			}
			{
				JLabel lblY2 = new JLabel("Y coordinate of end point:");
				pnlWest.add(lblY2);
			}
		}
		{
			JPanel pnlCenter = new JPanel();
			contentPanel.add(pnlCenter, BorderLayout.CENTER);
			{
				txtX1 = new JTextField();
				txtX1.setColumns(10);
			}
			{
				txtY1 = new JTextField();
				txtY1.setColumns(10);
			}
			{
				txtX2 = new JTextField();
				txtX2.setColumns(10);
			}
			{
				txtY2 = new JTextField();
				txtY2.setColumns(10);
			}
			
			btnColor = new JButton("Color");
			btnColor.setBackground(color);
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
						.addGap(28)
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addComponent(txtX1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtY2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addComponent(txtX2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
								.addComponent(btnColor))
							.addComponent(txtY1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap())
			);
			gl_pnlCenter.setVerticalGroup(
				gl_pnlCenter.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCenter.createSequentialGroup()
						.addGroup(gl_pnlCenter.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addContainerGap()
								.addComponent(txtX1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(30)
								.addComponent(txtY1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
								.addComponent(txtX2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(30)
								.addComponent(txtY2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnlCenter.createSequentialGroup()
								.addGap(89)
								.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
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
							if (getTxtX1().getText().trim().isEmpty() ||
									getTxtX2().getText().trim().isEmpty() ||
									getTxtY1().getText().trim().isEmpty() ||
									getTxtY2().getText().trim().isEmpty()) {
								isOk = false;
								getToolkit().beep();
								setVisible(true);
								JOptionPane.showMessageDialog(null, "You need to enter all values!");
							} else if (Integer.parseInt(getTxtX1().getText().toString()) <= 0 || 
									Integer.parseInt(getTxtY1().getText().toString()) <= 0 ||
									Integer.parseInt(getTxtX2().getText().toString()) <= 0 || 
									Integer.parseInt(getTxtY2().getText().toString()) <= 0) {
								JOptionPane.showMessageDialog(null, "You need to enter values greater than 0!");
							} else if (Integer.parseInt(getTxtX1().getText().toString()) >= 700 ||
									Integer.parseInt(getTxtY1().getText().toString()) >= 700 ||
									Integer.parseInt(getTxtX2().getText().toString()) >= 700 ||
									Integer.parseInt(getTxtY2().getText().toString()) >= 700)
								JOptionPane.showMessageDialog(null, "You need to enter values for x coordinate between 0 and 700 and between 0 and 700 for y!"); 
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

	public JTextField getTxtX1() {
		return txtX1;
	}

	public void setTxtX1(JTextField txtX1) {
		this.txtX1 = txtX1;
	}

	public JTextField getTxtX2() {
		return txtX2;
	}

	public void setTxtX2(JTextField txtX2) {
		this.txtX2 = txtX2;
	}

	public JTextField getTxtY1() {
		return txtY1;
	}

	public void setTxtY1(JTextField txtY1) {
		this.txtY1 = txtY1;
	}

	public JTextField getTxtY2() {
		return txtY2;
	}

	public void setTxtY2(JTextField txtY2) {
		this.txtY2 = txtY2;
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
