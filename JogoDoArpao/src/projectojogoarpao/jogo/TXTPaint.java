/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.ImageObserver;
import projectojogoarpao.invPrimarios.CadeiaCaracteres;
import projectojogoarpao.invPrimarios.CadeiaConstante;

public class TXTPaint implements Pintavel {

    public TXTPaint(Font font, CadeiaCaracteres cadeia) {
        this.font = font;
        this.cadeia = cadeia;
    }
    public TXTPaint(Font font, String cadeia) {
        this.font = font;
        this.cadeia = new CadeiaConstante(cadeia);
    }
        
    private int x, y;
    boolean visivel;
    protected CadeiaCaracteres cadeia;
    private Font font;

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public boolean estaVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incY(int y) {
        this.y += y;
    }

    public void setEnquadramento(Dimension enquadramento) {
        //não utilizado
    }

    public void update(Graphics2D g2d, ImageObserver iobs) {
        if (visivel) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setFont(font);
            g2d.drawString(cadeia.getCadeia(), x, y);
        }
    }
}
