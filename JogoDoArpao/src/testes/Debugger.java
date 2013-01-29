package testes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import projectojogoarpao.jogo.fisica.Rectangulo;
import projectojogoarpao.jogo.fisica.movimentos.MovimentoGravitico;

public class Debugger {
	int rY, cY, rY2, cY2, rX, cX, rX2, cX2;
	public int geth0 = 200;
	public static Graphics2D g2d;

	public void debug(int rY, int cY, int rY2, int cY2, int rX, int cX,
			int rX2, int cX2) {
		this.rY = rY;
		this.cY = cY;
		this.rY2 = rY2;
		this.cY2 = cY2;
		this.rX = rX;
		this.cX = cX;
		this.rX2 = rX2;
		this.cX2 = cX2;
		System.out.printf("rx:%d cx:%d rx2:%d cx2:%d\n", rX, cX, rX2, cX2);
		System.out.printf("ry:%d cy:%d ry2:%d cy2:%d\n", rY, cY, rY2, cY2);
	}

	public void track() throws Throwable {
		throw new Throwable("tracking");
	}

	public void update(Graphics2D g2d, ImageObserver iobs) {
		Color cor = g2d.getColor();
		g2d.setColor(Color.red);
		g2d.drawLine(0, cY, 800, cY);
		g2d.drawLine(cX, 0, cX, 600);
		g2d.drawLine(0, rY, 800, rY);
		g2d.setColor(Color.green);
		g2d.drawLine(0, rY2, 800, rY2);
		g2d.drawLine(cX2, 0, cX2, 600);
		g2d.drawLine(0, cY2, 800, cY2);
		g2d.setColor(cor);
	}

	static Debugger debugger = null;

	public static Debugger get() {
		if (debugger == null) {
			debugger = new Debugger();
		}
		return debugger;
	}

	public void printh(int h, int h0) {
		System.out.printf("h:%d h0:%d\n", h, h0);
	}

	public void drawH(Graphics2D g2d, int h0) {
		g2d.drawLine(0, h0, 800, h0);
	}

	public void drawRectangulo(Graphics2D g2d, Rectangulo r) {
		// g2d.fillRect(r.getX(), r.getY(), r.getLargura(), r.getAltura());
	}

	public void printf(String string) {
		if (dentro) {
			System.out.print(string);
		}
	}

	public void printf(String string, Object... args) {
		if (dentro) {
			System.out.printf(string, args);
		}
	}

	
	boolean dentro;

	public boolean getDentro() {
		return dentro;
	}

	public void setDentro(boolean dentro) {
		this.dentro = dentro;
	}
}
