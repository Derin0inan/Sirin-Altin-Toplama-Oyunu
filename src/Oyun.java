import javax.imageio.ImageIO;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static javax.swing.JOptionPane.showMessageDialog;

public class Oyun extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel puan;
	private int[][] harita;
	private Oyuncu oyuncu;
	private ArrayList<Dusman> sirinDusmanlari;
	private JButton[][] buttons;
	private ArrayList<Karakter> karakterler = new ArrayList<Karakter>();
	private ArrayList<Obje> altinlar = new ArrayList<Obje>();

	private ArrayList<Lokasyon> gecilenYollar = new ArrayList<Lokasyon>();

	private Obje mantar;
	private int labirentSutun = 11;
	private int labirentSatir = 13;

	private int altinSayi = 5;
	private Random rastgeleAta = new Random();

	public Oyun(Oyuncu oyuncu) {

		readFile("/resimler/harita.txt");

		setTitle("Sirine'yi Kurtar");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.white);

		setSize(640, 480);
		setLocationRelativeTo(null);
		setResizable(true);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.lightGray);

		ImageIcon sirineIcon = getIconImage("sirine", 60, 60);
		JLabel sirine = new JLabel(sirineIcon);
		sirine.setBounds(540, 260, 40, 40);

		puan = new JLabel("ŞirinLira : " + oyuncu.sirinLiraGoster());
		puan.setForeground(Color.RED);
		puan.setBounds(460, 5, 220, 10);

		panel.add(puan);
		panel.add(sirine);
		add(panel);

		this.oyuncu = oyuncu;



		haritaOlustur(panel);
		haritaCharEkle();

		Thread altin = new Thread(() -> {
			altinKontrol();
		});

		altin.start();
		Thread mantar = new Thread(() -> {
			mantarKontrol();
		});
		mantar.start();

		setVisible(true);

		dusmanYol(null,false);

	}


	//dusmanın geçtiği yollar bu method ile boyanır
	private synchronized void dusmanYol(Dusman geciciDusman, boolean ilerlet) {
		ArrayList<Lokasyon> yol;
		if (gecilenYollar != null) {
			for (Lokasyon koordinat : gecilenYollar) {
				buttons[koordinat.getX()][koordinat.getY()].setBackground(Color.white);
			}
			gecilenYollar.clear();
		}

		//oyuncuya en yakın mesafeyi boyar
		for (Dusman dusman : sirinDusmanlari) {
			if(!dusman.equals(geciciDusman)) {
				yol = dusman.enYakinMesafe(harita, oyuncu.getKoordinat());
				gecilenYollar.addAll(yol);
				for (Lokasyon koordinat : yol) {
					buttons[koordinat.getX()][koordinat.getY()].setBackground(dusman.getColor());
				}
				if (ilerlet)
					dusmanIlerle(dusman, yol);
			}
		}
	}


	//Dusmanın ilerlemesini sağlayan method
	private synchronized void dusmanIlerle(Dusman dusman, ArrayList<Lokasyon> yol) {
		Lokasyon yeniKoordinat;
		if (yol.size() > dusman.getDusmanAyrim().getAdim()) {
			yeniKoordinat = yol.get(yol.size() - dusman.getDusmanAyrim().getAdim() - 1);
			for (int i = 0; i < dusman.getDusmanAyrim().getAdim(); i++) {
				if(buttons[yol.get(yol.size() - i - 1).getX()][yol.get(yol.size() - i - 1).getY()].getBackground() == dusman.getColor() )
					buttons[yol.get(yol.size() - i - 1).getX()][yol.get(yol.size() - i - 1).getY()].setBackground(Color.WHITE);
			}

			//hedef alan boşsa koordinat düzenlemesi yapılır ve eski ikonları siler
			if (blokKontrol(yeniKoordinat.getX(), yeniKoordinat.getY(), false)) {
				buttons[yeniKoordinat.getX()][yeniKoordinat.getY()]
						.setIcon(buttons[dusman.getKoordinat().getX()][dusman.getKoordinat().getY()].getIcon());
				buttons[dusman.getKoordinat().getX()][dusman.getKoordinat().getY()].setIcon(null);
				dusman.setKoordinat(yeniKoordinat);

			}
			//hedef alanda oyuncu varsa karşılaşma durumu olur
			else if (yeniKoordinat.getX() == oyuncu.getKoordinat().getX()
					&& yeniKoordinat.getY() == oyuncu.getKoordinat().getY()) {
				karsilasma(dusman);
			}

			dusmanYol(null,false);
		}
	}

	private synchronized void karsilasma(Dusman dusman) {
		Oyuncu oyuncuPuan =  new Puan(this.oyuncu,dusman);

		int puan = oyuncuPuan.sirinLiraGoster();

		Thread thread3 = new Thread(() -> {
			sonSirinLira(puan);
		});

		thread3.start();

		if (puan <= 0) {
			showMessageDialog(null, "Tum SirinLiranızı Kaybettiniz. Yenildiniz!");
			dispose();
			System.exit(0);
		}

		//eğer oyun bitmemişse sirinlira hala duruyorsa düşmanı başladığı ilk noktaya gönder.
		else {
			buttons[dusman.getBaslangicKonum().getX()][dusman.getBaslangicKonum().getY()]
					.setIcon(buttons[dusman.getKoordinat().getX()][dusman.getKoordinat().getY()].getIcon());

			//karşılaşma noktasında bulunan ikonu siler
			if(dusman.getKoordinat().getX() != dusman.getBaslangicKonum().getX() && dusman.getKoordinat().getY() != dusman.getBaslangicKonum().getY()) {
				buttons[dusman.getKoordinat().getX()][dusman.getKoordinat().getY()].setIcon(null);
			}

			dusman.setKoordinat(dusman.getBaslangicKonum());
		}

	}

	private void altinKontrol() {

		int rastgeleX, rastgeleY;
		try {
			while (true) {
				Thread.sleep((rastgeleAta.nextInt(10) + 1) * 1000);

				for (int i = 0; i < altinSayi; i++) {

					rastgeleX = 0;
					rastgeleY = 0;

					//altın kordinat değerleri duvarla, kapıyla, mantarla veya karakterle çakışıyorsa altını harita dışına atar.
					while (harita[rastgeleX][rastgeleY] == 0 || !blokKontrol(rastgeleX, rastgeleY, true)
							|| kapiKoordinat(rastgeleX, rastgeleY) != "")
					{
						rastgeleX = rastgeleAta.nextInt(labirentSutun);
						rastgeleY = rastgeleAta.nextInt(labirentSatir);
					}

					altinlar.add(new Altin("Altin", 5, 10, 5, true, new Lokasyon(rastgeleX, rastgeleY)));
				}


				for (Obje altin : altinlar) {

					//altını boş alana koy
					if (blokKontrol(altin.getKoordinat().getX(), altin.getKoordinat().getY(), false)) {
						buttons[altin.getKoordinat().getX()][altin.getKoordinat().getY()]
								.setIcon(getIconImage("altin", 50, 40));

						//oyuncu altın üstündeyse puanı güncelle altını al
					} else if (altin.getKoordinat().getX() == oyuncu.getKoordinat().getX()
							&& altin.getKoordinat().getY() == oyuncu.getKoordinat().getY()) {
						oyuncu.setSirinLira(oyuncu.getSirinLira() + altin.getItemPuan());
						sonSirinLira(oyuncu.sirinLiraGoster());
					}

				}

				Thread.sleep(5 * 1000);
				for (Obje altin : altinlar) {
					if (altin.isAktif() && blokKontrol(altin.getKoordinat().getX(), altin.getKoordinat().getY(), false)) {
						buttons[altin.getKoordinat().getX()][altin.getKoordinat().getY()].setIcon(null);
					}
				}
				altinlar.clear();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void mantarKontrol() {
		int rastgeleX, rastgeleY;

		try {
			while (true) {
				Thread.sleep((rastgeleAta.nextInt(20) + 1) * 1000);

				rastgeleX = 0;
				rastgeleY = 0;

				while (harita[rastgeleX][rastgeleY] == 0 || !blokKontrol(rastgeleX, rastgeleY, true) || kapiKoordinat(rastgeleX, rastgeleY) != "") {
					rastgeleX = rastgeleAta.nextInt(labirentSutun);
					rastgeleY = rastgeleAta.nextInt(labirentSatir);
				}

				mantar = new Mantar("Mantar", 50, 20, 7, true, new Lokasyon(rastgeleX, rastgeleY));

				if (blokKontrol(mantar.getKoordinat().getX(), mantar.getKoordinat().getY(), false)) {

					buttons[mantar.getKoordinat().getX()][mantar.getKoordinat().getY()]
							.setIcon(getIconImage("mantar", 25, 25));

				} else if (mantar.getKoordinat().getX() == oyuncu.getKoordinat().getX()
						&& mantar.getKoordinat().getY() == oyuncu.getKoordinat().getY()) {
					oyuncu.setSirinLira(oyuncu.getSirinLira() + mantar.getItemPuan());
					sonSirinLira(oyuncu.sirinLiraGoster());
				}

				Thread.sleep(mantar.getBulunmaSuresi() * 1000);
				if (mantar.isAktif() && blokKontrol(mantar.getKoordinat().getX(), mantar.getKoordinat().getY(), false)) {
					buttons[mantar.getKoordinat().getX()][mantar.getKoordinat().getY()].setIcon(null);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean blokKontrol(int x, int y, boolean check) {

		charDurum();

		for (Karakter karakter : karakterler) {
			if (karakter.getKoordinat().getX() == x && karakter.getKoordinat().getY() == y)
				return false;
		}
		if (check) {
			for (Obje obje : altinlar) {
				if (obje.getKoordinat().getX() == x && obje.getKoordinat().getY() == y)
					return false;
			}
			if (mantar != null) {
				if (mantar.getKoordinat().getX() == x && mantar.getKoordinat().getY() == y)
					return false;
			}
		}

		return true;
	}

	private void charDurum() {
		karakterler.clear();
		karakterler.add(oyuncu);
		karakterler.addAll(sirinDusmanlari);
	}

	private synchronized void hareketEt(int keyCode) throws InterruptedException {

		int x, y, adim;

		x = oyuncu.getKoordinat().getX();
		y = oyuncu.getKoordinat().getY();

		adim = oyuncu.getOyuncuDavranis().getAdim();
		Dusman dusman;
		Dusman temp;

		switch (keyCode) {

		case KeyEvent.VK_UP:
			if (x - adim > 0) {

				//geçilmeyen bloğa rastlanmadıysa bu if e girer
				if (harita[x - adim][y] != 0) {

					dusman = null;

					for (int i = 1; i <= adim; i++) {

						//her adım için geçilmeyen bloğa rastlıyor mu kontrol ediyor

						if (harita[x - i][y] != 0) {
							objeDurum(x - i, y);

							temp = dusmanKontrol(x - i, y);
							if(temp != null) {
								dusman  = temp;
							}

							oyuncu.getKoordinat().setX(x - i);

							buttons[x - i][y].setIcon(buttons[x - i + 1][y].getIcon());
							buttons[x - i + 1][y].setIcon(null);

							Thread.sleep(250);
							if(i == adim) {
								dusmanYol(dusman,true);
							}
						}
						else {
							break;
						}

					}

				}
			}
			break;
		case KeyEvent.VK_DOWN:
			if (x + adim < labirentSutun) {
				if (harita[x + adim][y] != 0) {
					dusman = null;
					for (int i = 1; i <= adim; i++) {
						if (harita[x + i][y] != 0) {
							objeDurum(x + i, y);
							temp = dusmanKontrol(x + i, y);
							if(temp != null) {
								dusman  = temp;
							}
							oyuncu.getKoordinat().setX(x + i);
							buttons[x + i][y].setIcon(buttons[x + i - 1][y].getIcon());
							buttons[x + i - 1][y].setIcon(null);
							Thread.sleep(250);
							if(i == adim) {
								dusmanYol(dusman,true);
							}
						} else {
							break;
						}
					}
				}
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (y + adim < labirentSatir) {
				if (harita[x][y + adim] != 0) {
					dusman = null;
					for (int i = 1; i <= adim; i++) {
						if (harita[x][y + i] != 0) {
							objeDurum(x, y + i);
							temp = dusmanKontrol(x, y + i);
							if(temp != null) {
								dusman  = temp;
							}
							oyuncu.getKoordinat().setY(y + i);
							buttons[x][y + i].setIcon(buttons[x][y + i - 1].getIcon());
							buttons[x][y + i - 1].setIcon(null);
							Thread.sleep(250);
							if (x == 7 && (y + i) == 12) {
								showMessageDialog(null, "Sirine'ye Ulastin! Sirinlendin!");
								dispose();
								System.exit(0);
							}
							if(i == adim) {
								dusmanYol(dusman,true);
							}
						} else {
							break;
						}
					}
				}
			}
			break;
		case KeyEvent.VK_LEFT:
			if (y - adim > 0) {
				if (harita[x][y - adim] != 0) {

					dusman = null;

					for (int i = 1; i <= adim; i++) {

						if (harita[x][y - i] != 0) {

							objeDurum(x, y - i);
							temp = dusmanKontrol(x, y - i);
							if(temp != null) {
								dusman  = temp;
							}

							oyuncu.getKoordinat().setY(y - i);
							buttons[x][y - i].setIcon(buttons[x][y - i + 1].getIcon());
							buttons[x][y - i + 1].setIcon(null);
							Thread.sleep(250);

							if(i == adim) {
								dusmanYol(dusman,true);
							}
						} else {
							break;
						}
					}
				}
			}
			break;
		}
	}

	private void sonSirinLira(int sirinLira) {
		puan.setText("SirinLira : "+ sirinLira);
	}

	private void objeDurum(int x, int y) {
		for (Obje obje : altinlar) {
			if (obje.getKoordinat().getX() == x && obje.getKoordinat().getY() == y && obje.isAktif()) {
				oyuncu.setSirinLira(oyuncu.getSirinLira() + obje.getItemPuan());
				obje.setAktif(false);
				Thread thread3 = new Thread(() -> {
					sonSirinLira(oyuncu.sirinLiraGoster());
				});
				thread3.start();
			}
		}
		if (mantar != null) {
			if (mantar.getKoordinat().getX() == x && mantar.getKoordinat().getY() == y && mantar.isAktif()) {
				oyuncu.setSirinLira(oyuncu.getSirinLira() + mantar.getItemPuan());
				mantar.setAktif(false);
				Thread thread4 = new Thread(() -> {
					sonSirinLira(oyuncu.sirinLiraGoster());
				});
				thread4.start();

			}
		}
	}

	private Dusman dusmanKontrol(int x, int y) {
		Dusman dusman = null;
		for (Dusman geciciDusman : sirinDusmanlari) {
			if (geciciDusman.getKoordinat().getX() == x && geciciDusman.getKoordinat().getY() == y) {
				dusman = geciciDusman;
				break;
			}
		}
		if (dusman != null) {
			karsilasma(dusman);
			return dusman;
		}
		return null;

	}

	private void haritaOlustur(JPanel panel) {

		int buttonWidth = 40;
		int buttonHeight = 35;

		String gecisKapisi;
		charDurum();

		buttons = new JButton[labirentSutun][labirentSatir];

		for (int i = 0; i < labirentSutun; i++) {
			for (int j = 0; j < labirentSatir; j++) {
				gecisKapisi = kapiKoordinat(i, j);
				buttons[i][j] = new JButton();
				buttons[i][j].setForeground(Color.YELLOW);
				//blok kenarları
				buttons[i][j].setBorder(new LineBorder(Color.darkGray));

				if (harita[i][j] == 0) { //bloklar
					buttons[i][j].setBackground(new Color(70, 70, 70));
				} else if (gecisKapisi != "") {
					buttons[i][j].setBackground(new Color(255, 51, 51));
					for (Karakter karakter : karakterler) {
						if (karakter.getKoordinat().getX() == i && karakter.getKoordinat().getY() == j) {
							buttons[i][j].setBackground(Color.WHITE);
						} else {
							buttons[i][j].setText(gecisKapisi);
						}
					}
					if(buttons[i][j].getBackground() != Color.WHITE) {
						harita[i][j] = 0;
						//kapi
						buttons[i][j].setBackground(new Color(100,200,100));
					}
				} else { //yollar
					buttons[i][j].setBackground(Color.WHITE);
				}


				buttons[i][j].setBounds(20 + j * buttonWidth, 20 + i * buttonHeight, buttonWidth, buttonHeight);
				buttons[i][j].addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {

					}

					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN
								|| e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
							Thread thread = new Thread(() -> {
								try {
									hareketEt(e.getKeyCode());
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
							});
							thread.start();
						}
					}

					@Override
					public void keyReleased(KeyEvent e) {

					}
				});

				panel.add(buttons[i][j]);
			}
		}
	}

	private void haritaCharEkle() {

		String imageName = "";
		int x, y;

		charDurum();

		for (Karakter karakter : karakterler) {

			x = karakter.getKoordinat().getX();
			y = karakter.getKoordinat().getY();

			switch (karakter.getIsim()) {
			case "Gargamel":
				imageName = "gargamel";
				break;
			case "Azman":
				imageName = "azman";
				break;
			case "Gözlüklü Şirin":
				imageName = "gozlukluSirin";
				break;
			case "Tembel Şirin":
				imageName = "tembelSirin";
				break;
			}

			buttons[x][y].setText("");
			buttons[x][y].setIcon(getIconImage(imageName, 42, 38));

		}
	}

	private ImageIcon getIconImage(String imageName, int witdh, int height) {
		Image image;
		ImageIcon imageIcon = null;
		try {
			image = ImageIO.read(this.getClass().getResourceAsStream("/resimler/" + imageName + ".png"))
					.getScaledInstance(witdh, height, java.awt.Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(image);
		} catch (IOException ex) {
			System.out.print(ex);
		}
		return imageIcon;
	}

	private String kapiKoordinat(int i, int j) {
		if (i == 0 && j == 3) {
			return "A";
		} else if (i == 0 && j == 10) {
			return "B";
		} else if (i == 5 && j == 0) {
			return "C";
		} else if (i == 10 && j == 3) {
			return "D";
		} else if (i == 5 && j == 6) {
			return "S";
		} else {
			return "";
		}
	}

	private void readFile(String filePath) {

		Dusman dusman;
		Lokasyon koordinat = new Lokasyon(0, 0);
		harita = new int[labirentSutun][labirentSatir];
		sirinDusmanlari = new ArrayList<Dusman>();
		int dusmanSayi = 0, satirSayi = 0, dusmanID;
		String dusmanAd = "";
		try {
			Scanner scn = new Scanner(this.getClass().getResourceAsStream(filePath));
			String satir;
			while (scn.hasNextLine()) {
				satir = scn.nextLine();
				if (satir.startsWith("Karakter:")) {
					dusmanID = dusmanSayi++;
					for (var item : satir.split(",")) {
						item = item.substring(item.indexOf(":") + 1);
						if (item.length() > 1) {
							dusmanAd = item;
						} else {
							switch (item) {
							case "A":
								koordinat = new Lokasyon(0, 3);
								break;
							case "B":
								koordinat = new Lokasyon(0, 10);
								break;
							case "C":
								koordinat = new Lokasyon(5, 0);
								break;
							case "D":
								koordinat = new Lokasyon(10, 3);
								break;
							} 
						}
					}

					//Dosyada Gargamel ve Azman'ı burada anlıyor
					if (dusmanAd.startsWith("Gargamel")) {
						dusman = new Gargamel(new DusmanAyrim(2, true), dusmanID, dusmanAd,
								new Color(rastgeleAta.nextFloat(), rastgeleAta.nextFloat(), rastgeleAta.nextFloat()), koordinat);
						dusman.setBaslangicKonum(koordinat);
						sirinDusmanlari.add(dusman);
					} else if (dusmanAd.startsWith("Azman")) {
						dusman = new Azman(new DusmanAyrim(1, false), dusmanID, dusmanAd,
								new Color(rastgeleAta.nextFloat(), rastgeleAta.nextFloat(), rastgeleAta.nextFloat()), koordinat);
						dusman.setBaslangicKonum(koordinat);
						sirinDusmanlari.add(dusman);
					}

				} else {
					int i = 0;
					for (var item : satir.split("	")) {
						harita[satirSayi][i] = Integer.parseInt(item);
						i++;
					}
					satirSayi++;
				}
			}
		} catch (Exception e) {
			System.err.println("Error:" + e);
		}
	}

}
