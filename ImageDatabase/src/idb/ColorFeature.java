package idb;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ColorFeature {

	private Color[][] image;
	private Color[][] finalColor;
	public int colorFeature;
	
	/*
	 * Verilen dosyaya ve kalite deðerine göre color feature çýkaran arkadaþ
	 */
	public ColorFeature(File f, int value) {
		try {
			init(f, value);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Color featura ait frami veren arkadaþ
	 * kullanýlýyo
	 */
	@Deprecated
	public JFrame getAsFrame() {
		JFrame frame = new JFrame("Color Feature") {
			@Override
			public void paint(Graphics graph) {
				// TODO Auto-generated method stub
				super.paint(graph);
				if (image == null) {
					return;
				}

				int v_perPixel = 1;

				int offsetWidth = 50;
				int offsetHeight = 100;

				for (int i = 0; i < image.length; i++) {
					for (int j = 0; j < image[i].length; j++) {
						graph.setColor(image[i][j]);
						int left = j * v_perPixel + offsetWidth;
						int top = i * v_perPixel + offsetHeight;

						// System.out.println(left + " " + top + " " +
						// image[i][j].toString());
						graph.fillRect(left, top, v_perPixel, v_perPixel);
					}
				}

				int t_offWidth = offsetWidth + v_perPixel * image[0].length
						+ 50;
				// int t_offHeight = offsetHeight + v_perPixel * image.length +
				// 50;
				int t_offHeight = 100;

				int con = 3;

				v_perPixel *= image.length / finalColor.length;

				for (int i = 0; i < finalColor.length; i++) {
					for (int j = 0; j < finalColor[i].length; j++) {

						graph.setColor(finalColor[i][j]);

						int left = j * v_perPixel + t_offWidth + con * j;
						int top = i * v_perPixel + t_offHeight + con * i;

						graph.fillRect(left, top, v_perPixel, v_perPixel);
					}
				}

				String dr = "Color Feature : " + colorFeature;
				graph.drawString(dr, offsetWidth, offsetHeight / 2);
			}
		};
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setResizable(true);
		frame.setVisible(true);
		return frame;
	}
	
	public void init(File f, int value) throws IOException {

		if (!f.exists()) {
			return;
		}

		BufferedImage bi = ImageIO.read(f);
		int imageWidth = bi.getWidth();
		int imageHeight = bi.getHeight();

		//bu resme ait bütün renkleri alýrým
		this.image = new Color[imageHeight][imageWidth];

		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				image[i][j] = new Color(bi.getRGB(j, i));

				// System.out.println(i + " " + j + " " +
				// image[i][j].toString());
			}
		}

		int red, green, blue;
		int quality = value;

		int tempWidth = imageWidth / quality;
		int tempHeight = imageHeight / quality;

		// --
		// float sqrt = ((float) imageWidth / quality)
		// * ((float) imageHeight / quality);

		//
		//kalite deðerine göre parçaya ayýrýrým
		//her bir parça resimde kapladýðý alanýn renk ortalamasýný bulur
		//ve bu renk ortalamasýný bulunduðu konuma göre çarpar
		
		finalColor = new Color[quality][quality];
		for (int i = 0; i < quality; i++) {
			for (int j = 0; j < quality; j++) {

				red = green = blue = 0;
				for (int k = 0; k < tempHeight; k++) {
					for (int l = 0; l < tempWidth; l++) {

						Color c = image[i * tempHeight + k][j * tempWidth + l];
						red += c.getRed();
						green += c.getGreen();
						blue += c.getBlue();

					}
				}

				// System.out.println(red + " " + " " + green + " " + blue + " "
				// + sqrt);

				int sqrt = tempWidth * tempHeight;

				red /= sqrt;
				green /= sqrt;
				blue /= sqrt;

				// System.out.println(red + " " + " " + green + " " + blue + " "
				// + sqrt);

				// System.out.println(red + " " + " " + green + " " + blue + " "
				// + sqrt);
				finalColor[i][j] = new Color(red, green, blue);

				// System.out.println(finalColor[i][j].toString());

				// Calculate color feature
				colorFeature += (i + j + 1)
						* (finalColor[i][j].getRGB() & 0xffffff);

				// System.out.println(i + " " + j + " " +
				// finalColor[i][j].toString());
			}
		}

	}

}
