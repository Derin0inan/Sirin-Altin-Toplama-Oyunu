
public class Puan extends Oyuncu {

	
	private Oyuncu oyuncu;
	
	private Dusman dusman;
	

	public Puan(Oyuncu oyuncu, Dusman dusman) {
		this.oyuncu = oyuncu;
		this.dusman = dusman;
	}


	@Override
	public int sirinLiraGoster() {
		if (dusman.getDusmanTur() == "Gargamel") {
			oyuncu.setSirinLira(oyuncu.getSirinLira() - oyuncu.getOyuncuDavranis().getGargamelHasar());

		} else if (dusman.getDusmanTur() == "Azman") {
			oyuncu.setSirinLira(oyuncu.getSirinLira() - oyuncu.getOyuncuDavranis().getAzmanHasar());
		}
		return oyuncu.getSirinLira();
	}
	
}
