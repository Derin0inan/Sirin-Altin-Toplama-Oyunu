import java.util.ArrayList;

public class Oyuncu extends Karakter {

	private int oyuncuID;

	private String oyuncuAd;

	private String oyuncuTur;

	private int sirinLira;

	private OyuncuAyrim oyuncuAyrim;

	public Oyuncu() {
		super();
	}


	public Oyuncu(OyuncuAyrim oyuncuAyrim, int oyuncuID,
				  String oyuncuAd, String oyuncuTur, int sirinLira, Lokasyon koordinat) {
		super(oyuncuID, oyuncuAd, oyuncuTur, koordinat);
		this.oyuncuAyrim = oyuncuAyrim;
		this.oyuncuID = oyuncuID;
		this.oyuncuAd = oyuncuAd;
		this.oyuncuTur = oyuncuTur;
		this.sirinLira = sirinLira;
	}


	public int sirinLiraGoster() {
		return sirinLira;
	}

	@Override
	public ArrayList<Lokasyon> enYakinMesafe(int[][] harita, Lokasyon lokasyon) {return null;}


	public int getOyuncuID() {
		return oyuncuID;
	}
	public String getOyuncuAd() {
		return oyuncuAd;
	}
	public String getOyuncuTur() {
		return oyuncuTur;
	}
	public int getSirinLira() {
		return sirinLira;
	}
	public OyuncuAyrim getOyuncuDavranis() {
		return oyuncuAyrim;
	}


	public void setOyuncuID(int oyuncuID) {
		this.oyuncuID = oyuncuID;
	}
	public void setOyuncuAd(String oyuncuAd) {
		this.oyuncuAd = oyuncuAd;
	}
	public void setOyuncuTur(String oyuncuTur) {
		this.oyuncuTur = oyuncuTur;
	}
	public void setSirinLira(int sirinLira) {
		this.sirinLira = sirinLira;
	}
	public void setOyuncuDavranis(OyuncuAyrim oyuncuAyrim) {
		this.oyuncuAyrim = oyuncuAyrim;
	}

}


