package projectojogoarpao.jogo.arpao;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import projectojogoarpao.excepcoes.ExcecaoCarregamentoFicheiro;
import projectojogoarpao.invPrimarios.Booleano;
import projectojogoarpao.jogo.ControlavelPorTeclado;
import projectojogoarpao.jogo.Movivel;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.Rectangulo;
import projectojogoarpao.jogo.fisica.movimentos.MovimentoGravitico;

public class Arpao implements ControlavelPorTeclado, Movivel, Rectangulo {

	private MovimentoGravitico mg;
	private int h, lancamentoX;
	private Dimension enquadramento;
	private BufferedImage bufArpao;
	private Booleano movivel;
	private int h0;
	private Colisao colisao;

	public Arpao(Booleano movivel) {
		File fArpao = new File("imagens/arpao.png");
		try {
			bufArpao = ImageIO.read(fArpao);
			h = 0;
			this.movivel = movivel;
		} catch (IOException ex) {
			throw new ExcecaoCarregamentoFicheiro(fArpao);
		}
	}

	public int getX() {
		return lancamentoX + 3;
	}

	public void update(Graphics2D g2d, ImageObserver iobs) {
		if (h > 0 && h < enquadramento.height) {
			BufferedImage bufArpaoCortado;
			bufArpaoCortado = bufArpao.getSubimage(0, 0, 10, h);
			g2d.drawImage(bufArpaoCortado, lancamentoX, enquadramento.height
					- h - h0, iobs);

		}
	}

	public void teclaPressionada(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
			disparar();
		}
	}

	public void teclaLargada(KeyEvent ke) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	private void disparar() {
		if (movivel.activo() && h == 0) {
			h = 1;
			lancamentoX = mg.getX() + 7;
			h0 = enquadramento.height - mg.getY() - 34;
		}
	}

	public void processarMovimento() {
		if (movivel.activo()) {
			if (getY() <= 0) {
				h = 0;
			} else if (h > 0) {
				h += 8;
			}
		}
	}

	public boolean activo() {
		return h != 0;
	}

	public void setEnquadramento(Dimension enquadramento) {
		this.enquadramento = enquadramento;
	}

	public void iniciar() {
		h = 0;
	}

	public int getY() {
		return enquadramento.height - h - h0;
	}

	public int getLargura() {
		return 4;
	}

	public int getAltura() {
		return h;
	}

	void setMG(MovimentoGravitico mg) {
		this.mg = mg;
	}

	@Override
	public Colisao getColisao() {
		if (colisao == null) {
			colisao = new Colisao();
		}

		return colisao;
	}
}