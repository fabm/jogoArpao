package projectojogoarpao.jogo.arpao;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.Rectangulo;

public class Obstaculo implements Rectangulo, Pintavel {
	BufferedImage bi = null;

	public Obstaculo(int largura, int altura, int x, int y) {
		super();
		this.largura = largura;
		this.altura = altura;
		this.x = x;
		this.y = y;
	}

	private int largura, altura, x, y;
	private Colisao colisao;

	public Colisao getColisao() {
		if (colisao == null) {
			colisao = new Colisao();
		}

		return colisao;
	}

	public int getLargura() {
		return largura;
	}

	public int getAltura() {
		return altura;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public void setEnquadramento(Dimension enquadramento) {
		// TODO Auto-generated method stub
	}

	private void carregaImagem() {
		try {
			if (bi == null) {
				File file = new File("imagens/tijolos.jpg");
				bi = ImageIO.read(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Graphics2D g2d, ImageObserver iobs) {
		carregaImagem();
		Paint paintAnterior = g2d.getPaint();
		TexturePaint tp = new TexturePaint(bi, new Rectangle2D.Double(0, 0,
				153, 99));
		g2d.setPaint(tp);

		g2d.fillRect(x, y, largura, altura);
		g2d.setPaint(paintAnterior);
	}
	
	public static Obstaculo padrao() {
		Obstaculo obstaculo = new Obstaculo(200, 20, 300, 400);
		return obstaculo;
	}
}
