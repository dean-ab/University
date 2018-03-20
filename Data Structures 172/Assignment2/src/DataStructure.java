import java.util.Comparator;
import java.util.Arrays;

public class DataStructure implements DT {
	protected LinkedList x;
	protected LinkedList y; 

	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public DataStructure()
	{
		this.x = new LinkedList();
		this.y = new LinkedList();
	}

	// Adds new Point to the data structre
	@Override
	public void addPoint(Point point) {
		Container toInsertX = new Container(point);
		Container toInsertY = new Container(point);
		toInsertX.setOpp(toInsertY);
		toInsertY.setOpp(toInsertX);
		Comparator compX = new xComparator();
		Comparator compY = new yComparator();
		x.insert(toInsertX, compX);
		y.insert(toInsertY, compY);
		
	}
	
	// Returns an array of points that are at [min, max] by the given axis
	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		int counter = 0;
		if (axis) { // X
				Container scanner = x.getFirst();
				while (scanner != null) { // counts how many points are at [min,max] by their X value
					if (scanner.getData().getX() >= min & scanner.getData().getX() <= max)
						counter++;
					scanner = scanner.getNext();
				}
				scanner = x.getFirst();
				Point [] array = new Point [counter];
				int i = 0;
				while (scanner != null & i < counter) { // get those points inside an array
					if (scanner.getData().getX() >= min & scanner.getData().getX() <= max) {
						array[i] = scanner.getData();
						i++;
					}
					scanner = scanner.getNext();
				}
				return array;	
			
		}
		if (!axis) { // Y

				Container scanner = y.getFirst();
				while (scanner != null) { // counts how many points are at [min,max] by their Y value
					if (scanner.getData().getY() >= min & scanner.getData().getY() <= max)
						counter++;
					scanner = scanner.getNext();
				}
				scanner = y.getFirst();
				Point [] array = new Point [counter];
				int i = 0;
				while (scanner != null & i < counter) { // get those points inside an array
					if (scanner.getData().getY() >= min & scanner.getData().getY() <= max) {
						array[i] = scanner.getData();
						i++;
					}
					scanner = scanner.getNext();
				}
				return array;	
		}
		return null; // returns null if there are no points in range
	}	
	
	// Returns an array of points that are at [min, max] by the given opposite axis
	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		int counter = 0;
		if (axis) { // X
				Container scanner = y.getFirst();
				while (scanner != null) { // counts how many points are at [min,max] by their X value on Y LIST!!
					if (scanner.getData().getX() >= min & scanner.getData().getX() <= max)
						counter++;
					scanner = scanner.getNext();
				}
				scanner = y.getFirst();
				Point [] array = new Point [counter];
				int i = 0;
				while (scanner != null & i < counter) { // get those points inside an array
					if (scanner.getData().getX() >= min & scanner.getData().getX() <= max) {
						array[i] = scanner.getData();
						i++;
					}
					scanner = scanner.getNext();
				}
				return array;	
			}
		if (!axis) { // Y
				Container scanner = x.getFirst();
				while (scanner != null) { // counts how many points are at [min,max] by their Y value on X LIST!!
					if (scanner.getData().getY() >= min & scanner.getData().getY() <= max)
						counter++;
					scanner = scanner.getNext();
				}
				scanner = x.getFirst();
				Point [] array = new Point [counter];
				int i = 0;
				while (scanner != null & i < counter) { // get those points inside an array
					if (scanner.getData().getY() >= min & scanner.getData().getY() <= max) {
						array[i] = scanner.getData();
						i++;
					}
					scanner = scanner.getNext();
				}
				return array;	
			}
	return null;
	}
	
	// Calculates the density of the points at the data structre
	@Override
	public double getDensity() {
		if (x.getSize() == 0) {
			return 0;
		}
		else {
			double xMax = (double)(x.getLast().getData().getX());
			double xMin = (double)(x.getFirst().getData().getX());
			double yMax = (double)(y.getLast().getData().getY());
			double yMin = (double)(y.getFirst().getData().getY());
			return ((double)(x.getSize()))/((xMax-xMin)*(yMax-yMin));
		}
	}
	
	// Delete all the points that are not at [min, max] by the given axis
	@Override
	public void narrowRange(int min, int max, Boolean axis) {

		if(axis) // X
		{
			Container minimum = new Container(new Point(min, 0));
			Container curr = x.getFirst();
			while(curr!=null && curr.compareX(minimum)<0) // compare the points with the min value
			{
				Container tmp = curr.getNext();
				x.delete(curr);
				y.delete(curr.getOpp());
				curr = tmp;
			}
			Container maximum = new Container(new Point(max, 0)); 
			curr = x.getLast();
			while(curr!=null && curr.compareX(maximum)>0) // compare the points with the max value
			{
				Container tmp = curr.getPrev();
				x.delete(curr);
				y.delete(curr.getOpp());
				curr = tmp;
			}
		}
		else
		{
			Container minimum = new Container(new Point(0, min));
			Container curr = y.getFirst();
			while(curr!=null && curr.compareY(minimum)<0) // compare the points with the min value
			{
				Container tmp = curr.getNext();
				x.delete(curr.getOpp());
				y.delete(curr);
				curr = tmp;
			}
			Container maximum = new Container(new Point(0, max)); 
			curr = y.getLast();
			while(curr!=null && curr.compareY(maximum)>0) // compare the points with the max value
			{
				Container tmp = curr.getPrev();
				x.delete(curr.getOpp());
				y.delete(curr);
				curr = tmp;
			}
		}
		
	}
	
	// Return the largest axis 
	@Override
	public Boolean getLargestAxis() {
		return (x.getLast().getData().getX() - x.getFirst().getData().getX() > y.getLast().getData().getY() - y.getFirst().getData().getY());
	}
	
	// Return a contaianer with the median point of the given axis
	@Override
	public Container getMedian(Boolean axis) {
		if (axis) { // X
			Container scanner = x.getFirst();
			for (int i = 0; i < x.getSize()/2; i++)
				scanner = scanner.getNext();
			return scanner;
		}
		// Y
		Container scanner = y.getFirst();
		for (int i = 0; i < y.getSize()/2; i++)
			scanner = scanner.getNext();
		return scanner;
	}
	
	// Returns an array of the two closest points inside the given strip 
	public Point[] nearestPairInStrip(Container container, double width,
			Boolean axis) {
			if(axis) // X 
			{
				Container scanner = container;
				int counter1=0;
				while(scanner!=null && scanner.getData().getX()<=(width/2)+container.getData().getX())
				{ // count how much points are at [median, median + width/2]
					counter1++;
					scanner = scanner.getNext();
				}
				scanner = container.getPrev();
				int counter2=0;
				while(scanner!=null && scanner.getData().getX()>= container.getData().getX()-(width/2))
				{ // count how much points are at [median, median - width/2]
					counter2++;
					scanner = scanner.getPrev();
				} 
				Point[] strip = new Point[counter1+counter2];
				scanner = container;
				int i = counter2;
				while(scanner!=null && scanner.getData().getX()<=(width/2)+container.getData().getX())
				{// get the points into an array
					strip[i] = scanner.getData();
					i++;
					scanner = scanner.getNext();
				}
				scanner = container.getPrev();
				i = counter2-1;
				while(scanner!=null && scanner.getData().getX()>= container.getData().getX()-(width/2))
				{// get the points into an array
					strip[i] = scanner.getData();
					i--;
					scanner = scanner.getPrev();
				} 
				if(strip.length*(Math.log(strip.length)/Math.log(2)) > x.getSize())
				{ // checks if |B|log|B| > n
					Point [] stripOpp = getPointsInRangeOppAxis(strip[0].getX(), strip[strip.length-1].getX(), false);
				} 
				Comparator comp = new yComparator();
				Point [] stripOpp = strip;
				Arrays.sort(stripOpp, comp);	
				if (stripOpp.length == 2)
					return stripOpp;
				if (stripOpp.length == 0)
					return null;
				double minDist = distance(stripOpp[0], stripOpp[stripOpp.length-1]);
				Point [] pair = new Point [2];
				boolean inRange = true; // the array must has more than two slots
				int k = 1;
				for (int j = 0; j < stripOpp.length-1 ; j ++) {
					inRange = k + j < stripOpp.length;
					while (k < 8 & inRange) {
						if (distance(stripOpp[j], stripOpp[j+k]) < minDist) {
							minDist = distance(stripOpp[j], stripOpp[j+k]);
							pair[0] = stripOpp[j];
							pair [1] = stripOpp[j+k];
						}
						k++;
						inRange = k + j < stripOpp.length;
					}
					k = 1;
					
				}
				return pair;
			} // end of X
			Container scanner = container;
			int counter1=0;
			while(scanner!=null && scanner.getData().getY()<=(width/2)+container.getData().getY())
			{
				counter1++;
				scanner = scanner.getNext();
			}
			scanner = container.getPrev();
			int counter2=0;
			while(scanner!=null && scanner.getData().getY()>= container.getData().getY()-(width/2))
			{
				counter2++;
				scanner = scanner.getPrev();
			} 
			Point[] strip = new Point[counter1+counter2];
			scanner = container;
			int i = counter2;
			while(scanner!=null && scanner.getData().getY()<=(width/2)+container.getData().getY())
			{
				strip[i] = scanner.getData();
				i++;
				scanner = scanner.getNext();
			}
			scanner = container.getPrev();
			i = counter2-1;
			while(scanner!=null && scanner.getData().getY()>= container.getData().getY()-(width/2))
			{
				strip[i] = scanner.getData();
				i--;
				scanner = scanner.getPrev();
			}
			if(strip.length*(Math.log(strip.length)/Math.log(2)) > x.getSize())
			{ // checks if |B|log|B| > n
				Point [] stripOpp = getPointsInRangeOppAxis(strip[0].getY(), strip[strip.length-1].getY(), true);
			} 
			Comparator comp = new xComparator();
			Point [] stripOpp = strip;
			Arrays.sort(stripOpp, comp);
			if (stripOpp.length == 2)
				return stripOpp;
			if (stripOpp.length == 0)
				return null;
			double minDist = distance(stripOpp[0], stripOpp[stripOpp.length-1]);
			Point [] pair = new Point [2];
			boolean inRange = true; // the array must has more than two slots so it must be in range
			int k = 1;
			for (int j = 0; j < stripOpp.length-1 ; j ++) {
				inRange = k + j < stripOpp.length;
				while (k < 8 & inRange) {
					if (distance(stripOpp[j], stripOpp[j+k]) < minDist) {
						minDist = distance(stripOpp[j], stripOpp[j+k]);
						pair[0] = stripOpp[j];
						pair [1] = stripOpp[j+k];
					}
					k++;
					inRange = k + j < stripOpp.length;
				}
				k = 1;
				
			}
			return pair;
	}
	
	// Returns an array of the two closest points inside the data structure
	@Override
	public Point[] nearestPair() {
		
		if(x.getSize()<2) // there are no points 
			return null;
		if(x.getSize()==2) // there are only two points 
		{
			Point[] pair = {x.getFirst().getData(), x.getLast().getData()};
			return pair;
		}
		boolean axis = this.getLargestAxis();
		Container cont = this.getMedian(axis);
		Point[] stripX = new Point[x.getSize()];
		Point[] stripY = new Point[y.getSize()];
		Container scanner;
		scanner = x.getFirst();
		for(int i=0; i<stripX.length; i++) // create an array of points sorted by their X coordinate
		{
			stripX[i] = scanner.getData();
			scanner = scanner.getNext();
		}
		scanner = y.getFirst(); 
		for(int i=0; i<stripY.length; i++) // create an array of points sorted by their Y coordinates
		{
			stripY[i] = scanner.getData();
			scanner = scanner.getNext();
		}
		if(axis)
			return nearestX(stripX, stripY); 
		return nearestY(stripX, stripY);
	}

// <--------------------------- Private Methods ---------------------------> //	
	
// Divides the points by their Y coordinate, check if they are inside of the strip or not
private Point[] yDivide(Point[] xDivide, Point[] stripY)
{
	Point[] output = new Point[xDivide.length];
	int min = xDivide[0].getX();
	int max = xDivide[xDivide.length-1].getX();
	int j=0;
	for(int i=0;i<stripY.length; i++)
	{
		if(stripY[i].getX()>=min && stripY[i].getX()<=max)
		{
			output[j] = stripY[i];
			j++;
		}
	}
	return output;
}

//Divides the points by their X coordinate, check if they are inside of the strip or not
private Point[] xDivide(Point[] yDivide, Point[] stripX)
{
	Point[] output = new Point[yDivide.length];
	int min = yDivide[0].getY();
	int max = yDivide[yDivide.length-1].getY();
	int j=0;
	for(int i=0;i<stripX.length; i++)
	{
		if(stripX[i].getY()>=min && stripX[i].getY()<=max)
		{
			output[j] = stripX[i];
			j++;
		}
	}
	return output;
}

// Returns a sub array start at array[from] and ends at array[to]
private Point[] subArray(Point[] arr, int from, int to)
{
	Point[] output = new Point[to-from+1];
	for(int i=0; i<output.length; i++)
	{
		output[i] = arr[from+i];
	}
	return output;
}

// Base case: check which pair of points from 3 given points has the min distance
private Point[] check3(Point[] arr)
{
	int numPoints = arr.length;
    if (numPoints < 2)
      return null;
    Point[] pair = {arr[0], arr[1]};
    if (numPoints > 2)
    {
      for (int i = 0; i < numPoints - 1; i++)
      {
        Point point1 = arr[i];
        for (int j = i + 1; j < numPoints; j++)
        {
          Point point2 = arr[j];
          double distance = distance(point1, point2);
          if (distance < distance(pair[0], pair[1]))
          {
        	  pair[0]=point1; pair[1]=point2;
          }
        }
      }
    }
    return pair;
}

// Calculates the geometric distance by the form of two given points
private double distance(Point p1, Point p2)
{
	double xdist = p2.getX() - p1.getX();
    double ydist = p2.getY() - p1.getY();
    xdist = Math.abs(xdist);
    ydist = Math.abs(ydist);
    return Math.sqrt(xdist*xdist + ydist*ydist);
}

// Returns the closest points if the largest axis is X
public Point[] nearestX(Point[] stripX, Point[] stripY)
{		
	if(stripX.length<=3)
		return check3(stripX); // Base case - only 3 points
	int midIndex = stripX.length/2;
	Point[] leftStripX = subArray(stripX, 0, midIndex-1);
	Point[] rightStripX = subArray(stripX, midIndex, stripX.length-1);
	Point[] leftStripY = yDivide(leftStripX, stripY);
	Point[] rightStripY = yDivide(rightStripX, stripY);
	Point[] pair = nearestX(leftStripX, leftStripY); // recursive call of two smaller arrays (divided by their median point)
	Point[] pair2 = nearestX(rightStripX, rightStripY); // recursive call of two smaller arrays (divided by their median point)
	if(distance(pair[0], pair[1]) > distance(pair2[0], pair2[1]))
	{
		pair[0] = pair2[0];
		pair[1] = pair2[1];
	}
	double closet = distance(pair[0], pair[1]); // our current min distance between two points
	
	int xMid = stripX[midIndex].getX();
	int counter = 0;
	for(int i=0 ; i<stripX.length; i++) // checks which points get inside a rectangle of width = 2*closet
	{
		if(Math.abs(stripX[i].getX()-xMid) < closet)
			counter++;
	}
	Point[] xRange = new Point[counter];
	int j = 0;
	for(int i=0 ; i<stripX.length; i++) // get those points into an array
	{
		if(Math.abs(stripX[i].getX()-xMid) < closet)
		{
			xRange[j] = stripX[i];
			j++;
		}
	}
	xRange = yDivide(xRange, stripY); 
	for(int i=0; i<xRange.length; i++) // check those points by their Y coordinate
	{
		Point p1 = xRange[i];
		for(int k=i+1; k<xRange.length; k++) // should be 7 iterations by the geometric proof
		{
			Point p2 = xRange[k];
			if(p2.getY() - p1.getY() >= closet) // checks if the distance between the Y coordinates < min distance
				break;
			double dist = distance(p1, p2);
			if(dist < closet) // update the two closest pair (which has the min distance now)
			{
				closet = dist;
				pair[0] = p1;
				pair[1] = p2;
			}
		}
	}
	return pair;
}

// Returns the closest points if the largest axis is Y
public Point[] nearestY(Point[] stripX, Point[] stripY)
{		
	if(stripY.length<=3)
		return check3(stripY);
	int midIndex = stripY.length/2;
	Point[] leftStripY = subArray(stripY, 0, midIndex-1);
	Point[] rightStripY = subArray(stripY, midIndex, stripY.length-1);
	Point[] leftStripX = xDivide(leftStripY, stripX);
	Point[] rightStripX = xDivide(rightStripY, stripX);
	Point[] pair = nearestY(leftStripX, leftStripY);
	Point[] pair2 = nearestY(rightStripX, rightStripY);
	if(distance(pair[0], pair[1]) > distance(pair2[0], pair2[1]))
	{
		pair[0] = pair2[0];
		pair[1] = pair2[1];
	}
	double closet = distance(pair[0], pair[1]);
	
	int yMid = stripY[midIndex].getY();
	int counter = 0;
	for(int i=0 ; i<stripY.length; i++)
	{
		if(Math.abs(stripY[i].getY()-yMid) < closet)
			counter++;
	}
	Point[] yRange = new Point[counter];
	int j = 0;
	for(int i=0 ; i<stripY.length; i++)
	{
		if(Math.abs(stripY[i].getY()-yMid) < closet)
		{
			yRange[j] = stripY[i];
			j++;
		}
	}
	yRange = xDivide(yRange ,stripX);
	for(int i=0; i<yRange.length; i++)
	{
		Point p1 = yRange[i];
		for(int k=i+1; k<yRange.length; k++)
		{
			Point p2 = yRange[k];
			if(p2.getX() - p1.getX() >= closet)
				break;
			double dist = distance(p1, p2);
			if(dist < closet)
			{
				closet = dist;
				pair[0] = p1;
				pair[1] = p2;
			}
		}
	}
	return pair;
	}
}