import java.util.ArrayList;

//SUBMIT
public class BNode implements BNodeInterface {

	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private final int t;
	private int numOfBlocks;
	private boolean isLeaf;
	private ArrayList<Block> blocksList;
	private ArrayList<BNode> childrenList;

	/**
	 * Constructor for creating a node with a single child.<br>
	 * Useful for creating a new root.
	 */
	public BNode(int t, BNode firstChild) {
		this(t, false, 0);
		this.childrenList.add(firstChild);
	}

	/**
	 * Constructor for creating a <b>leaf</b> node with a single block.
	 */
	public BNode(int t, Block firstBlock) {
		this(t, true, 1);
		this.blocksList.add(firstBlock);
	}

	public BNode(int t, boolean isLeaf, int numOfBlocks) {
		this.t = t;
		this.isLeaf = isLeaf;
		this.numOfBlocks = numOfBlocks;
		this.blocksList = new ArrayList<Block>();
		this.childrenList = new ArrayList<BNode>();
	}

	// For testing purposes.
	public BNode(int t, int numOfBlocks, boolean isLeaf,
			ArrayList<Block> blocksList, ArrayList<BNode> childrenList) {
		this.t = t;
		this.numOfBlocks = numOfBlocks;
		this.isLeaf = isLeaf;
		this.blocksList = blocksList;
		this.childrenList = childrenList;
	}

	@Override
	public int getT() {
		return t;
	}

	@Override
	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public ArrayList<Block> getBlocksList() {
		return blocksList;
	}

	@Override
	public ArrayList<BNode> getChildrenList() {
		return childrenList;
	}

	@Override
	public boolean isFull() {
		return numOfBlocks == 2 * t - 1;
	}

	@Override
	public boolean isMinSize() {
		return numOfBlocks == t - 1;
	}
	
	@Override
	public boolean isEmpty() {
		return numOfBlocks == 0;
	}
	
	@Override
	public int getBlockKeyAt(int indx) {
		return blocksList.get(indx).getKey();
	}
	
	@Override
	public Block getBlockAt(int indx) {
		return blocksList.get(indx);
	}

	@Override
	public BNode getChildAt(int indx) {
		return childrenList.get(indx);
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blocksList == null) ? 0 : blocksList.hashCode());
		result = prime * result
				+ ((childrenList == null) ? 0 : childrenList.hashCode());
		result = prime * result + (isLeaf ? 1231 : 1237);
		result = prime * result + numOfBlocks;
		result = prime * result + t;
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BNode other = (BNode) obj;
		if (blocksList == null) {
			if (other.blocksList != null)
				return false;
		} else if (!blocksList.equals(other.blocksList))
			return false;
		if (childrenList == null) {
			if (other.childrenList != null)
				return false;
		} else if (!childrenList.equals(other.childrenList))
			return false;
		if (isLeaf != other.isLeaf)
			return false;
		if (numOfBlocks != other.numOfBlocks)
			return false;
		if (t != other.t)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "BNode [t=" + t + ", numOfBlocks=" + numOfBlocks + ", isLeaf="
				+ isLeaf + ", blocksList=" + blocksList + ", childrenList="
				+ childrenList + "]";
	}

	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	
	
	
	@Override
	public Block search(int key) {
		int i = 0;
		while (i < numOfBlocks && blocksList.get(i).getKey() < key) {
			i ++;
		}
		if (i < blocksList.size() && blocksList.get(i).getKey() == key)
			return blocksList.get(i);
		if (isLeaf)
			return null;
		return childrenList.get(i).search(key);
	}
	
	@Override
	public void insertNonFull(Block d) {
		int i = numOfBlocks-1;
		if (isLeaf) { // Insert to a leaf
			while (i >= 0 && d.getKey() < blocksList.get(i).getKey()) {
				i--;
			}
			blocksList.add(i+1, d);
			numOfBlocks++;	
		} else { // Insert to an intern node
			while (i >= 0 && d.getKey() < blocksList.get(i).getKey()) {
				i--;
			}
			i++;
			if (childrenList.get(i).numOfBlocks == 2*t-1) {
				splitChild(i);
				if (d.getKey() > blocksList.get(i).getKey())
					i++;
			}
			childrenList.get(i).insertNonFull(d);	
		}
	}
	
	public void splitChild(int childIndex) {
		BNode y = childrenList.get(childIndex);
		BNode z = new BNode(t, y.isLeaf, t-1);
		
		// copy the [t,2t-2] blocks to new node called z
		z.blocksList = new ArrayList<Block> (y.blocksList.subList(t, y.blocksList.size()));
		y.blocksList = new ArrayList<Block> (y.blocksList.subList(0, t));
		y.numOfBlocks = y.numOfBlocks - (t-1);
		
		if (!y.isLeaf) {
			z.childrenList = new ArrayList<BNode> (y.childrenList.subList(t, y.childrenList.size())); // copy the children to the new node
			y.childrenList = new ArrayList<BNode> (y.childrenList.subList(0, t));
			}
		childrenList.add(childIndex+1, z);
		blocksList.add(childIndex, y.blocksList.get(t-1));
		numOfBlocks++;
		y.blocksList.remove(t-1);
		y.numOfBlocks--;		

	}
	
	@Override
	public MerkleBNode createHashNode() {
		if (isLeaf) {
			ArrayList<byte[]> hash = new ArrayList<byte[]>();
			for (int i = 0; i < blocksList.size(); i ++) {
				hash.add(blocksList.get(i).getData());
			}
			byte [] hashValue = HashUtils.sha1Hash(hash);
			return (new MerkleBNode(hashValue));
		} else {
			ArrayList<byte[]> hash = new ArrayList<byte[]>();
			ArrayList<MerkleBNode> merkleChildren = new ArrayList<MerkleBNode>();
			for (int i = 0; i < childrenList.size(); i ++) {
				merkleChildren.add(childrenList.get(i).createHashNode());
			}
			for (int i = 0; i < numOfBlocks ; i ++) {
				hash.add(merkleChildren.get(i).getHashValue());
				hash.add(blocksList.get(i).getData());
			}
			hash.add(merkleChildren.get(numOfBlocks).getHashValue());
			byte [] hashValue = HashUtils.sha1Hash(hash);
			return (new MerkleBNode(hashValue, merkleChildren));
		}
	}
	
	public void print(int spaces) {
		String spacing = "";
		for(int i=0; i<spaces; i++) {
			spacing = spacing.concat("-");
		}
		spacing = spacing.concat(" ");
		
		System.out.println(spacing + "Has " + numOfBlocks + " Blocks, " + childrenList.size() + " Children.");
		System.out.print(spacing + "Blocks: {");
		if(numOfBlocks == 0) System.out.println("}");
		for(int i=0; i<numOfBlocks; i++) {
			System.out.print(blocksList.get(i).getKey());
			if(i<=numOfBlocks-2) System.out.print(", ");
			else System.out.println("}");
		}
		System.out.println(spacing + "Children:");
		for(int i=0; i<childrenList.size(); i++) {
			System.out.print(spacing);
			childrenList.get(i).print(spaces + 1);
		}
	}

	private boolean childHasNonMinimalLeftSibling(int childIndx) {
		if (childrenList.get(childIndx-1) != null)
			return (childrenList.get(childIndx-1).numOfBlocks == t-1);
		else return false;
	}
	
	
	private boolean childHasNonMinimalRightSibling(int childIndx) {
		if (childrenList.get(childIndx+1) != null)
			return (childrenList.get(childIndx+1).numOfBlocks == t-1);
		else return false;
	}

	@Override
	public void delete(int key) {
		if(isLeaf)
		{
			deleteFromLeaf(key);
			return;
		}
		int index = getIndexOfKey(key);
		if(!childrenList.get(index).isMinSize())
		{
			Block predecessor = childrenList.get(index).findPredecessor();
			blocksList.set(index, predecessor);
			int newKey = predecessor.getKey();
			BNode newNode = childrenList.get(index).findNode(newKey);
			newNode.delete(newKey);
		}
		else
		{
			if(!childrenList.get(index+1).isMinSize())
			{
				Block successor = childrenList.get(index+1).findSuccessor();
				blocksList.set(index, successor);
				int newKey = successor.getKey();
				BNode newNode = childrenList.get(index+1).findNode(newKey);
				newNode.delete(newKey);
			}
			else
			{
				rightMerge(index);
				childrenList.get(index).delete(key);
			}
		}
	}
	
	private void deleteFromLeaf(int key)
	{
		int i=0;
		while(blocksList.get(i).getKey() < key)
		{
			i++;
		}
		blocksList.remove(i);
		numOfBlocks--;
	}
	private Block findSuccessor()
	{
		if(!isLeaf)
			return childrenList.get(0).findSuccessor();
		else
			return blocksList.get(0);
	}
	private Block findPredecessor()
	{
		if(!isLeaf)
			return childrenList.get(childrenList.size()-1).findPredecessor();
		else
			return blocksList.get(blocksList.size()-1);
	}
	private int getIndexOfKey(int key)
	{
		int i = 0;
		while(blocksList.get(i).getKey() < key)
			i++;
		return i;
	}
	public BNode findNode(int key)
	{
		int i=0;
		while(i != blocksList.size() && blocksList.get(i).getKey() < key)
		{
			i++;
		}
		if(i==blocksList.size())
			if(isLeaf)
				return null;
			else
			{
				if(childrenList.get(childrenList.size()-1).isMinSize())
					shiftOrMerge(childrenList.size()-1);
				return childrenList.get(childrenList.size()-1).findNode(key);
			}
				
		if(blocksList.get(i).getKey() == key)
			return this;
		if(isLeaf)
			return null;
		if(childrenList.get(i).isMinSize())
			shiftOrMerge(i);
		return childrenList.get(i).findNode(key);
	}
	private boolean leftSiblbing(int childIndex)
	{
		if(childIndex == 0)
			return false;
		if(childrenList.get(childIndex-1).isMinSize())
			return false;
		return true;			
	}
	private boolean rightSiblbing(int childIndex)
	{
		if(childIndex == childrenList.size()-1)
			return false;
		if(childrenList.get(childIndex+1).isMinSize())
			return false;
		return true;			
	}
	private boolean isPosRightMerge(int childIndex)
	{
		if(childIndex == childrenList.size()-1)
			return false;
		return true;
	}
	private boolean isPosLeftMerge(int childIndex)
	{
		if(childIndex == 0)
			return false;
		return true;
	}
	private void shiftLeft(int childIndex)
	{
		ArrayList<Block> leftBlocks = childrenList.get(childIndex-1).blocksList;
		ArrayList<Block> childBlocks = childrenList.get(childIndex).blocksList;
		ArrayList<BNode> leftChildren = childrenList.get(childIndex-1).childrenList;
		ArrayList<BNode> childChildren = childrenList.get(childIndex).childrenList;
		childBlocks.add(0, blocksList.get(childIndex-1));
		if(!childrenList.get(childIndex-1).isLeaf)
		{
			childChildren.add(0, leftChildren.get(leftChildren.size()-1));
			leftChildren.remove(leftChildren.size()-1);	
		}
		blocksList.set(childIndex-1, leftBlocks.get(leftBlocks.size()-1));
		leftBlocks.remove(leftBlocks.size()-1);
		childrenList.get(childIndex-1).numOfBlocks--;
		childrenList.get(childIndex).numOfBlocks++;		
	}
	private void shiftRight(int childIndex)
	{
		ArrayList<Block> rightBlocks = childrenList.get(childIndex+1).blocksList;
		ArrayList<Block> childBlocks = childrenList.get(childIndex).blocksList;
		ArrayList<BNode> rightChildren = childrenList.get(childIndex+1).childrenList;
		ArrayList<BNode> childChildren = childrenList.get(childIndex).childrenList;
		childBlocks.add(blocksList.get(childIndex));
		if(!childrenList.get(childIndex+1).isLeaf)
		{
			childChildren.add(rightChildren.get(0));
			rightChildren.remove(0);
		}
		blocksList.set(childIndex, rightBlocks.get(0));
		rightBlocks.remove(0);
		childrenList.get(childIndex+1).numOfBlocks--;
		childrenList.get(childIndex).numOfBlocks++;
		
	}
	private void leftMerge(int childIndex)
	{
		ArrayList<Block> leftBlocks = childrenList.get(childIndex-1).blocksList;
		ArrayList<Block> childBlocks = childrenList.get(childIndex).blocksList;
		ArrayList<BNode> leftChildren = childrenList.get(childIndex-1).childrenList;
		ArrayList<BNode> childChildren = childrenList.get(childIndex).childrenList;
		leftBlocks.add(blocksList.get(childIndex-1));
		for(int i=0; i<childBlocks.size(); i++)
			leftBlocks.add(childBlocks.get(i));
		for(int i=0; i<childChildren.size(); i++)
			leftChildren.add(childChildren.get(i));
		blocksList.remove(childIndex-1);
		childrenList.remove(childIndex);		
		childrenList.get(childIndex-1).numOfBlocks = 2*t - 1;
		numOfBlocks--;
	}
	public BNode rightMerge(int childIndex)
	{
		ArrayList<Block> rightBlocks = childrenList.get(childIndex+1).blocksList;
		ArrayList<Block> childBlocks = childrenList.get(childIndex).blocksList;
		ArrayList<BNode> rightChildren = childrenList.get(childIndex+1).childrenList;
		ArrayList<BNode> childChildren = childrenList.get(childIndex).childrenList;
		childBlocks.add(blocksList.get(childIndex));
		for(int i=0; i<rightBlocks.size(); i++)
			childBlocks.add(rightBlocks.get(i));
		for(int i=0; i<rightChildren.size(); i++)
			childChildren.add(rightChildren.get(i));
		blocksList.remove(childIndex);
		childrenList.remove(childIndex+1);
		childrenList.get(childIndex).numOfBlocks = 2*t - 1;
		numOfBlocks--;	
		return childrenList.get(childIndex);
	}
	private void shiftOrMerge(int childIndex)
	{
		if(leftSiblbing(childIndex))
			shiftLeft(childIndex);
		else
			if(rightSiblbing(childIndex))
				shiftRight(childIndex);
			else
				if(isPosRightMerge(childIndex))
					rightMerge(childIndex);
				else
					if(isPosLeftMerge(childIndex))
						leftMerge(childIndex);
					
	}


}
