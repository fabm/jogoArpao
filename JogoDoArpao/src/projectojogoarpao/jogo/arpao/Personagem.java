/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import projectojogoarpao.jogo.fisica.Colidivel;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.Rectangulo;
import projectojogoarpao.jogo.fisica.movimentos.MovimentoGravitico;

/**
 * 
 * @author francisco
 */
public class Personagem implements Movivel, ControlavelPorTeclado, Rectangulo, Colidivel{

	private static final int VELOCIDADE = 3;
	private BufferedImage bi;
	private boolean acelAnteriorDireita;
	protected Dimension enquadramento;
	private int contMovimentos;
	private int contPiscar;
	private boolean emSalto;
	private Booleano movivel;
	private boolean morto;
	protected MovimentoGravitico mg;
	private Colisao colisao;

	public static BufferedImage imagemPersonagem() {
		File fPersonagem = new File("imagens/pang.png");
		BufferedImage bi;
		try {
			bi = ImageIO.read(fPersonagem);
		} catch (IOException ex) {
			throw new ExcecaoCarregamentoFicheiro(fPersonagem);
		}
		return bi;
	}

	public Personagem() {
		bi = imagemPersonagem();
		emSalto = false;
		iniciar();
	}

	public MovimentoGravitico getMg() {
		return mg;
	}

	public void setMg(MovimentoGravitico mg) {
		this.mg = mg;
		mg.stopY = false;
		mg.setVx(0);
	}

	public Colisao getColisao() {
		if (colisao == null) {
			colisao = new Colisao();
		}
		return colisao;
	}

	public final void iniciar() {
		acelAnteriorDireita = true;
		contMovimentos = -1;
		// vx = 0;
		contPiscar = -1;
		morto = false;
	}

	private int getIdImagem() {
		int spritesARepetir = 12;
		// morto
		if (morto) {
			if (contMovimentos <= spritesARepetir) {
				contMovimentos++;
			}
			return contMovimentos / spritesARepetir + 9;
		}
		// vivo
		if (mg.getVx() == 0) {
			contMovimentos = -1;
			if (acelAnteriorDireita) {
				return 21;
			} else {
				return 20;
			}
		}

		spritesARepetir = 4;
		contMovimentos++;
		if (contMovimentos >= spritesARepetir * 4) {
			contMovimentos = 0;
		}

		int resultado = 0;
		if (mg.getVx() < 0) {
			resultado = 11;
		}

		return contMovimentos / spritesARepetir + resultado;
	}

	public void teclaPressionada(KeyEvent ke) {
		if (movivel.activo()) {
			int keyCode = ke.getKeyCode();
			if (!movivel.activo()) {
				mg.setVx(0);
				mg.stopX = true;
				return;
			}
			if (keyCode == KeyEvent.VK_LEFT && mg.getVx() >= 0) {
				// vx = -VELOCIDADE;
				mg.stopX = false;
				mg.setVx(-VELOCIDADE);
			}
			if (keyCode == KeyEvent.VK_RIGHT) {
				mg.stopX = false;
				mg.setVx(VELOCIDADE);
				// vx = VELOCIDADE;
			}
			if (keyCode == KeyEvent.VK_UP) {
				if (!emSalto) {
					emSalto = true;
					inicioSalto = true;
					mg.setVy(-30);
				}
			}
		}
	}

	public void teclaLargada(KeyEvent ke) {
		if (movivel.activo()) {
			int kc = ke.getKeyCode();
			if (!movivel.activo()) {
				// vx = 0;
				mg.stopX = true;
				return;
			}
			if (mg.getVx() != 0) {
				// acelAnteriorDireita = vx > 0;
				acelAnteriorDireita = mg.getVx() > 0;
			}

			if (kc == KeyEvent.VK_LEFT && mg.getVx() != VELOCIDADE) {
				mg.setVx(0);
				// vx = 0;
			}
			if (kc == KeyEvent.VK_RIGHT && mg.getVx() != -VELOCIDADE) {
				mg.setVx(0);
				// vx = 0;
			}
		}
	}

	//
	public void update(Graphics2D g2d, ImageObserver iobs) {
		if (contPiscar == -1 || contPiscar % 6 < 3) {
			BufferedImage subImagem = bi.getSubimage(getIdImagem() * 34, 0, 34,
					32);
			g2d.drawImage(subImagem, mg.getX(), mg.getY(), iobs);
		}
	}

	public void atingido() {
		contPiscar = 0;
	}

	public boolean ressucitar() {
		return contPiscar != -1;
	}

	boolean inicioSalto = false;

	private void movimentoVivo() {

		if (getColisao().base) {
			if (mg.getVy() >= 0) {
				mg.setY(mg.getYmax() - getAltura());
				mg.setVy(0);
				emSalto = false;
			}
		}
		if (getColisao().topo) {
			mg.setVy(-mg.getVy());
		}
		if (getColisao().esquerda) {
			mg.setX(0);
		}
		if (getColisao().direita) {
			mg.setX(mg.getXmax() - 34);
		}

		mg.move();
		if (contPiscar != -1 && contPiscar < 120) {
			contPiscar++;
		} else {
			contPiscar = -1;
		}
	}

	public void processarMovimento() {
		if (morto) {
			movivel.setValor(false);
		}
		if (movivel.activo()) {
			movimentoVivo();
		}
	}

	public void setEnquadramento(Dimension enquadramento) {
		this.enquadramento = enquadramento;
	}

	public int getLargura() {
		return 26;
	}

	public int getAltura() {
		return 34;
	}

	public void setMovivel(Booleano movivel) {
		this.movivel = movivel;
	}


	@Override
	public int getX() {
		return mg.getX();
	}

	@Override
	public int getY() {
		return mg.getY();
	}

}