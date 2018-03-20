import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicArray implements List{

	private static final int DEFAULT_CAPACITY = 16;
	private Object[] data; 
	private int size; 
	private int incrementSize;          
	
    public DynamicArray(int initialCapacity){
	    if (initialCapacity <= 0)
			throw new IllegalArgumentException(" … ");
		    data = new Object[initialCapacity];    
		    incrementSize = initialCapacity;
		    size = 0;
    }
    public DynamicArray(){
    	this(DEFAULT_CAPACITY);  
    }

    public String toString() {
    	String output = "";
    	for (int index = 0; index < size(); index = index + 1) {
    		if ((index + 1) % 20 == 0) output = output + "\n";
    		output = output + get(index) + "\t";
    	}
    	return output;
    }
    
    public boolean equals(Object other) {
	    boolean isEqual = true;
	    if (! (other instanceof DynamicArray)) 
	    	isEqual = false;
	    else {
		      DynamicArray otherDA = (DynamicArray) other;
		      if (size() != otherDA.size()) 
		    	  isEqual = false;
		      else {
		    	  for (int index = 0; index < size() & isEqual; index = index + 1) {
		    		  isEqual = get(index).equals(otherDA.get(index));
		    	  }
		      }
	    }
	    return isEqual;
    }
    
    public int size(){
    	return size;
    }
    public boolean isEmpty(){
    	return size == 0;
    }
    public Object get(int index){
    	return data[index];
    }
    
    public Object set(int index, Object element){
		rangeCheck(index);
		dataCheck(element);
		Object tmp = get(index);
		data[index] = element;
		return tmp;
    }
    
	public void add(Object element){
		dataCheck(element);			
		data[size] = element;
		size = size+1;
	 	ensureCapacity(size);
	}
	
	public void add(int index, Object element){
		if(index < 0 || index > size())
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
		dataCheck(element);
		for(int i = size()-1; i >= index; i = i - 1)
			data[i] = data[i-1];
		data[index] = element;
		size = size+1;
	 	ensureCapacity(size);
	}
	
	// remove and return the last element in this array
	public Object remove(){
		if (isEmpty())
			throw new NoSuchElementException();
		Object output = data[size()-1];
		data[size()-1] = null;
		size = size -1;
		return output;
	}
	
	public boolean remove(Object element){
		boolean found = false;
		int i;
		for (i = 0; i<size() & !found ; i = i+1){
			if(data[i].equals(element)){
				found = true;
			}
		}
		if(found){
			for(;i<size();i = i+1){
				data[i-1] = data[i];
			}
			size = size-1;
			return true;
		}
		return false;
	}

	private void ensureCapacity(int minCapacity){
		if (minCapacity >= data.length){
			Object[] newData = new Object[data.length + incrementSize];
			for(int i=0; i<data.length; i=i+1)
				newData[i] = data[i];        
			data = newData;
		}
	}
	

	private void rangeCheck(int index) {
		if(index < 0 || index >= size())
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
	}
	
	private void dataCheck(Object element){
		if (element == null)
			throw new NullPointerException();
	}
	
	public boolean contains(Object element){
		  boolean output = false;
	        for(int i=0; (i<size()) && (! output); i=i+1)
	            if(get(i).equals(element)) output = true;
	        return output;
	}
}
