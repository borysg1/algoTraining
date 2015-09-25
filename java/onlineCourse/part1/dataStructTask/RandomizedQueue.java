import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
   private Item [] itemTab;
   private int N;
   /*construct an empty randomized queue*/
   public RandomizedQueue()
   {
      N = 0;
      itemTab = (Item []) new Object[1];
   }
   
   /* is the queue empty?*/
   public boolean isEmpty()
   {
      return (0 == N);
   }

   /*return the number of items on the queue*/
   public int size()
   {
      return N;
   }
   
   /*add the item*/
   public void enqueue(Item item)
   {
      if (null == item)
         throw new java.lang.NullPointerException();
      
      if (N == itemTab.length)
         resize(2*N);
      
      itemTab[N++] = item;
   }

   /*remove and return a random item*/
   public Item dequeue()
   {
      if (0 == N)
         throw new java.util.NoSuchElementException();

      int randomIdx = StdRandom.uniform(N);
      Item randomItem = itemTab[randomIdx];
      itemTab[randomIdx] = itemTab[--N];
      itemTab[N] = null;
      
      if (N > 0 && N == itemTab.length/4)
         resize(itemTab.length/2);
      
      return randomItem;
   }

   /*return (but do not remove) a random item*/
   public Item sample()
   {
      if (0 == N)
         throw new java.util.NoSuchElementException();

      int randomIdx = StdRandom.uniform(N);
      Item randomItem = itemTab[randomIdx];
      return randomItem;
   }
   
   /*return an independent iterator over items in random order*/
   public Iterator<Item> iterator()
   {
      
      return new RandomizedQueueIterator();
   }
   
   private class RandomizedQueueIterator implements Iterator<Item>
   {
      private int [] randomIdxtTab;
      private int randomIdxTabIdx;
      
      public RandomizedQueueIterator()
      {
         randomIdxtTab = new int [N];
         randomIdxTabIdx = 0;
         
         /*Create random idxs for iterator*/
         for (int i = 0; i < randomIdxtTab.length; i++)
         {
            randomIdxtTab[i] = i;
            int r = StdRandom.uniform(i+1);
            exch(randomIdxtTab, i, r);
         }
      }
      @Override
      public boolean hasNext() {
         // TODO Auto-generated method stub
         //System.out.println("randomIdxTabIdx " + randomIdxTabIdx + " randomIdxtTab.length-1) " + (randomIdxtTab.length-1));
         return (randomIdxTabIdx < (randomIdxtTab.length));
      }

      @Override
      public Item next() {
         // TODO Auto-generated method stub
         if (randomIdxTabIdx == N)
            throw new java.util.NoSuchElementException();

         //System.out.println("Index " + randomIdxTabIdx + " outer index " + randomIdxtTab[randomIdxTabIdx]);
         return itemTab[randomIdxtTab[randomIdxTabIdx++]];
      }

      @Override
      public void remove() {
         // TODO Auto-generated method stub
         throw new java.lang.UnsupportedOperationException();
      }
      
   }

   private void resize(int capacity)
   {
      Item [] newTab = (Item []) new Object[capacity];
      for (int i = 0; i < N; i++)
      {
         newTab[i] = itemTab[i];
      }
      
      itemTab = newTab;
   }
   
   private void exch(int [] tab, int idxA, int idxB)
   {
      int swap = tab[idxA];
      tab[idxA] = tab[idxB];
      tab[idxB] = swap;
   }
   /* unit testing*/
   public static void main(String[] args)
   {
      System.out.println("Start!");
      
      RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
      randQueue.enqueue("String1");
      randQueue.enqueue("String2");
      randQueue.enqueue("String3");
      randQueue.enqueue("String4");
      randQueue.enqueue("String5");
      
      
      System.out.println("Use RandomizedQueueIterator it1 to display RandomizedQueue!");
      Iterator<String> it1 = randQueue.iterator();
      if (!randQueue.isEmpty())
      {
         while (it1.hasNext())
         {
            System.out.println("next item: " + it1.next());
         }
      }

      System.out.println("Use RandomizedQueueIterator it2 to display RandomizedQueue!");
      Iterator<String> it2 = randQueue.iterator();
      if (!randQueue.isEmpty())
      {
         while (it2.hasNext())
         {
            System.out.println("next item: " + it2.next());
         }
      }
      
      System.out.println("RandomizedQueue size " + randQueue.size());
      
      randQueue.dequeue();
      System.out.println("RandomizedQueue size " + randQueue.size());

      System.out.println("Use RandomizedQueueIterator it3 to display RandomizedQueue!");
      Iterator<String> it3 = randQueue.iterator();
      if (!randQueue.isEmpty())
      {
         while (it3.hasNext())
         {
            System.out.println("next item: " + it3.next());
         }
      }

   }
}