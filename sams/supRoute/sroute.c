#include <stdio.h>

#define POINT_MAX_CNT      (unsigned int)10000

static unsigned int minRoute = ((unsigned int) -1);
static unsigned int currentRoute = 0;
static unsigned int tab[POINT_MAX_CNT];
static unsigned int visited[POINT_MAX_CNT];

inline int downIdx(int idx, int dim)
{
   if ((dim - 1) != (idx / dim))
   {
      return idx + dim;
   }
   else
   {
      return ((unsigned int) -1);
   }
}

inline int upIdx(int idx, int dim)
{
   if (idx > (dim - 1))
   {
      return idx - dim;
   }
   else
   {
      return ((unsigned int) -1);
   }
}

inline int leftIdx(int idx, int dim)
{
   if (0 != (idx % dim))
   {
      return idx - 1;
   }
   else
   {
      return ((unsigned int) -1);
   }
}

inline int rightIdx(int idx, int dim)
{
   if (0 != ((idx + 1) % dim))
   {
      return idx + 1;
   }
   else
   {
      return ((unsigned int) -1);
   }
}

static void routeFind(unsigned int idx, unsigned int dim, unsigned int curMin)
{
   int nextIdx = 0;

   curMin += tab[idx];

   visited[idx] = 1;

   //printf("idx: %d curMin: %d\n", idx, curMin);

   if (curMin > minRoute)
   {
      visited[idx] = 0;
      return;
   }

   if (idx == ((dim * dim) - 1))
   {
      if (curMin < minRoute)
         minRoute = curMin;

      visited[idx] = 0;
      return;
   }

   if (((unsigned int)-1) != (nextIdx = upIdx(idx, dim)))
   {
      if (1 != visited[nextIdx])
      {
         routeFind(nextIdx, dim, curMin);
      }
   }

   if (((unsigned int)-1) != (nextIdx = downIdx(idx, dim)))
   {
      if (1 != visited[nextIdx])
      {
         routeFind(nextIdx, dim, curMin);
      }
   }
   
   if (((unsigned int)-1) != (nextIdx = leftIdx(idx, dim)))
   {
      if (1 != visited[nextIdx])
      {
         routeFind(nextIdx, dim, curMin);
      }
   }
   
   if (((unsigned int)-1) != (nextIdx = rightIdx(idx, dim)))
   {
      if (1 != visited[nextIdx])
      {
         routeFind(nextIdx, dim, curMin);
      }
   }
   visited[idx] = 0;
}

int main (int argc, char * argv[])
{
   unsigned int testCnt = 0;
   unsigned int dim = 0;
   unsigned int cntr, inerCntr;

   scanf("%d", &testCnt);

   //printf("There will be %d test cases\n", testCnt);

   for (cntr = 0; cntr < testCnt; cntr++)
   {
      scanf("%d", &dim);
      unsigned int limit = dim * dim;
      unsigned int curIdx = 0;

      for (inerCntr = 0; inerCntr < limit; inerCntr++)
      {
         scanf("%d", &tab[inerCntr]);
         visited[inerCntr] = 0;
      }
/*
      printf("print matrix\n");
      for (inerCntr = 0; inerCntr < limit; inerCntr++)
      {
         if (0 == (inerCntr % dim))
            printf("\n");

         printf("%d ", tab[inerCntr]);
      }
      printf("\n");
*/
      visited[curIdx] = 1;

      routeFind(curIdx, dim, currentRoute);

      printf("#%d %d\n", cntr, minRoute);
      
      minRoute = ((unsigned int)-1);
      currentRoute = 0;
   }

   return 0;
}

