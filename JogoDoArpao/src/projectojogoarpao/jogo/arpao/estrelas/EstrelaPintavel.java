package projectojogoarpao.jogo.arpao.estrelas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import projectojogoarpao.jogo.fisica.Circulo;

public class EstrelaPintavel {

	public Circulo circulo = null;
	public int rotacao = 0;
	public int nRotacoes = 0;
	public Color[] cores;

	public void draw(Graphics2D g) {
		int xn = circulo.getX() + circulo.getDiametro() / 2;
		int yn = circulo.getY() + circulo.getDiametro() / 2;

		int ct1 = 31;
		int ct2 = 9;
		int lt = 8;
		// desenho dos limites
		// g.drawRect(getX(), getY(), (int)(getDiametro()),
		// (int)(getDiametro()));

		Paint paintAnterior = g.getPaint();
		Polygon p = new Polygon();
		p.xpoints = new int[] { -lt, 0, lt, 0, -lt };
		p.ypoints = new int[] { -ct1, 0, -ct1, -ct1 - ct2, -ct1 };
		p.npoints = 5;

		AffineTransform at = new AffineTransform();
		at.translate(xn, yn);
		Shape s = at.createTransformedShape(p);
		Area a = new Area(s);
		for (int i = 0; i < 15; i++) {
			at.setToTranslation(xn, yn);
			at.rotate(Math.PI / 8);
			at.translate(-xn, -yn);
			s = at.createTransformedShape(s);
			a.add(new Area(s));
		}

		float prop = circulo.getDiametro() / 80f;

		at = new AffineTransform();
		// volta ao ponto inicial
		at.translate(xn, yn);
		// faz a rotação
		if (nRotacoes != 0)
			at.rotate(rotacao * Math.PI / nRotacoes);
		// reduz com a proporção do diametro tendo em conta
		// que o diametro original era 80
		at.scale(prop, prop);
		// desloca ao ponto (0,0) o centro da estrela
		at.translate(-xn, -yn);
		s = at.createTransformedShape(a);

		Point2D centro = new Point2D.Float(xn, yn);
		float r = circulo.getDiametro() / 2;
		float[] d = { 0f, 0.6f, 1f };
		if (cores == null)
			cores = getCoresPadrao();
		RadialGradientPaint rgp = new RadialGradientPaint(centro, r, d, cores);
		g.setPaint(rgp);
		g.fill(s);
		g.setPaint(paintAnterior);
	}

	private Color[] getCoresPadrao() {
		return new Color[] { Color.yellow, Color.yellow, Color.red };
	}
}
