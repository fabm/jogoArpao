package projectojogoarpao.jogo.arpao.estrelas;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashMap;

import projectojogoarpao.invPrimarios.Booleano;
import projectojogoarpao.jogo.Movivel;
import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.fisica.Circulo;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.movimentos.MovimentoLinear;

public class Estrela implements Pintavel, Movivel, Circulo {

	private static HashMap<Integer, BufferedImage[]> cache;

	public Estrela(final int diametro) {
		if (Estrela.cache == null) {
			Estrela.cache = new HashMap<>();
		}
		ml.setVx(1);
		ml.setVy(1);
		this.diametro = diametro;
		this.estrelaPintavel = new EstrelaPintavel();
		this.estrelaPintavel.circulo = this;

		final int nr = 64;
		estrelaPintavel.nRotacoes = nr;
		bis = new BufferedImage[nr];
		estrelaPintavel.rotacao = 0;

		Thread carregador = new Thread() {
			@Override
			public void run() {
				if (Estrela.cache.containsKey(diametro)) {
					bis = Estrela.cache.get(diametro);
				} else {
					for (int i = 0; i < nr; i++) {
						bis[i] = new BufferedImage(diametro, diametro,
								BufferedImage.TYPE_INT_ARGB);
						estrelaPintavel.circulo = new Circulo() {

							@Override
							public int getY() {
								return 0;
							}

							@Override
							public int getX() {
								return 0;
							}

							@Override
							public Colisao getColisao() {
								return null;
							}

							@Override
							public int getDiametro() {
								return Estrela.this.diametro;
							}
						};
						estrelaPintavel.draw(bis[i].createGraphics());
						estrelaPintavel.rotacao++;
					}
					Estrela.cache.put(diametro, bis);
				}
			}
		};
		carregador.start();
	}

	private Booleano emMov;
	private int diametro;
	private BufferedImage[] bis;
	private MovimentoLinear ml = new MovimentoLinear();
	private int rotacao = 0;
	private Colisao colisao;
	private EstrelaPintavel estrelaPintavel;

	public void setEmMov(Booleano emMov) {
		this.emMov = emMov;
	}

	public void incRotacao() {
		rotacao++;
		if (rotacao == 64)
			rotacao = 0;
	}

	@Override
	public void setEnquadramento(Dimension enquadramento) {
		ml.setXmax(enquadramento.width);
		ml.setYmax(enquadramento.height);
	}

	@Override
	public void update(Graphics2D g, ImageObserver iobs) {
		if (bis[rotacao] != null) {
			g.drawImage(bis[rotacao], getX(), getY(), iobs);
		}
		/*
		 * else{ System.out.println("nulo"); }
		 */
	}

	protected boolean roda() {
		return true;
	}

	@Override
	public void processarMovimento() {

		if (emMov.activo()) {
			if ((getColisao().base && ml.getVy() > 0) || getColisao().topo
					&& ml.getVy() < 0) {
				ml.setVy(-ml.getVy());
			}
			if ((getColisao().esquerda) || (getColisao().direita))
				ml.setVx(-ml.getVx());
			incRotacao();
			ml.move();
		}

	}

	@Override
	public Colisao getColisao() {
		if (colisao == null) {
			colisao = new Colisao();
		}
		return colisao;
	}

	@Override
	public int getDiametro() {
		return diametro;
	}

	@Override
	public int getX() {
		return ml.getX();
	}

	@Override
	public int getY() {
		return ml.getY();
	}

	public int getVx() {
		return ml.getVx();
	}

	public void setX(int x) {
		ml.setX(x);
	}

	public void setY(int y) {
		ml.setY(y);
	}

	public void setVx(int vx) {
		ml.setVx(vx);
	}

	public Estrela criaMenor(int diametro, boolean inverteVX) {
		Estrela nova = new Estrela(diametro);
		nova.setVx(getVx() * (inverteVX ? -1 : 1));
		nova.ml.setVy(-ml.getVy());
		nova.setX(getX());
		nova.setY(getY());
		nova.setEmMov(emMov);
		return nova;
	}

}
