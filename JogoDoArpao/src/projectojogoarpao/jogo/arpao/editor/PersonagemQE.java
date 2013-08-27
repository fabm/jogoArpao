package projectojogoarpao.jogo.arpao.editor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.arpao.Personagem;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.GestorIntercepcoes;
import projectojogoarpao.jogo.fisica.Rectangulo;

public class PersonagemQE implements Rectangulo, Arrastavel, Pintavel {

	public PersonagemQE() {
		centro = new Point(); 
	}
	
	private boolean arrasta;

	private final int largura = 34;
	private final int altura = 34;
	private int x, y;
	private BufferedImage bi = Personagem.imagemPersonagem();
	private Dimension enquadramento;

	private Point centro;

	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void arrasta(int x, int y) {
		boolean valido = true;
		int xp = x - centro.x;
		int yp = y - centro.y;

		if (xp + largura > enquadramento.width) {
			valido = false;
			setX(enquadramento.width - getLargura());
		} else if (xp < 0) {
			valido = false;
			setX(0);
		} else if (yp + altura > enquadramento.height) {
			valido = false;
			setY(enquadramento.height - getLargura());
		} else if (yp < 0) {
			valido = false;
			setY(0);
		}

		if (arrasta && valido) {
			setX(xp);
			setY(yp);
		}
	}

	public boolean inicioDoArrastamento(int x, int y) {
		if (GestorIntercepcoes.pontoDentro(this, x, y)) {
			centro.x = x - getX();
			centro.y = y - getY();
			arrasta = true;
		} else {
			arrasta = false;
		}
		return arrasta;
	}

	@Override
	public void setEnquadramento(Dimension enquadramento) {
		this.enquadramento = enquadramento;
	}

	@Override
	public void update(Graphics2D g2d, ImageObserver iobs) {
		BufferedImage subImagem = bi.getSubimage(34, 0, 34, 32);
		g2d.drawImage(subImagem, getX(), getY(), iobs);
	}

	@Override
	public Colisao getColisao() {
		return null;
	}

	@Override
	public int getLargura() {
		return 34;
	}

	@Override
	public int getAltura() {
		return 34;
	}

}