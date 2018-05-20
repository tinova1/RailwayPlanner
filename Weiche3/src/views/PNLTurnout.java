package views;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class PNLTurnout extends JPanel {

	/**
	 * Create the panel.
	 */
	private JScrollPane scrollPane = new JScrollPane();
	JPanel[] panels = new JPanel[4];

	PNLTurnout(final GUIBackend backend, final Dimension dimension) {

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateLayout();
			}
		});

		setSize(dimension);
		setLayout(null);

		add(scrollPane);

		panels[0] = new PNLTurnoutGeneral(backend, this);
		panels[0].setVisible(true);
		panels[1] = new PNLTurnoutStraight(backend);
		panels[1].setVisible(true);
		panels[2] = new PNLTurnoutCurved(backend);
		panels[2].setVisible(false);
		panels[3] = new PNLTurnoutWye(backend);
		panels[3].setVisible(false);
		for (JPanel panel : panels) {
			add(panel);
		}
		updateLayout();
	}

	void updateLayout() {
		int dynHeight = 0;
		int width = (int) getSize().getWidth();
		for (JPanel panel : panels) {
			if (panel.isVisible()) {
				int preferredHeight = (int) panel.getPreferredSize().getHeight();
				panel.setBounds(0, dynHeight, width, preferredHeight);
				dynHeight += preferredHeight;
			}
		}
	}

}