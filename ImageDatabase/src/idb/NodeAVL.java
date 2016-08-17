package idb;
public class NodeAVL extends Node {
	
	public ImageObject obj;
	int height;

	private static int height(NodeAVL t) {
		return t == null ? -1 : t.height;
	}
	
	public NodeAVL(ImageObject obj){
		super(obj.colorFeature);
		this.obj = obj;
	}

	public int getBalanceFactor() {

		return (height((NodeAVL) this.right) - (height((NodeAVL) this.left)));
	}
	
	//JTreede gösterilecek kýsým olarak kendisine ait olan imageobject bilgisini veririm
	public String toString(){
		return obj.toString();
	}
}
