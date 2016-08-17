package idb;

import java.util.ArrayList;

import sorting.Quick;
import sorting.SortableImageObject;

public class IntervalTree extends AVL_Tree {

	//Her aralýðýn ne kadar olacaðýný veren arkadaþ
	public final int interval;

	//Aðacý oluþturuken aralýk deðerini alýrým
	public IntervalTree(int interval) {
		super();
		this.interval = interval;
	}

	//
	@Override
	public void insert_imageObject(ImageObject obj) {
		// TODO Auto-generated method stub
		
		//verilen objection hangi node gidebileceðini bulur ve bunu aðaçtan isterim
		int seaVal = getStartValue(obj.colorFeature);
		Node sea = super.Search(root, seaVal);

		if (sea == null) {
			// Eðer öyle bir aralýk hiç yoksa demek ben bu aralýðý yaratýcam
			NodeInterval newNode = new NodeInterval(seaVal);
			newNode.list.add(obj);
			super.InsertIterative(newNode);
		} else {
			// Eðer öyle bir aralýk varsa bu aralýða kendi deerimi eklerim
			NodeInterval in = (NodeInterval) sea;
			in.list.add(obj);
		}
	}
	
	//
	@Override
	public void delete_imageObject(ImageObject obj) {
		// TODO Auto-generated method stub
		int colorFeature = obj.colorFeature;
		//verilen objection hangi node da olabileceðini bulur
		//ve bu node'u aðaçtan isterim
		int seaVal = getStartValue(colorFeature);
		Node sea = super.Search(root, seaVal);

		if (sea != null) {
			//Eðer byle bir node varsa demekki belki böyle bu object olabilir
			NodeInterval ni = (NodeInterval) sea;
			for (int i = 0; i < ni.list.size(); i++) {
				//Eðer bu objecti listemde bulursam silerim
				if (ni.list.get(i).colorFeature == colorFeature) {
					ni.list.remove(i);
					break;
				}
			}
			
			//eðer bu node'un listesinde eleman kalmamýþsa bu node'u aðaçtan silerim
			if (ni.list.size() == 0) {
				super.DeleteIterative(seaVal);
			}
		}

	}

	//Verilen imageobjecte göre istenilen ssayý kadar yakýn imageobject döndüren fonksiyon
	@Override
	public ImageObject[] getClosest(ImageObject obj, int count) {
		// TODO Auto-generated method stub
		int colorFeature = obj.colorFeature;
		//yakýn olabilecek imageobjectleri tutabileceðim bir liste
		ArrayList<ImageObject> list = new ArrayList<ImageObject>();
		//ve bu elemnýn olabileceði node'u buluom
		int startVal = getStartValue(colorFeature);
		NodeInterval node = (NodeInterval) super.Search(root, startVal);
		//ve o elemanlarý listeme atýom
		list.addAll(node.list);
		// --
		//bir sonraki ve bir önceki elemanlarýda listeme atýyoum
		NodeInterval temp = (NodeInterval) super.Successor(node);
		if (temp != null) {
			list.addAll(temp.list);
		}
		// --
		temp = (NodeInterval) super.Predecessor(node);
		if (temp != null) {
			list.addAll(temp.list);
		}

		NodeInterval success = (NodeInterval) super.Successor(node);
		NodeInterval predec = (NodeInterval) super.Predecessor(node);

		//Eðer bu elemanlar istediðin sayýda deðilse diðer aralýklarýdaki
		//imageobjectleride listeme atýom
		while (list.size() < count) {

			if (success == null && predec == null) {
				break;
			}
			if (success != null)
				success = (NodeInterval) super.Successor(success);

			if (predec != null)
				predec = (NodeInterval) super.Predecessor(predec);

			if (success != null) {
				list.addAll(success.list);
			}

			if (predec != null) {
				list.addAll(predec.list);
			}
		}
		
		//bulduðum elemanlarý istenilen imageobjecte yakýnlýðýna göre sýralamk için
		//quick sort kullandýk ve bunu kullanýrken gerekli elemanlarý hazýrladýk
		SortableImageObject[] sio = new SortableImageObject[list.size()];
		for (int i = 0; i < sio.length; i++) {
			sio[i] = new SortableImageObject(list.get(i), obj);
		}
		
		//quick sortu oluþturup sort ettirdik
		Quick<SortableImageObject> quickSort = new Quick<SortableImageObject>(sio);
		quickSort.sort();
		
		ImageObject[] arr = quickSort.getArray();
		
		//null pointerlara karþý en uygun size'ý seçtik ve arr'i döndürdük
		count = Math.min(count, list.size());

		ImageObject[] fin = new ImageObject[count];

		System.arraycopy(arr, 0, fin, 0, count);

		return fin;
	}

	//verilen deðerin aralýðýna göre o aralýýn baþlangýç deðerini veren method
	//
	//Ek bilgi olarak Node'da key olarak baþlangýç deðerini tutuk
	private int getStartValue(int value) {

		int ret = 0;
		ret = value / interval;
		ret = ret * interval;
		return ret;
	}

}
