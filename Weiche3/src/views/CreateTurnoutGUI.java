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

import guiTransfer.ParseInput;

import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

public class CreateTurnoutGUI extends JFrame {

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
	private JToggleButton tglbtnLockApproAngle;
	private JToggleButton tglbtnLockApproLength;
	private JToggleButton tglbtnLockApproRadius;

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
		setBounds(100, 100, 450, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE));

		JPanel tabGeo = new JPanel();
		tabbedPane.addTab("Geometry", null, tabGeo, null);
		tabbedPane.setEnabledAt(0, true);

		JLabel lblRightTrackRadius = new JLabel("right track radius");

		JLabel lblLeftTrackRadius = new JLabel("left track radius");

		JLabel lblRightTrackAngle = new JLabel("right track angle");

		JLabel lblLeftTrackAngle = new JLabel("left track angle");

		JLabel lblRightTrackStraight = new JLabel("right track straight length");

		JLabel lblLeftTrackStraight = new JLabel("left track straight length");

		tfRightRadius = new JTextField();
		tfRightRadius.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRightRadius.setColumns(10);

		tfRightAngle = new JTextField();
		tfRightAngle.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRightAngle.setColumns(10);

		tfRightStrLength = new JTextField();
		tfRightStrLength.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRightStrLength.setColumns(10);

		tfLeftRadius = new JTextField();
		tfLeftRadius.setHorizontalAlignment(SwingConstants.RIGHT);
		tfLeftRadius.setColumns(10);

		tfLeftAngle = new JTextField();
		tfLeftAngle.setHorizontalAlignment(SwingConstants.RIGHT);
		tfLeftAngle.setColumns(10);

		tfLeftStrLength = new JTextField();
		tfLeftStrLength.setHorizontalAlignment(SwingConstants.RIGHT);
		tfLeftStrLength.setColumns(10);

		JLabel lblApproachRadius = new JLabel("approach radius");

		tfApproRadius = new JTextField();
		tfApproRadius.setHorizontalAlignment(SwingConstants.RIGHT);
		tfApproRadius.setColumns(10);

		tfApproLength = new JTextField();
		tfApproLength.setHorizontalAlignment(SwingConstants.RIGHT);
		tfApproLength.setColumns(10);

		tfApproAngle = new JTextField();
		tfApproAngle.setHorizontalAlignment(SwingConstants.RIGHT);
		tfApproAngle.setColumns(10);

		JLabel lblApproachLength = new JLabel("approach length");

		JLabel lblApproachAngle = new JLabel("approach angle");

		tglbtnLockApproLength = new JToggleButton("");
		tglbtnLockApproLength.setIcon(new ImageIcon(CreateTurnoutGUI.class.getResource("/resources/lockUnocked.png")));
		tglbtnLockApproLength
				.setSelectedIcon(new ImageIcon(CreateTurnoutGUI.class.getResource("/resources/lockLocked.png")));
		tglbtnLockApproLength.setFocusable(false);

		tglbtnLockApproRadius = new JToggleButton("");
		tglbtnLockApproRadius
				.setSelectedIcon(new ImageIcon(CreateTurnoutGUI.class.getResource("/resources/lockLocked.png")));
		tglbtnLockApproRadius.setIcon(new ImageIcon(CreateTurnoutGUI.class.getResource("/resources/lockUnocked.png")));
		tglbtnLockApproRadius.setFocusable(false);
		
		tglbtnLockApproAngle = new JToggleButton("");
		tglbtnLockApproAngle
				.setSelectedIcon(new ImageIcon(CreateTurnoutGUI.class.getResource("/resources/lockLocked.png")));
		tglbtnLockApproAngle.setIcon(new ImageIcon(CreateTurnoutGUI.class.getResource("/resources/lockUnocked.png")));
		tglbtnLockApproRadius.setFocusable(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_tabGeo = new GroupLayout(tabGeo);
		gl_tabGeo.setHorizontalGroup(
			gl_tabGeo.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tabGeo.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tabGeo.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabGeo.createSequentialGroup()
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRightTrackRadius)
								.addComponent(lblRightTrackAngle)
								.addComponent(lblRightTrackStraight)
								.addComponent(lblLeftTrackRadius)
								.addComponent(lblLeftTrackAngle)
								.addComponent(lblLeftTrackStraight))
							.addGap(17))
						.addGroup(gl_tabGeo.createSequentialGroup()
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_tabGeo.createSequentialGroup()
									.addComponent(lblApproachLength)
									.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
									.addComponent(tglbtnLockApproLength, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_tabGeo.createSequentialGroup()
									.addComponent(lblApproachRadius)
									.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
									.addComponent(tglbtnLockApproRadius, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_tabGeo.createSequentialGroup()
									.addComponent(lblApproachAngle)
									.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
									.addComponent(tglbtnLockApproAngle, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_tabGeo.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, gl_tabGeo.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfApproRadius))
						.addComponent(tfApproLength, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
						.addComponent(tfLeftStrLength, Alignment.LEADING)
						.addComponent(tfLeftAngle, Alignment.LEADING)
						.addComponent(tfLeftRadius, Alignment.LEADING)
						.addComponent(tfRightStrLength)
						.addComponent(tfRightAngle)
						.addComponent(tfRightRadius, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
						.addComponent(tfApproAngle, Alignment.LEADING))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addGap(486))
		);
		gl_tabGeo.setVerticalGroup(
			gl_tabGeo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabGeo.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tabGeo.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_tabGeo.createSequentialGroup()
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblRightTrackRadius)
								.addComponent(tfRightRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblRightTrackAngle)
								.addComponent(tfRightAngle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfRightStrLength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblRightTrackStraight))
							.addGap(18)
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblLeftTrackRadius)
								.addComponent(tfLeftRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfLeftAngle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLeftTrackAngle))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfLeftStrLength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLeftTrackStraight))
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_tabGeo.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_tabGeo.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblApproachRadius)
										.addComponent(tglbtnLockApproRadius, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_tabGeo.createSequentialGroup()
									.addGap(18)
									.addComponent(tfApproRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_tabGeo.createParallelGroup(Alignment.BASELINE)
									.addComponent(tfApproLength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblApproachLength))
								.addComponent(tglbtnLockApproLength, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_tabGeo.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfApproAngle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblApproachAngle)
								.addComponent(tglbtnLockApproAngle, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
				JTextPane txtpnLoremIpsumDolor = new JTextPane();
				scrollPane.setRowHeaderView(txtpnLoremIpsumDolor);
				txtpnLoremIpsumDolor.setText(
						"Enter positive radius values for a left turn, positive for right turn. \r\nEnter 0 as radius for straight track");
		tabGeo.setLayout(gl_tabGeo);

		JPanel tabRail = new JPanel();
		tabbedPane.addTab("Rail", null, tabRail, null);
		tabbedPane.setEnabledAt(1, true);
		GroupLayout groupLayout = new GroupLayout(tabRail);
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 419, Short.MAX_VALUE));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 223, Short.MAX_VALUE));
		tabRail.setLayout(groupLayout);

		JPanel tabTie = new JPanel();
		tabbedPane.addTab("Ties", null, tabTie, null);
		tabbedPane.setEnabledAt(2, true);

		JPanel tabFile = new JPanel();
		tabbedPane.addTab("File", null, tabFile, null);

		JButton btnCreate = new JButton("CREATE");
		tabFile.add(btnCreate);
		tabbedPane.setEnabledAt(3, true);
		contentPane.setLayout(gl_contentPane);
	}

	private void createEvents() {
		tglbtnLockApproRadius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tglbtnLockApproRadius.isSelected()) {
					tglbtnLockApproLength.setSelected(false);
					tglbtnLockApproAngle.setSelected(false);
				} else { // unlocked
					tglbtnLockApproLength.setSelected(true);
				}
			}
		});
		tglbtnLockApproRadius.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				final boolean enabled = !tglbtnLockApproRadius.isSelected();
				tfApproRadius.setEnabled(enabled);
			}
		});
		tglbtnLockApproLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tglbtnLockApproLength.isSelected()) {
					tglbtnLockApproRadius.setSelected(false);
					tglbtnLockApproAngle.setSelected(false);
				} else { // unlocked
					tglbtnLockApproAngle.setSelected(true);
				}
			}
		});
		tglbtnLockApproLength.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				final boolean enabled = !tglbtnLockApproLength.isSelected();
				tfApproLength.setEnabled(enabled);
			}
		});
		tglbtnLockApproAngle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tglbtnLockApproAngle.isSelected()) {
					tglbtnLockApproRadius.setSelected(false);
					tglbtnLockApproLength.setSelected(false);
				} else { // unlocked
					tglbtnLockApproLength.setSelected(true);
				}
			}
		});
		tglbtnLockApproAngle.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				final boolean enabled = !tglbtnLockApproAngle.isSelected();
				tfApproAngle.setEnabled(enabled);
			}
		});
		tfRightRadius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfRightRadius.getText();
				tfRightRadius.setText(ParseInput.parseLength(text)+"mm");
			}
		});
		tfRightStrLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfRightStrLength.getText();
				tfRightStrLength.setText(ParseInput.parseLength(text)+"mm");
			}
		});
		tfRightAngle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfRightAngle.getText();
				tfRightAngle.setText(ParseInput.parseAngle(text)+"°");
			}
		});
		tfLeftRadius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfLeftRadius.getText();
				tfLeftRadius.setText(ParseInput.parseLength(text)+"mm");
			}
		});
		tfLeftStrLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfLeftStrLength.getText();
				tfLeftStrLength.setText(ParseInput.parseLength(text)+"mm");
			}
		});
		tfLeftAngle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String text = tfLeftAngle.getText();
				tfLeftAngle.setText(ParseInput.parseAngle(text)+"°");
			}
		});
	}
}
