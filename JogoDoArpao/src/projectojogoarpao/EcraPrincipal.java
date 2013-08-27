package projectojogoarpao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class EcraPrincipal extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage bi = null;

	public EcraPrincipal() {
		setPreferredSize(new Dimension(800, 600));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		File f = new File("imagens/entrada.png");
		if (bi == null) {
			try {
				bi = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		g.drawImage(bi, 0, 0, this);
		g.setColor(Color.gray);
		g.setFont(new Font("Sans Serif",Font.PLAIN , 40));
		g.drawString("Jogo do arpão", 250, 250);
		
		super.paintComponent(g);
	}
}
