import java.util.NoSuchElementException;

public class QueueAsLinkedList implements Queue{
	
	private LinkedList list;
	
	public QueueAsLinkedList() {
		this.list = new LinkedList();
	}
	
	public void enqueue(Object element) {
		list.add(element);
	}
	
	public Object dequeue() {
		if(isEmpty())
			throw new NoSuchElementException();
		Object output = list.removeFirst();
		return output;
	}

	public boolean isEmpty() {
		return list.size() == 0;
	}
}
