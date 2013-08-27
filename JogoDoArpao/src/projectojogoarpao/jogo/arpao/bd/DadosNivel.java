/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo.arpao.bd;

import java.util.ArrayList;

/**
 *
 * @author francisco
 */
public class DadosNivel {
    public String fundo;
    public int xPersonagem;
	public int yPersonagem;
    public ArrayList<DadosBola> bolas = new ArrayList<DadosBola>();
    public ArrayList<DadosEstrela> estrelas = new ArrayList<DadosEstrela>();
    public int num;
}
