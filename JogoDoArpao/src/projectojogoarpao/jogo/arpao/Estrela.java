package projectojogoarpao.jogo.arpao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.ImageObserver;

import projectojogoarpao.invPrimarios.Booleano;
import projectojogoarpao.jogo.Movivel;
import projectojogoarpao.jogo.Pintavel;
import projectojogoarpao.jogo.fisica.Circulo;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.movimentos.MovimentoLinear;

public class Estrela implements Pintavel, Movivel, Circulo {

	public Estrela() {
		ml.setVx(2);
		ml.setVy(2);
	}

	private Booleano emMov;

	public void setEmMov(Booleano emMov) {
		this.emMov = emMov;
	}

	private MovimentoLinear ml = new MovimentoLinear();
	private int rotacao = 0;
	private Colisao colisao;

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

		int h = g.getClipBounds().height;
		int w = g.getClipBounds().width;
		int xn = ml.getX() + 40;
		int yn = ml.getY() + 40;

		g.setColor(Color.blue);
		Polygon p = new Polygon();
		p.xpoints = new int[] { 0, 20, 40 };
		p.ypoints = new int[] { 20, 0, 20 };
		p.npoints = 3;

		AffineTransform at = new AffineTransform();
		at.translate(xn - 20, yn - 40);
		Shape s = at.createTransformedShape(p);
		Area a = new Area(s);
		for (int i = 0; i < 7; i++) {
			at.setToRotation(Math.PI / 4, xn, yn);
			s = at.createTransformedShape(s);
			a.add(new Area(s));
		}

		at.setToRotation(rotacao * Math.PI / 32, xn, yn);
		g.fill(at.createTransformedShape(a));
	}

	@Override
	public void processarMovimento() {
		if (emMov.activo()) {
			if ((getColisao().base && 
					ml.getVy() > 0) ||getColisao().topo && ml.getVy() < 0) {
				ml.setVy(-ml.getVy());
			}
			if ((getColisao().esquerda && ml.getVx() < 0)
					|| (getColisao().direita && ml.getVx() > 0))
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
		return 80;
	}

	@Override
	public int getX() {
		return ml.getX();
	}

	@Override
	public int getY() {
		return ml.getY();
	}
}
