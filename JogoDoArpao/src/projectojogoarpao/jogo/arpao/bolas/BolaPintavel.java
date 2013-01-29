package projectojogoarpao.jogo.arpao.bolas;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.fisica.Circulo;

public abstract class BolaPintavel implements Pintavel, Circulo {

	private ImagemBola imagemBola;
	protected Dimension enquadramento;
	protected TipoBola tipoBola;

	public BolaPintavel(TipoBola tipoBola) {
		this.tipoBola = tipoBola;
		imagemBola = new ImagemBola();
		imagemBola.setTipo(tipoBola);
	}

	public void setEnquadramento(Dimension enquadramento) {
		this.enquadramento = enquadramento;
	}

	public void update(Graphics2D g2d, ImageObserver iobs) {
		g2d.drawImage(imagemBola.getBI(), getX(), getY(), iobs);
	}

	public int getDiametro() {
		return imagemBola.getDiametro();
	}

	public TipoBola getTipoBola() {
		return tipoBola;
	}
}
