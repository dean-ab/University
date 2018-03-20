public class Link {
	
	private Object data;
	private Link next;
	
	public Link(Object data, Link next){
	    this.data = data;
	    this.next = next;
	}
	public Link(Object data){
	    this(data, null);
	}
	
	public Object getData(){
		return data;
	}
	
	public Object setData(Object data){
		Object tmp = this.data;
		this.data = data; 
		return tmp;
	}
	
	public Link getNext(){
		return next;
	}
	
	public void setNext(Link next){
		this.next = next;
	}
	
	public String toString(){ 
		return data.toString();
	}
	
   	public boolean equals(Object other){
   		return data.equals(((Link)other).getData());
   	}

}
