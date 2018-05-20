package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FrameTurnout extends JFrame {

	
	
	public static void main(String[] args) {
		
	}
	
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FrameTurnout(GUIBackend backend) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0,0,0,0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	
		JPanel panel = new PNLTurnout(backend,getSize());
		panel.setVisible(true);
		contentPane.add(panel, BorderLayout.CENTER);
	}

}
