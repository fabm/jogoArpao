/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

/**
 *
 * @author francisco
 */
public interface Pintavel {
    void setEnquadramento(Dimension enquadramento);
    void update(Graphics2D g2d, ImageObserver iobs);
}
