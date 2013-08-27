package projectojogoarpao.jogo.arpao.bolas;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import projectojogoarpao.excepcoes.ExcecaoCarregamentoFicheiro;

/**
 * Imagem da bola com preto e branco
 * 
 * @author francisco
 */
public class ImagemBolaCPB extends ImagemBola {

    private static BufferedImage biBolasPB = null;
    private BufferedImage biBolaPB;

    @Override
    protected void carrega(int x, int y, int diametro) {
        super.carrega(x, y, diametro);
        if (biBolasPB == null) {
            File ficheiro = new File("imagens/bolas.png");
            try {
                biBolasPB = ImageIO.read(ficheiro);
                rgbToGray(biBolasPB);
            } catch (IOException ex) {
                throw new ExcecaoCarregamentoFicheiro(ficheiro);
            }
        }
        biBolaPB = biBolasPB.getSubimage(x, y, diametro, diametro);
    }
    
        private static void rgbToGray(BufferedImage image) {
        int w = image.getWidth(), h = image.getHeight();
        WritableRaster wr = image.getRaster();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int[] pixel =new int[4];
                wr.getPixel(x, y, pixel);
                pixel[0]=(pixel[0]+pixel[1]+pixel[2])/3;
                pixel[2]=pixel[1]=pixel[0];
                wr.setPixel(x, y, pixel);
            }
        }
    }

    public BufferedImage getBIPB() {
        return biBolaPB;
    }
}
