package projectojogoarpao.jogo.arpao.bolas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import projectojogoarpao.excepcoes.ExcecaoCarregamentoFicheiro;

public class ImagemBola {

    private static BufferedImage biBolas = null;
    private BufferedImage biBola;
    private int diametro;
    private TipoBola tipo;

    protected void carrega(int x, int y, int diametro) {
        this.diametro = diametro;
        if (biBolas == null) {
            File ficheiro = new File("imagens/bolas.png");
            try {
                biBolas = ImageIO.read(ficheiro);
            } catch (IOException ex) {
                throw new ExcecaoCarregamentoFicheiro(ficheiro);
            }
        }
        biBola = biBolas.getSubimage(x, y, diametro, diametro);
    }
    
    void setTipo(TipoBola tipoBola) {
        this.tipo = tipoBola;
        switch (tipoBola) {
            case grande:
                carrega(0, 0, 97);
                return;
            case media:
                carrega(51, 109, 46);
                return;
            case pequena:
                carrega(5, 123, 32);
                return;
            case micro:
                carrega(12, 99, 12);
        }
    }
    public BufferedImage getBI(){
        return biBola;
    }
    public int getDiametro(){
        return diametro;
    }
    public TipoBola getTipo(){
        return tipo;
    }
}
