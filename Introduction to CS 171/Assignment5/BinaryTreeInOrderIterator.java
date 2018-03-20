import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryTreeInOrderIterator implements Iterator{
	private Stack stack;
 
	public BinaryTreeInOrderIterator(BinaryNode root) {
		stack = new StackAsDynamicArray();
		stack.push(root);
		pushUntilMin(root);
	}
	
	private void pushUntilMin(BinaryNode node) //prepare the next 
	{
		if(node.left != null)
		{
			stack.push(node.left);
			pushUntilMin(node.left);
		}
	}
 
	public boolean hasNext() {
		return !stack.isEmpty();
	}
 
	public Object next() {
		if(!hasNext())
			throw new NoSuchElementException();
		BinaryNode node = (BinaryNode)(stack.pop());
		if(node.right != null)
		{
			stack.push(node.right); //in order scan
			pushUntilMin(node.right);
		}
		return node.data;
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
