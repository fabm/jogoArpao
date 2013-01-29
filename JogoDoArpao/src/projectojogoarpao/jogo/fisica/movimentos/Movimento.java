package projectojogoarpao.jogo.fisica.movimentos;


public class Movimento {
	private int x, y;
	private int vy, vx;
	private int xmax, xmin, ymax, ymin;


	public int getXmax() {
		return xmax;
	}

	public void setXmax(int xmax) {
		this.xmax = xmax;
	}

	public int getXmin() {
		return xmin;
	}

	public void setXmin(int xmin) {
		this.xmin = xmin;
	}

	public int getYmax() {
		return ymax;
	}

	public void setYmax(int ymax) {
		this.ymax = ymax;
	}

	public int getYmin() {
		return ymin;
	}

	public void setYmin(int ymin) {
		this.ymin = ymin;
	}

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
			this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
			this.vy = vy;
		
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
			this.x = x;
	}

	public void setY(int y) {
			this.y = y;
	}

	public void incX(int x) {
		this.setX(this.getX() + x);
	}

	public void incY(int y) {
		this.setY(this.getY() + y);
	}

	public void incVX(int vx) {
		this.setVx(getVx() + vx);
	}

	public void incVY(int vy) {
		this.setVy(getVy() + vy);
	}

}
