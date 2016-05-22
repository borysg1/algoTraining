#include <stdio.h>

#define MAX_COINS 100

int boxes[MAX_COINS];
int maxVal = -1;

static int mult(int * tab, int size)
{
   int cntr;
   int mult = 1;
   for (cntr = 0; cntr < size; cntr++)
      mult *= tab[cntr];

   return mult;
}

int cntMaxVal(int coin, int group)
{
   int cntr;
   int coinTemp = coin;
   int localMaxVal = -1;
   int incIndex = 0;
   /*First drop*/
   for (cntr = 0; cntr < group - 1; cntr++)
   {
      boxes[cntr] = 1;
      coinTemp--;
   }
   boxes[group - 1] = coinTemp;

   localMaxVal = mult(boxes, group);

   if (maxVal < localMaxVal)
      maxVal = localMaxVal;
 
   printf("prevLast boxes: %d\n", boxes[group -1]);

   while ( 0 != boxes[group - 1])
   {
      boxes[incIndex++]++;
      boxes[group - 1]--;
      if ((group - 1) == incIndex)
         incIndex = 0;

      localMaxVal = mult(boxes, group);
      if (maxVal < localMaxVal)
         maxVal = localMaxVal;
   }

   return maxVal;
}

int main(int argc, char * argv[])
{
   int testCnt;
   int cntr;
   int coinCnt;
   int groupCnt;

   scanf("%d", &testCnt);

   printf("Start testCnt: %d\n", testCnt);

   for (cntr = 0; cntr < testCnt; cntr++)
   {
      int localMaxVal;
      scanf("%d %d", &coinCnt, &groupCnt);

      localMaxVal = cntMaxVal(coinCnt, groupCnt);

      printf("#%d %d\n", cntr, localMaxVal);
   }

   return 0;
}
