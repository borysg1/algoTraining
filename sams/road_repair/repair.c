#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <stdio.h>

#define MAX_DIST        (unsigned int)10000
#define MAX_POINT_CNT   (unsigned int)10000
#define MAX_REPAIR_CNT  (unsigned int)400

#define INVALID         (unsigned int)20000
#define INVALIDi        (int)-1

typedef struct pairStr
{
   int origIdx;
   int tabIdx;
}pairS;

typedef struct powerStr
{
   int leftIdx;
   int power;
}powerS;

static void sort(int * tab, int tabLen)
{
   int i;
   int j;
   for (i = 0; i < tabLen; i++)
   {
      for (j = i; j > 0; j--)
      {
         if (tab[j] < tab[j - 1])
         {
            int temp = tab[j - 1];
            tab[j - 1] = tab[j];
            tab[j] = temp;
         }
      }
   }
}

static pairS lowest(int * tab, int tabLen, int leftIdx, int rightIdx)
{
   int lo = 0;
   int hi = tabLen - 1;
   pairS pair;
   pair.origIdx = INVALID;
   pair.tabIdx = INVALID;

   while (lo <= hi)
   {
      int mid = (lo + hi) / 2;
      if (tab[mid] == leftIdx)
      {
         pair.origIdx = tab[mid];
         pair.tabIdx = mid;
         return pair;
      }
      if (tab[mid] < leftIdx)
      {
         lo = mid + 1;
      }
      else
      {
         if (tab[mid] <= rightIdx)
         {
            pair.tabIdx = mid;
            pair.origIdx = tab[mid];
         }
         
         hi = mid - 1;
      }
   }

   return pair;
}

static pairS highest(int * tab, int tabLen, int leftIdx, int rightIdx)
{
   int lo = 0;
   int hi = tabLen - 1;
   pairS pair;
   pair.origIdx = INVALID;
   pair.tabIdx = INVALID;

   while (lo <= hi)
   {
      int mid = (lo + hi) / 2;
      if (tab[mid] == rightIdx)
      {
         pair.origIdx = tab[mid];
         pair.tabIdx = mid;
         return pair;
      }
      if (tab[mid] < rightIdx)
      {
         if (tab[mid] >= leftIdx)
         {
            pair.tabIdx = mid;
            pair.origIdx = tab[mid];
         }
         lo = mid + 1;
      }
      else
      {
         hi = mid - 1;
      }
   }

   return pair;
}

static int present(int * tab, int tabLen, int val)
{
   int lo = 0;
   int hi = tabLen - 1;
   int ret = 0;

   while (lo <= hi)
   {
      int mid = (lo + hi) / 2;
      if (tab[mid] == val) return 1;
      if (tab[mid] > val)
      {
         hi = mid - 1;
      }
      else
      {
         lo = mid + 1;
      }
   }

   return 0;
}


int main (int argc, char * argv[])
{
   FILE *fd;
   int testCnt;
   int Kdist;
   int Npoints;

   int i;
   powerS powerVal;
   int points[MAX_REPAIR_CNT];
   int marked[MAX_POINT_CNT];

   freopen("data.txt", "r", stdin);

   scanf("%d", &testCnt);
 
   printf("Test cnt %d\n", testCnt);
   for (i = 0; i < testCnt; i++)
   {
      scanf("%d", &Kdist);
      scanf("%d", &Npoints);

      for (i = 0; i < points[Npoints - 1]; i++)
         marked[i] = 0;
      
      for (i = 0; i < Npoints; i++)
      {
         scanf("%d", &points[i]);
      }


      for (i = 0; i < Npoints; i++)
         printf("%d\t", points[i]);
      printf("\n");
      
      sort(points, Npoints);

      for (i = 0; i < Npoints; i++)
         printf("%d\t", points[i]);
      printf("\n");
   
      /*printf("present 21?: %d\n", present(points, Npoints, 21));
      printf("present 1?: %d\n", present(points, Npoints, 1));
      printf("present 2?: %d\n", present(points, Npoints, 2));
      printf("present 22?: %d\n", present(points, Npoints, 22));
      printf("present 23?: %d\n", present(points, Npoints, 23));*/

      /*pairS low = lowest(points, Npoints, 1, 3);
      pairS high = highest(points, Npoints, 1, 3);
      printf("Limit 1 3 LOW: orig: %d tab: %d HIGH: orig: %d tab: %d\n", low.origIdx, low.tabIdx, high.origIdx, high.tabIdx);

      low = lowest(points, Npoints, 5, 7);
      high = highest(points, Npoints, 5, 7);
      printf("Limit 5 7 LOW: orig: %d tab: %d HIGH: orig: %d tab: %d\n", low.origIdx, low.tabIdx, high.origIdx, high.tabIdx);*/

      int total = 0;
      for (int i = 0; i < Npoints; i++)
      {
         int leftLimit = points[i] + 1 - Kdist;
         int rightLimit = points[i] + Kdist -1;

         if (leftLimit < 0) leftLimit = 0;
         if (rightLimit > points[Npoints - 1]) rightLimit = points[Npoints - 1];

         powerVal.leftIdx = INVALID;
         powerVal.power = 0;

         /*Scan point neighborhood*/
         for (int shift = leftLimit; shift <= (rightLimit - Kdist + 1); shift++ )
         {
            pairS low = lowest(points, Npoints, shift, shift + Kdist - 1);
            pairS high = highest(points, Npoints, shift, shift + Kdist - 1);

            int totalFixedInArea = high.tabIdx - low.tabIdx + 1;
            int closedAreas = 0;
            int fixedInMarked = 0;

            if (low.tabIdx != INVALID && high.tabIdx != INVALID)
            {
               for (int scanIdx = shift; scanIdx < shift + Kdist; scanIdx++)
               {
                  if (marked[scanIdx])
                  {
                     /*Count closed areas - power of this selectio*/
                     closedAreas++;
                     if (present(points, Npoints, scanIdx))
                     {
                        /* Mark fixing areas that are in marked zone of previous closure 
                         * this is required to determine how many fixing areas are in not closed zone yet.
                         */
                        fixedInMarked++;
                     }
                  }
                  else
                  {
                     closedAreas += (totalFixedInArea - fixedInMarked);
                     break;
                  }
               }
            }
            
            if (closedAreas >= powerVal.power)
            {
               powerVal.power = closedAreas;
               powerVal.leftIdx = shift;
            }

         }

         for (int mark = powerVal.leftIdx; mark < powerVal.leftIdx + Kdist; mark++)
         {
            if (!marked[mark])
            {
               marked[mark] = 1;
               total++;
            }
         }
      }

      printf("Result: %d\n", total);
   }
}
