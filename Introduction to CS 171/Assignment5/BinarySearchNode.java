import java.util.Comparator;
import java.util.Iterator;

public class BinarySearchNode extends BinaryNode{

	private Comparator treeComparator;
	
	public BinarySearchNode(Object data, Comparator myComparator) {
		super(data);
		this.treeComparator = myComparator;
	}
	
	// assume otherTreeRoot.size()>0
	public BinarySearchNode(BinarySearchNode otherTreeRoot, Iterator otherTreeIterator) {
		super("dummy");
		treeComparator = otherTreeRoot.getComparator();
		buildPerfectTree(otherTreeRoot.size());
		fillTheNodes(this, otherTreeIterator);
	}
	
	// element is an Entry with one "dummy" field
	public Object findData(Object element){
		if (treeComparator.compare(data, element) > 0){
			if (left != null )
				return ((BinarySearchNode)left).findData(element);
			else
				return null;
		}
		else if(treeComparator.compare(data, element) < 0){
			if (right != null )
				return ((BinarySearchNode)right).findData(element);
			else
				return null;
		}
		else 
			return this.data;
	}
	
	public Object findMin(){
		BinaryNode currNode = this;
		while (currNode.left != null){
			currNode = currNode.left;
		}
		return currNode.data;
	}
	
	// Complete the following methods:
	
	private void buildPerfectTree(int size){
		Queue q = new QueueAsLinkedList();
		q.enqueue(this);
		// create the remaining size-1 nodes in the tree
		String dummy = "dummy";
		BinarySearchNode currNode = this;
		int sumOfNodes=1; //count the nodes that have been added 
		while(sumOfNodes<size) 
		{
			if(currNode.left == null)
			{
				currNode.left = new BinarySearchNode(dummy, treeComparator);
				sumOfNodes++; 
				q.enqueue(currNode.left);
			}
			else
			if(currNode.right == null)
			{
				currNode.right = new BinarySearchNode(dummy, treeComparator);
				sumOfNodes++;
				q.enqueue(currNode.right);
			}
			else
				currNode = (BinarySearchNode) q.dequeue();
		}
		
		// now this is the root of a tree with size dummy nodes
	}
	
	private void fillTheNodes(BinarySearchNode root, Iterator treeIterator){ //scan in order and insert the data
		if(root.left != null)
			((BinarySearchNode) root.left).fillTheNodes((BinarySearchNode) root.left, treeIterator);
		Object newData = treeIterator.next();
		data= newData;
		if(root.right != null)
			((BinarySearchNode) root.right).fillTheNodes((BinarySearchNode) root.right, treeIterator);
		
	}

	public Comparator getComparator(){
		return treeComparator; 
	}

	public void insert(Object toInsert) {
		if(toInsert == null)
			throw new NullPointerException();
		int comp = treeComparator.compare(data, toInsert);
		if(comp>0) //check if to insert to the right or to the left
		{
			if(left==null)
				left = new BinarySearchNode(toInsert , treeComparator);
			else
				((BinarySearchNode)left).insert(toInsert);
		}		
		if(comp<0)
		{
			if(right==null)
				right = new BinarySearchNode(toInsert , treeComparator);
			else
				((BinarySearchNode)right).insert(toInsert);
		}
		}
	
	
	public boolean contains(Object element) {
		return ((findData(element)) != null); //use findData
		
	}
	
	public BinaryNode remove(Object toRemove){
		if(toRemove == null)
			throw new NullPointerException();
		int comp = treeComparator.compare(this.data, toRemove);
		if(comp>0) //check if to remove from the right from to the left
		{
			if (left != null)
				left = ((BinarySearchNode) left).remove(toRemove);
		}
		else if(comp<0)
		{
			if (right != null)
				right = ((BinarySearchNode) right).remove(toRemove);
		}
		else 
		{
			if (left == null || right == null){ // the base cases...
				if (left == null)
					return right;
				else
					return left;
			}
			else{ // this node has two children
				data = ((BinarySearchNode) right).findMin();
				right = ((BinarySearchNode) right).remove(data);
			}
		}
		return this;
		}
	}



