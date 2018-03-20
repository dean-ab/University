import java.util.Comparator;
public class yComparator implements Comparator<Point> {
	public int compare(Point point1, Point point2)
    {
      if (point1.getY() < point2.getY())
        return -1;
      if (point1.getY() > point2.getY())
        return 1;
      return 0;
    }

}
