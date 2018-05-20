package dddEngine;

import java.awt.Dimension;

import javax.swing.JFrame;

public class DDDFrame extends JFrame {
	public Screen ScreenObject = new Screen();

	public DDDFrame() {
		add(ScreenObject);
		setUndecorated(false);
		setSize(new Dimension(800, 600));
		setVisible(true);
	}

	public static void main(String[] args) {

	}
}
