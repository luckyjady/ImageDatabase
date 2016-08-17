package idb;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class NodeInterval extends NodeAVL {

	//kendisine ait aralýktaki deðerleri tutabilmesi için bir liste
	public ArrayList<ImageObject> list;

	//Key olarak bu aralýðýn en düþük deðerini veririm
	public NodeInterval(int lowBound) {
		super(new ImageObject(lowBound, null));
		list = new ArrayList<ImageObject>();
	}
	
	//JTreede bu aralýk hangi deðerle baþlýyor ve içindeki deðerler yazdýrmasý
	@Override
	public String toString(){
		return key + " : " + list.toString();
	}
}
