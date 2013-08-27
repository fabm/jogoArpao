package projectojogoarpao.jogo.arpao;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import projectojogoarpao.jogo.Movivel;
import projectojogoarpao.jogo.Pintavel;

public class Ampulheta implements Pintavel {

	private int seg, ry;
	private long iniciado, pausado;

	public Ampulheta() {
		this.ry = 20;
	}

	@Override
	public void setEnquadramento(Dimension enquadramento) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Graphics2D g2d, ImageObserver iobs) {
		if (iniciado != 0) {
			Paint paintAntigo = g2d.getPaint();
			int posX = 680;
			int posY = 30;
			GeneralPath gp = new GeneralPath();
			//
			int rx = 12;
			// y da curva base
			int cb = 5;
			// y da curva do gargalo
			int cg = 5;
			// x da largura do gargalo
			int lg = 2;

			gp.moveTo(-rx, -ry);
			gp.lineTo(rx, -ry);
			gp.curveTo(rx, -cb, lg, -cg, lg, 0);
			gp.curveTo(lg, cg, rx, cb, rx, ry);
			gp.lineTo(-rx, ry);
			gp.curveTo(-rx, cb, -lg, cg, -lg, 0);
			gp.curveTo(-lg, -cg, -rx, -cb, -rx, -ry);

			AffineTransform at = new AffineTransform();
			at.translate(posX, posY);
			Shape s = at.createTransformedShape(gp);
			g2d.draw(at.createTransformedShape(gp));
			g2d.setClip(s);
			int desY = (int) (((pausado == 0 ? System.currentTimeMillis()
					- iniciado : pausado - iniciado) / seg)
					* ry / 1000);

			BufferedImage bi;
			try {
				File file = new File("imagens/areia.jpg");
				bi = ImageIO.read(file);
				TexturePaint tp = new TexturePaint(bi, new Rectangle2D.Double(0, desY, 50, 50));				
				g2d.setPaint(tp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g2d.fillRect(-lg + posX+1, posY, lg*2-1, ry);
			
			g2d.fillRect(-rx + posX, (int) (-ry + posY + desY), rx * 2, ry - desY);
			g2d.fillRect(-rx + posX, (int) (ry + posY - desY), rx * 2, desY);
			g2d.setClip(0, 0, 800, 600);
			g2d.setPaint(paintAntigo);
		}
	}


	public void iniciar(int segundos) {
		this.pausado = 0;
		this.seg = segundos;
		iniciado = System.currentTimeMillis();
	}

	public void pausar() {
		pausado = System.currentTimeMillis();
	}

	public void continuar() {
		iniciado = System.currentTimeMillis() - iniciado - pausado;
		pausado = 0;
	}

}
