
public class Lokasyon {

	private int x;
	private int y;
	private int value;


	public Lokasyon() {

	}

	public Lokasyon(int x, int y) {
		this.x = x;
		this.y = y;
	}



	public int getValue() {
		return value;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}



	public void setValue(int value) {
		this.value = value;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

}
