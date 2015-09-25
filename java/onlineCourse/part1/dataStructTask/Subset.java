import edu.princeton.cs.algs4.StdIn;

public class Subset {
   public static void main(String[] args)
   {
      final int k = Integer.parseInt(args[0]);
      RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
      String inputString;
      //while((inputString = StdIn.readString()) != "")
      while (!StdIn.isEmpty())
      {
         inputString = StdIn.readString();
         randQueue.enqueue(inputString);
      }
      
      for (int i = 0; i < k; i++)
      {
         System.out.println(randQueue.dequeue());
      }
   }
}