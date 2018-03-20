import java.util.Comparator;
public class LinkedList {
	private Container start;
	private Container end;
	private int size;
	
	public LinkedList()
    {
        start = null;
        end = null;
        size = 0;
    }
	
	public boolean isEmpty()
    {
        return start == null;
    }
	
	public int getSize()
    {
        return size;
    }
	
	public Container getLast()
    {
        return end;
        
    }
	
	public Container getFirst()
    {
        return start;
    }
	
	 public void insert(Container toInsert,Comparator comp) {
		 if(start == null)
	        {
	            start = toInsert;
	            end = start;
	        }
	        else
	        {
	            Container scanner = start;
	            if ((comp.compare(toInsert.getData(), scanner.getData()) == -1)) {
            		scanner.setPrev(toInsert);
            		toInsert.setNext(scanner);
            		toInsert.setPrev(null);
            		start = toInsert;
					size ++;
					return; }
	            while (scanner != null) {
	            	if ((comp.compare(toInsert.getData(), scanner.getData()) == -1)) {
	            		Container tmp = scanner.getPrev();
	            		scanner.setPrev(toInsert);
	            		toInsert.setNext(scanner);
	            		tmp.setNext(toInsert);
	            		toInsert.setPrev(tmp);
						size ++;
						return;
	            	}
	            	scanner = scanner.getNext();	
	            }
	            toInsert.setPrev(end);
				toInsert.setNext(null);
				end.setNext(toInsert);
				end = toInsert;
	        }
		 
	        size++;
	    }

    public void deleteAtPos(int pos)
    {        
        if (pos == 1) 
        {
            if (size == 1)
            {
                start = null;
                end = null;
                size = 0;
                return; 
            }
            start = start.getNext();
            start.setPrev(null);
            size--; 
            return ;
        }
        if (pos == size)
        {
            end = end.getPrev();
            end.setNext(null);
            size-- ;
        }
        Container scanner = start.getNext();
        for (int i = 2; i <= size; i++)
        {
            if (i == pos)
            {
                Container p = scanner.getPrev();
                Container n = scanner.getNext();
 
                p.setNext(n);
                n.setPrev(p);
                size-- ;
                return;
            }
            scanner = scanner.getNext();
        }        
    }    
    
    public Container delete(Container cont)
	{
		if(cont.getPrev() == null)
		{
			if(cont.getNext()==null)
			{
				start=null;
				end=null;
				size--;
				return null;
			}
			else
			{
				start = cont.getNext();
				start.setPrev(null);
				size--;
				return start;
			}
		}
		else
		{
			if(cont.getNext()==null)
			{
				end = cont.getPrev();
				end.setNext(null);
				size--;
				return null;
			}
			Container tmp = cont.getPrev();	 
			tmp.setNext(cont.getNext());
			cont.getNext().setPrev(tmp);
			size--;
			return tmp.getNext();
		}
	}

    public String toString()
    {        
    	String output = "";
        if (this.isEmpty()) {
        	output = "There are no Containers in the list";
        	return output; }
        else {
        	output = "[ ";
        	Container scanner = start;
        	for (int i = 1; i <= size; i++)
            {
                output = output + scanner.toString() + "| ";
                scanner = scanner.getNext();
            }
        	
        	return output.substring(0, output.length()-2)+"]";
        }
        
    }
}

