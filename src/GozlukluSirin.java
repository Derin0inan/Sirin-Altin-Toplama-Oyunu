
public class GozlukluSirin extends Oyuncu {
	
	private OyuncuAyrim oyuncuAyrim;

	public GozlukluSirin() {
		super();
	}
	
	public GozlukluSirin(OyuncuAyrim oyuncuAyrim, int oyuncuID, String oyuncuAd, int sirinLira, Lokasyon koordinat) {
		super(oyuncuAyrim, oyuncuID, oyuncuAd, "Gozluklu", sirinLira, koordinat);
		this.oyuncuAyrim = oyuncuAyrim;
	}

}
