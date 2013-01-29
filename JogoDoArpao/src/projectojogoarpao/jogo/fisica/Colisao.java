package projectojogoarpao.jogo.fisica;

public class Colisao {
	public boolean base = false, topo = false, esquerda = false,
			direita = false;

	public int x,y;
	public void reeniciar() {
		base = false;
		topo = false;
		esquerda = false;
		direita = false;
	}

	public boolean tem() {
		return base||esquerda||direita||topo;
	}
}
