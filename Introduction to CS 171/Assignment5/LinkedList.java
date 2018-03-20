import java.util.Iterator;
import java.util.NoSuchElementException;
public class LinkedList implements List{

	private Link first;
	private Link last;

	public LinkedList(){
		first = null;
		last = null;
	}
	
	public void addFirst(Object element) {
		nullCheck(element);
		if(isEmpty()){
			first = new Link(element);
			last = first;
		}
		else {
		   Link newFirst = new Link(element, first);
		   first = newFirst;
	   }
	}
	   
	public void add(Object element){
		nullCheck(element);
		if(isEmpty()){
			first = new Link(element);
			last = first;
		}
		else {
		   Link newLast = new Link(element);
		   last.setNext(newLast);
		   last = newLast;
	   }
	}

	public void add(int index, Object element){
		if(index < 0 || index > size())
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
		nullCheck(element);
		if(index == 0) {
			addFirst(element);
		} else {
			Link prev = null ;
			Link curr = first ;
			for(int i=0; i<index; i=i+1){
				prev = curr ;
				curr = curr.getNext() ;
			}
			Link toAdd = new Link(element, curr);
			prev.setNext(toAdd);
			if(index == size())
				last = toAdd;
		}
	}
	
   	public int size(){
   		int size = 0;
   		Link curr = first;
   		while(curr != null){
   			size = size+ 1;
   			curr = curr.getNext();
   		}
   		return size;
   	}
   	
	public boolean isEmpty(){
   		return first == null;
	}
	
	public boolean contains(Object element){
   		for(Link curr = first; curr != null; curr = curr.getNext()){
   			if(element.equals(curr.getData()))
   				return true;
   		}
		return false;
	}
	
	public Object set(int index, Object element){
		rangeCheck(index);
		nullCheck(element);
		Link curr = first;
		for(int i = 0; i < index; i = i+1){
   			curr = curr.getNext();
   		}
		Object output = curr.getData();
		curr.setData(element);
   		return output;
	}
	
	public Object get(int index){
		Link curr = first;
   		for(int i = 0; i < index; i = i+1){
   			curr = curr.getNext();
   		}
   		return curr.getData();
	}
	public Object removeFirst() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		Object output = first.getData();
		first = first.getNext();
		return output;
	}
	
	public boolean remove(Object element){
		nullCheck(element);
		Link current = first;
		Link prev = current;
		boolean removed = false;
		while (current != null & !removed) {
			if (element.equals(current.getData())) {
				if (first == current) {
					first = first.getNext();
				}
				else {
					prev.setNext(current.getNext());
				}
				if(last == current){
					last = prev;
				}
				return true;				
			}
			else {
				prev = current;
				current = current.getNext();
			}
		}
		return false;
	}
	
	public String toString() {
		if (isEmpty())
			return "[]";
	     String output = "[";
	     Link current = first;
	     while (current != null) {
	    	 output = output + current.toString()+"\t";
	    	 current = current.getNext();
	    }
	    return output.substring(0, output.length()-1)+"]";
	}
	
	private void rangeCheck(int index) {
		if(index < 0 || index >= size())
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
	}
	
	private void nullCheck(Object element){
		if (element == null)
			throw new NullPointerException();
	}
}
