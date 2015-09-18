import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation
{
   private int size;
   private boolean [] ufTabOpen;
   private WeightedQuickUnionUF wQuickUF;
   private WeightedQuickUnionUF wQuickUFBackwash;
   private int topIdx;
   private int bottomIdx;

   /*creates N-by-N grid, with all sites blocked*/
   public Percolation(int N)
   {

      if (N > 0) 
      {
         /*+2 because of 2 virtual roots */
         int arraySize = N*N+2;
         size = N;

         /*N-N array + 2 virtual roots open status - default value of 0 guaranteed 
          * by the language spec*/
         ufTabOpen = new boolean[N*N+2];
         wQuickUF = new WeightedQuickUnionUF(arraySize);
         wQuickUFBackwash = new WeightedQuickUnionUF(arraySize);
         
         /*Open both virtual roots*/
         /*Top*/
         ufTabOpen[arraySize - 2] = true;
         /*Bottom*/
         ufTabOpen[arraySize - 1] = true;
         
         topIdx = arraySize -2;
         bottomIdx = arraySize - 1;
      }
      else
      {
         throw new java.lang.IllegalArgumentException();
      }

   }
   
   /*open site (row i, column j) if it is not opened already*/
   public void open(int i, int j)
   {
      validateIdxs(i, j);
      
      int val = xyTo1D(i, j);
      int idx = valToIdx(val);

      /*Open element*/
      ufTabOpen[idx] = true;
      
      
      if (i > 1)
      {
         if (ufTabOpen[getTopIdx(idx)])
         {
            wQuickUF.union(idx, getTopIdx(idx));
            wQuickUFBackwash.union(idx, getTopIdx(idx));
         }
      }
      else
      {
         /*Connect with top virtual root*/
         wQuickUF.union(idx, topIdx);
         wQuickUFBackwash.union(idx, topIdx);
      }
      
      if (i < size)
      {
         if (ufTabOpen[getBottomIdx(idx)])
         {
            wQuickUF.union(idx, getBottomIdx(idx));
            wQuickUFBackwash.union(idx, getBottomIdx(idx));
         }
      }
      else
      {
         /*Connect with bottom virtual root*/
         wQuickUF.union(idx, bottomIdx);
      }

      if (j > 1)
      {
         if (ufTabOpen[getLeftIdx(idx)])
         {
            wQuickUF.union(idx, getLeftIdx(idx));
            wQuickUFBackwash.union(idx, getLeftIdx(idx));
         }
      }

      if (j < size)
      {
         if (ufTabOpen[getRightIdx(idx)])
         {
            wQuickUF.union(idx, getRightIdx(idx));
            wQuickUFBackwash.union(idx, getRightIdx(idx));
         }
      }
   }

   /*is site (row i, column j) opened?*/
   public boolean isOpen(int i, int j)
   {
      validateIdxs(i, j);
      
      int val = xyTo1D(i, j);
      int idx = valToIdx(val);
      
      return ufTabOpen[idx];
   }

   /*is site (row i, column j) full? - if site is connected with at least one site 
    * from top row => the system percolates if at least one site from bottom row is 
    * full*/
   public boolean isFull(int i, int j)
   {
      validateIdxs(i, j);

      int val = xyTo1D(i, j);
      int idx = valToIdx(val);

      return wQuickUFBackwash.connected(idx, topIdx);

   }

   /*does the system percolates*/
   public boolean percolates()
   {
      return wQuickUF.connected(topIdx, bottomIdx);
   }
   
   private int xyTo1D(int i, int j)
   {
      return i*size + j;
   }
   
   private int valToIdx(int val)
   {
      return val -size - 1;
   }
   
/*   private int idxToVal(int idx)
   {
      return idx + size + 1;
   }*/
   
   private int getLeftIdx(int idx)
   {
      return idx - 1;
   }

   private int getRightIdx(int idx)
   {
      return idx + 1;
   }

   private int getTopIdx(int idx)
   {
      return idx - size;
   }

   private int getBottomIdx(int idx)
   {
      return idx + size;
   }

   private void validateIdxs(int i, int j)
   {
      if (i < 1 || i > size || j < 1 || j > size)
      {
         throw new java.lang.IndexOutOfBoundsException();
      }
   }
   
   /*test client (optional)*/
   public static void main(String [] args)
   {
      final int arraySize = Integer.parseInt(args[0]);
      
      Percolation perc = new Percolation(Integer.parseInt(args[0]));
      
      int cntr = 0;
      int row, col;
      
      do
      {
         do
         {
            row = StdRandom.uniform(1, arraySize+1);
            col = StdRandom.uniform(1, arraySize+1);
         } while(perc.isOpen(row, col));

         perc.open(row, col);
         cntr++;
      } while(!perc.percolates());
      System.out.println("System percorates at i = " + cntr + " of max " 
                                 + perc.size * perc.size +" possibilities");

   }
}
