/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import layout.HelperGBC;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import projectojogoarpao.jogo.arpao.bolas.BolaQE;
import projectojogoarpao.jogo.arpao.bolas.JBola;
import projectojogoarpao.jogo.arpao.bolas.TipoBola;

/**
 *
 * @author francisco
 */
public class TesteGridBag {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        Iterator<HelperGBC> iterador;
        JPanel painel = new JPanel(new GridBagLayout());
        jf.setContentPane(painel);

        ArrayList<HelperGBC> listaConstrains =
                new ArrayList<HelperGBC>();

        listaConstrains.add(HelperGBC.criaHGBC(0, 0, 2, 8));
        listaConstrains.add(HelperGBC.criaHGBC(2, 0, 2, 1));
        listaConstrains.add(HelperGBC.criaHGBC(4, 0, 2, 1));
        listaConstrains.add(HelperGBC.criaHGBC(2, 1, 4, 1));
        listaConstrains.add(HelperGBC.criaHGBC(2, 2, 4, 1));
        listaConstrains.add(HelperGBC.criaHGBC(2, 3, 4, 1));
        listaConstrains.add(HelperGBC.criaHGBC(2, 4, 4, 1));
        listaConstrains.add(HelperGBC.criaHGBC(2, 5, 4, 1));
        listaConstrains.add(HelperGBC.criaHGBC(2, 6, 1, 1));
        listaConstrains.add(HelperGBC.criaHGBC(3, 6, 1, 1));
        listaConstrains.add(HelperGBC.criaHGBC(4, 6, 1, 1));
        listaConstrains.add(HelperGBC.criaHGBC(5, 6, 1, 1));
        listaConstrains.add(HelperGBC.criaHGBC(2, 7, 2, 1));
        listaConstrains.add(HelperGBC.criaHGBC(4, 7, 2, 1));
        listaConstrains.add(HelperGBC.criaHGBC(0, 8, 6, 1));

        iterador = listaConstrains.iterator();

        iterador.next().setAlinhamento(GridBagConstraints.SOUTHWEST);
        iterador.next().setAlinhamento(GridBagConstraints.EAST);
        iterador.next().setAlinhamento(GridBagConstraints.WEST);
        iterador.next().setAlinhamento(GridBagConstraints.CENTER);
        iterador.next().setAlinhamento(GridBagConstraints.CENTER);
        iterador.next().setAlinhamento(GridBagConstraints.CENTER);
        iterador.next().setAlinhamento(GridBagConstraints.CENTER);
        iterador.next().setAlinhamento(GridBagConstraints.CENTER);
        iterador.next().setAlinhamento(GridBagConstraints.EAST);
        iterador.next().setAlinhamento(GridBagConstraints.WEST);
        iterador.next().setAlinhamento(GridBagConstraints.EAST);
        iterador.next().setAlinhamento(GridBagConstraints.WEST);
        iterador.next().setAlinhamento(GridBagConstraints.CENTER);
        iterador.next().setAlinhamento(GridBagConstraints.CENTER);
        iterador.next().setAlinhamento(GridBagConstraints.SOUTH);

        iterador = listaConstrains.iterator();

        iterador.next().insets(0, 0, 0, 0);
        iterador.next().insets(0, 5, 0, 0);
        iterador.next().insets(5, 0, 0, 0);
        iterador.next().insets(0, 0, 0, 0);
        iterador.next().insets(0, 0, 0, 0);
        iterador.next().insets(0, 0, 0, 0);
        iterador.next().insets(0, 0, 0, 0);
        iterador.next().insets(0, 0, 0, 0);
        iterador.next().insets(5, 5, 0, 0);
        iterador.next().insets(5, 10, 0, 0);
        iterador.next().insets(10, 5, 0, 0);
        iterador.next().insets(5, 5, 0, 0);
        iterador.next().insets(0, 0, 0, 0);
        iterador.next().insets(0, 0, 0, 0);
        iterador.next().insets(0, 0, 0, 0);

        iterador = listaConstrains.iterator();

        
        iterador.next().setPreenchimento(GridBagConstraints.BOTH);
        iterador.next().setPreenchimento(GridBagConstraints.NONE);
        for (int i = 0; i < 12; i++) {
            iterador.next().setPreenchimento(GridBagConstraints.NONE);
        }
        iterador.next().setPreenchimento(GridBagConstraints.HORIZONTAL);

        iterador = listaConstrains.iterator();

        iterador.next().setPeso(0, 0);
        for (int i = 0; i < 13; i++) {
            iterador.next().setPeso(1, 1);
        }
        iterador.next().setPeso(0, 0);

        ArrayList<JComponent> componentes = new ArrayList<JComponent>();

        JPanel qe = new JPanel();
        qe.setPreferredSize(new Dimension(800, 600));
        qe.setSize(new Dimension(800, 600));
        qe.setBackground(Color.red);
        componentes.add(qe);

        JLabel jlNivel = new JLabel("Label nº de nivel");
        componentes.add(jlNivel);

        JTextField jtfNivel = new JTextField();
        jtfNivel.setColumns(4);
        componentes.add(jtfNivel);

        for (int i = 1; i < 5; i++) {
            componentes.add(new JBola(TipoBola.get(i)));
        }
        JPanel painelFundo = new JPanel();
        painelFundo.setToolTipText("Painel fundo");
        componentes.add(painelFundo);

        JLabel jlVX = new JLabel("Label velocidade x");
        componentes.add(jlVX);

        JTextField jtfVX= new JTextField();
        jtfVX.setColumns(4);
        componentes.add(jtfVX);

        JLabel jlVY = new JLabel("Label velocidade y");
        componentes.add(jlVY);

        JTextField jtfVY= new JTextField();
        jtfVY.setColumns(4);
        componentes.add(jtfVY);

        JButton jbCarregar = new JButton("Carregar");
        componentes.add(jbCarregar);

        JButton jbSalvar =new JButton("Salvar");
        componentes.add(jbSalvar);

        JLabel jlStatusBar = new JLabel("Status");
        componentes.add(jlStatusBar);

        iterador = listaConstrains.iterator();
        for (JComponent jComponent : componentes) {
            painel.add(jComponent, iterador.next().getGBC());
        }
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private static JLabel criaLabelTest(String texto) {
        JLabel jlTeste = new JLabel(texto);
        jlTeste.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return jlTeste;
    }

    private static GridBagConstraints criaGBC(int ancora, int x, int y,
            int largura, int altura) {
        return criaGBC(ancora, x, y, largura, altura, GridBagConstraints.NONE);
    }

    private static GridBagConstraints criaGBC(int ancora, int x, int y,
            int largura, int altura, int preenchimento) {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc = new GridBagConstraints();
        gbc.anchor = ancora;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = largura;
        gbc.gridheight = altura;
        gbc.fill = preenchimento;

        return gbc;
    }
}
