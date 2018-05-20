package views;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.border.TitledBorder;

import guiTransfer.ParseInput;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PNLTurnoutCurved extends JPanel {
	private GUIBackend backend;
	private JTextField tfSmallerRadius;
	private JTextField tfGreaterRadius;
	private JTextField tfSmRadiusStrLen;
	private JTextField tfGrRadiusStrLen;
	private JTextField tfOffset;

	/**
	 * Create the panel.
	 */
	public PNLTurnoutCurved(final GUIBackend backend) {
		this.backend = backend;
		setPreferredSize(new Dimension(281, 144));
		setBorder(new TitledBorder(null, "Curved Turnout", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		initComponents();
		addEventHandlers();
	}

	private void initComponents() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblSmallerRadius = new JLabel("Smaller Radius [mm]");
		GridBagConstraints gbc_lblSmallerRadius = new GridBagConstraints();
		gbc_lblSmallerRadius.insets = new Insets(0, 0, 5, 5);
		gbc_lblSmallerRadius.anchor = GridBagConstraints.EAST;
		gbc_lblSmallerRadius.gridx = 1;
		gbc_lblSmallerRadius.gridy = 0;
		add(lblSmallerRadius, gbc_lblSmallerRadius);

		tfSmallerRadius = new JTextField();

		GridBagConstraints gbc_tfSmallerRadius = new GridBagConstraints();
		gbc_tfSmallerRadius.insets = new Insets(0, 0, 5, 0);
		gbc_tfSmallerRadius.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSmallerRadius.gridx = 2;
		gbc_tfSmallerRadius.gridy = 0;
		add(tfSmallerRadius, gbc_tfSmallerRadius);
		tfSmallerRadius.setColumns(10);

		JLabel lblSmallerRadiusStraight = new JLabel("Smaller Radius Straight Length [mm]");
		GridBagConstraints gbc_lblSmallerRadiusStraight = new GridBagConstraints();
		gbc_lblSmallerRadiusStraight.anchor = GridBagConstraints.EAST;
		gbc_lblSmallerRadiusStraight.insets = new Insets(0, 0, 5, 5);
		gbc_lblSmallerRadiusStraight.gridx = 1;
		gbc_lblSmallerRadiusStraight.gridy = 1;
		add(lblSmallerRadiusStraight, gbc_lblSmallerRadiusStraight);

		tfSmRadiusStrLen = new JTextField();

		GridBagConstraints gbc_tfSmRadiusStrLen = new GridBagConstraints();
		gbc_tfSmRadiusStrLen.insets = new Insets(0, 0, 5, 0);
		gbc_tfSmRadiusStrLen.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSmRadiusStrLen.gridx = 2;
		gbc_tfSmRadiusStrLen.gridy = 1;
		add(tfSmRadiusStrLen, gbc_tfSmRadiusStrLen);
		tfSmRadiusStrLen.setColumns(10);

		JLabel lblGreaterRadiusmm = new JLabel("Greater Radius [mm]");
		GridBagConstraints gbc_lblGreaterRadiusmm = new GridBagConstraints();
		gbc_lblGreaterRadiusmm.anchor = GridBagConstraints.EAST;
		gbc_lblGreaterRadiusmm.insets = new Insets(0, 0, 5, 5);
		gbc_lblGreaterRadiusmm.gridx = 1;
		gbc_lblGreaterRadiusmm.gridy = 2;
		add(lblGreaterRadiusmm, gbc_lblGreaterRadiusmm);

		tfGreaterRadius = new JTextField();

		GridBagConstraints gbc_tfGreaterRadius = new GridBagConstraints();
		gbc_tfGreaterRadius.insets = new Insets(0, 0, 5, 0);
		gbc_tfGreaterRadius.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfGreaterRadius.gridx = 2;
		gbc_tfGreaterRadius.gridy = 2;
		add(tfGreaterRadius, gbc_tfGreaterRadius);
		tfGreaterRadius.setColumns(10);

		JLabel lblGreaterRadiusStraight = new JLabel("Greater Radius Straight Length [mm]");
		GridBagConstraints gbc_lblGreaterRadiusStraight = new GridBagConstraints();
		gbc_lblGreaterRadiusStraight.anchor = GridBagConstraints.EAST;
		gbc_lblGreaterRadiusStraight.insets = new Insets(0, 0, 5, 5);
		gbc_lblGreaterRadiusStraight.gridx = 1;
		gbc_lblGreaterRadiusStraight.gridy = 3;
		add(lblGreaterRadiusStraight, gbc_lblGreaterRadiusStraight);

		tfGrRadiusStrLen = new JTextField();

		GridBagConstraints gbc_tfGrRadiusStrLen = new GridBagConstraints();
		gbc_tfGrRadiusStrLen.insets = new Insets(0, 0, 5, 0);
		gbc_tfGrRadiusStrLen.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfGrRadiusStrLen.gridx = 2;
		gbc_tfGrRadiusStrLen.gridy = 3;
		add(tfGrRadiusStrLen, gbc_tfGrRadiusStrLen);
		tfGrRadiusStrLen.setColumns(10);

		JLabel lblOffsetmm = new JLabel("Offset [mm]");
		GridBagConstraints gbc_lblOffsetmm = new GridBagConstraints();
		gbc_lblOffsetmm.anchor = GridBagConstraints.EAST;
		gbc_lblOffsetmm.insets = new Insets(0, 0, 0, 5);
		gbc_lblOffsetmm.gridx = 1;
		gbc_lblOffsetmm.gridy = 4;
		add(lblOffsetmm, gbc_lblOffsetmm);

		tfOffset = new JTextField();

		GridBagConstraints gbc_tfOffset = new GridBagConstraints();
		gbc_tfOffset.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfOffset.gridx = 2;
		gbc_tfOffset.gridy = 4;
		add(tfOffset, gbc_tfOffset);
		tfOffset.setColumns(10);
	}

	private void addEventHandlers() {
		tfSmallerRadius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backend.smRadius = ParseInput.parseLength(tfSmallerRadius.getText());
				tfSmallerRadius.setText(backend.smRadius + "");
			}
		});
		tfSmRadiusStrLen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backend.smRadiusStrLen = ParseInput.parseLength(tfSmRadiusStrLen.getText());
				tfSmRadiusStrLen.setText(backend.smRadius + "");
			}
		});
		tfGreaterRadius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backend.grRadius = ParseInput.parseLength(tfGreaterRadius.getText());
				tfGreaterRadius.setText(backend.grRadius + "");
			}
		});
		tfGrRadiusStrLen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backend.grRadiusStrLen = ParseInput.parseLength(tfGrRadiusStrLen.getText());
				tfGrRadiusStrLen.setText(backend.grRadius + "");
			}
		});
		tfOffset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backend.offset = ParseInput.parseLength(tfOffset.getText());
				tfOffset.setText(backend.offset + "");
			}
		});
	}

}
