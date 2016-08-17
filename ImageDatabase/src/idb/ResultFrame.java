package idb;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;


public class ResultFrame extends JFrame {

	private JPanel contentPane;
	private JTable results_table;
	private ImageObject[] data;
	//
	private JPanel result_colorpanel;
	
	/**
	 * Create the frame.
	 */
	public ResultFrame(ImageObject selected,ImageObject[] result) {
		setResizable(false);
		//
		this.data = result;
		//--
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 521, 638);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Selected image panel
		//--
		JPanel selectedimage_panel = new JPanel();
		selectedimage_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		selectedimage_panel.setBounds(10, 11, 487, 227);
		contentPane.add(selectedimage_panel);
		selectedimage_panel.setLayout(null);
		
		JLabel selectedNameLabel = new JLabel("Selected Image");
		selectedNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectedNameLabel.setBounds(10, 11, 467, 14);
		selectedimage_panel.add(selectedNameLabel);
		
		JLabel selected_imageLabel = new JLabel();
		selected_imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		selected_imageLabel.setBounds(10, 36, 225, 173);
		selectedimage_panel.add(selected_imageLabel);
		//TODO
		//Se�ilen resmi ekranda g�stermek i�in
		selected_imageLabel.setIcon(getIconImageObject(selected));
		
		//Se�ilen resmin �zelli�ini i�eren Jpaneli al�oz
		JPanel selected_colorpanel = MainUI.getColorFeaturePanel(selected);
		//JPanel selected_colorpanel = new JPanel();
		//selected_colorpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		selected_colorpanel.setBounds(267, 70, 191, 104);
		selectedimage_panel.add(selected_colorpanel);
		
		//Result Panel
		//--
		final JPanel results_panel = new JPanel();
		results_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		results_panel.setBounds(10, 249, 485, 339);
		contentPane.add(results_panel);
		results_panel.setLayout(null);
		
		JLabel resultsNameLabel = new JLabel("Results");
		resultsNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultsNameLabel.setBounds(10, 11, 462, 14);
		results_panel.add(resultsNameLabel);
		
		
		JScrollPane results_scroll = new JScrollPane();
		results_scroll.setBounds(10, 36, 227, 289);
		results_panel.add(results_scroll);
		
		//--
		//Sonu�lar� g�steren tablo
		results_table = new JTable();
		results_scroll.setViewportView(results_table);
		
		final JLabel result_imageLabel = new JLabel();
		result_imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		result_imageLabel.setBounds(247, 36, 225, 173);
		results_panel.add(result_imageLabel);
		
		result_colorpanel = new JPanel();
		result_colorpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		result_colorpanel.setBounds(265, 220, 191, 104);
		results_panel.add(result_colorpanel);
		//--
		//Preparing table
		//Tablonun se�ilme stili ve modelini yani i�indekileri haz�rlama yeri
		results_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		results_table.setModel(new ImageObjectDataModel());
		
		//Tabloda se�ilen row de�i�ti�inde onun bilgilerini ekrana getirmek i�in
		results_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				// TODO Auto-generated method stub
				if(event.getValueIsAdjusting()){
					return;
				}
				//--
				//Se�ilen rowdaki imageobjecti al�p ona g�re de�erleri haz�rlay�p gui'yi de�i�tiriom
				results_panel.remove(result_colorpanel);
				result_colorpanel = MainUI.getColorFeaturePanel(data[results_table.getSelectedRow()]);
				result_colorpanel.setBounds(265, 220, 191, 104);
				results_panel.add(result_colorpanel);
				results_panel.updateUI();
				//--
				//--
				result_imageLabel.setIcon(getIconImageObject(data[results_table.getSelectedRow()]));
				ResultFrame.this.repaint();
				//--
			}
		});
		
		this.repaint();
	}
	
	//Verilen resme g�re Icon d�nd�ren yard�mc� method
	private Icon getIconImageObject(ImageObject selected) {
		// TODO Auto-generated method stub
		try{
			File f = new File(selected.path);
			BufferedImage bi = ImageIO.read(f);
			return new ImageIcon(MainUI.resizeImage(bi));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	//Inner Class
	//JTable i�in gerek veri modeli
	//Ald���m verileri yukardaki array tutuyorum istenilen rowu yukardan se�iyorum
	//b�ylece birden fazla sonu� frame'i ��karabiliyor
	//static kullanmak yerine
	private class ImageObjectDataModel extends AbstractTableModel{
		
		//Ekranda g�sterilecek column ba�l�klar�
		private String[] columnNames = {"No","File Name","Color Feature"};
		
		public ImageObjectDataModel(){
			super();
		}
		
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return data.length;
		}
		
		public String getColumnName(int col){
			return columnNames[col];
		}
		
		//It is not neccessary
		public Class getColumnClass(int c){
			return getValueAt(0, c).getClass();
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			if(columnIndex == 1){
				String str = data[rowIndex].path;
				return str.substring(str.lastIndexOf("\\") + 1);
			}
			else if(columnIndex == 2){				
				return data[rowIndex].colorFeature;
			}
			return Integer.toString(rowIndex + 1);
		}
	}
}

