package projectojogoarpao.excepcoes;

import java.io.File;

public class ExcecaoCarregamentoFicheiro extends ExcecaoTE {
    private static String criaMSG(File ficheiro){
        String msg = "Não foi possivel carregar o ficheiro " + 
                ficheiro.getAbsolutePath();
        if (ficheiro.exists()) {
            return msg+ ", apesar deste existir";
        }
        return msg;
    }
    public ExcecaoCarregamentoFicheiro(File ficheiro) {
        super(criaMSG(ficheiro));
    }
}
