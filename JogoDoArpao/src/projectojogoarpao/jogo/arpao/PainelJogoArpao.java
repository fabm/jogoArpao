/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo.arpao;

import java.awt.Dimension;

import javax.swing.JDialog;

import projectojogoarpao.jogo.PainelJogavel;
import projectojogoarpao.jogo.ProcessadorJogo;

@SuppressWarnings("serial")
public class PainelJogoArpao extends PainelJogavel {

    private JDialog dialogoEdicao;
    private JogoArpao jogoArpao;

    public PainelJogoArpao() {
        super();
        jogoArpao = new JogoArpao();
        setPreferredSize(new Dimension(800, 600));
    }

    public void novoJogo() {
        iniciarAnimacao();
        jogoArpao.iniciarJogo();
    }

    public void editar() {
        dialogoEdicao.setVisible(true);
    }

    @Override
    protected ProcessadorJogo getProcessadorJogo() {
        return jogoArpao;
    }

    @Override
    protected int getFreq() {
        return 60;
    }

}
