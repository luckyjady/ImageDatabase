package idb;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class Loader extends JFrame {

	private JPanel contentPane;
	private JSlider slider;
	private JLabel fileNameLabel;
	private JProgressBar progressBar;
	private JPanel loader_panel;
	private File selectedFile;
	private JButton startButton;
	private AVL_Tree tree;
	private FileFilter filter;
	private float progress;
	private float eachProg;

	/**
	 * Create the frame.
	 */
	public static void main(String[] args) {
		Loader ld = new Loader();
		ld.setVisible(true);
	}

	public Loader() {
		super("Image Database");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// QualityPanel
		// --
		JPanel quality_panel = new JPanel();
		quality_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		quality_panel.setBounds(194, 11, 240, 105);
		contentPane.add(quality_panel);
		quality_panel.setLayout(null);

		// Slider
		// --
		slider = new JSlider();
		slider.setBounds(10, 36, 226, 31);
		quality_panel.add(slider);
		slider.setRequestFocusEnabled(false);
		slider.setMinorTickSpacing(1);
		slider.setValue(3);
		slider.setMinimum(2);
		slider.setMaximum(4);
		slider.setPaintTicks(true);
		// --

		JLabel LowLabel = new JLabel("Low");
		LowLabel.setBounds(10, 80, 46, 14);
		quality_panel.add(LowLabel);
		LowLabel.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel QualityLabel = new JLabel("Quality");
		QualityLabel.setBounds(0, 11, 236, 14);
		quality_panel.add(QualityLabel);
		QualityLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel NormalLabel = new JLabel("Normal");
		NormalLabel.setBounds(101, 80, 46, 14);
		quality_panel.add(NormalLabel);
		NormalLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel HighLabel = new JLabel("High");
		HighLabel.setBounds(190, 80, 46, 14);
		quality_panel.add(HighLabel);
		HighLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		// --

		// Loader panel
		// --
		loader_panel = new JPanel();
		loader_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		loader_panel.setVisible(false);
		loader_panel.setBounds(10, 212, 424, 67);
		contentPane.add(loader_panel);
		loader_panel.setLayout(null);

		progressBar = new JProgressBar();
		progressBar.setBounds(10, 11, 404, 14);
		loader_panel.add(progressBar);

		fileNameLabel = new JLabel("");
		fileNameLabel.setBounds(10, 42, 404, 14);
		loader_panel.add(fileNameLabel);
		// --

		// Tree Selection Panel
		// --
		JPanel tree_panel = new JPanel();
		tree_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		tree_panel.setBounds(10, 11, 174, 105);
		contentPane.add(tree_panel);
		tree_panel.setLayout(null);

		JLabel treeSelectionLabel = new JLabel("Tree Selection");
		treeSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		treeSelectionLabel.setBounds(0, 11, 174, 14);
		tree_panel.add(treeSelectionLabel);

		final JRadioButton avlTreeRadioButton = new JRadioButton("AVL Tree");
		avlTreeRadioButton.setBounds(10, 32, 109, 23);
		tree_panel.add(avlTreeRadioButton);
		avlTreeRadioButton.setSelected(true);

		JRadioButton intervalTreeRadioButton = new JRadioButton("Interval Tree");
		intervalTreeRadioButton.setBounds(10, 58, 109, 23);
		tree_panel.add(intervalTreeRadioButton);

		ButtonGroup group = new ButtonGroup();
		group.add(avlTreeRadioButton);
		group.add(intervalTreeRadioButton);
		// --

		// Directory Panel
		// --
		JPanel directory_panel = new JPanel();
		directory_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		directory_panel.setBounds(10, 127, 424, 74);
		contentPane.add(directory_panel);
		directory_panel.setLayout(null);

		JButton selectDirectoryButton = new JButton("Select Directory");
		selectDirectoryButton.setBounds(150, 11, 140, 23);
		directory_panel.add(selectDirectoryButton);

		final JLabel directoryLabel = new JLabel("Selected Directory :");
		directoryLabel.setBounds(10, 49, 404, 14);
		directory_panel.add(directoryLabel);

		//Baþlamak için bir directory seçmek için bu void
		selectDirectoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				File desktop = new File(MainUI.desktop);
				final JFileChooser cho = new JFileChooser(desktop);
				cho.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				cho.setAcceptAllFileFilterUsed(false);

				int result = cho.showOpenDialog(Loader.this);

				if (result == JFileChooser.APPROVE_OPTION) {

					selectedFile = cho.getSelectedFile();
					directoryLabel.setText("Selected Directory : "
							+ selectedFile.getAbsolutePath());
					startButton.setEnabled(true);
				}
			}
		});
		// --
		
		//Sadece directory ve resimleri okuyabilmek için bir filitre
		//Filter
		filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if(pathname.isDirectory()){
					return true;
				}
				
				String name = pathname.getName();
				String extention = name.substring(name.lastIndexOf(".") + 1);
				
				if (extention.equals("jpg")) {
					return true;
				}

				if (extention.equals("png")) {
					return true;
				}

				if (extention.equals("gif")) {
					return true;
				}

				return false;
			}
		};
		//--
		
		// Start Button
		// --
		startButton = new JButton("Start");
		startButton.setBounds(180, 230, 89, 23);
		contentPane.add(startButton);

		//Dosyalarý okuyup treeye atan arkadaþ
		startButton.setEnabled(false);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				slider.disable();
				startButton.setVisible(false);
				Loader.this.loader_panel.setVisible(true);

				//Kullanýcýnýn isteðine göre aðacý oluþturma
				if (avlTreeRadioButton.isSelected()) {
					tree = new AVL_Tree();
				} else {
					int interval;
					if (slider.getValue() == 2) {
						interval = 100000;
					} else if (slider.getValue() == 3) {
						interval = 10000000;
					} else {
						interval = 100000000;
					}
					tree = new IntervalTree(interval);
				}
				//seçilen elemaný deðiþmeyecek bir deðiþkene atýom ki böylece
				//yükleme sýrasýnda kullanýcýný  deðiþliðiyle herhangi bi aksilik çýkmasýn
				final File dir = selectedFile;
				//Dosyadaki bütün resimleri buluyoruz
				int totalPic = calculatePics(dir);
				//Her resim için progress barýn ne kadar ilerleyeceðini belirlioz
				eachProg = ((float) 1 / totalPic) * 100000;
				progress = 0;
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						progressBar.setMaximum(100000);
						
						//seçilen directoryi iþlerim
						readDirectory(dir);
						
						//ve ana frame'i oluþan tree ve quality deðeriyle oluþturur
						//ana frame'i görünür hale getirip bu frame'i ekrandan alýrým
						MainUI window = new MainUI(tree, slider.getValue());
						window.frame.setVisible(true);

						Loader.this.dispose();
					}
				}).start();
			}
		});
		// --
	}
	
	private int calculatePics(File dir) {
		// TODO Auto-generated method stub
		int sum = 0;
		File[] files = dir.listFiles(filter);
		
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()){
				sum += calculatePics(files[i]);
			}
			else{
				sum++;
			}
		}
		return sum;
	}
	
	//Kendisine verilen directorynin içerisindeki bütün resimleri okumak için
	//Eðer kendi içerisinde bir directory varsa onuda recursion olarak çaðýrýyor
	private void readDirectory(File directory){
		
		File[] files = directory.listFiles(filter);
		
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()){
				readDirectory(files[i]);
			}
			else{
				//--
				fileNameLabel.setText(files[i].getAbsolutePath());
				ColorFeature cf = new ColorFeature(files[i], slider
						.getValue());
				ImageObject obj = new ImageObject(cf.colorFeature,
						files[i].getAbsolutePath());
				tree.insert_imageObject(obj);
				
				progress += eachProg;
				progressBar.setValue((int) progress);
			}
		}
		
	}
	
}
