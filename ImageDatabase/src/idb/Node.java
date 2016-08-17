package idb;
import javax.swing.tree.DefaultMutableTreeNode;

public class Node {

	int key;
	Node left;
	Node right;
	Node parent;

	public Node(int key) {
		this.key = key;
	}

	// TODO
	// JTree i�in gereken bir method olu�turuken ojet olarak kendisi veririm
	public DefaultMutableTreeNode getDefaultMutableTreeNode() {

		DefaultMutableTreeNode node = new DefaultMutableTreeNode(this);
		if (left != null)
			node.add(left.getDefaultMutableTreeNode());
		if (right != null)
			node.add(right.getDefaultMutableTreeNode());

		return node;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return key + "";
	}
}
