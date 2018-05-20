package views;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

public class PNLTurnoutWye extends JPanel {
	private GUIBackend backend;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Create the panel.
	 */
	public PNLTurnoutWye(final GUIBackend backend) {
		this.backend=backend;
		setPreferredSize(new Dimension(241, 123));
		setBorder(new TitledBorder(null, "Wye", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblRightRadiusmm = new JLabel("Right Radius [mm]");
		GridBagConstraints gbc_lblRightRadiusmm = new GridBagConstraints();
		gbc_lblRightRadiusmm.anchor = GridBagConstraints.EAST;
		gbc_lblRightRadiusmm.insets = new Insets(0, 0, 5, 5);
		gbc_lblRightRadiusmm.gridx = 1;
		gbc_lblRightRadiusmm.gridy = 0;
		add(lblRightRadiusmm, gbc_lblRightRadiusmm);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblLeftRadiusmm = new JLabel("Left Radius [mm]");
		GridBagConstraints gbc_lblLeftRadiusmm = new GridBagConstraints();
		gbc_lblLeftRadiusmm.anchor = GridBagConstraints.EAST;
		gbc_lblLeftRadiusmm.insets = new Insets(0, 0, 5, 5);
		gbc_lblLeftRadiusmm.gridx = 1;
		gbc_lblLeftRadiusmm.gridy = 1;
		add(lblLeftRadiusmm, gbc_lblLeftRadiusmm);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 1;
		add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblRightOffsetmm = new JLabel("Right Offset [mm]");
		GridBagConstraints gbc_lblRightOffsetmm = new GridBagConstraints();
		gbc_lblRightOffsetmm.anchor = GridBagConstraints.EAST;
		gbc_lblRightOffsetmm.insets = new Insets(0, 0, 5, 5);
		gbc_lblRightOffsetmm.gridx = 1;
		gbc_lblRightOffsetmm.gridy = 2;
		add(lblRightOffsetmm, gbc_lblRightOffsetmm);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 2;
		add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblLeftOffsetmm = new JLabel("Left Offset [mm]");
		GridBagConstraints gbc_lblLeftOffsetmm = new GridBagConstraints();
		gbc_lblLeftOffsetmm.insets = new Insets(0, 0, 0, 5);
		gbc_lblLeftOffsetmm.anchor = GridBagConstraints.EAST;
		gbc_lblLeftOffsetmm.gridx = 1;
		gbc_lblLeftOffsetmm.gridy = 3;
		add(lblLeftOffsetmm, gbc_lblLeftOffsetmm);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 3;
		add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);

	}

}
