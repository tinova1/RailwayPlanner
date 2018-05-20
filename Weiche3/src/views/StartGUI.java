package views;

import java.awt.EventQueue;

class StartGUI {
	private static GUIBackend backend=new GUIBackend();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameTurnout frame = new FrameTurnout(backend);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
