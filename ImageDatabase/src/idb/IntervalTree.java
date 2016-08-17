package idb;

import java.util.ArrayList;

import sorting.Quick;
import sorting.SortableImageObject;

public class IntervalTree extends AVL_Tree {

	//Her aral���n ne kadar olaca��n� veren arkada�
	public final int interval;

	//A�ac� olu�turuken aral�k de�erini al�r�m
	public IntervalTree(int interval) {
		super();
		this.interval = interval;
	}

	//
	@Override
	public void insert_imageObject(ImageObject obj) {
		// TODO Auto-generated method stub
		
		//verilen objection hangi node gidebilece�ini bulur ve bunu a�a�tan isterim
		int seaVal = getStartValue(obj.colorFeature);
		Node sea = super.Search(root, seaVal);

		if (sea == null) {
			// E�er �yle bir aral�k hi� yoksa demek ben bu aral��� yarat�cam
			NodeInterval newNode = new NodeInterval(seaVal);
			newNode.list.add(obj);
			super.InsertIterative(newNode);
		} else {
			// E�er �yle bir aral�k varsa bu aral��a kendi deerimi eklerim
			NodeInterval in = (NodeInterval) sea;
			in.list.add(obj);
		}
	}
	
	//
	@Override
	public void delete_imageObject(ImageObject obj) {
		// TODO Auto-generated method stub
		int colorFeature = obj.colorFeature;
		//verilen objection hangi node da olabilece�ini bulur
		//ve bu node'u a�a�tan isterim
		int seaVal = getStartValue(colorFeature);
		Node sea = super.Search(root, seaVal);

		if (sea != null) {
			//E�er byle bir node varsa demekki belki b�yle bu object olabilir
			NodeInterval ni = (NodeInterval) sea;
			for (int i = 0; i < ni.list.size(); i++) {
				//E�er bu objecti listemde bulursam silerim
				if (ni.list.get(i).colorFeature == colorFeature) {
					ni.list.remove(i);
					break;
				}
			}
			
			//e�er bu node'un listesinde eleman kalmam��sa bu node'u a�a�tan silerim
			if (ni.list.size() == 0) {
				super.DeleteIterative(seaVal);
			}
		}

	}

	//Verilen imageobjecte g�re istenilen ssay� kadar yak�n imageobject d�nd�ren fonksiyon
	@Override
	public ImageObject[] getClosest(ImageObject obj, int count) {
		// TODO Auto-generated method stub
		int colorFeature = obj.colorFeature;
		//yak�n olabilecek imageobjectleri tutabilece�im bir liste
		ArrayList<ImageObject> list = new ArrayList<ImageObject>();
		//ve bu elemn�n olabilece�i node'u buluom
		int startVal = getStartValue(colorFeature);
		NodeInterval node = (NodeInterval) super.Search(root, startVal);
		//ve o elemanlar� listeme at�om
		list.addAll(node.list);
		// --
		//bir sonraki ve bir �nceki elemanlar�da listeme at�youm
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

		//E�er bu elemanlar istedi�in say�da de�ilse di�er aral�klar�daki
		//imageobjectleride listeme at�om
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
		
		//buldu�um elemanlar� istenilen imageobjecte yak�nl���na g�re s�ralamk i�in
		//quick sort kulland�k ve bunu kullan�rken gerekli elemanlar� haz�rlad�k
		SortableImageObject[] sio = new SortableImageObject[list.size()];
		for (int i = 0; i < sio.length; i++) {
			sio[i] = new SortableImageObject(list.get(i), obj);
		}
		
		//quick sortu olu�turup sort ettirdik
		Quick<SortableImageObject> quickSort = new Quick<SortableImageObject>(sio);
		quickSort.sort();
		
		ImageObject[] arr = quickSort.getArray();
		
		//null pointerlara kar�� en uygun size'� se�tik ve arr'i d�nd�rd�k
		count = Math.min(count, list.size());

		ImageObject[] fin = new ImageObject[count];

		System.arraycopy(arr, 0, fin, 0, count);

		return fin;
	}

	//verilen de�erin aral���na g�re o aral��n ba�lang�� de�erini veren method
	//
	//Ek bilgi olarak Node'da key olarak ba�lang�� de�erini tutuk
	private int getStartValue(int value) {

		int ret = 0;
		ret = value / interval;
		ret = ret * interval;
		return ret;
	}

}
