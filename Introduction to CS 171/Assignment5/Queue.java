public interface Queue {
	
	//Returns true iff this queue is empty.
	public boolean isEmpty();
	
	//Removes the object at the front of this queue and returns that object.
	public Object dequeue();
	
	//Insert an item into the back of this queue.
	public void enqueue(Object element);


}
