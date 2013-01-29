package projectojogoarpao.jogo.arpao.editor;

import projectojogoarpao.jogo.arpao.bolas.TipoBola;

public interface AccoesQE {

    public int getNumNivel();

    public String getFundo();
    TipoBola getTipoBola();
    TipoAccao getAccaoActual();
    void setAutorizacaoRemocao(boolean autorizacao);
}
