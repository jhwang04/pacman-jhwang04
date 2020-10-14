package jhwang04.pacman;

import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;

public class Pacman {

	public static void main(String[] args) {
		PacmanApplet applet = new PacmanApplet();
		
		PSurfaceAWT surf = (PSurfaceAWT) applet.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();
		window.setSize(500, 500);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setVisible(true);
		canvas.requestFocus();
	}

}
