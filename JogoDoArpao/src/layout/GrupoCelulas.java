package layout;

import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GrupoCelulas {

    public GrupoCelulas() {
        celulas = new ArrayList<CelulaPainel>();
        linhas = 0;
        colunas = 0;
        iniciox = 0;
        inicioy = 0;
        fimx = 0;
    }
    private ArrayList<CelulaPainel> celulas;
    private int linhas;
    private int colunas;
    private int inicioy;
    private int iniciox;
    private int fimx;

    
    public CelulaPainel adComponente(JComponent componente){
        return adComponente(componente, 1);
    }
    /**
     * Adiciona célula ao grupo
     * @param componente
     * @return CelulaPainel adicionada
     */
    public CelulaPainel adComponente(JComponent componente, int incremento) {
        CelulaPainel celulaPainel = new CelulaPainel(componente);
        celulaPainel.getGbc().gridx = colunas;
        celulaPainel.getGbc().gridy = linhas;
        celulaPainel.setLargura(incremento);

        celulas.add(celulaPainel);
        colunas+=incremento;
        if (colunas > fimx) {
            fimx = colunas;
        }
        return celulaPainel;
    }

    
    public CelulaPainel adComponenteNL(JComponent componente, int incremento) {
        CelulaPainel celulaPainel = adComponente(componente,incremento);
        colunas = 0;
        linhas++;
        return celulaPainel;
    }
    /**
     * Adiciona célula e muda de linha
     * @param componente
     * @return CelulaPainel adicionada
     */
    public CelulaPainel adComponenteNL(JComponent componente) {
        return adComponenteNL(componente, 1);
    }

    /**
     * desloca todas células na horizontal 
     * @param nColunas
     * negativo, desloca para a esquerda
     * e positivo para a direita
     * 
     */
    public void deslocaColunas(int nColunas) {
        iniciox += nColunas;
        fimx += nColunas;
        for (CelulaPainel celulaPainel : celulas) {
            celulaPainel.getGbc().gridx += nColunas;
        }
    }

    /**
     * desloca todas as células na vertical 
     * @param nLinhas
     * negativo, desloca para a cima
     * e positivo para a baixo
     * 
     */
    public void deslocaLinhas(int nLinhas) {
        inicioy += nLinhas;
        linhas += nLinhas;
        for (CelulaPainel celulaPainel : celulas) {
            celulaPainel.getGbc().gridy += nLinhas;
        }
    }

    /**
     * adiciona as células ao painel
     * @param painel 
     */
    public void adAoPainel(JPanel painel) {
        for (CelulaPainel celulaPainel : celulas) {
            painel.add(celulaPainel.getComponente(), celulaPainel.getGbc());
        }
    }

    public int getInicioX() {
        return iniciox;
    }

    public int getInicioY() {
        return inicioy;
    }

    public int getColunas() {
        return fimx - iniciox;
    }

    public int getLinhas() {
        return linhas - inicioy + 1;
    }
}
