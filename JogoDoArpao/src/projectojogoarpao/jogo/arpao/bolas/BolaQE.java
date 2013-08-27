package projectojogoarpao.jogo.arpao.bolas;

import java.awt.Point;

import projectojogoarpao.jogo.arpao.editor.Arrastavel;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.GestorIntercepcoes;
import projectojogoarpao.jogo.fisica.movimentos.Movimento;

/**
 * Bola do quadro de edição
 */
public class BolaQE extends BolaPintavel implements Arrastavel{

	private boolean arrasta;
	private Point centro;
	private Movimento mv;

	public BolaQE(TipoBola tipoBola) {
		super(tipoBola);
		mv= new Movimento();
		mv.setVx(1);
		mv.setVy(1);
		centro = new Point();
	}

	private void setXY(int x, int y) {
		setX(x);
		setY(y);
	}

	public void setX(int x) {
		mv.setX(x);
	}

	public void setY(int y) {
		mv.setY(y);
	}

	public void arrasta(int x, int y) {
		int xbola = x - centro.x;
		int ybola = y - centro.y;
		
		boolean valido = true;;
		if(xbola+getDiametro()>enquadramento.width)
			valido = false;
		if(ybola+getDiametro()>enquadramento.height)
			valido = false;
		if(xbola<0)
			valido = false;
		if(ybola<0)
			valido = false;

		
		if (arrasta && valido) {
			setXY(xbola, ybola);
		}
	}

	public boolean inicioDoArrastamento(int x, int y) {
		if (GestorIntercepcoes.pontoDentro(this, x, y)) {
			arrasta = true;
			centro.x = x - mv.getX();
			centro.y = y - mv.getY();
		} else {
			arrasta = false;
		}
		return arrasta;
	}

	public int getVx() {
		return mv.getVx();
	}

	public void setVx(int vx) {
		mv.setVx(vx);
	}

	public void setVy(int vy) {
		mv.setVy(vy);
	}

	public int getVy() {
		return mv.getVy();
	}

	public int getX() {
		return mv.getX();
	}

	public int getY() {
		return mv.getY();
	}

	@Override
	public Colisao getColisao() {
		return null;
	}

}