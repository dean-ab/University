// SUBMIT
public class BTree implements BTreeInterface {

	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private BNode root;
	private final int t;

	/**
	 * Construct an empty tree.
	 */
	public BTree(int t) { //
		this.t = t;
		this.root = null;
	}

	// For testing purposes.
	public BTree(int t, BNode root) {
		this.t = t;
		this.root = root;
	}

	@Override
	public BNode getRoot() {
		return root;
	}

	@Override
	public int getT() {
		return t;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		result = prime * result + t;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BTree other = (BTree) obj;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		if (t != other.t)
			return false;
		return true;
	}
	
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////


	@Override
	public Block search(int key) {
		if (isEmpty())
			return null;
		return root.search(key);
	}

	@Override
	public void insert(Block b) {
		if (isEmpty())
			root = new BNode(t, b);
		else {
			BNode r = root;
			if (r.isFull()) {
				BNode s = new BNode (t, r); // create new root
				root = s;
				s.splitChild(0); // split the root
				s.insertNonFull(b);
			} else {
				r.insertNonFull(b);
			}
		}
	}

	@Override
	public void delete(int key) {
		if(root.getNumOfBlocks()==1 && root.isLeaf())
		{
			root=null;
			return;
		}

		if(root.getNumOfBlocks()==1 && !root.isLeaf() && root.getChildAt(0).isMinSize() && root.getChildAt(1).isMinSize())
		{
			root = root.rightMerge(0);
			BNode node = root.findNode(key);
			if(node == null)
				return;
			node.delete(key);
		}
		else
		{
			BNode node = root.findNode(key);
			if(node == null)
				return;
			node.delete(key);
		}
	}

	@Override
	public MerkleBNode createMBT() {
		return root.createHashNode();
	}

	private boolean isEmpty() {
		return (root == null);
	}
	
	public void print() {
		root.print(0);
		System.out.println("--------");
		System.out.println();
	}

}
