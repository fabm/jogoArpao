package projectojogoarpao.jogo.arpao.editor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.arpao.Personagem;

public class PersonagemQE implements Arrastavel, Pintavel {

	private int centro;
	private boolean arrasta;

	private final int xmin = 0, ymin = 0;
	private int xmax;
	private int ymax;
	private final int largura = 34;
	private final int altura = 34;
	private int x, y;
	private BufferedImage bi = Personagem.imagemPersonagem();

	public void setX(int x) {
		if (x <= xmin) {
			x = xmin;
			return;
		}
		if (x >= largura - xmax) {
			x = largura - xmax;
			return;
		}
		if (y <= ymin) {
			y = ymin;
			return;
		}
		if (y >= altura - ymax) {
			y = altura - ymax;
			return;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void arrasta(int x, int y) {
		if (arrasta) {
			setX(x - centro);
		}
	}

	public boolean inicioDoArrastamento(int x, int y) {
		if (x > getX() && y > getY() && x - 34 < getX() && y - 34 < getY()) {
			centro = x - getX();
			arrasta = true;
		} else {
			arrasta = false;
		}
		return arrasta;
	}

	@Override
	public void setEnquadramento(Dimension enquadramento) {
		xmax = enquadramento.width;
		ymax = enquadramento.height;
	}

	@Override
	public void update(Graphics2D g2d, ImageObserver iobs) {
		BufferedImage subImagem = bi.getSubimage(34, 0, 34,
				32);
		g2d.drawImage(subImagem, getX(), getY(), iobs);
	}
}