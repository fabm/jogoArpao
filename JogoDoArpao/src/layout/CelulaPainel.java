package layout;

import java.awt.GridBagConstraints;
import javax.swing.JComponent;

public class CelulaPainel {

    public CelulaPainel(JComponent componente) {
        this.componente = componente;
        gbc = new GridBagConstraints();
        margem = new Margem(gbc.insets);
    }
    private JComponent componente;
    private GridBagConstraints gbc;
    private Margem margem;
    
    public void setPesoX(int peso) {
        gbc.weightx = peso;
    }

    public void setPesoY(int peso) {
        gbc.weighty = peso;
    }

    /**
     * Define a altura em células
     * @param h nº de células
     */
    public void setAltura(int h) {
        gbc.gridheight = h;
    }

    /**
     * Define a largura em células
     * @param l nº de células
     */
    public void setLargura(int l) {
        gbc.gridwidth = l;
    }

    /**
     * Define o tipo de preenchimento
     * @param preenchimento 
     */
    public void setPreenchimento(Preenchimento preenchimento) {
        gbc.fill = preenchimento.getGBC();
    }

    /**
     * Define o tipo de alinhamento
     * @param alinhamento 
     */
    public void setAlinhamento(Alinhamento alinhamento) {
        gbc.anchor = alinhamento.getGBC();
    }

    public JComponent getComponente() {
        return componente;
    }

    public GridBagConstraints getGbc() {
        return gbc;
    }

    public void setComponente(JComponent componente) {
        this.componente = componente;
    }

    public Margem getMargem() {
        return margem;
    }
}
