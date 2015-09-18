import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
   private double [] simResTab;
   private int repeatCnt;

   /*perform T independent experiments on an N-by-N grid*/
   public PercolationStats(int N, int T)
   {
      if (N > 0 && T > 0)
      {
         simResTab = new double[T];
         repeatCnt = T;
         int elemCnt = N*N;

         
         for (int i = 0; i < T; i++)
         {
            Percolation perc = new Percolation(N);
            
            int cntr = 0;
            int row, col;
            
            do
            {
               do
               {
                  row = StdRandom.uniform(1, N+1);
                  col = StdRandom.uniform(1, N+1);
               } while(perc.isOpen(row, col));

               perc.open(row, col);
               cntr++;
            } while(!perc.percolates());
            
            simResTab[i] = (double) cntr/elemCnt;
            perc = null;
         }
      }
      else
      {
         throw new java.lang.IllegalArgumentException();
      }
      
   }
   /*sample mean of percolation threshold*/
   public double mean()
   {
      return StdStats.mean(simResTab);
   }
   /*sample standard deviation of percolation threshold*/
   public double stddev()
   {
      return StdStats.stddev(simResTab);
   }
   
   /*low  endpoint of 95% confidence interval*/
   public double confidenceLo()
   {
      return StdStats.mean(simResTab) - 1.96*StdStats.stddev(simResTab)
            / Math.sqrt(repeatCnt);
   }

   /*high endpoint of 95% confidence interval*/
   public double confidenceHi()
   {
      return StdStats.mean(simResTab) + 1.96*StdStats.stddev(simResTab)
            / Math.sqrt(repeatCnt);
   }

   public static void main(String [] args)
   {
      System.out.println("Start! Array size " + args[0] + " Repeat count "
                                                                  + args[1]);
      final int arraySize = Integer.parseInt(args[0]);
      final int repeatCnt = Integer.parseInt(args[1]);
      
      PercolationStats percStat = new PercolationStats(arraySize, repeatCnt);

      System.out.println("mean                      = " + percStat.mean());
      System.out.println("stddev                    = " + percStat.stddev());
      System.out.println("95% confidence interval   = " + percStat.confidenceLo()
                                                        + ", "
                                                        + percStat.confidenceHi());
      percStat = null;
   
   }
}
