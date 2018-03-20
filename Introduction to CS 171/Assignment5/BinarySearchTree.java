import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchTree extends BinaryTree implements Iterable{
	
	private Comparator treeComparator;
	
	// This constructor builds an empty tree
	public BinarySearchTree(Comparator myComparator){
		super();
		this.treeComparator = myComparator;
	}
	
	// This constructor is a copy-constructor
	// it creates a perfect tree with the same data as in otherTree
	public BinarySearchTree(BinarySearchTree otherTree){
		super();
		this.treeComparator = otherTree.getComparator();
		if(! otherTree.isEmpty())
			root = new BinarySearchNode((BinarySearchNode)otherTree.root, otherTree.iterator());
	}
	
	public Object findData(Object element){
		if(isEmpty())
			return null;
		return ((BinarySearchNode)root).findData(element);
	}

	public Comparator getComparator(){
		return treeComparator;
	}
	
	public void insert(Object toInsert) {
		if (isEmpty()) 
			root = new BinarySearchNode(toInsert, treeComparator);
		else 
			root.insert(toInsert);
	}
	
	public void remove(Object toRemove){
		if(isEmpty())
			throw new NoSuchElementException();
		root = ((BinarySearchNode)root).remove(toRemove);
	}
	
	public Iterator iterator(){
		return new BinaryTreeInOrderIterator(root);
	}																
}

