public class Phonebook {

	private BinarySearchTree namesTree;
	private BinarySearchTree numbersTree;
	
	public Phonebook() {
		namesTree = new BinarySearchTree(new EntryComparatorByName());
		numbersTree = new BinarySearchTree(new EntryComparatorByNumber());
	}

	public PhoneEntry lookUp(String name){
		// create an Entry with the given name and a "dummy" number (1)
		// This "dummy" number will be ignored when executing getData
		PhoneEntry lookFor = new PhoneEntry(name, 1);
		return (PhoneEntry)namesTree.findData(lookFor);
	}
	public PhoneEntry lookUp(int number){
		// create an Entry with a "dummy" name and the given number
		// This "dummy" name will be ignored when executing getData
		PhoneEntry lookFor = new PhoneEntry("dummy", number);
		return (PhoneEntry)numbersTree.findData(lookFor);
	}
	
	public void balance(){
		namesTree = new BinarySearchTree(namesTree);
		numbersTree = new BinarySearchTree(numbersTree);
	}
	
	public Object exportNames() {
		return this.namesTree;
	}
	public Object exportNumbers() {
		return this.numbersTree;
	}
	
	// Complete the following methods:
	
	public boolean add(PhoneEntry newEntry) {
		if(newEntry == null)
			throw new IllegalArgumentException();
		if(namesTree.contains(newEntry) || numbersTree.contains(newEntry)) //check if the trees contains the data
			return false;
		namesTree.insert(newEntry);
		numbersTree.insert(newEntry);
		return true;
	}
	
	public boolean delete(String name){ //delete according to the name
		PhoneEntry toRemove = lookUp(name);
		/* Enter you code here:*/
		if(toRemove == null)
			return false;
		namesTree.remove(toRemove);
		numbersTree.remove(toRemove);
		return true;
		
	}
	
	public boolean delete(int number){ //delete according to the number
		PhoneEntry toRemove = lookUp(number);
		/* Enter you code here:*/
		if(toRemove == null)
			return false;
		namesTree.remove(toRemove);
		numbersTree.remove(toRemove);
		return true;	
	}
	
	


}
