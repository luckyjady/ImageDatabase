package idb;
import java.util.Stack;

public class Binary_Search_Tree {

	Node root;

	public Binary_Search_Tree() {
		root = null;
	}

	public Node Search(Node x, int key) {
		// TODO
		while (x != null && x.key != key) {

			x = (key < x.key) ? x.left : x.right;
		}
		return x;
		/*
		 * if(x == null){ return null; } else if(key == x.key){ return x; } else
		 * if(key < x.key){ return Search(x.left, key); }
		 * 
		 * return Search(x.right, key); // remove this line
		 */
	}

	public Node Minimum(Node x) {
		while (x.left != null)
			x = x.left;
		return x;
	}

	public Node Maximum(Node x) {
		while (x.right != null)
			x = x.right;
		return x;
	}

	public Node Predecessor(Node x) {
		if (x.left != null)
			return Maximum(x.left);
		Node y = x.parent;
		while (y != null && x == y.left) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	public Node Successor(Node x) {
		if (x.right != null)
			return Minimum(x.right);
		Node y = x.parent;
		while (y != null && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	public Node Insert(Node z) {
		Node y = null;
		Node x = root;
		while (x != null) {
			y = x;
			if (z.key < x.key)
				x = x.left;
			else
				x = x.right;
		}
		z.parent = y;
		if (y == null)
			root = z; // tree was empty
		else if (z.key < y.key)
			y.left = z;
		else
			y.right = z;
		return z;
	}

	public void Transplant(Node u, Node v) {
		if (u.parent == null)
			root = v;
		else if (u == u.parent.left)
			u.parent.left = v;
		else
			u.parent.right = v;
		if (v != null)
			v.parent = u.parent;
	}

	public Node Delete(int key) {
		Node z = Search(this.root, key);
		if (z == null)
			return null;
		return Delete(z);
	}

	public Node Delete(Node z) {
		Node p = z.parent;
		if (z.left == null)
			Transplant(z, z.right);
		else if (z.right == null)
			Transplant(z, z.left);
		else {
			Node y = Maximum(z.left);
			if (y.parent != z) {
				Transplant(y, y.left);
				y.left = z.left;
				y.left.parent = y;
			}
			Transplant(z, y);
			y.right = z.right;
			y.right.parent = y;

		}
		return p;
	}

	public void Inorder_Tree_Walk(Node x) {

		if (x != null) {

			Inorder_Tree_Walk(x.left);
			System.out.println(x.key + ",");
			Inorder_Tree_Walk(x.right);

		}
	}

	public void Iterative_Inorder_Traversal(Node x) {
		Stack<Node> s = new Stack<Node>();
		Node current = x;
		while (!s.empty() || current != null) {
			if (current != null) {
				s.push(current);
				current = current.left;
			} else {
				current = s.pop();
				System.out.print(current.key + ",");
				current = current.right;
			}
		}
	}

}
