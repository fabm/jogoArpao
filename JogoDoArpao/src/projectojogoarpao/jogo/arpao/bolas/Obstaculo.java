/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo.arpao.bolas;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import projectojogoarpao.jogo.Pintavel;

/**
 *
 * @author Francisco
 */
public class Obstaculo implements Pintavel{

    private Dimension enq;
    public void setEnquadramento(Dimension enquadramento) {
        this.enq=enquadramento;
    }

    public void update(Graphics2D g2d, ImageObserver iobs) {
        
    }
    
}
