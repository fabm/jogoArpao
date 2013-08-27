/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo.fisica.movimentos;


public class MovimentoGravitico extends MovimentoLinear{
	@Override
	protected void deslocaY() {
		if (!stopY) {
			int g = 1;
			incVY(g);
			incY(getVy() * 6 / 10);
		}
	}
}
