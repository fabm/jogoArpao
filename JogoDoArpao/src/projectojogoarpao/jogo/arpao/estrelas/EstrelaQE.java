package projectojogoarpao.jogo.arpao.estrelas;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;

import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.arpao.editor.Arrastavel;
import projectojogoarpao.jogo.fisica.Circulo;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.GestorIntercepcoes;

public class EstrelaQE implements Pintavel,Arrastavel, Circulo {
	public EstrelaQE(int diametro) {
		this.diametro = diametro;
		arrasta = false;
		centro = new Point();
		estrelaPintavel = new EstrelaPintavel();
		estrelaPintavel.circulo = this;
	}

	private int diametro;
	private int x, y;
	private Point centro;
	private Dimension enquadramento;
	private boolean arrasta;
	
	private EstrelaPintavel estrelaPintavel;

	@Override
	public void arrasta(int x, int y) {
		boolean valido = true;
		int xe = x - centro.x;
		int ye = y - centro.y;

		if (xe + getDiametro() > enquadramento.width) {
			valido = false;
			setX(enquadramento.width - getDiametro());
		} else if (xe < 0) {
			valido = false;
			setX(0);
		} else if (ye + getDiametro() > enquadramento.height) {
			valido = false;
			setY(enquadramento.height - getDiametro());
		} else if (ye < 0) {
			valido = false;
			setY(0);
		}

		if (arrasta && valido) {
			setX(xe);
			setY(ye);
		}
	}

	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

	@Override
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
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public Colisao getColisao() {
		return null;
	}

	@Override
	public int getDiametro() {
		return diametro;
	}

	@Override
	public void setEnquadramento(Dimension enquadramento) {
		this.enquadramento = enquadramento;
	}

	@Override
	public void update(Graphics2D g2d, ImageObserver iobs) {
		estrelaPintavel.draw(g2d);
	}
}