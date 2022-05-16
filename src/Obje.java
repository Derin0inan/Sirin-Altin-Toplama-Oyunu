
public class Obje {
	
	private String itemIsim;
	
	private int itemPuan;
	
    private int yerdeKalmaSuresi;
	
	private int bulunmaSuresi;
	
	private boolean aktif;
	
	private Lokasyon koordinat;

	public Obje() {
		
	}

	public Obje(String itemIsim, int itemPuan, int yerdeKalmaSuresi, int bulunmaSuresi, boolean aktif, Lokasyon koordinat) {
		this.itemIsim = itemIsim;
		this.itemPuan = itemPuan;
		this.yerdeKalmaSuresi = yerdeKalmaSuresi;
		this.bulunmaSuresi = bulunmaSuresi;
		this.aktif = aktif;
		this.koordinat = koordinat;
	}

	public String getItemIsim() {
		return itemIsim;
	}

	public int getItemPuan() {
		return itemPuan;
	}

	public int getYerdeKalmaSuresi() {
		return yerdeKalmaSuresi;
	}

	public int getBulunmaSuresi() {
		return bulunmaSuresi;
	}

	public Lokasyon getKoordinat() {
		return koordinat;
	}



	public void setItemIsim(String itemIsim) {
		this.itemIsim = itemIsim;
	}

	public void setItemPuan(int itemPuan) {
		this.itemPuan = itemPuan;
	}

	public void setYerdeKalmaSuresi(int yerdeKalmaSuresi) {
		this.yerdeKalmaSuresi = yerdeKalmaSuresi;
	}

	public void setBulunmaSuresi(int bulunmaSuresi) {
		this.bulunmaSuresi = bulunmaSuresi;
	}

	public void setAktif(boolean aktif) {
		this.aktif = aktif;
	}

	public void setKoordinat(Lokasyon koordinat) {
		this.koordinat = koordinat;
	}


	public boolean isAktif() {
		return aktif;
	}
	
}
