/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import projectojogoarpao.invPrimarios.CadeiaCaracteres;

/**
 *
 * @author francisco
 */
public class TXTPaintComSobra extends TXTPaint {

    public TXTPaintComSobra(Font font, CadeiaCaracteres cadeia) {
        super(font,cadeia);
    }
    public TXTPaintComSobra(Font font, String cadeia) {
        super(font,cadeia);
    }

    @Override
    public void update(Graphics2D g2d, ImageObserver iobs) {
        if (visivel) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
            g2d.setFont(getFont());
            GlyphVector glyphs = getFont().createGlyphVector(g2d.getFontRenderContext(), cadeia.getCadeia());
            Rectangle2D rec = glyphs.getVisualBounds();

            int xCentrado = (int) (400 - (rec.getWidth() + 8) / 2);
            int yCentrado = (int) (300 - (rec.getHeight() + 8) / 2 + rec.getHeight());
            g2d.drawString(cadeia.getCadeia(), xCentrado, yCentrado);
            g2d.setComposite(ac);
            setX(xCentrado + 8);
            setY(yCentrado + 8);
            super.update(g2d, iobs);
        }
    }
}
