import java.util.Comparator;
public class xComparator implements Comparator<Point> {
	public int compare(Point point1, Point point2)
    {
      if (point1.getX() < point2.getX())
        return -1;
      if (point1.getX() > point2.getX())
        return 1;
      return 0;
    }
}
