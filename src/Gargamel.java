import java.awt.Color;

public class Gargamel extends Dusman {
	
	private DusmanAyrim dusmanAyrim;

	public Gargamel(DusmanAyrim dusmanAyrim, int dusmanID, String dusmanAd, Color hamleRengi, Lokasyon koordinat) {
		super(dusmanAyrim, dusmanID, dusmanAd, hamleRengi, "Gargamel", koordinat);
		this.dusmanAyrim = dusmanAyrim;
	}


}
