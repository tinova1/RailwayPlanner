package views;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.border.TitledBorder;

import guiTransfer.ParseInput;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PNLTurnoutStraight extends JPanel {
	private GUIBackend backend;
	private JTextField tfRadius;
	private JTextField tfLength;
	private JTextField tfStrLength;
	private JTextField tfAngle;

	/**
	 * Create the panel.
	 */
	public PNLTurnoutStraight(final GUIBackend backen) {
		this.backend = backend;
		setPreferredSize(new Dimension(241, 120));
		setBorder(new TitledBorder(null, "Straight Turnout", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		initComponents();
		addEventHandlers();
	}

	private void initComponents() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblAngle = new JLabel("angle [°]");
		GridBagConstraints gbc_lblAngle = new GridBagConstraints();
		gbc_lblAngle.anchor = GridBagConstraints.EAST;
		gbc_lblAngle.insets = new Insets(0, 0, 5, 5);
		gbc_lblAngle.gridx = 1;
		gbc_lblAngle.gridy = 0;
		add(lblAngle, gbc_lblAngle);

		tfAngle = new JTextField();

		GridBagConstraints gbc_tfAngle = new GridBagConstraints();
		gbc_tfAngle.insets = new Insets(0, 0, 5, 0);
		gbc_tfAngle.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfAngle.gridx = 2;
		gbc_tfAngle.gridy = 0;
		add(tfAngle, gbc_tfAngle);
		tfAngle.setColumns(10);

		JLabel lblRadiusmm = new JLabel("radius [mm]");
		GridBagConstraints gbc_lblRadiusmm = new GridBagConstraints();
		gbc_lblRadiusmm.anchor = GridBagConstraints.EAST;
		gbc_lblRadiusmm.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadiusmm.gridx = 1;
		gbc_lblRadiusmm.gridy = 1;
		add(lblRadiusmm, gbc_lblRadiusmm);

		tfRadius = new JTextField();

		GridBagConstraints gbc_tfRadius = new GridBagConstraints();
		gbc_tfRadius.insets = new Insets(0, 0, 5, 0);
		gbc_tfRadius.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfRadius.gridx = 2;
		gbc_tfRadius.gridy = 1;
		add(tfRadius, gbc_tfRadius);
		tfRadius.setColumns(10);

		JLabel lblLengthmm = new JLabel("length [mm]");
		GridBagConstraints gbc_lblLengthmm = new GridBagConstraints();
		gbc_lblLengthmm.anchor = GridBagConstraints.EAST;
		gbc_lblLengthmm.insets = new Insets(0, 0, 5, 5);
		gbc_lblLengthmm.gridx = 1;
		gbc_lblLengthmm.gridy = 2;
		add(lblLengthmm, gbc_lblLengthmm);

		tfLength = new JTextField();

		GridBagConstraints gbc_tfLength = new GridBagConstraints();
		gbc_tfLength.insets = new Insets(0, 0, 5, 0);
		gbc_tfLength.anchor = GridBagConstraints.NORTH;
		gbc_tfLength.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfLength.gridx = 2;
		gbc_tfLength.gridy = 2;
		add(tfLength, gbc_tfLength);
		tfLength.setColumns(10);

		JLabel lblStraightLengthmm = new JLabel("straight length [mm]");
		GridBagConstraints gbc_lblStraightLengthmm = new GridBagConstraints();
		gbc_lblStraightLengthmm.anchor = GridBagConstraints.EAST;
		gbc_lblStraightLengthmm.insets = new Insets(0, 0, 0, 5);
		gbc_lblStraightLengthmm.gridx = 1;
		gbc_lblStraightLengthmm.gridy = 3;
		add(lblStraightLengthmm, gbc_lblStraightLengthmm);

		tfStrLength = new JTextField();

		GridBagConstraints gbc_tfStrLength = new GridBagConstraints();
		gbc_tfStrLength.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStrLength.gridx = 2;
		gbc_tfStrLength.gridy = 3;
		add(tfStrLength, gbc_tfStrLength);
		tfStrLength.setColumns(10);
	}

	private void addEventHandlers() {
		tfAngle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backend.angle = ParseInput.parseAngle(tfAngle.getText());
				tfAngle.setText(backend.angle + "");
			}
		});

		tfRadius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backend.radius = ParseInput.parseLength(tfRadius.getText());
				tfRadius.setText(backend.radius + "");
			}
		});

		tfLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backend.length = ParseInput.parseLength(tfLength.getText());
				tfLength.setText(backend.length + "");
			}
		});

		tfStrLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backend.strLength = ParseInput.parseLength(tfStrLength.getText());
				tfStrLength.setText(backend.strLength + "");
			}
		});
	}
}
