public interface Stack {
	
	//Returns true iff this stack is empty.
	public boolean isEmpty();
	
	//Removes the object at the top of this stack and returns that object.
	public Object pop();
	
	//Pushes an item onto the top of this stack.
	public void push(Object element);

}
