package idb;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class NodeInterval extends NodeAVL {

	//kendisine ait aral�ktaki de�erleri tutabilmesi i�in bir liste
	public ArrayList<ImageObject> list;

	//Key olarak bu aral���n en d���k de�erini veririm
	public NodeInterval(int lowBound) {
		super(new ImageObject(lowBound, null));
		list = new ArrayList<ImageObject>();
	}
	
	//JTreede bu aral�k hangi de�erle ba�l�yor ve i�indeki de�erler yazd�rmas�
	@Override
	public String toString(){
		return key + " : " + list.toString();
	}
}
