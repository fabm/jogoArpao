package projectojogoarpao.jogo.arpao.bolas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import projectojogoarpao.jogo.fisica.Circulo;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.GestorIntercepcoes;

@SuppressWarnings("serial")
public class JBola extends JComponent implements FocusListener,
		MouseMotionListener, MouseListener {

	private class CBola implements Circulo {
		private Colisao colisao;

		public int getX() {
			return 0;
		}

		public int getY() {
			return 0;
		}

		public int getDiametro() {
			return JBola.this.getDiametro();
		}

		@Override
		public Colisao getColisao() {
			if (colisao == null) {
				colisao = new Colisao();

			}

			return colisao;
		}

	}

	private ImagemBolaCPB imagemBola;
	private CBola cbola = new CBola();
	private boolean emCima = false;
	private Dimension tamanho;

	public JBola(TipoBola tipo) {
		imagemBola = new ImagemBolaCPB();
		imagemBola.setTipo(tipo);

		tamanho = new Dimension(imagemBola.getBI().getWidth(), imagemBola
				.getBI().getHeight());
		this.addFocusListener(JBola.this);
		this.addMouseListener(JBola.this);
		this.addMouseMotionListener(JBola.this);
	}

	@Override
	public Dimension getPreferredSize() {
		return tamanho;
	}

	public int getDiametro() {
		return imagemBola.getDiametro();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage bi;

		if (!isEnabled()) {
			bi = imagemBola.getBIPB();
			g2d.drawImage(bi, 0, 0, this);
			return;
		} else

			bi = imagemBola.getBI();
		g2d.drawImage(bi, 0, 0, this);

		if (emCima || this.isFocusOwner()) {
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			Ellipse2D ellipse = new Ellipse2D.Float(2, 2, getDiametro() - 4,
					getDiametro() - 4);
			if (isFocusOwner()) {
				g2d.setPaint(Color.GREEN);
			} else {
				g2d.setPaint(Color.BLUE);
			}

			BasicStroke bs = new BasicStroke(4);

			g2d.setStroke(bs);
			g2d.draw(ellipse);
		}
	}

	/*
	 * public void realizaTamanho() { setSize(getDiametro(), getDiametro()); }
	 */
	
	public TipoBola getTipo() {
		return imagemBola.getTipo();
	}

	public void setTipo(TipoBola tipo) {
		imagemBola.setTipo(tipo);
	}

	public void focusGained(FocusEvent e) {
		repaint();
	}

	public void focusLost(FocusEvent e) {
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
		// não é necessário
	}

	public void mouseMoved(MouseEvent e) {
		boolean estadoAnterior = emCima;
		emCima = GestorIntercepcoes.pontoDentro(cbola, e.getX(), e.getY());
		if (estadoAnterior != emCima) {
			repaint();
		}
	}

	public void mouseClicked(MouseEvent e) {

		if (GestorIntercepcoes.pontoDentro(cbola, e.getX(), e.getY())) {
			requestFocus();
			repaint();
		}
	}

	public void mousePressed(MouseEvent e) {
		// não é necessário
	}

	public void mouseReleased(MouseEvent e) {
		// não é necessário
	}

	public void mouseEntered(MouseEvent e) {
		// não é necessário
	}

	public void mouseExited(MouseEvent e) {
		emCima = false;
		repaint();
	}
}
