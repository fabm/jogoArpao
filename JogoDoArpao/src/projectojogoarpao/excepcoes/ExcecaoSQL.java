package projectojogoarpao.excepcoes;

import java.sql.SQLException;

public class ExcecaoSQL extends ExcecaoTE {

    public ExcecaoSQL(SQLException ex) {
        super("Erro sql\n" + ex.getMessage());
        ex.printStackTrace();
    }
}
