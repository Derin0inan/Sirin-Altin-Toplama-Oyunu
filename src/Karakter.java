import java.util.ArrayList;

public abstract class Karakter {
	
	private int ID;
	
	private String isim;
	
	private String tur;
	
	private Lokasyon koordinat;
	
	public Karakter() {}
	
	public Karakter(int ID, String isim, String tur, Lokasyon koordinat) {
		super();
		this.ID = ID;
		this.isim = isim;
		this.tur = tur;
		this.koordinat = koordinat;
	}

	public abstract ArrayList<Lokasyon> enYakinMesafe(int [][] harita, Lokasyon koordinat);

	public int getID() {
		return ID;
	}

	public String getIsim() {
		return isim;
	}

	public String getTur() {
		return tur;
	}

	public Lokasyon getKoordinat() {
		return koordinat;
	}



	public void setIsim(String isim) {
		this.isim = isim;
	}

	public void setTur(String tur) {
		this.tur = tur;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public void setKoordinat(Lokasyon koordinat) {
		this.koordinat = koordinat;
	}

}
