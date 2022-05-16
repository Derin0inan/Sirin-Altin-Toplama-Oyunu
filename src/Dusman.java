import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;

public class Dusman extends Karakter {

	private int dusmanID;

	private String dusmanAd;

	private String dusmanTur;

	private Color hamleRengi;

	private DusmanAyrim dusmanAyrim;

	private Lokasyon baslangicKonum;

	public Dusman() {
	}

	public Dusman(DusmanAyrim dusmanAyrim, int dusmanID, String dusmanAd, Color hamleRengi, String dusmanTur,
				  Lokasyon koordinat) {
		super(dusmanID, dusmanAd, dusmanTur, koordinat);
		this.dusmanAyrim = dusmanAyrim;
		this.dusmanID = dusmanID;
		this.dusmanAd = dusmanAd;
		this.dusmanTur = dusmanTur;
		this.hamleRengi = hamleRengi;
	}

	@Override
	public ArrayList<Lokasyon> enYakinMesafe(int[][] harita, Lokasyon oyuncuLokasyon) {
		ArrayList<Lokasyon> koordinatlar = new ArrayList<Lokasyon>();

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 13; j++) {
				if (this.getKoordinat().getX() == i && this.getKoordinat().getY() == j) {
					this.getKoordinat().setValue(0);
					koordinatlar.add(this.getKoordinat());
				} else {
					Lokasyon koordinat = new Lokasyon(i, j);
					koordinat.setValue(Integer.MAX_VALUE);
					koordinatlar.add(koordinat);
				}

			}
		}

		ArrayList<Lokasyon> liste = new ArrayList<Lokasyon>();
		Hashtable<Lokasyon, Lokasyon> tablo = new Hashtable<Lokasyon, Lokasyon>();

		liste.add(this.getKoordinat());

		Lokasyon ilkLokasyon = this.getKoordinat();
		int ilkLokasyonDeger = 0;

		for (int i = 1; i < koordinatlar.size(); i++) {
			ArrayList<Lokasyon> komsular = komsuAra(harita, oyuncuLokasyon, koordinatlar, ilkLokasyon);
			for (Lokasyon komsu : komsular)
				if (!liste.contains(komsu)) {
					int yeniDeger = ilkLokasyonDeger + 1;
					if (yeniDeger < komsu.getValue()) {
						komsu.setValue(yeniDeger);
						tablo.put(komsu, ilkLokasyon);
					}
				}
			Lokasyon yeniLokasyon = null;
			int yeniLokasyonDeger = Integer.MAX_VALUE;
			for (Lokasyon l : koordinatlar)
				if (!liste.contains(l)) {
					int piV = l.getValue();
					if (piV < yeniLokasyonDeger) {
						yeniLokasyon = l;
						yeniLokasyonDeger = piV;
					}
				}
			if (yeniLokasyon == null)
				break;
			ilkLokasyon = yeniLokasyon;
			ilkLokasyonDeger = yeniLokasyonDeger;
			liste.add(ilkLokasyon);

		}

		ArrayList<Lokasyon> yol = new ArrayList<Lokasyon>();
		Lokasyon lokasyon = null;

		for (Lokasyon l : koordinatlar) {
			if (l.getX() == oyuncuLokasyon.getX() && l.getY() == oyuncuLokasyon.getY()) {
				lokasyon = l;
				break;
			}
		}

		while (lokasyon != null) {
			yol.add(lokasyon);
			lokasyon = tablo.get(lokasyon);
		}
		return yol;
	}

	private ArrayList<Lokasyon> komsuAra(int[][] harita, Lokasyon oyuncuLokasyon, ArrayList<Lokasyon> koordinatlar,
										 Lokasyon current) {

		ArrayList<Lokasyon> komsuNoktalar = new ArrayList<Lokasyon>();

		if (current.getX() - 1 > 0) {
			for (Lokasyon koordinat : koordinatlar) {
				if (harita[current.getX() - 1][current.getY()] == 1 && koordinat.getX() == current.getX() - 1
						&& koordinat.getY() == current.getY()) {
					komsuNoktalar.add(koordinat);
					break;
				}
			}
		}

		if (current.getX() + 1 < 11) {
			for (Lokasyon lokasyon : koordinatlar) {
				if (harita[current.getX() + 1][current.getY()] == 1 && lokasyon.getX() == current.getX() + 1
						&& lokasyon.getY() == current.getY()) {
					komsuNoktalar.add(lokasyon);
					break;
				}
			}
		}

		if (current.getY() - 1 > 0) {
			for (Lokasyon lokasyon : koordinatlar) {
				if (harita[current.getX()][current.getY() - 1] == 1 && lokasyon.getX() == current.getX()
						&& lokasyon.getY() == current.getY() - 1) {
					komsuNoktalar.add(lokasyon);
					break;
				}
			}
		}

		if (current.getY() + 1 < 13) {
			for (Lokasyon lokasyon : koordinatlar) {
				if (harita[current.getX()][current.getY() + 1] == 1 && lokasyon.getX() == current.getX()
						&& lokasyon.getY() == current.getY() + 1) {
					komsuNoktalar.add(lokasyon);
					break;
				}
			}
		}

		return komsuNoktalar;
	}



	public int getDusmanID() {
		return dusmanID;
	}
	public String getDusmanAd() {
		return dusmanAd;
	}
	public String getDusmanTur() {
		return dusmanTur;
	}
	public Color getColor() {
		return hamleRengi;
	}
	public DusmanAyrim getDusmanAyrim() {
		return dusmanAyrim;
	}
	public Lokasyon getBaslangicKonum() {
		return baslangicKonum;
	}


	public void setBaslangicKonum(Lokasyon baslangicKonum) {
		this.baslangicKonum = baslangicKonum;
	}
	public void setDusmanAd(String dusmanAd) {
		this.dusmanAd = dusmanAd;
	}
	public void setDusmanID(int dusmanID) {
		this.dusmanID = dusmanID;
	}
	public void setDusmanDavranis(DusmanAyrim dusmanAyrim) {
		this.dusmanAyrim = dusmanAyrim;
	}
	public void setDusmanTur(String dusmanTur) {
		this.dusmanTur = dusmanTur;
	}
	public void setColor(Color color) {
		this.hamleRengi = color;
	}



}
