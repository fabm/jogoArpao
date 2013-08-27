package projectojogoarpao.jogo.arpao;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import projectojogoarpao.jogo.Pintavel;

public class TXTInicioNivel implements Pintavel {

    public TXTInicioNivel(int nivel) {
        this.nivel = nivel;
    }    

    private int nivel;
    private Dimension enquadramento;

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setEnquadramento(Dimension enquadramento) {
        this.enquadramento = enquadramento;
    }

    public void update(Graphics2D g2d, ImageObserver iobs) {
        Font font = new Font("Comic Sans MS", Font.BOLD, 40);
        String linhaNivel = "Nivel " + nivel;
        GlyphVector gv = font.createGlyphVector(g2d.getFontRenderContext(), linhaNivel);
        Rectangle2D bounds = gv.getVisualBounds();
        Rectangle r = criaRectangulo(bounds.getBounds());
        g2d.drawGlyphVector(gv, r.x, r.y + r.height);

        /*
        for (int i = 7; i > -10; i--) {
        g2d.drawGlyphVector(gv, r.x, r.y + r.height * -i);
        }
        g2d.draw(r);
         */
    }

    private Rectangle criaRectangulo(Rectangle r) {
        int xm = enquadramento.width / 2;
        int ym = enquadramento.height / 2;
        Rectangle rectangulo = new Rectangle(xm - r.width / 2, ym - r.height / 2, r.width, r.height);
        return rectangulo;
    }
}
