/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo.arpao;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import projectojogoarpao.excepcoes.ExcecaoCarregamentoFicheiro;
import projectojogoarpao.jogo.Pintavel;

/**
 *
 * @author francisco
 */
public class Fundo implements Pintavel {

    BufferedImage buf=null;

    public Fundo(String ficheiro) {
        mudaFundo(ficheiro);
    }

    public void update(Graphics2D g2d, ImageObserver iobs) {
        g2d.drawImage(buf, 0, 0, iobs);
    }

    public void setEnquadramento(Dimension enquadramento) {
        if (buf.getWidth() > enquadramento.width && buf.getHeight() > enquadramento.height) {
            buf = buf.getSubimage(0, 0, enquadramento.width, enquadramento.height);
        }
    }
    public final void mudaFundo(String ficheiro){
        String caminho = "imagens/fundos/" + ficheiro;
        File fFundo = new File(caminho);
        try {
            buf = ImageIO.read(fFundo);
        } catch (IOException ex) {
            throw new ExcecaoCarregamentoFicheiro(fFundo);
        }
    }
}
