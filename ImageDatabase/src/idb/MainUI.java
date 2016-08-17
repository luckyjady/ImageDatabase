package idb;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MainUI {

	//Hangi bilgisaarda olursa olsun o bilgisayarýn masaüstü
	public static final String desktop = System.getProperty("user.home")
			+ "\\Desktop";
	public JFrame frame;
	private AVL_Tree tree;
	private JTree jtree;
	private final int quality;
	//--
	private JPanel insert_colorpanel;
	private File insert_file;
	private JButton insertButton;
	//--
	private JPanel delete_colorpanel;
	private File delete_file;
	private JButton deleteButton;
	//--
	private JPanel search_colorpanel;
	private File search_file;
	private JButton searchButton;
	private JSpinner spinner;
	//--
	private FileNameExtensionFilter filter;
	/**
	 * Create the application.
	 */
	public MainUI(AVL_Tree tree,int quality) {
		this.tree = tree;
		this.quality = quality;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//--
		filter = new FileNameExtensionFilter("JPG, PNG & GIF", "png","jpg","gif");
		//--
		frame = new JFrame("Image Database");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setBounds(100, 100, 477, 278);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Tabbed Pane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 451, 227);
		frame.getContentPane().add(tabbedPane);

		//
		// Insert Panel
		// --
		final JPanel insert_panel = new JPanel(false);
		tabbedPane.addTab("Insert", insert_panel);
		insert_panel.setLayout(null);

		JButton select_insertButton = new JButton("Select Image");
		select_insertButton.setBounds(10, 15, 191, 23);
		insert_panel.add(select_insertButton);

		final JLabel insert_imageLabel = new JLabel();
		insert_imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		insert_imageLabel.setBounds(211, 15, 225, 173);
		insert_panel.add(insert_imageLabel);
		
		insertButton = new JButton("Insert");
		insertButton.setBounds(10, 165, 191, 23);
		insert_panel.add(insertButton);
		insertButton.setEnabled(false);
		
		insert_colorpanel = new JPanel();
		insert_colorpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		insert_colorpanel.setBounds(10, 49, 191, 104);
		insert_panel.add(insert_colorpanel);
		insert_colorpanel.setLayout(null);
		insert_colorpanel.setVisible(false);
		
		//Eklemek için bir resim seçilmesi için olan method
		select_insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser cho = new JFileChooser(desktop);
				cho.setFileFilter(filter);
				int result = cho.showOpenDialog(frame);

				if (result == JFileChooser.APPROVE_OPTION) {

					try {
						//Resimle alakalý bilgileri alýp eklemeye hazýrlýyoruz
						insert_file = cho.getSelectedFile();
						BufferedImage bi = ImageIO.read(insert_file);
						Icon icon = new ImageIcon(resizeImage(bi));
						insert_imageLabel.setIcon(icon);
						
						ColorFeature cf = new ColorFeature(insert_file, quality);
						ImageObject obj = new ImageObject(cf.colorFeature, insert_file.getAbsolutePath());
						insert_panel.remove(insert_colorpanel);
						insert_colorpanel = getColorFeaturePanel(obj);
						insert_panel.add(insert_colorpanel);
						insertButton.setEnabled(true);
						frame.repaint();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		//Resmi ekle tuþuna bastýðýnda olan olaylar
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Resimle alakalý bilgileri hazýrlýyorum
				ColorFeature cf = new ColorFeature(insert_file, quality);
				ImageObject obj = new ImageObject(cf.colorFeature, insert_file.getAbsolutePath());
				
				//ve aðaca eklemesini söylüyorum
				tree.insert_imageObject(obj);
				repaintTree();
				insertButton.setEnabled(false);
				insert_colorpanel.setVisible(false);
				insert_imageLabel.setIcon(null);
			}
		});
		
		//
		// Delete Panel
		// --
		final JPanel delete_panel = new JPanel(false);
		tabbedPane.addTab("Delete", delete_panel);
		delete_panel.setLayout(null);
		
		final JLabel delete_imageLabel = new JLabel();
		delete_imageLabel.setBounds(211, 15, 225, 173);
		delete_imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		delete_panel.add(delete_imageLabel);
		
		JButton select_deleteButton = new JButton("Select Image");
		select_deleteButton.setBounds(10, 15, 191, 23);
		delete_panel.add(select_deleteButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(10, 165, 191, 23);
		delete_panel.add(deleteButton);
		deleteButton.setEnabled(false);
		
		delete_colorpanel = new JPanel();
		delete_colorpanel.setBounds(10, 49, 191, 104);
		delete_colorpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		delete_panel.add(delete_colorpanel);
		delete_colorpanel.setVisible(false);
		
		//Silinecek resmin seçilme olayý
		select_deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser cho = new JFileChooser(desktop);
				cho.setFileFilter(filter);
				int result = cho.showOpenDialog(frame);

				if (result == JFileChooser.APPROVE_OPTION) {

					try {
						//Silinecek resimle alakalý bilgileri topluyorum
						delete_file = cho.getSelectedFile();
						BufferedImage bi = ImageIO.read(delete_file);
						Icon icon = new ImageIcon(resizeImage(bi));
						delete_imageLabel.setIcon(icon);
						
						ColorFeature cf = new ColorFeature(delete_file, quality);
						ImageObject obj = new ImageObject(cf.colorFeature, delete_file.getAbsolutePath());
						delete_panel.remove(delete_colorpanel);
						delete_colorpanel = getColorFeaturePanel(obj);
						delete_panel.add(delete_colorpanel);
						deleteButton.setEnabled(true);
						frame.repaint();
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		//Resmi sil tuþuna bastýðýndaki olaylar
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Resimle alakalý bilgileri hazýrlýyorum
				ColorFeature cf = new ColorFeature(delete_file, quality);
				ImageObject obj = new ImageObject(cf.colorFeature, delete_file.getAbsolutePath());
				
				//aðaca bu resmi silmesini söylüyorum
				tree.delete_imageObject(obj);
				repaintTree();
				deleteButton.setEnabled(false);
				delete_colorpanel.setVisible(false);
				delete_imageLabel.setIcon(null);
			}
		});
		
		//
		// Search Panel
		// --
		final JPanel search_panel = new JPanel(false);
		tabbedPane.addTab("Search", search_panel);
		search_panel.setLayout(null);

		final JLabel search_imageLabel = new JLabel();
		search_imageLabel.setBounds(211, 15, 225, 173);
		search_imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		search_panel.add(search_imageLabel);
		
		JButton select_searchButton = new JButton("Select Image");
		select_searchButton.setBounds(10, 15, 191, 23);
		search_panel.add(select_searchButton);
		
		search_colorpanel = new JPanel();
		search_colorpanel.setBounds(10, 49, 191, 104);
		search_colorpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		search_panel.add(search_colorpanel);
		search_colorpanel.setVisible(false);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setBounds(151, 165, 50, 23);
		search_panel.add(spinner);
		spinner.setEnabled(false);
		
		searchButton = new JButton("Search");
		searchButton.setBounds(10, 165, 131, 23);
		search_panel.add(searchButton);
		searchButton.setEnabled(false);
		
		//Aranacak resmi seçtiren arkadaþ
		select_searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser cho = new JFileChooser(desktop);
				cho.setFileFilter(filter);
				int result = cho.showOpenDialog(frame);

				if (result == JFileChooser.APPROVE_OPTION) {

					try {
						//Seçilen resimle alakalý bilgileri topluyor
						search_file = cho.getSelectedFile();
						BufferedImage bi = ImageIO.read(search_file);
						Icon icon = new ImageIcon(resizeImage(bi));
						search_imageLabel.setIcon(icon);
						
						ColorFeature cf = new ColorFeature(search_file, quality);
						ImageObject obj = new ImageObject(cf.colorFeature, search_file.getAbsolutePath());
						search_panel.remove(search_colorpanel);
						search_colorpanel = getColorFeaturePanel(obj);
						search_panel.add(search_colorpanel);
						searchButton.setEnabled(true);
						spinner.setEnabled(true);
						frame.repaint();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		//Arama yapýlcaðý zaman çaðýrýlan arkadaþ
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Aranacak resimle alakalý bilgileri topluyorum
				ColorFeature cf = new ColorFeature(search_file, quality);
				ImageObject obj = new ImageObject(cf.colorFeature, search_file.getAbsolutePath());
				
				//ve aðactan bu resme istenen sayý kadar resim vermesini söylom
				ImageObject[] arr = tree.getClosest(obj, (int)spinner.getValue());
				//--
				// TODO
				//Çýkan sonuçlarlada gösterilecek frami hazýrlatýp görünür hale getirtiriom
				ResultFrame rf = new ResultFrame(obj, arr);
				rf.setVisible(true);
				//--
				searchButton.setEnabled(false);
				spinner.setEnabled(false);
				search_colorpanel.setVisible(false);
				search_imageLabel.setIcon(null);
			}
		});
		// --
		
		//
		// Tree Panel
		// --
		JPanel tree_panel = new JPanel(false);
		tabbedPane.addTab("Tree", tree_panel);
		tree_panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 426, 177);
		tree_panel.add(scrollPane);
		
		jtree = new JTree();
		scrollPane.setViewportView(jtree);
		search_panel.setLayout(null);
		
		repaintTree();
	}

	public void repaintTree() {
		//Aðacý yeniden çizerken yeni bi model oluþturup bunu aðacýn rootundan istiom
		//ve jtreenin modeli deðiþtrip aðacý yenilemiþ oluyom
		DefaultTreeModel dtm = new DefaultTreeModel(null);
		DefaultMutableTreeNode root = tree.root.getDefaultMutableTreeNode();
		dtm.setRoot(root);
		jtree.setModel(dtm);
		frame.repaint();
	}

	// ---
	// Resize images to
	
	//Resimleri ekrandaki JPanellere sýðdýrabilmek için
	private static final int IMG_WIDTH = 255;
	private static final int IMG_HEIGHT = 173;

	public static BufferedImage resizeImage(BufferedImage originalImage) {
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
				type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		return resizedImage;
	}
	
	//Verilen imageobjecte göre bir JPanel döndürüyor
	public static JPanel getColorFeaturePanel(ImageObject obj){
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 49, 191, 104);
		panel.setLayout(null);
		
		JLabel colorFeatureNameLabel = new JLabel("Color Feature");
		colorFeatureNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		colorFeatureNameLabel.setBounds(10, 11, 171, 14);
		panel.add(colorFeatureNameLabel);
		
		JLabel colorFeatureLabel = new JLabel("" + obj.colorFeature);
		colorFeatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
		colorFeatureLabel.setBounds(10, 36, 171, 14);
		panel.add(colorFeatureLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 64, 191, 40);
		scrollPane.setIgnoreRepaint(false);
		panel.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("File path : " + obj.path);
		scrollPane.setViewportView(lblNewLabel);
		
		return panel;
	}
}
