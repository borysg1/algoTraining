import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;


public class KdTree {

   private static final int LEFT_HALF = 0;
   private static final int RIGHT_HALF = 1;
   private static final int BOTTOM_HALF = 2;
   private static final int TOP_HALF = 3;

   private Node kdTree;
   private int kdTreeSize;

   // construct an empty set of points
   public KdTree() {
      kdTree = null;
      kdTreeSize = 0;
   }
   
   private void insertNewNode(Node parentNode, Point2D p, int level,
         int posDesignate) {
      Node newNode = new Node(p);
      if (0 == level % 2) {
         /* split by x coordinate */
         if (LEFT_HALF == posDesignate) {
            RectHV rect = new RectHV(parentNode.rect.xmin(),
                  parentNode.rect.ymin(), parentNode.p.x(),
                  parentNode.rect.ymax());
            newNode.setRect(rect);
            parentNode.lb = newNode;
         } else if (RIGHT_HALF == posDesignate) {
            RectHV rect = new RectHV(parentNode.p.x(), parentNode.rect.ymin(),
                  parentNode.rect.xmax(), parentNode.rect.ymax());
            newNode.setRect(rect);
            parentNode.rt = newNode;
         } else {
            System.out.println("Incorrect combination level: " + level
                  + " posDesignate " + posDesignate);
         }
      } else {
         /* split by y coordinate */
         /* split by x coordinate */
         if (BOTTOM_HALF == posDesignate) {
            RectHV rect = new RectHV(parentNode.rect.xmin(),
                  parentNode.rect.ymin(), parentNode.rect.xmax(),
                  parentNode.p.y());
            newNode.setRect(rect);
            parentNode.lb = newNode;
         } else if (TOP_HALF == posDesignate) {
            RectHV rect = new RectHV(parentNode.rect.xmin(), parentNode.p.y(),
                  parentNode.rect.xmax(), parentNode.rect.ymax());
            newNode.setRect(rect);
            parentNode.rt = newNode;
         } else {
            System.out.println("Incorrect combination level: " + level
                  + " posDesignate " + posDesignate);
         }
      }
      kdTreeSize++;
   }

   /* recursive draw */
   private int drawInternal(Node node, int entryLevel)
   {
      int tempEntryLevel = entryLevel;
      tempEntryLevel++;
      Node currentNode = node;

      currentNode.draw(tempEntryLevel);
      if (null != node.lb) {
         currentNode = node.lb;
         drawInternal(currentNode, tempEntryLevel);
      }

      if (null != node.rt) {
         currentNode = node.rt;
         drawInternal(currentNode, tempEntryLevel);
      }

      return 0;
   }

   private boolean interceptRect(RectHV a, RectHV b) {

      boolean xIntercept = false;
      boolean yIntercept = false;

      if (a.xmin() < b.xmin()) {
         xIntercept = (a.xmax() >= b.xmin());
      } else {
         xIntercept = (b.xmax() >= a.xmin());
      }

      if (a.ymin() < b.ymin()) {
         yIntercept = (a.ymax() >= b.ymin());
      } else {
         yIntercept = (b.ymax() >= a.ymin());
      }

      return (xIntercept && yIntercept);
   }

   private double rectDist(Point2D p, RectHV rect) {
      double result = Double.POSITIVE_INFINITY;

      /*
       * if x coordinate in range of rect then distance is differecne between
       * xcood
       */
      if (p.x() >= rect.xmin() && p.x() <= rect.xmax()) {
         if (p.y() >= rect.ymin() && p.y() <= rect.ymax()) {
            result = 0;
         } else if (p.y() < rect.ymin()) {
            result = rect.ymin() - p.y();
         } else {
            result = p.y() - rect.ymax();
         }
      } else if (p.y() >= rect.ymin() && p.y() <= rect.ymax()) {
         /*
          * don't need to service case with point falling into x rect range
          * because it is serviced in 'if' above
          */
         if (p.x() < rect.xmin()) {
            result = rect.xmin() - p.x();
         } else {
            result = p.x() - rect.xmax();
         }
      } else if (p.x() < rect.xmin() && p.y() < rect.ymin()) {
         result = p.distanceTo(new Point2D(rect.xmin(), rect.ymin()));
      } else if (p.x() > rect.xmax() && p.y() < rect.ymin()) {
         result = p.distanceTo(new Point2D(rect.xmax(), rect.ymin()));
      } else if (p.x() < rect.xmin() && p.y() > rect.ymax()) {
         result = p.distanceTo(new Point2D(rect.xmin(), rect.ymax()));
      } else {
         result = p.distanceTo(new Point2D(rect.xmax(), rect.ymax()));
      }

      return result;
   }

   private boolean rectContains(Point2D p, RectHV rect) {
      boolean result = false;
      if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin()
            && p.y() <= rect.ymax()) {
         result = true;
      }

      return result;
   }

   private ReturnPair minDistanceInternal(Point2D p, Node currentNode,
         double minDistance) {

      Point2D minPoint = new Point2D(4, 4);
      Node firstNode = null;
      Node secondNode = null;

      ReturnPair firstMinPointPair = null;
      ReturnPair secondMinPointPair = null;
      double currentDistance = Double.POSITIVE_INFINITY;

      double tempMinDistance = minDistance;
      /* check current node distance */
      currentDistance = p.distanceTo(currentNode.p);

      if (currentDistance < tempMinDistance) {
         tempMinDistance = currentDistance;
         minPoint = currentNode.p;
      }

      if (null != currentNode.lb) {
         if (rectContains(p, currentNode.lb.rect)) {
            firstNode = currentNode.lb;
            if (null != currentNode.rt) {
               double rectDistance = rectDist(p, currentNode.rt.rect);
               if (rectDistance < tempMinDistance) {
                  secondNode = currentNode.rt;
               }
            }
         } else {
            if (null != currentNode.rt) {
               if (rectContains(p, currentNode.rt.rect)) {
                  firstNode = currentNode.rt;

                  double rectDistance = rectDist(p, currentNode.lb.rect);
                  if (rectDistance < tempMinDistance) {
                     secondNode = currentNode.lb;
                  }
               }
            }
         }
      } else {
         if (null != currentNode.rt) {
            if (rectContains(p, currentNode.rt.rect)) {
               firstNode = currentNode.rt;
            }
         }
      }

      /*
       * No rect containing referece point - select left as first if it's rect
       * is closer than min distance
       */
      if (null == firstNode && null == secondNode) {
         if (null != currentNode.lb) {
            double rectDistance = rectDist(p, currentNode.lb.rect);
            if (rectDistance < tempMinDistance) {
               firstNode = currentNode.lb;
            }
         }

         if (null != currentNode.rt) {
            double rectDistance = rectDist(p, currentNode.rt.rect);
            if (rectDistance < tempMinDistance) {
               if (null == firstNode) {
                  firstNode = currentNode.rt;
               } else {
                  secondNode = currentNode.rt;
               }
            }
         }
      }

      if (null != firstNode) {
         firstMinPointPair = minDistanceInternal(p, firstNode, tempMinDistance);
         // currentDistance = p.distanceTo(firstMinPoint);
         if (firstMinPointPair.getDistance() < tempMinDistance) {
            tempMinDistance = firstMinPointPair.getDistance();
            minPoint = firstMinPointPair.getPoint();
         }
      }

      if (null != secondNode) {
         secondMinPointPair = minDistanceInternal(p, secondNode, tempMinDistance);
         // currentDistance = p.distanceTo(secondMinPoint);
         if (secondMinPointPair.getDistance() < tempMinDistance) {
            tempMinDistance = secondMinPointPair.getDistance();
            minPoint = secondMinPointPair.getPoint();
         }
      }

      return new ReturnPair(minPoint, tempMinDistance);
   }

   private void rangeInternal(Node rangeNode, RectHV rangeRect,
         Iterable<Point2D> rangeRef) {
      if (rectContains(rangeNode.p, rangeRect)) {
         ((Stack<Point2D>) rangeRef).push(rangeNode.p);
      }

      if (null != rangeNode.lb) {
         if (interceptRect(rangeRect, rangeNode.lb.rect)) {
            rangeInternal(rangeNode.lb, rangeRect, rangeRef);
         } else {
            if (rectContains(rangeNode.lb.p, rangeRect)) {
               ((Stack<Point2D>) rangeRef).push(rangeNode.lb.p);
            }
         }
      }

      if (null != rangeNode.rt) {
         if (interceptRect(rangeRect, rangeNode.rt.rect)) {
            rangeInternal(rangeNode.rt, rangeRect, rangeRef);
         } else {
            if (rectContains(rangeNode.rt.p, rangeRect)) {
               ((Stack<Point2D>) rangeRef).push(rangeNode.rt.p);
            }
         }
      }
   }

   // is the set empty?
   public boolean isEmpty() {
      return null == kdTree;
   }

   // number of points in the set
   public int size() {
      return kdTreeSize;
   }

   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
      int level = 0;

      if (null == kdTree) {
         RectHV rect = new RectHV(0, 0, 1, 1);
         Node node = new Node(p);
         node.setRect(rect);
         kdTree = node;
         kdTreeSize++;
      } else {
         Node currentNode = kdTree;
         boolean inserted = false;
         do {
            double result = currentNode.compareNodeK2(new Node(p), level);
            if (0 < result) {
               /* go left */
               if (null != currentNode.lb) {
                  currentNode = currentNode.lb;
               } else {
                  insertNewNode(currentNode, p, level, 2 * (level % 2));
                  inserted = true;
               }
            } else {
               if (0 == result) {
                  if (currentNode.p.equals(p)) {
                     break;
                  }
               }

               /* go right */
               if (null != currentNode.rt) {
                  currentNode = currentNode.rt;
               } else {
                  insertNewNode(currentNode, p, level, 2 * (level % 2) + 1);
                  inserted = true;
               }

            }
            level++;
         } while (!inserted);
      }
   }

   // does the set contain point p?
   public boolean contains(Point2D p) {
      boolean result = false;
      int level = 0;

      Node currentNode = kdTree;
      if (null != currentNode) {
         do {
            if (0 != p.compareTo(currentNode.p)) {
               double compResult = currentNode
                     .compareNodeK2(new Node(p), level);
               if (0 < compResult) {
                  /* go left/down */
                  currentNode = currentNode.lb;
               } else {
                  /* go right/top */
                  currentNode = currentNode.rt;
               }
               level++;
            } else {
               result = true;
               break;
            }

         } while (null != currentNode);
      }

      return result;
   }

   // draw all points to standard draw
   public void draw() {

      Node currentNode = kdTree;
      int level = 0;
      if (null != currentNode) {
         // currentNode.draw(level);
         drawInternal(currentNode, level);
      }
   }

   // all points that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect) {
      Stack<Point2D> rangePoints = new Stack<Point2D>();

      Node currentNode = kdTree;

      if (null != currentNode) {
         rangeInternal(currentNode, rect, rangePoints);
      }

      return rangePoints;
   }

   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
      ReturnPair minPointPair;

      double minDistance = Double.POSITIVE_INFINITY;

      Node currentNode = kdTree;

      if (null != currentNode) {
         minPointPair = minDistanceInternal(p, currentNode, minDistance);
         return minPointPair.point;

      } else {
         return null;
      }
   }

   private class ReturnPair {
      private final Point2D point;
      private final double distance;

      public ReturnPair(Point2D p, double dist) {
         point = p;
         distance = dist;
      }

      public Point2D getPoint()
      {
         return point;
      }

      public double getDistance()
      {
         return distance;
      }
   }

   private static class Node {
      // the point
      private Point2D p;
      // the axis-aligned rectangle corresponding to this node
      private RectHV rect;
      // the left/bottom subtree
      private Node lb;
      // the right/top subtree
      private Node rt;

      // Constructor
      public Node(Point2D point) {
         p = point;
         lb = null;
         rt = null;
      }

      // compare Nodes
      private double compareNodeK2(Node that, int level) {
         double result;
         if (0 == level % 2) {
            /* compare by x cooridnates */
            result = this.p.x() - that.p.x();
         } else {
            /* compare by y coordinates */
            result = this.p.y() - that.p.y();
         }
         return result;
      }

      private void setRect(RectHV rectData) {
         this.rect = rectData;
      }

      private void draw(int level) {
         /* Draw point */
         StdDraw.setPenColor(StdDraw.BLACK);
         StdDraw.setPenRadius(0.01);
         StdDraw.point(this.p.x(), this.p.y());

         if (0 == level % 2) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(this.p.x(), this.rect.ymin(), this.p.x(),
                  this.rect.ymax());
         } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(this.rect.xmin(), this.p.y(), this.rect.xmax(),
                  this.p.y());
         }
         StdDraw.show(40);
      }
   }

   // unit testing of the methods (optional)
   public static void main(String[] args) {
      String filename = args[0];
      In refIn = new In(filename);

      // initialize the data structures with N points from standard input
      KdTree kdtree = new KdTree();
      while (!refIn.isEmpty()) {
         double x = refIn.readDouble();
         double y = refIn.readDouble();
         Point2D p = new Point2D(x, y);
         kdtree.insert(p);
      }

      In in = new In(filename);
      while (!in.isEmpty()) {
         double x = in.readDouble();
         double y = in.readDouble();
         Point2D p = new Point2D(x, y);
         boolean contains = kdtree.contains(p);
         System.out
               .println("Point: " + p.toString() + " contains? " + contains);
      }
   }
}