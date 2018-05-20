package views;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import guiTransfer.ParseInput;
import utils.Side;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import views.GUIBackend.TurnoutTypes;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class PNLTurnoutGeneral extends JPanel {
	private GUIBackend backend;
	private PNLTurnout pnlTurnout;

	private JTextField tfGauge;
	private final ButtonGroup btnGrpHandiness = new ButtonGroup();
	private JComboBox comboBox;
	JRadioButton rdbtnRighthand;
	JRadioButton rdbtnLefthand;

	/**
	 * Line 1: gauge Line 2: drop-down menu with selection of turnout type
	 */
	PNLTurnoutGeneral(final GUIBackend backend, final PNLTurnout pnlTurnout) {
		this.backend = backend;
		this.pnlTurnout = pnlTurnout;
		setPreferredSize(new Dimension(241, 117));
		setBorder(new TitledBorder(null, "General", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		initComponents();
		addEventHandlers();
	}

	private void initComponents() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 15, 87, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblGaugemm = new JLabel("gauge [mm]");
		GridBagConstraints gbc_lblGaugemm = new GridBagConstraints();
		gbc_lblGaugemm.insets = new Insets(0, 0, 5, 5);
		gbc_lblGaugemm.anchor = GridBagConstraints.EAST;
		gbc_lblGaugemm.gridx = 1;
		gbc_lblGaugemm.gridy = 0;
		add(lblGaugemm, gbc_lblGaugemm);

		tfGauge = new JTextField();

		GridBagConstraints gbc_tfGauge = new GridBagConstraints();
		gbc_tfGauge.insets = new Insets(0, 0, 5, 0);
		gbc_tfGauge.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfGauge.gridx = 2;
		gbc_tfGauge.gridy = 0;
		add(tfGauge, gbc_tfGauge);
		tfGauge.setColumns(10);

		JLabel lblTurnoutType = new JLabel("turnout type");
		GridBagConstraints gbc_lblTurnoutType = new GridBagConstraints();
		gbc_lblTurnoutType.anchor = GridBagConstraints.EAST;
		gbc_lblTurnoutType.insets = new Insets(0, 0, 5, 5);
		gbc_lblTurnoutType.gridx = 1;
		gbc_lblTurnoutType.gridy = 1;
		add(lblTurnoutType, gbc_lblTurnoutType);

		comboBox = new JComboBox();

		comboBox.setModel(new DefaultComboBoxModel(TurnoutTypes.values()));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		add(comboBox, gbc_comboBox);

		rdbtnRighthand = new JRadioButton("right-hand");
		rdbtnRighthand.setSelected(true);
		btnGrpHandiness.add(rdbtnRighthand);
		GridBagConstraints gbc_rdbtnRighthand = new GridBagConstraints();
		gbc_rdbtnRighthand.anchor = GridBagConstraints.WEST;
		gbc_rdbtnRighthand.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnRighthand.gridx = 2;
		gbc_rdbtnRighthand.gridy = 2;
		add(rdbtnRighthand, gbc_rdbtnRighthand);

		rdbtnLefthand = new JRadioButton("left-hand");
		btnGrpHandiness.add(rdbtnLefthand);
		GridBagConstraints gbc_rdbtnLefthand = new GridBagConstraints();
		gbc_rdbtnLefthand.anchor = GridBagConstraints.WEST;
		gbc_rdbtnLefthand.gridx = 2;
		gbc_rdbtnLefthand.gridy = 3;
		add(rdbtnLefthand, gbc_rdbtnLefthand);
	}

	private void addEventHandlers() {
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backend.turnoutType = (TurnoutTypes) comboBox.getSelectedItem();
				JPanel[] panels = pnlTurnout.panels;
				for (int i = 1; i < 4; i++) { // make all panels invisible
					panels[i].setVisible(false);
				}
				// make the panel that is related to the selected item visible
				panels[comboBox.getSelectedIndex() + 1].setVisible(true);
				// make Radio Buttons inactive when selected "Wye"
				final boolean enabled = comboBox.getSelectedItem() != TurnoutTypes.WYE;
				rdbtnLefthand.setEnabled(enabled);
				rdbtnRighthand.setEnabled(enabled);

				pnlTurnout.updateLayout();
			}
		});

		tfGauge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backend.gauge = ParseInput.parseLength(tfGauge.getText());
				tfGauge.setText(backend.gauge + "");
			}
		});

		rdbtnRighthand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnRighthand.isSelected())
					backend.handiness = Side.RIGHT;
			}
		});

		rdbtnLefthand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnLefthand.isSelected())
					backend.handiness = Side.LEFT;
			}
		});
	}
}
