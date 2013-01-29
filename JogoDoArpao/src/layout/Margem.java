/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Insets;

/**
 *
 * @author francisco
 */
public class Margem {
    public Margem(Insets insets) {
        if (insets == null) {
            throw new RuntimeException("O objecto insets não pode ser nulo");
        }
        this.insets = insets;
    }
    
    private Insets insets;

    public int getBase() {
        return insets.bottom;
    }

    public void setBase(int base) {
        insets.bottom = base;
    }

    public int getDireita() {
        return insets.right;
    }

    public void setDireita(int direita) {
        insets.right = direita;
    }

    public int getEsquerda() {
        return insets.left;
    }

    public void setEsquerda(int esquerda) {
        insets.left = esquerda;
    }

    public int getTopo() {
        return insets.top;
    }

    public void setTopo(int topo) {
        insets.top = topo;
    }    
    
}
