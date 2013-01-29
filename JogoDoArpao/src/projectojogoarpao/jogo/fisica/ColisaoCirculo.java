package projectojogoarpao.jogo.fisica;

import projectojogoarpao.invPrimarios.Dupla;
import projectojogoarpao.jogo.arpao.Arpao;
import projectojogoarpao.jogo.fisica.GestorIntercepcoes.PosicaoColisao;

public class ColisaoCirculo {
	/*
	private Circulo circulo;

	public ColisaoCirculo(Circulo circulo) {
		this.circulo = circulo;
	}


	public boolean pontoDentro(int px, int py) {
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

	public void criaColisao(Circulo circulo,Rectangulo rectangulo) {
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

		Colisao colisao = rectangulo.;
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
			if (pontoDentro(rX2, rY2)) {
				colisao.direita = true;
				colisao.base = true;
			}
			// colisao tipo 6
		} else if (cXM < rX && cYM > rY2) {
			if(pontoDentro(rX, rY2)){
				colisao.esquerda = true;
				colisao.base = true;				
			}
			// colisao tipo 7
		} else if (cXM > rX2 && cYM < rY) {
			if(pontoDentro(rX2, rY)){
				colisao.direita = true;
				colisao.topo = true;
			}
			// colisao tipo 8
		} else if (cXM < rX && cYM < rY) {
			if(pontoDentro(rX, rY)){
				colisao.topo=true;
				colisao.esquerda = true;
			}
		}
	}
*/
}
