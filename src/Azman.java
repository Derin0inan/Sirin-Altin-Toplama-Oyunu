import java.awt.Color;

public class Azman extends Dusman {

	private DusmanAyrim dusmanAyrim;

	public Azman(DusmanAyrim dusmanAyrim, int dusmanID, String dusmanAdi, Color hamleRengi, Lokasyon koordinat) {
		super(dusmanAyrim, dusmanID,dusmanAdi,Color.pink,"Azman", koordinat);
		this.dusmanAyrim = dusmanAyrim;
	}
}
