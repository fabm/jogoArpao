/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

import javax.swing.JFrame;
import javax.swing.JPanel;

import projectojogoarpao.jogo.fisica.Circulo;

/**
 * 
 * @author Francisco
 */
@SuppressWarnings("serial")
public class PainelTeste extends JFrame implements Runnable {

	private Panel p;

	public PainelTeste() throws HeadlessException {
		setSize(new Dimension(800, 600));
		p = new Panel();
		setContentPane(p);
		Thread thread = new Thread(this);
		thread.start();
	}

	public static void main(String[] args) {
		PainelTeste pt = new PainelTeste();

		pt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pt.setVisible(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				int fs = 60;
				int ms = (int) ((1.0 / fs) * 1000);
				Thread.sleep(ms);
				p.incPA();
				p.x++;
				p.y++;
				repaint();
			} catch (InterruptedException e) {
			}
		}
	}
}

class Panel extends JPanel {
	private int pa = 0;
	public int x = 0, y = 0;

	public void incPA() {
		pa += 1;
		pa = pa < 64 ? pa : 0;
	}

	public void update(Graphics2D g) {
		int h = g.getClipBounds().height;
		int w = g.getClipBounds().width;
		int xn = x+40;
		int yn = y+40;
		Rectangle r = new Rectangle(w-80,h-80,80,80);
		g.draw(r);
		
		g.setColor(Color.blue);
		Polygon p = new Polygon();
		p.xpoints = new int[] { 0, 20, 40 };
		p.ypoints = new int[] { 20, 0, 20 };
		p.npoints = 3;

		g.drawLine(0, 300, 800, 300);
		g.drawLine(400, 0, 400, 600);

		AffineTransform at = new AffineTransform();
		at.translate(xn - 20, yn - 40);
		Shape s = at.createTransformedShape(p);
		Area a = new Area(s);
		for (int i = 0; i < 7; i++) {
			at.setToRotation(Math.PI / 4, xn, yn);
			s = at.createTransformedShape(s);
			a.add(new Area(s));
		}

		at.setToRotation(pa * Math.PI / 32, xn, yn);
		g.fill(at.createTransformedShape(a));
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		update(g2d);
	}
}
