/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo.arpao.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.JDBC;
import projectojogoarpao.excepcoes.ExcecaoSQL;

/**
 *
 * @author francisco
 */
public class BDJogoArpao {

    protected Connection ligacao;

    public BDJogoArpao() {
        criaLigacao();
    }

    public void criaLigacao() {
        try {
            DriverManager.registerDriver(new JDBC());
            ligacao = DriverManager.getConnection("jdbc:sqlite:jogoArpao.db");
            ligacao.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new ExcecaoSQL(ex);
        }
    }

    public void fecha() {
        try {
            ligacao.close();
        } catch (SQLException ex) {
            throw new ExcecaoSQL(ex);
        }
    }

    @Override
    @SuppressWarnings("FinalizeDeclaration")
    public final void finalize() throws Throwable {
        fecha();
        super.finalize();
    }
    
}
