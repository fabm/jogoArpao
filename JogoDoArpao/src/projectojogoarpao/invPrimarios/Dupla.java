package projectojogoarpao.invPrimarios;

public class Dupla {
	private int a, b;

	public static Dupla cria(int a, int b) {
		Dupla dupla = new Dupla();
		dupla.a = a;
		dupla.b = b;
		return dupla;
	}
	
	public boolean estaEntre(int c){
		return c > a && c < b;
	}
}
