#include <stdio.h>

#define POINT_MAX_CNT      (unsigned int)10000

typedef struct RectStruct
{
   unsigned int width;
   unsigned int height;
   unsigned int size;
}RectS;

static unsigned int tab[POINT_MAX_CNT];
static unsigned int visited[POINT_MAX_CNT];
static RectS matrices[20];
static unsigned int xMin, xMax, yMin, yMax;

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

static void findMinMax(int idx, int dim)
{
   int currentIdx = idx;
   int nextIdx;

   /*Find x_min, x_max -> go max to left then max to right*/
   while ((((unsigned int)-1) != (nextIdx = leftIdx(currentIdx, dim))) && (0 != tab[nextIdx]))
   {
      yMin = nextIdx % dim;
      currentIdx = nextIdx;
   }

   currentIdx = idx;
   while ((((unsigned int)-1) != (nextIdx = rightIdx(currentIdx, dim))) && (0 != tab[nextIdx]))
   {
      yMax = nextIdx % dim;      
      currentIdx = nextIdx;
   }

   currentIdx = idx;
   while ((((unsigned int)-1) != (nextIdx = upIdx(currentIdx, dim))) && (0 != tab[nextIdx]))
   {
      xMin = nextIdx / dim;      
      currentIdx = nextIdx;
   }

   currentIdx = idx;
   while ((((unsigned int)-1) != (nextIdx = downIdx(currentIdx, dim))) && (0 != tab[nextIdx]))
   {
      xMax = nextIdx / dim; 
      currentIdx = nextIdx;
   }
}

static void sortRect(RectS * rectTab, unsigned int rectCnt)
{
   unsigned int i,j;
   
   RectS tempRect;
   unsigned int minSize;
   unsigned int minIdx;
   for (i = 0; i < rectCnt; i++)
   {
      minIdx = i;
      minSize = rectTab[i].size;
      for (j = i + 1; j < rectCnt; j++)
      {
         if (minSize > rectTab[j].size)
         {
            minIdx = j;
            minSize = rectTab[j].size;
         }
      }

      if (i != minIdx)
      {
         tempRect = rectTab[minIdx];
         rectTab[minIdx] = rectTab[i];
         rectTab[i] = tempRect;
      }
   }
}

static void complementVisited(int min_x, int max_x, int min_y, int max_y, int dim)
{
   int x_cntr, y_cntr;
   for (x_cntr = min_x; x_cntr <= max_x; x_cntr++)
   {
      for (y_cntr = min_y; y_cntr <= max_y; y_cntr++)
      {
         visited[(x_cntr * dim) + y_cntr] = 1;
      }
   }
}

int main (int argc, char * argv[])
{
   unsigned int testCnt = 0;
   unsigned int dim = 0;
   unsigned int cntr, inerCntr, findCntr, resCntr;
   unsigned int arraySize = 0;
   unsigned int rectIdx = 0;
   
   scanf("%d", &testCnt);

   for (cntr = 0; cntr < testCnt; cntr++)
   {
      rectIdx = 0;
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
      arraySize = dim * dim;  
      for (findCntr = 0; findCntr < arraySize; findCntr++)
      {
         if ((0 == visited[findCntr]) && (0 != tab[findCntr]))
         {
            xMin = xMax = findCntr / dim;
            yMin = yMax = findCntr % dim;
            findMinMax(findCntr, dim);
            complementVisited(xMin, xMax, yMin, yMax, dim);
            matrices[rectIdx].width = xMax - xMin + 1;
            matrices[rectIdx].height = yMax - yMin + 1;
            matrices[rectIdx].size = matrices[rectIdx].width * matrices[rectIdx].height;
            rectIdx++;
         }
      }

      sortRect(matrices, rectIdx);

      printf("#%d %d", cntr, rectIdx);

      for (resCntr = 0; resCntr < rectIdx; resCntr++)
      {
         printf(" %d %d", matrices[resCntr].width, matrices[resCntr].height);
      }
      printf("\n");
   }

   return 0;
}

