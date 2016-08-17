package idb;
import java.util.Stack;

import sorting.Quick;
import sorting.SortableImageObject;

public class AVL_Tree extends Binary_Search_Tree {
	private static int height(NodeAVL t) {
		return t == null ? -1 : t.height;
	}

	/* Rotate binary tree node with left child */
	private NodeAVL rotate_right(NodeAVL k2) {
		//System.out.println(k2.key + " right rotate");
		NodeAVL k1 = (NodeAVL) k2.left;
		k2.left = k1.right;
		k1.right = k2;
		// update parents
		k1.parent = k2.parent;
		k2.parent = k1;
		if (k2.left != null)
			k2.left.parent = k2;

		k2.height = Math.max(height((NodeAVL) k2.left),
				height((NodeAVL) k2.right)) + 1;
		k1.height = Math.max(height((NodeAVL) k1.left), k2.height) + 1;
		return k1;
	}

	/* Rotate binary tree node with right child */
	private NodeAVL rotate_left(NodeAVL k1) {
		//System.out.println(k1.key + " left rotate");

		NodeAVL k2 = (NodeAVL) k1.right;
		k1.right = k2.left;
		k2.left = k1;

		// update parents
		k2.parent = k1.parent;
		k1.parent = k2;
		if (k1.right != null)
			k1.right.parent = k1;

		k1.height = Math.max(height((NodeAVL) k1.left),
				height((NodeAVL) k1.right)) + 1;
		k2.height = Math.max(height((NodeAVL) k2.right),
				height((NodeAVL) k2.left)) + 1;
		return k2;
	}

	/**
	 * Double rotate binary tree node: first left child with its right child;
	 * then node k3 with new left child
	 */
	private NodeAVL rotate_left_right(NodeAVL k3) {
		//System.out.println(k3.key + " double left right rotate");

		k3.left = rotate_left((NodeAVL) k3.left);
		return rotate_right(k3);
	}

	/**
	 * Double rotate binary tree node: first right child with its left child;
	 * then node k1 with new right child
	 */
	private NodeAVL rotate_right_left(NodeAVL k1) {
		//System.out.println(k1.key + " double right left rotate");

		k1.right = rotate_right((NodeAVL) k1.right);
		return rotate_left(k1);
	}

	public void DeleteIterative(int k) {

		// TODO
		// write delete code
		NodeAVL temp = (NodeAVL) super.Delete(k);
		restructure(temp);
	}

	/*
	 * • Balance factor -2 (left subtree longer), balance factor of the left
	 * daughter -1 or 0: single right rotation. • Balance factor 2 (right
	 * subtree longer), balance factor of the right daughter 1 or 0: single left
	 * rotation Balance factor -2, balance factor of the left daughter 1: double
	 * rotation, around the daughter to the left, around the root to the right.
	 * • Balance factor 2, balance factor of the right daughter -1: double
	 * rotation, around the daughter to the right, around the root to the left.
	 */
	public void InsertIterative(NodeAVL x) {
		x.height = 0;
		x = (NodeAVL) super.Insert(x);

		this.root = restructure(x);
	}

	public NodeAVL restructure(NodeAVL x) {
		NodeAVL y = null;

		while (x != null) {

			if (x.getBalanceFactor() < -1) { // left longer
				if (((NodeAVL) x.left).getBalanceFactor() <= 0) {
					x = rotate_right(x);
				} else {
					x = rotate_left_right(x);
				}
			} else if (x.getBalanceFactor() > 1) {
				if (((NodeAVL) x.right).getBalanceFactor() >= 0) {
					x = rotate_left(x);
				} else {
					x = rotate_right_left(x);
				}
			}
			if (x.parent != null)
				if (x.key < x.parent.key)
					x.parent.left = x;
				else
					x.parent.right = x;
			else
				this.root = x;
			x.height = Math.max(height((NodeAVL) x.left),
					height((NodeAVL) x.right)) + 1;
			y = x;
			x = (NodeAVL) x.parent;
		}
		return y;
	}
	
	@Deprecated
	public void Insert(NodeAVL z) {
		root = Insert(z, (NodeAVL) root);
	}
	
	@Deprecated
	public NodeAVL Insert(NodeAVL x, NodeAVL t) {
		if (t == null)
			t = x;
		else if (x == null || t == null) {
			return null;
		}
		if (x.key < t.key) {
			t.left = Insert(x, (NodeAVL) t.left);
			if (height((NodeAVL) t.left) - height((NodeAVL) t.right) == 2)
				if (x.key < t.left.key)
					t = rotate_right(t);
				else
					t = rotate_left_right(t);
		} else if (x.key > t.key) {
			t.right = Insert(x, (NodeAVL) t.right);
			if (height((NodeAVL) t.right) - height((NodeAVL) t.left) == 2)
				if (x.key > t.right.key)
					t = rotate_left(t);
				else
					t = rotate_right_left(t);
		} else
			; // Duplicate; do nothing
		t.height = Math
				.max(height((NodeAVL) t.left), height((NodeAVL) t.right)) + 1;
		return t;
	}

	public void Iterative_Inorder_Traversal_balances(NodeAVL x) {
		Stack<NodeAVL> s = new Stack<NodeAVL>();
		NodeAVL current = x;
		while (!s.empty() || current != null) {
			if (current != null) {
				s.push(current);
				current = (NodeAVL) current.left;
			} else {
				current = s.pop();
				current = (NodeAVL) current.right;
			}
		}
	}

	public void Inorder_balance(NodeAVL x) {
		if (x != null) {
			Inorder_balance((NodeAVL) x.left);
			System.out.print(x.height + ",");
			Inorder_balance((NodeAVL) x.right);
		}
	}

	/*
	 * Interval ve AVL tree için ortak method
	 * Amaç bana verilen imageobjecti kendi aðacýma eklerim
	 * 
	 * Böylelikle sadece tek bir aðac tutup bu aðacý kullanýcýya göre
	 * oluþturup ayný methodlarý kullanmak için
	 */
	public void insert_imageObject(ImageObject obj){
		
		NodeAVL node = new NodeAVL(obj);
		this.InsertIterative(node);
	}
	/*
	 * Interval ve AVL tree için ortak method
	 * Amaç bana verilen imageobjecti kendi aðacýmdan silerim
	 */
	public void delete_imageObject(ImageObject obj){
		
		this.DeleteIterative(obj.colorFeature);
	}
	/*
	 * Verilen imageobjecte göre istenilen sayýda ona yakýn resim döndüren arkadaþ
	 */
	public ImageObject[] getClosest(ImageObject obj,int count){
		//Verilen imageobjecte en yakýn imageobjecti bulup
		//ondan sonra istenilen sayýdaki imageobjecti ona göre döndürcek
		
		NodeAVL temp = (NodeAVL) root;
		NodeAVL success ;
		NodeAVL preccess ;
		while(true){
			
			//þu anda bulunduum elemandan bir sonraki ve bir önceki elemanlarý alýom
			success = (NodeAVL) Successor(temp);
			preccess = (NodeAVL) Predecessor(temp);
			
			//Farklarý hesaplýom.. Eðer bir önce eleman yoksa farký sonsuz alýom ki karþýlaþtýrma yaparken sorun çýkarmasýn
			int diff = Math.abs(temp.obj.colorFeature - obj.colorFeature);
			int diffsuc = success != null ? Math.abs(success.obj.colorFeature - obj.colorFeature) : Integer.MAX_VALUE ;
			int diffpre = preccess != null ? Math.abs(preccess.obj.colorFeature - obj.colorFeature) : Integer.MAX_VALUE;
					
			//Eðer en yakýn þu anda bulumduðum elemansa aðaçta bundan yakýn eleman yoktur
			//bu yüzden döngüden çýkarým
			if(diff < diffsuc && diff < diffpre){
				break;
			}
			
			//Eðer bir sonraki eleman daha yakýnsa istenilen imageobjecte sað tarafa doðru atlarým
			if(diffsuc < diffpre){
				temp = (NodeAVL)temp.right;
			}
			else{
				temp = (NodeAVL)temp.left;
			}
		}
		//Ýstenilen imageobjecte en yakýn image objecti bulduksan sonra
		//aðaçta bulunan image objecte en yakýnlarý döndürebilirim
		return getClosestHelper(temp.obj, count);
	}
	
	
	public ImageObject[] getClosestHelper(ImageObject obj,int count){
		//verilen imageobjecte ait node aldým
		NodeAVL head = (NodeAVL) Search(root, obj.colorFeature);
		
		//bir sonraki ve bir önceki adamlarý aldým
		NodeAVL next = (NodeAVL) Successor(head);
		NodeAVL prev = (NodeAVL) Predecessor(head);
		
		//buna yakýn olabilecek ihtimalli arkadaþlarý bir arrayde toplýcam
		ImageObject[] ret = new ImageObject[2 * count + 1];
		//ve bunun ilk elemaný kendisi olacak
		ret[0] = obj;
		//arrayin hangi indexine eklicem elemaný bunun için bi deðiþken
		int counter = 1;

		for (int i = 0; i < count; i++) {
			//eðer bir sonraki eleman varsa alýrým ve ilerlerim
			if (next != null) {
				ret[counter] = next.obj;
				next = (NodeAVL) Successor(next);
				counter++;
			}
			//ayný þekilde eðer bir önce eleman varsa arraye atar ve bi geri giderim
			if (prev != null) {
				ret[counter] = prev.obj;
				prev = (NodeAVL) Predecessor(prev);
				counter++;
			}
		}
		
		//DEBUG
		//for (int i = 0; i < ret.length; i++) {
			//System.out.println(ret[i].colorFeature);
		//}
		//--
		//System.out.println("-------------");
		//--
		//--
		
		//Bu bulduðum arkadaþlarýn istenilen arkadaþa yakýnlýðýna göre sýralamak için
		//quick sort yöntemini kullanýrým ve bu yöntemi kullanmak için gerekli arkadaþlarý oluþtururum
		SortableImageObject[] sio = new SortableImageObject[counter];
		for (int i = 0; i < sio.length; i++) {
			sio[i] = new SortableImageObject(ret[i], obj);
		}
		//ve bu elamanlarý qucksorta sýralatýrým
		Quick<SortableImageObject> quickSort = new Quick<SortableImageObject>(sio);
		quickSort.sort();
		//sonucu quick sorttan alýrým
		ret = quickSort.getArray();
		//Ve herhangi bir nullpointere karþý döndürebilceðim uygun elemansayýsýný seçer
		//o kadar elemanlý arrayý yaratýp veririm
		counter = Math.min(counter, count);
		
		ImageObject[] fin = new ImageObject[counter];
		
		System.arraycopy(ret, 0, fin, 0, counter);
		
		return fin;
	}
}
