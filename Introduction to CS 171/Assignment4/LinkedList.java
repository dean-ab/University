import java.util.Iterator;

public class LinkedList implements List {
	private Link first;
	private Link last;

	public LinkedList(){
		first = null;
		last = null;
	}

	public void sortBy(Comparator comp){
		nullCheck(comp);
		for(int i = 0; i < size()-1; i++)
			for(int j = 0; j < size()-1-i; j++) 
				if(comp.compare(get(j), get(j+1)) > 0) {
					Object tmp = get(j);
					set(j, get(j+1));
					set(j+1, tmp);
				}
				
	}		
	
	public String toString() {
		 String output = "\n";
	     Link curr = first;
	     while (curr != null) {
	    	 output = output + "<" + curr.toString() + ">" + "\n";
	    	 curr = curr.getNext();
	     }
	    return output;
	}
	
	public boolean equals(Object other) {  

		if(!(other instanceof LinkedList))
			return false;
		LinkedList secList=(LinkedList) other; 
		if(size() != secList.size())
			return false;
		Link first=this.first;
		if(first==null)
			return true;
		int i = 0;
		while(first!=null)
		{
			if(!((first.getData()).equals(secList.get(i))))
				return false;
			first=first.getNext();
			i++;
		}
		return true;		
	}
//
//	public boolean equals(Object other) {  
//		boolean isEqual = true;
//		if (!(other instanceof LinkedList)) 
//			isEqual = false;
//		else {
//			Link curr1 = this.first;
//			Link curr2 = ((LinkedList) other).first;
//			while ((isEqual & curr1 != null) & (isEqual & curr2 != null)) {
//				isEqual = curr1.equals(curr2);
//				curr1 = curr1.getNext();
//				curr2 = curr2.getNext();
//			}
//			isEqual = (curr1 == null && curr2 == null);
//		}
//		return isEqual;
//	}
		
	public void add(int index, Object element) {
		rangeCheck(index);
		nullCheck(element);
		if(index == 0) {
			first = new Link(element, first) ;
			if(last == null)
				last = first ;
		} else {
			Link prev = null ;
			Link curr = first ;
			for(int i=0; i<index; i=i+1) {
				prev = curr ;
				curr = curr.getNext() ;
			}
			Link toAdd = new Link(element, curr);
			prev.setNext(toAdd);
			if(index == size())
				last = toAdd;
		}
	}

	public void add(Object element) {
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

	@Override
	public int size() {
		Link curr = this.first;
		int index = 0;
		while (curr != null){
			curr = curr.getNext();
			index ++;
		}
		return index;
	}

	@Override
	public boolean contains(Object element) {
		Link other = new Link(element);
		Link curr = first;
		boolean found = false;
		while(curr != null && !found) {
			found = curr.equals(other);
			curr = curr.getNext();			
		}
		return found;
	}

	@Override
	public boolean isEmpty() {
		if (this.first == null)
			return true;
		else return false;
	}

	@Override
	public Object get(int index) {
		rangeCheck(index);	
		Link curr = this.first;
		for (int i = 0; i < index; i++) {
			curr = curr.getNext();
		}
		return curr.getData();
	}

	@Override
	public Object set(int index, Object element) {
		rangeCheck(index);
		nullCheck(element);
		Link curr = first;
		for (int i = 0; i < index; i++)
			curr = curr.getNext();
		Object temp = curr.getData();
		curr.setData(element);
		return (temp);
	}

	@Override
	public Iterator iterator() {
		return new LinkedListIterator(first);
	}

	// throws an exception if the given index is not in range
	private void rangeCheck(int index) {
		if(index < 0 || index >= size())
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
	}
	
	// throws an exception if the given element is null
	private void nullCheck(Object element){
		if (element == null)
			throw new NullPointerException();
	}

}
