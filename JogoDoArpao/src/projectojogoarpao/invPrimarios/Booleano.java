package projectojogoarpao.invPrimarios;
public class Booleano {
    private boolean valor;

    public Booleano(boolean valor) {
        this.valor = valor;
    }

    public boolean activo() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }
    public boolean alterna(){
        valor = !valor;
        return valor;
    }
}
