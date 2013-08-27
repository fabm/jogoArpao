package projectojogoarpao.invPrimarios;

public class Inteiro {
    private int valor;

    public Inteiro(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    public void inc(int incremento){
        valor+=incremento;
    }

    @Override
    public String toString() {
        return ""+valor;
    }
    
}
