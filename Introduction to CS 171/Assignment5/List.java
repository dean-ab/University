public interface List{
	
	// returns the number of elements in the list
   	public int size();
   	
   	// returns true if the list is empty
	public boolean isEmpty();
	
	// Returns true if this list contains the specified element.
	public boolean contains(Object element);
	
	// Replaces the element at the specified position in this list with the specified element.
	public Object set(int index, Object element);
	
	// Returns the element at the specified position in this list.
	public Object get(int index);

	// Appends the specified element to the end of this list.
	public void add(Object element);
	
	// Inserts the specified element at the specified position in this list.
	public void add(int index, Object element);
	
}
