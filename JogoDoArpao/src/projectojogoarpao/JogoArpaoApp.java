package projectojogoarpao;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

import projectojogoarpao.jogo.arpao.PainelJogoArpao;
import projectojogoarpao.jogo.arpao.editor.PainelEdicao;



@SuppressWarnings("serial")
public class JogoArpaoApp extends javax.swing.JFrame implements ActionListener {



	private enum Estado{
        principal,edicao,novo
    }
    public static void main(String args[]) {
        new JogoArpaoApp().setVisible(true);
    }
    private javax.swing.JMenu jMenuAjuda;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItemNovo;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JMenuItem jMenuItemEditar;
    private javax.swing.JMenu jMenuJogo;
    private PainelJogoArpao pJogoArpao;
    private Estado estadoActual;
    
    public JogoArpaoApp() {
        estadoActual = Estado.principal;
        initComponents();
        
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jMenuBar = new javax.swing.JMenuBar();
        jMenuJogo = new javax.swing.JMenu();
        jMenuItemNovo = new javax.swing.JMenuItem();
        jMenuItemSair = new javax.swing.JMenuItem();
        jMenuItemEditar = new javax.swing.JMenuItem();
        jMenuAjuda = new javax.swing.JMenu();
        pJogoArpao = new PainelJogoArpao();

        jMenuJogo.setText("Jogo");
        jMenuItemNovo.setText("Novo");
        jMenuItemEditar.setText("Editar");
        jMenuItemSair.setText("Sair");
        jMenuAjuda.setText("Ajuda");

        jMenuJogo.setMnemonic(KeyEvent.VK_ALT);
        
        jMenuBar.add(jMenuJogo);
        jMenuJogo.add(jMenuItemNovo);
        jMenuJogo.add(jMenuItemEditar);
        jMenuJogo.add(jMenuItemSair);
        jMenuBar.add(jMenuAjuda);
        setJMenuBar(jMenuBar);

        jMenuItemNovo.addActionListener(this);
        jMenuItemSair.addActionListener(this);
        jMenuItemEditar.addActionListener(this);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 600));
        setContentPane(panel);
        pack();
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jMenuItemNovo) {
            JPanel painelJA = new JPanel();
            painelJA.add(pJogoArpao);
            setContentPane(painelJA);
            pJogoArpao.requestFocus();
            pJogoArpao.novoJogo();
            estadoActual = Estado.novo;
            pack();
            return;
        }
        if (e.getSource() == jMenuItemEditar) {
            if (estadoActual.equals(Estado.edicao)) {
            	pack();
                return;
            }
            
            setContentPane(new PainelEdicao());
            estadoActual = Estado.edicao;
            return;
        }
        if (e.getSource() == jMenuItemSair) {
            System.exit(0);
        }
    }
}
