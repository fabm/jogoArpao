package projectojogoarpao.jogo.arpao.estrelas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.Transient;

import javax.swing.JComponent;

import projectojogoarpao.jogo.fisica.Circulo;
import projectojogoarpao.jogo.fisica.Colisao;
import projectojogoarpao.jogo.fisica.GestorIntercepcoes;

public class JEstrela extends JComponent implements Circulo, FocusListener,
		MouseMotionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JEstrela(int diametro) {
		estrelaPintavel = new EstrelaPintavel();
		cestrela = new CEstrela();
		estrelaPintavel.circulo = cestrela;
		this.diametro = diametro;
		this.addFocusListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		emCima = false;
	}

	private class CEstrela implements Circulo {
		private Colisao colisao;

		public int getX() {
			return 0;
		}

		public int getY() {
			return 0;
		}

		public int getDiametro() {
			return diametro;
		}

		@Override
		public Colisao getColisao() {
			if (colisao == null) {
				colisao = new Colisao();

			}

			return colisao;
		}
	}

	private CEstrela cestrela;
	private boolean emCima;
	private int diametro;
	private EstrelaPintavel estrelaPintavel;

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(diametro, diametro);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		if (isEnabled()) {
			estrelaPintavel.cores = null;
		} else {
			estrelaPintavel.cores = new Color[] { Color.lightGray,
					Color.lightGray, Color.gray };
		}

		estrelaPintavel.draw((Graphics2D) g2d);

		Stroke strokeAntigo = g2d.getStroke();
		Color anterior = g2d.getColor();
		g2d.setStroke(new BasicStroke(4));

		if (emCima && isEnabled()) {
			g2d.setColor(Color.blue);
			g2d.drawOval(0, 0, diametro, diametro);
		}

		if (isFocusOwner() && isEnabled()) {
			g2d.setColor(Color.green);
			g2d.drawOval(0, 0, diametro, diametro);
		}
		g2d.setColor(anterior);
		g2d.setStroke(strokeAntigo);
	}

	@Override
	public Colisao getColisao() {
		return null;
	}

	@Override
	public int getDiametro() {
		return diametro;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (GestorIntercepcoes.pontoDentro(cestrela, e.getX(), e.getY())) {
			requestFocus();
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// não é necessário
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		emCima = false;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// não é necessário

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// não é necessário
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// não é necessário
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		boolean estadoAnterior = emCima;
		emCima = GestorIntercepcoes.pontoDentro(cestrela, e.getX(), e.getY());
		if (estadoAnterior != emCima) {
			repaint();
		}

	}

	@Override
	public void focusGained(FocusEvent arg0) {
		repaint();
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		repaint();
	}
}
