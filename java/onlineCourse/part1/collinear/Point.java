/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    // compare points by slope
   // YOUR DEFINITION HERE
    //public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;                      // x coordinate
    private final int y;                      // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
       double slopeVal;
       if (y == that.y && x == that.x)
       {
          slopeVal = Double.NEGATIVE_INFINITY;
       }
       else if (x == that.x)
       {
          slopeVal = Double.POSITIVE_INFINITY;
       }
       else if (y == that.y)
       {
          slopeVal = +0.0;
       }
       else
       {
          slopeVal = (double) (that.y -y) / (double) (that.x -x);
       }
       
       return slopeVal;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
       int result;
       if (y != that.y)
          result = y - that.y;
       else if (y == that.y && x != that.x)
       {
          result = x - that.x;
       }
       else
       {
          result = 0;
       }

       return result;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new SlopeOrder();
    }
    private class SlopeOrder implements Comparator<Point>
    {

      @Override
      public int compare(Point o1, Point o2) {
         // TODO Auto-generated method stub
 
         double slope1 = slopeTo(o1);
         double slope2 = slopeTo(o2);

         return Double.compare(slope1, slope2);
      }
       
    }
    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}