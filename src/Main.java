import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();



		setTitle("Sirine'yi Kurtar");
		setBackground(Color.RED);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 280); //kutu boyutu
		setResizable(true);  //kutu boyutu değiştirebilme
		setLocation(((screenSize.width - getWidth()) / 2), ((screenSize.height - getHeight()) / 2)); //kutunun ekrandaki konumu

		Image gozlukluPNG;
		Image tembelPNG;

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY); //şirin seçme kutusu
		setContentPane(panel);
		panel.setLayout(null);

		ImageIcon tembelIkon = new ImageIcon();
		ImageIcon gozlukluIkon = new ImageIcon();

		JLabel title = new JLabel("Sirininizi Seciniz!");
		title.setForeground(Color.WHITE);
		title.setBounds(100, 6, 220, 40);

		try {
			gozlukluPNG = ImageIO.read(this.getClass().getResourceAsStream("/resimler/gozlukluSirin.png"))
					.getScaledInstance(90, 90, Image.SCALE_REPLICATE);

			gozlukluIkon = new ImageIcon(gozlukluPNG);

			tembelPNG = ImageIO.read(this.getClass().getResourceAsStream("/resimler/tembelSirin.png"))
					.getScaledInstance(90, 90, Image.SCALE_REPLICATE);

			tembelIkon = new ImageIcon(tembelPNG);

		} catch (IOException ex) {
			System.out.print(ex);
		}

		JButton buttonGozlukluSirin = new JButton(gozlukluIkon);
		buttonGozlukluSirin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Oyun(new GozlukluSirin(new OyuncuAyrim(2,5,15),1, "Gözlüklü Şirin", 20, new Lokasyon(5, 6)));
				dispose();
			}
		});
		buttonGozlukluSirin.setForeground(Color.pink);
		buttonGozlukluSirin.setBackground(Color.pink);
		buttonGozlukluSirin.setBounds(40, 50, 110, 110);

		JButton buttonTembelSirin = new JButton(tembelIkon);
		buttonTembelSirin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Oyun(new TembelSirin(new OyuncuAyrim(1,5,15),1, "Tembel Şirin", 20, new Lokasyon(5, 6)));
				dispose();
			}
		});
		buttonTembelSirin.setForeground(Color.ORANGE);
		buttonTembelSirin.setBackground(Color.ORANGE);
		buttonTembelSirin.setBounds(180, 50, 110, 110);

		panel.add(title);
		panel.add(buttonGozlukluSirin);
		panel.add(buttonTembelSirin);

	}
}
