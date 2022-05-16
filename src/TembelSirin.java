
public class TembelSirin extends Oyuncu {
	
	private OyuncuAyrim oyuncuAyrim;

	public TembelSirin() {
		super();
	}
	
	public TembelSirin(OyuncuAyrim oyuncuAyrim, int oyuncuID,
					   String oyuncuAd, int sirinLira, Lokasyon koordinat) {
		super(oyuncuAyrim, oyuncuID, oyuncuAd, "Tembel", sirinLira, koordinat);
		this.oyuncuAyrim = oyuncuAyrim;
	}
}
