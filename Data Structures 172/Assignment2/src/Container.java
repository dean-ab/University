
//Don't change the class name
public class Container{
	private Point data;//Don't delete or change this field;
	private Container next;
	private Container prev;
	private Container opp;
	
	/* Constructor */
    public Container(Point data)
    {
        this.next = null;
        this.prev = null;
        this.data = data;
    }
    
    /* Constructor */
    public Container(Point data, Container next, Container prev)
    {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
    
    // Set an opposite Container
    public void setOpp(Container opp)
    {
        this.opp = opp;
    }
    
    // Get the opposite Container
    public Container getOpp()
    {
       return opp;
    }
    
    /* Function to set link to next Container */
    public void setNext(Container next)
    {
        this.next = next;
    }
 
    /* Function to set a previous Container */
    public void setPrev(Container prev)
    {
        this.prev = prev;
    }    
    /* Function to get a link to next Container */
    public Container getNext()
    {
        return next;
    }
    
    /* Function to get link to previous node */
    public Container getPrev()
    {
        return prev;
    }
    
    /* Function to set data to Container */
    public void setData(Point toSet)
    {
        this.data = toSet;
    }

	//Don't delete or change this function
	public Point getData()
	{
		return data;
	}
	
	public String toString(){ 
		return data.toString();
	}
	
	// Point must be equal on BOTH x & y coordinates
	public boolean equals(Object other) {
		if (!(other instanceof Container))
			return false;
		else return (this.data.equals(((Container) other).getData()));
	}
	
	// Compare the point inside the container by their x coordinate
	public int compareX(Container cont)
	{
		int p1 = data.getX();
		int p2 = cont.getData().getX();
		if (p1 < p2)
			return -1;
		if (p1 > p2)
		   return 1;
		return 0;		
	}
	
	// Compare the point inside the container by their y coordinate
	public int compareY(Container cont)
	{
		int p1 = data.getY();
		int p2 = cont.getData().getY();
		if (p1 < p2)
			return -1;
		if (p1 > p2)
		   return 1;
		return 0;		
	}

}
