package projectojogoarpao.jogo.arpao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.Rectangulo;

public class Obstaculo implements Rectangulo,Pintavel{
	
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

	@Override
	public void update(Graphics2D g2d, ImageObserver iobs) {
		Color anterior = g2d.getColor();
		g2d.setColor(Color.blue);
		g2d.draw(new Rectangle2D.Double(x, y, largura, altura));
		g2d.setColor(anterior);
	}
}
