package layout;

import java.awt.GridBagConstraints;

public class HelperGBC {

    private GridBagConstraints gbc = new GridBagConstraints();

    public static HelperGBC criaHGBC() {
        return new HelperGBC();
    }

    public static HelperGBC criaHGBC(int posX, int posY, int largura, int altura) {
        HelperGBC helper = new HelperGBC();
        helper.setXYAL(posX, posY, largura, altura);
        return helper;
    }

    public void setXYAL(int posX, int posY, int largura, int altura) {
        gbc.gridx = posX;
        gbc.gridy = posY;
        gbc.gridwidth = largura;
        gbc.gridheight = altura;
    }

    public void setPreenchimento(int preenchimento) {
        gbc.fill = preenchimento;
    }

    public void setAlinhamento(int alinhamento) {
        gbc.anchor = alinhamento;
    }

    public void setPeso(int x, int y) {
        gbc.weightx = x;
        gbc.weighty = y;
    }

    public void insets(int esquerda, int direita, int topo, int base) {
        gbc.insets.left = esquerda;
        gbc.insets.right = direita;
        gbc.insets.top = topo;
        gbc.insets.bottom = base;
    }

    public GridBagConstraints getGBC() {
        return gbc;
    }
}
