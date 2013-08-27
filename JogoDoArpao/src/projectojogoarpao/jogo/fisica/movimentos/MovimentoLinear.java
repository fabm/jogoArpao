/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo.fisica.movimentos;


public class MovimentoLinear extends Movimento {

	public boolean stopX = false;
	public boolean stopY = false;

	protected void deslocaY() {
		if (!stopY) {
			incY(getVy());
		}
	}

	protected void deslocaX() {
		if (!stopX) {
			incX(getVx());
		}
	}

	public void move(){
		deslocaX();
		deslocaY();
	}
}
