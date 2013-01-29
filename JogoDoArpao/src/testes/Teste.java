/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.sql.SQLException;

import javax.swing.JPanel;

import projectojogoarpao.jogo.arpao.bd.BDTpontuacoes;

/**
 *
 * @author francisco
 */
public class Teste {
    //
    public static void testaPainel(JPanel painel){
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            BDTpontuacoes tPontuacoes = new BDTpontuacoes();
            tPontuacoes.inserirUtilizador("Monteiro", 20);
            tPontuacoes.fecha();
        } catch (SQLException ex) {
            System.out.println("Erro");
        }
    }
}
