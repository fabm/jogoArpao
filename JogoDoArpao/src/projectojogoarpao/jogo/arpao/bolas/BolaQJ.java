package projectojogoarpao.jogo.arpao.bolas;

import java.awt.Dimension;

import projectojogoarpao.invPrimarios.Booleano;
import projectojogoarpao.jogo.Movivel;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.movimentos.MovimentoGravitico;

/**
 * BolaQJ é a abreviatura de Bola do quadro de jogo
 */
public class BolaQJ extends BolaPintavel implements Movivel {

	private Booleano emMov;
	protected MovimentoGravitico mg;

	private Colisao colisao;

	private int // velocidade segundo y após colisão
			vyAC,
			// velocidade segundo y após rebentamento
			vyAR;

	public BolaQJ(TipoBola tipoBola) {
		super(tipoBola);
		setPropriedades();
		mg = new MovimentoGravitico();
		mg.setXmin(0);
		mg.setYmax(0);
	}

	public Colisao getColisao() {
		if (colisao == null) {
			colisao = new Colisao();
		}
		return colisao;
	}

	private void setPropriedades() {
		switch (this.tipoBola) {
		case grande:
			vyAR = 20;
			vyAC = 40;
			return;
		case media:
			vyAR = 20;
			vyAC = 35;
			return;
		case pequena:
			vyAR = 20;
			vyAC = 30;
			return;
		case micro:
			vyAR = 20;
			vyAC = 25;
		}
	}

	public void setEmMov(Booleano emMov) {
		this.emMov = emMov;
	}

	public int getVyAR() {
		return vyAR;
	}

	@Override
	public void setEnquadramento(Dimension enquadramento) {
		super.setEnquadramento(enquadramento);
		mg.setXmax(enquadramento.width);
		mg.setYmax(enquadramento.height);
	}

	public void processarMovimento() {
		if (emMov.activo()) {
			if (getColisao().base) {
				mg.setY(mg.getYmax() - getDiametro());
				mg.setVy(-vyAC);
			}
			if (getColisao().topo) {
				mg.setVy(-mg.getVy());
			}
			if (getColisao().esquerda || getColisao().direita)
				mg.setVx(-mg.getVx());
			mg.move();
		}
	}

	public void setX(int x) {
		mg.setX(x);
	}

	public void setY(int y) {
		mg.setY(y);
	}

	public void setVx(int vx) {
		mg.setVx(vx);
	}

	public void setVy(int vy) {
		mg.setVy(vy);
	}

	public int getVx() {
		return mg.getVx();
	}

	public int getVy() {
		return mg.getVy();
	}

	public int getX() {
		return mg.getX();
	}

	public int getY() {
		return mg.getY();
	}
}
