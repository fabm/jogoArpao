package projectojogoarpao.jogo.fisica;

import java.util.Iterator;
import java.util.LinkedList;

import projectojogoarpao.invPrimarios.Dupla;
import projectojogoarpao.jogo.arpao.Arpao;
import testes.Debugger;

public class GestorIntercepcoes<T extends Circulo>{
	/**
	 * @author Francisco Posições da colisão do primeiro objecto em relação ao
	 *         segundo
	 */
	public enum PosicaoColisao {
		semColisao, emBaixo, emCima, aEsquerda, aDireida
	}

	
	private LinkedList<T> circulos;
	private LinkedList<? extends Rectangulo> rectangulos;
	private Iterator<T> iterador = null;
	
	public Circulo circuloInterceptado = null;
	public Rectangulo rectanguloInterceptado = null;
	public PosicaoColisao posicacoColisao;

	public GestorIntercepcoes(LinkedList<T> circulos) {
		this.circulos = circulos;
	}

	public void setCirculos(LinkedList<T> circulos) {
		this.circulos = circulos;
	}

	public boolean interceptadoComRectangulos(Rectangulo rectangulo) {
		if (rectangulos == null) {
			rectanguloInterceptado = null;
			return false;
		}
		for (Rectangulo rectangulo2 : rectangulos) {
			atualizaColisao(rectangulo, rectangulo2);
			if (rectangulo.getColisao().tem()) {
				rectanguloInterceptado = rectangulo2;
				return true;
			}
		}
		rectanguloInterceptado = null;
		return false;
	}

	public boolean interceptadoComCirculos(Rectangulo rectangulo) {
		if (circulos == null) {
			circuloInterceptado = null;
			return false;
		}
		for (T circulo : circulos) {
			atualizaColisao(circulo, rectangulo);
			if (circulo.getColisao().tem()) {
				circuloInterceptado = circulo;
				return true;
			}
		}
		circuloInterceptado = null;
		return false;
	}

	public T interceptado(int x, int y) {
		Iterator<T> ic = circulos.descendingIterator();
		while (ic.hasNext()) {
			T circulo = ic.next();
			
			if (pontoDentro(circulo, x, y)) {
				return circulo;
			}
		}
		return null;
	}

	public LinkedList<T> interceptados(Rectangulo rectangulo) {
		LinkedList<T> interceptados = new LinkedList<T>();
		for (T circulo : circulos) {
			if (estaDentroDe(circulo,rectangulo)) {
				interceptados.add(circulo);
			}
		}
		return interceptados;
	}

	public boolean cicloIntercepcoes() {
		if (iterador == null) {
			circuloInterceptado = null;
			iterador = circulos.iterator();
		}
		if (iterador.hasNext()) {
			for (Rectangulo obstaculo : rectangulos) {
				if (interceptadoComCirculos(obstaculo)) {
					iterador.next();
					return true;
				}
			}
		}
		iterador = null;
		return false;
	}

	public void setRectangulos(LinkedList<? extends Rectangulo> obstaculos) {
		this.rectangulos = obstaculos;
	}
	
	public static boolean estaDentroDe(Circulo circulo, Rectangulo rectangulo) {

		if (circulo.getX() < rectangulo.getX()) {
			return false;
		}
		if (circulo.getX() + circulo.getDiametro() > rectangulo.getX()
				+ rectangulo.getLargura()) {
			return false;
		}
		if (circulo.getY() < rectangulo.getY()) {
			return false;
		}
		if (circulo.getY() + circulo.getDiametro() > rectangulo.getX()
				+ rectangulo.getLargura()) {
			return false;
		}
		return true;
	}

	public static boolean pontoDentro(Circulo circulo, int px, int py) {
		int cR = circulo.getDiametro() / 2;
		int cX = circulo.getX();
		int cX2 = cX + circulo.getDiametro();
		int cY = circulo.getY();
		int cY2 = cY + circulo.getDiametro();

		// verifica se está dentro do pseudo quadrado que envolve o cirulo
		if (px > cX && px < cX2 && py > cY && py < cY2) {
			int xr, yr = 0;
			// verifica se o ponto pertence ao 2º quadrante
			if (px < cX + cR && py < cY + cR) {
				xr = cX2 - px + cX;
				yr = py;
				// verifica se o ponto pertence ao 3º quadrante
			} else if (px < cX + cR && py > cY + cR) {
				xr = cX2 - px + cX;
				yr = cY2 - py + cY;
				// verifica se o ponto pertence ao 4º quadrante
			} else if (px > cX + cR && py > cY + cR) {
				xr = px;
				yr = cY2 - py + cY;
				// verifica se o ponto pertence ao 1º quadrante
			} else {
				xr = px;
				yr = py;
			}
			int h2 = cR * cR;
			int y2 = cY + cR - yr;
			y2 *= y2;
			double xcirc;
			xcirc = cX + cR + Math.sqrt(h2 - y2);

			if (xcirc >= xr) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}

	public static void atualizaColisao(Rectangulo rectangulo,Rectangulo rectangulo2){
		Colisao colisao = rectangulo.getColisao();
		int r1x = rectangulo.getX();
		int r2x = rectangulo2.getX();
		int r1x2 = rectangulo.getX() + rectangulo.getLargura();
		int r2x2 = rectangulo2.getX() + rectangulo2.getLargura();
		int r1y = rectangulo.getY();
		int r2y = rectangulo2.getY();
		int r1y2 = rectangulo.getY() + rectangulo.getAltura();
		int r2y2 = rectangulo2.getY() + rectangulo2.getAltura();

		if (r1x > r2x2 || r1x2 < r2x || r1y > r2y2 || r1y2 < r2y) {
			return;
		}
		if (r1y < r2y) {
			colisao.base = true;
			// colisao tipo 2
		} else if (r1x2 > r2x2) {
			colisao.esquerda = true;
			// colisao tipo 3
		} else if (r1y2 > r2y2) {
			colisao.topo = true;
			// colisao tipo 4
		}else
			colisao.direita = true;
		Debugger.get().printf("colisao e:%s\n",colisao.esquerda);
		Debugger.get().printf("colisao d:%s\n",colisao.direita);
		Debugger.get().printf("colisao t:%s\n",colisao.topo);
		Debugger.get().printf("colisao b:%s\n",colisao.base);
	}
	
	public static void atualizaColisao(
			Circulo circulo,Rectangulo rectangulo) {
		int rX = rectangulo.getX();
		int rX2 = rX + rectangulo.getLargura();
		int rY = rectangulo.getY();
		int rY2 = rY + rectangulo.getAltura();
		int cR = circulo.getDiametro() / 2;
		int cX = circulo.getX();
		int cXM = cX + cR;
		int cX2 = cX + circulo.getDiametro();
		int cY = circulo.getY();
		int cYM = cY + cR;
		int cY2 = cY + circulo.getDiametro();
		if (rectangulo instanceof Arpao) {
			Arpao arpao = (Arpao) rectangulo;
			rX = arpao.getX();
			rX2 = rX + rectangulo.getLargura();
			rY = arpao.getY();
			rY2 = rY + rectangulo.getAltura();
		}

		Colisao colisao = circulo.getColisao();
		if (rX > cX2 || rX2 < cX || rY > cY2 || rY2 < cY) {
			return;
		}
		if (Dupla.cria(rX, rX2).estaEntre(cXM) && rY < cY) {
			colisao.base = true;
			// colisao tipo 2
		} else if (Dupla.cria(rY, rY2).estaEntre(cYM) && rX2 > cX2) {
			colisao.direita = true;
			// colisao tipo 3
		} else if (Dupla.cria(rX, rX2).estaEntre(cXM) && rY2 > cY2) {
			colisao.topo = true;
			// colisao tipo 4
		} else if (Dupla.cria(rY, rY2).estaEntre(cYM) && rX < cX) {
			colisao.esquerda = true;
			// colisao tipo 5
		} else if (cXM > rX2 && cYM > rY2) {
			if (pontoDentro(circulo,rX2, rY2)) {
				colisao.base = true;
			}
			// colisao tipo 6
		} else if (cXM < rX && cYM > rY2) {
			if(pontoDentro(circulo,rX, rY2)){
				colisao.base = true;				
			}
			// colisao tipo 7
		} else if (cXM > rX2 && cYM < rY) {
			if(pontoDentro(circulo,rX2, rY)){
				colisao.topo = true;
			}
			// colisao tipo 8
		} else if (cXM < rX && cYM < rY) {
			if(pontoDentro(circulo,rX, rY)){
				colisao.topo=true;
			}
		}
	}

}
