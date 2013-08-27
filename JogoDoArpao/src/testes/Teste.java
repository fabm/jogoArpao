/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import projectojogoarpao.jogo.PainelJogavel;
import projectojogoarpao.jogo.arpao.PainelJogoArpao;
import projectojogoarpao.jogo.arpao.bd.BDTpontuacoes;

/**
 *
 * @author francisco
 */
public class Teste {
    
    public static void testaPainel(JPanel painel){
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	JFrame jFrame = new JFrame();
    	JPanel panel = new JPanel();
    	final PainelJogavel pja = new PainelJogavel(new JogoArpaoTestes());
    	panel.add(pja);
    	panel.setPreferredSize(new Dimension(800, 600));
    	jFrame.add(panel);
    	jFrame.setVisible(true);
    	jFrame.pack();
    	pja.novoJogo();
    	jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	jFrame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				pja.pararAnimacao();
				System.out.println("fechou");
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    
    
    
}
