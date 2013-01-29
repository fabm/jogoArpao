/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Janelas {

    public static JFrame criaJanela(String titulo,JPanel painel) {
        JFrame jf = new JFrame(titulo);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setContentPane(painel);
        jf.pack();
        jf.setVisible(true);
        return jf;
    }
    
    public static JPanel criaPainel(){
        JPanel painel = new JPanel(new GridBagLayout());
        return painel;
    }
}