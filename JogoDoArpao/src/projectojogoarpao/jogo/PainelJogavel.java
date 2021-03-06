/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.Timer;

import projectojogoarpao.jogo.arpao.JogoArpao;

/**
 *
 * @author francisco
 */
@SuppressWarnings("serial")
public class PainelJogavel extends JComponent implements
        ActionListener, KeyListener {
    
	private JogoArpao jogoArpao;
    private Timer timer;
    
    public PainelJogavel(JogoArpao jogoArpao){
        super();
        setPreferredSize(new Dimension(800, 600));
        this.jogoArpao = jogoArpao;
        iniciarQuadro();    	
    }
    public PainelJogavel() {
        super();
        setPreferredSize(new Dimension(800, 600));
        jogoArpao = new JogoArpao();
        iniciarQuadro();
    }
    
    protected ProcessadorJogo getProcessadorJogo(){
    	return jogoArpao;
    }
        
    private void iniciarQuadro(){
        addKeyListener(this);
        setFocusable(true);
        setDoubleBuffered(true);
        //Periodo em milisegundos = 1000/freq
        timer = new Timer(1000 / jogoArpao.getFPS(), this);        
    }
    
    protected void iniciarAnimacao() {
        timer.start();
    }
    
    public void pararAnimacao() {
        timer.stop();
    }
    
    public void actionPerformed(ActionEvent e) {
        
        Collection<Pintavel> colPintaveis =
                getProcessadorJogo().getColPintaveis();
        
        getProcessadorJogo().preProcessarJogo();
        if (colPintaveis != null) {
            for (Pintavel pintavel : colPintaveis) {
                Movivel movivel;
                if (pintavel instanceof Movivel) {
                    movivel = (Movivel) pintavel;
                    movivel.processarMovimento();
                }
                
            }
        }
        if (!getProcessadorJogo().processarJogo()) {
            
            pararAnimacao();
        }
        repaint();
    }
    
    public void keyTyped(KeyEvent e) {
        //não implementado
    }
    
    public void keyPressed(KeyEvent e) {
        Collection<ControlavelPorTeclado> colCT =
                getProcessadorJogo().getColContTeclado();
        if (colCT != null) {
            for (ControlavelPorTeclado cpt : colCT) {
                cpt.teclaPressionada(e);
            }
        }
    }
    
    public void keyReleased(KeyEvent e) {
        Collection<ControlavelPorTeclado> colCT =
                getProcessadorJogo().getColContTeclado();
        if (colCT != null) {
            for (ControlavelPorTeclado cpt : colCT) {
                cpt.teclaLargada(e);
            }
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        Collection<Pintavel> colPintaveis =
                getProcessadorJogo().getColPintaveis();
        if (colPintaveis != null) {
            for (Pintavel pintavel : colPintaveis) {
                pintavel.update(g2d, this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

	public void novoJogo() {
		iniciarAnimacao();
		jogoArpao.iniciarJogo();
	}
	
	public void fimJogo(){
		pararAnimacao();
	}
}
