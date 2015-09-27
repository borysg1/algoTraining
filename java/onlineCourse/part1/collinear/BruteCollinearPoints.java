import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


public class BruteCollinearPoints 
{
   private static final int COMPARE_POINT_CNT = 4;
   private LineSegment [] lineSegments;

   private class Pair
   {
      private Point pStart;
      private Point pStop;

      public Pair(Point start, Point stop)
      {
         pStart = start;
         pStop = stop;
      }
      
      public Point getStart()
      {
         return pStart;
      }
      public Point getStop()
      {
         return pStop;
      }
   }
   
   private HashSet<Pair> pointPairSet;

   /*Finds all line segments containing 4 points*/
   public BruteCollinearPoints(Point[] points)
   {
      pointPairSet = new HashSet<Pair>();
      Point [] pntArray = new Point[COMPARE_POINT_CNT];
      double slope1, slope2, slope3;

      if (null == points)
         throw new java.lang.NullPointerException();

      /*Input validation - corner case - check last 4 points*/
      int checkSize;
      if (points.length > 3)
         checkSize = 3;
      else
         checkSize = points.length;

      Point [] pntArray1 = new Point[checkSize];

      for (int checkCntr = 0; checkCntr < checkSize; checkCntr++)
         pntArray1[checkCntr] = points[points.length - 1 - checkCntr];

      Arrays.sort(pntArray1);

      for (int checkCntr1 = 0; checkCntr1 < checkSize-1; checkCntr1++)
      {
         if (0 == pntArray1[checkCntr1].compareTo(pntArray1[checkCntr1 + 1]))
            throw new java.lang.IllegalArgumentException();
      }

      for (int i = 0; i < points.length; i++)
      {
         for (int j = i+1; j < points.length; j++)
         {
            for (int k = j+1; k < points.length; k++)
            {
               for (int l = k+1; l < points.length; l++)
               {
                  slope1 = points[i].slopeTo(points[j]);
                  slope2 = points[i].slopeTo(points[k]);
                  slope3 = points[i].slopeTo(points[l]);
                  if (Double.NEGATIVE_INFINITY == slope1 || 
                      Double.NEGATIVE_INFINITY == slope2 || 
                      Double.NEGATIVE_INFINITY == slope3)
                         throw new java.lang.IllegalArgumentException();
                  if ((slope1 == slope2) && (slope1 == slope3))
                  {
                     pntArray[0] = points[i];
                     pntArray[1] = points[j];
                     pntArray[2] = points[k];
                     pntArray[3] = points[l];
                     Arrays.sort(pntArray);
                     pointPairSet.add(new Pair(pntArray[0], pntArray[3]));
                  }
               }
            }
         }
      }
      lineSegments = new LineSegment[pointPairSet.size()];

      Iterator<Pair> it = pointPairSet.iterator();
      int segmentsCnt = 0;

      while (it.hasNext())
      {
         Pair currentPair = it.next();
         lineSegments[segmentsCnt++] = new LineSegment(currentPair.getStart(), currentPair.getStop());
      }
      
      pointPairSet.clear();
      pointPairSet  = null;
      pntArray = null;
   }

   /*The number of line segments*/
   public int numberOfSegments()
   {
      return lineSegments.length;
   }

   /*the line segments*/
   public LineSegment[] segments()
   {
      return (LineSegment[]) lineSegments.clone();
   }

   public static void main(String [] args)
   {
      
      if (1 != args.length)
      {
         throw new java.util.NoSuchElementException();
      }
      
      System.out.println(args[0]);
      In inputFile = new In(args[0]);
      
      String pointCntStr = inputFile.readLine();
      int pointCnt = Integer.parseInt(pointCntStr);
      Point [] pointArray = new Point[pointCnt];
      String pointStr;
      
      int currPoint = 0;
      while (0 < pointCnt)
      {
         pointStr = inputFile.readLine();
         if (null == pointStr)
            throw new java.lang.NullPointerException();
         
         pointStr = pointStr.trim();
         
         if (0 != pointStr.length())
         {
            String xCoord = pointStr.substring(0, pointStr.indexOf(" ")).trim();
            String yCoord = pointStr.substring(pointStr.indexOf(" ")).trim();
            pointArray[currPoint] = 
                     new Point(Integer.parseInt(xCoord), Integer.parseInt(yCoord));
            currPoint++;
            pointCnt--;
         }
      }

      /*draw the points*/
      StdDraw.show(0);
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      for (Point p : pointArray) {
          p.draw();
      }
      StdDraw.show();

      /*print and draw the line segments*/
      BruteCollinearPoints collinear = new BruteCollinearPoints(pointArray);
      for (LineSegment segment : collinear.segments()) {
          StdOut.println(segment);
          segment.draw();
      }
   }
}
