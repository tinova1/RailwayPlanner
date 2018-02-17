package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.event.ChangeListener;

import common.components.Rail;
import common.geometry.Canvas;
import common.io.Export_obj;
import common.io.Export_svg;
import common.model.railway.RW_Path;
import common.model.railway.Railway;
import common.vectorMath.RotDir;
import common.vectorMath.objects2D.Arc;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Line;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;
import guiTransfer.CreateTurnout;
import guiTransfer.ParseInput;

import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.beans.PropertyChangeEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CreateTurnoutGUI extends JFrame {

	private final int tfWidth = 120;

	private JPanel contentPane;
	private JTextField tfRightRadius;
	private JTextField tfRightAngle;
	private JTextField tfRightStrLength;
	private JTextField tfLeftRadius;
	private JTextField tfLeftAngle;
	private JTextField tfLeftStrLength;
	private JTextField tfApproRadius;
	private JTextField tfApproLength;
	private JTextField tfApproAngle;
	private JTextField tfTieWidth;
	private JTextField tfRailHead;
	private JTextField tfRailFoot;
	private JTextField tfMaxTieLength;
	private JTextField tfTieLength;
	private JTextField tfTieHeight;
	private JButton btnCreate;
	private JCheckBox chckbxExportsvg;
	private JCheckBox chckbxExportobj;
	private JLabel lblFlange;
	private JLabel lblGuideRailLength;
	private JLabel lblKfrogLengthmm;
	private JLabel lblVfrogLengthmm;
	private JTextField tfFlange;
	private JTextField tfGuide;
	private JTextField tfVFrog;
	private JTextField tfKFrog;
	private JLabel lblGauge;
	private JTextField tfGauge;
	private JComboBox cbApproType;
	private JLabel lblApproRadius;
	private JLabel lblApproAngle;
	private JTextField tfLeftOffs;
	private JTextField tfRightOffs;
	private JComboBox cbTurnoutGeo;
	private JLabel lblRightRadius;
	private JLabel lblRightAngle;
	private JLabel lblRightStrLength;
	private JLabel lblRightOffs;
	private JLabel lblLeftRadius;
	private JLabel lblLeftAngle;
	private JLabel lblLeftStrLength;
	private JLabel lblLeftOffs;
	private JLabel lblApproLength;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateTurnoutGUI frame = new CreateTurnoutGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateTurnoutGUI() {

		initComponents();
		createEvents();

	}

	private void initComponents() {
		setTitle("Turnout Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 503);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		JPanel tabGeo = new JPanel();
		tabbedPane.addTab("Geometry", null, tabGeo, null);
		tabbedPane.setEnabledAt(0, true);

		JPanel pnlAppro = new JPanel();
		pnlAppro.setBorder(new TitledBorder(null, "Approach", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel pnlTurnoutGeo = new JPanel();
		pnlTurnoutGeo.setBorder(
				new TitledBorder(null, "Turnout Geometry", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_tabGeo = new GroupLayout(tabGeo);
		gl_tabGeo.setHorizontalGroup(gl_tabGeo.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tabGeo.createSequentialGroup().addContainerGap()
						.addGroup(gl_tabGeo.createParallelGroup(Alignment.TRAILING)
								.addComponent(pnlAppro, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 399,
										Short.MAX_VALUE)
								.addComponent(pnlTurnoutGeo, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 399,
										Short.MAX_VALUE))
						.addContainerGap()));
		gl_tabGeo.setVerticalGroup(gl_tabGeo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabGeo.createSequentialGroup().addContainerGap()
						.addComponent(pnlTurnoutGeo, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pnlAppro, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
						.addGap(31)));

		lblLeftStrLength = new JLabel("left track straight length [mm]");

		tfLeftStrLength = new JTextField();
		tfLeftStrLength.setText("100.0");
		tfLeftStrLength.setHorizontalAlignment(SwingConstants.RIGHT);
		tfLeftStrLength.setColumns(10);

		tfRightRadius = new JTextField();
		tfRightRadius.setText("200.0");
		tfRightRadius.setEnabled(false);
		tfRightRadius.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRightRadius.setColumns(10);

		lblRightRadius = new JLabel("right track radius [mm]");
		lblRightRadius.setEnabled(false);

		cbTurnoutGeo = new JComboBox();

		cbTurnoutGeo.setModel(new DefaultComboBoxModel(new String[] { "left-hand straight turnout",
				"right-hand straight turnout", "left-hand curved turnout", "right-hand curved turnout", "wye" }));

		lblRightAngle = new JLabel("right track angle [°]");
		lblRightAngle.setEnabled(false);

		tfRightAngle = new JTextField();
		tfRightAngle.setText("20.0");
		tfRightAngle.setEnabled(false);
		tfRightAngle.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRightAngle.setColumns(10);

		lblRightOffs = new JLabel("right track offset [mm]");
		lblRightOffs.setEnabled(false);

		tfRightOffs = new JTextField();
		tfRightOffs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRightOffs.setText("0.0");
		tfRightOffs.setEnabled(false);
		tfRightOffs.setColumns(10);

		lblRightStrLength = new JLabel("right track straight length [mm]");

		tfRightStrLength = new JTextField();
		tfRightStrLength.setText("100.0");
		tfRightStrLength.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRightStrLength.setColumns(10);

		lblLeftRadius = new JLabel("left track radius [mm]");

		tfLeftRadius = new JTextField();
		tfLeftRadius.setText("300.0");
		tfLeftRadius.setHorizontalAlignment(SwingConstants.RIGHT);
		tfLeftRadius.setColumns(10);

		lblLeftAngle = new JLabel("left track angle [°]");

		tfLeftAngle = new JTextField();
		tfLeftAngle.setText("15.0");
		tfLeftAngle.setHorizontalAlignment(SwingConstants.RIGHT);
		tfLeftAngle.setColumns(10);

		lblLeftOffs = new JLabel("left track offset [mm]");
		lblLeftOffs.setEnabled(false);

		tfLeftOffs = new JTextField();
		tfLeftOffs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfLeftOffs.setText("0.0");
		tfLeftOffs.setEnabled(false);
		tfLeftOffs.setColumns(10);

		JCheckBox chckbxMirror = new JCheckBox("mirror");
		GroupLayout gl_pnlTurnoutGeo = new GroupLayout(pnlTurnoutGeo);
		gl_pnlTurnoutGeo.setHorizontalGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addGap(6).addGroup(gl_pnlTurnoutGeo
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addComponent(lblRightAngle).addGap(88)
								.addComponent(tfRightAngle, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
						.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addComponent(lblRightStrLength).addGap(33)
								.addComponent(tfRightStrLength, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
						.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addComponent(lblRightOffs).addGap(74)
								.addComponent(tfRightOffs, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
						.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addComponent(lblLeftRadius).addGap(80)
								.addComponent(tfLeftRadius, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
						.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addComponent(lblLeftAngle).addGap(94)
								.addComponent(tfLeftAngle, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
						.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addComponent(lblLeftStrLength).addGap(39)
								.addComponent(tfLeftStrLength, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
						.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addComponent(lblLeftOffs).addGap(80)
								.addComponent(tfLeftOffs, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
						.addGroup(gl_pnlTurnoutGeo.createSequentialGroup()
								.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
										.addComponent(lblRightRadius).addComponent(cbTurnoutGeo,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGap(28)
								.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
										.addComponent(chckbxMirror, GroupLayout.PREFERRED_SIZE, 97,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(tfRightRadius, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))))
						.addContainerGap()));
		gl_pnlTurnoutGeo.setVerticalGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addGap(5)
						.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbTurnoutGeo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(chckbxMirror))
						.addGap(2)
						.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_pnlTurnoutGeo.createSequentialGroup().addGap(9).addComponent(lblRightRadius))
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addGap(6).addComponent(tfRightRadius,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_pnlTurnoutGeo.createSequentialGroup().addGap(9).addComponent(lblRightAngle))
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(tfRightAngle,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addGap(9)
										.addComponent(lblRightStrLength))
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(tfRightStrLength,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addGap(8).addComponent(lblRightOffs))
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addGap(5).addComponent(tfRightOffs,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_pnlTurnoutGeo.createSequentialGroup().addGap(29).addComponent(lblLeftRadius))
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addGap(26).addComponent(tfLeftRadius,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addGap(9).addComponent(lblLeftAngle))
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(tfLeftAngle,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addGap(9)
										.addComponent(lblLeftStrLength))
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(tfLeftStrLength,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlTurnoutGeo.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup().addGap(9).addComponent(lblLeftOffs))
								.addGroup(gl_pnlTurnoutGeo.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(tfLeftOffs,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		pnlTurnoutGeo.setLayout(gl_pnlTurnoutGeo);

		tfApproAngle = new JTextField();
		tfApproAngle.setBounds(191, 76, 134, 20);
		tfApproAngle.setText("5.0");
		tfApproAngle.setHorizontalAlignment(SwingConstants.RIGHT);
		tfApproAngle.setColumns(10);

		lblApproAngle = new JLabel("approach angle [°]");
		lblApproAngle.setBounds(10, 79, 90, 14);

		lblApproLength = new JLabel("approach length [mm]");
		lblApproLength.setBounds(9, 108, 105, 14);

		tfApproLength = new JTextField();
		tfApproLength.setBounds(191, 105, 134, 20);
		tfApproLength.setText("10.0");
		tfApproLength.setEnabled(false);
		tfApproLength.setHorizontalAlignment(SwingConstants.RIGHT);
		tfApproLength.setColumns(10);

		lblApproRadius = new JLabel("approach radius [mm]");
		lblApproRadius.setBounds(10, 54, 104, 14);

		tfApproRadius = new JTextField();
		tfApproRadius.setBounds(191, 51, 134, 20);
		tfApproRadius.setText("200.0");
		tfApproRadius.setHorizontalAlignment(SwingConstants.RIGHT);
		tfApproRadius.setColumns(10);

		cbApproType = new JComboBox();
		cbApproType.setBounds(10, 23, 199, 20);

		cbApproType.setModel(new DefaultComboBoxModel(
				new String[] { "straight approach", "left curve approach", "right curve approach" }));
		pnlAppro.setLayout(null);
		pnlAppro.add(lblApproRadius);
		pnlAppro.add(lblApproAngle);
		pnlAppro.add(lblApproLength);
		pnlAppro.add(tfApproLength);
		pnlAppro.add(tfApproAngle);
		pnlAppro.add(tfApproRadius);
		pnlAppro.add(cbApproType);
		tabGeo.setLayout(gl_tabGeo);

		JPanel tabTie = new JPanel();
		tabbedPane.addTab("Ties", null, tabTie, null);

		JLabel lblTieLength = new JLabel("tie length [mm]");
		lblTieLength.setBounds(10, 14, 72, 14);

		JLabel lblMaximumTieLength = new JLabel("maximum tie length [mm]");
		lblMaximumTieLength.setBounds(10, 45, 119, 14);

		JLabel lblTieWidth = new JLabel("tie width [mm]");
		lblTieWidth.setBounds(10, 76, 68, 14);

		JLabel lblTieHeight = new JLabel("tie height [mm]");
		lblTieHeight.setBounds(10, 107, 72, 14);

		tfMaxTieLength = new JTextField();
		tfMaxTieLength.setBounds(139, 42, 107, 20);
		tfMaxTieLength.setText("40.0");
		tfMaxTieLength.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMaxTieLength.setColumns(10);

		tfTieLength = new JTextField();
		tfTieLength.setBounds(139, 11, 107, 20);
		tfTieLength.setText("20.0");
		tfTieLength.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTieLength.setColumns(10);

		tfTieWidth = new JTextField();
		tfTieWidth.setBounds(139, 73, 107, 20);
		tfTieWidth.setText("2.0");
		tfTieWidth.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTieWidth.setColumns(10);

		tfTieHeight = new JTextField();
		tfTieHeight.setBounds(139, 104, 107, 20);
		tfTieHeight.setText("1.0");
		tfTieHeight.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTieHeight.setColumns(10);
		tabTie.setLayout(null);
		tabTie.add(lblMaximumTieLength);
		tabTie.add(lblTieLength);
		tabTie.add(lblTieWidth);
		tabTie.add(lblTieHeight);
		tabTie.add(tfTieHeight);
		tabTie.add(tfTieWidth);
		tabTie.add(tfTieLength);
		tabTie.add(tfMaxTieLength);

		JLabel lblTieDist = new JLabel("tie distance [mm]");
		lblTieDist.setBounds(10, 132, 92, 14);
		tabTie.add(lblTieDist);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setText("5.0");
		textField.setBounds(139, 129, 107, 20);
		tabTie.add(textField);
		textField.setColumns(10);
		tabbedPane.setEnabledAt(1, true);

		JPanel tabRail = new JPanel();
		tabbedPane.addTab("Rail", null, tabRail, null);
		tabbedPane.setEnabledAt(2, true);
		tabRail.setLayout(null);

		JPanel pnlGauge = new JPanel();
		pnlGauge.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlGauge.setBounds(10, 11, 399, 39);
		tabRail.add(pnlGauge);
		pnlGauge.setLayout(null);

		lblGauge = new JLabel("gauge [mm]");
		lblGauge.setBounds(10, 11, 57, 14);
		pnlGauge.add(lblGauge);

		tfGauge = new JTextField();
		tfGauge.setText("16.5");
		tfGauge.setHorizontalAlignment(SwingConstants.RIGHT);
		tfGauge.setBounds(127, 8, 133, 20);
		pnlGauge.add(tfGauge);
		tfGauge.setColumns(10);

		JPanel pnlProfile = new JPanel();
		pnlProfile
				.setBorder(new TitledBorder(null, "Rail Profile", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlProfile.setBounds(10, 61, 399, 136);
		tabRail.add(pnlProfile);
		pnlProfile.setLayout(null);

		tfRailHead = new JTextField();
		tfRailHead.setText("0.2");
		tfRailHead.setBounds(119, 42, 133, 20);
		pnlProfile.add(tfRailHead);
		tfRailHead.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRailHead.setColumns(10);

		JLabel lblHeadWidth = new JLabel("head width [mm]");
		lblHeadWidth.setBounds(10, 45, 80, 14);
		pnlProfile.add(lblHeadWidth);

		JLabel lblFootWidth = new JLabel("foot width [mm]");
		lblFootWidth.setBounds(10, 71, 76, 14);
		pnlProfile.add(lblFootWidth);

		tfRailFoot = new JTextField();
		tfRailFoot.setText("0.5");
		tfRailFoot.setBounds(119, 68, 133, 20);
		pnlProfile.add(tfRailFoot);
		tfRailFoot.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRailFoot.setColumns(10);

		tfFlange = new JTextField();
		tfFlange.setHorizontalAlignment(SwingConstants.RIGHT);
		tfFlange.setText("1.0");
		tfFlange.setBounds(119, 99, 133, 20);
		pnlProfile.add(tfFlange);
		tfFlange.setColumns(10);

		lblFlange = new JLabel("flange width [mm]");
		lblFlange.setBounds(10, 102, 86, 14);
		pnlProfile.add(lblFlange);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(119, 11, 133, 20);
		pnlProfile.add(comboBox);

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "additional lengths", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 208, 399, 187);
		tabRail.add(panel);

		tfGuide = new JTextField();
		tfGuide.setHorizontalAlignment(SwingConstants.RIGHT);
		tfGuide.setText("20.0");
		tfGuide.setColumns(10);

		lblGuideRailLength = new JLabel("guide rail length [mm]");

		lblVfrogLengthmm = new JLabel("V-Frog length [mm]");

		tfVFrog = new JTextField();
		tfVFrog.setHorizontalAlignment(SwingConstants.RIGHT);
		tfVFrog.setText("10.0");
		tfVFrog.setColumns(10);

		tfKFrog = new JTextField();
		tfKFrog.setHorizontalAlignment(SwingConstants.RIGHT);
		tfKFrog.setText("10.0");
		tfKFrog.setColumns(10);

		lblKfrogLengthmm = new JLabel("K-Frog length [mm]");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(4)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblKfrogLengthmm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGap(18)
								.addComponent(tfKFrog, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup().addComponent(lblVfrogLengthmm)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(tfVFrog, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING,
								gl_panel.createSequentialGroup().addComponent(lblGuideRailLength)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(tfGuide,
												GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(6)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfGuide, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGuideRailLength))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblVfrogLengthmm).addComponent(
						tfVFrog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfKFrog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKfrogLengthmm))
				.addContainerGap(76, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		JPanel tabFile = new JPanel();
		tabbedPane.addTab("File", null, tabFile, null);

		btnCreate = new JButton("CREATE");

		chckbxExportsvg = new JCheckBox("export .svg");
		chckbxExportsvg.setSelected(true);

		chckbxExportobj = new JCheckBox("export .obj");
		GroupLayout gl_tabFile = new GroupLayout(tabFile);
		gl_tabFile.setHorizontalGroup(gl_tabFile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabFile.createSequentialGroup().addContainerGap()
						.addGroup(gl_tabFile.createParallelGroup(Alignment.LEADING).addComponent(chckbxExportobj)
								.addComponent(btnCreate).addComponent(chckbxExportsvg))
						.addContainerGap(312, Short.MAX_VALUE)));
		gl_tabFile.setVerticalGroup(gl_tabFile.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_tabFile.createSequentialGroup().addContainerGap().addComponent(chckbxExportsvg)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chckbxExportobj)
						.addPreferredGap(ComponentPlacement.RELATED, 88, Short.MAX_VALUE).addComponent(btnCreate)
						.addGap(107)));
		tabFile.setLayout(gl_tabFile);
		tabbedPane.setEnabledAt(3, true);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
				GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
				Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 434, Short.MAX_VALUE));
		contentPane.setLayout(gl_contentPane);
	}

	private void createEvents() {
		tfRightRadius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfRightRadius.getText();
				tfRightRadius.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfRightStrLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfRightStrLength.getText();
				tfRightStrLength.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfRightAngle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfRightAngle.getText();
				tfRightAngle.setText(ParseInput.parseAngle(text) + "");
			}
		});
		tfLeftRadius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfLeftRadius.getText();
				tfLeftRadius.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfLeftStrLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfLeftStrLength.getText();
				tfLeftStrLength.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfLeftAngle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfLeftAngle.getText();
				tfLeftAngle.setText(ParseInput.parseAngle(text) + "");
			}
		});
		tfApproRadius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfApproRadius.getText();
				tfApproRadius.setText(ParseInput.parseLength(text) + "");
				final double radius = Double.parseDouble(tfApproRadius.getText());
				if (tfApproLength.isEnabled()) {
					final double length = Double.parseDouble(tfApproLength.getText());
					final double angle = length / radius;
					tfApproAngle.setText(Math.toDegrees(angle) + "");
				} else { // tfApproAngle.isEnabled()
					final double angle = Double.parseDouble(tfApproAngle.getText());
					final double length = Math.toRadians(angle) * radius;
					tfApproLength.setText(length + "");
				}
			}
		});
		tfApproLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfApproLength.getText();
				tfApproLength.setText(ParseInput.parseLength(text) + "");
				final double length = Double.parseDouble(tfApproLength.getText());
				if (tfApproAngle.isEnabled()) {
					final double angle = Double.parseDouble(tfApproAngle.getText());
					final double radius = length / Math.toRadians(angle);
					tfApproRadius.setText(radius + "");
				} else {// tfApproRadius.isEnabled()
					final double radius = Double.parseDouble(tfApproRadius.getText());
					final double angle = length / radius;
					tfApproAngle.setText(Math.toDegrees(angle) + "");
				}
			}
		});
		tfApproAngle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfApproAngle.getText();
				tfApproAngle.setText(ParseInput.parseAngle(text) + "");
				final double angle = Double.parseDouble(tfApproAngle.getText());
				if (tfApproRadius.isEnabled()) {
					final double radius = Double.parseDouble(tfApproRadius.getText());
					final double length = Math.toRadians(angle) * radius;
					tfApproLength.setText(length + "");
				} else {// tfApproLength.isEnabled()
					final double length = Double.parseDouble(tfApproLength.getText());
					final double radius = length / angle;
					tfApproRadius.setText(radius + "");
				}
			}
		});
		tfRailHead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfRailHead.getText();
				tfRailHead.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfRailFoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfRailFoot.getText();
				tfRailFoot.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfFlange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfFlange.getText();
				tfFlange.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfGuide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfGuide.getText();
				tfGuide.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfVFrog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfVFrog.getText();
				tfVFrog.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfKFrog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfKFrog.getText();
				tfKFrog.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfGauge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfGauge.getText();
				tfGauge.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfTieLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfTieLength.getText();
				tfTieLength.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfMaxTieLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfMaxTieLength.getText();
				tfMaxTieLength.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfTieWidth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfTieWidth.getText();
				tfTieWidth.setText(ParseInput.parseLength(text) + "");
			}
		});
		tfTieHeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String text = tfTieHeight.getText();
				tfTieHeight.setText(ParseInput.parseLength(text) + "");
			}
		});

		cbApproType.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				final boolean t = true;
				final boolean f = false;
				if (cbApproType.getSelectedIndex() == 0) {
					lblApproRadius.setEnabled(f);
					tfApproRadius.setEnabled(f);
					lblApproAngle.setEnabled(f);
					tfApproAngle.setEnabled(f);
					lblApproLength.setEnabled(t);
					tfApproLength.setEnabled(t);
				} else {
					lblApproRadius.setEnabled(t);
					tfApproRadius.setEnabled(t);
					lblApproAngle.setEnabled(t);
					tfApproAngle.setEnabled(t);
					lblApproLength.setEnabled(f);
					tfApproLength.setEnabled(f);
				}
			}
		});
		cbTurnoutGeo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final boolean t = true;
				final boolean f = false;
				switch (cbTurnoutGeo.getSelectedIndex()) {
				case 0: // left-hand straight turnout
					lblRightRadius.setEnabled(f);
					tfRightRadius.setEnabled(f);
					lblRightAngle.setEnabled(f);
					tfRightAngle.setEnabled(f);
					lblRightStrLength.setEnabled(t);
					tfRightStrLength.setEnabled(t);
					lblRightOffs.setEnabled(f);
					tfRightOffs.setEnabled(f);
					lblLeftRadius.setEnabled(t);
					tfLeftRadius.setEnabled(t);
					lblLeftAngle.setEnabled(t);
					tfLeftAngle.setEnabled(t);
					lblLeftStrLength.setEnabled(t);
					tfLeftStrLength.setEnabled(t);
					lblLeftOffs.setEnabled(f);
					tfLeftOffs.setEnabled(f);
					break;
				case 1: // right-hand straight turnout
					lblRightRadius.setEnabled(t);
					tfRightRadius.setEnabled(t);
					lblRightAngle.setEnabled(t);
					tfRightAngle.setEnabled(t);
					lblRightStrLength.setEnabled(t);
					tfRightStrLength.setEnabled(t);
					lblRightOffs.setEnabled(f);
					tfRightOffs.setEnabled(f);
					lblLeftRadius.setEnabled(f);
					tfLeftRadius.setEnabled(f);
					lblLeftAngle.setEnabled(f);
					tfLeftAngle.setEnabled(f);
					lblLeftStrLength.setEnabled(t);
					tfLeftStrLength.setEnabled(t);
					lblLeftOffs.setEnabled(f);
					tfLeftOffs.setEnabled(f);
					break;
				case 2: // left-hand curved turnout
					lblRightRadius.setEnabled(t);
					tfRightRadius.setEnabled(t);
					lblRightAngle.setEnabled(t);
					tfRightAngle.setEnabled(t);
					lblRightStrLength.setEnabled(t);
					tfRightStrLength.setEnabled(t);
					lblRightOffs.setEnabled(t);
					tfRightOffs.setEnabled(t);
					lblLeftRadius.setEnabled(t);
					tfLeftRadius.setEnabled(t);
					lblLeftAngle.setEnabled(t);
					tfLeftAngle.setEnabled(t);
					lblLeftStrLength.setEnabled(t);
					tfLeftStrLength.setEnabled(t);
					lblLeftOffs.setEnabled(f);
					tfLeftOffs.setEnabled(f);
					break;
				case 3: // right-hand curved turnout
					lblRightRadius.setEnabled(t);
					tfRightRadius.setEnabled(t);
					lblRightAngle.setEnabled(t);
					tfRightAngle.setEnabled(t);
					lblRightStrLength.setEnabled(t);
					tfRightStrLength.setEnabled(t);
					lblRightOffs.setEnabled(f);
					tfRightOffs.setEnabled(f);
					lblLeftRadius.setEnabled(t);
					tfLeftRadius.setEnabled(t);
					lblLeftAngle.setEnabled(t);
					tfLeftAngle.setEnabled(t);
					lblLeftStrLength.setEnabled(t);
					tfLeftStrLength.setEnabled(t);
					lblLeftOffs.setEnabled(t);
					tfLeftOffs.setEnabled(t);
					break;
				case 4: // wye
					lblRightRadius.setEnabled(t);
					tfRightRadius.setEnabled(t);
					lblRightAngle.setEnabled(t);
					tfRightAngle.setEnabled(t);
					lblRightStrLength.setEnabled(t);
					tfRightStrLength.setEnabled(t);
					lblRightOffs.setEnabled(t);
					tfRightOffs.setEnabled(t);
					lblLeftRadius.setEnabled(t);
					tfLeftRadius.setEnabled(t);
					lblLeftAngle.setEnabled(t);
					tfLeftAngle.setEnabled(t);
					lblLeftStrLength.setEnabled(t);
					tfLeftStrLength.setEnabled(t);
					lblLeftOffs.setEnabled(t);
					tfLeftOffs.setEnabled(t);
					break;
				}
			}
		});
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final boolean svg = chckbxExportsvg.isSelected();
				final boolean obj = chckbxExportobj.isSelected();

				// Geometry
				double gauge = Double.parseDouble(tfGauge.getText());
				double radiusLeft = Double.parseDouble(tfLeftRadius.getText());
				double radiusRight = Double.parseDouble(tfRightRadius.getText());
				double radiusAppro = Double.parseDouble(tfApproRadius.getText());
				double angleLeft = Math.toRadians(Double.parseDouble(tfLeftAngle.getText()));
				double angleRight = Math.toRadians(Double.parseDouble(tfRightAngle.getText()));
				double angleAppro = Math.toRadians(Double.parseDouble(tfApproAngle.getText()));
				double strLeft = Double.parseDouble(tfLeftStrLength.getText());
				double strRight = Double.parseDouble(tfRightStrLength.getText());
				double strAppro = Double.parseDouble(tfApproLength.getText());
				double offsLeft = Double.parseDouble(tfLeftOffs.getText());
				double offsRight = Double.parseDouble(tfRightOffs.getText());

				// Rail
				double foot = Double.parseDouble(tfRailFoot.getText());
				double head = Double.parseDouble(tfRailHead.getText());
				double vFrog = Double.parseDouble(tfVFrog.getText());
				double kFrog = Double.parseDouble(tfKFrog.getText());
				double guide = Double.parseDouble(tfGuide.getText());
				double flange = Double.parseDouble(tfFlange.getText());

				switch (cbTurnoutGeo.getSelectedIndex()) {
				case 0: // straight left-hand turnout
					angleRight = 0.;
					break;
				case 1: // straight right-hand turnout
					angleLeft = 0.;
					radiusRight *= -1.;
					break;
				case 2: // curved left-hand turnout
					offsLeft = 0.;
					break;
				case 3: // curved right-hand turnout
					offsRight = 0.;
					radiusRight *= -1.;
					radiusLeft *= -1.;
					break;
				}
				final Rail rail = new Rail(foot, head, vFrog, kFrog, guide, flange);
				Canvas canvas = new Canvas();
				canvas.addToRailwayList(CreateTurnout.pathTurnout(rail, gauge, radiusLeft, radiusRight, angleLeft,
						angleRight, strLeft, strRight));

				final Path approPath = new Path();
				switch (cbApproType.getSelectedIndex()) {
				case 0: // straight
					approPath.add(new LineSeg(new Point(0, 0), new Point(-strAppro, 0)));
					break;
				case 1: // left
					approPath.add(new Arc(new Point(0, radiusAppro), radiusAppro, -Math.PI / 2.,
							-Math.PI / 2. - angleAppro, RotDir.NEG));
					break;
				case 2: // right
					approPath.add(new Arc(new Point(0, -radiusAppro), radiusAppro, Math.PI / 2.,
							Math.PI / 2. + angleAppro, RotDir.POS));
					break;
				}
				Railway approach;
				approach = new RW_Path(gauge, rail, approPath);
				canvas.addToRailwayList(approach);
				Date date = new Date();
				final String fileName = date.toString();
				if (obj) {
					Export_obj.ausgabe(fileName, canvas);
				}
				if (svg) {
					Export_svg s = new Export_svg(canvas);
					s.ausgabe(fileName);
				}
				System.out.println("Fertig");
			}
		});
	}
}
