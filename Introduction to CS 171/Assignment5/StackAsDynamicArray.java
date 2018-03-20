import java.util.NoSuchElementException;

public class StackAsDynamicArray implements Stack{

	private DynamicArray array;
	
	public StackAsDynamicArray() {
		this.array = new DynamicArray();
	}
	
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	public Object pop(){
		if(isEmpty())
			throw new NoSuchElementException();
		return array.remove();
	}
		
	public void push(Object element){
		array.add(element);		
	}
}
