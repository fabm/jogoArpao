package projectojogoarpao.jogo.arpao.bd;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BDTpontuacoes extends BDJogoArpao {

    public BDTpontuacoes() {
        super();
    }

    public void inserirUtilizador(String nome, int pontuacao)
            throws SQLException {
        PreparedStatement ps = ligacao.prepareStatement(
                "insert into pontuacoes(nome,pontos) values (?,?);");
        ps.setString(1, nome);
        ps.setInt(2, pontuacao);
        ps.execute();
        ligacao.commit();
        ps.close();
    }
}
