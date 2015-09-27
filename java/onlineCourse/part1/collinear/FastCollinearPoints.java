import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints
{
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

    /*finds all line segments containing 4 or more points*/
   public FastCollinearPoints(Point[] points)
   {
      if (null == points)
         throw new java.lang.NullPointerException();

      pointPairSet = new HashSet<Pair>();
      Point [] pointArraySorted = new Point[points.length];
      System.arraycopy(points, 0, pointArraySorted, 0, points.length);
      /*Input validation - corner case - check last 4 points*/
      int checkSize;
      if (points.length > 3) checkSize = 3;
      else checkSize = points.length;
      Point [] pntArray = new Point[checkSize];
      for (int checkCntr = 0; checkCntr < checkSize; checkCntr++)
         pntArray[checkCntr] = points[points.length - 1 - checkCntr];
      Arrays.sort(pntArray);
      for (int checkCntr1 = 0; checkCntr1 < checkSize - 1; checkCntr1++)
      {
         if (0 == pntArray[checkCntr1].compareTo(pntArray[checkCntr1 + 1]))
            throw new java.lang.IllegalArgumentException();
      }

      for (int i = 0; i < points.length; i++)
      {
         Arrays.sort(pointArraySorted, points[i].slopeOrder());
         int idxRef = 0;
         int actualPointRelation;
         int refPointRelation = pointArraySorted[idxRef].compareTo(points[i]);
         double actualPointSlope = Double.NaN;
         double refPointSlope = Double.NaN;

         if (0 < refPointRelation)
         {
            refPointSlope = points[i].slopeTo(pointArraySorted[idxRef]);
         }
         else
         {
            refPointSlope = pointArraySorted[idxRef].slopeTo(points[i]);
         }

         double duplicatedLineSlope = Double.NaN;
         /*Ugly method for checking repeated points*/
         int theSamePoint = 0;
         for (int j = 0; j < pointArraySorted.length; j++)
         {
            actualPointRelation = pointArraySorted[j].compareTo(points[i]);

            if (0 < actualPointRelation)
            {
               actualPointSlope = points[i].slopeTo(pointArraySorted[j]);
               if ((refPointSlope == actualPointSlope
                     || Double.NEGATIVE_INFINITY == actualPointSlope)
                    && duplicatedLineSlope != actualPointSlope)
               {
                  if (Double.NEGATIVE_INFINITY == actualPointSlope)
                     theSamePoint++;
                  if (1 < theSamePoint)
                     throw new java.lang.IllegalArgumentException();
                  continue;
               }
               else
               {
                  if (((j - idxRef) > 2) 
                        && (duplicatedLineSlope != refPointSlope) 
                        && (duplicatedLineSlope != actualPointSlope))
                  {
                     /*>4 collinear point - print and draw them*/
                     Point [] linePointArray = 
                                 Arrays.copyOfRange(pointArraySorted, idxRef, j+1);
                     linePointArray[linePointArray.length-1] = points[i];
                     Arrays.sort(linePointArray);

                     pointPairSet.add(new Pair(linePointArray[0], linePointArray[linePointArray.length - 1]));
                     linePointArray = null;

                     idxRef = j;
                     refPointSlope = actualPointSlope;
                  }
                  else
                  {
                     idxRef = j;
                     refPointSlope = actualPointSlope;
                  }
               }
            }
            else
            {
               actualPointSlope = pointArraySorted[j].slopeTo(points[i]);
               if (Double.NEGATIVE_INFINITY == actualPointSlope)
               {
                   if (Double.NEGATIVE_INFINITY == actualPointSlope)
                      theSamePoint++;
                   if (1 < theSamePoint)
                      throw new java.lang.IllegalArgumentException();
                    continue;
               }

               if (((j - idxRef) > 2) && (actualPointSlope != refPointSlope))
               {
                  Point [] linePointArray = 
                        Arrays.copyOfRange(pointArraySorted, idxRef, j+1);
                  linePointArray[linePointArray.length-1] = points[i];
                  Arrays.sort(linePointArray);

                  pointPairSet.add(new Pair(linePointArray[0], linePointArray[linePointArray.length - 1]));
                  linePointArray = null;
                  
                  idxRef = j;
                  refPointSlope = actualPointSlope;
               }
               else
               {
                  idxRef = j;
                  refPointSlope = actualPointSlope;
               }
               duplicatedLineSlope = actualPointSlope;
            }
         }

         /*Last run*/
         if ((pointArraySorted.length - idxRef > 2) 
               && (duplicatedLineSlope != actualPointSlope))
         {
            Point [] linePointArray = 
                  Arrays.copyOfRange(pointArraySorted, idxRef, 
                        pointArraySorted.length + 1);
            linePointArray[linePointArray.length-1] = points[i];
            Arrays.sort(linePointArray);

            pointPairSet.add(new Pair(linePointArray[0], linePointArray[linePointArray.length - 1]));
            linePointArray = null;
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
      pointPairSet = null;
      pointArraySorted = null;
   }

   /* the number of line segments*/
   public int numberOfSegments()
   {
      return lineSegments.length;
   }

   /* the line segments*/
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

      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      Arrays.sort(pointArray);

      /*draw the points*/
      StdDraw.show(0);
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      for (Point p : pointArray) {
          p.draw();
      }
      StdDraw.show();

      /*print and draw the line segments*/
      FastCollinearPoints collinear = new FastCollinearPoints(pointArray);
      for (LineSegment segment : collinear.segments()) {
          StdOut.println(segment);
          segment.draw();
      }

      pointArray = null;
   }
}