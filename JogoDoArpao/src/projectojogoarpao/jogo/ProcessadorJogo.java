/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo;

import java.util.Collection;

/**
 *
 * Sincronizavel nos objectos visuais
 */
public interface ProcessadorJogo {

    public void preProcessarJogo();
    public boolean processarJogo();

    public Collection<Pintavel> getColPintaveis();

    public Collection<ControlavelPorTeclado> getColContTeclado();
}
