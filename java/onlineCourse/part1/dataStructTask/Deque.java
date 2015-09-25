import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> 
{
   private Node dequeHead;
   private Node dequeTail;
   private int dequeSize;

   /*construct an empty deque*/
   public Deque()
   {
      dequeHead = null;
      dequeTail = null;
      dequeSize = 0;
   }

   /*is the deque empty?*/
   public boolean isEmpty()
   {
      return (null == dequeHead);
   }
   
   /*return the number of items on the deque*/
   public int size()
   {
      return dequeSize;
   }
   
   /*add the item to the front*/
   public void addFirst(Item item)
   {
      if (null == item)
         throw new java.lang.NullPointerException();

      Node newNode = new Node();
      newNode.item = item;

      if (null == dequeHead)
      {
         /*Dequeue empty*/
         newNode.next = null;
         newNode.prev = null;
         dequeHead = newNode;
         dequeTail = newNode;
      }
      else
      {
         /*Dequeue has one element*/
         newNode.next = dequeHead;
         newNode.prev = null;
         dequeHead.prev = newNode;
         dequeHead = newNode;
         
      }
      dequeSize++;
   }
   

   /*add the item to the end*/
   public void addLast(Item item)
   {
      if (null == item)
         throw new java.lang.NullPointerException();

      Node newNode = new Node();
      newNode.item = item;
      
      if (null == dequeTail)
      {
         /*Dequeue empty*/
         newNode.next = null;
         newNode.prev = null;
         dequeHead = newNode;
         dequeTail = newNode;
      }
      else
      {
         newNode.prev = dequeTail;
         newNode.next = null;
         dequeTail.next = newNode;
         dequeTail = newNode;
      }
      dequeSize++;
   }

   /*remove and return the item from the front*/
   public Item removeFirst()
   {
      if (null == dequeHead)
         throw new java.util.NoSuchElementException();

      Item item = dequeHead.item;
      if (null == dequeHead.next)
      {
         dequeHead = null;
         dequeTail = null;
      }
      else
      {
          Node currentHead = dequeHead;
          dequeHead.next.prev = null;
          dequeHead = dequeHead.next;
          /*Loitering*/
          currentHead = null;
      }
      dequeSize--;
      return item;
   }

   /*remove and return the item from the end*/
   public Item removeLast()
   {
      if (null == dequeTail)
         throw new java.util.NoSuchElementException();

      Item item = dequeTail.item;
      if (null == dequeTail.prev)
      {
         dequeTail = null;
         dequeHead = null;
         
      }
      else
      {
         Node currentTail = dequeTail;
         dequeTail.prev.next = null;
         dequeTail = dequeTail.prev;
         /*Loitering*/
         currentTail = null;
      }
      
      dequeSize--;
      return item;
   }

   /*return an iterator over items in order from front to end*/
   public Iterator<Item> iterator()
   {
      return new DequeIterator();
   }
   
   private class Node
   {
      private Item item;
      private Node next;
      private Node prev;
   }
   
   private class DequeIterator implements Iterator<Item>
   {
      private Node current = dequeHead;
      
      public boolean hasNext() {
         return (null != current);
      }

      public Item next() {
         if (null == current)
            throw new java.util.NoSuchElementException();

         Item item = current.item;
         current = current.next;
         return item;
      }

      public void remove() {
         // TODO Auto-generated method stub
         throw new java.lang.UnsupportedOperationException();
      }
   }
   
   /*unit testing*/
   public static void main(String[] args)
   {
      System.out.println("Start!");
      Deque<String> myDequeue = new Deque<String>();
      myDequeue.addLast("Test");
      
      //Iterator<String> it = myDequeue.iterator();
      //it 
      
      System.out.println("Size = " + myDequeue.size());

/*      myDequeue.addFirst("front1");
      System.out.println("Size = " + myDequeue.size());
      myDequeue.addFirst("front2");
      System.out.println("Size = " + myDequeue.size());
      myDequeue.addFirst("front3");
      System.out.println("Size = " + myDequeue.size());
      myDequeue.addFirst("front4");
      System.out.println("Size = " + myDequeue.size());
      myDequeue.addLast("last1");
      System.out.println("Size = " + myDequeue.size());
      myDequeue.addLast("last2");
      System.out.println("Size = " + myDequeue.size());
      myDequeue.addLast("last3");
      System.out.println("Size = " + myDequeue.size());
      myDequeue.addLast("last4");
      System.out.println("Size = " + myDequeue.size());
      
      if (null != myDequeue.dequeHead)
      {
         System.out.println("Head " + myDequeue.dequeHead.item);
         Iterator<String> it1 = myDequeue.iterator();
         
         while (it1.hasNext())
         {
            System.out.println("nextElem " + it1.next());
         }
      }

      myDequeue.removeFirst();
      System.out.println("Size = " + myDequeue.size());
      myDequeue.removeFirst();
      System.out.println("Size = " + myDequeue.size());
      myDequeue.removeFirst();
      System.out.println("Size = " + myDequeue.size());
      myDequeue.removeFirst();
      System.out.println("Size = " + myDequeue.size());
      myDequeue.removeFirst();
      System.out.println("Size = " + myDequeue.size());
      myDequeue.removeFirst();
      System.out.println("Size = " + myDequeue.size());
      myDequeue.removeFirst();
      System.out.println("Size = " + myDequeue.size());
      myDequeue.removeFirst();
      System.out.println("Size = " + myDequeue.size());

      if (null != myDequeue.dequeHead)
      {
         System.out.println("Head " + myDequeue.dequeHead.item);
         Iterator<String> it2 = myDequeue.iterator();
         
         while (it2.hasNext())
         {
            System.out.println("nextElem " + it2.next());
         }
      }

      myDequeue.addFirst("front1");
      if (null != myDequeue.dequeHead)
      {
         System.out.println("Head " + myDequeue.dequeHead.item);
         Iterator<String> it2 = myDequeue.iterator();
         
         while (it2.hasNext())
         {
            System.out.println("nextElem " + it2.next());
         }
      }

      System.out.println("Remove last after inserting first");
      myDequeue.removeLast();
      if (null != myDequeue.dequeHead)
      {
         System.out.println("Head " + myDequeue.dequeHead.item);
         Iterator<String> it2 = myDequeue.iterator();
         
         while (it2.hasNext())
         {
            System.out.println("nextElem " + it2.next());
         }
      }
      */
   }
   

}